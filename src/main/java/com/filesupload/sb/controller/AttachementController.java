package com.filesupload.sb.controller;


import com.filesupload.sb.entity.Attachement;
import com.filesupload.sb.model.ResponseData;
import com.filesupload.sb.repository.AttatchementRepository;
import com.filesupload.sb.service.AttachementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachementController {
    AttachementService attachementService;

    @Autowired
    public AttachementController(AttachementService attachementService) {
        this.attachementService = attachementService;
    }
    // method for uploading file to the server - spring boot
    @PostMapping("/upload")
    public ResponseData uplaod(@RequestParam("file")MultipartFile file) throws Exception {
        Attachement attachement = null;
        attachement = attachementService.safeAttachement(file);

        // create download url

        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(String.valueOf(attachement.getId()))
                .toUriString();

        return new ResponseData(
                attachement.getFileName(),
                file.getContentType(),
                file.getSize(),
                downloadURL
        );
    }

    // method for downloading file from th server - spring boot

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) throws Exception {
        Attachement attachement = null;
        attachement = attachementService.getAttachement(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachement.getFileType()) )
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachement; file name \""+ attachement.getFileName() + "\"")
                .body(new ByteArrayResource(attachement.getData()));
    }

}
