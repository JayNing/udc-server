package com.interfaces.mascot;

import com.thrift.common.body.ExCategory;
import com.thrift.common.body.UserInfo;

import java.util.List;

/**
 * 考试培训分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/17
 */
public interface ExCategoryService extends BasicService {


    /**
     * 获取考试培训分类目录列表
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 考试培训分类的父类id
     * @return list
     */
    List<ExCategory> getExCategoryList(UserInfo userInfo, Integer parentId);

    /**
     * 新增分类目录
     *
     * @param userInfo     调用接口用户信息
     * @param parentId     考试培训分类的父类id
     * @param categoryName 目录名称
     * @return result -1:违反唯一约束；null：异常
     */
    Integer addExCategory1(UserInfo userInfo, Integer parentId, String categoryName);

    /**
     * 获取考试培训分类详情
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 分类编号
     * @return exCategory
     */
    ExCategory getExCategoryDetail(UserInfo userInfo, Integer categoryId);

    /**
     * 编辑修改考试培训分类
     *
     * @param userInfo     调用接口用户信息
     * @param categoryId   分类编号
     * @param categoryName 分类名称
     * @return result
     */
    Integer updateExCategory(UserInfo userInfo, Integer categoryId, String categoryName);

    /**
     * 删除考试培训分类
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 分类编号
     * @return result
     */
    Integer deleteExCategory(UserInfo userInfo, Integer categoryId);

    /**
     * 根据上级目录获取下一级目录
     *
     * @param userInfo 调用接口用户信息
     * @param categoryId 上级级分类编号
     * @return categoryIds 下级分类Id字符串，若无二级分类，则返回N
     */
    String getNextExCategoryByParentId(UserInfo userInfo, Integer categoryId);

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 一级分类编号
     * @return IdList
     */
    List<Integer> getAllExCategoryByParentId(UserInfo userInfo, Integer categoryId);


}
