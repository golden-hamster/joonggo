package com.capstone.joonggo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;


}
