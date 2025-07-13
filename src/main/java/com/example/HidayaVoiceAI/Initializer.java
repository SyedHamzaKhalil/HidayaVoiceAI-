package com.example.HidayaVoiceAI;

import com.example.HidayaVoiceAI.Repository.AyahFingerPrintRepository;
import com.example.HidayaVoiceAI.model.AyahFingerPrint;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Initializer {

    @Bean
    public ApplicationRunner sampleDataToDB(AyahFingerPrintRepository repo) {
        return args -> {
            repo.save(new AyahFingerPrint()
                    .setId(String.valueOf(UUID.randomUUID()))
                    .setSurahNumber(1)
                    .setAyahNumber(1)
                    .setSurah("Al-Fatiha")
                    .setTransliteration("Bismillah ar-Rahman ar-Rahim")
                    .setArabicText("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ")
                    .setAudioUrl("/Users/hamzakhalil/Documents/BismillahSample.mp4"));
            System.out.println("Sample record inserted.");
        };
    }
}
