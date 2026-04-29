package com.ruoyi.framework.utils.oss;

import java.io.InputStream;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.config.properties.OssProperties;

/**
 * 阿里云OSS工具类
 * 
 * @author ruoyi
 */
@Component
public class OssUtils
{
    @Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    /**
     * 上传文件到OSS
     * 
     * @param file 上传的文件
     * @param folder 文件夹路径（例如：vendors/health-cert）
     * @return 上传成功后的文件访问URL
     */
    public String uploadFile(MultipartFile file, String folder) throws Exception
    {
        if (file.isEmpty())
        {
            throw new ServiceException("上传文件不能为空");
        }

        try
        {
            // 生成唯一的文件名
            String originalFileName = file.getOriginalFilename();
            String suffix = getSuffix(originalFileName);
            String fileName = folder + "/" + UUID.randomUUID() + suffix;

            // 获取文件流
            InputStream inputStream = file.getInputStream();

            // 上传文件到OSS
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                ossProperties.getBucketName(),
                fileName,
                inputStream
            );
            
            ossClient.putObject(putObjectRequest);

            // 返回文件访问URL
            return generateUrl(fileName);
        }
        catch (Exception e)
        {
            throw new ServiceException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 生成文件访问URL
     * 
     * @param fileName 文件名（包含路径）
     * @return 文件URL
     */
    public String generateUrl(String fileName)
    {
        // 使用公开读的URL访问
        String url = String.format("https://%s.%s/%s",
            ossProperties.getBucketName(),
            ossProperties.getEndpoint(),
            fileName
        );
        return url;
    }

    /**
     * 获取文件后缀
     * 
     * @param fileName 文件名
     * @return 后缀名（包含点）
     */
    private String getSuffix(String fileName)
    {
        if (fileName == null || fileName.isEmpty())
        {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1)
        {
            return "";
        }
        return fileName.substring(index);
    }
}

