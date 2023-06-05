package com.example.ludikgames.controller;

import com.example.ludikgames.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {
    @GetMapping("/get-images")
    public ResponseEntity<List<String>> getImages() {
        String folderPath = "src/main/resources/static/images/symbols";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<String> imageNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imageNames.add("/images/symbols/" + file.getName());
                }
            }
        } else {
            throw new NotFoundException("Images not found");
        }

        return ResponseEntity.ok().body(imageNames);
    }
}
