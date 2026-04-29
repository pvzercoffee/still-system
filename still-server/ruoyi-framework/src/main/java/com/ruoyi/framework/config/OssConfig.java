package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ruoyi.framework.config.properties.OssProperties;

/**
 * 阿里云OSS配置
 *
 * @author ruoyi
 */
@Configuration
public class OssConfig
{
    @Autowired
    private OssProperties ossProperties;

    @Bean
    public OSS ossClient()
    {
        // 创建OSSClient实例
        OSS oss = new OSSClientBuilder()
                .build(ossProperties.getEndpoint(), 
                       ossProperties.getAccessKeyId(), 
                       ossProperties.getAccessKeySecret());

        return oss;
    }
}

