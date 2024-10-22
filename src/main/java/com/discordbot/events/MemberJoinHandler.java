package com.discordbot.events;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class MemberJoinHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            // 서버 이름 가져오기
            String guildName = event.getGuild().getName();

            // 환영 메시지 생성
            String welcomeMessage = "🎉 환영합니다, " + event.getMember().getAsMention() + "님!! 🎉\n" +
                    guildName + " 서버에 오신 것을 환영합니다! 😊\n" +
                    "서버의 다양한 이벤트와 기능은 없습니다!! \n" +
                    "무엇이든 궁금한 게 있다면 언제든지 **관리자**에게 문의해 주세요! 💬";

            // 시스템 채널 가져오기 (서버 설정에서 시스템 메시지가 표시되는 채널)
            TextChannel systemChannel = event.getGuild().getSystemChannel();

            if (systemChannel != null) {
                // 시스템 채널이 존재하면 환영 메시지 전송
                systemChannel.sendMessage(welcomeMessage).queue();
            } else {
                // 시스템 채널이 없을 경우 첫 번째 텍스트 채널로 환영 메시지 전송
                List<TextChannel> textChannels = event.getGuild().getTextChannels();

                if (!textChannels.isEmpty()) {
                    textChannels.get(0).sendMessage(welcomeMessage).queue();
                } else {
                    System.out.println("이 서버에는 텍스트 채널이 없습니다.");
                }
            }
        } catch (Exception e) {
            System.err.println("멤버 입장 처리 중 오류 발생 ::: " + e.getMessage());
            e.printStackTrace();
        }
    }
}