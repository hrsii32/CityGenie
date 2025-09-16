package com.example.CityGenie.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    private final Path uploadDir = Paths.get("uploads");

    public StorageService() {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage folder", e);
        }
    }

    public String store(MultipartFile file) throws IOException {
        String original = file.getOriginalFilename();
        String extension = "";

        if (original != null && original.contains(".")) {
            extension = original.substring(original.lastIndexOf("."));
        }

        String uniqueName = UUID.randomUUID().toString() + extension;
        Path targetLocation = uploadDir.resolve(uniqueName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + uniqueName;
    }

}
