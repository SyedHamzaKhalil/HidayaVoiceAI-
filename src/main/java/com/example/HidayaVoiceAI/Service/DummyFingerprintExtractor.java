// src/main/java/com/example/HidayaVoiceAI/Service/DummyFingerprintExtractor.java
package com.example.HidayaVoiceAI.Service;

import com.example.HidayaVoiceAI.Exceptions.FingerPrintException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class DummyFingerprintExtractor implements FingerPrintExtractor {
    @Override
    public byte[] extractFingerprint(File audioFile) throws FingerPrintException {
        try {
            // Read the entire file
            byte[] fileBytes = Files.readAllBytes(audioFile.toPath());
            // Compute a SHA-256 hash as the “fingerprint”
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(fileBytes);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new FingerPrintException("Failed to extract dummy fingerprint", e);
        }
    }
}
