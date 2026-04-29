package com.ruoyi.web.controller.mini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.utils.oss.OssUtils;

/**
 * 小程序端通用接口
 */
@RestController
@RequestMapping("/mini")
public class MiniCommonController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(MiniCommonController.class);

    @Autowired
    private OssUtils ossUtils;

    /**
     * 上传文件到OSS（用于健康证等）
     *
     * @param file 上传的文件
     * @param folder 文件夹名称（可选，默认为temp）
     * @return 上传结果，包含文件URL
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file,
                                @RequestParam(value = "folder", defaultValue = "temp") String folder)
    {
        try
        {
            // 验证文件大小（10MB以内）
            long maxSize = 10 * 1024 * 1024;
            if (file.getSize() > maxSize)
            {
                return AjaxResult.error("文件大小不能超过10MB");
            }

            // 验证文件类型（仅允许图片）
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/"))
            {
                return AjaxResult.error("仅支持上传图片文件");
            }

            // 上传到OSS
            String url = ossUtils.uploadFile(file, folder);

            AjaxResult ajax = AjaxResult.success("上传成功");
            ajax.put("url", url);
            ajax.put("fileName", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            log.error("文件上传失败", e);
            return AjaxResult.error("文件上传失败：" + e.getMessage());
        }
    }
}

