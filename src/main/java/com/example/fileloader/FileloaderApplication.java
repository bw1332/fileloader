package com.example.fileloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Contended;

@SpringBootApplication
public class FileloaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileloaderApplication.class, args);
    }
}
