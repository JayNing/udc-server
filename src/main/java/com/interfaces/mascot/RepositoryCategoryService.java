package com.interfaces.mascot;

import com.thrift.common.body.RepositoryCategory;
import com.thrift.common.body.UserInfo;

import java.util.List;

/**
 * 知识仓库分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/3
 */
public interface RepositoryCategoryService extends BasicService {


    /**
     * 获取知识分类目录列表
     *
     * @param userInfo       调用接口用户信息
     * @param repCatParentId 知识仓库分类的父类id
     * @return list
     */
    List<RepositoryCategory> getRepositoryCategoryList(UserInfo userInfo, Integer repCatParentId);

    /**
     * 新增分类目录
     *
     * @param userInfo       调用接口用户信息
     * @param repCatParentId 知识仓库分类的父类id
     * @param repCatName     目录名称
     * @return result -1:违反唯一约束;null:异常
     */
    Integer addRepositoryCategory1(UserInfo userInfo, Integer repCatParentId, String repCatName);

    /**
     * 获取知识分类详情
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 分类编号
     * @return repositoryCategory
     */
    RepositoryCategory getRepositoryCategoryDetail(UserInfo userInfo, Integer repCatId);

    /**
     * 编辑修改知识分类
     *
     * @param userInfo   调用接口用户信息
     * @param repCatId   分类编号
     * @param repCatName 分类名称
     * @return result
     */
    Integer updateRepositoryCategory(UserInfo userInfo, Integer repCatId, String repCatName);

    /**
     * 删除知识分类
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 分类编号
     * @return result
     */
    Integer deleteRepositoryCategory(UserInfo userInfo, Integer repCatId);

    /**
     * 根据上级目录获取下一级目录（web层用）
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 上级级分类编号
     * @return repCatIds 下级分类Id字符串，若无二级分类，则返回N
     */
    String getNextRepositoryCategoryByParentId(UserInfo userInfo, Integer repCatId);

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 一级分类编号
     * @return IdList
     */
    List<Integer> getAllRepositoryCategoryByParentId(UserInfo userInfo, Integer repCatId);


}
