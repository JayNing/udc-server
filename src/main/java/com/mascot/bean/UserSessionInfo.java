package com.mascot.bean;

import com.thrift.common.body.UserInfo;
import com.thrift.common.define.AppType;
import com.thrift.common.define.PlatformType;

import java.util.Date;

/**
 * session
 * Created by Jerry on 2017/7/27.
 */
public class UserSessionInfo {

    private String tokenId;
    private UserInfo userInfo;
    private Date loginDate = new Date();             // 登录时间
    private Date lastOperationDate = new Date();     // 最后一次操作时间
    private Integer flag;               // 状态标记, 1: 正常, 2: 临时
    private PlatformType platformType;
    private AppType appType;

    public UserSessionInfo() {
    }

    public UserSessionInfo(String tokenId, UserInfo userInfo, Date loginDate, Date lastOperationDate,
                           Integer flag, PlatformType platformType, AppType appType) {
        this.tokenId = tokenId;
        this.userInfo = userInfo;
        this.loginDate = loginDate;
        this.lastOperationDate = lastOperationDate;
        this.flag = flag;
        this.platformType = platformType;
        this.appType = appType;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(Date lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "UserSessionInfo{" +
                "tokenId='" + tokenId + '\'' +
                ", userInfo=" + userInfo +
                ", loginDate=" + loginDate +
                ", lastOperationDate=" + lastOperationDate +
                ", flag=" + flag +
                ", platformType=" + platformType +
                ", appType=" + appType +
                '}';
    }
}
