package com.example.shopping.file;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("public/attach")
public class AttachController {
    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private AttachService attachService;

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        AttachDto attachDTO = attachService.save(file);
        return ResponseEntity.ok().body(attachDTO);
    }
    @PostMapping("/upload1")
    public ResponseEntity<String> uploadImages(@RequestBody String[] imageBase64Strings) {
        try {
            for (int i = 0; i < imageBase64Strings.length; i++) {
                String base64Image = imageBase64Strings[i];
                saveImage(base64Image, i);
            }
            return ResponseEntity.ok("Images uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
        }
    }

    private void saveImage(String base64Image, int index) throws IOException {
        String filePath = UPLOAD_DIR + "image" + index + ".png"; // Change the file format and name as needed
        File imageFile = new File(filePath);

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        try (FileOutputStream fileOutputStream = new FileOutputStream(imageFile)) {
            fileOutputStream.write(imageBytes);
        }
    }

    @GetMapping(value = "open", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return attachService.open(fileName);
    }

    @GetMapping("download")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) {
        Resource file = attachService.download(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(attachService.pagination(page, size));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }
}