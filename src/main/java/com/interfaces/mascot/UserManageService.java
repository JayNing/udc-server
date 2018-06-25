package com.interfaces.mascot;

import com.thrift.common.body.RoleInfo;
import com.thrift.common.body.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 账号管理接口
 *
 * @author zhangmengyu
 * 2018/4/23
 */
public interface UserManageService extends BasicService {

    /**
     * 获取账号管理列表信息
     *
     * @param userInfo     调用接口用户信息
     * @param name         用户姓名（登录名/别名）
     * @param departmentId 所属部门编号 --- 不为空
     * @param pageIndex    当前页 --- 不可为空
     * @param pageSize     当前页数据条数 --- 不可为空
     * @return list
     */
    List<Map<String, Object>> getUserInfo(UserInfo userInfo, String name, Integer departmentId, Integer pageIndex, Integer pageSize);

    /**
     * 获取账号管理列表信息总条数
     *
     * @param userInfo     调用接口用户信息
     * @param name         用户姓名（登录名/别名）
     * @param departmentId 所属部门编号
     * @return count
     */
    Integer getUserInfoCount(UserInfo userInfo, String name, Integer departmentId);

    /**
     * 新增用户
     *
     * @param userInfo     调用接口用户信息
     * @param loginName    用户名/登录名
     * @param passWd       密码
     * @param name         真实姓名
     * @param departmentId 部门编号
     * @param roleIdList   角色编号
     * @return result -1：已存在该有效用户；0：用户表插入失败；1：用户表插入成功，部门关联表插入失败；2：用户表和部门均插入成功
     */
    Integer saveUserInfo(UserInfo userInfo, String loginName, String passWd, String name, Integer departmentId, List<Integer> roleIdList, List<Integer> moduleIdList);

    /**
     * 编辑更改用户姓名头像部门
     *
     * @param userInfo     调用接口用户信息
     * @param userId       用户编号
     * @param avatar       头像地址
     * @param realName     真实姓名
     * @param departmentId 部门编号
     * @return result 1：修改成功；-1：用户中心修改失败;-2:无修改内容
     */
    Integer updateUserInfo1(UserInfo userInfo, Integer userId, String avatar, String realName, Integer departmentId);

    /**
     * 编辑更改用户
     *
     * @param userInfo     调用接口用户信息
     * @param userId       用户编号
     * @param avatar       头像地址
     * @param passWd       密码
     * @param realName     真实姓名
     * @param departmentId 部门编号
     * @param roleIdList   角色编号
     * @return result result 1：修改成功；-1：用户中心修改失败
     */
    Integer updateUserInfo2(UserInfo userInfo, Integer userId, String avatar, String passWd, String realName, Integer departmentId, List<Integer> roleIdList, List<Integer> moduleIdList);

    /**
     * 获取UserId获取详情信息
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return map
     */
    Map<String, Object> getUserInfoDetailByUserId(UserInfo userInfo, Integer userId);

    /**
     * 删除用户
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return result 1:成功；2：用户中心删除失败
     */
    Integer deleteUserInfo(UserInfo userInfo, Integer userId);

    /**
     * 根据 userId 获取角色列表
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return roleInfo
     */
    List<RoleInfo> getRoleInfoByUserId(UserInfo userInfo, Integer userId);

    /**
     * 发布平台公告
     *
     * @param userInfo       调用接口用户信息
     * @param title          标题
     * @param messageContent 消息内容
     * @return result 1：成功，其他:失败
     */
    Integer addNotice(UserInfo userInfo, String title, String messageContent);
}
