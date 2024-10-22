package com.discordbot;

import com.discordbot.config.BotConfig;
import com.discordbot.events.MemberJoinHandler;
import com.discordbot.events.MessageHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
        printLogo();

        // BotConfig 클래스에서 환경 변수(DISCORD_BOT_TOKEN) 불러오기
        String token = BotConfig.getToken();

        // 토큰이 없으면 경고 출력 후 종료
        if (token == null || token.isEmpty()) {
            System.out.println("DISCORD_BOT_TOKEN 환경 변수가 설정되지 않았습니다.");
            return;
        }

        // JDA 빌더로 봇을 초기화하고, 이벤트 핸들러를 추가
        JDA jda = JDABuilder.createDefault(token,
                        EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))  // 메시지 콘텐츠 접근 인텐트 추가
                .addEventListeners(new MemberJoinHandler())  // 신규 유저 입장 이벤트 리스너 등록
                .addEventListeners(new MessageHandler()) // 메시지 이벤트 리스너 추가
                .build();

        jda.awaitReady(); // 봇이 준비될 때까지 대기
        System.out.println("█ █ █ █ █ █ █  봇이 준비되었습니다!!  █ █ █ █ █ █ █");
    }

    // 그냥 해보고 싶었음;;
    private static void printLogo() {
        System.out.println("  ________                      ____        __ ");
        System.out.println(" /_  __/ /_  ________  ___     / __ )____  / /_");
        System.out.println("  / / / __ \\/ ___/ _ \\/ _ \\   / __  / __ \\/ __/");
        System.out.println(" / / / / / / /  /  __/  __/  / /_/ / /_/ / /_  ");
        System.out.println("/_/ /_/ /_/_/   \\___/\\___/  /_____/\\____/\\__/  ");
        System.out.println("                                               ");
        System.out.println();
    }

}
