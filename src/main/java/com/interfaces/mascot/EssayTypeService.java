package com.interfaces.mascot;

import com.thrift.common.body.EssayType;
import com.thrift.common.body.UserInfo;

import java.util.List;

/**
 * 社区贴文分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/3
 */
public interface EssayTypeService extends BasicService {


    /**
     * 获取贴文分类目录列表
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 父类id --- 可为空
     * @return list
     */
    List<EssayType> getEssayTypeList(UserInfo userInfo, Integer parentId);

    /**
     * 新增分类目录
     *
     * @param userInfo      调用接口用户信息
     * @param parentId      父类id --- 可为空
     * @param essayTypeName 目录名称
     * @return result -1:违反唯一约束；null：异常
     */
    Integer addEssayType(UserInfo userInfo, Integer parentId, String essayTypeName);

    /**
     * 获取贴文分类详情
     *
     * @param userInfo    调用接口用户信息
     * @param essayTypeId 分类编号
     * @return essayType
     */
    EssayType getEssayTypeDetail(UserInfo userInfo, Integer essayTypeId);

    /**
     * 编辑修改贴文分类
     *
     * @param userInfo      调用接口用户信息
     * @param essayTypeId   分类编号
     * @param essayTypeName 分类名称
     * @return result
     */
    Integer updateEssayType(UserInfo userInfo, Integer essayTypeId, String essayTypeName);

    /**
     * 删除贴文分类
     *
     * @param userInfo    调用接口用户信息
     * @param essayTypeId 分类编号
     * @return result
     */
    Integer deleteEssayType(UserInfo userInfo, Integer essayTypeId);

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 一级分类编号
     * @return list
     */
    List<EssayType> getEssayTypeListByParentId(UserInfo userInfo, Integer parentId);


}
