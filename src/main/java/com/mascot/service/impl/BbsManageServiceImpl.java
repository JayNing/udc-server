package com.mascot.service.impl;

import com.interfaces.mascot.BbsManageService;
import com.interfaces.mascot.EssayTypeService;
import com.interfaces.mascot.KnowledgeManageService;
import com.interfaces.mascot.MessageManageService;
import com.mascot.service.mongo.CacheServer;
import com.mascot.service.mongo.bbs.BBSMongoBean;
import com.thrift.common.body.DiscussionInfo;
import com.thrift.common.body.TagInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.DisType;
import com.thrift.common.define.FlagType;
import com.thrift.common.define.MascotMessageType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 社区管理接口实现
 *
 * @author zhangmengyu
 * 2018/5/11
 */
@Service(value = "bbsManageService")
public class BbsManageServiceImpl extends BasicServiceImpl implements BbsManageService {

    private final static Log logger = LogFactory.getLog(BbsManageServiceImpl.class);
    @Autowired
    KnowledgeManageService knowledgeManageService;

    @Autowired
    CacheServer bbsCacheServer;

    @Autowired
    EssayTypeService essayTypeService;

    @Autowired
    MessageManageService messageManageService;

    /**
     * 查询社区管理列表
     *
     * @param userInfo    调用接口用户信息
     * @param title       社区贴文/提问标题
     * @param disType     社区帖子类型：1，贴文；2，提问
     * @param essayTypeId 分类Id
     * @param startTime   起始时间
     * @param endTime     截止时间
     * @param pageIndex   当前页
     * @param pageSize    每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryDiscussionManageList(UserInfo userInfo, String title, Integer disType, Integer essayTypeId, Date startTime,
                                                               Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null && disType != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Object[] obj;

                if (StringUtils.hasText(title) && essayTypeId != null) {
                    title = "%" + title + "%";
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName,D.LastEditTime FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.DisTitle LIKE ? AND D.EssayTypeId = ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY D.IsTop DESC ,D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{disType, title, essayTypeId, startTime, endTime, row, pageSize};
                } else if (StringUtils.hasText(title)) {
                    title = "%" + title + "%";
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName,D.LastEditTime FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.DisTitle LIKE ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY D.IsTop DESC ,D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{disType, title, startTime, endTime, row, pageSize};
                } else if (essayTypeId != null) {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName,D.LastEditTime FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.EssayTypeId = ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY D.IsTop DESC ,D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{disType, essayTypeId, startTime, endTime, row, pageSize};
                } else {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName,D.LastEditTime FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY D.IsTop DESC ,D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{disType, startTime, endTime, row, pageSize};
                }
                List<Map<String, Object>> list = jdbcTemplate.query(sql, obj, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("DisId", rs.getInt("DisId"));
                    map.put("DisType", rs.getInt("DisType"));
                    map.put("DisRelationId", rs.getInt("DisRelationId"));
                    map.put("DisTitle", rs.getString("DisTitle"));
                    map.put("CreateTime", rs.getString("CreateTime"));
                    map.put("IsTop", rs.getInt("IsTop"));
                    map.put("EssayTypeId", rs.getInt("EssayTypeId"));
                    map.put("EssayTypeName", rs.getString("EssayTypeName"));
                    map.put("LastEditTime", rs.getString("LastEditTime"));
                    return map;
                });
                return list;

            } else {
                logger.info("==========>Service查询社区管理列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询社区管理列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询社区管理列表总条数
     *
     * @param userInfo    调用接口用户信息
     * @param title       社区贴文/提问标题
     * @param disType     社区帖子类型：1，贴文；2，提问
     * @param essayTypeId 分类Id
     * @param startTime   起始时间
     * @param endTime     截止时间
     * @return count
     */
    @Override
    public Integer queryDiscussionManageListCount(UserInfo userInfo, String title, Integer disType, Integer essayTypeId, Date startTime, Date endTime) {

        try {
            if (userInfo != null && disType != null) {
                String sql;
                Object[] obj;

                if (StringUtils.hasText(title) && essayTypeId != null) {
                    title = "%" + title + "%";
                    sql = "SELECT count(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.DisTitle LIKE ? AND D.EssayTypeId = ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                    obj = new Object[]{disType, title, essayTypeId, startTime, endTime};
                } else if (StringUtils.hasText(title)) {
                    title = "%" + title + "%";
                    sql = "SELECT count(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.DisTitle LIKE ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                    obj = new Object[]{disType, title, startTime, endTime};
                } else if (essayTypeId != null) {
                    sql = "SELECT count(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.EssayTypeId = ? AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                    obj = new Object[]{disType, essayTypeId, startTime, endTime};
                } else {
                    sql = "SELECT count(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisType = ? AND D.DisFlag = 1 AND E.Flag = 1 AND D.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                    obj = new Object[]{disType, startTime, endTime};
                }
                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                return count;

            } else {
                logger.info("==========>Service查询社区管理列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询社区管理列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 查询社区界面搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryDiscussionList(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {

                List<Map<String, Object>> list = new ArrayList<>();

                if (StringUtils.hasText(param)) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("param", param);
                    List<BBSMongoBean> bbsMongoBeans = bbsCacheServer.getCacheObjectListByPage(params, pageIndex, pageSize);
                    if (bbsMongoBeans != null && bbsMongoBeans.size() > 0) {
                        for (BBSMongoBean BBSMongoBean : bbsMongoBeans) {
                            Integer disId = BBSMongoBean.getDisId();
                            Map<String, Object> map = queryDisInfoByDisId(userInfo, disId);
                            if (map != null) {
                                Integer comOrAnswerTotalCount;
                                if ((Integer) map.get("DisType") == 1) {
                                    comOrAnswerTotalCount = queryCommentInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                                } else {
                                    comOrAnswerTotalCount = queryAnswerInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                                }
                                map.put("ComOrAnswerTotalCount", comOrAnswerTotalCount);
                                list.add(map);
                            }

                        }
                    }

                } else {
                    String sql = "SELECT D.DisId FROM F_BBS_DiscussionInfo D  WHERE D.DisFlag = 1 ORDER BY D.IsTop DESC ,D.LastEditTime DESC LIMIT ?,?";
                    Integer row = pageUtil.getRow(pageIndex, pageSize);
                    List<Integer> disIdList = jdbcTemplate.queryForList(sql, new Object[]{row, pageSize}, Integer.class);
                    if (disIdList != null && disIdList.size() > 0) {
                        for (Integer disId : disIdList) {
                            Map<String, Object> map = queryDisInfoByDisId(userInfo, disId);
                            if (map != null) {
                                Integer comOrAnswerTotalCount;
                                if ((Integer) map.get("DisType") == 1) {
                                    comOrAnswerTotalCount = queryCommentInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                                } else {
                                    comOrAnswerTotalCount = queryAnswerInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                                }
                                map.put("ComOrAnswerTotalCount", comOrAnswerTotalCount);
                                list.add(map);
                            }

                        }
                    }

                }

                return list;

            } else {
                logger.info("==========>Service查询社区界面搜索列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询社区界面搜索列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询社区界面搜索列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param param    搜索框参数
     * @return list
     */
    @Override
    public Integer queryDiscussionListCount(UserInfo userInfo, String param) {

        try {
            if (userInfo != null) {

                Integer count;

                if (StringUtils.hasText(param)) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("param", param);
                    Long bbsCount = bbsCacheServer.getCacheObjectCountByPage(params);
                    count = bbsCount.intValue();
                } else {
                    String sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D  WHERE D.DisFlag = 1";
                    count = jdbcTemplate.queryForObject(sql, Integer.class);
                }
                return count;

            } else {
                logger.info("==========>Service查询社区界面搜索列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询社区界面搜索列表总条数异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 社区标签搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> showBbsByTags(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && StringUtils.hasText(param) && pageIndex != null && pageSize != null) {
                //更新标签搜索数
                knowledgeManageService.updateTagInfo(userInfo, param);
                List<Map<String, Object>> list = new ArrayList<>();

                param = "%" + param + "%";
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql = "SELECT D.DisId FROM F_BBS_DiscussionInfo D  WHERE D.TagStrings LIKE ? AND D.DisFlag = 1 ORDER BY D.IsTop DESC ,D.LastEditTime DESC LIMIT ?,?";
                List<Integer> disIdList = jdbcTemplate.queryForList(sql, new Object[]{param, row, pageSize}, Integer.class);
                if (disIdList != null && disIdList.size() > 0) {
                    for (Integer disId : disIdList) {
                        Map<String, Object> map = queryDisInfoByDisId(userInfo, disId);
                        if (map != null) {
                            Integer comOrAnswerTotalCount;
                            if ((Integer) map.get("DisType") == 1) {
                                comOrAnswerTotalCount = queryCommentInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                            } else {
                                comOrAnswerTotalCount = queryAnswerInfoListCount(userInfo, (Integer) map.get("DisRelationId"));
                            }
                            map.put("ComOrAnswerTotalCount", comOrAnswerTotalCount);
                            list.add(map);
                        }

                    }
                }


                logger.info("======[" + userInfo.getUserId() + "]==========>Service社区标签搜索列表成功!");
                return list;

            } else {
                logger.info("==========>Service社区标签搜索列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service社区标签搜索列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 社区标签搜索列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @return list
     */
    @Override
    public Integer queryBbsByTagsCount(UserInfo userInfo, String param) {

        try {
            if (userInfo != null && StringUtils.hasText(param)) {

                param = "%" + param + "%";

                String sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D  WHERE D.TagStrings LIKE ? AND D.DisFlag = 1";
                Object[] obj = new Object[]{param};


                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service社区标签搜索列表总条数成功！");
                return count;

            } else {
                logger.info("==========>Service社区标签搜索列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service社区标签搜索列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 社区置顶
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return count 2：置顶成功；1：取消置顶成功；其他失败
     */
    @Override
    public Integer addTopDiscussion(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo != null && disId != null) {
                DiscussionInfo discussionInfo = queryDiscussionInfoByDisId(userInfo, disId);
                String sql = "UPDATE F_BBS_DiscussionInfo SET IsTop = ? WHERE DisId = ?";
                Object[] obj;
                Integer result;
                if (discussionInfo != null && discussionInfo.getIsTop() == 1) {
                    //置顶
                    obj = new Object[]{2, disId};
                    result = 2;
                } else if (discussionInfo != null && discussionInfo.getIsTop() == 2) {
                    //取消置顶成功
                    obj = new Object[]{1, disId};
                    result = 1;
                } else {
                    throw new Exception("DiscussionInfo对象搜索为空或异常！");
                }

                jdbcTemplate.update(sql, obj);
                return result;

            } else {
                logger.info("==========>Service社区置顶必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service社区置顶异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除帖子
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return result 1:成功；其他：失败
     */
    @Override
    public Integer deleteDiscussion(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo != null && disId != null) {
                String sql = "UPDATE F_BBS_DiscussionInfo SET DisFlag = 2 WHERE DisId = ?";
                jdbcTemplate.update(sql, disId);
                DiscussionInfo discussionInfo = queryDiscussionInfoByDisId(userInfo, disId);
                if (discussionInfo != null && discussionInfo.getDisType() == 1) {
                    //贴文
                    String sql2 = "UPDATE F_BBS_EssayInfo SET EssFlag = 2 WHERE EssayId = ?";
                    jdbcTemplate.update(sql2, discussionInfo.getDisRelationId());
                } else if (discussionInfo != null && discussionInfo.getDisType() == 2) {
                    //提问
                    String sql2 = "UPDATE F_BBS_QuestionInfo SET Flag = 2 WHERE QueId = ?";
                    jdbcTemplate.update(sql2, discussionInfo.getDisRelationId());
                } else {
                    throw new Exception("DiscussionInfo对象搜索为空或异常！");
                }

                return 1;

            } else {
                logger.info("==========>Service删除帖子必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除帖子异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我的帖子
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disType   1:  贴文/分享;2: 提问
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryMyDiscussion(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disType) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Object[] obj;
                if (disType != null) {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisFlag = 1 AND E.Flag = 1 AND D.UserId = ? AND D.DisType = ? ORDER BY D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{userInfo.getUserId(), disType, row, pageSize};
                } else {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisFlag = 1 AND E.Flag = 1 AND D.UserId = ? ORDER BY D.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{userInfo.getUserId(), row, pageSize};
                }

                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, obj);
                if (list != null && list.size() > 0) {

                    for (Map<String, Object> m : list) {
                        Integer comCount = 0;
                        if (DisType.Essay.getValue() == (Integer) m.get("DisType")) {
                            comCount = queryCommentInfoListCount(userInfo, (Integer) m.get("DisRelationId"));
                        } else if (DisType.Question.getValue() == (Integer) m.get("DisType")) {
                            comCount = queryAnswerInfoListCount(userInfo, (Integer) m.get("DisRelationId"));
                        }
                        m.put("ComCount", comCount);
                    }
                }
                return list;

            } else {
                logger.info("==========>Service查询我的帖子列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我的帖子列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我的帖子总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disType  1:  贴文/分享;2: 提问
     * @return list
     */
    @Override
    public Integer queryMyDiscussionCount(UserInfo userInfo, Integer disType) {

        try {
            if (userInfo != null) {
                String sql;
                Object[] obj;
                if (disType != null) {
                    sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisFlag = 1 AND E.Flag = 1 AND D.UserId = ? AND D.DisType = ?";
                    obj = new Object[]{userInfo.getUserId(), disType};
                } else {
                    sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId WHERE D.DisFlag = 1 AND E.Flag = 1 AND D.UserId = ?";
                    obj = new Object[]{userInfo.getUserId()};
                }

                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }

                return count;

            } else {
                logger.info("==========>Service查询我的帖子列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我的帖子列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我收藏的帖子
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disType   1:  贴文/分享;2: 提问
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryCollectDiscussion(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disType) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                List<Map<String, Object>> allList = new ArrayList<>();
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Object[] obj;
                if (disType != null) {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.UserId,DEP.DepartmentId,DEP.DepartmentName,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId LEFT JOIN F_PUB_Collect C ON C.DisId = D.DisId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = D.UserId LEFT JOIN F_PUB_Department DEP ON DEP.DepartmentId = UDR.DepartmentId WHERE D.DisFlag = 1 AND E.Flag = 1 AND C.Flag = 1 AND C.UserId = ? AND D.DisType = ? ORDER BY C.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{userInfo.getUserId(), disType, row, pageSize};
                } else {
                    sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.UserId,DEP.DepartmentId,DEP.DepartmentName,D.DisTitle,D.CreateTime,D.IsTop,D.EssayTypeId,E.EssayTypeName FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId LEFT JOIN F_PUB_Collect C ON C.DisId = D.DisId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = D.UserId LEFT JOIN F_PUB_Department DEP ON DEP.DepartmentId = UDR.DepartmentId WHERE D.DisFlag = 1 AND E.Flag = 1 AND C.Flag = 1 AND C.UserId = ? ORDER BY C.CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{userInfo.getUserId(), row, pageSize};
                }

                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, obj);
                if (list != null && list.size() > 0) {
                    List<Integer> idList = new ArrayList<>();
                    for (Map<String, Object> m : list) {
                        idList.add((Integer) m.get("UserId"));
                    }
                    List<UserInfo> usList = userInfoService.getUserListByUserIds(idList);
                    if (usList != null && usList.size() > 0) {
                        for (Map<String, Object> m : list) {
                            for (UserInfo u : usList) {
                                if (m.get("UserId").equals(u.getUserId())) {
                                    m.put("RealName", u.getRealName());
                                    Integer comCount = 0;
                                    if (DisType.Essay.getValue() == (Integer) m.get("DisType")) {
                                        comCount = queryCommentInfoListCount(userInfo, (Integer) m.get("DisRelationId"));
                                    } else if (DisType.Question.getValue() == (Integer) m.get("DisType")) {
                                        comCount = queryAnswerInfoListCount(userInfo, (Integer) m.get("DisRelationId"));
                                    }
                                    m.put("ComCount", comCount);
                                    allList.add(m);
                                }
                            }
                        }
                    }
                }
                return allList;

            } else {
                logger.info("==========>Service查询我收藏的帖子列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我收藏的帖子列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我收藏的帖子总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disType  1:  贴文/分享;2: 提问
     * @return list
     */
    @Override
    public Integer queryCollectDiscussionCount(UserInfo userInfo, Integer disType) {

        try {
            if (userInfo != null) {
                String sql;
                Object[] obj;
                if (disType != null) {
                    sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId LEFT JOIN F_PUB_Collect C ON C.DisId = D.DisId WHERE D.DisFlag = 1 AND E.Flag = 1 AND C.Flag = 1 AND C.UserId = ? AND D.DisType = ?";
                    obj = new Object[]{userInfo.getUserId(), disType};
                } else {
                    sql = "SELECT COUNT(1) FROM F_BBS_DiscussionInfo D LEFT JOIN F_BBS_EssayType E ON D.EssayTypeId = E.EssayTypeId LEFT JOIN F_PUB_Collect C ON C.DisId = D.DisId WHERE D.DisFlag = 1 AND E.Flag = 1 AND C.Flag = 1 AND C.UserId = ?";
                    obj = new Object[]{userInfo.getUserId()};
                }

                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }

                return count;

            } else {
                logger.info("==========>Service查询我收藏的帖子列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我收藏的帖子列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我关注的人
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<UserInfo> queryFollowUser(UserInfo userInfo, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);

                String sql = "SELECT FollowedId FROM F_PUB_Follow WHERE FollowerId = ? AND Flag = 1 ORDER BY CreateTime DESC LIMIT ?,?";
                Object[] obj = new Object[]{userInfo.getUserId(), row, pageSize};

                List<Integer> userIdList = jdbcTemplate.queryForList(sql, obj, Integer.class);
                List<UserInfo> list = userInfoService.getUserListByUserIds(userIdList);

                return list;

            } else {
                logger.info("==========>Service查询我关注的人列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我关注的人列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询我关注的人总条数
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public Integer queryFollowUserCount(UserInfo userInfo) {

        try {
            if (userInfo != null) {

                String sql = "SELECT COUNT(1) FROM F_PUB_Follow WHERE FollowerId = ? AND Flag = 1";
                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, new Object[]{userInfo.getUserId()}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }


                return count;

            } else {
                logger.info("==========>Service查询我关注的人总条数列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询我关注的人总条数列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 积分排行榜
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryIntegralRanking(UserInfo userInfo, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                List<Map<String, Object>> list = new ArrayList<>();

                String sql1 = "SELECT U.UserId, U.UserScore, D.DepartmentId, D.DepartmentName FROM F_PUB_UserInfo U LEFT JOIN F_PUB_UserDepRel R ON R.UserId = U.UserId LEFT JOIN F_PUB_Department D ON D.DepartmentId = R.DepartmentId ORDER BY U.UserScore DESC LIMIT ?,?";
                Object[] obj = new Object[]{row, pageSize};
                List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql1, obj);
                if (mapList != null && mapList.size() > 0) {
                    List<Integer> userIdList = new ArrayList<>();
                    for (Map<String, Object> m : mapList) {
                        userIdList.add((Integer) m.get("UserId"));
                    }
                    List<UserInfo> usList = userInfoService.getUserListByUserIds(userIdList);
                    if (usList != null) {
                        for (Map<String, Object> map : mapList) {
                            for (UserInfo us : usList) {
                                if (us.getUserId() == (Integer) map.get("UserId")) {
                                    map.put("RealName", us.getRealName());
                                    map.put("LoginName", us.getRealName());
                                    list.add(map);
                                }
                            }
                        }
                    }
                }

                return list;

            } else {
                logger.info("==========>Service积分排行榜必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service积分排行榜异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 积分排行榜总条数
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public Integer queryIntegralRankingCount(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                String sql = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }
                return count;

            } else {
                logger.info("==========>Service积分排行榜必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service积分排行榜异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 提问题
     *
     * @param userInfo    调用接口用户信息
     * @param title       标题
     * @param queDesc     问题描述
     * @param tagStrings  标签名(字符串)
     * @param essayTypeId 帖子分类
     * @param repCatId1   一级知识分类
     * @param repCatId2   二级知识分类
     * @param flowId      流程分类
     * @return result -1:包含中文逗号，则返回关键字不合法 ; 1:成功
     */
    @Override
    public Integer addQuestion(UserInfo userInfo, String title, String queDesc, String tagStrings, Integer essayTypeId, Integer repCatId1, Integer repCatId2, Integer flowId) {
        try {
            if (userInfo != null && StringUtils.hasText(title) && StringUtils.hasText(queDesc) && StringUtils.hasText(tagStrings) && essayTypeId != null && repCatId1 != null && flowId != null) {

                if (tagStrings.contains("，")) {
                    //若包含中文逗号，则返回关键字不合法
                    return -1;
                } else {
                    //1.新增标签表，并将标签ID转成字符串
                    String[] tagNames = tagStrings.split(",");
                    String tagIds = "";
                    for (String tagName : tagNames) {
                        TagInfo tagInfo = knowledgeManageService.queryTagInfoByTagName(userInfo, tagName);
                        if (tagInfo == null) {
                            String sql2 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                            jdbcTemplate.update(sql2, tagName);
                            tagInfo = knowledgeManageService.queryTagInfoByTagName(userInfo, tagName);
                        }
                        String tagStr = tagInfo.getTagId() + ",";
                        tagIds += tagStr;
                    }
                    if (StringUtils.hasText(tagIds)) {
                        //去除最后一个“，”
                        tagIds = tagIds.substring(0, tagIds.length() - 1);
                    } else {
                        tagIds = "";
                    }

                    //2.判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                    Integer repCatId = repCatId2 == null ? repCatId1 : repCatId2;

                    //3.插入提问表并获取插入的数据的主键
                    Date day = new Date();
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    String sql = "INSERT INTO F_BBS_QuestionInfo(QueDesc,Flag,CreateTime,AnsId) VALUES (?,1,?,NULL)";
                    jdbcTemplate.update(
                            new PreparedStatementCreator() {
                                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                    int i = 0;
                                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(++i, queDesc);
                                    ps.setObject(++i, day);
                                    return ps;
                                }
                            },
                            keyHolder);

                    Integer queId = keyHolder.getKey().intValue();

                    //4.插入社区表
                    Integer disId = saveDiss(userInfo, 2, queId, title, tagIds, tagStrings, essayTypeId, repCatId, flowId);

                    //5.获取分类名称
                    String essayTypeName = essayTypeService.getEssayTypeDetail(userInfo, essayTypeId) == null ? null : essayTypeService.getEssayTypeDetail(userInfo, essayTypeId).getEssayTypeName();

                    //MongoDB缓存
                    BBSMongoBean mongoObject = new BBSMongoBean();
                    mongoObject.setDisId(disId);
                    mongoObject.setFlag(1);
                    mongoObject.setTitle(title);
                    mongoObject.setEssayTypeName(essayTypeName);
                    mongoObject.setDate(new Date());
                    bbsCacheServer.saveOrUpdate(mongoObject);

                    return 1;
                }

            } else {
                logger.info("==========>Service提问题必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service提问题异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 分享知识/帖子
     *
     * @param userInfo    调用接口用户信息
     * @param title       标题
     * @param artContent  问题描述
     * @param tagStrings  标签名(字符串)
     * @param essayTypeId 帖子分类
     * @param repCatId1   一级知识分类
     * @param repCatId2   二级知识分类
     * @param flowId      流程分类
     * @return result -1:包含中文逗号，则返回关键字不合法 ; 1:成功
     */
    @Override
    public Integer addEssayInfo(UserInfo userInfo, String title, String artContent, String tagStrings, Integer essayTypeId, Integer repCatId1, Integer repCatId2, Integer flowId) {
        try {
            if (userInfo != null && StringUtils.hasText(title) && StringUtils.hasText(artContent) && StringUtils.hasText(tagStrings) && essayTypeId != null && repCatId1 != null && flowId != null) {

                if (tagStrings.contains("，")) {
                    //若包含中文逗号，则返回关键字不合法
                    return -1;

                } else {
                    //1.新增标签表，并将标签ID转成字符串
                    String[] tagNames = tagStrings.split(",");
                    String tagIds = "";
                    for (String tagName : tagNames) {
                        TagInfo tagInfo = knowledgeManageService.queryTagInfoByTagName(userInfo, tagName);
                        if (tagInfo == null) {
                            String sql2 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                            jdbcTemplate.update(sql2, tagName);
                            tagInfo = knowledgeManageService.queryTagInfoByTagName(userInfo, tagName);
                        }
                        String tagStr = tagInfo.getTagId() + ",";
                        tagIds += tagStr;
                    }
                    if (StringUtils.hasText(tagIds)) {
                        //去除最后一个“，”
                        tagIds = tagIds.substring(0, tagIds.length() - 1);
                    } else {
                        tagIds = "";
                    }

                    //2.判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                    Integer repCatId = repCatId2 == null ? repCatId1 : repCatId2;

                    //3.插入帖子表并获取插入的数据的主键
                    Date day = new Date();
                    KeyHolder keyHolder = new GeneratedKeyHolder();
                    String sql = "INSERT INTO F_BBS_EssayInfo(EssayContent,EssFlag,CreateTime) VALUES (?,1,?)";
                    jdbcTemplate.update(
                            new PreparedStatementCreator() {
                                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                    int i = 0;
                                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(++i, artContent);
                                    ps.setObject(++i, day);
                                    return ps;
                                }
                            },
                            keyHolder);

                    Integer essayId = keyHolder.getKey().intValue();

                    //4.插入社区表
                    Integer disId = saveDiss(userInfo, 1, essayId, title, tagIds, tagStrings, essayTypeId, repCatId, flowId);

                    //5.获取分类名称
                    String essayTypeName = essayTypeService.getEssayTypeDetail(userInfo, essayTypeId) == null ? null : essayTypeService.getEssayTypeDetail(userInfo, essayTypeId).getEssayTypeName();
                    //正则过滤
                    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
                    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
                    Matcher m_html=p_html.matcher(artContent);
                    String art=m_html.replaceAll(""); //过滤html标签
                    art = art.trim().replaceAll("\n", "").replaceAll("\t", "");
                    //MongoDB缓存
                    BBSMongoBean mongoObject = new BBSMongoBean();
                    mongoObject.setDisId(disId);
                    mongoObject.setFlag(1);
                    mongoObject.setTitle(title);
                    mongoObject.setEssayContent(art);
                    mongoObject.setEssayTypeName(essayTypeName);
                    mongoObject.setDate(new Date());
                    bbsCacheServer.saveOrUpdate(mongoObject);
                    return 1;
                }

            } else {
                logger.info("==========>Service分享知识/帖子必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service分享知识/帖子异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 新增社区内容并返回主键
     *
     * @param userInfo           调用接口用户信息
     * @param disType            类型：1贴文 2提问
     * @param disRelationId      关联的贴文/提问的Id
     * @param disTitle           标题
     * @param tagIds             标签Id
     * @param tagStrings         标签
     * @param essayTypeId        社区类型
     * @param knlgCategoryId     知识仓库类型
     * @param knlgCategoryFlowId 知识仓库类型
     * @return Id
     */
    @Transactional
    public Integer saveDiss(UserInfo userInfo, Integer disType, Integer disRelationId, String disTitle,
                            String tagIds, String tagStrings, Integer essayTypeId, Integer knlgCategoryId, Integer knlgCategoryFlowId) throws Exception {

        Date day = new Date();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO F_BBS_DiscussionInfo(DisType,DisRelationId,CreateTime,DisFlag,DisTitle,UserId,IsTop,EssayTypeId,KnlgCategoryId,TagIds,TagStrings,KnlgCategoryFlowId,KnlgFlag,UsefulCount,UnUsefulCount) VALUES (?,?,?,1,?,?,1,?,?,?,?,?,1,0,0)";
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                        int i = 0;
                        java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(++i, disType);
                        ps.setInt(++i, disRelationId);
                        ps.setObject(++i, day);
                        ps.setString(++i, disTitle);
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setInt(++i, essayTypeId);
                        ps.setInt(++i, knlgCategoryId);
                        ps.setString(++i, tagIds);
                        ps.setString(++i, tagStrings);
                        ps.setInt(++i, knlgCategoryFlowId);

                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();

    }

    /**
     * 关注/取消关注
     *
     * @param userInfo 调用接口用户信息
     * @param userId   被关注者Id
     * @return result 1：关注成功；2取消关注成功；其他：失败
     */
    @Override
    public Integer addFollow(UserInfo userInfo, Integer userId) {
        try {
            if (userInfo != null && userId != null) {
                String sql = "SELECT FollowId,Flag FROM F_PUB_Follow WHERE FollowerId = ? AND FollowedId = ?";
                Map<String, Object> map;
                Integer result;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{userInfo.getUserId(), userId}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("FollowId", rs.getInt("FollowId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }

                if (map == null) {
                    //原先没有关注，新增关注
                    String sql1 = "INSERT INTO F_PUB_Follow(FollowerId,TargetType,FollowedId,CreateTime,Flag) VALUES (?,1,?,?,1)";
                    jdbcTemplate.update(sql1, userInfo.getUserId(), userId, new Date());
                    result = 1;
                } else if ((Integer) map.get("Flag") == 1) {
                    //原先已关注，现取消关注
                    String sql2 = "UPDATE F_PUB_Follow SET Flag = 2 WHERE FollowId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("FollowId"));
                    result = 2;
                } else if ((Integer) map.get("Flag") == 2) {
                    //原先取消关注，现重新关注
                    String sql2 = "UPDATE F_PUB_Follow SET Flag = 1 WHERE FollowId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("FollowId"));
                    result = 1;
                } else {
                    //存在该关注行为，但是状态不是1和2
                    return -1;
                }

                return result;

            } else {
                logger.info("==========>Service关注必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service关注异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 收藏/取消收藏
     *
     * @param userInfo   调用接口用户信息
     * @param targetType 类型：1，贴文，2，提问
     * @param disId      社区Id
     * @return result 1：收藏成功；2：取消收藏成功；其他失败
     */
    @Override
    public Integer addCollect(UserInfo userInfo, Integer targetType, Integer disId) {
        try {
            if (userInfo != null && disId != null && targetType != null) {

                String sql = "SELECT CollectId,Flag FROM F_PUB_Collect WHERE DisId = ? AND UserId = ? AND TargetType = ?";

                Map<String, Object> map;
                Integer result;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{disId, userInfo.getUserId(), targetType}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("CollectId", rs.getInt("CollectId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }

                if (map == null) {
                    //原先没有收藏，新增收藏
                    String sql1 = "INSERT INTO F_PUB_Collect(UserId,TargetType,DisId,CreateTime,Flag) VALUES (?,?,?,?,1)";
                    jdbcTemplate.update(sql1, userInfo.getUserId(), targetType, disId, new Date());
                    result = 1;
                } else if ((Integer) map.get("Flag") == 1) {
                    //原先已收藏，现取消收藏
                    String sql2 = "UPDATE F_PUB_Collect SET Flag = 2 WHERE CollectId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("CollectId"));
                    result = 2;
                } else if ((Integer) map.get("Flag") == 2) {
                    //原先取消收藏，现重新收藏
                    String sql2 = "UPDATE F_PUB_Collect SET Flag = 1 WHERE CollectId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("CollectId"));
                    result = 1;
                } else {
                    //存在该收藏行为，但是状态不是1和2
                    return -1;
                }
                return result;

            } else {
                logger.info("==========>Service收藏必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service收藏异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 评论贴文
     *
     * @param userInfo   调用接口用户信息
     * @param comContent 评论内容
     * @param essayId    贴文Id
     * @param disId      社区Id
     * @return result
     */
    @Override
    public Integer addCommentInfo(UserInfo userInfo, String comContent, Integer essayId, Integer disId) {
        try {
            if (userInfo != null && StringUtils.hasText(comContent) && essayId != null) {

                String sql = "INSERT INTO F_BBS_CommentInfo(ComContent,ComType,EssayId,UserId,TargetId,ComLike,CreateTime,ComFlag) VALUES (?,1,?,?,null,0,?,1)";
                jdbcTemplate.update(sql, comContent, essayId, userInfo.getUserId(), new Date());

                //MongoDB缓存
                BBSMongoBean mongoObject = new BBSMongoBean();
                mongoObject.setDisId(disId);
                mongoObject.setFlag(2);
                mongoObject.setComContent(comContent);
                mongoObject.setDate(new Date());
                bbsCacheServer.saveOrUpdate(mongoObject);

                return 1;

            } else {
                logger.info("==========>Service贴文评论必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service贴文评论异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 回答提问
     *
     * @param userInfo   调用接口用户信息
     * @param ansContent 回答内容
     * @param queId      提问Id
     * @return result
     */
    @Override
    public Integer addAnswerInfo(UserInfo userInfo, String ansContent, Integer queId) {
        try {
            if (userInfo != null && StringUtils.hasText(ansContent) && queId != null) {

                String sql = "INSERT INTO F_BBS_AnswerInfo(AnsContent,QueId,UserId,TargetId,AnsLike,CreateTime,AnsFlag) VALUES (?,?,?,null,0,?,1)";
                jdbcTemplate.update(sql, ansContent, queId, userInfo.getUserId(), new Date());

                return 1;

            } else {
                logger.info("==========>Service回答提问必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service回答提问异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 设为最佳答案
     *
     * @param userInfo 调用接口用户信息
     * @param ansId    回答Id
     * @param disId    社区Id
     * @return result -1：已有最佳答案，不可再设;1:成功
     */
    @Override
    public Integer addBestAnswer(UserInfo userInfo, Integer ansId, Integer queId, Integer disId) {
        try {
            if (userInfo != null && ansId != null && queId != null) {
                String sql1 = "SELECT AnsId FROM F_BBS_QuestionInfo WHERE QueId = ?";
                Integer oldAnsId = jdbcTemplate.queryForObject(sql1, new Object[]{queId}, Integer.class);
                if (oldAnsId != null) {
                    //已有最佳答案，不可再设
                    return -1;
                } else {
                    String sql2 = "UPDATE F_BBS_QuestionInfo SET AnsId = ? WHERE QueId = ?";
                    jdbcTemplate.update(sql2, ansId, queId);

                    String sql3 = "SELECT AnsContent FROM F_BBS_AnswerInfo WHERE AnsFlag = 1 AND AnsId = ? ";
                    String bestAnswer = jdbcTemplate.queryForObject(sql3, new Object[]{ansId}, String.class);

                    //MongoDB缓存
                    BBSMongoBean mongoObject = new BBSMongoBean();
                    mongoObject.setDisId(disId);
                    mongoObject.setFlag(2);
                    mongoObject.setComContent(bestAnswer);
                    mongoObject.setDate(new Date());
                    bbsCacheServer.saveOrUpdate(mongoObject);
                }

                return 1;

            } else {
                logger.info("==========>Service设为最佳答案必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service设为最佳答案异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 查询评论/回答列表
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disId     社区Id
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryComOrAnsList(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disId) {

        try {
            if (userInfo == null || pageIndex == null || pageSize == null || disId == null) {
                logger.info("==========>Service查询评论/回答列表必传参数为空!");
                return null;
            }
            String relationIdSql = "SELECT DisRelationId,DisType FROM F_BBS_DiscussionInfo WHERE DisId = ? AND DisFlag = ?";
            Map<String, Object> relationMap;
            try {
                relationMap = jdbcTemplate.queryForMap(relationIdSql, disId, FlagType.Effective.getValue());
            } catch (EmptyResultDataAccessException e) {
                relationMap = null;
            }
            List<Map<String, Object>> commentInfoList;
            if (relationMap != null && DisType.Essay.getValue() == (Integer) relationMap.get("DisType")) {
                Integer essayId = (Integer) relationMap.get("DisRelationId");
                commentInfoList = queryCommentInfoList(userInfo, pageIndex, pageSize, essayId);
            } else if (relationMap != null && DisType.Question.getValue() == (Integer) relationMap.get("DisType")) {
                Integer queId = (Integer) relationMap.get("DisRelationId");
                commentInfoList = queryAnswerInfoList(userInfo, pageIndex, pageSize, queId);
            } else {
                commentInfoList = null;
            }

            return commentInfoList;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询评论/回答列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询评论/回答列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区Id
     * @return list
     */
    @Override
    public Integer queryComOrAnsListCount(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo == null || disId == null) {
                logger.info("==========>Service查询评论/回答列表总条数必传参数为空!");
                return null;
            }
            String relationIdSql = "SELECT DisRelationId,DisType FROM F_BBS_DiscussionInfo WHERE DisId = ? AND DisFlag = ?";
            Map<String, Object> relationMap;
            try {
                relationMap = jdbcTemplate.queryForMap(relationIdSql, disId, FlagType.Effective.getValue());
            } catch (EmptyResultDataAccessException e) {
                relationMap = null;
            }
            Integer count;
            if (relationMap != null && DisType.Essay.getValue() == (Integer) relationMap.get("DisType")) {
                Integer essayId = (Integer) relationMap.get("DisRelationId");
                count = queryCommentInfoListCount(userInfo, essayId);
            } else if (relationMap != null && DisType.Question.getValue() == (Integer) relationMap.get("DisType")) {
                Integer queId = (Integer) relationMap.get("DisRelationId");
                count = queryAnswerInfoListCount(userInfo, queId);
            } else {
                count = 0;
            }

            return count;
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询提问回答列表总条数列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询贴文评论列表
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param essayId   贴文Id
     * @return list
     */
    private List<Map<String, Object>> queryCommentInfoList(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer essayId) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null && essayId != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql = "SELECT C.CommentId,C.CreateTime,C.ComContent,C.UserId,DP.DepartmentId,DP.DepartmentName,C.EssayId,C.ComLike FROM F_BBS_CommentInfo C LEFT JOIN F_PUB_UserDepRel UDR ON C.UserId = UDR.UserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.ComFlag = 1 AND C.EssayId = ? ORDER BY C.CreateTime DESC LIMIT ?,?";
                List<Map<String, Object>> commentInfoList = new ArrayList<>();
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, essayId, row, pageSize);
                if (list != null && list.size() > 0) {
                    List<Integer> userIdList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        userIdList.add((Integer) map.get("UserId"));
                    }
                    List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                    for (Map<String, Object> map : list) {
                        for (UserInfo us : userInfos) {
                            if (us.getUserId() == (Integer) map.get("UserId")) {
                                map.put("RealName", us.getRealName());
                                map.put("HeadUrl", us.getAvatar());
                                //查询点赞状态
                                String sql3 = "SELECT ComLikeId FROM F_BBS_DisctLike WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ? AND Flag  = ?";
                                Integer isLike;
                                try {
                                    isLike = jdbcTemplate.queryForObject(sql3, new Object[]{map.get("CommentId"), 1, userInfo.getUserId(), FlagType.Effective.getValue()}, Integer.class);
                                } catch (EmptyResultDataAccessException e) {
                                    isLike = null;
                                }
                                Boolean isComLike = (isLike != null);
                                map.put("IsComLike", isComLike);
                                commentInfoList.add(map);
                            }
                        }
                    }

                }
                return commentInfoList;

            } else {
                logger.info("==========>Service查询贴文评论列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询贴文评论列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询贴文评论列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param essayId  贴文Id
     * @return count
     */
    private Integer queryCommentInfoListCount(UserInfo userInfo, Integer essayId) {

        try {
            if (userInfo != null && essayId != null) {

                String sql = "SELECT COUNT(1) FROM F_BBS_CommentInfo WHERE ComFlag = 1 AND EssayId = ?";
                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, new Object[]{essayId}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }

                return count;

            } else {
                logger.info("==========>Service查查询贴文评论列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询贴文评论列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询提问回答列表
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param queId     提问Id
     * @return list
     */
    private List<Map<String, Object>> queryAnswerInfoList(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer queId) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null && queId != null) {

                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql = "SELECT A.AnsId,A.QueId,A.CreateTime,A.UserId,DP.DepartmentId,DP.DepartmentName,A.AnsContent,A.AnsLike FROM F_BBS_AnswerInfo A LEFT JOIN F_PUB_UserDepRel UDR ON A.UserId = UDR.UserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE A.AnsFlag = 1 AND A.QueId = ? ORDER BY A.CreateTime DESC LIMIT ?,?";
                List<Map<String, Object>> commentInfoList = new ArrayList<>();
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, queId, row, pageSize);

                //判断当前登录用户是否是提问用户
                String sql2 = "SELECT UserId FROM F_BBS_DiscussionInfo WHERE DisType = 2 AND DisRelationId = ?";
                Integer queUserId;
                try {
                    queUserId = jdbcTemplate.queryForObject(sql2, new Object[]{queId}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    queUserId = null;
                }
                Integer buttonFlag;
                if (queUserId != null && queUserId == userInfo.getUserId()) {
                    //若是同一人
                    buttonFlag = 1;
                } else {
                    //若不是同一人
                    buttonFlag = 2;
                }

                if (list != null && list.size() > 0) {
                    List<Integer> userIdList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        userIdList.add((Integer) map.get("UserId"));
                    }
                    List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                    for (Map<String, Object> map : list) {
                        for (UserInfo us : userInfos) {
                            if (us.getUserId() == (Integer) map.get("UserId")) {
                                map.put("AnswerName", us.getRealName());
                                map.put("HeadUrl", us.getAvatar());
                                map.put("ButtonFlag", buttonFlag);
                                //查询点赞状态
                                String sql3 = "SELECT ComLikeId FROM F_BBS_DisctLike WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ? AND Flag  = ?";
                                Integer isLike;
                                try {
                                    isLike = jdbcTemplate.queryForObject(sql3, new Object[]{map.get("AnsId"), 2, userInfo.getUserId(), FlagType.Effective.getValue()}, Integer.class);
                                } catch (EmptyResultDataAccessException e) {
                                    isLike = null;
                                }
                                Boolean isComLike = (isLike != null);
                                map.put("IsComLike", isComLike);
                                commentInfoList.add(map);
                            }
                        }
                    }

                }

                return commentInfoList;

            } else {
                logger.info("==========>Service查询提问回答列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询提问回答列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询提问回答列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param queId    提问Id
     * @return list
     */
    private Integer queryAnswerInfoListCount(UserInfo userInfo, Integer queId) {

        try {
            if (userInfo != null && queId != null) {

                String sql = "SELECT COUNT(1) FROM F_BBS_AnswerInfo WHERE AnsFlag = 1 AND QueId = ? ";
                Integer count;
                try {
                    count = jdbcTemplate.queryForObject(sql, new Object[]{queId}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    count = 0;
                }

                return count;

            } else {
                logger.info("==========>Service查询提问回答列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询提问回答列表总条数列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 点赞回答/评论  取消点赞
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  点赞对象：1.贴文评论；2.提问回答
     * @param relationId 对象Id
     * @return result 1：成功，其他：失败
     */
    @Override
    public Map<String, Object> addDisctLike(UserInfo userInfo, Integer UsefulTyp, Integer relationId) {
        try {
            if (userInfo != null && relationId != null && UsefulTyp != null) {

                String sql = "SELECT ComLikeId,Flag FROM F_BBS_DisctLike WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ?";
                Map<String, Object> map;
                //1点赞成功；2取消成功
                Integer result = null;
                Integer likeCount = null;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{relationId, UsefulTyp, userInfo.getUserId()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("ComLikeId", rs.getInt("ComLikeId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {

                    map = null;
                }
                if (map == null) {
                    //原先没有点赞，则新增点赞
                    String sql2 = "INSERT INTO F_BBS_DisctLike(RelationId,UserId,Flag,UsefulTyp) VALUES (?,?,1,?)";
                    jdbcTemplate.update(sql2, relationId, userInfo.getUserId(), UsefulTyp);
                    if (UsefulTyp == 1) {
                        //贴文评论点赞
                        String sql3 = "SELECT ComLike FROM F_BBS_CommentInfo WHERE ComFlag = 1 AND CommentId = ?";
                        Integer comLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        comLike++;
                        String sql4 = "UPDATE F_BBS_CommentInfo SET ComLike = ? WHERE CommentId = ?";
                        jdbcTemplate.update(sql4, comLike, relationId);
                        result = 1;
                        likeCount = comLike;
                    } else if (UsefulTyp == 2) {
                        //提问回答点赞
                        String sql3 = "SELECT AnsLike FROM F_BBS_AnswerInfo WHERE AnsFlag = 1 AND AnsId = ?";
                        Integer ansLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        ansLike++;
                        String sql4 = "UPDATE F_BBS_AnswerInfo SET AnsLike = ? WHERE AnsId = ?";
                        jdbcTemplate.update(sql4, ansLike, relationId);
                        result = 1;
                        likeCount = ansLike;
                    }
                } else if ((Integer) map.get("Flag") == 1) {
                    //原先已点赞，现取消赞
                    String sql2 = "UPDATE F_BBS_DisctLike SET Flag = 2 WHERE ComLikeId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("ComLikeId"));
                    if (UsefulTyp == 1) {
                        //贴文评论取消点赞
                        String sql3 = "SELECT ComLike FROM F_BBS_CommentInfo WHERE ComFlag = 1 AND CommentId = ?";
                        Integer comLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        comLike--;
                        String sql4 = "UPDATE F_BBS_CommentInfo SET ComLike = ? WHERE CommentId = ?";
                        jdbcTemplate.update(sql4, comLike, relationId);
                        result = 2;
                        likeCount = comLike;
                    } else if (UsefulTyp == 2) {
                        //提问回答取消点赞
                        String sql3 = "SELECT AnsLike FROM F_BBS_AnswerInfo WHERE AnsFlag = 1 AND AnsId = ?";
                        Integer ansLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        ansLike--;
                        String sql4 = "UPDATE F_BBS_AnswerInfo SET AnsLike = ? WHERE AnsId = ?";
                        jdbcTemplate.update(sql4, ansLike, relationId);
                        result = 2;
                        likeCount = ansLike;
                    }
                } else if ((Integer) map.get("Flag") == 2) {
                    //原先取消赞，现重新赞
                    String sql2 = "UPDATE F_BBS_DisctLike SET Flag = 1 WHERE ComLikeId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("ComLikeId"));
                    if (UsefulTyp == 1) {
                        //贴文评论点赞
                        String sql3 = "SELECT ComLike FROM F_BBS_CommentInfo WHERE ComFlag = 1 AND CommentId = ?";
                        Integer comLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        comLike++;
                        String sql4 = "UPDATE F_BBS_CommentInfo SET ComLike = ? WHERE CommentId = ?";
                        jdbcTemplate.update(sql4, comLike, relationId);
                        result = 1;
                        likeCount = comLike;
                    } else if (UsefulTyp == 2) {
                        //提问回答点赞
                        String sql3 = "SELECT AnsLike FROM F_BBS_AnswerInfo WHERE AnsFlag = 1 AND AnsId = ?";
                        Integer ansLike = jdbcTemplate.queryForObject(sql3, new Object[]{relationId}, Integer.class);
                        ansLike++;
                        String sql4 = "UPDATE F_BBS_AnswerInfo SET AnsLike = ? WHERE AnsId = ?";
                        jdbcTemplate.update(sql4, ansLike, relationId);
                        result = 1;
                        likeCount = ansLike;
                    }
                } else {
                    //存在该点赞行为，但是状态不是1和2
                    return null;
                }
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", result);
                resultMap.put("likeCount", likeCount);

                return resultMap;

            } else {
                logger.info("==========>Service评论回答点赞必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service评论回答点赞异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 有用
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  有用对象：1.贴文；2.最佳回答
     * @param relationId 对象Id ：提问Id/贴文Id
     * @param disId      社区Id
     * @return result 1：成功，-1：已点无用，请先取消； 其他：失败
     */
    @Override
    public Integer addUseful(UserInfo userInfo, Integer UsefulTyp, Integer relationId, Integer disId) {
        try {
            if (userInfo != null && relationId != null && UsefulTyp != null && disId != null) {

                String unUsefulSql = "SELECT PkId FROM F_BBS_UnUseFul WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ? AND Flag =1";
                Integer pkId;
                try {
                    pkId = jdbcTemplate.queryForObject(unUsefulSql, new Object[]{relationId, UsefulTyp, userInfo.getUserId()}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    pkId = null;
                }
                if (pkId != null) {
                    //已点无用，请先取消
                    return -1;
                }
                String sql = "SELECT PkId,Flag FROM F_BBS_UseFul WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ?";
                Map<String, Object> map;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{relationId, UsefulTyp, userInfo.getUserId()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("PkId", rs.getInt("PkId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }

                if (map == null) {
                    //原先没有点有用，则新增有用
                    String sql2 = "INSERT INTO F_BBS_UseFul(RelationId,UserId,Flag,UsefulTyp) VALUES (?,?,1,?)";
                    jdbcTemplate.update(sql2, relationId, userInfo.getUserId(), UsefulTyp);

                    String sql3 = "SELECT UsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer usefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    usefulCount++;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, usefulCount, disId);
                } else if ((Integer) map.get("Flag") == 1) {
                    //原先已点有用，现取消
                    String sql2 = "UPDATE F_BBS_UseFul SET Flag = 2 WHERE PkId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("PkId"));

                    String sql3 = "SELECT UsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer usefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    usefulCount--;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, usefulCount, disId);
                } else if ((Integer) map.get("Flag") == 2) {
                    //原先取消有用，现重新有用
                    String sql2 = "UPDATE F_BBS_UseFul SET Flag = 1 WHERE PkId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("PkId"));

                    String sql3 = "SELECT UsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer usefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    usefulCount++;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, usefulCount, disId);
                } else {
                    //存在该有用行为，但是状态不是1和2
                    return -1;
                }

                return 1;

            } else {
                logger.info("==========>Service有用必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service有用异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 无用
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  有用对象：1.贴文；2.最佳回答
     * @param relationId 对象Id：提问Id/贴文Id
     * @param disId      社区Id
     * @return result 1：成功，-1：已点有用，请先取消； 其他：失败
     */
    @Override
    public Integer addUnUseful(UserInfo userInfo, Integer UsefulTyp, Integer relationId, Integer disId) {
        try {
            if (userInfo != null && relationId != null && UsefulTyp != null) {

                String usefulSql = "SELECT PkId FROM F_BBS_UseFul WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ? AND Flag =1";
                Integer pkId;
                try {
                    pkId = jdbcTemplate.queryForObject(usefulSql, new Object[]{relationId, UsefulTyp, userInfo.getUserId()}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    pkId = null;
                }
                if (pkId != null) {
                    //已点有用，请先取消
                    return -1;
                }

                String sql = "SELECT PkId,Flag FROM F_BBS_UnUseFul WHERE RelationId = ? AND UsefulTyp = ? AND UserId = ?";
                Map<String, Object> map;
                try {
                    map = jdbcTemplate.queryForObject(sql, new Object[]{relationId, UsefulTyp, userInfo.getUserId()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("PkId", rs.getInt("PkId"));
                        m.put("Flag", rs.getInt("Flag"));
                        return m;
                    });
                } catch (EmptyResultDataAccessException e) {
                    map = null;
                }
                if (map == null) {
                    //原先没有点无用，则新增无用
                    String sql2 = "INSERT INTO F_BBS_UnUseFul(RelationId,UserId,Flag,UsefulTyp) VALUES (?,?,1,?)";
                    jdbcTemplate.update(sql2, relationId, userInfo.getUserId(), UsefulTyp);

                    String sql3 = "SELECT UnUsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer unUsefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    unUsefulCount++;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UnUsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, unUsefulCount, disId);
                } else if ((Integer) map.get("Flag") == 1) {
                    //原先已点无用，现取消
                    String sql2 = "UPDATE F_BBS_UnUseFul SET Flag = 2 WHERE PkId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("PkId"));

                    String sql3 = "SELECT UnUsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer unUsefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    unUsefulCount--;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UnUsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, unUsefulCount, disId);
                } else if ((Integer) map.get("Flag") == 2) {
                    //原先取消无用，现重新无用
                    String sql2 = "UPDATE F_BBS_UnUseFul SET Flag = 1 WHERE PkId = ?";
                    jdbcTemplate.update(sql2, (Integer) map.get("PkId"));

                    String sql3 = "SELECT UnUsefulCount FROM F_BBS_DiscussionInfo WHERE DisFlag = 1 AND DisId = ?";
                    Integer unUsefulCount = jdbcTemplate.queryForObject(sql3, new Object[]{disId}, Integer.class);
                    unUsefulCount++;
                    String sql4 = "UPDATE F_BBS_DiscussionInfo SET UnUsefulCount = ? WHERE DisId = ?";
                    jdbcTemplate.update(sql4, unUsefulCount, disId);
                } else {
                    //存在该无用行为，但是状态不是1和2
                    return -1;
                }

                return 1;

            } else {
                logger.info("==========>Service无用必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service无用异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 通过disId查询有用数和无用数
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return discussionInfo
     */
    @Override
    public Map<String, Object> queryUsefulCountByDisId(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo != null && disId != null) {

                String sql = "SELECT D.DisId,D.UsefulCount,D.UnUsefulCount FROM F_BBS_DiscussionInfo D WHERE D.DisId = ?";
                Map<String, Object> discussionInfo;
                try {
                    discussionInfo = jdbcTemplate.queryForMap(sql, disId);
                } catch (EmptyResultDataAccessException e) {
                    discussionInfo = null;
                }
                return discussionInfo;

            } else {
                throw new Exception("==========>Service通过disId查询有用数和无用数必传参数为空!");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service通过disId查询有用数和无用数异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 通过disId查询社区详细
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return discussionInfo
     */
    @Override
    public Map<String, Object> queryDisInfoByDisId(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo != null && disId != null) {

                String sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.UserId,UDR.DepartmentId,DP.DepartmentName,D.DisFlag,D.EssayTypeId,D.TagIds,D.TagStrings,D.KnlgCategoryId,D.KnlgCategoryFlowId,D.KnlgFlag,D.UsefulCount,D.UnUsefulCount,ES.EssayTypeName FROM F_BBS_DiscussionInfo D LEFT JOIN F_PUB_UserDepRel UDR ON D.UserId = UDR.UserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId LEFT JOIN F_BBS_EssayType ES ON ES.EssayTypeId = D.EssayTypeId WHERE D.DisId = ?";
                Map<String, Object> discussionInfo;
                try {
                    discussionInfo = jdbcTemplate.queryForObject(sql, new Object[]{disId}, (rs, rowNum) -> {
                        Map<String, Object> dis = new HashMap<>();
                        dis.put("DisId", rs.getInt("DisId"));
                        dis.put("DisType", rs.getInt("DisType"));
                        dis.put("DisRelationId", rs.getInt("DisRelationId"));
                        dis.put("DisTitle", rs.getString("DisTitle"));
                        dis.put("UserId", rs.getInt("UserId"));
                        dis.put("DepartmentId", rs.getInt("DepartmentId"));
                        dis.put("DepartmentName", rs.getString("DepartmentName"));
                        dis.put("DisFlag", rs.getInt("DisFlag"));
                        dis.put("EssayTypeId", rs.getInt("EssayTypeId"));
                        dis.put("TagIds", rs.getString("TagIds"));
                        dis.put("TagStrings", rs.getString("TagStrings"));
                        dis.put("KnlgCategoryId", rs.getInt("KnlgCategoryId"));
                        dis.put("KnlgCategoryFlowId", rs.getInt("KnlgCategoryFlowId"));
                        dis.put("KnlgFlag", rs.getInt("KnlgFlag"));
                        dis.put("UsefulCount", rs.getInt("UsefulCount"));
                        dis.put("UnUsefulCount", rs.getInt("UnUsefulCount"));
                        dis.put("EssayTypeName", rs.getString("EssayTypeName"));
                        return dis;
                    });
                } catch (EmptyResultDataAccessException e) {
                    discussionInfo = null;
                }
                if (discussionInfo != null) {
                    if ((Integer) discussionInfo.get("DisType") == 1) {
                        //贴文
                        String sql2 = "SELECT EssayContent FROM F_BBS_EssayInfo WHERE EssayId = ?";
                        Map<String, Object> essayInfo = jdbcTemplate.queryForMap(sql2, (Integer) discussionInfo.get("DisRelationId"));
                        discussionInfo.put("EssayContent", essayInfo.get("EssayContent"));
                    } else if ((Integer) discussionInfo.get("DisType") == 2) {
                        //提问
                        String sql2 = "SELECT Q.QueDesc,Q.AnsId,A.AnsContent FROM F_BBS_QuestionInfo Q LEFT JOIN F_BBS_AnswerInfo A ON Q.AnsId = A.AnsId WHERE Q.QueId = ?";
                        Map<String, Object> essayInfo = jdbcTemplate.queryForMap(sql2, (Integer) discussionInfo.get("DisRelationId"));
                        discussionInfo.put("QueDesc", essayInfo.get("QueDesc"));
                        discussionInfo.put("AnsId", essayInfo.get("AnsId"));
                        discussionInfo.put("AnsContent", essayInfo.get("AnsContent"));
                    }

                    List<Integer> userIdList = new ArrayList<>();
                    userIdList.add((Integer) discussionInfo.get("UserId"));
                    List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                    discussionInfo.put("AuthorName", userInfos.get(0).getRealName());

                    //查询关注状态
                    String sql1 = "SELECT FollowId FROM F_PUB_Follow WHERE FollowerId = ? AND FollowedId = ? AND Flag = ?";
                    Integer isFll;
                    try {
                        isFll = jdbcTemplate.queryForObject(sql1, new Object[]{userInfo.getUserId(), discussionInfo.get("UserId"), FlagType.Effective.getValue()}, Integer.class);
                    } catch (EmptyResultDataAccessException e) {
                        isFll = null;
                    }
                    Boolean isFollow = (isFll != null);

                    discussionInfo.put("IsFollow", isFollow);

                    //查询收藏状态
                    String sql2 = "SELECT CollectId FROM F_PUB_Collect WHERE DisId = ? AND UserId = ? AND Flag = ?";
                    Integer isCll;
                    try {
                        isCll = jdbcTemplate.queryForObject(sql2, new Object[]{disId, userInfo.getUserId(), FlagType.Effective.getValue()}, Integer.class);
                    } catch (EmptyResultDataAccessException e) {
                        isCll = null;
                    }
                    Boolean isCollect = (isCll != null);

                    discussionInfo.put("IsCollect", isCollect);

                    //查询有用状态
                    String sql3 = "SELECT PkId FROM F_BBS_UseFul WHERE RelationId = ? AND UserId = ? AND Flag = ? AND UsefulTyp = ?";
                    Integer isUs;
                    try {
                        isUs = jdbcTemplate.queryForObject(sql3, new Object[]{discussionInfo.get("DisRelationId"), userInfo.getUserId(), FlagType.Effective.getValue(), discussionInfo.get("DisType")}, Integer.class);
                    } catch (EmptyResultDataAccessException e) {
                        isUs = null;
                    }
                    Boolean isUseful = (isUs != null);

                    discussionInfo.put("IsUseful", isUseful);

                    //查询无用状态
                    String sql4 = "SELECT PkId FROM F_BBS_UnUseFul WHERE RelationId = ? AND UserId = ? AND Flag = ? AND UsefulTyp = ?";
                    Integer isUnUs;
                    try {
                        isUnUs = jdbcTemplate.queryForObject(sql4, new Object[]{discussionInfo.get("DisRelationId"), userInfo.getUserId(), FlagType.Effective.getValue(), discussionInfo.get("DisType")}, Integer.class);
                    } catch (EmptyResultDataAccessException e) {
                        isUnUs = null;
                    }
                    Boolean isUnUseful = (isUnUs != null);

                    discussionInfo.put("IsUnUseful", isUnUseful);

                    //查询当前登录用户是否是发帖用户
                    Boolean followButton = (userInfo.getUserId() == (Integer) discussionInfo.get("UserId"));
                    discussionInfo.put("FollowButton", followButton);
                }

                return discussionInfo;

            } else {
                throw new Exception("==========>Service通过disId查询社区帖子信息必传参数为空!");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service通过disId查询社区帖子信息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 通过disId查询社区帖子信息
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return discussionInfo
     */
    private DiscussionInfo queryDiscussionInfoByDisId(UserInfo userInfo, Integer disId) {

        try {
            if (userInfo != null && disId != null) {

                String sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.CreateTime,D.DisFlag,D.DisTitle,D.IsTop,D.UserId,D.EssayTypeId,D.KnlgCategoryId,D.TagIds,D.TagStrings,D.KnlgCategoryFlowId,D.KnlgFlag,D.UsefulCount,D.UnUsefulCount FROM F_BBS_DiscussionInfo D WHERE D.DisId = ?";
                DiscussionInfo discussionInfo = jdbcTemplate.queryForObject(sql, new Object[]{disId}, (rs, rowNum) -> {
                    DiscussionInfo dis = new DiscussionInfo();
                    dis.setDisId(rs.getInt("DisId"));
                    dis.setDisType(rs.getInt("DisType"));
                    dis.setDisRelationId(rs.getInt("DisRelationId"));
                    dis.setDisTitle(rs.getString("DisTitle"));
                    dis.setCreateTime(rs.getInt("CreateTime"));
                    dis.setIsTop(rs.getInt("IsTop"));
                    dis.setDisFlag(rs.getInt("DisFlag"));
                    dis.setUserId(rs.getInt("UserId"));
                    dis.setEssayTypeId(rs.getInt("EssayTypeId"));
                    dis.setKnlgCategoryId(rs.getInt("KnlgCategoryId"));
                    dis.setKnlgCategoryFlowId(rs.getInt("KnlgCategoryFlowId"));
                    dis.setTagIds(rs.getString("TagIds"));
                    dis.setTagStrings(rs.getString("TagStrings"));
                    dis.setKnlgFlag(rs.getInt("KnlgFlag"));
                    dis.setKnlgFlag(rs.getInt("UsefulCount"));
                    dis.setKnlgFlag(rs.getInt("UnUsefulCount"));
                    return dis;
                });
                return discussionInfo;

            } else {
                throw new Exception("==========>Service通过disId查询社区帖子信息必传参数为空!");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service通过disId查询社区帖子信息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 社区加入知识仓库
     *
     * @param userInfo 调用接口用户信息
     * @param dis      社区对象Id
     * @return result 1：成功，-2：已加入知识仓库，-3：该提问没有最佳答案，无法加入知识仓库，其他失败
     */
    @Override
    public Integer addBbsToKnlg(UserInfo userInfo, Integer dis) {
        try {
            if (userInfo != null && dis != null) {
                Map<String, Object> discussionInfo = queryDisInfoByDisId(userInfo, dis);
                Integer result;
                if (discussionInfo != null && (Integer) discussionInfo.get("KnlgFlag") == 2) {
                    //已加入知识仓库
                    result = -2;
                } else if (discussionInfo != null && (Integer) discussionInfo.get("DisType") == 1) {
                    //贴文加入知识仓库
                    knowledgeManageService.addKnowledge(userInfo, discussionInfo.get("DisTitle").toString(), discussionInfo.get("AuthorName").toString(), "", 1,
                            discussionInfo.get("EssayContent").toString(), discussionInfo.get("TagStrings").toString(), null, (Integer) discussionInfo.get("KnlgCategoryId"),
                            (Integer) discussionInfo.get("KnlgCategoryId"), (Integer) discussionInfo.get("KnlgCategoryFlowId"), 2);
                    String sql = "UPDATE F_BBS_DiscussionInfo SET KnlgFlag = 2 WHERE DisId = ?";
                    jdbcTemplate.update(sql, dis);
                    result = 1;
                } else if (discussionInfo != null && (Integer) discussionInfo.get("DisType") == 2 && discussionInfo.get("AnsId") != null) {
                    //提问加入知识仓库
                    knowledgeManageService.addKnowledge(userInfo, discussionInfo.get("DisTitle").toString(), discussionInfo.get("AuthorName").toString(), discussionInfo.get("QueDesc").toString(), 1,
                            discussionInfo.get("AnsContent").toString(), discussionInfo.get("TagStrings").toString(), null, (Integer) discussionInfo.get("KnlgCategoryId"),
                            (Integer) discussionInfo.get("KnlgCategoryId"), (Integer) discussionInfo.get("KnlgCategoryFlowId"), 2);
                    String sql = "UPDATE F_BBS_DiscussionInfo SET KnlgFlag = 2 WHERE DisId = ?";
                    jdbcTemplate.update(sql, dis);
                    result = 1;
                } else if (discussionInfo != null && (Integer) discussionInfo.get("DisType") == 2 && discussionInfo.get("AnsId") == null) {
                    //该提问没有最佳答案，无法加入知识仓库
                    result = -3;
                } else {
                    result = -1;
                }

                return result;

            } else {
                logger.info("==========>Service社区加入知识仓库必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service社区加入知识仓库异常：");
            ep.printStackTrace();
            return null;
        }
    }


    /**
     * 相关推荐 --- 社区
     * 规则：相同知识仓库分类下，有用数最多的帖子
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区Id
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryBbsRecommendByDisId(UserInfo userInfo, Integer disId, Integer pageSize) {

        try {
            if (userInfo != null && disId != null && pageSize != null) {
                String sql = "SELECT D.KnlgCategoryId FROM F_BBS_DiscussionInfo D WHERE D.DisId = ?";
                Integer knlgCategoryId = jdbcTemplate.queryForObject(sql, new Object[]{disId}, Integer.class);

                String sql1 = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.UserId,UDR.DepartmentId,DP.DepartmentName,D.DisFlag,D.EssayTypeId,D.TagIds,D.TagStrings,D.KnlgCategoryId,D.KnlgCategoryFlowId,D.KnlgFlag,D.UsefulCount,D.UnUsefulCount FROM F_BBS_DiscussionInfo D LEFT JOIN F_PUB_UserDepRel UDR ON D.UserId = UDR.UserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE D.KnlgCategoryId = ? ORDER BY D.UsefulCount DESC LIMIT 0,?";
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, knlgCategoryId, pageSize);
                List<Map<String, Object>> bbsList = new ArrayList<>();

                if (list != null && list.size() > 0) {
                    List<Integer> userIdList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        userIdList.add((Integer) map.get("UserId"));
                    }
                    List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                    for (Map<String, Object> map : list) {
                        for (UserInfo us : userInfos) {
                            if (map.get("UserId").equals(us.getUserId()) && !disId.equals(map.get("DisId"))) {
                                map.put("AnswerName", us.getRealName());
                                bbsList.add(map);
                            }
                        }
                    }

                }

                return bbsList;

            } else {
                logger.info("==========>Service查询社区相关推荐列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询社区相关推荐列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 发布社区公告
     *
     * @param userInfo       调用接口用户信息
     * @param messageContent 消息内容
     * @return result 1：成功，其他:失败
     */
    @Override
    public Integer addBbsNotice(UserInfo userInfo, String messageContent) {
        try {
            if (userInfo == null || !StringUtils.hasText(messageContent)) {
                logger.info("==========>Service发布社区公告必传参数为空！");
                return null;
            }
            String userSql = "SELECT UserId FROM F_PUB_UserInfo";
            List<Integer> usList = jdbcTemplate.queryForList(userSql, Integer.class);

            return messageManageService.addMessage(userInfo, MascotMessageType.BbsNotice.getValue(), null, messageContent, usList);


        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service发布社区公告异常：");
            ep.printStackTrace();
            return null;
        }
    }


}
