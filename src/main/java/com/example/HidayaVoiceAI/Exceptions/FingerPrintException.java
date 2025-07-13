package com.example.HidayaVoiceAI.Exceptions;

public class FingerPrintException extends Exception {
    private static final long serialVersionUID = 1L;

    public FingerPrintException() {
        super();
    }

    public FingerPrintException(String message) {
        super(message);
    }

    public FingerPrintException(String message, Throwable cause) {
        super(message, cause);
    }

    public FingerPrintException(Throwable cause) {
        super(cause);
    }
}
