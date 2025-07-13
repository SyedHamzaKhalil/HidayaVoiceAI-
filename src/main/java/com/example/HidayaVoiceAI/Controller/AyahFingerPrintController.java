package com.example.HidayaVoiceAI.Controller;

import static com.example.HidayaVoiceAI.Constants.*;
import static com.example.HidayaVoiceAI.utils.GeneralMethods.addMessage;
import static com.example.HidayaVoiceAI.utils.GeneralMethods.isEmpty;

import com.example.HidayaVoiceAI.Exceptions.FingerPrintException;
import com.example.HidayaVoiceAI.Repository.AyahFingerPrintRepository;
import com.example.HidayaVoiceAI.Service.AyahFingerPrintService;
import com.example.HidayaVoiceAI.Service.FingerPrintExtractor;
import com.example.HidayaVoiceAI.model.AyahFingerPrint;
import com.example.HidayaVoiceAI.model.WholeQuranResponse;
import com.example.HidayaVoiceAI.utils.GeneralMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(AYAH_FINGERPRINT_CONTROLLER_MAIN)
public class AyahFingerPrintController {

    @Autowired
    AyahFingerPrintService ayahFingerPrintService;

    @Autowired
    AyahFingerPrintRepository ayahFingerPrintRepository;

    @Autowired
    FingerPrintExtractor fingerPrintExtractor;

    @RequestMapping(value = AYAH_FINGERPRINT_TEST, method = RequestMethod.GET)
    public void ayayTest(HttpServletRequest req, HttpServletResponse resp,
                         @RequestParam(required = false) String workingString) {

        AyahFingerPrint ayahFingerPrint = new AyahFingerPrint();
        ayahFingerPrint.setAyahNumber(10)
                .setId(String.valueOf(UUID.randomUUID()))
                .setSurah(workingString);


        addMessage(req, true, "This is working");
    }


    @RequestMapping(value = UPLOAD_AUDIO, method = RequestMethod.POST)
    public ResponseEntity<String> uploadAudio(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam("audioFile") MultipartFile audio) throws IOException, FingerPrintException {

        if (isEmpty(audio)) {
            addMessage(req, false, "No Audio file was received");
            return ResponseEntity.badRequest().body("No audio file was received");
        }

        // 1) See if the file exits so we dont have to resave it



        // 1) Save the file to the user's Downloads folder
        String filename = UUID.randomUUID() + "-" + audio.getOriginalFilename();

        String userHome = System.getProperty("user.home");
        Path downloadsDir = Paths.get(userHome, "Downloads");
        Files.createDirectories(downloadsDir);
        Path storage = downloadsDir.resolve(filename);
        audio.transferTo(storage.toFile());

        // 2) Run your fingerprint + matching logic
        String matchedAyah = ayahFingerPrintService.matchAudio(storage.toFile(), fingerPrintExtractor);

        if (GeneralMethods.isEmpty(matchedAyah)) {
            addMessage(req, false, "There is no Ayah records found.");
            return ResponseEntity.badRequest().body("There is no Ayah records found. ");
        }
        // 3) Return the match result (or a URL to fetch the audio)
        return ResponseEntity.ok("Matched ayah: " + matchedAyah);
    }


    @RequestMapping(value = WHOLE_QURAN, method = RequestMethod.GET)
    public void getWholeQuranFromWeb() throws Exception {
        WholeQuranResponse wholeQuranResponse = ayahFingerPrintService.getWholeQuranFromWeb();
        if (!isEmpty(wholeQuranResponse)) {
            ayahFingerPrintService.saveWholeQuranToDB(wholeQuranResponse);
            log.info("Success!");
        } else {
            log.info("Failure!");
        }
    }


}
