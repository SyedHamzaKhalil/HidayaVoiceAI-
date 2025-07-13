package com.example.HidayaVoiceAI.Service;

import com.example.HidayaVoiceAI.Exceptions.ErrorResponse;
import com.example.HidayaVoiceAI.Exceptions.FingerPrintException;
import com.example.HidayaVoiceAI.Repository.AyahFingerPrintRepository;
import com.example.HidayaVoiceAI.model.AyahFingerPrint;
import com.example.HidayaVoiceAI.model.WholeQuranResponse;
import com.example.HidayaVoiceAI.utils.GeneralMethods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.File;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;



@Service
@Slf4j
public class AyahFingerPrintService {

    @Autowired
    AyahFingerPrintRepository ayahFingerPrintRepository;


    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    public static final MediaType JSON = MediaType.get("application/json");


    @Value("http://api.alquran.cloud/v1/quran/quran-uthmani")
    private String wholeQuranUrl;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofMinutes(3))
            .readTimeout(Duration.ofSeconds(20))
            .writeTimeout(Duration.ofSeconds(20))
            .build();


    public WholeQuranResponse getWholeQuranFromWeb() throws Exception {

        HttpUrl.Builder url = HttpUrl.parse(wholeQuranUrl).newBuilder();
        HttpUrl httpUrl = url.build();
        Request request = new Request.Builder().url(httpUrl).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                return gson.fromJson(jsonData, WholeQuranResponse.class);
            } else {
                log.info("Getting Error Response for: " + response.body().string());
                throw new Exception("Getting Error Response for Whole Quran API call");
            }
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }

    public boolean saveWholeQuranToDB(WholeQuranResponse wholeQuranResponse) {
        if (!GeneralMethods.isEmpty(wholeQuranResponse)) {
            List<AyahFingerPrint> fingerprints = AyahFingerPrint.to(wholeQuranResponse);
            List<AyahFingerPrint> saved = ayahFingerPrintRepository.saveAll(fingerprints);
            // Return true if saved list is not empty
            return saved != null && !saved.isEmpty();
        }
        return false;
    }


    public String matchAudio(File audioFile, FingerPrintExtractor fingerprintExtractor) throws FingerPrintException {
        // 1) extract fingerprint from the file
        byte[] fingerPrintFromDB = fingerprintExtractor.extractFingerprint(audioFile);
        AyahFingerPrint bestMatch = null;

        // 2) compare against all stored AyahFingerPrint records
        try {
             bestMatch = ayahFingerPrintRepository.findAll().stream()
                     .min(Comparator.comparingDouble(x -> calculateDistance(x.getFingerPrint(), fingerPrintFromDB)))
                     .orElse(null);


        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse er = new com.example.HidayaVoiceAI.Exceptions.ErrorResponse(e.getMessage()) {
            };
            return er.getMessage();
        }

        if (GeneralMethods.isEmpty(bestMatch)) {
            return null;
        }

        // 3) return a descriptive label
        return String.format("Surah %d Ayah %d", bestMatch.getSurahNumber(), bestMatch.getAyahNumber());
    }

    private double calculateDistance(byte[] fingerprint1, byte[] fingerprint2) {
        if (GeneralMethods.isEmpty(fingerprint1) || GeneralMethods.isEmpty(fingerprint2) || fingerprint1.length != fingerprint2.length) {
            return Double.MAX_VALUE;
        }

        double sum = 0.0;
        for (int i = 0; i < fingerprint1.length; i++) {
            double diff = (fingerprint1[i] & 0xFF) - (fingerprint2[i] & 0xFF);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

}
