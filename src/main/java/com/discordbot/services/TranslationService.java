package com.discordbot.services;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TranslationService {

    private final Translate translate;
    private final Properties translations;

    public TranslationService() {
        // .env에서 API 키 불러오기
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("GOOGLE_API_KEY");

        // Google Translate 서비스 초기화
        translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();

        // translations.properties 파일 로드
        translations = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("translations.properties")) {
            if (input == null) {
                System.out.println("translations.properties 파일을 찾을 수 없습니다.");
                return;
            }
            translations.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 언어 감지 메소드
    public String detectLanguage(String text) {
        Detection detection = translate.detect(text);
        return detection.getLanguage(); // 언어 코드 반환 (예: "ko", "zh", "en")
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

    // 단어 교체 전처리 메소드
    public String preProcessMessage(String message, String targetLanguage) {
        String processedMessage = message;

        // 메시지와 교체할 단어들을 소문자로 처리하여 대소문자 무시 비교
        String lowerCaseMessage = message.toLowerCase();

        for (String key : translations.stringPropertyNames()) {
            if (key.endsWith("." + targetLanguage)) {
                String word = key.split("\\.")[0];
                String translation = translations.getProperty(key);

                // 대소문자 구분 없이 메시지의 단어를 교체
                processedMessage = processedMessage.replaceAll("(?i)" + word, translation);

                System.out.println("preProcessMessage ::: 단어 교체: " + word + " -> " + translation);
            }
        }

        return processedMessage;
    }

}