package com.discordbot.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class TranslationService {
    public String translate(String text, String sourceLang, String targetLang) {
        try {
            Dotenv dotenv = Dotenv.load();
            String clientId = dotenv.get("PAPAGO_CLIENT_ID");
            String clientSecret = dotenv.get("PAPAGO_CLIENT_SECRET");

            URL url = new URL("https://openapi.naver.com/v1/papago/n2mt");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postParams.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONObject("message").getJSONObject("result").getString("translatedText");

        } catch (Exception e) {
            e.printStackTrace();
            return "번역 중 오류가 발생했습니다.";
        }
    }
}