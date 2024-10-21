DiscordTransBot 설정 가이드

DiscordTransBot을 정상적으로 실행하기 위해서는 .env 파일에 디스코드 봇 토큰을 입력해야 합니다. 아래의 지침을 따라 설정해주세요.

1. .env 파일 생성
   DiscordTransBot 프로젝트 폴더 하위에 .env 파일을 생성합니다.
   .env 파일은 봇의 환경 변수들을 관리하기 위한 파일입니다.

   파일 내용에 아래와 같이 디스코드 봇 토큰을 입력합니다:

   예시: .env 파일 내용

   DISCORD_BOT_TOKEN=YOUR_TOKEN

   YOUR_TOKEN을 디스코드 개발자 포털에서 생성한 봇 토큰으로 교체하세요.

2. .env 파일 예시
   DiscordTransBot/
   ├── .env                # 여기에 봇 토큰을 입력
   ├── src/
   │   ├── main/
   │   ├── java/
   │   └── ...

   .env 파일은 프로젝트의 루트 디렉토리에 위치해야 합니다.

3. 주의사항
   절대로 .env 파일을 공개 저장소에 푸시하지 마세요. .gitignore 파일을 사용해 .env 파일을 추적 대상에서 제외해야 합니다.
   .env 파일이 저장소에 푸시되면 봇의 토큰이 노출되어 악용될 수 있습니다.

   예시로 .gitignore에 .env 추가하기:

   # Ignore .env files
   .env

4. 봇 실행
   이제 .env 파일에 토큰을 입력했으므로, 봇을 정상적으로 실행할 수 있습니다!
