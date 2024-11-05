package com.discordbot.events;

import com.discordbot.services.TranslationService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.*;

public class MessageHandler extends ListenerAdapter {

    private final TranslationService translationService;
    private  boolean translationEnabled = false; // 번역 상태 저장 변수
    private  final Set<String> simpleCommands = new HashSet<>();

    public MessageHandler(TranslationService translationService) {
        this.translationService = translationService;
        initializeSimpleCommands();
    }

    private void initializeSimpleCommands() {
        // 간단한 명령어를 여기서 설정
        simpleCommands.add("!test");
        simpleCommands.add("!hello");
        simpleCommands.add("!화이팅");
        simpleCommands.add("!help");
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
            if (isSimpleCommand(content)) {
                handleSimpleCommands(event, content);
                return;  // 간단한 명령어일 경우 번역을 수행하지 않음
            }

            // 번역 기능 제어 명령어 처리
            if (isTranslationControlCommand(content)) {
                handleTranslationControlCommands(event, content);
                return;  // 제어 명령어일 경우 번역을 수행하지 않음
            }

            if (translationEnabled) {
                handleTranslationContent(event, content);
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
            case "!help":
                event.getChannel().sendMessage("📋 **명령어 안내** 📋\n" +
                        "`!help` - 사용 가능한 명령어를 안내합니다.\n" +
                        "`!on` - 번역 기능을 켭니다. \n" +
                        "`!off` - 번역 기능을 끕니다.\n" +
                        "`!hello` - 봇이 자신을 소개하는 인사 메시지를 보냅니다.\n" +
                        "`!화이팅` - 응원 메시지를 보냅니다.\n" +
                        "`!test` - 봇이 응답하는 테스트 메시지를 보냅니다.\n" +
                        "\n**번역기능** - 메시지를 보내면 자동으로 언어를 감지하여 번역합니다: \n" +
                            "\t1️⃣ 한국어 메시지는 중국어로 번역됩니다.\n" +
                            "\t2️⃣ 중국어 메시지는 한국어로 번역됩니다..\n" +
                            "\t3️⃣ 영어 메시지는 한국어와 중국어로 각각 번역되어 표시됩니다.\n\n" +
                        "다양한 명령어로 봇을 즐겨보세요!! 🤖👍").queue();
                break;
        }
    }

    // 간단한 명령어 식별 메소드
    private boolean isSimpleCommand(String content) {
        return simpleCommands.contains(content.toLowerCase());
    }

    // 번역 제어 명령어 식별 메소드
    private boolean isTranslationControlCommand(String content) {
        return content.equalsIgnoreCase("!on") || content.equalsIgnoreCase("!off");
    }

    // 번역 로직 처리 메소드
    private void handleTranslation(MessageReceivedEvent event, String detectedLanguage, String content) {
        // 감지된 언어 로그 출력
        System.out.println("감지된 언어: " + detectedLanguage);
        System.out.println("번역할 내용: " + content);

        // 단어 교체를 위한 전처리
        String preProcessedContent = translationService.preProcessMessage(content, detectedLanguage);

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

    // 번역 기능 제어 명령어 처리 메소드
    private void handleTranslationControlCommands(MessageReceivedEvent event, String content) {
        System.out.println("Received command: " + content.trim().toLowerCase());  // 디버깅용 로그
        switch (content.trim().toLowerCase()) {
            case "!on":
                translationEnabled = true;
                event.getChannel().sendMessage("번역 기능이 활성화되었습니다! 🌐").queue();
                break;
            case "!off":
                translationEnabled = false;
                event.getChannel().sendMessage("번역 기능이 비활성화되었습니다! 🚫").queue();
                break;
        }
    }

    // 번역 처리 메소드
    private void handleTranslationContent(MessageReceivedEvent event, String content) {
        // 기존 번역 로직 호출
        String detectedLanguage = translationService.detectLanguage(content);
        handleTranslation(event, detectedLanguage, content);
    }
}
