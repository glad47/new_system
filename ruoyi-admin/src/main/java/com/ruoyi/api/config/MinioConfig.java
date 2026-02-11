package com.ruoyi.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MinIO Configuration
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig
{
    /** MinIO endpoint URL */
    private static String endpoint;

    /** Access key (username) */
    private static String accessKey;

    /** Secret key (password) */
    private static String secretKey;

    /** Default bucket name */
    private static String bucketName;

    public static String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        MinioConfig.endpoint = endpoint;
    }

    public static String getAccessKey()
    {
        return accessKey;
    }

    public void setAccessKey(String accessKey)
    {
        MinioConfig.accessKey = accessKey;
    }

    public static String getSecretKey()
    {
        return secretKey;
    }

    public void setSecretKey(String secretKey)
    {
        MinioConfig.secretKey = secretKey;
    }

    public static String getBucketName()
    {
        return bucketName;
    }

    public void setBucketName(String bucketName)
    {
        MinioConfig.bucketName = bucketName;
    }
}
