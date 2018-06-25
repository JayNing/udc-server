package com.mascot.service.impl;

import com.interfaces.mascot.EssayTypeService;
import com.thrift.common.body.EssayType;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 社区贴文分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/3
 */
@Service(value = "essayTypeService")
public class EssayTypeServiceImpl extends BasicServiceImpl implements EssayTypeService {

    private final static Log logger = LogFactory.getLog(EssayTypeServiceImpl.class);

    /**
     * 获取贴文分类目录列表
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 父类id --- 可为空
     * @return list
     */
    @Override
    public List<EssayType> getEssayTypeList(UserInfo userInfo, Integer parentId) {
        try {

            if (userInfo != null) {
                String sql;
                Object[] obj;
                if (parentId != null) {
                    sql = "SELECT EssayTypeId, EssayTypeName, ParentId FROM F_BBS_EssayType WHERE Flag = ? AND ParentId = ? ORDER BY CreateTime DESC";
                    obj = new Object[]{1, parentId};
                } else {
                    sql = "SELECT EssayTypeId, EssayTypeName, ParentId FROM F_BBS_EssayType WHERE Flag = ? ORDER BY CreateTime DESC";
                    obj = new Object[]{1};
                }
                List<EssayType> list = jdbcTemplate.query(sql, obj, (rs, rowNum) -> {
                    EssayType essayType = new EssayType();
                    essayType.setEssayTypeId(rs.getInt("EssayTypeId"));
                    essayType.setParentId(rs.getInt("ParentId"));
                    essayType.setEssayTypeName(rs.getString("EssayTypeName"));
                    return essayType;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取贴文分类目录列表成功!");
                return list;

            } else {
                logger.info("==========>Service获取贴文分类目录列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取贴文分类目录列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 新增分类目录
     *
     * @param userInfo      调用接口用户信息
     * @param parentId      父类id --- 可为空
     * @param essayTypeName 目录名称
     * @return result -1:违反唯一约束；null：异常
     */
    @Override
    public Integer addEssayType(UserInfo userInfo, Integer parentId, String essayTypeName) {
        try {
            if (userInfo != null && StringUtils.hasText(essayTypeName)) {
                EssayType essayType = getEssayTypeDetailByName(userInfo, essayTypeName);
                Integer result;
                parentId = parentId==null?0:parentId;
                if (essayType == null) {
                    String sql1 = "INSERT INTO F_BBS_EssayType(ParentId,EssayTypeName,CreateTime,Flag) VALUES (?,?,?,1)";
                    result = jdbcTemplate.update(sql1, parentId, essayTypeName, new Date());
                } else if (essayType.getFlag() == 2) {
                    String sql2 = "update F_BBS_EssayType set Flag = 1 , ParentId = ?, CreateTime = ? where EssayTypeId = ?";
                    result = jdbcTemplate.update(sql2, parentId, new Date(), essayType.getEssayTypeId());
                } else {
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增分类目录成功!");
                return result;

            } else {
                logger.info("==========>Service新增分类目录必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增分类目录异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 获取贴文分类详情
     *
     * @param userInfo    调用接口用户信息
     * @param essayTypeId 分类编号
     * @return essayType
     */
    @Override
    public EssayType getEssayTypeDetail(UserInfo userInfo, Integer essayTypeId) {
        try {
            if (userInfo != null && essayTypeId != null) {

                String sql = "SELECT EssayTypeId, ParentId, EssayTypeName, Flag FROM F_BBS_EssayType WHERE EssayTypeId = ?";
                try {
                    EssayType essayType = jdbcTemplate.queryForObject(sql, new Object[]{essayTypeId}, (rs, rowNum) -> {
                        EssayType ess = new EssayType();
                        ess.setEssayTypeId(rs.getInt("EssayTypeId"));
                        ess.setParentId(rs.getInt("ParentId"));
                        ess.setEssayTypeName(rs.getString("EssayTypeName"));
                        ess.setFlag(rs.getInt("Flag"));
                        return ess;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取贴文分类详情成功!");
                    return essayType;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取贴文分类详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service获取贴文分类详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取贴文分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑修改贴文分类
     *
     * @param userInfo      调用接口用户信息
     * @param essayTypeId   分类编号
     * @param essayTypeName 分类名称
     * @return result
     */
    @Override
    public Integer updateEssayType(UserInfo userInfo, Integer essayTypeId, String essayTypeName) {
        try {
            if (userInfo != null && essayTypeId != null && StringUtils.hasText(essayTypeName)) {

                String sql = "update F_BBS_EssayType set EssayTypeName = ? where EssayTypeId = ?";
                Integer result = jdbcTemplate.update(sql, essayTypeName, essayTypeId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改贴文分类成功!");
                return result;

            } else {
                logger.info("==========>Service编辑修改贴文分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改贴文分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除贴文分类
     *
     * @param userInfo    调用接口用户信息
     * @param essayTypeId 分类编号
     * @return result
     */
    @Override
    public Integer deleteEssayType(UserInfo userInfo, Integer essayTypeId) {
        try {
            if (userInfo != null && essayTypeId != null) {

                String sql = "update F_BBS_EssayType set Flag = 2 where EssayTypeId = ?";
                Integer result = jdbcTemplate.update(sql, essayTypeId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除贴文分类成功!");
                return result;

            } else {
                logger.info("==========>Service删除贴文分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除贴文分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 一级分类编号
     * @return list
     */
    @Override
    public List<EssayType> getEssayTypeListByParentId(UserInfo userInfo, Integer parentId) {
        try {
            if (userInfo != null && parentId != null) {
                String sql = "SELECT EssayTypeId, ParentId, EssayTypeName, Flag FROM F_BBS_EssayType WHERE ParentId = ?";
                List<EssayType> list = jdbcTemplate.query(sql, new Object[]{parentId}, (rs, rowNum) -> {
                    EssayType ess = new EssayType();
                    ess.setEssayTypeId(rs.getInt("EssayTypeId"));
                    ess.setParentId(rs.getInt("ParentId"));
                    ess.setEssayTypeName(rs.getString("EssayTypeName"));
                    ess.setFlag(rs.getInt("Flag"));
                    return ess;
                });
                return list;

            } else {
                throw new Exception("==========>Service根据上级目录获取所有下级目录必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据上级目录获取所有下级目录列表异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 根据目录名称获取分类详情
     *
     * @param userInfo      调用接口用户信息
     * @param essayTypeName 分类名称
     * @return essayType
     */
    public EssayType getEssayTypeDetailByName(UserInfo userInfo, String essayTypeName) {
        try {
            if (userInfo != null && StringUtils.hasText(essayTypeName)) {

                String sql = "SELECT EssayTypeId, ParentId, EssayTypeName, Flag FROM F_BBS_EssayType WHERE EssayTypeName = ?";
                try {
                    EssayType essayType = jdbcTemplate.queryForObject(sql, new Object[]{essayTypeName}, (rs, rowNum) -> {
                        EssayType ess = new EssayType();
                        ess.setEssayTypeId(rs.getInt("EssayTypeId"));
                        ess.setParentId(rs.getInt("ParentId"));
                        ess.setEssayTypeName(rs.getString("EssayTypeName"));
                        ess.setFlag(rs.getInt("Flag"));
                        return ess;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取分类详情成功!");
                    return essayType;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取分类详情接口查询结果为空！");
                    return null;
                }

            } else {
                throw new Exception("==========>Service根据目录名称获取分类详情必传参数为空！");
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据目录名称获取分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

}

