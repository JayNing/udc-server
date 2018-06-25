package com.interfaces.file;

import com.thrift.common.body.FileData;
import com.thrift.common.body.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公共服务接口
 * Created by liujinren on 2017/7/10.
 */
public interface BasicService {

    /**
     * 保存文件信息
     * @param fileName
     * @param originalFileName
     * @param fileUrl
     * @param extensionName
     * @return
     */
    Integer saveFileInfo(String fileName, String originalFileName, String fileUrl, String extensionName, long fileSize);

    /**
     * 获取文件信息
     * @param s
     * @return
     */
    FileInfo getInfoByFileName(String s);

    /**
     * 保存上传文件
     * @param file
     * @param appType
     * @return
     */
    FileInfo saveUploadFile(MultipartFile file, Integer appType) throws Exception;

    /**
     * 保存上传文件
     * @param fileData
     * @param appType
     * @return
     * @throws Exception
     */
    com.thrift.common.body.FileInfo saveUploadFile(FileData fileData, Integer appType) throws Exception;

    /**
     * 根据文件路径获取文件信息
     * @param fileUrl
     * @return
     */
    com.thrift.common.body.FileInfo getFileInfo(String fileUrl);



}
