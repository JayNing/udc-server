package com.interfaces.mascot;


import com.thrift.common.body.BookInfo;
import com.thrift.common.body.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 图书管理接口
 *
 * @author zhangmengyu
 * 2018/4/18
 */
public interface BookManageService extends BasicService {

    /**
     * 查询图书列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryBookInfo(UserInfo userInfo, String param, Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 查询图书列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    Integer getBookListCount(UserInfo userInfo, String param, Date startTime, Date endTime);

    /**
     * 新增图书
     *
     * @param userInfo 调用接口用户信息
     * @param bookNo   图书编号 --- 必输项
     * @param bookName 书名 --- 必输项
     * @param author   作者 --- 必输项
     * @return result -2：图书编码已存在
     */
    Integer saveBookInfo(UserInfo userInfo, String bookNo, String bookName, String author);

    /**
     * 根据图书编号删除图书信息
     *
     * @param userInfo 调用接口用户信息
     * @param pkId     图书主键 --- 必输项
     * @return result
     */
    Integer deleteBookInfoByPkId(UserInfo userInfo, Integer pkId);

    /**
     * 根据图书主键查询该书借阅记录
     *
     * @param userInfo  调用接口用户信息
     * @param pkId    图书主键 --- 必输项
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryBorrowRecordByPkId(UserInfo userInfo, Integer pkId, Integer pageIndex, Integer pageSize);

    /**
     * 查询该书借阅记录总条数
     *
     * @param userInfo  调用接口用户信息
     * @param pkId    图书主键 --- 必输项
     * @return list
     */
    Integer getBorrowRecordCount(UserInfo userInfo, Integer pkId);

    /**
     * 根据BookNo查询图书信息
     *
     * @param userInfo 调用接口用户信息
     * @param bookNo   图书编号 --- 必输项
     * @return bookInfo
     */
    BookInfo queryBookInfoById(UserInfo userInfo, String bookNo);

    /**
     * 新增借阅记录
     *
     * @param userInfo     调用接口用户信息
     * @param userId       借阅用户Id --- 不为空
     * @param bkPkId       图书主键 --- 不为空
     * @param borrowDays   借书时长（天数） --- 不为空
     * @param givebackTime 归还时间 --- 不为空
     * @param remark       备注
     * @return result  1:借阅表插入成功；2：借阅表插入成功且图书表状态更改成功;0:借阅表插入失败；-2：借阅表插入成功，但图书表状态更改失败
     */
    Integer saveBorrowInfo(UserInfo userInfo, Integer userId, Integer bkPkId, Integer borrowDays, Date givebackTime, String remark);

    /**
     * 归还图书
     *
     * @param userInfo 调用接口用户信息
     * @param userId   借阅用户Id --- 不为空
     * @param bkPkId   图书主键 --- 不为空
     * @return result 1:正常；-2：图书已还，无需归还；其他：失败
     */
    Integer updateGiveBackBook(UserInfo userInfo, Integer userId, Integer bkPkId);

}
