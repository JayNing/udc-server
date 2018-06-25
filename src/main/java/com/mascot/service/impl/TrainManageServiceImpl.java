package com.mascot.service.impl;

import com.interfaces.mascot.ExCategoryService;
import com.interfaces.mascot.MessageManageService;
import com.interfaces.mascot.TrainManageService;
import com.thrift.common.body.MessageModelConstants;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 考试管理接口实现
 *
 * @author zhangmengyu
 * 2018/06/02
 */
@Service(value = "trainManageService")
public class TrainManageServiceImpl extends BasicServiceImpl implements TrainManageService {

    private final static Log logger = LogFactory.getLog(TrainManageServiceImpl.class);

    @Autowired
    ExCategoryService exCategoryService;

    @Autowired
    MessageManageService messageManageService;


    /**
     * 发起培训
     *
     * @param userInfo     调用接口用户信息
     * @param trainType    培训类型，1线上2线下
     * @param trainFlag    培训类型，1内训2外训
     * @param title        训练标题
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param trainSite    培训地点
     * @param lecturerName 讲师姓名
     * @param lecHeadUrl   讲师头像
     * @param lecProfile   讲师简介
     * @param category1    考试培训分类一级目录
     * @param category2    考试培训分类二级目录
     * @param trainContent 培训内容
     * @param fileList     文件列表 参数1：FileUrl，参数2：FileType
     * @param userIdList   参与人列表
     * @return 1：成功，其他失败
     */
    @Override
    public Integer addTrain(UserInfo userInfo, Integer trainType, Integer trainFlag, String title, Date startTime, Date endTime,
                            String trainSite, String lecturerName, String lecHeadUrl, String lecProfile, Integer category1, Integer category2,
                            String trainContent, List<Map<String, Object>> fileList, List<Integer> userIdList) {
        try {
            if (userInfo == null || !StringUtils.hasText(title) || startTime == null || endTime == null || !StringUtils.hasText(lecturerName)
                    || !StringUtils.hasText(lecHeadUrl) || category1 == null || trainType == null || !StringUtils.hasText(lecProfile) || trainFlag == null
                    || !StringUtils.hasText(trainContent) || userIdList == null || userIdList.size() == 0) {
                logger.info("==========>Service发起培训必传参数为空！");
                return null;
            }
            if ((trainType.equals(TrainType.Online.getValue()) && (fileList == null || fileList.size() == 0))
                    || (trainType.equals(TrainType.OffLine.getValue()) && trainSite == null)) {
                //线上必须传文件，线下必须有地点
                logger.info("==========>Service发起培训必传参数为空！");
                return null;
            }

            //校验标题唯一性
            String sqlTitle = "SELECT TrainId,TrainTitle FROM F_EX_TrainInfo WHERE Flag = ? AND TrainTitle = ?";
            Map<String, Object> trainInfo;
            try {
                trainInfo = jdbcTemplate.queryForMap(sqlTitle, FlagType.Effective.getValue(), title);
            } catch (EmptyResultDataAccessException e) {
                trainInfo = null;
            }
            if (trainInfo != null) {
                //标题重复
                return -1;
            }
            //插入讲师表
            Date day = new Date();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String lecturerSql = "INSERT INTO F_EX_LecturerInfo(LecturerName,LecHeadUrl,LecIntroduction,Flag,CreateTime) VALUES (?,?,?,?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            int i = 0;
                            java.sql.PreparedStatement ps = conn.prepareStatement(lecturerSql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(++i, lecturerName);
                            ps.setString(++i, lecHeadUrl);
                            ps.setString(++i, lecProfile);
                            ps.setInt(++i, FlagType.Effective.getValue());
                            ps.setObject(++i, day);
                            return ps;
                        }
                    },
                    keyHolder);

            Integer lecturerId = keyHolder.getKey().intValue();

            //判断分类，若无二级分类则选一级分类，若有二级分类则选二级分类
            Integer category = category2 == null ? category1 : category2;

            //插入培训表
            KeyHolder holder = new GeneratedKeyHolder();
            String trainSql = "INSERT INTO F_EX_TrainInfo(TrainTitle,TrainType,CategoryId,StartTime,EndTime,TrainSite,LecturerId,Content,TrainStatus,CreateTime,CreateUserId,LastEditTime,LastEditUserId,Flag,TrainFlag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            int i = 0;
                            java.sql.PreparedStatement ps = conn.prepareStatement(trainSql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(++i, title);
                            ps.setInt(++i, trainType);
                            ps.setInt(++i, category);
                            ps.setObject(++i, startTime);
                            ps.setObject(++i, endTime);
                            ps.setString(++i, trainSite);
                            ps.setInt(++i, lecturerId);
                            ps.setString(++i, trainContent);
                            ps.setInt(++i, TrainStatus.Review.getValue());
                            ps.setObject(++i, day);
                            ps.setInt(++i, userInfo.getUserId());
                            ps.setObject(++i, day);
                            ps.setInt(++i, userInfo.getUserId());
                            ps.setInt(++i, FlagType.Effective.getValue());
                            ps.setInt(++i, trainFlag);
                            return ps;
                        }
                    },
                    holder);

            Integer trainId = holder.getKey().intValue();
            if (fileList != null && fileList.size() > 0) {
                //批量删除原有文件
                String deleteFileSql = "DELETE FROM F_EX_TrainFileInfo WHERE TrainId = ?";
                jdbcTemplate.update(deleteFileSql, trainId);
                //批量插入文件
                String addFileSql = "INSERT INTO F_EX_TrainFileInfo(TrainId,FileUrl,FileType) VALUES (?,?,?)";
                jdbcTemplate.batchUpdate(addFileSql, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, trainId);
                        ps.setString(2, fileList.get(i).get("FileUrl").toString());
                        ps.setInt(3, (Integer) fileList.get(i).get("FileType"));
                    }

                    public int getBatchSize() {
                        return fileList.size();
                    }
                });
            }

            //删除原参与人关联
            String deleteUserSql = "DELETE FROM F_EX_TrainUserRel WHERE TrainId = ?";
            jdbcTemplate.update(deleteUserSql, trainId);
            //批量插入参与人表
            String addUserSql = "INSERT INTO F_EX_TrainUserRel(TrainId,UserId,CreateTime,AffirmTime,SginStartTime,SginEndTime) VALUES (?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(addUserSql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, trainId);
                    ps.setInt(2, userIdList.get(i));
                    ps.setObject(3, day);
                    ps.setDate(4, null);
                    ps.setDate(5, null);
                    ps.setDate(6, null);
                }

                public int getBatchSize() {
                    return userIdList.size();
                }
            });

            //生成培训审核短信
            String exNotice = trainType.equals(TrainType.Online.getValue()) ? MessageModelConstants.TRAIN_PERMIT_NOTICE_ONLINE : MessageModelConstants.TRAIN_PERMIT_NOTICE_OFFLINE;

            List<Integer> idList = new ArrayList<>();
            idList.add(userInfo.getUserId());
            List<UserInfo> userInfos = userInfoService.getUserListByUserIds(idList);
            String departmentSql = "SELECT D.DepartmentName FROM F_PUB_Department D LEFT JOIN F_PUB_UserDepRel R ON R.DepartmentId = D.DepartmentId WHERE R.UserId = ?";
            String departmentName = jdbcTemplate.queryForObject(departmentSql, new Object[]{userInfo.getUserId()}, String.class);

            exNotice = exNotice.replace("【：部门】", departmentName);
            exNotice = exNotice.replace("【：姓名】", userInfos.get(0).getRealName());
            exNotice = exNotice.replace("【：培训】", title);
            //TODO 由于权限未定，经与产品经理协商，权限码roleId暂时写死
            String permitUserSql = "SELECT UserId FROM F_PUB_UserRoleRel WHERE RoleId = ?";
            List<Integer> permitUserList = jdbcTemplate.queryForList(permitUserSql, new Object[]{2}, Integer.class);
            messageManageService.addMessage(userInfo, MascotMessageType.TrainPermitNotice.getValue(), null, exNotice, permitUserList);
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service发起培训异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 培训详情
     *
     * @param userInfo   调用接口用户信息
     * @param trainTitle 培训标题
     * @return 1：成功，注：IsCancel true：该培训已取消；false：该培训正常显示
     */
    @Override
    public Map<String, Object> queryTrainDetail(UserInfo userInfo, String trainTitle) {
        try {
            if (userInfo == null || StringUtils.hasText(trainTitle)) {
                logger.info("==========>Service培训消息详情必传参数为空！");
                return null;
            }
            String trainSql = "SELECT T.TrainId,T.TrainTitle,T.StartTime,T.EndTime,T.TrainSite,T.TrainFlag,T.Content,L.LecturerId,L.LecturerName,L.LecIntroduction,L.LecHeadUrl,T.TrainStatus FROM F_EX_TrainInfo T LEFT JOIN F_EX_LecturerInfo L ON T.LecturerId = L.LecturerId WHERE T.Flag = ? AND L.Flag = ? AND T.TrainTitle = ?";
            Map<String, Object> trainInfo;
            try {
                trainInfo = jdbcTemplate.queryForMap(trainSql, FlagType.Effective.getValue(), FlagType.Effective.getValue(), trainTitle);
                trainInfo.put("IsCancel", JudFlag.N.getValue());
            } catch (EmptyResultDataAccessException e) {
                //该培训已取消
                trainInfo = new HashMap<>();
                trainInfo.put("IsCancel", JudFlag.Y.getValue());
            }

            return trainInfo;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service培训审核消息详情异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 培训审核
     *
     * @param userInfo 调用接口用户信息
     * @param trainId  培训Id
     * @param optionId 操作Id 2：通过；4驳回
     * @return 1：成功，其他失败
     */
    @Override
    public Integer updateTrainStatus(UserInfo userInfo, Integer trainId, Integer optionId) {
        try {
            if (userInfo == null || trainId == null || optionId == null) {
                logger.info("==========>Service培训审核必传参数为空！");
                return null;
            }
            String trainSql = "SELECT TrainTitle,TrainType,CreateUserId FROM F_EX_TrainInfo WHERE TrainId = ?";
            Map<String, Object> train;
            try {
                train = jdbcTemplate.queryForMap(trainSql, trainId);
            } catch (EmptyResultDataAccessException e) {
                return null;
            }

            String upTrainSql = "UPDATE F_EX_TrainInfo SET TrainStatus = ? WHERE TrainId = ?";
            jdbcTemplate.update(upTrainSql, optionId, trainId);

            if (optionId.equals(TrainStatus.Pass.getValue())) {
                //生成培训邀请消息
                String exNotice = train.get("TrainType").equals(TrainType.Online.getValue()) ? MessageModelConstants.TRAIN_NOTICE_ONLINE : MessageModelConstants.TRAIN_NOTICE_OFFLINE;
                exNotice = exNotice.replace("【：培训】", train.get("TrainTitle").toString());
                String userSql = "SELECT UserId FROM F_EX_TrainUserRel WHERE TrainId = ?";
                List<Integer> userList = jdbcTemplate.queryForList(userSql, new Object[]{trainId}, Integer.class);
                messageManageService.addMessage(userInfo, MascotMessageType.TrainNotice.getValue(), null, exNotice, userList);
            }

            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service培训审核异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 培训确认
     *
     * @param userInfo 调用接口用户信息
     * @param trainId  培训Id
     * @return 1：成功，其他失败
     */
    @Override
    public Integer updateAffirmFlag(UserInfo userInfo, Integer trainId) {
        try {
            if (userInfo == null || trainId == null) {
                logger.info("==========>Service培训确认必传参数为空！");
                return null;
            }
            String upTrainSql = "UPDATE F_EX_TrainUserRel SET AffirmTime = ? WHERE TrainId = ? AND UserId = ?";
            jdbcTemplate.update(upTrainSql, new Date(), trainId, userInfo.getUserId());
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service培训确认异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 培训参与人及签到表
     *
     * @param userInfo 调用接口用户信息
     * @param trainId  培训Id
     * @return
     */
    @Override
    public List<Map<String, Object>> trainUserList(UserInfo userInfo, Integer trainId, Integer pageIndex, Integer pageSize) {
        try {
            if (userInfo == null || trainId == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service培训参与人及签到表必传参数为空！");
                return null;
            }
            String titleSql = "SELECT TrainTitle FROM F_EX_TrainInfo WHERE TrainId = ?";
            String title = jdbcTemplate.queryForObject(titleSql, new Object[]{trainId}, String.class);
            title = "%《" + title + "》%";
            String messageIdSql = "SELECT MessageId FROM F_PUB_Message WHERE MessageTitle Like ?";
            Integer messageId = jdbcTemplate.queryForObject(messageIdSql, new Object[]{title}, Integer.class);
            Integer row = pageUtil.getRow(pageIndex, pageSize);
            List<Map<String, Object>> allList = new ArrayList<>();
            String sql = "SELECT T.TrainId,T.UserId,T.CreateTime,T.AffirmTime,T.SginStartTime,T.SginEndTime,M.ReadSign FROM F_EX_TrainUserRel T LEFT JOIN F_PUB_UserMsgRel M ON T.UserId = M.UserId WHERE T.TrainId = ? AND M.MessageId = ? LIMIT ?,?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, trainId, messageId, row, pageSize);
            List<Integer> userIdList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    userIdList.add(Integer.valueOf(map.get("UserId").toString()));
                }
                List<UserInfo> userInfos = userInfoService.getUserListByUserIds(userIdList);
                for (Map<String, Object> map : list) {
                    for (UserInfo us : userInfos) {
                        if (map.get("UserId").equals(us.getUserId())) {
                            map.put("RealName", us.getRealName());
                            allList.add(map);
                        }
                    }
                }
            }

            return allList;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service培训参与人及签到表异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 培训参与人及签到表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param trainId  培训Id
     * @return
     */
    @Override
    public Integer trainUserListCount(UserInfo userInfo, Integer trainId) {
        try {
            if (userInfo == null || trainId == null) {
                logger.info("==========>Service培训参与人及签到表总条数必传参数为空！");
                return null;
            }
            List<Map<String, Object>> allList = new ArrayList<>();
            String sql = "SELECT COUNT(1) FROM F_EX_TrainUserRel WHERE TrainId = ?";
            Integer count;
            try{
                count = jdbcTemplate.queryForObject(sql, new Object[]{trainId},Integer.class);
            }catch (EmptyResultDataAccessException e){
                count = 0;
            }
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service培训参与人及签到表总条数异常：");
            ep.printStackTrace();
            return null;
        }
    }

}
