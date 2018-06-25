package com.interfaces.mascot;

import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.RoleInfo;
import com.thrift.common.body.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 权限管理接口
 *
 * @author zhangmengyu
 * 2018/4/13
 */
public interface RoleManageService extends BasicService {

    /**
     * 获取角色列表信息
     *
     * @param userInfo  调用接口用户信息
     * @param roleName  角色姓名
     * @param pageIndex 当前页 --- 不可为空
     * @param pageSize  当前页数据条数 --- 不可为空
     * @return list
     */
    List<RoleInfo> getRoleList(UserInfo userInfo, String roleName, Integer pageIndex, Integer pageSize);

    /**
     * 获取角色列表信息总条数
     *
     * @param userInfo 调用接口用户信息
     * @param roleName 角色姓名
     * @return list
     */
    Integer getRoleListCount(UserInfo userInfo, String roleName);

    /**
     * 根据 moduleCode 获取模块信息
     *
     * @param userInfo   调用接口用户信息
     * @param moduleCode 模块权限码
     * @return moduleInfo
     */
    ModuleInfo getModuleDetailsByModuleCode(UserInfo userInfo, String moduleCode);

    /**
     * 通过角色名称查询角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleName 角色名称
     * @return roleInfo
     */
    RoleInfo getRoleInfoByRoleName(UserInfo userInfo, String roleName);

    /**
     * 通过角色id查询角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleId   角色编号
     * @return roleInfo
     */
    Map<String,Object> getRoleInfoDetailByRoleId(UserInfo userInfo, Integer roleId);

    /**
     * 添加角色信息
     *
     * @param userInfo     调用接口用户信息
     * @param roleName     角色姓名---不可为空
     * @param moduleIdList 模块Id列表
     * @return 1 成功
     */
    Integer saveRoleInfo(UserInfo userInfo, String roleName, List<Integer> moduleIdList);

    /**
     * 编辑修改角色信息
     *
     * @param userInfo   调用接口用户信息
     * @param roleName   角色名称
     * @param moduleIdList 模块Id
     * @return result  1：修改成功
     */
    Integer updateRoleInfo(UserInfo userInfo, Integer roleId, String roleName, List<Integer> moduleIdList);


    /**
     * 删除角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleId   角色流水号
     * @return result
     */
    Integer deleteRoleInfo(UserInfo userInfo, Integer roleId);

    /**
     * 根据 roleId 获取模块信息
     *
     * @param userInfo   调用接口用户信息
     * @param roleIdList 角色流水号
     * @return list
     */
    List<ModuleInfo> getModuleInfoByRoleId(UserInfo userInfo, List<Integer> roleIdList);

    /**
     * 根据 roleId 获取模块信息(web封装二维数组专用)
     *
     * @param userInfo   调用接口用户信息
     * @param roleIdList 角色流水号
     * @return list
     */
    List<ModuleInfo> getModuleInfoByRoleId2(UserInfo userInfo, List<Integer> roleIdList);

}
