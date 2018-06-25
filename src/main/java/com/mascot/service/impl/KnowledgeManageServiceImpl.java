package com.mascot.service.impl;

import com.interfaces.mascot.KnowledgeManageService;
import com.interfaces.mascot.RepositoryCategoryService;
import com.thrift.common.body.*;
import com.thrift.common.define.FlagType;
import com.thrift.common.define.KnowledgeType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 知识管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/28
 */
@Service(value = "knowledgeManageService")
public class KnowledgeManageServiceImpl extends BasicServiceImpl implements KnowledgeManageService {

    private final static Log logger = LogFactory.getLog(KnowledgeManageServiceImpl.class);

    @Autowired
    RepositoryCategoryService repositoryCategoryService;

    /**
     * 查询知识仓库管理列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     知识标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param flowId    流程分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryKnowledgeInfo(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Integer flowId,
                                                        Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Map<String, Object> paramMap = new HashMap<>();

                if (repCatId1 != null) {
                    //1.确定知识分类的值
                    List<Integer> cgIdList = new ArrayList<>();
                    if (repCatId2 != null) {
                        cgIdList.add(repCatId2);
                    } else {
                        cgIdList = repositoryCategoryService.getAllRepositoryCategoryByParentId(userInfo, repCatId1);
                        cgIdList.add(repCatId1);
                    }

                    //2.确定是否传入参数title和节点分类
                    if (StringUtils.hasText(title) && flowId != null) {
                        title = "%" + title + "%";
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.FlowId = :flowId AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("title", title);
                        paramMap.put("flowId", flowId);
                    } else if (StringUtils.hasText(title)) {
                        title = "%" + title + "%";
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("title", title);
                    } else if (flowId != null) {
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.FlowId = :flowId AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("flowId", flowId);
                    } else {
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                    }
                    paramMap.put("cgIdList", cgIdList);

                } else {
                    //没有传入知识分类
                    if (StringUtils.hasText(title) && flowId != null) {
                        title = "%" + title + "%";
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.FlowId = :flowId AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("title", title);
                        paramMap.put("flowId", flowId);
                    } else if (StringUtils.hasText(title)) {
                        title = "%" + title + "%";
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("title", title);
                    } else if (flowId != null) {
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.FlowId = :flowId AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                        paramMap.put("flowId", flowId);
                    } else {
                        sql = "SELECT K.KId,K.Title,K.CreateTime,K.BbsFlag,K.ViewCount FROM F_KNLG_KnowledgeInfo K  WHERE K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag ORDER BY K.CreateTime DESC LIMIT :row,:pageSize";
                    }
                }
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);
                paramMap.put("flag", FlagType.Effective.getValue());
                paramMap.put("row", row);
                paramMap.put("pageSize", pageSize);
                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                List<Map<String, Object>> list = jdbc.query(sql, paramMap, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("KId", rs.getInt("KId"));
                    map.put("Title", rs.getString("Title"));
                    map.put("CreateTime", rs.getString("CreateTime"));
                    map.put("BbsFlag", rs.getInt("BbsFlag"));
                    map.put("ViewCount", rs.getInt("ViewCount"));
                    return map;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询知识列表成功!");
                return list;

            } else {
                logger.info("==========>Service查询知识列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询知识列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询知识仓库管理列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     知识标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param flowId    流程分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    @Override
    public Integer queryKnowledgeInfoCount(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Integer flowId,
                                           Date startTime, Date endTime) {

        try {
            if (userInfo != null) {
                String sql;
                Map<String, Object> paramMap = new HashMap<>();
                if (repCatId1 != null) {
                    //1.确定知识分类的值
                    List<Integer> cgIdList = new ArrayList<>();
                    if (repCatId2 != null) {
                        cgIdList.add(repCatId2);
                    } else {
                        cgIdList = repositoryCategoryService.getAllRepositoryCategoryByParentId(userInfo, repCatId1);
                        cgIdList.add(repCatId1);
                    }

                    //2.确定是否传入参数title和节点分类
                    if (StringUtils.hasText(title) && flowId != null) {
                        title = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.FlowId = :flowId AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("title", title);
                        paramMap.put("flowId", flowId);
                    } else if (StringUtils.hasText(title)) {
                        title = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("title", title);
                    } else if (flowId != null) {
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.FlowId = :flowId AND K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("flowId", flowId);
                    } else {
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.RepCatId IN (:cgIdList) AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                    }
                    paramMap.put("cgIdList", cgIdList);

                } else {
                    //没有传入知识分类
                    if (StringUtils.hasText(title) && flowId != null) {
                        title = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.FlowId = :flowId AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("title", title);
                        paramMap.put("flowId", flowId);
                    } else if (StringUtils.hasText(title)) {
                        title = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.Title LIKE :title AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("title", title);
                    } else if (flowId != null) {
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.FlowId = :flowId AND K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                        paramMap.put("flowId", flowId);
                    } else {
                        sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) AND K.Flag = :flag";
                    }
                }
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);
                paramMap.put("flag", FlagType.Effective.getValue());
                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                Integer count = jdbc.queryForObject(sql, paramMap, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询知识仓库管理列表总条数成功!");
                return count;

            } else {
                logger.info("==========>Service查询知识仓库管理列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询知识仓库管理列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 智能搜索知识仓库列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数 --- 不为空
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryKnowledgeInfoIntelligent(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && StringUtils.hasText(param) && pageIndex != null && pageSize != null) {

                param = "%" + param + "%";
                Integer row = pageUtil.getRow(pageIndex, pageSize);

                String sql = "SELECT K.KId,K.Title,K.Profile,K.CreateTime,K.TagIds,K.TagStrings,K.BbsFlag FROM F_KNLG_KnowledgeInfo K  WHERE CONCAT(K.TagStrings, K.Title) LIKE ? AND K.Flag = 1 ORDER BY K.CreateTime DESC LIMIT ?,?";
                Object[] obj = new Object[]{param, row, pageSize};

                List<Map<String, Object>> knowledgeList = jdbcTemplate.queryForList(sql, obj);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service智能搜索知识仓库列表成功!");
                return knowledgeList;

            } else {
                logger.info("==========>Service智能搜索知识仓库列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service智能搜索知识仓库列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 智能搜索知识仓库列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param param    搜索框参数 --- 不为空
     * @return list
     */
    @Override
    public Integer queryKnowledgeInfoIntelligentCount(UserInfo userInfo, String param) {

        try {
            if (userInfo != null && StringUtils.hasText(param)) {

                param = "%" + param + "%";

                String sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE CONCAT(K.TagStrings, K.Title) LIKE ? AND K.Flag = 1";
                Object[] obj = new Object[]{param};


                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service智能搜索知识仓库列表总条数成功！");
                return count;

            } else {
                logger.info("==========>Service智能搜索知识仓库列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service智能搜索知识仓库列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 标签搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> showKnowledgeByTags(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && StringUtils.hasText(param) && pageIndex != null && pageSize != null) {
                //更新标签搜索数
                updateTagInfo(userInfo, param);

                param = "%" + param + "%";
                Integer row = pageUtil.getRow(pageIndex, pageSize);

                String sql = "SELECT K.KId,K.Title,K.Profile,K.CreateTime,K.TagIds,K.TagStrings,K.BbsFlag FROM F_KNLG_KnowledgeInfo K  WHERE K.TagStrings LIKE ? AND K.Flag = 1 ORDER BY K.CreateTime DESC LIMIT ?,?";
                Object[] obj = new Object[]{param, row, pageSize};

                List<Map<String, Object>> knowledgeList = jdbcTemplate.queryForList(sql, obj);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service标签搜索列表成功!");
                return knowledgeList;

            } else {
                logger.info("==========>Service标签搜索列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service智能搜索知识仓库列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 标签搜索列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @return list
     */
    @Override
    public Integer queryKnowledgeByTagsCount(UserInfo userInfo, String param) {

        try {
            if (userInfo != null && StringUtils.hasText(param)) {

                param = "%" + param + "%";

                String sql = "SELECT COUNT(1) FROM F_KNLG_KnowledgeInfo K  WHERE K.TagStrings LIKE ? AND K.Flag = 1";
                Object[] obj = new Object[]{param};


                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service标签搜索列表总条数成功！");
                return count;

            } else {
                logger.info("==========>Service标签搜索列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service标签搜索列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 最近浏览知识列表
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryKnowledgeInfoRecently(UserInfo userInfo, Integer num) {

        try {
            if (userInfo != null && num != null) {

                String sql = "SELECT K.KId,K.Title,K.TagIds, K.TagStrings,V.ViewTime,K.BbsFlag FROM F_KNLG_KnowledgeInfo K LEFT JOIN F_KNLG_UserViewKnlg V ON K.KId = V.KnowlegdeId WHERE V.UserId = ? AND K.Flag = 1 GROUP BY K.KId ORDER BY V.ViewTime DESC LIMIT 0,?";
                Object[] obj = new Object[]{userInfo.getUserId(), num};

                List<Map<String, Object>> knowledgeList = jdbcTemplate.queryForList(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service最近浏览知识列表成功!");
                return knowledgeList;

            } else {
                logger.info("==========>Service最近浏览知识列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service最近浏览知识列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 公司热点知识列表
     * 规则：社区有用数由高到低
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryKnowledgeInfoHot(UserInfo userInfo, Integer num) {

        try {
            if (userInfo != null && num != null) {

                String sql = "SELECT D.DisId,D.DisType,D.DisRelationId,D.DisTitle,D.UserId,D.DisFlag,D.EssayTypeId,D.TagIds,D.TagStrings,D.KnlgCategoryId,D.KnlgCategoryFlowId,D.KnlgFlag,D.UsefulCount,D.UnUsefulCount,D.CreateTime FROM F_BBS_DiscussionInfo D WHERE D.DisFlag = 1 ORDER BY D.UsefulCount DESC LIMIT 0,?";
                Object[] obj = new Object[]{num};

                List<Map<String, Object>> discussionInfoList = jdbcTemplate.queryForList(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service公司热点知识列表成功!");
                return discussionInfoList;

            } else {
                logger.info("==========>Service公司热点知识列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service公司热点知识列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 智能推荐知识列表
     * 规则：知识仓库浏览量由高到低
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryKnowledgeInfoIntelligently(UserInfo userInfo, Integer num) {

        try {
            if (userInfo != null && num != null) {

                String sql = "SELECT K.KId,K.Title,K.TagIds, K.TagStrings,K.CreateTime,K.ViewCount,K.BbsFlag FROM F_KNLG_KnowledgeInfo K WHERE K.Flag = 1 ORDER BY K.ViewCount DESC LIMIT 0,?";
                Object[] obj = new Object[]{num};

                List<Map<String, Object>> knowledgeList = jdbcTemplate.queryForList(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service智能推荐知识列表成功!");
                return knowledgeList;

            } else {
                logger.info("==========>Service智能推荐知识列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service智能推荐知识列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 录入知识
     *
     * @param userInfo   调用接口用户信息
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param knlgType   知识类型：1.文章，2.非文章
     * @param artContent 文章内容
     * @param tagStrings 标签名(字符串)
     * @param fileList   附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @param repCatId1  一级知识分类
     * @param repCatId2  二级知识分类
     * @param flowId     流程分类
     * @param bbsFlag    是否社区沉淀
     * @return result  -1 : 包含英文逗号，则返回关键字不合法,请用中文逗号分隔
     */
    @Override
    public Integer addKnowledge(UserInfo userInfo, String title, String author, String profile, Integer knlgType,
                                String artContent, String tagStrings, List<Map<String, Object>> fileList, Integer repCatId1,
                                Integer repCatId2, Integer flowId, Integer bbsFlag) {
        try {
            if (userInfo != null && StringUtils.hasText(title) && knlgType != null && profile!=null
                    && StringUtils.hasText(tagStrings) && repCatId1 != null && flowId != null && bbsFlag != null) {
                if (tagStrings.contains("，")) {
                    //若包含中文逗号，则返回关键字不合法,请用英文逗号分隔
                    return -1;
                } else {
                    String[] tagNames = tagStrings.split(",");

                    //判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                    Integer repCatId = repCatId2 == null ? repCatId1 : repCatId2;
                    Integer result;
                    if (knlgType == 2 && fileList != null && fileList.size() > 0) {
                        //1.判断类别，若不是文章，则必须有附件
                        //插入知识及相关表
                        result = saveKnowledgeInfo1(userInfo, title, author, profile,
                                knlgType, tagNames, tagStrings, fileList, repCatId, flowId, bbsFlag);

                    } else if (knlgType == 1 && StringUtils.hasText(artContent) && fileList != null && fileList.size() > 0) {
                        //2.判断类别，若是文章，且带有附件
                        //查询该文章是否存在
                        ArticleInfo articleInfo = queryArticleInfoByArtContent(userInfo, artContent);
                        //插入知识及相关表
                        result = saveKnowledgeInfo2(userInfo, articleInfo, title, author, profile,
                                knlgType, artContent, tagNames, tagStrings, fileList, repCatId, flowId, bbsFlag);

                    } else if (knlgType == 1 && StringUtils.hasText(artContent)) {
                        //3.判断类别，若是文章，且不带有附件
                        //查询该文章是否存在
                        ArticleInfo articleInfo = queryArticleInfoByArtContent(userInfo, artContent);
                        //插入知识及相关表
                        result = saveKnowledgeInfo3(userInfo, articleInfo, title, author, profile,
                                knlgType, artContent, tagNames, tagStrings, repCatId, flowId, bbsFlag);

                    } else {
                        //4.否则，参数不全
                        logger.info("==========>Service新增知识必传参数为空");
                        result = null;
                    }
                    return result;
                }

            } else {
                logger.info("==========>Service新增知识必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service新增知识异常：");
            ep.printStackTrace();
            return null;
        }
    }


    /**
     * 热门标签
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示数量
     * @return tagInfoList
     */
    @Override
    public List<TagInfo> updateTagInfoCounts(UserInfo userInfo, Integer num) {
        try {
            if (userInfo != null && num != null) {

                String sql = "SELECT TagId, TagCounts, TagName FROM F_KNLG_TagInfo ORDER BY TagCounts DESC LIMIT 0,?";

                List<TagInfo> tagInfoList = jdbcTemplate.query(sql, new Object[]{num}, (rs, rowNum) -> {
                    TagInfo ti = new TagInfo();
                    ti.setTagId(rs.getInt("TagId"));
                    ti.setTagCounts(rs.getInt("TagCounts"));
                    ti.setTagName(rs.getString("TagName"));
                    return ti;
                });
                return tagInfoList;

            } else {
                logger.debug("==========>Service搜索热门标签必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service搜索热门标签异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查看知识详情
     *
     * @param userInfo  调用接口用户信息
     * @param kId       知识编号
     * @param viewOrUpd 标志：1，修改时需要点开的详情界面（不计浏览量）；2，浏览时点开的详情界面（计浏览量）
     * @return knowledgeInfo
     */
    @Override
    public Map<String, Object> showKnowledgeInfoDetails(UserInfo userInfo, Integer kId, Integer viewOrUpd) {
        try {
            if (userInfo != null && kId != null && viewOrUpd != null) {
                if (viewOrUpd == 2) {
                    //更新知识浏览数
                    updateKnlgViewCounts(userInfo, kId);
                }
                //查询知识主表map
                String sql = "SELECT K.KId,K.Title,K.Author,K.Profile,K.TagIds,K.TagStrings,K.ViewCount,K.BbsFlag,R.KnlgType,K.RepCatId,K.FlowId FROM F_KNLG_KnowledgeInfo K LEFT JOIN F_KNLG_KnowledgeRel R ON R.KnowledgeId = K.KId WHERE K.KId = ? AND K.Flag = 1 GROUP BY K.KId";
                Map<String, Object> knowledgeInfo = jdbcTemplate.queryForMap(sql, kId);
                if (knowledgeInfo == null || knowledgeInfo.size() == 0) {
                    return null;
                }
                if (1 == (Integer) knowledgeInfo.get("KnlgType")) {
                    //判断该知识为文章类型
                    //查询文章表并拼装map
                    String sql2 = "SELECT A.ArtId,A.ArtContent FROM F_KNLG_ArticleInfo A LEFT JOIN F_KNLG_KnowledgeRel R ON A.ArtId = R.ArtcOrFileId WHERE R.KnlgType = 1 AND R.KnowledgeId = ?";
                    Map<String, Object> articleInfo = jdbcTemplate.queryForMap(sql2, kId);
                    knowledgeInfo.put("ArtId", articleInfo.get("ArtId"));
                    knowledgeInfo.put("ArtContent", articleInfo.get("ArtContent"));
                    //查询文章相应附件
                    List<KnowledgeFileInfo> fList = qureyFileTypeByArtId(userInfo, (Integer) articleInfo.get("ArtId"));
                    List<Map<String, Object>> fileList = new ArrayList<>();
                    if (fList != null && fList.size() > 0) {
                        for (KnowledgeFileInfo fInfo : fList) {
                            //根据文件url获取文件服务器文件信息
                            FileInfo fileInfo = fileBasicService.getFileInfo(fInfo.getFileUrl());
                            Map<String, Object> fileMap = new HashMap<>();
                            fileMap.put("FileId", fInfo.getFileId());
                            fileMap.put("FileUrl", fInfo.getFileUrl());
                            fileMap.put("FileType", fInfo.getFileType());
                            fileMap.put("FileName", fileInfo.getOriginalFileName());
                            fileMap.put("FileSize", fileInfo.getFileSize());
                            fileList.add(fileMap);
                        }
                    }
                    knowledgeInfo.put("FileList", fileList);

                } else if (2 == (Integer) knowledgeInfo.get("KnlgType")) {
                    //判断改知识为文件类型
                    //根据知识类型
                    List<KnowledgeFileInfo> fList = queryFileInfoByKnlgId(userInfo, kId);
                    List<Map<String, Object>> fileList = new ArrayList<>();
                    if (fList != null && fList.size() > 0) {
                        for (KnowledgeFileInfo fInfo : fList) {
                            //根据文件url获取文件服务器文件信息
                            FileInfo fileInfo = fileBasicService.getFileInfo(fInfo.getFileUrl());
                            Map<String, Object> fileMap = new HashMap<>();
                            fileMap.put("FileId", fInfo.getFileId());
                            fileMap.put("FileUrl", fInfo.getFileUrl());
                            fileMap.put("FileType", fInfo.getFileType());
                            fileMap.put("FileName", fileInfo.getOriginalFileName());
                            fileMap.put("FileSize", fileInfo.getFileSize());
                            fileList.add(fileMap);
                        }
                    }
                    knowledgeInfo.put("FileList", fileList);
                }
                //查询一二级分类Id并拼装
                String sql3 = "SELECT RepCatParentId FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";

                Integer repCatParentId;
                try {
                    repCatParentId = jdbcTemplate.queryForObject(sql3, new Object[]{FlagType.Effective.getValue(), knowledgeInfo.get("RepCatId")}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    repCatParentId = null;
                }
                if (repCatParentId != null) {
                    String sql4 = "SELECT RepCatName FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";
                    Integer repId1 = repCatParentId == 0 ? (Integer) knowledgeInfo.get("RepCatId") : repCatParentId;
                    String repId1Name;
                    try {
                        repId1Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId1}, String.class);
                    } catch (EmptyResultDataAccessException e) {
                        repId1Name = null;
                    }

                    Integer repId2 = repCatParentId == 0 ? null : (Integer) knowledgeInfo.get("RepCatId");
                    String repId2Name;
                    try {
                        repId2Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId2}, String.class);
                    } catch (EmptyResultDataAccessException e) {
                        repId2Name = null;
                    }
                    knowledgeInfo.put("RepCatId1", repId1);
                    knowledgeInfo.put("RepCatId1Name", repId1Name);
                    knowledgeInfo.put("RepCatId2", repId2);
                    knowledgeInfo.put("RepCatId2Name", repId2Name);
                }

                return knowledgeInfo;

            } else {
                logger.debug("==========>Service查看知识详情（只读）必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查看知识详情（只读）异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 更改知识
     *
     * @param userInfo   调用接口用户信息
     * @param kId        知识编号
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param knlgType   知识类型：1.文章，2.非文章
     * @param artContent 文章内容
     * @param tagStrings 标签名(字符串),逗号分隔
     * @param repCatId1  一级知识分类
     * @param repCatId2  二级知识分类
     * @param flowId     流程分类
     * @param fileList   附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @return result
     */
    @Override
    public Integer updateKnowledge(UserInfo userInfo, Integer kId, String title, String author, String profile,
                                   Integer knlgType, String artContent, String tagStrings, Integer repCatId1,
                                   Integer repCatId2, Integer flowId, List<Map<String, Object>> fileList) {
        try {
            if (userInfo != null && kId != null && StringUtils.hasText(title) && StringUtils.hasText(profile) && knlgType != null
                    && StringUtils.hasText(tagStrings) && repCatId1 != null && flowId != null) {

                //1.新增标签表，并将标签ID转成字符串
                String[] tagNames = tagStrings.split(",");
                String tagIds = "";
                for (String tagName : tagNames) {
                    TagInfo tagInfo = queryTagInfoByTagName(userInfo, tagName);
                    if (tagInfo == null) {
                        String sql2 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                        jdbcTemplate.update(sql2, tagName);
                        tagInfo = queryTagInfoByTagName(userInfo, tagName);
                    }
                    String tagStr = tagInfo.getTagId() + ",";
                    tagIds += tagStr;
                }
                //去除最后一个“,”
                tagIds = tagIds.substring(0, tagIds.length() - 1);

                //2.判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                Integer repCatId = repCatId2 == null ? repCatId1 : repCatId2;

                //3.更新知识表
                String sql = "UPDATE F_KNLG_KnowledgeInfo SET Title = ?, Author = ?, Profile = ?, RepCatId = ?, FlowId = ?,TagIds = ?,TagStrings = ? WHERE KId = ?";
                Object[] obj = new Object[]{title, author, profile, repCatId, flowId, tagIds, tagStrings, kId};
                jdbcTemplate.update(sql, obj);
                if (knlgType == 1 && StringUtils.hasText(artContent)) {
                    String artIdSql = "SELECT ArtcOrFileId FROM F_KNLG_KnowledgeRel WHERE KnlgType = ? AND KnowledgeId = ?";
                    Integer artId = jdbcTemplate.queryForObject(artIdSql,new Object[]{knlgType,kId},Integer.class);
                    //4.判断类别，若是文章，更改文章内容
                    String sql3 = "UPDATE F_KNLG_ArticleInfo SET ArtContent = ? WHERE ArtId = ?";
                    jdbcTemplate.update(sql3, artContent, artId);
                    //删除文章与原附件关联
                    String sql4 = "DELETE FROM F_KNLG_FileArtRel WHERE ArtId = ?";
                    jdbcTemplate.update(sql4, artId);
                    if (fileList != null && fileList.size() > 0) {
                        //删除新增的url的原文件
                        String sql5 = "DELETE FROM F_KNLG_KnowledgeFileInfo WHERE FileUrl = ?";
                        jdbcTemplate.batchUpdate(sql5, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setString(1, fileList.get(i).get("FileUrl").toString());
                            }

                            public int getBatchSize() {
                                return fileList.size();
                            }
                        });
                        //批量插入新文件
                        String sql6 = "INSERT INTO F_KNLG_KnowledgeFileInfo(FileUrl,FileType) VALUES (?,?)";
                        jdbcTemplate.batchUpdate(sql6, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setString(1, fileList.get(i).get("FileUrl").toString());
                                ps.setInt(2, (Integer) fileList.get(i).get("FileType"));
                            }

                            public int getBatchSize() {
                                return fileList.size();
                            }
                        });

                        //批量插入文章文件关联表
                        String sql7 = "INSERT INTO F_KNLG_FileArtRel(FileId,ArtId) VALUES (?,?)";
                        for (Map<String, Object> map : fileList) {
                            KnowledgeFileInfo fileInfo = queryFileInfoByFileUrl(userInfo, map.get("FileUrl").toString());
                            jdbcTemplate.update(sql7, fileInfo.getFileId(), artId);
                        }
                    }

                } else if (knlgType == 2 && fileList != null && fileList.size() > 0) {
                    //删除知识与原附件关联
                    String sql8 = "DELETE FROM F_KNLG_KnowledgeRel WHERE KnlgType = 2 AND KnowledgeId = ?";
                    jdbcTemplate.update(sql8, kId);
                    //批量删除原有文件
                    String sql9 = "DELETE FROM F_KNLG_KnowledgeFileInfo WHERE FileUrl = ?";
                    jdbcTemplate.batchUpdate(sql9, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, fileList.get(i).get("FileUrl").toString());
                        }

                        public int getBatchSize() {
                            return fileList.size();
                        }
                    });
                    //批量插入文件
                    String sql10 = "INSERT INTO F_KNLG_KnowledgeFileInfo(FileUrl,FileType) VALUES (?,?)";
                    jdbcTemplate.batchUpdate(sql10, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, fileList.get(i).get("FileUrl").toString());
                            ps.setInt(2, (Integer) fileList.get(i).get("FileType"));
                        }

                        public int getBatchSize() {
                            return fileList.size();
                        }
                    });
                    //插入知识关联表
                    for (Map<String, Object> map : fileList) {
                        KnowledgeFileInfo fileInfo = queryFileInfoByFileUrl(userInfo, map.get("FileUrl").toString());
                        String sql11 = "INSERT INTO F_KNLG_KnowledgeRel(KnowledgeId,KnlgType,ArtcOrFileId) VALUES (?,?,?)";
                        jdbcTemplate.update(sql11, kId, knlgType, fileInfo.getFileId());
                    }

                } else {
                    logger.debug("参数不合法或参数为空！");
                    return null;
                }

                return 1;

            } else {
                logger.info("==========>Service修改知识必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service修改知识异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 删除知识
     *
     * @param userInfo 调用接口用户信息
     * @param kId      知识编号
     * @return 1:成功；其他：失败
     */
    @Override
    public Integer deleteKnowledge(UserInfo userInfo, Integer kId) {
        try {
            if (userInfo != null && kId != null) {
                String searchSql = "SELECT R.KnlgType FROM F_KNLG_KnowledgeInfo K LEFT JOIN F_KNLG_KnowledgeRel R ON K.KId = R.KnowledgeId WHERE K.KId = ? GROUP BY K.KId";
                Integer knlgType;
                try {
                    knlgType = jdbcTemplate.queryForObject(searchSql, new Object[]{kId}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    knlgType = null;
                }
                if (knlgType == null) {
                    throw new Exception("知识编号为" + kId + "的知识类型查询为空！");
                }
                String artSql = "SELECT ArtcOrFileId FROM F_KNLG_KnowledgeRel WHERE KnlgType = ? AND KnowledgeId = ?";
                Integer artId;
                try {
                    artId = jdbcTemplate.queryForObject(artSql, new Object[]{knlgType, kId}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    artId = null;
                }
                if (artId == null) {
                    throw new Exception("知识编号为" + kId + "的知识所关联的文章为空！");
                }
                //1.删除知识
                String sql = "UPDATE F_KNLG_KnowledgeInfo SET Flag = 2 WHERE KId = ?";
                jdbcTemplate.update(sql, kId);
                //2.删除知识关联
                String sql1 = "DELETE FROM F_KNLG_KnowledgeRel WHERE KnowledgeId = ?";
                jdbcTemplate.update(sql1, kId);

                if (knlgType == KnowledgeType.Article.getValue()) {
                    //3.删除文章
                    String sql2 = "DELETE FROM F_KNLG_ArticleInfo WHERE ArtId = ?";
                    jdbcTemplate.update(sql2, artId);
                    //4.删除文章附件关联
                    String sql3 = "DELETE FROM F_KNLG_FileArtRel WHERE ArtId = ?";
                    jdbcTemplate.update(sql3, artId);
                }
                return 1;

            } else {
                logger.debug("==========>Service删除知识必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除知识异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 插入知识表并返回该条主键
     *
     * @param userInfo   调用接口用户信息
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param tagStrings 标签名（字符串）
     * @param repCatId   知识分类
     * @param flowId     流程分类
     * @return kId 知识表新增结果的主键
     */
    sactional
    public Integer saveKnowledge(UserInfo userInfo, String title, String author, String profile, String tagIds,
                                 String tagStrings, Integer repCatId, Integer flowId, Integer bbsFlag) throws Exception {

        Date day = new Date();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO F_KNLG_KnowledgeInfo(Title,Author,Profile,ViewCount,RepCatId,FlowId,TagIds,TagStrings,UserId,CreateTime,Flag,BbsFlag) VALUES (?,?,?,0,?,?,?,?,?,?,1,?)";
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                        int i = 0;
                        java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(++i, title);
                        ps.setString(++i, author);
                        ps.setString(++i, profile);
                        ps.setInt(++i, repCatId);
                        ps.setInt(++i, flowId);
                        ps.setString(++i, tagIds);
                        ps.setString(++i, tagStrings);
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setObject(++i, day);
                        ps.setInt(++i, bbsFlag);
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();

    }

    /**
     * 非文章类型有附件状态下 保存知识表及相关文件表、标签表
     *
     * @param userInfo   调用接口用户信息
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param knlgType   知识类型：1.文章，2.非文章
     * @param tagNames   标签名（数组）
     * @param tagStrings 标签名（字符串）
     * @param fileList   附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @param repCatId   知识分类
     * @param flowId     流程分类
     * @return result 知识表新增结果
     */
    @Transactional
    public Integer saveKnowledgeInfo1(UserInfo userInfo, String title, String author, String profile,
                                      Integer knlgType, String[] tagNames, String tagStrings, List<Map<String, Object>> fileList,
                                      Integer repCatId, Integer flowId, Integer bbsFlag) throws Exception {

        //1.新增文件表
        //批量删除原有文件
        String sql1 = "DELETE FROM F_KNLG_KnowledgeFileInfo WHERE FileUrl = ?";
        jdbcTemplate.batchUpdate(sql1, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, fileList.get(i).get("FileUrl").toString());
            }

            public int getBatchSize() {
                return fileList.size();
            }
        });
        //批量插入文件
        String sql4 = "INSERT INTO F_KNLG_KnowledgeFileInfo(FileUrl,FileType) VALUES (?,?)";
        jdbcTemplate.batchUpdate(sql4, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, fileList.get(i).get("FileUrl").toString());
                ps.setInt(2, (Integer) fileList.get(i).get("FileType"));
            }

            public int getBatchSize() {
                return fileList.size();
            }
        });


        //2.新增标签表，并将标签ID转成字符串
        String tagIds = "";
        for (String tagName : tagNames) {
            TagInfo tagInfo = queryTagInfoByTagName(userInfo, tagName);
            if (tagInfo == null) {
                String sql2 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                jdbcTemplate.update(sql2, tagName);
                tagInfo = queryTagInfoByTagName(userInfo, tagName);
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


        //3.新增知识表并获取知识主键
        Integer knlgId = saveKnowledge(userInfo, title, author, profile, tagIds, tagStrings, repCatId, flowId, bbsFlag);

        //4.插入知识关联表
        for (Map<String, Object> map : fileList) {
            KnowledgeFileInfo fileInfo = queryFileInfoByFileUrl(userInfo, map.get("FileUrl").toString());
            String sql5 = "INSERT INTO F_KNLG_KnowledgeRel(KnowledgeId,KnlgType,ArtcOrFileId) VALUES (?,?,?)";
            jdbcTemplate.update(sql5, knlgId, knlgType, fileInfo.getFileId());
        }


        return 1;

    }

    /**
     * 文章类型有附件状态下 保存知识表及相关文件表、文章表、标签表
     *
     * @param userInfo    调用接口用户信息
     * @param articleInfo 文章信息
     * @param title       知识标题
     * @param author      知识作者 - 可为空
     * @param profile     知识简介
     * @param knlgType    知识类型：1.文章，2.非文章
     * @param artContent  文章内容
     * @param tagNames    标签名（数组）
     * @param tagStrings  标签名（字符串）
     * @param fileList    附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @param repCatId    知识分类
     * @param flowId      流程分类
     * @return result 知识表新增结果
     */
    @Transactional
    public Integer saveKnowledgeInfo2(UserInfo userInfo, ArticleInfo articleInfo, String title, String author,
                                      String profile, Integer knlgType, String artContent, String[] tagNames, String tagStrings,
                                      List<Map<String, Object>> fileList, Integer repCatId, Integer flowId, Integer bbsFlag) throws Exception {

        //1.新增文件表
        //批量删除原有文件
        String sql1 = "DELETE FROM F_KNLG_KnowledgeFileInfo WHERE FileUrl = ?";
        jdbcTemplate.batchUpdate(sql1, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, fileList.get(i).get("FileUrl").toString());
            }

            public int getBatchSize() {
                return fileList.size();
            }
        });
        //批量插入文件
        String sql6 = "INSERT INTO F_KNLG_KnowledgeFileInfo(FileUrl,FileType) VALUES (?,?)";
        jdbcTemplate.batchUpdate(sql6, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, fileList.get(i).get("FileUrl").toString());
                ps.setInt(2, (Integer) fileList.get(i).get("FileType"));
            }

            public int getBatchSize() {
                return fileList.size();
            }
        });

        //2.新增文章表
        if (articleInfo == null) {
            String sql2 = "INSERT INTO F_KNLG_ArticleInfo(ArtContent) VALUES (?)";
            jdbcTemplate.update(sql2, artContent);
            articleInfo = queryArticleInfoByArtContent(userInfo, artContent);
        }

        //3.新增文件文章关联表
        //批量删除原有文件
        String sql7 = "DELETE FROM F_KNLG_FileArtRel WHERE ArtId = ?";
        jdbcTemplate.update(sql7, articleInfo.getArtId());
        //批量插入文章文件关联表
        String sql3 = "INSERT INTO F_KNLG_FileArtRel(FileId,ArtId) VALUES (?,?)";
        for (Map<String, Object> map : fileList) {
            KnowledgeFileInfo fileInfo = queryFileInfoByFileUrl(userInfo, map.get("FileUrl").toString());
            jdbcTemplate.update(sql3, fileInfo.getFileId(), articleInfo.getArtId());
        }

        //4.新增标签表，并将标签ID转成字符串
        String tagIds = "";
        for (String tagName : tagNames) {
            TagInfo tagInfo = queryTagInfoByTagName(userInfo, tagName);
            if (tagInfo == null) {
                String sql4 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                jdbcTemplate.update(sql4, tagName);
                tagInfo = queryTagInfoByTagName(userInfo, tagName);
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

        //5.新增知识表并获取知识主键
        Integer knlgId = saveKnowledge(userInfo, title, author, profile, tagIds, tagStrings, repCatId, flowId, bbsFlag);

        //6.插入知识关联表
        String sql8 = "INSERT INTO F_KNLG_KnowledgeRel(KnowledgeId,KnlgType,ArtcOrFileId) VALUES (?,?,?)";
        jdbcTemplate.update(sql8, knlgId, knlgType, articleInfo.getArtId());


        return 1;

    }

    /**
     * 文章类型无附件状态下 保存知识表及相关文章表、标签表
     *
     * @param userInfo    调用接口用户信息
     * @param articleInfo 文章信息
     * @param title       知识标题
     * @param author      知识作者 - 可为空
     * @param profile     知识简介
     * @param knlgType    知识类型：1.文章，2.非文章
     * @param artContent  文章内容
     * @param tagNames    标签名（数组）
     * @param tagStrings  标签名（字符串）
     * @param repCatId    知识分类
     * @param flowId      流程分类
     * @return result 知识表新增结果
     */
    @Transactional
    public Integer saveKnowledgeInfo3(UserInfo userInfo, ArticleInfo articleInfo, String title, String author, String profile,
                                      Integer knlgType, String artContent, String[] tagNames, String tagStrings, Integer repCatId,
                                      Integer flowId, Integer bbsFlag) throws Exception {

        //1.新增文章表
        if (articleInfo == null) {
            String sql1 = "INSERT INTO F_KNLG_ArticleInfo(ArtContent) VALUES (?)";
            jdbcTemplate.update(sql1, artContent);
            articleInfo = queryArticleInfoByArtContent(userInfo, artContent);
        }

        //2.新增标签表，并将标签ID转成字符串
        String tagIds = "";
        for (String tagName : tagNames) {
            TagInfo tagInfo = queryTagInfoByTagName(userInfo, tagName);
            if (tagInfo == null) {
                String sql2 = "INSERT INTO F_KNLG_TagInfo(TagName,TagType,TagCounts) VALUES (?,1,0)";
                jdbcTemplate.update(sql2, tagName);
                tagInfo = queryTagInfoByTagName(userInfo, tagName);
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

        //3.新增知识表并获取知识主键
        Integer knlgId = saveKnowledge(userInfo, title, author, profile, tagIds, tagStrings, repCatId, flowId, bbsFlag);

        //4.新增知识关联表
        String sql4 = "INSERT INTO F_KNLG_KnowledgeRel(KnowledgeId,KnlgType,ArtcOrFileId) VALUES (?,?,?)";
        jdbcTemplate.update(sql4, knlgId, knlgType, articleInfo.getArtId());

        return 1;

    }

    /**
     * 根据文件Url查询本地文件信息
     *
     * @param userInfo 调用接口用户信息
     * @param fileUrl  文件路径
     * @return fileInfo
     */
    public KnowledgeFileInfo queryFileInfoByFileUrl(UserInfo userInfo, String fileUrl) {
        try {
            if (userInfo != null && StringUtils.hasText(fileUrl)) {

                String sql = "SELECT FileId, FileUrl, FileType FROM F_KNLG_KnowledgeFileInfo WHERE FileUrl = ?";
                try {
                    KnowledgeFileInfo fileInfo = jdbcTemplate.queryForObject(sql, new Object[]{fileUrl}, (rs, rowNum) -> {
                        KnowledgeFileInfo file = new KnowledgeFileInfo();
                        file.setFileId(rs.getInt("FileId"));
                        file.setFileUrl(rs.getString("FileUrl"));
                        file.setFileType(rs.getInt("FileType"));
                        return file;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据文件Url查询有效文件信息详情成功!");
                    return fileInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据文件Url查询有效文件信息详情接口查询结果为空！");
                    return null;
                }

            } else {
                logger.info("==========>Service根据文件url查询有效文件信息详情必传参数为空！");
                throw new Exception("===异常==========>Service根据文件url查询有效文件信息详情必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Servic根据文件url查询有效文件信息详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据知识Id查询本地文件信息
     *
     * @param userInfo 调用接口用户信息
     * @param knlgId   知识Id
     * @return list
     */
    public List<KnowledgeFileInfo> queryFileInfoByKnlgId(UserInfo userInfo, Integer knlgId) {
        try {
            if (userInfo != null && knlgId != null) {

                String sql = "SELECT F.FileId,F.FileType,F.FileUrl FROM F_KNLG_KnowledgeFileInfo F LEFT JOIN F_KNLG_KnowledgeRel R ON F.FileId = R.ArtcOrFileId WHERE R.KnlgType = 2 AND R.KnowledgeId = ?";
                try {
                    List<KnowledgeFileInfo> list = jdbcTemplate.query(sql, new Object[]{knlgId}, (rs, rowNum) -> {
                        KnowledgeFileInfo file = new KnowledgeFileInfo();
                        file.setFileId(rs.getInt("FileId"));
                        file.setFileUrl(rs.getString("FileUrl"));
                        file.setFileType(rs.getInt("FileType"));
                        return file;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据知识Id查询有效文件信息详情成功!");
                    return list;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据知识Id查询有效文件信息详情接口查询结果为空！");
                    return null;
                }

            } else {
                logger.info("==========>Service根据知识Id查询有效文件信息详情必传参数为空！");
                throw new Exception("===异常==========>Service根据知识Id查询有效文件信息详情必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Servic根据知识Id查询有效文件信息详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据标签名/关键字查询标签信息
     *
     * @param userInfo 调用接口用户信息
     * @param tagName  标签名/关键字
     * @return tagInfo
     */
    @Override
    public TagInfo queryTagInfoByTagName(UserInfo userInfo, String tagName) {
        try {
            if (userInfo != null && StringUtils.hasText(tagName)) {

                String sql = "SELECT TagId, TagName, TagType FROM F_KNLG_TagInfo WHERE TagName = ?";
                try {
                    TagInfo tagInfo = jdbcTemplate.queryForObject(sql, new Object[]{tagName}, (rs, rowNum) -> {
                        TagInfo tag = new TagInfo();
                        tag.setTagId(rs.getInt("TagId"));
                        tag.setTagName(rs.getString("TagName"));
                        tag.setTagType(rs.getInt("TagType"));
                        return tag;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据标签名/关键字查询标签详情成功!");
                    return tagInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据标签名/关键字查询标签详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service根据标签名/关键字查询标签详情必传参数为空！");
                throw new Exception("===异常==========>Service根据标签名/关键字查询标签详情必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Servic根据标签名/关键字查询标签详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据文章内容查询文章是否存在
     *
     * @param userInfo   调用接口用户信息
     * @param artContent 文章内容
     * @return fileInfo
     */
    public ArticleInfo queryArticleInfoByArtContent(UserInfo userInfo, String artContent) {
        try {
            if (userInfo != null && StringUtils.hasText(artContent)) {

                String sql = "SELECT ArtId, ArtContent FROM F_KNLG_ArticleInfo WHERE ArtContent = ? ";
                try {
                    ArticleInfo articleInfo = jdbcTemplate.queryForObject(sql, new Object[]{artContent}, (rs, rowNum) -> {
                        ArticleInfo article = new ArticleInfo();
                        article.setArtId(rs.getInt("ArtId"));
                        article.setArtContent(rs.getString("ArtContent"));
                        return article;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据文章内容查询文章成功!");
                    return articleInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据文章内容查询文章情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service根据文章内容查询文章必传参数为空！");
                throw new Exception("===异常==========>Service根据文章内容查询文章必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Servic根据文章内容查询文章异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据文章Id查询附件类型
     *
     * @param userInfo 调用接口用户信息
     * @param artId    文章编号
     * @return map
     */
    public List<KnowledgeFileInfo> qureyFileTypeByArtId(UserInfo userInfo, Integer artId) {
        try {
            if (userInfo != null && artId != null) {

                String sql = "SELECT F.FileId,F.FileUrl,F.FileType From F_KNLG_ArticleInfo A LEFT JOIN F_KNLG_FileArtRel R ON A.ArtId = R.ArtId  LEFT JOIN F_KNLG_KnowledgeFileInfo F ON F.FileId = R.FileId WHERE A.ArtId = ?";
                try {
                    List<KnowledgeFileInfo> list = jdbcTemplate.query(sql, new Object[]{artId}, (rs, rowNum) -> {
                        KnowledgeFileInfo fi = new KnowledgeFileInfo();
                        fi.setFileId(rs.getInt("FileId"));
                        fi.setFileUrl(rs.getString("FileUrl"));
                        fi.setFileType(rs.getInt("FileType"));
                        return fi;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据文章Id查询附件类型成功!");
                    return list;
                } catch (EmptyResultDataAccessException e) {
                    return null;
                }

            } else {
                throw new Exception("==========>Service根据文章Id查询附件类型必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service根据文章Id查询附件类型异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 更新标签的搜索数
     *
     * @param userInfo 调用接口用户信息
     * @param tagName  标签名
     * @return result
     */
    public Integer updateTagInfo(UserInfo userInfo, String tagName) {
        try {
            if (userInfo != null && StringUtils.hasText(tagName)) {
                Integer result;
                tagName = "%" + tagName + "%";

                String sql1 = "SELECT TagId, TagCounts FROM F_KNLG_TagInfo WHERE TagName LIKE ?";

                List<TagInfo> tagInfo = jdbcTemplate.query(sql1, new Object[]{tagName}, (rs, rowNum) -> {
                    TagInfo ti = new TagInfo();
                    ti.setTagId(rs.getInt("TagId"));
                    ti.setTagCounts(rs.getInt("TagCounts"));
                    return ti;
                });

                if (tagInfo != null) {
                    String sql2 = "UPDATE F_KNLG_TagInfo SET TagCounts = ? WHERE TagId = ?";

                    //循环增加这些标签的搜索数
                    int[] updateCounts = jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, (tagInfo.get(i).getTagCounts() + 1));
                            ps.setInt(2, tagInfo.get(i).getTagId());
                        }

                        public int getBatchSize() {
                            return tagInfo.size();
                        }
                    });

                    result = updateCounts.length;
                } else {
                    result = 0;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service更新标签的搜索数成功!");
                return result;

            } else {
                throw new Exception("==========>Service更新标签的搜索数必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service更新标签的搜索数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 更新知识的浏览数
     *
     * @param userInfo 调用接口用户信息
     * @param kId      知识编号
     * @return result
     */
    @Transactional
    public void updateKnlgViewCounts(UserInfo userInfo, Integer kId) throws Exception {
        if (userInfo != null && kId != null) {
            //1.获取该知识原有的浏览数
            String sql1 = "SELECT ViewCount FROM F_KNLG_KnowledgeInfo WHERE KId = ?";

            Map<String, Object> map = jdbcTemplate.queryForMap(sql1, kId);

            //浏览数+1
            Integer viewCount = (Integer) map.get("ViewCount");
            viewCount++;
            //2.更新知识浏览数
            String sql2 = "UPDATE F_KNLG_KnowledgeInfo SET ViewCount = ? WHERE KId = ?";
            jdbcTemplate.update(sql2, viewCount, kId);

            //3.插入用户知识浏览表
            String sql4 = "DELETE FROM F_KNLG_UserViewKnlg WHERE UserId = ? AND KnowlegdeId = ?";
            jdbcTemplate.update(sql4, userInfo.getUserId(), kId);
            String sql3 = "INSERT INTO F_KNLG_UserViewKnlg(UserId,KnowlegdeId) VALUES (?,?)";
            jdbcTemplate.update(sql3, userInfo.getUserId(), kId);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service更新知识的浏览数成功!");


        } else {
            throw new Exception("==========>Service更新知识的浏览数必传参数为空！");
        }

    }

    /**
     * 相关知识推荐
     * 规则：相同知识库分类下，浏览数最多的知识
     *
     * @param userInfo 调用接口用户信息
     * @param kId      知识Id
     * @param num      最大展示条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryRelatedKnowledge(UserInfo userInfo, Integer kId, Integer num) {

        try {
            if (userInfo != null && kId != null && num != null) {
                //查询知识主表map
                String sql = "SELECT K.RepCatId FROM F_KNLG_KnowledgeInfo K WHERE K.KId = ? AND K.Flag = 1";
                Integer knlgCategoryId = jdbcTemplate.queryForObject(sql, new Object[]{kId}, Integer.class);

                //查询知识主表map
                String sql1 = "SELECT K.KId,K.Title,K.Author,K.CreateTime FROM F_KNLG_KnowledgeInfo K WHERE K.RepCatId = ? AND K.Flag = 1 ORDER BY K.ViewCount DESC LIMIT 0,?";
                List<Map<String, Object>> allList = jdbcTemplate.queryForList(sql1, knlgCategoryId, num);

                List<Map<String, Object>> list = new ArrayList<>();

                if (allList != null && allList.size() > 0) {
                    for (Map<String, Object> map : allList) {
                            if (!kId.equals(map.get("KId"))) {
                                list.add(map);
                            }
                    }

                }

                return list;

            } else {
                logger.info("==========>Service查询相关知识推荐列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询相关知识推荐列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


}
