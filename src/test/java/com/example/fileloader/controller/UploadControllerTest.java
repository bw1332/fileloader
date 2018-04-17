package com.example.fileloader.controller;

import com.example.fileloader.exception.FileNotFoundException;
import com.example.fileloader.pojo.FileMeta;
import com.example.fileloader.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UploadController.class)
public class UploadControllerTest {

    @Value("${spring.servlet.multipart.location}")
    private String directory;

    @Mock
    private UploadController uploadController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

    private FileMeta fileMeta;

    private MockMultipartFile file;

    @Before
    public void setup(){
        fileMeta = new FileMeta();
        fileMeta.setFileId(1);
        fileMeta.setSize(1);
        fileMeta.setDirectory(directory);
        fileMeta.setFileName("test");
        file = new MockMultipartFile("file", "test", null, "mock test".getBytes());
    }

    @Test
    public void uploadSaved() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Mockito.when(fileService.save(file)).thenReturn(fileMeta);
        mvc.perform(multipart("/file").file(file))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(fileMeta)));
    }

    @Test
    public void uploadNotSaved() throws Exception {
        Mockito.when(fileService.save(file)).thenThrow(IOException.class);
        mvc.perform(multipart("/file").file(file))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void getMetaFound() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Mockito.when(fileService.getMetaDataById(1)).thenReturn(fileMeta);
        mvc.perform(get("/file/1/meta"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(fileMeta)));
    }

    @Test
    public void getMetaNotFound() throws Exception {
        Mockito.when(fileService.getMetaDataById(1)).thenThrow(FileNotFoundException.class);
        mvc.perform(get("/file/1/meta"))
                .andExpect(status().isNotFound());
    }
}