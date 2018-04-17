package com.example.fileloader.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.fileloader.dao.FileMetaRepository;
import com.example.fileloader.pojo.FileMeta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {
    @Value("${spring.servlet.multipart.location}")
    private String directory;

    @Mock
    private FileMetaRepository fileMetaRepository;

    @InjectMocks
    private FileService fileService = new FileServiceImpl();

    private FileMeta fileMeta;

    private MultipartFile file;

    @Before
    public void setup() {
        fileMeta = new FileMeta();
        fileMeta.setFileName("test");
        fileMeta.setDirectory(directory);
        fileMeta.setModifyTime(new Date());
        fileMeta.setSize(10);
        fileMeta.setFileId(1);
        file = new MockMultipartFile("file", "test", null, "mock test".getBytes());
    }

    @Test
    public void getMetaDataByIdValid() throws FileNotFoundException {
        Optional<FileMeta> optionalFileMeta = Optional.of(fileMeta);
        Mockito.when(fileMetaRepository.findById(1)).thenReturn(optionalFileMeta);
        Assert.assertEquals(fileMeta, fileService.getMetaDataById(1));
    }

    @Test(expected = FileNotFoundException.class)
    public void getMetaDataByIdInvalidId() throws FileNotFoundException {
        Optional<FileMeta> optionalFileMeta = Optional.empty();
        Mockito.when(fileMetaRepository.findById(1)).thenReturn(optionalFileMeta);
        fileService.getMetaDataById(1);
    }

    @Test
    public void saveTest() throws IOException {
        Mockito.when(fileMetaRepository.save(fileMeta)).thenReturn(fileMeta);
        Assert.assertEquals(fileMeta, fileService.save(file));
    }
}