// src/main/java/com/example/HidayaVoiceAI/Service/FingerPrintExtractor.java
package com.example.HidayaVoiceAI.Service;

import com.example.HidayaVoiceAI.Exceptions.FingerPrintException;
import java.io.File;

public interface FingerPrintExtractor {
    /**
     * Analyze the given audio file and return its fingerprint.
     * @param audioFile a File pointing to the uploaded audio
     * @return a fingerprint object or byte[] that uniquely represents the clip
     * @throws FingerPrintException if extraction fails
     */
    byte[] extractFingerprint(File audioFile) throws FingerPrintException;
}
