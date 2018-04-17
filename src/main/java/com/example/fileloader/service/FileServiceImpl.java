package com.example.fileloader.service;

import com.example.fileloader.dao.FileMetaRepository;
import com.example.fileloader.pojo.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Value("${spring.servlet.multipart.location}")
    private String directory;

    @Autowired
    private FileMetaRepository fileMetaRepository;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public FileMeta save(MultipartFile inputFile) throws IOException {
        //set up entity
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileName(inputFile.getOriginalFilename());
        fileMeta.setSize(inputFile.getSize());
        fileMeta.setModifyTime(new Date());
        fileMeta.setDirectory(directory);
        //store meta data in DB
        fileMeta = fileMetaRepository.save(fileMeta);
        int id = fileMeta.getFileId();
        //store file in file system
        File file = new File(directory  + "/" + id + "-" + inputFile.getOriginalFilename());
        inputFile.transferTo(file);
        return fileMeta;
    }

    @Override
    @Transactional
    public FileMeta getMetaDataById(int id) throws FileNotFoundException {
        Optional<FileMeta> fileMeta = fileMetaRepository.findById(id);
        if (!fileMeta.isPresent()){
            throw new FileNotFoundException("FILE ID " + id + " NOT FOUND");
        }
        return fileMeta.get();
    }

    @Override
    public File getFile(int id) throws FileNotFoundException {
        FileMeta fileMeta = getMetaDataById(id);
        String realPath = fileMeta.getDirectory() + "/" + fileMeta.getFileId() + "-" + fileMeta.getFileName();
        return new File(realPath);
    }
}
