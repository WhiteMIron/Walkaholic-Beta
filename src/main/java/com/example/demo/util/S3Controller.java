package com.example.demo.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class S3Controller {

    private final S3FileUploadService s3Uploader;


    @PostMapping("/upload")
    @ResponseBody
    public String upload(  @RequestParam("data") MultipartFile file,Test test) throws IOException {
        log.info("/upload 도착!@");
        log.info("메세지:{}",test.message);

        return s3Uploader.upload(file, "dev-board");
    }
}