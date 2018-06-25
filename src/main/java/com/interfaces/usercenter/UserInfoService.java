package com.interfaces.usercenter;


import com.thrift.common.body.PageInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.body.UserSessionInfo;
import com.thrift.common.define.*;
import com.thrift.common.head.UDCHead;

import java.util.List;
import java.util.Map;

/**
 * 用户信息服务
 * Created by liujinren on 2017/7/27.
 */
public interface UserInfoService extends BasicService{

    /**
     * 通过 ID 列表 获取 用户列表
     * @param idList
     * @return
     */
    List<UserInfo> getUserListByUserIds(List<Integer> idList);

    /**
     * 通过手机号获取用户信息
     * @param phoneList
     * @return
     */
    List<UserInfo> getUserInfoByPhone(List<String> phoneList);

    /**
     * 通过邮箱获取用户信息
     * @param emailList
     * @return
     */
    List<UserInfo> getUserInfoByEmail(List<String> emailList);

    /**
     * 通过登录名获取用户信息
     * @param loginNameList
     * @return
     */
    List<UserInfo> getUserInfoByLoginName(List<String> loginNameList);
    /**
     * 添加一个用户
     * @param userInfo
     * @return
     */
    Map<RespType, Object> addUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    Boolean updateUserInfo(Integer userId, UserInfo userInfo);

    /**
     * 删除系统用户
     * @param userId
     * @param systemType
     * @return
     */
    Boolean deleteSystemUser(Integer userId, Integer systemType);

    /**
     * 关联系统用户
     * @param userId
     * @param systemType
     * @return
     */
    Boolean relationSystemUser(Integer userId, Integer systemType);

    /**
     * 用户登录
     * @param loginName
     * @param passwd
     * @param platformType
     * @param appType
     * @return
     */
    UserSessionInfo login(String loginName, String passwd, PlatformType platformType, AppType appType);

    /**
     * 自动登录 TODO
     * @param tokenId
     * @return
     */
    UserSessionInfo autoLoginByTokenId(String tokenId);

    /**
     * 验证登录
     * @param tokenId
     * @return
     */
    UserSessionInfo verifyLoginByTokenId(String tokenId);

    /**
     * 检查无效的 tokenId 列表
     * @param tokenIdList
     * @return
     */
    List<String> checkInvalidTokenIdList(List<String> tokenIdList);

    /**
     * 获取用户, 通过手机号 邮箱 登录名
     * @return
     */
    Map<RespType, Object> getUserByPhone(UserInfo userInfo);

    /**
     * 通过 tokenId 获取用户信息
     * @param tokenId
     * @return
     */
    UserSessionInfo getUserInfoByTokenId(String tokenId);

    /**
     * 通过手机号修改密码
     * @param phone
     * @param password
     * @return
     */
    Boolean updatePasswordByPhone(String phone, String password);

    /**
     * 修改密码
     * @param oldPassword
     * @param phone
     * @param newPassword
     * @return
     */
    Boolean updatePasswordByPassword(String oldPassword, String phone, String newPassword);

    /**
     * 分页获取用户数据
     * @param appType
     * @param pageInfo
     * @return
     */
    List<UserInfo> getUserInfoListByPage(AppType appType, UserType userType, PageInfo pageInfo);

    /**
     * 获取用户总数量
     * @param appType
     * @return
     */
    Integer getUserInfoCount(AppType appType, UserType userType);

    /**
     * 清除相关 session
     * @param head
     * @return
     */
    Boolean clearSessionWithTokenId(UDCHead head);

    /**
     * 检查是否已存在电话
     * @param phone
     * @return
     */
    Boolean checkPhone(String phone);

    /**
     * 检查是否已存在邮箱
     * @param email
     * @return
     */
    Boolean checkEmail(String email);
    /**
     * 检查是否已存在登录名
     * @param loginName
     * @return
     */
    Boolean checkLoginName(String loginName);

    /**
     * 获取已登录的用户信息, TODO
     * @param appType
     * @param platformType
     * @param pageInfo
     * @return
     */
    List<UserInfo> getRecentlyLoginUserList(AppType appType, PlatformType platformType, PageInfo pageInfo);

    /**
     * 根据条件获取用户分页列表
     * 与 getUserInfoCountWithPage 配合使用
     * @param appType not null
     * @param userIdList option
     * @param userNameList option
     * @param realNameList option
     * @param phoneList option
     * @param emailList option
     * @return
     */
    List<UserInfo> getUserInfoListWithPage(AppType appType, List<Integer> userIdList, List<String> userNameList,
                                           List<String> realNameList, List<String> phoneList, List<String> emailList,
                                           Integer pageIndex, Integer pageSize);
    /**
     * 根据条件获取用户总数
     * 与 getUserInfoListWithPage 配合使用
     * @param appType not null
     * @param userIdList option
     * @param userNameList option
     * @param realNameList option
     * @param phoneList option
     * @param emailList option
     * @return
     */
    Integer getUserInfoCountWithPage(AppType appType, List<Integer> userIdList, List<String> userNameList,
                                     List<String> realNameList, List<String> phoneList, List<String> emailList);

}
