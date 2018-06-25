package com.mascot.service.impl;

import com.interfaces.mascot.BookManageService;
import com.thrift.common.body.BookInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.FlagType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * 图书管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/18
 */
@Service(value = "bookManageService")
public class BookManageServiceImpl extends BasicServiceImpl implements BookManageService {

    private final static Log logger = LogFactory.getLog(BookManageServiceImpl.class);

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
    @Override
    public List<Map<String, Object>> queryBookInfo(UserInfo userInfo, String param, Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {

                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Object[] obj;
                if (StringUtils.hasText(param)) {
                    param = "%" + param + "%";

                    sql = "SELECT PkId,BookNo,BookName,Author,Flag,CreateTime FROM F_BK_BookInfo WHERE CONCAT(BookNo, BookName, Author) LIKE ? AND CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND Flag != 3 ORDER BY CreateTime DESC LIMIT ?,? ";
                    obj = new Object[]{param, startTime, endTime, row, pageSize};

                } else {
                    sql = "SELECT PkId,BookNo,BookName,Author,Flag,CreateTime FROM F_BK_BookInfo WHERE CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND Flag != 3 ORDER BY CreateTime DESC LIMIT ?,? ";
                    obj = new Object[]{startTime, endTime, row, pageSize};
                }

                List<Map<String, Object>> list = jdbcTemplate.query(sql, obj, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pkId", rs.getInt("PkId"));
                    map.put("bookNo", rs.getString("BookNo"));
                    map.put("bookName", rs.getString("BookName"));
                    map.put("author", rs.getString("Author"));
                    map.put("createTime", rs.getString("CreateTime").substring(0, 10));
                    map.put("flag", rs.getString("Flag"));
                    map.put("pageIndex", pageIndex);
                    return map;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询图书列表成功!");
                return list;

            } else {
                logger.info("==========>Service查询图书列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询图书列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询图书列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    @Override
    public Integer getBookListCount(UserInfo userInfo, String param, Date startTime, Date endTime) {


        try {
            if (userInfo != null) {
                String sql;
                Object[] obj;
                if (StringUtils.hasText(param)) {
                    param = "%" + param + "%";

                    sql = "SELECT COUNT(1) FROM F_BK_BookInfo WHERE CONCAT(BookNo, BookName, Author) LIKE ? AND CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND Flag != 3";
                    obj = new Object[]{param, startTime, endTime};

                } else {
                    sql = "SELECT COUNT(1) FROM F_BK_BookInfo WHERE CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND Flag != 3";
                    obj = new Object[]{startTime, endTime};
                }

                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询图书列表总条数成功！");
                return count;

            } else {
                logger.info("==========>Service查询图书列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询图书列表总条数异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 新增图书
     *
     * @param userInfo 调用接口用户信息
     * @param bookNo   图书编号 --- 必输项
     * @param bookName 书名 --- 必输项
     * @param author   作者 --- 必输项
     * @return result -2：图书编码已存在
     */
    @Override
    public Integer saveBookInfo(UserInfo userInfo, String bookNo, String bookName, String author) {

        try {
            if (userInfo != null && StringUtils.hasText(bookNo) && StringUtils.hasText(bookName) && StringUtils.hasText(author)) {
                Integer result;
                if (queryBookInfoById(userInfo, bookNo) != null) {
                    //若已存在该书编码
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service图书编码已存在!");
                    result = -2;
                } else {
                    String sql = "INSERT INTO F_BK_BookInfo(BookNo,BookName,Author,Flag,CreateTime,CreateUserId,LastEditUserId) VALUES (?,?,?,1,?,?,?)";
                    result = jdbcTemplate.update(sql, bookNo, bookName, author, new Date(), userInfo.getUserId(), userInfo.getUserId());
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service新增图书接口调用成功!返回数据库执行成功行数为：" + result);
                }
                return result;
            } else {
                logger.info("==========>Service新增图书接口必传参数为空！");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service新增图书接口调用异常:");
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 根据图书主键删除图书信息
     *
     * @param userInfo 调用接口用户信息
     * @param pkId     图书主键 --- 必输项
     * @return result
     */
    @Override
    public Integer deleteBookInfoByPkId(UserInfo userInfo, Integer pkId) {
        try {
            if (userInfo != null && pkId != null) {
                String sql = "update F_BK_BookInfo set Flag = 3 where PkId = ?";
                Integer result = jdbcTemplate.update(sql, pkId);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除图书接口调用成功!返回数据库执行成功行数为：" + result);
                return result;
            } else {
                logger.info("==========>Service删除图书接口必传参数为空！");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除图书接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据图书主键查询该书借阅记录
     *
     * @param userInfo  调用接口用户信息
     * @param pkId      图书主键 --- 必输项
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryBorrowRecordByPkId(UserInfo userInfo, Integer pkId, Integer pageIndex, Integer pageSize) {
        try {
            if (userInfo != null && pkId != null && pageIndex != null && pageSize != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql = "SELECT b.BorrowId,u.UserId,d.DepartmentName,b.BorrowTime,b.GivebackTime,bk.Flag FROM F_BK_BorrowInfo b LEFT JOIN F_PUB_UserInfo u ON b.UserId = u.UserId LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON r.DepartmentId = d.DepartmentId LEFT JOIN F_BK_BookInfo bk ON bk.PkId = b.BkPkId WHERE b.BkPkId = ? GROUP BY b.BorrowId ORDER BY b.BorrowTime DESC LIMIT ?,?";

                List<Map<String, Object>> list1 = jdbcTemplate.query(sql, new Object[]{pkId, row, pageSize}, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("BorrowId", rs.getInt("BorrowId"));
                    map.put("UserId", rs.getString("UserId"));
                    map.put("DepartmentName", rs.getString("DepartmentName"));
                    map.put("GivebackTime", rs.getString("GivebackTime"));
                    map.put("BorrowTime", rs.getString("BorrowTime"));
                    map.put("Flag", rs.getInt("Flag"));
                    return map;
                });


                if (list1 != null && list1.size() > 0) {
                    List<Integer> idList = new ArrayList<>();
                    for (Map<String, Object> m : list1) {
                        idList.add(Integer.valueOf(m.get("UserId").toString()) );
                    }
                    List<UserInfo> usCenter = userInfoService.getUserListByUserIds(idList);
                    if (usCenter != null && usCenter.size() > 0) {
                        for (UserInfo us : usCenter) {
                            for (Map<String, Object> mp : list1) {
                                if (Integer.valueOf(mp.get("UserId").toString()) == us.getUserId()) {
                                    mp.put("UserName", us.getRealName());
                                    list.add(mp);
                                }

                            }
                        }

                    }
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询图书借阅记录接口调用成功!");
                return list;
            } else {
                logger.info("==========>Service查询图书借阅记录接口必传参数为空!");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询图书借阅记录接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询该书借阅记录总条数
     *
     * @param userInfo 调用接口用户信息
     * @param pkId     图书主键 --- 必输项
     * @return list
     */
    @Override
    public Integer getBorrowRecordCount(UserInfo userInfo, Integer pkId) {
        try {
            if (userInfo != null && pkId != null) {
                String sql = "SELECT COUNT(1) FROM F_BK_BorrowInfo b WHERE b.BkPkId = ?";

                Integer count = jdbcTemplate.queryForObject(sql, new Object[]{pkId}, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询该书借阅记录总条数接口调用成功!");
                return count;
            } else {
                logger.info("==========>Service查询该书借阅记录总条数接口必传参数为空!");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询该书借阅记录总条数接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据BookNo查询图书信息
     *
     * @param userInfo 调用接口用户信息
     * @param bookNo   图书编号 --- 必输项
     * @return bookInfo
     */
    @Override
    public BookInfo queryBookInfoById(UserInfo userInfo, String bookNo) {
        try {
            if (userInfo != null && bookNo != null) {
                String sql = "SELECT PkId,BookNo,BookName,Author,Flag,CreateTime from F_BK_BookInfo where BookNo = ?";
                try {
                    BookInfo bookInfo = jdbcTemplate.queryForObject(sql, new Object[]{bookNo}, (rs, rowNum) -> {

                        BookInfo book = new BookInfo();
                        book.setPkId(rs.getInt("PkId"));
                        book.setBookNo(rs.getString("BookNo"));
                        book.setBookName(rs.getString("BookName"));
                        book.setAuthor(rs.getString("Author"));
                        book.setFlag(rs.getInt("Flag"));
                        book.setCreateTime(rs.getLong("CreateTime"));
                        return book;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service查询图书详情接口调用成功!");
                    return bookInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service查询图书详情为空:");
                    e.printStackTrace();
                    return null;
                }
            } else {
                logger.info("==========>Service查询图书详情接口必传参数为空！");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询图书详情接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 新增借阅记录
     *
     * @param userInfo     调用接口用户信息
     * @param userId       借阅用户Id --- 不为空
     * @param bkPkId       图书主键 --- 不为空
     * @param borrowDays   借书时长（天数） --- 不为空
     * @param givebackTime 归还时间 --- 不为空
     * @param remark       备注
     * @return result -1：该书您已借阅，请先归还；1：成功
     */
    @Override
    public Integer saveBorrowInfo(UserInfo userInfo, Integer userId, Integer bkPkId, Integer borrowDays, Date givebackTime, String remark) {

        try {
            if (userInfo != null && userId != null && bkPkId != null && borrowDays != null && givebackTime != null) {
                String sql = "SELECT BorrowId,Flag FROM F_BK_BorrowInfo WHERE BkPkId = ? AND UserId = ?";
                Map<String, Object> map;
                try {
                    map = jdbcTemplate.queryForObject(sql,  new Object[]{bkPkId,userId},(rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("BorrowId", rs.getInt("BorrowId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }
                if (map != null && Integer.valueOf(map.get("Flag").toString())==1) {
                    //该书您已借阅，请先归还
                    return -1;
                } else {
                    String sql1 = "INSERT INTO F_BK_BorrowInfo(UserId,BkPkId,Flag,BorrowDays,GivebackTime,CreateTime,CreateUserId,LastEditUserId,Remark) VALUES (?,?,1,?,?,?,?,?,?)";
                    jdbcTemplate.update(sql1, userId, bkPkId, borrowDays, givebackTime, new Date(), userInfo.getUserId(), userInfo.getUserId(), remark);

                    String sql2 = "update F_BK_BookInfo set Flag = 2 where PkId = ?";
                    jdbcTemplate.update(sql2, bkPkId);
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增借阅记录接口调用成功!");
                return 1;
            } else {
                logger.info("==========>Service新增借阅记录接口必传参数为空!");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service新增借阅记录接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 归还图书
     *
     * @param userInfo 调用接口用户信息
     * @param userId   借阅用户Id --- 不为空
     * @param bkPkId   图书主键 --- 不为空
     * @return result 1:正常；-2：图书已还，无需归还；其他：失败
     */
    @Override
    public Integer updateGiveBackBook(UserInfo userInfo, Integer userId, Integer bkPkId) {

        try {
            if (userInfo != null && userId != null && bkPkId != null) {
                String sql = "SELECT BorrowId,Flag FROM F_BK_BorrowInfo WHERE BkPkId = ? AND UserId = ? ORDER BY CreateTime DESC LIMIT 1";
                Map<String, Object> map;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{bkPkId, userId}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("BorrowId", rs.getInt("BorrowId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }
                if (map != null && (Integer) map.get("Flag") == 1) {
                    //借书未还，现正常归还
                    String sql1 = "UPDATE F_BK_BorrowInfo SET Flag = 2 ,GivebackTime = ? WHERE BorrowId = ?";
                    jdbcTemplate.update(sql1, new Date(), map.get("BorrowId"));

                    String sql2 = "update F_BK_BookInfo set Flag = 1 where PkId = ?";
                    jdbcTemplate.update(sql2, bkPkId);

                    return 1;

                } else if (map != null && (Integer) map.get("Flag") == 2) {
                    //图书已还，无需归还
                    return -2;

                } else {
                    return -1;
                }

            } else {
                logger.info("==========>Service归还图书接口必传参数为空!");
                return null;
            }
        } catch (Exception e) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service归还图书记录接口调用异常:");
            e.printStackTrace();
            return null;
        }

    }

}
