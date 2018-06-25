package com.mascot.service.impl;

import com.interfaces.mascot.MessageManageService;
import com.interfaces.mascot.TrainManageService;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.FlagType;
import com.thrift.common.define.JudFlag;
import com.thrift.common.define.MascotMessageType;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息管理接口实现
 *
 * @author zhangmengyu
 * 2018/6/2
 */
@Service(value = "messageManageService")
public class MessageManageServiceImpl extends BasicServiceImpl implements MessageManageService {

    private final static Log logger = LogFactory.getLog(MessageManageServiceImpl.class);
    @Autowired
    TrainManageService trainManageService;

    /**
     * 新增消息
     *
     * @param userInfo       调用接口用户信息
     * @param messageType    消息类型
     * @param messageContent 消息内容
     * @param userIdList     接收消息的人
     * @return result 1：成功; 其他：失败
     */
    @Override
    public Integer addMessage(UserInfo userInfo, Integer messageType, String title, String messageContent, List<Integer> userIdList) {
        try {
            if (userInfo == null || messageType == null || !StringUtils.hasText(messageContent) || userIdList == null || userIdList.size() == 0) {
                logger.info("==========>Service新增消息接口必传参数为空!");
                return null;
            }
            if (messageType.equals(MascotMessageType.Notice.getValue())  && title == null) {
                logger.info("==========>Service新增消息接口必传参数为空!");
                return null;
            }

            String addMsg = "INSERT INTO F_PUB_Message(MessageType,MessageContent,MessageFlag,CreateTime,MessageTitle) VALUES (?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            int i = 0;
                            PreparedStatement ps = conn.prepareStatement(addMsg, Statement.RETURN_GENERATED_KEYS);
                            ps.setInt(++i, messageType);
                            ps.setString(++i, messageContent);
                            ps.setInt(++i, FlagType.Effective.getValue());
                            ps.setObject(++i, new Date());
                            ps.setString(++i, messageType.equals(MascotMessageType.Notice.getValue()) ? title : messageContent);
                            return ps;
                        }
                    },
                    keyHolder);

            Integer messageId = keyHolder.getKey().intValue();


            //批量插入关联表
            String sql3 = "INSERT INTO F_PUB_UserMsgRel(MessageId,UserId,ReadSign) VALUES (?,?,?)";
            jdbcTemplate.batchUpdate(sql3, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, messageId);
                    ps.setInt(2, userIdList.get(i));
                    ps.setInt(3, JudFlag.N.getValue());
                }

                public int getBatchSize() {
                    return userIdList.size();
                }
            });

            logger.info("======[" + userInfo.getUserId() + "]==========>Service新增消息接口成功!");
            return 1;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service新增消息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询消息
     *
     * @param userInfo        调用接口用户信息
     * @param messageTypeList 类型Id列表
     * @param pageIndex       当前页
     * @param pageSize        每页条数
     * @return list
     */
    @Override
    public List<Map<String, Object>> queryMessage(UserInfo userInfo, List<Integer> messageTypeList, Integer pageIndex, Integer pageSize) {
        try {
            if (userInfo == null || pageIndex == null || pageSize == null) {
                logger.info("==========>Service查询消息接口必传参数为空!");
                return null;
            }
            Integer row = pageUtil.getRow(pageIndex, pageSize);
            String searchSql;
            Map<String, Object> paramMap = new HashMap<>();
            if (messageTypeList == null || messageTypeList.size() == 0) {
                searchSql = "SELECT M.MessageId,M.MessageType,M.MessageContent,M.MessageFlag,M.CreateTime,M.MessageTitle,R.ReadSign from F_PUB_Message M LEFT JOIN F_PUB_UserMsgRel R ON R.MessageId = M.MessageId WHERE R.UserId = :userId AND M.MessageFlag = :messageFlag AND M.MessageType != :messageType ORDER BY M.CreateTime DESC LIMIT :row,:pageSize";
            } else {
                searchSql = "SELECT M.MessageId,M.MessageType,M.MessageContent,M.MessageFlag,M.CreateTime,M.MessageTitle,R.ReadSign from F_PUB_Message M LEFT JOIN F_PUB_UserMsgRel R ON R.MessageId = M.MessageId WHERE M.MessageType IN (:messageTypeList) AND R.UserId = :userId AND M.MessageFlag = :messageFlag AND M.MessageType != :messageType ORDER BY M.CreateTime DESC LIMIT :row,:pageSize";
                paramMap.put("messageTypeList", messageTypeList);
            }

            paramMap.put("messageType", MascotMessageType.BbsNotice);
            paramMap.put("userId", userInfo.getUserId());
            paramMap.put("messageFlag", FlagType.Effective.getValue());
            paramMap.put("row", row);
            paramMap.put("pageSize", pageSize);

            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            List<Map<String, Object>> list = jdbc.queryForList(searchSql, paramMap);

            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询消息接口成功!");
            return list;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询消息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询消息总条数
     *
     * @param userInfo        调用接口用户信息
     * @param messageTypeList 类型Id列表
     * @return count
     */
    @Override
    public Integer queryMessageCount(UserInfo userInfo, List<Integer> messageTypeList) {
        try {
            if (userInfo == null) {
                logger.info("==========>Service查询消息接口必传参数为空!");
                return null;
            }
            String searchSql;
            Map<String, Object> paramMap = new HashMap<>();
            if (messageTypeList == null || messageTypeList.size() == 0) {
                searchSql = "SELECT COUNT(1) from F_PUB_Message M LEFT JOIN F_PUB_UserMsgRel R ON R.MessageId = M.MessageId WHERE R.UserId = :userId AND M.MessageFlag = :messageFlag";
            } else {
                searchSql = "SELECT COUNT(1) from F_PUB_Message M LEFT JOIN F_PUB_UserMsgRel R ON R.MessageId = M.MessageId WHERE M.MessageType IN (:messageTypeList) AND R.UserId = :userId AND M.MessageFlag = :messageFlag";
                paramMap.put("messageTypeList", messageTypeList);
            }
            paramMap.put("userId", userInfo.getUserId());
            paramMap.put("messageFlag", FlagType.Effective.getValue());

            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            Integer count;
            try {
                count = jdbc.queryForObject(searchSql, paramMap, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                count = 0;
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service查询消息总条数接口成功!");
            return count;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询消息总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 展示最新一条消息
     *
     * @param userInfo    调用接口用户信息
     * @param messageType 消息类型
     * @return map
     */
    @Override
    public Map<String, Object> queryNewestMessage(UserInfo userInfo, Integer messageType) {
        try {
            if (userInfo == null) {
                logger.info("==========>Service展示最新一条消息必传参数为空!");
                return null;
            }
            Map<String, Object> msg;
            String searchSql;
            Object obj;
            if (messageType != null) {
                searchSql = "SELECT M.MessageId,M.MessageType,M.MessageContent,M.MessageFlag,M.CreateTime,M.MessageTitle from F_PUB_Message M WHERE M.MessageType = ? AND M.MessageFlag = ? ORDER BY M.CreateTime DESC LIMIT 1";
                try {
                    msg = jdbcTemplate.queryForMap(searchSql, messageType, FlagType.Effective.getValue());
                } catch (EmptyResultDataAccessException e) {
                    msg = null;
                }
            } else {
                searchSql = "SELECT M.MessageId,M.MessageType,M.MessageContent,M.MessageFlag,M.CreateTime,M.MessageTitle from F_PUB_Message M WHERE M.MessageFlag = ? ORDER BY M.CreateTime DESC LIMIT 1";
                try {
                    msg = jdbcTemplate.queryForMap(searchSql, FlagType.Effective.getValue());
                } catch (EmptyResultDataAccessException e) {
                    msg = null;
                }
            }

            logger.info("======[" + userInfo.getUserId() + "]==========>Service展示最新一条消息成功!");
            return msg;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service展示最新一条消息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 展示消息详情
     *
     * @param userInfo  调用接口用户信息
     * @param messageId 消息Id
     * @return map
     */
    @Override
    public Map<String, Object> queryMessageDetail(UserInfo userInfo, Integer messageId) {
        try {
            if (userInfo == null) {
                logger.info("==========>Service展示最新一条消息必传参数为空!");
                return null;
            }
            String searchSql = "SELECT M.MessageId,M.MessageType,M.MessageContent,M.MessageFlag,M.CreateTime,M.MessageTitle from F_PUB_Message M WHERE M.MessageId = ? ";
            Map<String, Object> msg = jdbcTemplate.queryForMap(searchSql, messageId);
            if (msg.get("MessageType").equals(MascotMessageType.TrainNotice.getValue()) || msg.get("MessageType").equals(MascotMessageType.TrainPermitNotice.getValue())) {
                String trainTitle = org.apache.commons.lang.StringUtils.substringBeforeLast(msg.get("MessageContent").toString(), "》");
                trainTitle = org.apache.commons.lang.StringUtils.substringAfter(trainTitle, "《");
                Map<String, Object> trainInfo = trainManageService.queryTrainDetail(userInfo, trainTitle);
                if (trainInfo == null) {
                    return null;
                } else if (trainInfo.get("IsCancel").equals(JudFlag.N.getValue())) {
                    msg.putAll(trainInfo);
                }
            }
            String messageRead = "UPDATE F_PUB_UserMsgRel SET ReadSign = ? WHERE MessageId = ? AND UserId = ?";
            jdbcTemplate.update(messageRead, JudFlag.Y.getValue(), messageId, userInfo.getUserId());

            logger.info("======[" + userInfo.getUserId() + "]==========>Service展示消息详情成功!");
            return msg;

        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service展示消息详情异常：");
            ep.printStackTrace();
            return null;
        }

    }

}

