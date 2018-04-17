package com.example.fileloader.service;

import com.example.fileloader.pojo.FileMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    public FileMeta save(MultipartFile file) throws IOException;
    public FileMeta getMetaDataById(int id) throws FileNotFoundException;
    public File getFile(int id) throws FileNotFoundException;
}
