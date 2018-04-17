package com.example.fileloader.dao;

import com.example.fileloader.pojo.FileMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetaRepository extends JpaRepository<FileMeta, Integer> {
}
