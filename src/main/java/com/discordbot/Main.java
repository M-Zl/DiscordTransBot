package com.discordbot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        // .env 파일에서 환경 변수를 불러옴
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_BOT_TOKEN");

        if (token == null || token.isEmpty()) {
            System.out.println("DISCORD_BOT_TOKEN 환경 변수가 설정되지 않았습니다.");
            return;
        }

        JDABuilder.createDefault(token)
                .addEventListeners(new Main())  // 이벤트 리스너 추가
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // test
        if (content.equals("!hello")) {
            event.getChannel().sendMessage("안녕하세요! 만나서 반가워요!").queue();  // 명령어에 반응하여 메시지 전송
        }
    }
}