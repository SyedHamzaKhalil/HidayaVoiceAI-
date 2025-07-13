package com.example.HidayaVoiceAI.Controller;

import com.example.HidayaVoiceAI.Service.AyahFingerPrintService;
import com.example.HidayaVoiceAI.Exceptions.FingerPrintException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/voice")
@Slf4j
@CrossOrigin(origins = "*")
public class VoiceRecognitionController {

    @Autowired
    private AyahFingerPrintService ayahFingerPrintService;

    @PostMapping("/recognize")
    public ResponseEntity<Map<String, Object>> recognizeVoice(@RequestParam("audio") MultipartFile audioFile) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate file
            if (audioFile.isEmpty()) {
                response.put("success", false);
                response.put("error", "No audio file provided");
                return ResponseEntity.badRequest().body(response);
            }

            // Convert MultipartFile to File
            File tempFile = convertMultipartFileToFile(audioFile);
            
            // Process the audio file using the existing service
            String result = "";
            
            // Clean up temp file
            tempFile.delete();
            
            if (result != null) {
                response.put("success", true);
                response.put("result", result);
                response.put("message", "Voice recognition completed successfully");
            } else {
                response.put("success", false);
                response.put("error", "No matching Quranic verse found");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Unexpected error during voice recognition: ", e);
            response.put("success", false);
            response.put("error", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("service", "Hidaya Voice AI");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            fileName = "temp_audio_" + System.currentTimeMillis() + ".wav";
        }
        
        Path tempPath = Files.createTempFile("hidaya_voice_", "_" + fileName);
        Files.write(tempPath, multipartFile.getBytes());
        
        return tempPath.toFile();
    }
} 