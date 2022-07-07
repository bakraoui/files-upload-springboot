package com.filesupload.sb.service;


import com.filesupload.sb.entity.Attachement;
import org.springframework.web.multipart.MultipartFile;

public interface AttachementService {
    Attachement safeAttachement(MultipartFile file) throws Exception;

    Attachement getAttachement(String fileId) throws Exception;
}
