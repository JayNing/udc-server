package com.interfaces.mascot;

import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 模块管理接口
 *
 * @author zhangmengyu
 * 2018/4/13
 */
public interface ModuleManageService extends BasicService {

    /**
     * 新增模块
     *
     * @param userInfo 调用接口用户信息
     * @return result  1：成功
     *                  -1：请先输入上级模块的权限码！
     *                  -2：上级分类为空！
     *                  -3：模块码名称重复
     *                  -4：权限码重复
     *                  -5：异常
     */
    Integer addModuleInfo(UserInfo userInfo, Integer parentId, String moduleCode, String ModuleName);

    /**
     * 更改模块
     *
     * @param userInfo 调用接口用户信息
     * @return result  1：成功
     *                  -1：请先输入上级模块的权限码！
     *                  -2：上级分类为空！
     *                  -3：模块码名称重复
     *                  -4：权限码重复
     *                  -5：异常
     */
    Integer updateModuleInfo(UserInfo userInfo, Integer parentId, Integer moduleId, String moduleCode, String ModuleName);

    /**
     * 删除模块
     *
     * @param userInfo 调用接口用户信息
     * @return result 共删除的条数
     */
    Integer deleteModuleInfo(UserInfo userInfo, Integer moduleId);

    /**
     * 根据上级目录获取下一级目录
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 上级分类编号
     * @return list
     */
    List<Map<String,Object>> getNextModuleInfoByParentId(UserInfo userInfo, Integer moduleId);

    /**
     * 获取所有模块列表
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    List<ModuleInfo> getAllModuleInfo(UserInfo userInfo);

    /**
     * 根据 userId 获取模块信息
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户Id
     */
    List<ModuleInfo> getModuleInfoByUserId(UserInfo userInfo, Integer userId);

    /**
     * 根据 moduleId 获取模块信息
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 模块码自增主键
     * @return ModuleInfo
     */
    ModuleInfo getModuleInfoByModuleId(UserInfo userInfo, Integer moduleId);

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 一级分类编号
     * @return moduleIdList
     */
    List<Integer> getModuleInfoByParentId(UserInfo userInfo, Integer moduleId);

}
