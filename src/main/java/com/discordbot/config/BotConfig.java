package com.discordbot.config;

import io.github.cdimascio.dotenv.Dotenv;
public class BotConfig {
    /*
        ** 환경 변수를 불러오거나, 설정 값을 저장하는 클래스
    */

    // .env 파일에서 환경 변수를 불러오는 Dotenv 객체
    private static final Dotenv dotenv = Dotenv.load();

    // 디스코드 봇 토큰을 가져오는 메서드
    public static String getToken() {
        return dotenv.get("DISCORD_BOT_TOKEN");
    }

    // 추가 설정 예시 (필요 시..)
    public static String getPrefix() {
        // 봇 명령어 앞에 붙일 접두사 (예: !, ? 등)
        return dotenv.get("BOT_PREFIX", "!");  // 없으면 기본값으로 "!" 사용
    }

    // 다른 설정 값도 여기서 관리할 수 있음
//    public static String getChannelId() {
//        return dotenv.get("evn변수");
//    }
}
