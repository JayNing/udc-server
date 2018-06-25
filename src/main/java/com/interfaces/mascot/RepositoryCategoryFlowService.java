package com.interfaces.mascot;

import com.thrift.common.body.RepositoryCategoryFlow;
import com.thrift.common.body.UserInfo;

import java.util.List;

/**
 * 知识仓库流程分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/10
 */
public interface RepositoryCategoryFlowService extends BasicService {


    /**
     * 获取知识流程分类目录列表
     *
     * @param userInfo       调用接口用户信息
     * @return list
     */
    List<RepositoryCategoryFlow> getRepositoryCategoryFlowList(UserInfo userInfo);

    /**
     * 新增流程分类目录
     *
     * @param userInfo       调用接口用户信息
     * @param flowName     目录名称
     * @return result -1:违反唯一约束;null:异常
     */
    Integer addRepositoryCategoryFlow(UserInfo userInfo, String flowName);

    /**
     * 获取知识流程分类详情
     *
     * @param userInfo 调用接口用户信息
     * @param flowId 流程分类编号
     * @return repositoryCategoryFlow
     */
    RepositoryCategoryFlow getRepositoryCategoryFlowDetail(UserInfo userInfo, Integer flowId);

    /**
     * 编辑修改知识流程分类
     *
     * @param userInfo   调用接口用户信息
     * @param flowId   流程分类编号
     * @param flowName 流程分类名称
     * @return result
     */
    Integer updateRepositoryCategoryFlow(UserInfo userInfo, Integer flowId, String flowName);

    /**
     * 删除知识流程分类
     *
     * @param userInfo 调用接口用户信息
     * @param flowId 流程分类编号
     * @return result
     */
    Integer deleteRepositoryCategoryFlow(UserInfo userInfo, Integer flowId);


}
