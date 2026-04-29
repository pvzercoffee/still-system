package com.ruoyi.web.controller.mini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.config.properties.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.InputStream;
import java.util.UUID;

/**
 * 小程序端文件上传接口
 * 用于上传健康证、营业执照等文件到阿里云 OSS
 */
@RestController
@RequestMapping("/mini/file")
public class MiniFileController extends BaseController
{
    @Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    /**
     * 文件上传到阿里云 OSS
     * 
     * @param file 上传的文件
     * @return 返回文件的公开访问 URL
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file)
    {
        try
        {
            if (file == null || file.isEmpty())
            {
                return AjaxResult.error("文件不能为空");
            }

            // 检查文件大小（限制为 10MB）
            long maxSize = 10 * 1024 * 1024;
            if (file.getSize() > maxSize)
            {
                return AjaxResult.error("文件大小不能超过 10MB");
            }

            // 获取文件原始名称和扩展名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty())
            {
                return AjaxResult.error("无法获取文件名称");
            }

            // 检查文件类型（仅允许图片）
            String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
            String lowerCaseName = originalFilename.toLowerCase();
            boolean isAllowed = false;
            for (String ext : allowedExtensions)
            {
                if (lowerCaseName.endsWith(ext))
                {
                    isAllowed = true;
                    break;
                }
            }
            if (!isAllowed)
            {
                return AjaxResult.error("仅支持上传 jpg、jpeg、png、gif、webp 格式的图片");
            }

            // 生成唯一的文件名（使用 UUID 避免文件名冲突）
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = "mini/cert/" + UUID.randomUUID().toString() + fileExtension;

            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 上传到 OSS
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossProperties.getBucketName(),
                    uniqueFilename,
                    inputStream
            );
            ossClient.putObject(putObjectRequest);

            // 生成公开访问 URL
            String fileUrl = String.format("https://%s.%s/%s",
                    ossProperties.getBucketName(),
                    ossProperties.getEndpoint(),
                    uniqueFilename
            );

            return AjaxResult.success()
                    .put("fileName", originalFilename)
                    .put("fileUrl", fileUrl)
                    .put("message", "文件上传成功");
        }
        catch (Exception e)
        {
            return AjaxResult.error("文件上传失败: " + e.getMessage());
        }
    }
}

