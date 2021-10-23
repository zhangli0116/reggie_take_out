package com.example.controller;

import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName CommonController
 * @Description
 * @Author zhang
 * @Date 2021/10/16 15:24
 * @Version 1.0
 **/
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String path;

    @RequestMapping("/upload")
    public R<String> upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        //file 是一个临时文件，需要持久化到本地
        String originalFilename = multipartFile.getOriginalFilename();
        //创建一个目录对象
        File file = new File(path);
        //如果目录不存在进行创建
        if(!file.exists()){
            file.mkdirs();
        }
        //生成文件名
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.indexOf(".")) ;
        //将文件保存到服务器
        multipartFile.transferTo(new File(path + fileName));
        log.info("文件已保存到服务器，文件名为 {} ,保存的位置为 {}",fileName ,path);
        return R.success(fileName);
    }

    /**
     * 从服务器下载数据
     */
    @RequestMapping("/download")
    public void download(@RequestParam("name") String filename, HttpServletResponse response) throws Exception{
        //获得输入流对象
        FileInputStream fileInputStream = new FileInputStream(path + filename);
        //获得输出流对象
        ServletOutputStream outputStream = response.getOutputStream();
        //获得字节数组
        byte[] bytes = FileCopyUtils.copyToByteArray(fileInputStream);
        //设置响应数据的类型 或 image/jpeg
        response.setContentType("application/octet-stream");
        //输出
        outputStream.write(bytes,0,bytes.length);

    }


}
