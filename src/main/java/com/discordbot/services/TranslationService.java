package com.discordbot.services;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import io.github.cdimascio.dotenv.Dotenv;

public class TranslationService {

    private final Translate translate;

    public TranslationService() {
        // .env에서 API 키 불러오기
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("GOOGLE_API_KEY");

        // Google Translate 서비스 초기화
        translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
    }

    // 번역 메소드
    public String translateText(String text, String targetLanguage) {
        try {
            Translation translation = translate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );
            return translation.getTranslatedText();
        } catch (Exception e) {
            System.out.println("번역 중 오류 발생: " + e.getMessage());
            return "번역 중 오류가 발생했습니다.";
        }
    }
}