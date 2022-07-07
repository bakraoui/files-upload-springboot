package com.filesupload.sb.service;

import com.filesupload.sb.entity.Attachement;
import com.filesupload.sb.repository.AttatchementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachementServiceImpl implements AttachementService{

    AttatchementRepository attatchementRepository;

    @Autowired
    public AttachementServiceImpl(AttatchementRepository attatchementRepository) {
        this.attatchementRepository = attatchementRepository;
    }

    @Override
    public Attachement safeAttachement(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")){
                throw new Exception("file contain illegal characters sequences.");
            }
            Attachement attachement = new Attachement(fileName, file.getContentType(), file.getBytes() );
            return attachement;
        }catch (Exception e){
            throw new Exception("coud not save the file : " + fileName);
        }
    }

    @Override
    public Attachement getAttachement(String fileId) throws Exception {
        return attatchementRepository
                .findById(Long.valueOf(fileId))
                .orElseThrow(() -> new Exception("file not found with id : " + fileId)  );
    }
}
