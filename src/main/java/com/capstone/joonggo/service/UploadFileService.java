package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.UploadFile;
import com.capstone.joonggo.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadFileService {

    private UploadFileRepository uploadFileRepository;


    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String storeName) {
        return fileDir + storeName;
    }

    @Transactional
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFiles.add(saveFile(multipartFile));
            }
        }
        return storeFiles;
    }

    @Transactional
    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        //엔티티를 생성하고 저장
        UploadFile uploadFile = UploadFile.createUploadFile(originalFilename, storeFilename);

        return uploadFileRepository.save(uploadFile);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
