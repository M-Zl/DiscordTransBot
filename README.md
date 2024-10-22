# DiscordTransBot 설정 가이드

DiscordTransBot을 정상적으로 실행하기 위해서는 **`.env`** 파일에 **디스코드 봇 토큰**을 입력해야 한다. 아래 내용을 확인하고 설정하기.

<br>

## ⚠️ 주의사항
- 절대로 **`.env`** 파일을 공개 저장소에 푸시하지 않기!!

## .gitignore에 .env 추가하기:
```bash
# Ignore .env files
.env
```

<br><br>  

## 📄 1. `.env` 파일 생성

1. **DiscordTransBot 프로젝트 폴더** 하위에 **`.env`** 파일을 생성
   - `.env` 파일은 봇의 환경 변수들을 관리하기 위한 파일
   
2. 파일 내용에 아래와 같이 **디스코드 봇 토큰**을 입력하기


### 📝 예시: `.env` 파일 내용

```bash
DISCORD_BOT_TOKEN=YOUR_DISCORD_BOT_TOKEN
```
- `YOUR_DISCORD_BOT_TOKEN`을 [디스코드 개발자 포털](https://discord.com/developers/docs/intro)에서 생성한 봇 토큰으로 복사하여 붙여넣기

<br>

## 📌 2. .env 파일 예시
```bash
DiscordTransBot/
├── .env                # 여기에 봇 토큰을 입력
├── src/
│   ├── main/
│   ├── java/
│   └── ...
```
- **`.env`** 파일은 프로젝트의 루트 디렉토리에 위치해야 함

<br>

## 🚀 3. 봇 실행
이제 **`.env`** 파일에 토큰을 입력했으므로, 봇을 정상적으로 실행할 수 있다!
