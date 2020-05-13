package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.13 19:11
 * @Description:
 */
public class FileUtils {
    /**
     * 图片上传 返回访问地址
     * @return imgUrl
     */
    public static String uploadFileReturnUrl(HttpServletRequest request, String filePath, MultipartFile file , String prefixUrl) throws IOException {

        // 获取完整的文件名
        String trueFileName = file.getOriginalFilename();
        // 获取文件后缀名
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
//            // 生成新文件的名字
        String fileName = UUID.randomUUID() + suffix;
//            // 获取项目地址
        String itemPath = getItemPath(request);

        // 判断当前要上传的路径是否存在
        String targetParentPath = filePath + prefixUrl;
        File targetParentFile = new File(targetParentPath);

        if (!targetParentFile.exists()) {
            targetParentFile.mkdirs();
        }


        File targetFile = new File(targetParentPath, fileName);
        file.transferTo(targetFile);

        return itemPath + prefixUrl +fileName;
    }
    public static String getItemPath(HttpServletRequest request){

        String scheme = request.getScheme(); // 获取链接协议，http
        String serverName = request.getServerName(); // 获取服务器名称 localhost
        int serverPort = request.getServerPort(); // 获取端口 8080
        String path = scheme+"://"+serverName+":"+serverPort;
        return path;
    }
}
