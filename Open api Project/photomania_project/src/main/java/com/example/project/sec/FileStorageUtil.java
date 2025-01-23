package com.example.project.sec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileStorageUtil {
    private static final String UPLOAD_DIR = "D:/photoMania_uploadFile"; // 저장할 디렉터리 경로

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            log.error("업로드된 파일이 없습니다.");
            return null;
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + File.separator + filename;
        log.info("파일 저장 경로: {}", filePath);

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                log.info("디렉토리 생성됨: {}", UPLOAD_DIR);
            } else {
                log.error("디렉토리 생성 실패: {}", UPLOAD_DIR);
            }
        }

        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
            log.info("파일 저장 성공: {}", filePath);
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage());
            throw new IOException("파일 저장 오류", e); // 예외를 다시 던져서 상위에서 처리
        }

        return filename;
    }
}
