package com.example.service;

import com.example.data.response.FileUploadResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fuguangwei
 * @date 2023-01-29
 */
@Slf4j
@Service
public class FileServiceImpl {

    //拦截的url，虚拟路径
    public String pathPattern = "files";

    //自己设置的目录
    private static final String fileDir = "fileStorage";

    //上传文件存放目录  =  工作目录绝对路径 + 自己设置的目录，也可以直接自己指定服务器目录

    //System.getProperty("user.dir")   返回当前工作目录的路径
    //.File.separator                  分隔符通常是斜杠（/）或反斜杠（\）
    //fileDir                          自己设置的目录
    public String filePath = System.getProperty("user.dir") + File.separator + fileDir + File.separator;
    //如: filePath = D:\develop\work\project\myblog\myblog-file-upload\fileStorage

    private static final AtomicInteger SUFFIX = new AtomicInteger(0);

    @Value(value = "${file.upload.suffix:jpg,jpeg,png,bmp,xls,xlsx,pdf}")
    private String fileUploadSuffix;

    public FileUploadResponse upload(MultipartFile file) {
        FileUploadResponse result = new FileUploadResponse();
        if (file.isEmpty()) {
            log.error("the file to be uploaded is empty");
            return result;
        }
        List<String> suffixList = Lists.newArrayList(fileUploadSuffix.split(","));

        try {
            //校验文件后缀
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (!suffixList.contains(suffix)) {
                log.error("unsupported file format");
                return result;
            }

            //首次需生成目录
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 唯一文件名
            String fileName = timeFormat(System.currentTimeMillis())
                    + SUFFIX.getAndIncrement() + "." + suffix;
            String absolutePath = filePath + fileName;  // D:\develop\work\project\myblog\myblog-file-upload\fileStorage\202302021010345680.jpg
            log.info("绝对路径: {}", absolutePath);
            file.transferTo(new File(absolutePath)); // 将上传的文件保存到指定路径的文件中

            String separator = "/";
            String path = separator + pathPattern + separator + fileName;
            // 响应结果 {路径 文件名}
            result.setPath(path);
            result.setFileName(fileName);
        } catch (Exception e) {
            log.error("the file upload error occurred. e ", e);
        }
        return result;
    }

    public static String timeFormat(Long time) {
        if (Objects.isNull(time)) {
            return null;
        }
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(time);
    }

}
