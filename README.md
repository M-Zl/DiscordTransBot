# DiscordTransBot 설정 가이드

DiscordTransBot을 정상적으로 실행하기 위해서는 **`.env`** 파일에 **디스코드 봇 토큰**을 입력해야 합니다. 아래의 지침을 따라 설정해주세요.

## 📄 1. `.env` 파일 생성

1. **DiscordTransBot 프로젝트 폴더** 하위에 **`.env`** 파일을 생성합니다.
   - `.env` 파일은 봇의 환경 변수들을 관리하기 위한 파일입니다.
   
2. 파일 내용에 아래와 같이 **디스코드 봇 토큰**을 입력합니다.

### 📝 예시: `.env` 파일 내용

```bash
DISCORD_BOT_TOKEN=YOUR_TOKEN
```
- YOUR_TOKEN을 [디스코드 개발자 포털](https://discord.com/developers/docs/intro)에서 생성한 봇 토큰으로 복사하여 붙여넣기하세요

## 📌 2. .env 파일 예시
```bash
DiscordTransBot/
├── .env                # 여기에 봇 토큰을 입력
├── src/
│   ├── main/
│   ├── java/
│   └── ...
```
- **`.env`** 파일은 프로젝트의 루트 디렉토리에 위치해야 합니다.

## 🚀 3. 봇 실행
이제 .env 파일에 토큰을 입력했으므로, 봇을 정상적으로 실행할 수 있습니다!
