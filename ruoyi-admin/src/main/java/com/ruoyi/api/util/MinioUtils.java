package com.ruoyi.api.util;

import java.io.InputStream;
import java.util.UUID;

import com.ruoyi.api.config.MinioConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

/**
 * MinIO Utility Class
 * Static methods for file upload, download, and delete operations
 * 
 * @author ruoyi
 */
public class MinioUtils
{
    private static final Logger log = LoggerFactory.getLogger(MinioUtils.class);

    /** MinIO client singleton */
    private static MinioClient minioClient;

    /**
     * Get MinIO client instance (lazy initialization)
     */
    private static synchronized MinioClient getMinioClient()
    {
        if (minioClient == null)
        {
            minioClient = MinioClient.builder()
                    .endpoint(MinioConfig.getEndpoint())
                    .credentials(MinioConfig.getAccessKey(), MinioConfig.getSecretKey())
                    .build();
        }
        return minioClient;
    }

    /**
     * Upload file to MinIO with default bucket
     * 
     * @param file MultipartFile to upload
     * @return File URL
     */
    public static String upload(MultipartFile file) throws Exception
    {
        return upload(MinioConfig.getBucketName(), null, file);
    }

    /**
     * Upload file to MinIO with folder
     * 
     * @param folder Folder path (e.g., "templates", "images")
     * @param file MultipartFile to upload
     * @return File URL
     */
    public static String upload(String folder, MultipartFile file) throws Exception
    {
        return upload(MinioConfig.getBucketName(), folder, file);
    }

    /**
     * Upload file to MinIO
     * 
     * @param bucketName Bucket name
     * @param folder Folder path (optional)
     * @param file MultipartFile to upload
     * @return File URL
     */
    public static String upload(String bucketName, String folder, MultipartFile file) throws Exception
    {
        // Ensure bucket exists
        createBucketIfNotExists(bucketName);

        // Generate unique file name
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String fileName = DateUtils.datePath() + "/" + UUID.randomUUID().toString().replace("-", "") + extension;
        
        // Add folder prefix if provided
        if (StringUtils.isNotEmpty(folder))
        {
            fileName = folder + "/" + fileName;
        }

        // Upload file
        try (InputStream inputStream = file.getInputStream())
        {
            getMinioClient().putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
        }

        String url = getFileUrl(bucketName, fileName);
        log.info("File uploaded to MinIO: {}", url);
        return url;
    }

    /**
     * Upload file with custom file name
     * 
     * @param folder Folder path
     * @param customFileName Custom file name (without extension)
     * @param file MultipartFile to upload
     * @return File URL
     */
    public static String uploadWithName(String folder, String customFileName, MultipartFile file) throws Exception
    {
        String bucketName = MinioConfig.getBucketName();
        createBucketIfNotExists(bucketName);

        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = customFileName + extension;
        
        if (StringUtils.isNotEmpty(folder))
        {
            fileName = folder + "/" + fileName;
        }

        try (InputStream inputStream = file.getInputStream())
        {
            getMinioClient().putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
        }

        return getFileUrl(bucketName, fileName);
    }

    /**
     * Upload InputStream to MinIO
     * 
     * @param folder Folder path
     * @param fileName File name with extension
     * @param inputStream Input stream
     * @param size File size
     * @param contentType Content type
     * @return File URL
     */
    public static String upload(String folder, String fileName, InputStream inputStream, long size, String contentType) throws Exception
    {
        String bucketName = MinioConfig.getBucketName();
        createBucketIfNotExists(bucketName);

        String objectName = fileName;
        if (StringUtils.isNotEmpty(folder))
        {
            objectName = folder + "/" + fileName;
        }

        getMinioClient().putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build()
        );

        return getFileUrl(bucketName, objectName);
    }

    /**
     * Get file URL
     * 
     * @param fileName File name/path in bucket
     * @return Public URL to access the file
     */
    public static String getFileUrl(String fileName)
    {
        return getFileUrl(MinioConfig.getBucketName(), fileName);
    }

    /**
     * Get file URL
     * 
     * @param bucketName Bucket name
     * @param fileName File name/path in bucket
     * @return Public URL to access the file
     */
    public static String getFileUrl(String bucketName, String fileName)
    {
        return MinioConfig.getEndpoint() + "/" + bucketName + "/" + fileName;
    }

    /**
     * Get presigned URL (temporary access URL with expiry)
     * 
     * @param fileName File name/path in bucket
     * @param expiry Expiry time in seconds
     * @return Presigned URL
     */
    public static String getPresignedUrl(String fileName, int expiry) throws Exception
    {
        return getMinioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(MinioConfig.getBucketName())
                .object(fileName)
                .expiry(expiry)
                .build()
        );
    }

    /**
     * Download file from MinIO
     * 
     * @param fileName File name/path in bucket
     * @return InputStream of the file
     */
    public static InputStream download(String fileName) throws Exception
    {
        return getMinioClient().getObject(
            GetObjectArgs.builder()
                .bucket(MinioConfig.getBucketName())
                .object(fileName)
                .build()
        );
    }

    /**
     * Download file from MinIO
     * 
     * @param bucketName Bucket name
     * @param fileName File name/path in bucket
     * @return InputStream of the file
     */
    public static InputStream download(String bucketName, String fileName) throws Exception
    {
        return getMinioClient().getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build()
        );
    }

    /**
     * Delete file from MinIO
     * 
     * @param fileName File name/path in bucket
     */
    public static void delete(String fileName) throws Exception
    {
        delete(MinioConfig.getBucketName(), fileName);
    }

    /**
     * Delete file from MinIO
     * 
     * @param bucketName Bucket name
     * @param fileName File name/path in bucket
     */
    public static void delete(String bucketName, String fileName) throws Exception
    {
        getMinioClient().removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build()
        );
        log.info("File deleted from MinIO: {}/{}", bucketName, fileName);
    }

    /**
     * Delete file by URL
     * 
     * @param fileUrl Full URL of the file
     */
    public static void deleteByUrl(String fileUrl) throws Exception
    {
        String fileName = extractFileNameFromUrl(fileUrl);
        if (StringUtils.isNotEmpty(fileName))
        {
            delete(fileName);
        }
    }

    /**
     * Check if file exists
     * 
     * @param fileName File name/path in bucket
     * @return true if exists
     */
    public static boolean exists(String fileName)
    {
        try
        {
            getMinioClient().getObject(
                GetObjectArgs.builder()
                    .bucket(MinioConfig.getBucketName())
                    .object(fileName)
                    .build()
            ).close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Create bucket if it doesn't exist
     */
    public static void createBucketIfNotExists(String bucketName) throws Exception
    {
        boolean exists = getMinioClient().bucketExists(
            BucketExistsArgs.builder().bucket(bucketName).build()
        );
        
        if (!exists)
        {
            getMinioClient().makeBucket(
                MakeBucketArgs.builder().bucket(bucketName).build()
            );
            log.info("MinIO bucket created: {}", bucketName);
        }
    }

    /**
     * Extract file extension from filename
     */
    public static String getFileExtension(String fileName)
    {
        if (StringUtils.isEmpty(fileName) || fileName.lastIndexOf(".") == -1)
        {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Extract file name from URL
     */
    public static String extractFileNameFromUrl(String fileUrl)
    {
        if (StringUtils.isEmpty(fileUrl))
        {
            return null;
        }
        String bucketPath = "/" + MinioConfig.getBucketName() + "/";
        int index = fileUrl.indexOf(bucketPath);
        if (index != -1)
        {
            return fileUrl.substring(index + bucketPath.length());
        }
        return null;
    }

    /**
     * Get original file name from URL (last part after /)
     */
    public static String getOriginalFileName(String fileUrl)
    {
        if (StringUtils.isEmpty(fileUrl))
        {
            return null;
        }
        int lastSlash = fileUrl.lastIndexOf("/");
        if (lastSlash != -1 && lastSlash < fileUrl.length() - 1)
        {
            return fileUrl.substring(lastSlash + 1);
        }
        return null;
    }
}
