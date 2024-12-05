package com.vijay.vz.dreamshop.controller;

import com.vijay.vz.dreamshop.dto.ImageDto;
import com.vijay.vz.dreamshop.response.ApiResponse;
import com.vijay.vz.dreamshop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api/prefix}/images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
       try {
           List<ImageDto> imageDtos = imageService.saveImages(files, productId);
           return ResponseEntity.ok(new ApiResponse("Upload success", imageDtos));
       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed",e.getMessage()));
       }
    }
}
