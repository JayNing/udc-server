package com.mascot.service.mongo.bbs;

import java.util.Date;

/**
 * 社区搜索对象
 */
public class BBSMongoBean {

    /**
     * 数据状态： 1: 初始化; 2：更新
     */
    private Integer flag = 1;
    private Date date = new Date();

    /**
     * 社区编号
     */
    private Integer disId;


    /**
     * 社区分类
     */
    private String essayTypeName;

    /**
     * 社区标题
     */
    private String title;

    /**
     * 贴文内容
     */
    private String essayContent;

    /**
     * 评论内容
     */
    private String comContent;

    /**
     * 关键字列表
     */
    /*private List<String> labels;*/
    public Integer getDisId() {
        return disId;
    }

    public void setDisId(Integer disId) {
        this.disId = disId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEssayTypeName() {
        return essayTypeName;
    }

    public void setEssayTypeName(String essayTypeName) {
        this.essayTypeName = essayTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEssayContent() {
        return essayContent;
    }

    public void setEssayContent(String essayContent) {
        this.essayContent = essayContent;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

}
