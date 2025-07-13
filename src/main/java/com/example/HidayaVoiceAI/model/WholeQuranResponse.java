package com.example.HidayaVoiceAI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "whole.quran")
public class WholeQuranResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private DataContent data;

    @Data
    public static class DataContent {
        @SerializedName("surahs")
        private List<Surah> surahs;

        @SerializedName("edition")
        private Edition edition;
    }

    @Data
    public static class Surah {
        @SerializedName("number")
        private int number;

        @SerializedName("name")
        private String name;

        @SerializedName("englishName")
        private String englishName;

        @SerializedName("englishNameTranslation")
        private String englishNameTranslation;

        @SerializedName("revelationType")
        private String revelationType;

        @SerializedName("ayahs")
        private List<Ayah> ayahs;
    }

    @Data
    public static class Ayah {
        @SerializedName("number")
        private int number;

        @SerializedName("text")
        private String text;

        @SerializedName("numberInSurah")
        private int numberInSurah;

        @SerializedName("juz")
        private int juz;

        @SerializedName("manzil")
        private int manzil;

        @SerializedName("page")
        private int page;

        @SerializedName("ruku")
        private int ruku;

        @SerializedName("hizbQuarter")
        private int hizbQuarter;

        @SerializedName("sajda")
        private Object sajda;
    }

    @Data
    public static class Edition {
        @SerializedName("identifier")
        private String identifier;

        @SerializedName("language")
        private String language;

        @SerializedName("name")
        private String name;

        @SerializedName("englishName")
        private String englishName;

        @SerializedName("format")
        private String format;

        @SerializedName("type")
        private String type;
    }

}