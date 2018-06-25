package com.mascot.service.mongo;

import java.util.List;
import java.util.Map;

/**
 * 争对多维度搜索缓存服务
 */
public interface CacheServer<T> {



    /**
     * 保存/修改搜索对象
     * @param mongoObject
     */
    Boolean saveOrUpdate(T mongoObject);

    /**
     * 保存/修改搜索对象列表
     * @param mongoObjectList
     * @return
     */
    Boolean saveOrUpdateList(List<T> mongoObjectList);

    /**
     * 获取分页搜索对象的主键
     * @param params 查询参数
     * @param pageIndex
     * @param pageSize
     * @return disIdList
     */
    List<T> getCacheObjectListByPage(Map<String, Object> params, Integer pageIndex, Integer pageSize);

    /**
     * 获取分页搜索对象总数
     * @param params
     * @return
     */
    Long getCacheObjectCountByPage(Map<String, Object> params);

    /**
     * 初始化缓存搜索对象
     * @return
     */
    Boolean initCacheObject();

}
