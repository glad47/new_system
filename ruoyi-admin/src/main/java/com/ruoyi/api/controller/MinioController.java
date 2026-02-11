package com.ruoyi.web.controller.common;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.api.util.MinioUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.domain.AjaxResult;


/**
 * MinIO File Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/common/minio")
public class MinioController
{
    private static final Logger log = LoggerFactory.getLogger(MinioController.class);

    /**
     * Upload single file
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder)
    {
        try
        {
            if (file.isEmpty())
            {
                return AjaxResult.error("File is empty");
            }

            String url = MinioUtils.upload(folder, file);
            
            AjaxResult ajax = AjaxResult.success("Upload successful");
            ajax.put("url", url);
            ajax.put("fileName", MinioUtils.extractFileNameFromUrl(url));
            ajax.put("originalName", file.getOriginalFilename());
            ajax.put("fileSize", file.getSize());
            ajax.put("fileType", file.getContentType());
            return ajax;
        }
        catch (Exception e)
        {
            log.error("File upload failed", e);
            return AjaxResult.error("Upload failed: " + e.getMessage());
        }
    }

    /**
     * Upload multiple files
     */
    @PostMapping("/uploadMultiple")
    public AjaxResult uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "folder", required = false) String folder)
    {
        try
        {
            if (files == null || files.length == 0)
            {
                return AjaxResult.error("No files provided");
            }

            List<Map<String, Object>> results = new ArrayList<>();
            for (MultipartFile file : files)
            {
                if (!file.isEmpty())
                {
                    String url = MinioUtils.upload(folder, file);
                    
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("url", url);
                    fileInfo.put("fileName", MinioUtils.extractFileNameFromUrl(url));
                    fileInfo.put("originalName", file.getOriginalFilename());
                    fileInfo.put("fileSize", file.getSize());
                    fileInfo.put("fileType", file.getContentType());
                    results.add(fileInfo);
                }
            }

            AjaxResult ajax = AjaxResult.success("Upload successful");
            ajax.put("files", results);
            ajax.put("count", results.size());
            return ajax;
        }
        catch (Exception e)
        {
            log.error("Multiple file upload failed", e);
            return AjaxResult.error("Upload failed: " + e.getMessage());
        }
    }

    /**
     * Download file
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName)
    {
        try
        {
            InputStream inputStream = MinioUtils.download(fileName);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            String displayName = MinioUtils.getOriginalFileName(fileName);
            if (displayName == null)
            {
                displayName = fileName;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", 
                URLEncoder.encode(displayName, StandardCharsets.UTF_8.toString()));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bytes);
        }
        catch (Exception e)
        {
            log.error("File download failed: {}", fileName, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete file by fileName
     */
    @DeleteMapping("/delete")
    public AjaxResult deleteFile(@RequestParam("fileName") String fileName)
    {
        try
        {
            MinioUtils.delete(fileName);
            return AjaxResult.success("File deleted successfully");
        }
        catch (Exception e)
        {
            log.error("File delete failed: {}", fileName, e);
            return AjaxResult.error("Delete failed: " + e.getMessage());
        }
    }

    /**
     * Delete file by URL
     */
    @DeleteMapping("/deleteByUrl")
    public AjaxResult deleteFileByUrl(@RequestParam("url") String url)
    {
        try
        {
            MinioUtils.deleteByUrl(url);
            return AjaxResult.success("File deleted successfully");
        }
        catch (Exception e)
        {
            log.error("File delete failed: {}", url, e);
            return AjaxResult.error("Delete failed: " + e.getMessage());
        }
    }

    /**
     * Get presigned URL
     */
    @GetMapping("/presignedUrl")
    public AjaxResult getPresignedUrl(
            @RequestParam("fileName") String fileName,
            @RequestParam(value = "expiry", defaultValue = "3600") int expiry)
    {
        try
        {
            String url = MinioUtils.getPresignedUrl(fileName, expiry);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("expiry", expiry);
            return ajax;
        }
        catch (Exception e)
        {
            log.error("Get presigned URL failed: {}", fileName, e);
            return AjaxResult.error("Failed to get URL: " + e.getMessage());
        }
    }
}
