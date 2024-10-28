package com.discordbot.events;

import com.discordbot.services.TranslationService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {

    private final TranslationService translationService;

    public MessageHandler(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            Message message = event.getMessage();
            String content = message.getContentRaw();

            // 메시지 관련 정보 출력 (디버깅용)
            System.out.println("Message received :: " + content);
            System.out.println("Author: " + event.getAuthor().getName());
            System.out.println("Channel: " + event.getChannel().getName());

            // 봇이 보내는 메세지는 무시
            if (event.getAuthor().isBot()) return;

            // 간단한 명령어에 대한 반응 처리
            handleSimpleCommands(event, content);

            // "!t [언어] [문장]" 명령어 처리
            if (content.startsWith("!t")) {
                handleTranslateCommand(event, content);
            } else {
                // 언어 자동 감지 및 번역 처리
                String detectedLanguage = translationService.detectLanguage(content);
                handleTranslation(event, detectedLanguage, content);
            }
        } catch (Exception e) {
            System.err.println("유저 메세지를 읽는 중 오류 발생 ::: " + e.getMessage());
            event.getChannel().sendMessage("오류가 발생했습니다. 다시 시도해주세요!").queue();
            e.printStackTrace();
        }
    }

    // 간단한 명령어 처리 메소드
    private void handleSimpleCommands(MessageReceivedEvent event, String content) {
        switch (content.toLowerCase()) {
            case "!test":
                event.getChannel().sendMessage("Hello, I'm Three_Dev Bot! 🤖🖐️").queue();
                break;
            case "!hello":
                event.getChannel().sendMessage("Hello!!☺️").queue();
                break;
            case "!화이팅":
                event.getChannel().sendMessage("가보자고!!👊").queue();
                break;
        }
    }

    // 번역 명령어 처리 메소드
    private void handleTranslateCommand(MessageReceivedEvent event, String content) {
        String[] parts = content.split(" ", 3);
        if (parts.length < 3) {
            event.getChannel().sendMessage("사용법: !t [언어코드] [문장]").queue();
            return;
        }

        String targetLanguage = parts[1];
        String textToTranslate = parts[2];

        try {
            // 번역 요청
            String translatedText = translationService.translateText(textToTranslate, targetLanguage);
            event.getChannel().sendMessage("번역 결과: " + translatedText).queue();
        } catch (Exception e) {
            System.err.println("번역 중 오류 발생 ::: " + e.getMessage());
            event.getChannel().sendMessage("번역 중 오류가 발생했습니다. 올바른 언어 코드를 입력했는지 확인해주세요!").queue();
        }
    }

    // 번역 로직 처리 메소드
    private void handleTranslation(MessageReceivedEvent event, String detectedLanguage, String content) {
        // 감지된 언어 로그 출력
        System.out.println("감지된 언어: " + detectedLanguage);
        System.out.println("번역할 내용: " + content);

        if (detectedLanguage.equals("ko")) {
            // 메시지가 한국어이면 중국어로 번역
            String translatedText = translationService.translateText(content, "zh");
            System.out.println("번역 결과 (CHN): " + translatedText);
            event.getChannel().sendMessage("[CHN] " + translatedText).queue();
        } else if (detectedLanguage.equals("zh")) {
            // 메시지가 중국어이면 한국어로 번역
            String translatedText = translationService.translateText(content, "ko");
            System.out.println("번역 결과 (KOR): " + translatedText);
            event.getChannel().sendMessage("[KOR] " + translatedText).queue();
        } else if (detectedLanguage.equals("zh-CN")) {
            // 메시지가 중국어이면 한국어로 번역
            String translatedText = translationService.translateText(content, "ko");
            System.out.println("번역 결과 (KOR): " + translatedText);
            event.getChannel().sendMessage("[KOR] " + translatedText).queue();
        } else if (detectedLanguage.equals("en")) {
            // 메시지가 영어일 경우, 한국어와 중국어로 번역
            String translatedTextKOR = translationService.translateText(content, "ko");
            String translatedTextCHN = translationService.translateText(content, "zh");

            System.out.println("번역 결과 (KOR): " + translatedTextKOR);
            System.out.println("번역 결과 (CHN): " + translatedTextCHN);

            String response = "[KOR] " + translatedTextKOR + "\n[CHN] " + translatedTextCHN;
            event.getChannel().sendMessage(response).queue();
        } else {
            // 지원하지 않는 언어일 경우
            System.out.println("지원하지 않는 언어입니다.");
            event.getChannel().sendMessage("지원하지 않는 언어입니다.").queue();
        }
    }
}
