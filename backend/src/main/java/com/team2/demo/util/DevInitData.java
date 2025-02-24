package com.team2.demo.util;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Profile("dev")
@Configuration
public class DevInitData {
    @Bean
    public ApplicationRunner devApplicationRunner() {
        return args -> {
            genApiJsonFile("http://localhost:8080/v3/api-docs/version1", "apiV1.json");
            runCmd(
                    List.of(
                            "cmd.exe",
                            "/c",
                            "npx --package typescript --package openapi-typescript --package punycode openapi-typescript apiV1.json -o ../frontend/src/lib/backend/apiV1/schema.d.ts")
            );
        };
    }

    public void genApiJsonFile(String url, String filename) {
        Path filePath = Path.of(filename); // 저장할 파일명
        // HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();
        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 응답 상태 코드 확인
            if (response.statusCode() == 200) {
                // JSON 데이터를 파일로 저장
                Files.writeString(filePath, response.body(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("JSON 데이터가 " + filePath.toAbsolutePath() + "에 저장되었습니다.");
            } else {
                System.err.println("오류: HTTP 상태 코드 " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runCmd(List<String> command) {
        // 실행할 터미널 명령어 (예: ls -l 또는 dir)
//        List<String> command = List.of("ls", "-l"); // macOS/Linux
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // 표준 에러를 표준 출력과 합침
            Process process = processBuilder.start();
            // 명령어 실행 결과 읽기
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            // 프로세스 종료 코드 확인
            int exitCode = process.waitFor();
            System.out.println("프로세스 종료 코드: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}