package com.example.HidayaVoiceAI.Exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ErrorResponse {

    private String message;
}
