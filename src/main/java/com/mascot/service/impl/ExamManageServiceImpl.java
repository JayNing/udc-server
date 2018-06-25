package com.mascot.service.impl;

import com.interfaces.mascot.ExCategoryService;
import com.interfaces.mascot.ExamManageService;
import com.interfaces.mascot.MessageManageService;
import com.interfaces.mascot.RepositoryCategoryService;
import com.thrift.common.body.MessageModelConstants;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.*;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 考试管理接口实现
 *
 * @author zhangmengyu
 * 2018/5/16
 */
@Service(value = "examManageService")
public class ExamManageServiceImpl extends BasicServiceImpl implements ExamManageService {

    private final static Log logger = LogFactory.getLog(ExamManageServiceImpl.class);

    @Autowired
    RepositoryCategoryService repositoryCategoryService;

    @Autowired
    ExCategoryService exCategoryService;

    @Autowired
    MessageManageService messageManageService;

    //=================================================题目管理=======================================================================================

    /**
     * 添加题目
     *
     * @param userInfo          调用接口用户信息
     * @param type              题目类型，1单选2多选3判断4简答
     * @param excsTitle         题目内容
     * @param repCatId1         知识库一级分类
     * @param repCatId2         知识库二级分类
     * @param optionList        选项列表 OptionCode 选项名；OptionDesc 选项值；CrrctAns 是否正确答案
     * @param currAnswerContent 参考答案
     * @return result 1:成功 -1：标题重复
     */
    @Override
    public Integer addExercise(UserInfo userInfo, Integer type, String excsTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> optionList, String currAnswerContent) {

        try {
            if (userInfo == null || type == null || !StringUtils.hasText(excsTitle) || repCatId1 == null) {
                logger.info("==========>Service添加题目必传参数为空!");
                return null;
            }

            //校验标题唯一性
            String sqlTitle = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Flag = ? AND ExcsTitle = ?";
            Map<String, Object> exercisesInfo;
            try {
                exercisesInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), excsTitle);
            } catch (EmptyResultDataAccessException e) {
                exercisesInfo = null;
            }
            if (exercisesInfo != null) {
                //标题重复
                return -1;
            }
            //判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
            Integer categoryId = repCatId2 == null ? repCatId1 : repCatId2;

            if ((type == ExercisesType.SingleSelection.getValue() || type == ExercisesType.MultipleSelection.getValue() || type == ExercisesType.Judgment.getValue())
                    && optionList != null && optionList.size() > 0) {
                //如果是选择题或判断题，要有选项列表
                String str = "";
                for (Map<String, Object> option : optionList) {
                    Integer isCorrect = option.get("CrrctAns") == null ? 0 : Integer.valueOf(option.get("CrrctAns").toString());
                    if (isCorrect.equals(JudFlag.Y.getValue())) {
                        //若是正确答案
                        str = str + option.get("OptionCode").toString() + ",";
                    }
                }
                if (StringUtils.hasText(str)) {
                    currAnswerContent = str.substring(0, str.length() - 1);
                    String[] correctAs = currAnswerContent.split(",");
                    Arrays.sort(correctAs);
                    currAnswerContent = org.apache.commons.lang.StringUtils.join(correctAs);
                }

                Integer exerciseId = saveExercise(userInfo, type, excsTitle, categoryId, currAnswerContent);
                //保存选项表
                String sql = "INSERT INTO F_EX_OptionsInfo(ExerciseId,OptionCode,OptionDesc,CrrctAns,Flag) VALUES (?,?,?,?,?)";

                //批量插入关联表
                jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, exerciseId);
                        ps.setString(2, optionList.get(i).get("OptionCode").toString());
                        ps.setString(3, optionList.get(i).get("OptionDesc").toString());
                        ps.setInt(4, Integer.valueOf(optionList.get(i).get("CrrctAns").toString()));
                        ps.setInt(5, FlagType.Effective.getValue());
                    }

                    public int getBatchSize() {
                        return optionList.size();
                    }
                });

            } else if (type == ExercisesType.ShortAnswer.getValue() && StringUtils.hasText(currAnswerContent)) {
                //如果是简答题，要有参考答案
                saveExercise(userInfo, type, excsTitle, categoryId, currAnswerContent);
            } else {
                logger.info("==========>Service添加题目必传参数为空!");
                return null;
            }

            return 1;


        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service添加题目异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 插入题目表并返回该条主键
     *
     * @param userInfo          调用接口用户信息
     * @param type              题目类型
     * @param excsTitle         题目
     * @param categoryId        知识仓库分类
     * @param currAnswerContent 参考答案
     * @return
     */
    private Integer saveExercise(UserInfo userInfo, Integer type, String excsTitle, Integer categoryId, String currAnswerContent) throws Exception {

        Date day = new Date();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO F_EX_ExercisesInfo(Type,ExcsTitle,CreateTime,CreateUserId,LastEditUserId,Flag,CategoryId,CurrAnswerContent) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                        int i = 0;
                        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(++i, type);
                        ps.setString(++i, excsTitle);
                        ps.setObject(++i, day);
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setInt(++i, FlagType.Effective.getValue());
                        ps.setInt(++i, categoryId);
                        ps.setString(++i, currAnswerContent);
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();

    }

    /**
     * 查询题目详情
     *
     * @param userInfo    调用接口用户信息
     * @param exercisesId 题目Id
     * @return exercisesInfo
     */
    @Override
    public Map<String, Object> queryExerciseDetail(UserInfo userInfo, Integer exercisesId) {

        try {
            if (userInfo != null && exercisesId != null) {

                String sql = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE ExercisesId = ?";
                Map<String, Object> exercisesInfo;
                try {
                    exercisesInfo = jdbcTemplate.queryForObject(sql, new Object[]{exercisesId}, (rs, rowNum) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ExercisesId", rs.getInt("ExercisesId"));
                        map.put("Type", rs.getInt("Type"));
                        map.put("ExcsTitle", rs.getString("ExcsTitle"));
                        map.put("CreateTime", rs.getString("CreateTime"));
                        map.put("CreateUserId", rs.getInt("CreateUserId"));
                        map.put("CurrAnswerContent", rs.getString("CurrAnswerContent"));
                        map.put("CategoryId", rs.getInt("CategoryId"));
                        return map;
                    });
                } catch (EmptyResultDataAccessException e) {
                    exercisesInfo = null;
                }

                if (exercisesInfo != null && (Integer.valueOf(exercisesInfo.get("Type").toString()).equals(ExercisesType.SingleSelection.getValue()) || Integer.valueOf(exercisesInfo.get("Type").toString()).equals(ExercisesType.MultipleSelection.getValue()) || Integer.valueOf(exercisesInfo.get("Type").toString()).equals(ExercisesType.Judgment.getValue()))) {
                    //如果是选择题或判断题，要有选项列表
                    String sql2 = "SELECT OptionId,OptionCode,OptionDesc,CrrctAns FROM F_EX_OptionsInfo WHERE Flag = ? AND ExerciseId = ? ORDER BY OptionCode";
                    List<Map<String, Object>> optionList = jdbcTemplate.query(sql2, new Object[]{FlagType.Effective.getValue(), exercisesId}, (rs, rowNum) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("OptionId", rs.getInt("OptionId"));
                        map.put("OptionCode", rs.getString("OptionCode"));
                        map.put("OptionDesc", rs.getString("OptionDesc"));
                        map.put("CrrctAns", rs.getInt("CrrctAns"));
                        return map;
                    });
                    exercisesInfo.put("OptionList", optionList);

                }

                if (exercisesInfo != null) {
                    //查询提问者姓名并拼装
                    List<Integer> userIdList = new ArrayList<>();
                    userIdList.add((Integer) exercisesInfo.get("CreateUserId"));
                    List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                    exercisesInfo.put("CreateUserName", userInfos.get(0).getRealName());

                    //查询一二级分类Id并拼装
                    String sql3 = "SELECT RepCatParentId FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";

                    Integer repCatParentId;
                    try {
                        repCatParentId = jdbcTemplate.queryForObject(sql3, new Object[]{FlagType.Effective.getValue(), exercisesInfo.get("CategoryId")}, Integer.class);
                    } catch (EmptyResultDataAccessException e) {
                        repCatParentId = null;
                    }
                    if (repCatParentId != null) {
                        String sql4 = "SELECT RepCatName FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";
                        Integer repId1 = repCatParentId == 0 ? (Integer) exercisesInfo.get("CategoryId") : repCatParentId;
                        String repId1Name;
                        try {
                            repId1Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId1}, String.class);
                        } catch (EmptyResultDataAccessException e) {
                            repId1Name = null;
                        }

                        Integer repId2 = repCatParentId == 0 ? null : (Integer) exercisesInfo.get("CategoryId");
                        String repId2Name;
                        try {
                            repId2Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId2}, String.class);
                        } catch (EmptyResultDataAccessException e) {
                            repId2Name = null;
                        }
                        exercisesInfo.put("RepCatId1", repId1);
                        exercisesInfo.put("RepCatId1Name", repId1Name);
                        exercisesInfo.put("RepCatId2", repId2);
                        exercisesInfo.put("RepCatId2Name", repId2Name);
                    }
                }

                return exercisesInfo;


            } else {
                logger.info("==========>Service查询题目详情必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询题目详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 修改题目
     *
     * @param userInfo          调用接口用户信息
     * @param exercisesId       题号
     * @param type              题目类型，1选择2判断3简答  ---  不可更改
     * @param excsTitle         题目内容
     * @param repCatId1         知识库一级分类
     * @param repCatId2         知识库二级分类
     * @param optionList        选项列表 OptionCode 选项名；OptionDesc 选项值；CrrctAns 是否正确答案；
     * @param currAnswerContent 参考答案
     * @return result 1:成功;-1：标题重复
     */
    @Override
    public Integer updateExercise(UserInfo userInfo, Integer exercisesId, Integer type, String excsTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> optionList, String currAnswerContent) {

        try {
            if (userInfo != null && exercisesId != null && type != null && StringUtils.hasText(excsTitle) && repCatId1 != null) {
                //校验标题唯一性
                String sqlTitle = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Flag = ? AND ExcsTitle = ?";
                Map<String, Object> exercisesInfo;
                try {
                    exercisesInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), excsTitle);
                } catch (EmptyResultDataAccessException e) {
                    exercisesInfo = null;
                }
                if (exercisesInfo != null && !exercisesId.equals(exercisesInfo.get("ExercisesId"))) {
                    //标题重复
                    return -1;
                }

                //判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                Integer categoryId = repCatId2 == null ? repCatId1 : repCatId2;

                if ((type == ExercisesType.SingleSelection.getValue() || type == ExercisesType.MultipleSelection.getValue() || type == ExercisesType.Judgment.getValue())
                        && optionList != null && optionList.size() > 0) {
                    //如果是选择题或判断题，要有选项列表
                    String str = "";
                    for (Map<String, Object> option : optionList) {
                        Integer isCorrect = option.get("CrrctAns") == null ? 0 : Integer.valueOf(option.get("CrrctAns").toString());
                        if (isCorrect.equals(JudFlag.Y.getValue())) {
                            //若是正确答案
                            //若是正确答案
                            str = str + option.get("OptionCode").toString() + ",";
                        }
                    }
                    if (StringUtils.hasText(str)) {
                        currAnswerContent = str.substring(0, str.length() - 1);
                        String[] correctAs = currAnswerContent.split(",");
                        Arrays.sort(correctAs);
                        currAnswerContent = org.apache.commons.lang.StringUtils.join(correctAs);
                    }

                    String sql = "UPDATE F_EX_ExercisesInfo SET ExcsTitle = ?,LastEditUserId = ? ,CategoryId = ?,CurrAnswerContent = ? WHERE ExercisesId = ?";
                    jdbcTemplate.update(sql, excsTitle, userInfo.getUserId(), categoryId, currAnswerContent, exercisesId);

                    //删除原有选项表
                    String sql2 = "DELETE FROM F_EX_OptionsInfo WHERE ExerciseId = ?";
                    jdbcTemplate.update(sql2, exercisesId);
                    //保存选项表
                    String sql3 = "INSERT INTO F_EX_OptionsInfo(ExerciseId,OptionCode,OptionDesc,CrrctAns,Flag) VALUES (?,?,?,?,?)";
                    //批量插入角色模块关联表
                    jdbcTemplate.batchUpdate(sql3, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, exercisesId);
                            ps.setString(2, optionList.get(i).get("OptionCode").toString());
                            ps.setString(3, optionList.get(i).get("OptionDesc").toString());
                            ps.setInt(4, Integer.valueOf(optionList.get(i).get("CrrctAns").toString()));
                            ps.setInt(5, FlagType.Effective.getValue());
                        }

                        public int getBatchSize() {
                            return optionList.size();
                        }
                    });

                } else if (type == ExercisesType.ShortAnswer.getValue() && StringUtils.hasText(currAnswerContent)) {
                    //如果是简答题，要有参考答案
                    String sql = "UPDATE F_EX_ExercisesInfo SET ExcsTitle = ?,LastEditUserId = ? ,CategoryId = ?,CurrAnswerContent = ? WHERE ExercisesId = ?";
                    jdbcTemplate.update(sql, excsTitle, userInfo.getUserId(), categoryId, currAnswerContent, exercisesId);
                } else {
                    logger.info("==========>Service修改题目必传参数为空!");
                    return null;
                }

                return 1;

            } else {
                logger.info("==========>Service修改题目必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service修改题目异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 删除题目
     *
     * @param userInfo    调用接口用户信息
     * @param exercisesId 题号
     * @return result 1:成功
     */
    @Override
    public Integer deleteExercise(UserInfo userInfo, Integer exercisesId) {

        try {
            if (userInfo != null && exercisesId != null) {
                String sql1 = "DELETE FROM F_EX_OptionsInfo WHERE ExerciseId = ?";
                jdbcTemplate.update(sql1, exercisesId);

                String sql = "UPDATE F_EX_ExercisesInfo SET Flag = ? WHERE ExercisesId = ?";
                jdbcTemplate.update(sql, FlagType.Ineffective.getValue(), exercisesId);

                return 1;

            } else {
                logger.info("==========>Service删除题目必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除题目异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询试题管理列表
     *
     * @param userInfo   调用接口用户信息
     * @param exeType    题目类型 1单选2多选3判断4简答
     * @param title      标题
     * @param repCatId1  一级分类
     * @param repCatId2  二级分类
     * @param createName 创建人
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryExerciseList(UserInfo userInfo, Integer exeType, String title, Integer repCatId1, Integer repCatId2, String createName,
                                                       Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null && exeType != null) {
                List<Map<String, Object>> list;
                List<Map<String, Object>> allList = new ArrayList<>();
                Map<String, Object> paramMap = new HashMap<>();

                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                List<Integer> cgIdList = new ArrayList<>();
                if (repCatId1 != null && repCatId2 != null) {
                    cgIdList.add(repCatId2);
                } else if (repCatId1 != null) {
                    cgIdList = repositoryCategoryService.getAllRepositoryCategoryByParentId(userInfo, repCatId1);
                    cgIdList.add(repCatId1);
                }


                //2.确定是否传入参数title和创建人姓名
                if (StringUtils.hasText(createName)) {
                    //组装姓名搜索参数
                    List<String> realNameList = new ArrayList<>();
                    realNameList.add(createName);
                    //根据姓名查询用户中心的用户列表
                    String sql1 = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                    Integer pSize = jdbcTemplate.queryForObject(sql1, Integer.class);
                    List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);

                    if (listUsCenter == null || listUsCenter.size() == 0) {
                        return null;
                    }
                    List<Integer> userIdList = new ArrayList<>();
                    for (UserInfo usInfo : listUsCenter) {
                        userIdList.add(usInfo.getUserId());
                    }
                    if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateUserId IN (:param) AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("paramLike", paramLike);
                        paramMap.put("cgIdList", cgIdList);
                    } else if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateUserId IN (:param) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("paramLike", paramLike);
                    } else if (cgIdList.size() > 0) {
                        sql = "SELECT Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateUserId IN (:param) AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("cgIdList", cgIdList);
                    } else {
                        sql = "SELECT Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateUserId IN (:param) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                    }
                    paramMap.put("param", userIdList);

                } else {
                    if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("paramLike", paramLike);
                        paramMap.put("cgIdList", cgIdList);
                    } else if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("paramLike", paramLike);
                    } else if (cgIdList.size() > 0) {
                        sql = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                        paramMap.put("cgIdList", cgIdList);
                    } else {
                        sql = "SELECT ExercisesId,Type,ExcsTitle,CreateTime,CreateUserId,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                    }
                }
                paramMap.put("exeTyp", exeType);
                paramMap.put("flag", FlagType.Effective.getValue());
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);
                paramMap.put("row", row);
                paramMap.put("pageSize", pageSize);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                list = jdbc.query(sql, paramMap, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ExercisesId", rs.getInt("ExercisesId"));
                    map.put("Type", rs.getInt("Type"));
                    map.put("ExcsTitle", rs.getString("ExcsTitle"));
                    map.put("CreateTime", rs.getString("CreateTime"));
                    map.put("CreateUserId", rs.getInt("CreateUserId"));
                    map.put("CurrAnswerContent", rs.getString("CurrAnswerContent"));
                    map.put("CategoryId", rs.getInt("CategoryId"));
                    return map;
                });

                if (list != null && list.size() > 0) {
                    List<Integer> usIdList = new ArrayList<>();
                    //将用户姓名组装到list中
                    for (Map<String, Object> map : list) {
                        usIdList.add((Integer) map.get("CreateUserId"));
                    }
                    List<UserInfo> usCenter = userInfoService.getUserListByUserIds(usIdList);
                    for (Map<String, Object> map : list) {
                        for (UserInfo usInfo : usCenter) {
                            if (usInfo.getUserId() == (Integer) map.get("CreateUserId")) {

                                //查询一二级分类Id并拼装
                                String sql3 = "SELECT RepCatParentId FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";

                                Integer repCatParentId;
                                try {
                                    repCatParentId = jdbcTemplate.queryForObject(sql3, new Object[]{FlagType.Effective.getValue(), map.get("CategoryId")}, Integer.class);
                                } catch (EmptyResultDataAccessException e) {
                                    repCatParentId = null;
                                }
                                if (repCatParentId != null) {
                                    String sql4 = "SELECT RepCatName FROM F_KNLG_RepositoryCategory WHERE Flag = ? AND RepCatId = ?";
                                    Integer repId1 = repCatParentId == 0 ? (Integer) map.get("CategoryId") : repCatParentId;
                                    String repId1Name;
                                    try {
                                        repId1Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId1}, String.class);
                                    } catch (EmptyResultDataAccessException e) {
                                        repId1Name = null;
                                    }

                                    Integer repId2 = repCatParentId == 0 ? null : (Integer) map.get("CategoryId");
                                    String repId2Name;
                                    try {
                                        repId2Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), repId2}, String.class);
                                    } catch (EmptyResultDataAccessException e) {
                                        repId2Name = null;
                                    }
                                    map.put("RepCatId1", repId1);
                                    map.put("RepCatId1Name", repId1Name);
                                    map.put("RepCatId2", repId2);
                                    map.put("RepCatId2Name", repId2Name);
                                }


                                map.put("CreateName", usInfo.getRealName());
                                allList.add(map);
                            }
                        }
                    }

                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询试题管理列表成功!");
                return allList;

            } else {
                logger.info("==========>Service查询试题管理列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询试题管理列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询试题管理列表总条数
     *
     * @param userInfo   调用接口用户信息
     * @param exeType    题目类型
     * @param title      标题
     * @param repCatId1  一级分类
     * @param repCatId2  二级分类
     * @param createName 创建人
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @return list
     */
    @Override
    public Integer queryExerciseListCount(UserInfo userInfo, Integer exeType, String title, Integer repCatId1, Integer repCatId2, String createName,
                                          Date startTime, Date endTime) {

        try {
            if (userInfo != null) {
                Integer count;
                Map<String, Object> paramMap = new HashMap<>();
                String sql;
                List<Integer> cgIdList = new ArrayList<>();
                if (repCatId1 != null && repCatId2 != null) {
                    cgIdList.add(repCatId2);
                } else if (repCatId1 != null) {
                    cgIdList = repositoryCategoryService.getAllRepositoryCategoryByParentId(userInfo, repCatId1);
                    cgIdList.add(repCatId1);
                }


                //2.确定是否传入参数title和创建人姓名
                if (StringUtils.hasText(createName)) {
                    //组装姓名搜索参数
                    List<String> realNameList = new ArrayList<>();
                    realNameList.add(createName);
                    //根据姓名查询用户中心的用户列表
                    String sql1 = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                    Integer pSize = jdbcTemplate.queryForObject(sql1, Integer.class);
                    List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);

                    if (listUsCenter == null || listUsCenter.size() == 0) {
                        return 0;
                    }
                    List<Integer> userIdList = new ArrayList<>();
                    for (UserInfo usInfo : listUsCenter) {
                        userIdList.add(usInfo.getUserId());
                    }

                    if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateUserId IN (:param) AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("paramLike", paramLike);
                        paramMap.put("cgIdList", cgIdList);
                    } else if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateUserId IN (:param) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("paramLike", paramLike);
                    } else if (cgIdList.size() > 0) {
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateUserId IN (:param) AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("cgIdList", cgIdList);
                    } else {
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateUserId IN (:param) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                    }
                    paramMap.put("param", userIdList);

                } else {
                    if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("paramLike", paramLike);
                        paramMap.put("cgIdList", cgIdList);
                    } else if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND ExcsTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("paramLike", paramLike);
                    } else if (cgIdList.size() > 0) {
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        paramMap.put("cgIdList", cgIdList);
                    } else {
                        sql = "SELECT COUNT(1) FROM F_EX_ExercisesInfo WHERE Type = :exeTyp AND Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                    }

                }
                paramMap.put("exeTyp", exeType);
                paramMap.put("flag", FlagType.Effective.getValue());
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                count = jdbc.queryForObject(sql, paramMap, Integer.class);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询试题管理列表成功!");
                return count;

            } else {
                logger.info("==========>Service查询试题管理列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询试题管理列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


    //=================================================试卷管理=======================================================================================


    /**
     * 添加试卷
     *
     * @param userInfo   调用接口用户信息
     * @param paperTitle 题目内容
     * @param repCatId1  试卷一级分类
     * @param repCatId2  试卷二级分类
     * @param exerList   题目列表 参数1：ExerType 题目模块（题目类型）；参数2：ExerTypeScore 每题分值；参数3：List<Map<String, Object>> ExerInfoList该类型下的题号列表--参数3.1：ExerId 题目编号 --参数3.2：Sort 题目位置（正序）
     * @return result 1:成功; -1:传入的模块下试题列表参数为空或异常；-2:分值不是100; -3:标题重复
     */
    @Override
    public Integer addPaper(UserInfo userInfo, String paperTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> exerList) {

        try {
            if (userInfo == null || !StringUtils.hasText(paperTitle) || repCatId1 == null || exerList == null || exerList.size() == 0) {
                logger.info("==========>Service添加试卷必传参数为空!");
                return null;
            }

            //校验标题唯一性
            String sqlTitle = "SELECT PaperId,PaperTitle,CreateTime,CreateUserId,CategoryId FROM F_EX_PaperInfo WHERE Flag = ? AND PaperTitle = ?";
            Map<String, Object> paperInfo;
            try {
                paperInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), paperTitle);
            } catch (EmptyResultDataAccessException e) {
                paperInfo = null;
            }
            if (paperInfo != null) {
                //标题重复
                return -3;
            }

            //判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
            Integer categoryId = repCatId2 == null ? repCatId1 : repCatId2;

            //判断试卷分值是否是100分
            Integer paperTotalScore = 0;
            for (Map<String, Object> m : exerList) {
                //模块每题分值
                Integer exerTypeScore = m.get("ExerTypeScore") == null ? 0 : (Integer) m.get("ExerTypeScore");
                //模块题数
                List<Map<String, Object>> exerIdList;
                try {
                    exerIdList = (ArrayList<Map<String, Object>>) m.get("ExerInfoList");
                } catch (ClassCastException e) {
                    //传入的模块下试题列表参数为空或异常；
                    return -1;
                }

                Integer exerCount = exerIdList == null ? 0 : exerIdList.size();
                paperTotalScore += exerCount * exerTypeScore;
            }

            if (!paperTotalScore.equals(100)) {
                //分值不是100
                return -2;
            } else {
                //插入试卷表
                Integer paperId = savePaper(userInfo, paperTitle, categoryId);
                //删除试卷题目关联表原关联
                String sql1 = "DELETE FROM F_EX_PaperExcsRel WHERE PaperId = ?";
                jdbcTemplate.update(sql1, paperId);
                //按模块分批量插入关联表
                String sql2 = "INSERT INTO F_EX_PaperExcsRel(PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp) VALUES (?,?,?,?,?)";
                for (Map<String, Object> m : exerList) {
                    //模块每题分值
                    Integer exerTypeScore = (Integer) m.get("ExerTypeScore");
                    //模块题目列表
                    List<Map<String, Object>> exerIdList = (ArrayList<Map<String, Object>>) m.get("ExerInfoList");
                    jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, paperId);
                            ps.setInt(2, (Integer) exerIdList.get(i).get("ExerId"));
                            ps.setInt(3, exerTypeScore);
                            ps.setInt(4, (Integer) exerIdList.get(i).get("Sort"));
                            ps.setInt(5, (Integer) m.get("ExerType"));
                        }

                        public int getBatchSize() {
                            return exerIdList.size();
                        }
                    });

                }
                return 1;
            }


        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service添加试卷异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 插入试卷表并返回该条主键
     *
     * @param userInfo   调用接口用户信息
     * @param paperTitle 题目
     * @param categoryId 分类
     * @return
     */
    private Integer savePaper(UserInfo userInfo, String paperTitle, Integer categoryId) throws Exception {

        Date day = new Date();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO F_EX_PaperInfo(PaperTitle,CreateTime,CreateUserId,LastEditUserId,Flag,CategoryId) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                        int i = 0;
                        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(++i, paperTitle);
                        ps.setObject(++i, day);
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setInt(++i, userInfo.getUserId());
                        ps.setInt(++i, FlagType.Effective.getValue());
                        ps.setInt(++i, categoryId);
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().intValue();

    }

    /**
     * 查询试卷详情
     *
     * @param userInfo 调用接口用户信息
     * @param paperId  试卷Id
     * @return paperInfo
     */
    @Override
    public Map<String, Object> queryPaperDetail(UserInfo userInfo, Integer paperId) {

        try {
            if (userInfo != null && paperId != null) {

                String sql = "SELECT PaperId,PaperTitle,CreateTime,CreateUserId,Flag,CategoryId FROM F_EX_PaperInfo WHERE PaperId = ?";
                Map<String, Object> paperInfo;
                try {
                    paperInfo = jdbcTemplate.queryForObject(sql, new Object[]{paperId}, (rs, rowNum) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("PaperId", rs.getInt("PaperId"));
                        map.put("Flag", rs.getInt("Flag"));
                        map.put("PaperTitle", rs.getString("PaperTitle"));
                        map.put("CreateTime", rs.getString("CreateTime"));
                        map.put("CreateUserId", rs.getInt("CreateUserId"));
                        map.put("CategoryId", rs.getInt("CategoryId"));
                        return map;
                    });
                } catch (EmptyResultDataAccessException e) {
                    paperInfo = null;
                }
                if (paperInfo != null) {
                    List<Map<String, Object>> exerList = new ArrayList<>();

                    String sqlStr = "SELECT PkId,ExercisesId,ExcsScore,Sort,ModuleTyp FROM F_EX_PaperExcsRel WHERE PaperId = ? AND ModuleTyp = ?";

                    //循环枚举类
                    for (ExercisesType e : ExercisesType.values()) {
                        List<Map<String, Object>> exerIfList = jdbcTemplate.query(sqlStr, new Object[]{paperId, e.getValue()}, (rs, rowNum) -> {
                            Map<String, Object> m = new HashMap<>();
                            m.put("PkId", rs.getInt("PkId"));
                            m.put("ExercisesId", rs.getInt("ExercisesId"));
                            m.put("Sort", rs.getInt("Sort"));
                            m.put("ExcsScore", rs.getInt("ExcsScore"));
                            m.put("ModuleTyp", rs.getInt("ModuleTyp"));
                            return m;
                        });
                        List<Map<String, Object>> exerInfoList = new ArrayList<>();
                        if (exerIfList != null && exerIfList.size() > 0) {
                            Map<String, Object> map = new HashMap<>();
                            for (Map<String, Object> m : exerIfList) {
                                Map<String, Object> exer = queryExerciseDetail(userInfo, (Integer) m.get("ExercisesId"));
                                exer.put("Sort", m.get("Sort"));
                                exerInfoList.add(exer);
                            }
                            map.put("ExerType", e.getValue());
                            map.put("ExerTypeScore", exerIfList.get(0).get("ExcsScore"));
                            map.put("ExerInfoList", exerInfoList);

                            exerList.add(map);
                        }


                    }
                    paperInfo.put("ExerList", exerList);
                }

                return paperInfo;


            } else {
                logger.info("==========>Service查询试卷详情必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询试卷详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 修改试卷
     *
     * @param userInfo   调用接口用户信息
     * @param paperId    试卷Id
     * @param paperTitle 题目内容
     * @param repCatId1  试卷一级分类
     * @param repCatId2  试卷二级分类
     * @param exerList   题目列表 参数1：ExerType 题目模块（题目类型）；参数2：ExerTypeScore 每题分值；参数3：List<Map<String, Object>> ExerInfoList该类型下的题号列表--参数3.1：ExerId 题目编号 --参数3.2：Sort 题目位置（正序）
     * @return result 1:成功; -1:传入的模块下试题列表参数为空或异常；-2:分值不是100; -3:标题重复
     */
    @Override
    public Integer updatePaper(UserInfo userInfo, Integer paperId, String paperTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> exerList) {
        try {
            if (userInfo != null && StringUtils.hasText(paperTitle) && repCatId1 != null && exerList != null && exerList.size() > 0) {

                //校验标题唯一性
                String sqlTitle = "SELECT PaperId,PaperTitle,CreateTime,CreateUserId,CategoryId FROM F_EX_PaperInfo WHERE Flag = ? AND PaperTitle = ?";
                Map<String, Object> paperInfo;
                try {
                    paperInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), paperTitle);
                } catch (EmptyResultDataAccessException e) {
                    paperInfo = null;
                }
                if (paperInfo != null && !paperId.equals(paperInfo.get("PaperId"))) {
                    //标题重复
                    return -3;
                }

                //判断知识分类，若无二级分类则选一级分类，若有二级分类则选二级分类
                Integer categoryId = repCatId2 == null ? repCatId1 : repCatId2;

                //判断试卷分值是否是100分
                Integer paperTotalScore = 0;
                for (Map<String, Object> m : exerList) {
                    //模块每题分值
                    Integer exerTypeScore = m.get("ExerTypeScore") == null ? 0 : (Integer) m.get("ExerTypeScore");
                    //模块题数
                    List<Map<String, Object>> exerIdList;
                    try {
                        exerIdList = (ArrayList<Map<String, Object>>) m.get("ExerInfoList");
                    } catch (ClassCastException e) {
                        //传入的模块下试题列表参数为空或异常；
                        return -1;
                    }

                    Integer exerCount = exerIdList == null ? 0 : exerIdList.size();
                    paperTotalScore += exerCount * exerTypeScore;
                }

                if (!paperTotalScore.equals(100)) {
                    //分值不是100
                    return -2;
                } else {
                    //修改试卷表
                    String sql = "UPDATE F_EX_PaperInfo SET PaperTitle = ?,LastEditUserId = ?,CategoryId = ? WHERE PaperId = ?";
                    jdbcTemplate.update(sql, paperTitle, userInfo.getUserId(), categoryId, paperId);
                    //删除试卷题目关联表原关联
                    String sql1 = "DELETE FROM F_EX_PaperExcsRel WHERE PaperId = ?";
                    jdbcTemplate.update(sql1, paperId);
                    //按模块分批量插入角色模块关联表
                    String sql2 = "INSERT INTO F_EX_PaperExcsRel(PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp) VALUES (?,?,?,?,?)";
                    for (Map<String, Object> m : exerList) {
                        //模块每题分值
                        Integer exerTypeScore = (Integer) m.get("ExerTypeScore");
                        //模块题目列表
                        List<Map<String, Object>> exerIdList = (ArrayList<Map<String, Object>>) m.get("ExerInfoList");
                        jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1, paperId);
                                ps.setInt(2, (Integer) exerIdList.get(i).get("ExerId"));
                                ps.setInt(3, exerTypeScore);
                                ps.setInt(4, (Integer) exerIdList.get(i).get("Sort"));
                                ps.setInt(5, (Integer) m.get("ExerType"));
                            }

                            public int getBatchSize() {
                                return exerIdList.size();
                            }
                        });

                    }
                    return 1;
                }

            } else {
                logger.info("==========>Service修改试卷必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service修改试卷异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 删除试卷
     *
     * @param userInfo 调用接口用户信息
     * @param paperId  试卷号
     * @return result 1:成功
     */
    @Override
    public Integer deletePaper(UserInfo userInfo, Integer paperId) {

        try {
            if (userInfo != null && paperId != null) {
                String sql1 = "DELETE FROM F_EX_PaperExcsRel WHERE PaperId = ?";
                jdbcTemplate.update(sql1, paperId);

                String sql = "UPDATE F_EX_PaperInfo SET Flag = ? WHERE PaperId = ?";
                jdbcTemplate.update(sql, FlagType.Ineffective.getValue(), paperId);

                return 1;

            } else {
                logger.info("==========>Service删除试卷必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除试卷异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询试卷管理列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryPaperList(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                List<Map<String, Object>> list;
                List<Map<String, Object>> allList = new ArrayList<>();
                Map<String, Object> paramMap = new HashMap<>();

                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                List<Integer> cgIdList = new ArrayList<>();
                if (repCatId1 != null && repCatId2 != null) {
                    cgIdList.add(repCatId2);
                } else if (repCatId1 != null) {
                    cgIdList = exCategoryService.getAllExCategoryByParentId(userInfo, repCatId1);
                    cgIdList.add(repCatId1);
                }
                if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                    String paramLike = "%" + title + "%";
                    sql = "SELECT PaperId,PaperTitle,CreateTime,CategoryId FROM F_EX_PaperInfo WHERE Flag = :flag AND PaperTitle LIKE :paramLike AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                    paramMap.put("paramLike", paramLike);
                    paramMap.put("cgIdList", cgIdList);
                } else if (StringUtils.hasText(title)) {
                    String paramLike = "%" + title + "%";
                    sql = "SELECT PaperId,PaperTitle,CreateTime,CategoryId FROM F_EX_PaperInfo WHERE Flag = :flag AND PaperTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                    paramMap.put("paramLike", paramLike);
                } else if (cgIdList.size() > 0) {
                    sql = "SELECT PaperId,PaperTitle,CreateTime,CategoryId FROM F_EX_PaperInfo WHERE Flag = :flag AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                    paramMap.put("cgIdList", cgIdList);
                } else {
                    sql = "SELECT PaperId,PaperTitle,CreateTime,CategoryId FROM F_EX_PaperInfo WHERE Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                }
                paramMap.put("flag", FlagType.Effective.getValue());
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);
                paramMap.put("row", row);
                paramMap.put("pageSize", pageSize);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                list = jdbc.queryForList(sql, paramMap);

                if (list != null && list.size() > 0) {

                    for (Map<String, Object> map : list) {
                        //查询一二级分类Id并拼装
                        String sql3 = "SELECT ParentId FROM F_EX_ExCategory WHERE Flag = ? AND CategoryId = ?";

                        Integer parentId;
                        try {
                            parentId = jdbcTemplate.queryForObject(sql3, new Object[]{FlagType.Effective.getValue(), map.get("CategoryId")}, Integer.class);
                        } catch (EmptyResultDataAccessException e) {
                            parentId = null;
                        }
                        if (parentId != null) {
                            String sql4 = "SELECT CategoryName FROM F_EX_ExCategory WHERE Flag = ? AND CategoryId = ?";
                            Integer exCatId1 = parentId == 0 ? (Integer) map.get("CategoryId") : parentId;
                            String exCatId1Name;
                            try {
                                exCatId1Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), exCatId1}, String.class);
                            } catch (EmptyResultDataAccessException e) {
                                exCatId1Name = null;
                            }

                            Integer exCatId2 = parentId == 0 ? null : (Integer) map.get("CategoryId");
                            String exCatId2Name;
                            try {
                                exCatId2Name = jdbcTemplate.queryForObject(sql4, new Object[]{FlagType.Effective.getValue(), exCatId2}, String.class);
                            } catch (EmptyResultDataAccessException e) {
                                exCatId2Name = null;
                            }
                            map.put("ExCatId1", exCatId1);
                            map.put("ExCatId1Name", exCatId1Name);
                            map.put("ExCatId2", exCatId2);
                            map.put("ExCatId2Name", exCatId2Name);
                        }
                        allList.add(map);
                    }


                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询试卷管理列表成功!");
                return allList;
            } else {
                logger.info("==========>Service查询试卷管理列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询试卷管理列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询试卷管理列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return list
     */
    @Override
    public Integer queryPaperListCount(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Date startTime, Date endTime) {

        try {
            if (userInfo != null) {
                Integer count;
                Map<String, Object> paramMap = new HashMap<>();
                String sql;
                List<Integer> cgIdList = new ArrayList<>();
                if (repCatId1 != null && repCatId2 != null) {
                    cgIdList.add(repCatId2);
                } else if (repCatId1 != null) {
                    cgIdList = exCategoryService.getAllExCategoryByParentId(userInfo, repCatId1);
                    cgIdList.add(repCatId1);
                }


                if (StringUtils.hasText(title) && cgIdList.size() > 0) {
                    String paramLike = "%" + title + "%";
                    sql = "SELECT COUNT(1) FROM F_EX_PaperInfo WHERE Flag = :flag AND PaperTitle LIKE :paramLike AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                    paramMap.put("paramLike", paramLike);
                    paramMap.put("cgIdList", cgIdList);
                } else if (StringUtils.hasText(title)) {
                    String paramLike = "%" + title + "%";
                    sql = "SELECT COUNT(1) FROM F_EX_PaperInfo WHERE Flag = :flag AND PaperTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                    paramMap.put("paramLike", paramLike);
                } else if (cgIdList.size() > 0) {
                    sql = "SELECT COUNT(1) FROM F_EX_PaperInfo WHERE Flag = :flag AND CategoryId IN (:cgIdList) AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                    paramMap.put("cgIdList", cgIdList);
                } else {
                    sql = "SELECT COUNT(1) FROM F_EX_PaperInfo WHERE Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                }
                paramMap.put("flag", FlagType.Effective.getValue());
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                count = jdbc.queryForObject(sql, paramMap, Integer.class);


                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询试卷管理列表总条数成功!");
                return count;

            } else {
                logger.info("==========>Service查询试卷管理列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询试卷管理列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    //=================================================考试管理=======================================================================================

    /**
     * 发起考试
     *
     * @param userInfo     调用接口用户信息
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param time         考试时长（分钟）
     * @param examTitle    考试标题
     * @param userIdList   参试人员
     * @param paperId      模板试卷编号
     * @param readerIdList 阅卷人员
     * @return result 1:成功；其他失败； -1：标题重复
     */
    @Override
    public Integer addExam(UserInfo userInfo, Date startTime, Date endTime, Integer time, String examTitle, List<Integer> userIdList,
                           Integer paperId, List<Integer> readerIdList) {

        try {
            if (userInfo == null || startTime == null || endTime == null || time == null || userIdList == null
                    || userIdList.size() == 0 || paperId == null || readerIdList == null || readerIdList.size() == 0 || !StringUtils.hasText(examTitle)) {
                logger.info("==========>Service发起考试必传参数为空!");
                return null;
            }

            Map<String, Object> paperModule = queryPaperDetail(userInfo, paperId);
            if (paperModule == null || paperModule.get("ExerList") == null) {
                throw new Exception("试卷模板详情查询失败！");
            }

            //校验标题唯一性
            String sqlTitle = "SELECT ExamId,ExamTitle FROM F_EX_ExamInfo WHERE Flag = ? AND ExamTitle = ?";
            Map<String, Object> examInfo;
            try {
                examInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), examTitle);
            } catch (EmptyResultDataAccessException e) {
                examInfo = null;
            }
            if (examInfo != null) {
                //标题重复
                return -1;
            }

            logger.info("======================================创建考试相关实例开始================================================");

            //创建试卷实例表
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "INSERT INTO F_EX_PaperInstance(PaperTitle,CategoryId) VALUES (?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            int i = 0;
                            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(++i, paperModule.get("PaperTitle").toString());
                            ps.setInt(++i, (Integer) paperModule.get("CategoryId"));
                            return ps;
                        }
                    },
                    keyHolder);

            Integer newPaperId = keyHolder.getKey().intValue();

            //试卷下模块列表
            List<Map<String, Object>> exerList = (ArrayList<Map<String, Object>>) paperModule.get("ExerList");
            if (exerList == null) {
                throw new Exception("试卷模块列表为空！");
            }
            for (Map<String, Object> exerMap : exerList) {
                //试卷模块下试题列表
                List<Map<String, Object>> exerInfoList = (ArrayList<Map<String, Object>>) exerMap.get("ExerInfoList");
                if (exerInfoList == null) {
                    throw new Exception("试卷模块下试题列表为空！");
                }

                List<Map<String, Integer>> newExerciseList = new ArrayList<>();

                for (Map<String, Object> exerInfoMap : exerInfoList) {

                    //创建题目实例表
                    KeyHolder keyHolder2 = new GeneratedKeyHolder();
                    String exerSql = "INSERT INTO F_EX_ExercisesInstance(ExType,ExcsTitle,CategoryId,CurrAnswerContent) VALUES (?,?,?,?)";
                    jdbcTemplate.update(
                            new PreparedStatementCreator() {
                                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                    int i = 0;
                                    PreparedStatement ps = conn.prepareStatement(exerSql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setInt(++i, (Integer) exerInfoMap.get("Type"));
                                    ps.setString(++i, exerInfoMap.get("ExcsTitle").toString());
                                    ps.setInt(++i, (Integer) exerInfoMap.get("CategoryId"));
                                    ps.setString(++i, exerInfoMap.get("CurrAnswerContent").toString());
                                    return ps;
                                }
                            },
                            keyHolder2);

                    Integer newExerciseId = keyHolder2.getKey().intValue();

                    Map<String, Integer> newExercise = new HashMap<>();
                    newExercise.put("NewExerciseId", newExerciseId);
                    newExercise.put("Sort", (Integer) exerInfoMap.get("Sort"));
                    newExerciseList.add(newExercise);
                    //若有选项列表
                    if (exerInfoMap.get("OptionList") != null) {
                        List<Map<String, Object>> optionList = (ArrayList<Map<String, Object>>) exerInfoMap.get("OptionList");
                        //创建选项实例表
                        String opSql = "INSERT INTO F_EX_OptionsInstance(ExerciseId,OptionCode,OptionDesc,CrrctAns) VALUES (?,?,?,?)";

                        //批量插入关联表
                        jdbcTemplate.batchUpdate(opSql, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1, newExerciseId);
                                ps.setString(2, optionList.get(i).get("OptionCode").toString());
                                ps.setString(3, optionList.get(i).get("OptionDesc").toString());
                                ps.setInt(4, Integer.valueOf(optionList.get(i).get("CrrctAns").toString()));
                            }

                            public int getBatchSize() {
                                return optionList.size();
                            }
                        });

                    }
                }

                //删除试卷题目关联表原关联
                String deleteSql = "DELETE FROM F_EX_PaperExcsRelInstance WHERE PaperId = ? AND ModuleTyp = ?";
                jdbcTemplate.update(deleteSql, paperId, exerMap.get("ExerType"));
                //按模块分批量插入关联表
                String addSql = "INSERT INTO F_EX_PaperExcsRelInstance(PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp) VALUES (?,?,?,?,?)";

                //模块题目列表
                jdbcTemplate.batchUpdate(addSql, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, newPaperId);
                        ps.setInt(2, newExerciseList.get(i).get("NewExerciseId"));
                        ps.setInt(3, (Integer) exerMap.get("ExerTypeScore"));
                        ps.setInt(4, newExerciseList.get(i).get("Sort"));
                        ps.setInt(5, (Integer) exerMap.get("ExerType"));
                    }

                    public int getBatchSize() {
                        return newExerciseList.size();
                    }
                });
            }

            logger.info("======================================创建考试相关实例结束================================================");

            //新增考试表
            KeyHolder keyHolder3 = new GeneratedKeyHolder();
            String addExamSql = "INSERT INTO F_EX_ExamInfo(ExamTitle,StartTime,EndTime,Time,AssmStatus,AssmContent,AssmUserId,AssmTime,OrganiserId,CreateTime,Flag) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            int i = 0;
                            PreparedStatement ps = conn.prepareStatement(addExamSql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(++i, examTitle);
                            ps.setObject(++i, startTime);
                            ps.setObject(++i, endTime);
                            ps.setInt(++i, time);
                            ps.setInt(++i, JudFlag.N.getValue());
                            ps.setString(++i, null);
                            ps.setString(++i, null);
                            ps.setDate(++i, null);
                            ps.setInt(++i, userInfo.getUserId());
                            ps.setObject(++i, new Date());
                            ps.setInt(++i, FlagType.Effective.getValue());
                            return ps;
                        }
                    },
                    keyHolder3);

            Integer examId = keyHolder3.getKey().intValue();


            //新增考试与试卷实例关联表
            String deleteRelSql = "DELETE FROM F_EX_ExamPaperRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteRelSql, examId);
            String addRelSql = "INSERT INTO F_EX_ExamPaperRel(ExamId,PaperId) VALUES (?,?)";
            jdbcTemplate.update(addRelSql, examId, newPaperId);
            //新增考试参与人表
            String deleteExamUserSql = "DELETE FROM F_EX_ExamUserRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteExamUserSql, examId);
            String addExamUserSql = "INSERT INTO F_EX_ExamUserRel(UserId,ExamId,CreateTime,ExScore,ExamStatus,ReaderStatus) VALUES (?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(addExamUserSql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, userIdList.get(i));
                    ps.setInt(2, examId);
                    ps.setObject(3, new Date());
                    ps.setObject(4, null);
                    ps.setInt(5, ExamStatus.DNS.getValue());
                    ps.setInt(6, JudFlag.N.getValue());
                }

                public int getBatchSize() {
                    return userIdList.size();
                }
            });

            //新增阅卷人表
            String deleteExamReaderSql = "DELETE FROM F_EX_ExamReaderRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteExamReaderSql, examId);
            String addExamReaderSql = "INSERT INTO F_EX_ExamReaderRel(UserId,ExamId,CreateTime) VALUES (?,?,?)";
            jdbcTemplate.batchUpdate(addExamReaderSql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, readerIdList.get(i));
                    ps.setInt(2, examId);
                    ps.setObject(3, new Date());
                }

                public int getBatchSize() {
                    return readerIdList.size();
                }
            });

            //生成考试邀请短信
            String exNotice = MessageModelConstants.EXAM_NOTICE;
            exNotice = exNotice.replace("【：考试】", examTitle);
            messageManageService.addMessage(userInfo, MascotMessageType.ExamNotice.getValue(), null, exNotice, userIdList);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service发起考试成功！");
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service发起考试异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除考试
     *
     * @param userInfo 调用接口用户信息
     * @param examId   试卷号
     * @return result 1:成功
     */
    @Override
    public Integer deleteExam(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service删除考试必传参数为空!");
                return null;
            }
            String titleSql = "SELECT ExamTitle FROM F_EX_ExamInfo WHERE ExamId = ?";
            String examTitle = jdbcTemplate.queryForObject(titleSql, new Object[]{examId}, String.class);
            String paperSql = "SELECT PaperId FROM F_EX_ExamPaperRel WHERE ExamId = ?";
            Integer paperId = jdbcTemplate.queryForObject(paperSql, new Object[]{examId}, Integer.class);
            String exerciseSql = "SELECT ExercisesId FROM F_EX_PaperExcsRelInstance WHERE PaperId = ?";
            List<Integer> exerciseIdList = jdbcTemplate.queryForList(exerciseSql, new Object[]{paperId}, Integer.class);
            String userIdSql = "SELECT UserId FROM F_EX_ExamUserRel WHERE ExamId = ?";
            List<Integer> userIdList = jdbcTemplate.queryForList(userIdSql, new Object[]{examId}, Integer.class);
            String readerIdSql = "SELECT UserId FROM F_EX_ExamReaderRel WHERE ExamId = ?";
            List<Integer> readerIdList = jdbcTemplate.queryForList(readerIdSql, new Object[]{examId}, Integer.class);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("exercisesId", exerciseIdList);
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);

            String deletePaperInstanceSql = "DELETE FROM F_EX_PaperInstance WHERE PaperId = ?";
            jdbcTemplate.update(deletePaperInstanceSql, paperId);

            String deleteExercisesInstanceSql = "DELETE FROM F_EX_ExercisesInstance WHERE ExercisesId IN (:exercisesId)";
            jdbc.update(deleteExercisesInstanceSql, paramMap);

            String deleteOptionsInstanceSql = "DELETE FROM F_EX_OptionsInstance WHERE ExerciseId IN (:exercisesId)";
            jdbc.update(deleteOptionsInstanceSql, paramMap);

            //删除试卷题目关联表原关联
            String deletePERelSql = "DELETE FROM F_EX_PaperExcsRelInstance WHERE PaperId = ?";
            jdbcTemplate.update(deletePERelSql, paperId);

            String deleteExamSql = "DELETE FROM F_EX_ExamInfo WHERE ExamId = ?";
            jdbcTemplate.update(deleteExamSql, examId);
            String deleteEPRelSql = "DELETE FROM F_EX_ExamPaperRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteEPRelSql, paperId);

            //考试参与人表
            String deleteExamUserSql = "DELETE FROM F_EX_ExamUserRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteExamUserSql, examId);

            //阅卷人表
            String deleteExamReaderSql = "DELETE FROM F_EX_ExamReaderRel WHERE ExamId = ?";
            jdbcTemplate.update(deleteExamReaderSql, examId);

            //生成考试取消消息
            String exNotice = MessageModelConstants.EXAM_CANCEL_NOTICE;
            exNotice = exNotice.replace("【：考试】", examTitle);
            messageManageService.addMessage(userInfo, MascotMessageType.ExamNotice.getValue(), null, exNotice, userIdList);
            messageManageService.addMessage(userInfo, MascotMessageType.ExamNotice.getValue(), null, exNotice, readerIdList);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service删除考试成功！");
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service删除考试异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询考试管理列表
     *
     * @param userInfo   调用接口用户信息
     * @param title      标题
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param assmStatus 评估状态：1.未评估 ；2，已评估
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryExamManageList(UserInfo userInfo, String title, Date startTime, Date endTime, Integer assmStatus, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service查询考试管理列表必传参数为空!");
                return null;
            }

            Map<String, Object> paramMap = new HashMap<>();
            String sql;

            Integer row = pageUtil.getRow(pageIndex, pageSize);

            if (StringUtils.hasText(title) && assmStatus != null) {
                String paramLike = "%" + title + "%";
                sql = "SELECT ExamId,ExamTitle,CreateTime,AssmStatus FROM F_EX_ExamInfo WHERE Flag = :flag AND ExamTitle LIKE :paramLike AND AssmStatus = :assmStatus AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                paramMap.put("paramLike", paramLike);
                paramMap.put("assmStatus", assmStatus);
            } else if (StringUtils.hasText(title)) {
                String paramLike = "%" + title + "%";
                sql = "SELECT ExamId,ExamTitle,CreateTime,AssmStatus FROM F_EX_ExamInfo WHERE Flag = :flag AND ExamTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                paramMap.put("paramLike", paramLike);
            } else if (assmStatus != null) {
                sql = "SELECT ExamId,ExamTitle,CreateTime,AssmStatus FROM F_EX_ExamInfo WHERE Flag = :flag AND AssmStatus = :assmStatus AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
                paramMap.put("assmStatus", assmStatus);
            } else {
                sql = "SELECT ExamId,ExamTitle,CreateTime,AssmStatus FROM F_EX_ExamInfo WHERE Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY Createtime DESC LIMIT :row,:pageSize";
            }
            paramMap.put("flag", FlagType.Effective.getValue());
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            paramMap.put("row", row);
            paramMap.put("pageSize", pageSize);

            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            List<Map<String, Object>> examList = jdbc.query(sql, paramMap, (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("ExamId", rs.getInt("ExamId"));
                map.put("AssmStatus", rs.getInt("AssmStatus"));
                map.put("ExamTitle", rs.getString("ExamTitle"));
                map.put("CreateTime", rs.getString("CreateTime"));
                return map;
            });
            List<Map<String, Object>> list = new ArrayList<>();
            if (examList == null || examList.size() == 0) {
                return null;
            }
            for (Map<String, Object> map : examList) {
                String readerSql = "SELECT PkId FROM F_EX_ExamReaderRel WHERE UserId = ? AND ExamId = ?";
                Integer readerPk;
                try {
                    readerPk = jdbcTemplate.queryForObject(readerSql, new Object[]{userInfo.getUserId(), map.get("ExamId")}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    readerPk = null;
                }
                Boolean isReader = (readerPk != null);
                //是否是该题目阅卷人
                map.put("IsReader", isReader);
                list.add(map);
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询考试管理列表成功!");
            return list;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询考试管理列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询考试管理列表总条数
     *
     * @param userInfo   调用接口用户信息
     * @param title      标题
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param assmStatus 评估状态：1.未评估 ；2，已评估
     * @return count
     */
    @Override
    public Integer queryExamManageListCount(UserInfo userInfo, String title, Date startTime, Date endTime, Integer assmStatus) {

        try {
            if (userInfo == null) {
                logger.info("==========>Service查询考试管理列表必传参数为空!");
                return null;
            }
            Map<String, Object> paramMap = new HashMap<>();
            String sql;

            if (StringUtils.hasText(title) && assmStatus != null) {
                String paramLike = "%" + title + "%";
                sql = "SELECT COUNT(1) FROM F_EX_ExamInfo WHERE Flag = :flag AND ExamTitle LIKE :paramLike AND AssmStatus = :assmStatus AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                paramMap.put("paramLike", paramLike);
                paramMap.put("assmStatus", assmStatus);
            } else if (StringUtils.hasText(title)) {
                String paramLike = "%" + title + "%";
                sql = "SELECT COUNT(1) FROM F_EX_ExamInfo WHERE Flag = :flag AND ExamTitle LIKE :paramLike AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                paramMap.put("paramLike", paramLike);
            } else if (assmStatus != null) {
                sql = "SELECT COUNT(1) FROM F_EX_ExamInfo WHERE Flag = :flag AND AssmStatus = :assmStatus AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                paramMap.put("assmStatus", assmStatus);
            } else {
                sql = "SELECT COUNT(1) FROM F_EX_ExamInfo WHERE Flag = :flag AND CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
            }
            paramMap.put("flag", FlagType.Effective.getValue());
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);

            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            Integer count = jdbc.queryForObject(sql, paramMap, Integer.class);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询考试管理列表总条数成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询考试管理列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 考试评估
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试编号
     * @return result 1:评估成功；2：已评估，无需再评估； 其他，失败
     */
    @Override
    public Integer addAssessment(UserInfo userInfo, Integer examId, String assmContent) {

        try {
            if (userInfo == null || examId == null || !StringUtils.hasText(assmContent)) {
                logger.info("==========>Service考试评估必传参数为空!");
                return null;
            }
            String sql = "SELECT AssmStatus FROM F_EX_ExamInfo WHERE ExamId = ? AND Flag = ?";
            Integer oldAssmStatus;
            Integer result;
            try {
                oldAssmStatus = jdbcTemplate.queryForObject(sql, new Object[]{examId, FlagType.Effective.getValue()}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                oldAssmStatus = null;
            }

            if (oldAssmStatus != null && oldAssmStatus.equals(JudFlag.N.getValue())) {
                //原先未评估，现评估
                String assmSql = "UPDATE F_EX_ExamInfo SET AssmStatus = ?,AssmContent = ?,AssmUserId = ?,AssmTime = ? WHERE ExamId = ?";
                jdbcTemplate.update(assmSql, JudFlag.Y.getValue(), assmContent, userInfo.getUserId(), new Date(), examId);
                result = 1;
            } else if (oldAssmStatus != null && oldAssmStatus.equals(JudFlag.Y.getValue())) {
                //已评估，无需再评估
                result = -1;
            } else {
                return null;
            }

            return result;
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service考试评估异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询批卷列表
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试编号
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryCheckPaperList(UserInfo userInfo, Integer examId, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo == null || pageIndex == null || pageSize == null || examId == null) {
                logger.info("==========>Service查询批卷列表必传参数为空!");
                return null;
            }
            Integer row = pageUtil.getRow(pageIndex, pageSize);
            String sql = "SELECT PkId,UserId,ExScore,ReaderStatus FROM F_EX_ExamUserRel WHERE ExamId = ? AND ExamStatus = ? ORDER BY UserId LIMIT ?,?";
            List<Map<String, Object>> originList = jdbcTemplate.query(sql, new Object[]{examId, ExamStatus.Finish.getValue(), row, pageSize}, (rs, rowNum) -> {
                Map<String, Object> m = new HashMap<>();
                m.put("PkId", rs.getInt("PkId"));
                m.put("UserId", rs.getInt("UserId"));
                m.put("ExScore", rs.getObject("ExScore"));
                m.put("ReaderStatus", rs.getInt("ReaderStatus"));
                return m;
            });
            List<Map<String, Object>> list = new ArrayList<>();
            if (originList != null && originList.size() > 0) {
                List<Integer> idList = new ArrayList<>();
                for (Map<String, Object> m : originList) {
                    idList.add((Integer) m.get("UserId"));
                }
                List<UserInfo> userInfoList = userInfoService.getUserListByUserIds(idList);
                if (userInfoList != null && userInfoList.size() > 0) {
                    for (Map<String, Object> m : originList) {
                        for (UserInfo us : userInfoList) {
                            if (us.getUserId() == (Integer) m.get("UserId")) {
                                m.put("RealName", us.getRealName());
                                list.add(m);
                            }

                        }
                    }
                }
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询批卷列表成功!");
            return list;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询批卷列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询批卷列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试编号
     * @return list
     */
    @Override
    public Integer queryCheckPaperListCount(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service查询批卷列表总条数必传参数为空!");
                return null;
            }
            String sql = "SELECT COUNT(1) FROM F_EX_ExamUserRel WHERE ExamId = ? AND ExamStatus = ?";
            List<Map<String, Object>> originList = jdbcTemplate.queryForList(sql, examId, ExamStatus.Finish.getValue());
            Integer count;
            try {
                count = jdbcTemplate.queryForObject(sql, new Object[]{examId, ExamStatus.Finish.getValue()}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                count = null;
            }
            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询批卷列表总条数成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询批卷列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 我的考试列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     搜索参数
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> showMyPaperList(UserInfo userInfo, String title, Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service我的考试列表必传参数为空!");
                return null;
            }
            Integer row = pageUtil.getRow(pageIndex, pageSize);
            String sql;
            Object[] obj;

            if (StringUtils.hasText(title)) {
                title = "%" + title + "%";
                sql = "SELECT R.ExamId,E.ExamTitle,E.StartTime,E.EndTime,R.ExScore,R.ExamStatus FROM F_EX_ExamUserRel R LEFT JOIN F_EX_ExamInfo E ON R.ExamId = E.ExamId WHERE E.Flag = ? AND E.ExamTitle LIKE ? AND R.UserId = ? AND E.StartTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND E.EndTime BETWEEN IFNULL(?, '') AND IFNULL(?, E.EndTime) ORDER BY R.Createtime DESC LIMIT ?,?";
                obj = new Object[]{FlagType.Effective.getValue(), title, userInfo.getUserId(), startTime, endTime, startTime, endTime, row, pageSize};
            } else {
                sql = "SELECT R.ExamId,E.ExamTitle,E.StartTime,E.EndTime,R.ExScore,R.ExamStatus FROM F_EX_ExamUserRel R LEFT JOIN F_EX_ExamInfo E ON R.ExamId = E.ExamId WHERE E.Flag = ? AND R.UserId = ? AND E.StartTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND E.EndTime BETWEEN IFNULL(?, '') AND IFNULL(?, E.EndTime) ORDER BY R.Createtime DESC LIMIT ?,?";
                obj = new Object[]{FlagType.Effective.getValue(), userInfo.getUserId(), startTime, endTime, startTime, endTime, row, pageSize};
            }
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, obj);
            List<Map<String, Object>> list = new ArrayList<>();
            if (mapList != null && mapList.size() > 0) {
                Date now = new Date();
                for (Map<String, Object> map : mapList) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date deadline = format.parse(map.get("EndTime").toString());
                    if (map.get("ExamStatus").equals(ExamStatus.DNS.getValue()) && now.getTime() > deadline.getTime()) {
                        map.put("ExamStatus", ExamStatus.DNF.getValue());
                        String upRelSql = "UPDATE F_EX_ExamUserRel SET ExamStatus = ? WHERE ExamId = ? AND UserId = ?";
                        jdbcTemplate.update(upRelSql, ExamStatus.DNF.getValue(), map.get("ExamId"), userInfo.getUserId());
                    }
                    list.add(map);
                }
            }


            logger.info("======[" + userInfo.getUserId() + "]==========>Service我的考试列表成功!");
            return list;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service我的考试列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 我的考试列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     搜索参数
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return list
     */
    @Override
    public Integer queryMyPaperListCount(UserInfo userInfo, String title, Date startTime, Date endTime) {

        try {
            if (userInfo == null) {
                logger.info("==========>Service我的考试列表总条数必传参数为空!");
                return null;
            }
            String sql;
            Object[] obj;

            if (StringUtils.hasText(title)) {
                title = "%" + title + "%";
                sql = "SELECT COUNT(1) FROM F_EX_ExamUserRel R LEFT JOIN F_EX_ExamInfo E ON R.ExamId = E.ExamId WHERE E.Flag = ? AND E.ExamTitle LIKE ? AND R.UserId = ? AND E.StartTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND E.EndTime BETWEEN IFNULL(?, '') AND IFNULL(?, E.EndTime)";
                obj = new Object[]{FlagType.Effective.getValue(), title, userInfo.getUserId(), startTime, endTime, startTime, endTime};
            } else {
                sql = "SELECT COUNT(1) FROM F_EX_ExamUserRel R LEFT JOIN F_EX_ExamInfo E ON R.ExamId = E.ExamId WHERE E.Flag = ? AND R.UserId = ? AND E.StartTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) AND E.EndTime BETWEEN IFNULL(?, '') AND IFNULL(?, E.EndTime)";
                obj = new Object[]{FlagType.Effective.getValue(), userInfo.getUserId(), startTime, endTime, startTime, endTime};
            }
            Integer count;
            try {
                count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                count = null;
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service我的考试列表总条数成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service我的考试列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 开始考试---查询试卷实例
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return paperInfo
     */
    @Override
    public Map<String, Object> showPaperInstanceDetail(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service开始考试---查询试卷实例必传参数为空!");
                return null;
            }

            String sql = "SELECT PAI.PaperId,PAI.PaperTitle,PAI.CategoryId FROM F_EX_PaperInstance PAI LEFT JOIN F_EX_ExamPaperRel EPR ON EPR.PaperId=PAI.PaperId WHERE EPR.ExamId = ?";
            Map<String, Object> paperInfo;
            try {
                paperInfo = jdbcTemplate.queryForMap(sql, examId);
            } catch (EmptyResultDataAccessException e) {
                paperInfo = null;
            }
            if (paperInfo != null) {
                List<Map<String, Object>> exerList = new ArrayList<>();
                String sqlStr = "SELECT PkId,PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp FROM F_EX_PaperExcsRelInstance WHERE PaperId = ? AND ModuleTyp = ?";

                //循环枚举类
                for (ExercisesType e : ExercisesType.values()) {
                    List<Map<String, Object>> exerIfList = jdbcTemplate.query(sqlStr, new Object[]{paperInfo.get("PaperId"), e.getValue()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("PkId", rs.getInt("PkId"));
                        m.put("PaperId", rs.getInt("PaperId"));
                        m.put("ExercisesId", rs.getInt("ExercisesId"));
                        m.put("Sort", rs.getInt("Sort"));
                        m.put("ExcsScore", rs.getInt("ExcsScore"));
                        m.put("ModuleTyp", rs.getInt("ModuleTyp"));
                        return m;
                    });
                    List<Map<String, Object>> exerInfoList = new ArrayList<>();
                    if (exerIfList != null && exerIfList.size() > 0) {
                        Map<String, Object> map = new HashMap<>();
                        for (Map<String, Object> m : exerIfList) {
                            Map<String, Object> exer = queryExercisesInstanceDetail(userInfo, (Integer) m.get("ExercisesId"));
                            exer.put("Sort", m.get("Sort"));
                            exerInfoList.add(exer);
                        }
                        map.put("ExerType", e.getValue());
                        map.put("ExerTypeScore", exerIfList.get(0).get("ExcsScore"));
                        map.put("ExerInfoList", exerInfoList);
                        exerList.add(map);
                    }


                }
                paperInfo.put("ExerList", exerList);
            }
            String upRelSql = "UPDATE F_EX_ExamUserRel SET ExamStatus = ? WHERE ExamId = ? AND UserId = ?";
            jdbcTemplate.update(upRelSql, ExamStatus.Start.getValue(), examId, userInfo.getUserId());

            return paperInfo;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service开始考试---查询试卷实例异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询题目实例详情
     *
     * @param userInfo    调用接口用户信息
     * @param exercisesId 题目Id
     * @return exercisesInfo
     */
    private Map<String, Object> queryExercisesInstanceDetail(UserInfo userInfo, Integer exercisesId) {

        try {
            if (userInfo == null && exercisesId == null) {
                logger.info("==========>Service查询题目实例详情必传参数为空!");
                return null;
            }

            String sql = "SELECT ExercisesId,ExType,ExcsTitle,CurrAnswerContent,CategoryId FROM F_EX_ExercisesInstance WHERE ExercisesId = ?";
            Map<String, Object> exercisesInfo;
            try {
                exercisesInfo = jdbcTemplate.queryForObject(sql, new Object[]{exercisesId}, (rs, rowNum) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ExercisesId", rs.getInt("ExercisesId"));
                    map.put("ExType", rs.getInt("ExType"));
                    map.put("ExcsTitle", rs.getString("ExcsTitle"));
                    map.put("CurrAnswerContent", rs.getString("CurrAnswerContent"));
                    map.put("CategoryId", rs.getInt("CategoryId"));
                    return map;
                });
            } catch (EmptyResultDataAccessException e) {
                exercisesInfo = null;
            }

            if (exercisesInfo != null && ((Integer) exercisesInfo.get("ExType") == ExercisesType.SingleSelection.getValue() || (Integer) exercisesInfo.get("ExType") == ExercisesType.MultipleSelection.getValue() || (Integer) exercisesInfo.get("ExType") == ExercisesType.Judgment.getValue())) {
                //如果是选择题或判断题，要有选项列表
                String sql2 = "SELECT OptionId,OptionCode,OptionDesc,CrrctAns FROM F_EX_OptionsInstance WHERE ExerciseId = ? ORDER BY OptionCode";
                List<Map<String, Object>> optionList = jdbcTemplate.queryForList(sql2, exercisesId);
                exercisesInfo.put("OptionList", optionList);

            }
            return exercisesInfo;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询题目实例详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 开始考试 --- 提交答案
     *
     * @param userInfo   调用接口用户信息
     * @param examId     模板试卷编号
     * @param answerList 作答列表 参数1：ExerType 题目模块（题目类型）；参数2：List<Map<String, Object>> ExerInfoList该类型下的题号列表--参数2.1：ExerId 题目编号 --参数2.2：AnsContent 作答内容，多选用英文逗号","分隔
     * @return result 1:成功；其他失败；
     */
    @Override
    public Integer addExamAnswer(UserInfo userInfo, Integer examId, List<Map<String, Object>> answerList) {

        try {
            if (userInfo == null || examId == null || answerList == null || answerList.size() == 0) {
                logger.info("==========>Service开始考试 --- 提交答案必传参数为空!");
                return null;
            }

            //按模块分批量插入关联表
            String addSql = "INSERT INTO F_EX_ExamAnswer(ExamId,ExerType,ExerciseId,AnswerUserId,AnsContent,Score,AnsCreateTime,IsCorrect) VALUES (?,?,?,?,?,?,?,?)";
            for (Map<String, Object> m : answerList) {
                //模块题目列表
                List<Map<String, Object>> exerInfoList = (ArrayList<Map<String, Object>>) m.get("ExerInfoList");
                jdbcTemplate.batchUpdate(addSql, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, examId);
                        ps.setInt(2, Integer.valueOf(m.get("ExerType").toString()) );
                        ps.setInt(3, Integer.valueOf(exerInfoList.get(i).get("ExerId").toString()));
                        ps.setInt(4, userInfo.getUserId());
                        ps.setString(5, exerInfoList.get(i).get("AnsContent").toString());
                        ps.setObject(6, null);
                        ps.setObject(7, new Date());
                        ps.setObject(8, null);
                    }

                    public int getBatchSize() {
                        return exerInfoList.size();
                    }
                });

            }
            String upRelSql = "UPDATE F_EX_ExamUserRel SET ExamStatus = ? WHERE ExamId = ? AND UserId = ?";
            jdbcTemplate.update(upRelSql, ExamStatus.Finish.getValue(), examId, userInfo.getUserId());
            logger.info("======[" + userInfo.getUserId() + "]==========>Service开始考试 --- 提交答案成功！");
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service开始考试 --- 提交答案异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 阅卷---展示答案
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @param userId   考试人Id
     * @return paperInfo
     */
    @Override
    public Map<String, Object> updateExamAnswer(UserInfo userInfo, Integer examId, Integer userId) {

        try {
            if (userInfo == null || examId == null || userId == null) {
                logger.info("==========>Service阅卷---展示答案必传参数为空!");
                return null;
            }

            String sql = "SELECT PAI.PaperId,PAI.PaperTitle,PAI.CategoryId FROM F_EX_PaperInstance PAI LEFT JOIN F_EX_ExamPaperRel EPR ON EPR.PaperId=PAI.PaperId WHERE EPR.ExamId = ?";
            Map<String, Object> paperInfo;
            try {
                paperInfo = jdbcTemplate.queryForMap(sql, examId);
            } catch (EmptyResultDataAccessException e) {
                paperInfo = null;
            }
            if (paperInfo != null) {
                String sqlStr = "SELECT PkId,PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp FROM F_EX_PaperExcsRelInstance WHERE PaperId = ? AND ModuleTyp = ?";
                List<Map<String, Object>> exerList = new ArrayList<>();
                //循环枚举类
                for (ExercisesType e : ExercisesType.values()) {
                    Map<String, Object> map = new HashMap<>();
                    List<Map<String, Object>> exerIfList = jdbcTemplate.query(sqlStr, new Object[]{paperInfo.get("PaperId"), e.getValue()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("PkId", rs.getInt("PkId"));
                        m.put("PaperId", rs.getInt("PaperId"));
                        m.put("ExercisesId", rs.getInt("ExercisesId"));
                        m.put("Sort", rs.getInt("Sort"));
                        m.put("ExcsScore", rs.getInt("ExcsScore"));
                        m.put("ModuleTyp", rs.getInt("ModuleTyp"));
                        return m;
                    });
                    List<Map<String, Object>> exerInfoList = new ArrayList<>();
                    if (exerIfList != null && exerIfList.size() > 0) {

                        for (Map<String, Object> m : exerIfList) {
                            Map<String, Object> exer = queryExercisesInstanceDetail(userInfo, (Integer) m.get("ExercisesId"));
                            if (exer == null) {
                                return null;
                            }
                            String answerSql = "SELECT AnsContent FROM F_EX_ExamAnswer WHERE ExamId = ? AND ExerciseId = ? AND AnswerUserId = ?";
                            String answer;
                            try {
                                answer = jdbcTemplate.queryForObject(answerSql, new Object[]{examId, m.get("ExercisesId"), userId}, String.class);
                                if (StringUtils.hasText(answer)) {
                                    String[] correctAs = answer.split(",");
                                    Arrays.sort(correctAs);
                                    answer = org.apache.commons.lang.StringUtils.join(correctAs);
                                }
                            } catch (EmptyResultDataAccessException ee) {
                                answer = null;
                            }
                            Integer isCorrect = null;
                            if (exer.get("ExType").equals(ExercisesType.SingleSelection.getValue()) || exer.get("ExType").equals(ExercisesType.MultipleSelection.getValue()) || exer.get("ExType").equals(ExercisesType.Judgment.getValue())) {
                                //若为非主观题，则自动阅卷
                                logger.info("===========系统正在对编号为【" + userId + "】的用户的【" + examId + "】编号考试进行自动阅卷：正在批阅题号为【" + m.get("ExercisesId") + "】的题目！");
                                String correct = exer.get("CurrAnswerContent").toString();
                                Integer actualSc = correct.equals(answer) ? (Integer) m.get("ExcsScore") : 0;
                                isCorrect = correct.equals(answer) ? FlagType.Effective.getValue() : FlagType.Ineffective.getValue();
                                String upSql = "UPDATE F_EX_ExamAnswer SET Score = ?,IsCorrect = ? WHERE ExamId = ? AND ExerciseId = ? AND AnswerUserId = ?";
                                jdbcTemplate.update(upSql, actualSc, isCorrect, examId, m.get("ExercisesId"), userId);
                            }
                            Integer actualScore;
                            String selcetSql = "SELECT Score FROM F_EX_ExamAnswer WHERE ExamId = ? AND ExerciseId = ? AND AnswerUserId = ?";
                            try {
                                actualScore = jdbcTemplate.queryForObject(selcetSql, new Object[]{examId, m.get("ExercisesId"), userId}, Integer.class);
                            } catch (EmptyResultDataAccessException ee) {
                                actualScore = null;
                            }
                            exer.put("ActualScore", actualScore);
                            exer.put("Sort", m.get("Sort"));
                            //1.正确，2.不正确
                            exer.put("IsCorrect", isCorrect);
                            exer.put("UserAnswer", answer);
                            exerInfoList.add(exer);
                        }
                        map.put("ExerType", e.getValue());
                        map.put("ExerTypeScore", exerIfList.get(0).get("ExcsScore"));
                        map.put("ExerInfoList", exerInfoList);
                        exerList.add(map);

                    }
                    paperInfo.put("ExerList", exerList);
                }

            }

            return paperInfo;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service阅卷---展示答案异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 阅卷---提交
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试Id
     * @param userId    考试人Id
     * @param scoreList 问答题评分列表 参数1：ExerId 题目编号 --参数2：ActualScore 阅卷人对该题目的打分
     * @return result 1:成功 其他失败
     */
    @Override
    public Integer updateUserTotalScore(UserInfo userInfo, Integer examId, Integer userId, List<Map<String, Object>> scoreList) {

        try {
            if (userInfo == null || examId == null || userId == null || scoreList == null || scoreList.size() == 0) {
                logger.info("==========>Service阅卷---提交必传参数为空!");
                return null;
            }
            //批量插入主观题的评分
            String upSql = "UPDATE F_EX_ExamAnswer SET Score = ? WHERE ExamId = ? AND ExerciseId = ? AND AnswerUserId = ?";
            jdbcTemplate.batchUpdate(upSql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, Integer.valueOf(scoreList.get(i).get("ActualScore").toString()));
                    ps.setInt(2, examId);
                    ps.setInt(3, Integer.valueOf(scoreList.get(i).get("ExerId").toString()));
                    ps.setInt(4, userInfo.getUserId());
                }

                public int getBatchSize() {
                    return scoreList.size();
                }
            });

            //查询该场考试中个人每题得分列表
            String exerciseScoreSql = "SELECT EA.ExerciseId,EA.Score FROM F_EX_ExamAnswer EA WHERE EA.ExamId = ? AND EA.AnswerUserId = ? GROUP BY EA.ExerciseId";
            List<Map<String, Object>> exerciseScoreList = jdbcTemplate.queryForList(exerciseScoreSql, examId, userId);
            Integer totalScore = 0;
            if (exerciseScoreList != null && exerciseScoreList.size() > 0) {
                for (Map<String, Object> map : exerciseScoreList) {
                    totalScore += map.get("Score") == null ? 0 : (Integer) map.get("Score");
                }
            }
            String upTotalScoreSql = "UPDATE F_EX_ExamUserRel SET ExScore = ? WHERE ExamId = ? AND UserId = ?";
            jdbcTemplate.update(upTotalScoreSql, totalScore, examId, userId);
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service阅卷---提交异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 考试分析
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return paperInfo
     */
    @Override
    public Map<String, Object> queryExamAnalysis(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service考试分析必传参数为空!");
                return null;
            }

            String sql = "SELECT PAI.PaperId,PAI.PaperTitle,PAI.CategoryId,EI.AssmContent FROM F_EX_PaperInstance PAI LEFT JOIN F_EX_ExamPaperRel EPR ON EPR.PaperId=PAI.PaperId LEFT JOIN F_EX_ExamInfo EI ON EI.ExamId = EPR.ExamId WHERE EPR.ExamId = ?";
            Map<String, Object> paperInfo;
            try {
                paperInfo = jdbcTemplate.queryForMap(sql, examId);
            } catch (EmptyResultDataAccessException e) {
                paperInfo = null;
            }
            if (paperInfo != null) {
                List<Map<String, Object>> exerList = new ArrayList<>();

                String sqlStr = "SELECT PkId,PaperId,ExercisesId,ExcsScore,Sort,ModuleTyp FROM F_EX_PaperExcsRelInstance WHERE PaperId = ? AND ModuleTyp = ?";

                //循环枚举类
                for (ExercisesType e : ExercisesType.values()) {
                    Map<String, Object> map = new HashMap<>();
                    List<Map<String, Object>> exerIfList = jdbcTemplate.query(sqlStr, new Object[]{paperInfo.get("PaperId"), e.getValue()}, (rs, rowNum) -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("PkId", rs.getInt("PkId"));
                        m.put("PaperId", rs.getInt("PaperId"));
                        m.put("ExercisesId", rs.getInt("ExercisesId"));
                        m.put("Sort", rs.getInt("Sort"));
                        m.put("ExcsScore", rs.getInt("ExcsScore"));
                        m.put("ModuleTyp", rs.getInt("ModuleTyp"));
                        return m;
                    });
                    List<Map<String, Object>> exerInfoList = new ArrayList<>();
                    if (exerIfList != null && exerIfList.size() > 0) {
                        for (Map<String, Object> m : exerIfList) {
                            Map<String, Object> exer = queryExercisesInstanceDetail(userInfo, (Integer) m.get("ExercisesId"));
                            if (exer == null) {
                                return null;
                            }
                            String correctSql = "SELECT COUNT(1) FROM F_EX_ExamAnswer WHERE ExamId = ? AND ExerciseId = ? AND IsCorrect = ?";
                            String totalSql = "SELECT COUNT(1) FROM F_EX_ExamAnswer WHERE ExamId = ? AND ExerciseId = ?";
                            Integer correctCount;
                            try {
                                correctCount = jdbcTemplate.queryForObject(correctSql, new Object[]{examId, m.get("ExercisesId"), FlagType.Effective.getValue()}, Integer.class);
                            } catch (EmptyResultDataAccessException ee) {
                                correctCount = 0;
                            }
                            Integer wrongCount;
                            try {
                                wrongCount = jdbcTemplate.queryForObject(correctSql, new Object[]{examId, m.get("ExercisesId"), FlagType.Ineffective.getValue()}, Integer.class);
                            } catch (EmptyResultDataAccessException ee) {
                                wrongCount = 0;
                            }
                            Integer totalCount;
                            try {
                                totalCount = jdbcTemplate.queryForObject(totalSql, new Object[]{examId, m.get("ExercisesId")}, Integer.class);
                            } catch (EmptyResultDataAccessException ee) {
                                totalCount = 0;
                            }
                            DecimalFormat df = new DecimalFormat("0.000");//格式化小数

                            String accurate = totalCount == 0 ? "0.000" : df.format((float) correctCount / totalCount);
                            String errorRate = totalCount == 0 ? "0.000" : df.format((float) wrongCount / totalCount);
                            String inefficiency = totalCount == 0 ? "0.000" : df.format((float) (totalCount - correctCount - wrongCount) / totalCount);
                            exer.put("Accurate", accurate);
                            exer.put("ErrorRate", errorRate);
                            exer.put("Inefficiency", inefficiency);
                            exerInfoList.add(exer);
                        }
                        map.put("ExerType", e.getValue());
                        map.put("ExerTypeScore", exerIfList.get(0).get("ExcsScore"));
                        map.put("ExerInfoList", exerInfoList);
                        exerList.add(map);
                    }
                    paperInfo.put("ExerList", exerList);
                }

            }

            return paperInfo;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service阅卷---展示答案异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 问答题作答情况
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @param exerciseId 题目Id
     * @param realName   模糊搜索真实姓名
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryShortAnswerAnalysis(UserInfo userInfo, Integer examId, Integer exerciseId, String realName, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo == null || examId == null || exerciseId == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service问答题作答情况必传参数为空!");
                return null;
            }
            List<Map<String, Object>> list;
            List<Map<String, Object>> allList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();

            Integer row = pageUtil.getRow(pageIndex, pageSize);
            String sql;

            //确定是否传入参数姓名
            if (StringUtils.hasText(realName)) {
                //组装姓名搜索参数
                List<String> realNameList = new ArrayList<>();
                realNameList.add(realName);
                //根据姓名查询用户中心的用户列表
                String countUs = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                Integer pSize = jdbcTemplate.queryForObject(countUs, Integer.class);
                List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);
                List<Integer> userIdList = new ArrayList<>();
                if (listUsCenter == null || listUsCenter.size() == 0) {
                    return null;
                }
                for (UserInfo usInfo : listUsCenter) {
                    userIdList.add(usInfo.getUserId());
                }
                sql = "SELECT A.AnswerId,A.AnswerUserId,A.Score,A.AnsContent FROM F_EX_ExamAnswer A WHERE A.ExamId = :examId AND A.ExerciseId = :exerciseId AND A.AnswerUserId IN (:userIdList) ORDER BY A.Score DESC LIMIT :row,:pageSize";
                paramMap.put("userIdList", userIdList);

            } else {
                sql = "SELECT A.AnswerId,A.AnswerUserId,A.Score,A.AnsContent FROM F_EX_ExamAnswer A WHERE A.ExamId = :examId AND A.ExerciseId = :exerciseId ORDER BY A.Score DESC LIMIT :row,:pageSize";
            }
            paramMap.put("examId", examId);
            paramMap.put("exerciseId", exerciseId);
            paramMap.put("row", row);
            paramMap.put("pageSize", pageSize);
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            list = jdbc.queryForList(sql, paramMap);

            if (list != null && list.size() > 0) {
                List<Integer> listId = new ArrayList<>();
                for (Map<String, Object> map : list) {
                    listId.add((Integer) map.get("AnswerUserId"));
                }
                List<UserInfo> userInfos = userInfoService.getUserListByUserIds(listId);
                if (userInfos != null && userInfos.size() > 0) {
                    for (Map<String, Object> map : list) {
                        for (UserInfo usInfo : userInfos) {
                            if (usInfo.getUserId() == (Integer) map.get("AnswerUserId")) {
                                map.put("RealName", usInfo.getRealName());
                                allList.add(map);
                            }
                        }
                    }
                }

            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service问答题作答情况成功!");
            return allList;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service问答题作答情况异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 问答题作答情况总条数
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @param exerciseId 题目Id
     * @param realName   模糊搜索真实姓名
     * @return count
     */
    @Override
    public Integer queryShortAnswerAnalysisCount(UserInfo userInfo, Integer examId, Integer exerciseId, String realName) {

        try {
            if (userInfo == null || examId == null || exerciseId == null) {
                logger.info("==========>Service问答题作答情况总条数必传参数为空!");
                return null;
            }
            Map<String, Object> paramMap = new HashMap<>();
            String sql;

            //确定是否传入参数姓名
            if (StringUtils.hasText(realName)) {
                //组装姓名搜索参数
                List<String> realNameList = new ArrayList<>();
                realNameList.add(realName);
                //根据姓名查询用户中心的用户列表
                String countUs = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                Integer pSize = jdbcTemplate.queryForObject(countUs, Integer.class);
                List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);
                List<Integer> userIdList = new ArrayList<>();
                if (listUsCenter == null || listUsCenter.size() == 0) {
                    return 0;
                }
                for (UserInfo usInfo : listUsCenter) {
                    userIdList.add(usInfo.getUserId());
                }
                sql = "SELECT COUNT(1) FROM F_EX_ExamAnswer A WHERE A.ExamId = :examId AND A.ExerciseId = :exerciseId AND A.AnswerUserId IN (:userIdList)";
                paramMap.put("userIdList", userIdList);

            } else {
                sql = "SELECT COUNT(1) FROM F_EX_ExamAnswer A WHERE A.ExamId = :examId AND A.ExerciseId = :exerciseId";
            }
            paramMap.put("examId", examId);
            paramMap.put("exerciseId", exerciseId);
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            Integer count = jdbc.queryForObject(sql, paramMap, Integer.class);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service问答题作答情况总条数成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service问答题作答情况总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 考试统计 --- 柱状图
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return map
     */
    @Override
    public List<Map<String, Object>> queryExamBarChart(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service考试统计 --- 柱状图必传参数为空!");
                return null;
            }

            String sql = "SELECT COUNT(1) FROM F_EX_ExamUserRel WHERE ExamId = ? AND ExScore BETWEEN ? AND ?";
            Integer phaseOneCount;
            Integer phaseTwoCount;
            Integer phaseThreeCount;
            Integer phaseFourCount;
            Integer phaseFiveCount;
            try {
                phaseOneCount = jdbcTemplate.queryForObject(sql, new Object[]{examId, ExamScorePhase.PhaseTwo.getValue(), ExamScorePhase.PhaseOne.getValue()}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                phaseOneCount = 0;
            }
            try {
                phaseTwoCount = jdbcTemplate.queryForObject(sql, new Object[]{examId, ExamScorePhase.PhaseThree.getValue(), ExamScorePhase.PhaseTwo.getValue() - 1}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                phaseTwoCount = 0;
            }
            try {
                phaseThreeCount = jdbcTemplate.queryForObject(sql, new Object[]{examId, ExamScorePhase.PhaseFour.getValue(), ExamScorePhase.PhaseThree.getValue() - 1}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                phaseThreeCount = 0;
            }
            try {
                phaseFourCount = jdbcTemplate.queryForObject(sql, new Object[]{examId, ExamScorePhase.PhaseFive.getValue(), ExamScorePhase.PhaseFour.getValue() - 1}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                phaseFourCount = 0;
            }
            try {
                phaseFiveCount = jdbcTemplate.queryForObject(sql, new Object[]{examId, 0, ExamScorePhase.PhaseFive.getValue() - 1}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                phaseFiveCount = 0;
            }
            String phaseOne = ExamScorePhase.PhaseTwo.getValue() + "~" + ExamScorePhase.PhaseOne.getValue();
            String phaseTwo = ExamScorePhase.PhaseThree.getValue() + "~" + (ExamScorePhase.PhaseTwo.getValue() - 1);
            String phaseThree = ExamScorePhase.PhaseFour.getValue() + "~" + (ExamScorePhase.PhaseThree.getValue() - 1);
            String phaseFour = ExamScorePhase.PhaseFive.getValue() + "~" + (ExamScorePhase.PhaseFour.getValue() - 1);
            String phaseFive = ExamScorePhase.PhaseFive.getValue() + "以下";
            Map<String, Object> mapOne = new HashMap<>();
            mapOne.put("ScorePhase", phaseOne);
            mapOne.put("UserCount", phaseOneCount);
            Map<String, Object> mapTwo = new HashMap<>();
            mapTwo.put("ScorePhase", phaseTwo);
            mapTwo.put("UserCount", phaseTwoCount);
            Map<String, Object> mapThree = new HashMap<>();
            mapThree.put("ScorePhase", phaseThree);
            mapThree.put("UserCount", phaseThreeCount);
            Map<String, Object> mapFour = new HashMap<>();
            mapFour.put("ScorePhase", phaseFour);
            mapFour.put("UserCount", phaseFourCount);
            Map<String, Object> mapFive = new HashMap<>();
            mapFive.put("ScorePhase", phaseFive);
            mapFive.put("UserCount", phaseFiveCount);
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(mapOne);
            list.add(mapTwo);
            list.add(mapThree);
            list.add(mapFour);
            list.add(mapFive);
            return list;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service考试统计 --- 柱状图异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 考试统计 --- 分数详情
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试Id
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryScoreAnalysis(UserInfo userInfo, Integer examId, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo == null || examId == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service考试统计 --- 分数详情必传参数为空!");
                return null;
            }
            List<Map<String, Object>> allList = new ArrayList<>();
            Integer row = pageUtil.getRow(pageIndex, pageSize);
            String sql = "SELECT A.UserId,A.ExScore FROM F_EX_ExamUserRel A WHERE A.ExamId = ? ORDER BY A.ExScore DESC LIMIT ?,?";

            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, examId, row, pageSize);

            if (list == null || list.size() == 0) {
                return null;
            }
            List<Integer> listId = new ArrayList<>();
            for (Map<String, Object> map : list) {
                listId.add((Integer) map.get("UserId"));
            }
            List<UserInfo> userInfos = userInfoService.getUserListByUserIds(listId);
            if (userInfos != null && userInfos.size() > 0) {
                for (Map<String, Object> map : list) {
                    for (UserInfo usInfo : userInfos) {
                        if (usInfo.getUserId() == (Integer) map.get("UserId")) {
                            map.put("RealName", usInfo.getRealName());
                            allList.add(map);
                        }
                    }
                }
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service考试统计 --- 分数详情成功!");
            return allList;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service考试统计 --- 分数详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 考试统计 --- 分数详情总条数
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return list
     */
    @Override
    public Integer queryScoreAnalysisCount(UserInfo userInfo, Integer examId) {

        try {
            if (userInfo == null || examId == null) {
                logger.info("==========>Service考试统计 --- 分数详情总条数必传参数为空!");
                return null;
            }
            String sql = "SELECT COUNT(1) FROM FROM F_EX_ExamUserRel A WHERE A.ExamId = ?";

            Integer count;
            try {
                count = jdbcTemplate.queryForObject(sql, new Object[]{examId}, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                count = 0;
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service考试统计 --- 分数详情总条数成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service考试统计 --- 分数详情总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

}
