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

            // "Test"
            if (content.equalsIgnoreCase("!test")) {
                event.getChannel().sendMessage("Hello, I'm Three_Dev Bot! 🤖🖐️").queue();
            }

            // "Hello"
            if (content.equalsIgnoreCase("!hello")) {
                event.getChannel().sendMessage("Hello!!☺️").queue();
            }

            // "화이팅"
            if (content.equalsIgnoreCase("!화이팅")) {
                event.getChannel().sendMessage("가보자고!!👊").queue();
            }

            // "!translate [언어] [문장]" 명령어 처리
            if (content.startsWith("!t")) {
                String[] parts = content.split(" ", 3);
                if (parts.length < 3) {
                    event.getChannel().sendMessage("사용법: !t [언어코드] [문장]").queue();
                    return;
                }

                String targetLanguage = parts[1];
                String textToTranslate = parts[2];

                // 번역 요청
                String translatedText = translationService.translateText(textToTranslate, targetLanguage);
                event.getChannel().sendMessage("번역 결과: " + translatedText).queue();
            }
        } catch (Exception e) {
            System.err.println("유저 메세지를 읽는 중 오류 발생 ::: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
