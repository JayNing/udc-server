package com.mascot.service.impl;

import com.interfaces.mascot.RepositoryCategoryService;
import com.thrift.common.body.RepositoryCategory;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 知识仓库分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/3
 */
@Service(value = "repositoryCategoryService")
public class RepositoryCategoryServiceImpl extends BasicServiceImpl implements RepositoryCategoryService {

    private final static Log logger = LogFactory.getLog(RepositoryCategoryServiceImpl.class);

    /**
     * 获取知识分类目录列表
     *
     * @param userInfo       调用接口用户信息
     * @param repCatParentId 知识仓库分类的父类id
     * @return list
     */
    @Override
    public List<RepositoryCategory> getRepositoryCategoryList(UserInfo userInfo, Integer repCatParentId) {
        try {
            if (userInfo != null && repCatParentId != null) {

                String sql = "SELECT RepCatId, RepCatParentId, RepCatName FROM F_KNLG_RepositoryCategory WHERE Flag = 1 AND RepCatParentId = ? ORDER BY CreateTime DESC ";
                List<RepositoryCategory> list = jdbcTemplate.query(sql, new Object[]{repCatParentId}, (rs, rowNum) -> {
                    RepositoryCategory repositoryCategory = new RepositoryCategory();
                    repositoryCategory.setRepCatId(rs.getInt("RepCatId"));
                    repositoryCategory.setRepCatParentId(rs.getInt("RepCatParentId"));
                    repositoryCategory.setRepCatName(rs.getString("RepCatName"));
                    return repositoryCategory;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识分类目录列表成功!");
                return list;

            } else {
                logger.info("==========>Service获取知识分类目录列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取知识分类目录列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 新增分类目录
     *
     * @param userInfo       调用接口用户信息
     * @param repCatParentId 知识仓库分类的父类id
     * @param repCatName     目录名称
     * @return result -1:违反唯一约束;null:异常
     */
    @Override
    public Integer addRepositoryCategory1(UserInfo userInfo, Integer repCatParentId, String repCatName) {
        try {
            if (userInfo != null && StringUtils.hasText(repCatName)) {
                RepositoryCategory repositoryCategory = getRepositoryCategoryDetailByRepCatName(userInfo, repCatName);
                Integer result;
                repCatParentId = repCatParentId==null?0:repCatParentId;
                if (repositoryCategory == null) {
                    String sql1 = "INSERT INTO F_KNLG_RepositoryCategory(RepCatParentId,RepCatName,CreateTime,Flag) VALUES (?,?,?,1)";
                    result = jdbcTemplate.update(sql1, repCatParentId, repCatName, new Date());
                } else if (repositoryCategory.getFlag() == 2) {
                    String sql2 = "update F_KNLG_RepositoryCategory set Flag = 1 , RepCatParentId = ?, CreateTime = ? where RepCatId = ?";
                    result = jdbcTemplate.update(sql2, repCatParentId, new Date(), repositoryCategory.getRepCatId());
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
     * 获取知识分类详情
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 分类编号
     * @return repositoryCategory
     */
    @Override
    public RepositoryCategory getRepositoryCategoryDetail(UserInfo userInfo, Integer repCatId) {
        try {
            if (userInfo != null && repCatId != null) {

                String sql = "SELECT RepCatId, RepCatParentId, RepCatName, Flag FROM F_KNLG_RepositoryCategory WHERE RepCatId = ?";
                try {
                    RepositoryCategory repositoryCategory = jdbcTemplate.queryForObject(sql, new Object[]{repCatId}, (rs, rowNum) -> {
                        RepositoryCategory rep = new RepositoryCategory();
                        rep.setRepCatId(rs.getInt("RepCatId"));
                        rep.setRepCatParentId(rs.getInt("RepCatParentId"));
                        rep.setRepCatName(rs.getString("RepCatName"));
                        rep.setFlag(rs.getInt("Flag"));
                        return rep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识分类详情成功!");
                    return repositoryCategory;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取知识分类详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service获取知识分类详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取知识分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑修改知识分类
     *
     * @param userInfo   调用接口用户信息
     * @param repCatId   分类编号
     * @param repCatName 分类名称
     * @return result
     */
    @Override
    public Integer updateRepositoryCategory(UserInfo userInfo, Integer repCatId, String repCatName) {
        try {
            if (userInfo != null && repCatId != null && StringUtils.hasText(repCatName)) {

                String sql = "update F_KNLG_RepositoryCategory set RepCatName = ? where RepCatId = ?";
                Integer result = jdbcTemplate.update(sql, repCatName, repCatId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改知识分类成功!");
                return result;

            } else {
                logger.info("==========>Service编辑修改知识分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改知识分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除知识分类
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 分类编号
     * @return result
     */
    @Override
    public Integer deleteRepositoryCategory(UserInfo userInfo, Integer repCatId) {
        try {
            if (userInfo != null && repCatId != null) {

                //获取所有下级
                List<Integer> idList = getAllRepositoryCategoryByParentId(userInfo, repCatId);
                idList.add(repCatId);
                String sql = "update F_KNLG_RepositoryCategory set Flag = 2 where RepCatId IN (:param)";

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("param", idList);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                Integer result = jdbc.update(sql, paramMap);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除知识分类成功!");
                return result;

            } else {
                logger.info("==========>Service删除知识分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除知识分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据上级目录获取下一级目录
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 上级级分类编号
     * @return repCatIds 下级分类Id字符串，若无二级分类，则返回""
     */
    @Override
    public String getNextRepositoryCategoryByParentId(UserInfo userInfo, Integer repCatId) {
        try {
            if (userInfo != null && repCatId != null) {
                String repCatIds = "";
                String sql = "SELECT RepCatId, RepCatParentId, RepCatName, Flag FROM F_KNLG_RepositoryCategory WHERE Flag = 1 AND RepCatParentId = ?";
                List<RepositoryCategory> list = jdbcTemplate.query(sql, new Object[]{repCatId}, (rs, rowNum) -> {
                    RepositoryCategory rep = new RepositoryCategory();
                    rep.setRepCatId(rs.getInt("RepCatId"));
                    rep.setRepCatParentId(rs.getInt("RepCatParentId"));
                    rep.setRepCatName(rs.getString("RepCatName"));
                    rep.setFlag(rs.getInt("Flag"));
                    return rep;
                });
                if (list != null&&list.size()>0) {
                    for (RepositoryCategory r : list) {
                        repCatIds = repCatIds + r.getRepCatId() + ",";
                    }
                    repCatIds = repCatIds.substring(0, repCatIds.length() - 1);
                }

                return repCatIds;

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
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param repCatId 一级分类编号
     * @return IdList
     */
    @Override
    public List<Integer> getAllRepositoryCategoryByParentId(UserInfo userInfo, Integer repCatId) {
        try {
            if (userInfo != null && repCatId != null) {
                List<Integer> listAll = new ArrayList<>();
                List<Integer> list = new ArrayList<>();
                list.add(repCatId);
                do {
                    String sql = "SELECT RepCatId FROM F_KNLG_RepositoryCategory WHERE Flag = 1 AND RepCatParentId IN (:param)";

                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("param", list);

                    NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                    list = jdbc.queryForList(sql, paramMap, Integer.class);

                    listAll.addAll(list);

                } while (list.size() > 0);

                return listAll;

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
     * @param userInfo   调用接口用户信息
     * @param repCatName 分类名称
     * @return repositoryCategory
     */
    public RepositoryCategory getRepositoryCategoryDetailByRepCatName(UserInfo userInfo, String repCatName) {
        try {
            if (userInfo != null && StringUtils.hasText(repCatName)) {

                String sql = "SELECT RepCatId, RepCatParentId, RepCatName, Flag FROM F_KNLG_RepositoryCategory WHERE RepCatName = ?";
                try {
                    RepositoryCategory repositoryCategory = jdbcTemplate.queryForObject(sql, new Object[]{repCatName}, (rs, rowNum) -> {
                        RepositoryCategory rep = new RepositoryCategory();
                        rep.setRepCatId(rs.getInt("RepCatId"));
                        rep.setRepCatParentId(rs.getInt("RepCatParentId"));
                        rep.setRepCatName(rs.getString("RepCatName"));
                        rep.setFlag(rs.getInt("Flag"));
                        return rep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取分类详情成功!");
                    return repositoryCategory;
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

