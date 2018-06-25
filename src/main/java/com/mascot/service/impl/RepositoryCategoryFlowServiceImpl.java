package com.mascot.service.impl;

import com.interfaces.mascot.RepositoryCategoryFlowService;
import com.interfaces.mascot.RepositoryCategoryFlowService;
import com.thrift.common.body.RepositoryCategoryFlow;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 知识仓库流程分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/3
 */
@Service(value = "repositoryCategoryFlowService")
public class RepositoryCategoryFlowServiceImpl extends BasicServiceImpl implements RepositoryCategoryFlowService {

    private final static Log logger = LogFactory.getLog(RepositoryCategoryFlowServiceImpl.class);

    /**
     * 获取知识流程分类目录列表
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public List<RepositoryCategoryFlow> getRepositoryCategoryFlowList(UserInfo userInfo) {
        try {
            if (userInfo != null) {

                String sql = "SELECT FlowId, FlowName FROM F_KNLG_RepositoryCategoryFlow WHERE FlowFlag = 1 ORDER BY CreateTime DESC ";
                List<RepositoryCategoryFlow> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
                    RepositoryCategoryFlow repositoryCategoryFlow = new RepositoryCategoryFlow();
                    repositoryCategoryFlow.setFlowId(rs.getInt("FlowId"));
                    repositoryCategoryFlow.setFlowName(rs.getString("FlowName"));
                    return repositoryCategoryFlow;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识流程分类目录列表成功!");
                return list;

            } else {
                logger.info("==========>Service获取知识流程分类目录列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取知识流程分类目录列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 新增流程分类目录
     *
     * @param userInfo 调用接口用户信息
     * @param flowName 目录名称
     * @return result -1:违反唯一约束;null:异常
     */
    @Override
    public Integer addRepositoryCategoryFlow(UserInfo userInfo, String flowName) {
        try {
            if (userInfo != null && StringUtils.hasText(flowName)) {
                RepositoryCategoryFlow repositoryCategoryFlow = getRepositoryCategoryFlowDetailByFlowName(userInfo, flowName);
                Integer result;
                if (repositoryCategoryFlow == null) {
                    String sql1 = "INSERT INTO F_KNLG_RepositoryCategoryFlow(FlowName,CreateTime,FlowFlag) VALUES (?,?,1)";
                    result = jdbcTemplate.update(sql1, flowName, new Date());
                } else if (repositoryCategoryFlow.getFlowFlag() == 2) {
                    String sql2 = "update F_KNLG_RepositoryCategoryFlow set FlowFlag = 1, CreateTime = ? where FlowId = ?";
                    result = jdbcTemplate.update(sql2, new Date(), repositoryCategoryFlow.getFlowId());
                } else {
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增流程分类目录成功!");
                return result;

            } else {
                logger.info("==========>Service新增流程分类目录必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增流程分类目录异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 获取知识流程分类详情
     *
     * @param userInfo 调用接口用户信息
     * @param flowId   流程分类编号
     * @return repositoryCategoryFlow
     */
    @Override
    public RepositoryCategoryFlow getRepositoryCategoryFlowDetail(UserInfo userInfo, Integer flowId) {
        try {
            if (userInfo != null && flowId != null) {

                String sql = "SELECT FlowId, FlowName, FlowFlag FROM F_KNLG_RepositoryCategoryFlow WHERE FlowId = ?";
                try {
                    RepositoryCategoryFlow repositoryCategoryFlow = jdbcTemplate.queryForObject(sql, new Object[]{flowId}, (rs, rowNum) -> {
                        RepositoryCategoryFlow rep = new RepositoryCategoryFlow();
                        rep.setFlowId(rs.getInt("FlowId"));
                        rep.setFlowName(rs.getString("FlowName"));
                        rep.setFlowFlag(rs.getInt("FlowFlag"));
                        return rep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识流程分类详情成功!");
                    return repositoryCategoryFlow;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识流程分类详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service获取知识流程分类详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取知识流程分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑修改知识流程分类
     *
     * @param userInfo 调用接口用户信息
     * @param flowId   流程分类编号
     * @param flowName 流程分类名称
     * @return result
     */
    @Override
    public Integer updateRepositoryCategoryFlow(UserInfo userInfo, Integer flowId, String flowName) {
        try {
            if (userInfo != null && flowId != null && StringUtils.hasText(flowName)) {

                String sql = "update F_KNLG_RepositoryCategoryFlow set FlowName = ? where FlowId = ?";
                Integer result = jdbcTemplate.update(sql, flowName, flowId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改知识流程分类成功!");
                return result;

            } else {
                logger.info("==========>Service编辑修改知识流程分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改知识流程分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除知识流程分类
     *
     * @param userInfo 调用接口用户信息
     * @param flowId   流程分类编号
     * @return result
     */
    @Override
    public Integer deleteRepositoryCategoryFlow(UserInfo userInfo, Integer flowId) {
        try {
            if (userInfo != null && flowId != null) {

                String sql = "update F_KNLG_RepositoryCategoryFlow set FlowFlag = 2 where FlowId = ?";
                Integer result = jdbcTemplate.update(sql, flowId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除知识流程分类成功!");
                return result;

            } else {
                logger.info("==========>Service删除知识流程分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除知识流程分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据目录名称获取流程分类详情
     *
     * @param userInfo 调用接口用户信息
     * @param flowName 流程分类名称
     * @return repositoryCategoryFlow
     */
    public RepositoryCategoryFlow getRepositoryCategoryFlowDetailByFlowName(UserInfo userInfo, String flowName) {
        try {
            if (userInfo != null && StringUtils.hasText(flowName)) {

                String sql = "SELECT FlowId, FlowName, FlowFlag FROM F_KNLG_RepositoryCategoryFlow WHERE FlowName = ?";
                try {
                    RepositoryCategoryFlow repositoryCategoryFlow = jdbcTemplate.queryForObject(sql, new Object[]{flowName}, (rs, rowNum) -> {
                        RepositoryCategoryFlow rep = new RepositoryCategoryFlow();
                        rep.setFlowId(rs.getInt("FlowId"));
                        rep.setFlowName(rs.getString("FlowName"));
                        rep.setFlowFlag(rs.getInt("FlowFlag"));
                        return rep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取流程分类详情成功!");
                    return repositoryCategoryFlow;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取流程分类详情接口查询结果为空！");
                    return null;
                }

            } else {
                throw new Exception("==========>Service根据目录名称获取流程分类详情必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据目录名称获取流程分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

}