package com.mascot.utils.common;

import org.springframework.stereotype.Component;

/**
 * 分页工具类
 */
@Component
public class PageUtil {
    /**
     * 根据当前页换算当前页第一条数据在该表所有数据中的rowNum
     *
     * @param pageIndex 当前页数
     * @param pageSize  当前页数据条数
     * @return row
     */
    public Integer getRow(Integer pageIndex, Integer pageSize) {
        Integer row;
        if (pageIndex == 1) {
            row = 0;
        } else {
            pageIndex += -1;
            row = pageIndex * pageSize;
        }
        return row;
    }
}
