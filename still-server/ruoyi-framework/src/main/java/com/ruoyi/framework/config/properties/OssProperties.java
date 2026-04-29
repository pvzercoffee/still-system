package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties
{
    /** OSS端点 */
    private String endpoint;

    /** AccessKeyId */
    private String accessKeyId;

    /** AccessKeySecret */
    private String accessKeySecret;

    /** Bucket名称 */
    private String bucketName;

    /** URL过期时间（秒） */
    private long urlExpireSeconds = 3600;

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId()
    {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId)
    {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret()
    {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret)
    {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName()
    {
        return bucketName;
    }

    public void setBucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }

    public long getUrlExpireSeconds()
    {
        return urlExpireSeconds;
    }

    public void setUrlExpireSeconds(long urlExpireSeconds)
    {
        this.urlExpireSeconds = urlExpireSeconds;
    }
}

