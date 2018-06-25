package com.mascot.bean;

/**
 *
 * Created by liujinren on 2017/8/1.
 */
public class FileInfo {

    private Integer fileId;
    private String fileUrl;
    private String originalFileName;
    private Long fileSize;

    public FileInfo(Integer fileId, String fileUrl, String originalFileName, Long fileSize) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.originalFileName = originalFileName;
        this.fileSize = fileSize;
    }

    public FileInfo() {
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
