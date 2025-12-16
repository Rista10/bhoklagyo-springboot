package com.backend.bhoklagyo.controller;
import com.backend.bhoklagyo.service.BackblazeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final BackblazeService backblazeService;

    @GetMapping("/signed-url")
    public Map<String, String> getSignedUrl(@RequestParam String fileName) {

        return backblazeService.generateUploadUrl(fileName);

    }

    @GetMapping("/view-url")
    public Map<String, String> getViewUrl(@RequestParam String key) {

        String signedUrl = backblazeService.generateSignedGetUrl(key);
        return Map.of("viewUrl", signedUrl);
    }

    @DeleteMapping
    public Map<String, String> deleteFile(@RequestParam String key) {

        backblazeService.deleteFile(key);

        return Map.of("message", "File deleted successfully");
    }


}
