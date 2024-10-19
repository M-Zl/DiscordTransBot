package com.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;
import java.util.EnumSet;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;


public class Main extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
        // .env에서 환경 변수를 불러온다.(DISCORD_BOT_TOKEN)
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_BOT_TOKEN");

        // 토큰이 없으면 경고 출력
        if (token == null || token.isEmpty()) {
            System.out.println("DISCORD_BOT_TOKEN 환경 변수가 설정되지 않았습니다.");
            return;
        }

        // GatewayIntent.MESSAGE_CONTENT 추가
        JDA jda = JDABuilder.createDefault(token,
                        EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new Main())
                .build();
        jda.awaitReady(); // 봇이 준비될 때까지 대기
        System.out.println("봇이 준비되었습니다!");
    }

    // 메시지 이벤트 리스너
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();

        System.out.println("Message received:: " + content);

        // 봇이 보낸 메시지는 무시
        if (event.getAuthor().isBot()) {
            return;
        }

        // "!hello" 명령어에 대한 반응
        if (content.equalsIgnoreCase("!hello")) {
            event.getChannel().sendMessage("Hello!").queue();
        }

        // "!test" 명령어에 대한 반응
        if (content.equalsIgnoreCase("!test")) {
            event.getChannel().sendMessage("Hello, Three_Dev Server!").queue();
        }
    }

    // 서버에 들어오면 환영인사
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        // 멤버가 속한 서버의 일반 텍스트 채널로 환영 메시지 보내기
        String welcomeMessage = "🎉 환영합니다, " + event.getMember().getAsMention() + "님!! 🎉\n" +
                "Three_Dev 서버에 오신 것을 환영합니다! 😊\n" +
                "서버의 다양한 이벤트와 기능을 확인하세요(없습니다)!! \n" +
                "무엇이든 궁금한 게 있다면 언제든지 **관리자(스리)**에게 문의해 주세요! 💬";

        Dotenv dotenv = Dotenv.load();
        String channelId = dotenv.get("CHANNEL_ID");

        // 기본 채널이나 첫 번째 텍스트 채널로 메시지 전송
        event.getGuild().getTextChannelById(channelId).sendMessage(welcomeMessage).queue();
    }
}
