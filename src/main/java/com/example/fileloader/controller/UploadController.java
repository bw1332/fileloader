package com.example.fileloader.controller;

import com.example.fileloader.pojo.ExceptionResponse;
import com.example.fileloader.pojo.FileMeta;
import com.example.fileloader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;


@RestController
public class UploadController {
    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/file")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        try {
            FileMeta fileMeta = fileService.save(file);
            return new ResponseEntity<FileMeta>(fileMeta, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(new Date(), HttpStatus.SERVICE_UNAVAILABLE.value(), "ERROR", "SERVICE_UNAVAILABLE")
                    , HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping(value = "/file/{id}/meta")
    public ResponseEntity getMeta(@PathVariable int id) throws FileNotFoundException {
        return new ResponseEntity<FileMeta>(fileService.getMetaDataById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/file/{id}")
    public void download(@PathVariable int id, HttpServletResponse response) throws FileNotFoundException {
        File file = fileService.getFile(id);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        try {
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity fileNotFoundExceptionHandler(Exception e, WebRequest request){
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getDescription(false))
                , HttpStatus.NOT_FOUND);
    }
}
