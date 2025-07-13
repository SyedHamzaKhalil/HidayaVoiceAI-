package com.example.HidayaVoiceAI.model;

import com.example.HidayaVoiceAI.utils.GeneralMethods;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Document(collection = "ayah_fingerprints")
public class AyahFingerPrint {

    @Id
    private String id;

    private int surahNumber;

    private int ayahNumber;

    private String surah;

    private String transliteration;

    private String arabicText;

    private String audioUrl;

    private byte[] fingerPrint;

    public static List<AyahFingerPrint> to(WholeQuranResponse quranResponse) {
        List<AyahFingerPrint> ayahFingerPrintsList = new ArrayList<>();

        // Null check
        if (quranResponse == null || quranResponse.getData() == null || quranResponse.getData().getSurahs() == null) {
            return ayahFingerPrintsList;
        }

        for (WholeQuranResponse.Surah surah : quranResponse.getData().getSurahs()) {
            if (GeneralMethods.isEmpty(surah.getAyahs())) {
                continue;
            }
            for (WholeQuranResponse.Ayah ayah : surah.getAyahs()) {
                AyahFingerPrint fingerprint = new AyahFingerPrint()
                        .setId(UUID.randomUUID().toString())
                        .setSurahNumber(surah.getNumber())
                        .setAyahNumber(ayah.getNumber())
                        .setSurah(surah.getName()) // Use the surah name as the 'surah' field (Arabic name)
                        .setTransliteration(surah.getEnglishNameTranslation())  // This is a placeholder; adjust the transliteration as needed.
                        .setArabicText(ayah.getText()) // Use the ayah text as the Arabic text.
                        .setAudioUrl("");   // Since the audio URL is not available, leave it blank.
                ayahFingerPrintsList.add(fingerprint);
            }
        }

        return ayahFingerPrintsList;
    }

}
