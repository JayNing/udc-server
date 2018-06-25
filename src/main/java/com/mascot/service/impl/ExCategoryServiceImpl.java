package com.mascot.service.impl;

import com.interfaces.mascot.ExCategoryService;
import com.thrift.common.body.ExCategory;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 考试培训分类管理接口
 *
 * @author zhangmengyu
 * 2018/5/17
 */
@Service(value = "exCategoryService")
public class ExCategoryServiceImpl extends BasicServiceImpl implements ExCategoryService {

    private final static Log logger = LogFactory.getLog(ExCategoryServiceImpl.class);

    /**
     * 获取考试培训分类目录列表
     *
     * @param userInfo 调用接口用户信息
     * @param parentId 考试培训分类的父类id
     * @return list
     */
    @Override
    public List<ExCategory> getExCategoryList(UserInfo userInfo, Integer parentId) {
        try {
            if (userInfo != null && parentId != null) {

                String sql = "SELECT CategoryId, ParentId, CategoryName FROM F_EX_ExCategory WHERE Flag = 1 AND ParentId = ? ORDER BY CreateTime DESC";
                List<ExCategory> list = jdbcTemplate.query(sql, new Object[]{parentId}, (rs, rowNum) -> {
                    ExCategory exCategory = new ExCategory();
                    exCategory.setCategoryId(rs.getInt("CategoryId"));
                    exCategory.setParentId(rs.getInt("ParentId"));
                    exCategory.setCategoryName(rs.getString("CategoryName"));
                    return exCategory;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取考试培训分类目录列表成功!");
                return list;

            } else {
                logger.info("==========>Service获取考试培训分类目录列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取考试培训分类目录列表异常：");
            ep.printStackTrace();
            return null;
        }

    }


    /**
     * 新增分类目录
     *
     * @param userInfo     调用接口用户信息
     * @param parentId     考试培训分类的父类id
     * @param categoryName 目录名称
     * @return result -1:违反唯一约束；null：异常
     */
    @Override
    public Integer addExCategory1(UserInfo userInfo, Integer parentId, String categoryName) {
        try {
            if (userInfo != null && StringUtils.hasText(categoryName)) {
                ExCategory exCategory = getExCategoryDetailByCategoryName(userInfo, categoryName);
                Integer result;
                parentId = parentId==null?0:parentId;
                if (exCategory == null) {
                    String sql1 = "INSERT INTO F_EX_ExCategory(ParentId,CategoryName,CreateTime,Flag) VALUES (?,?,?,1)";
                    result = jdbcTemplate.update(sql1, parentId, categoryName, new Date());
                } else if (exCategory.getFlag() == 2) {
                    String sql2 = "update F_EX_ExCategory set Flag = 1 , ParentId = ?, CreateTime = ? where CategoryId = ?";
                    result = jdbcTemplate.update(sql2, parentId, new Date(), exCategory.getCategoryId());
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
     * 获取考试培训分类详情
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 分类编号
     * @return exCategory
     */
    @Override
    public ExCategory getExCategoryDetail(UserInfo userInfo, Integer categoryId) {
        try {
            if (userInfo != null && categoryId != null) {

                String sql = "SELECT CategoryId, ParentId, CategoryName, Flag FROM F_EX_ExCategory WHERE CategoryId = ?";
                try {
                    ExCategory exCategory = jdbcTemplate.queryForObject(sql, new Object[]{categoryId}, (rs, rowNum) -> {
                        ExCategory cate = new ExCategory();
                        cate.setCategoryId(rs.getInt("CategoryId"));
                        cate.setParentId(rs.getInt("ParentId"));
                        cate.setCategoryName(rs.getString("CategoryName"));
                        cate.setFlag(rs.getInt("Flag"));
                        return cate;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取考试培训分类详情成功!");
                    return exCategory;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取考试培训分类详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service获取考试培训分类详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取考试培训分类详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑修改考试培训分类
     *
     * @param userInfo     调用接口用户信息
     * @param categoryId   分类编号
     * @param categoryName 分类名称
     * @return result
     */
    @Override
    public Integer updateExCategory(UserInfo userInfo, Integer categoryId, String categoryName) {
        try {
            if (userInfo != null && categoryId != null && StringUtils.hasText(categoryName)) {

                String sql = "update F_EX_ExCategory set CategoryName = ? where CategoryId = ?";
                Integer result = jdbcTemplate.update(sql, categoryName, categoryId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改考试培训分类成功!");
                return result;

            } else {
                logger.info("==========>Service编辑修改考试培训分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改考试培训分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除考试培训分类
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 分类编号
     * @return result
     */
    @Override
    public Integer deleteExCategory(UserInfo userInfo, Integer categoryId) {
        try {
            if (userInfo != null && categoryId != null) {

                //获取所有下级
                List<Integer> idList = getAllExCategoryByParentId(userInfo, categoryId);
                idList.add(categoryId);
                String sql = "update F_EX_ExCategory set Flag = 2 where CategoryId IN (:param)";

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("param", idList);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                Integer result = jdbc.update(sql, paramMap);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除考试培训分类成功!");
                return result;

            } else {
                logger.info("==========>Service删除考试培训分类必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除考试培训分类异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据上级目录获取下一级目录
     *
     * @param userInfo   调用接口用户信息
     * @param categoryId 上级级分类编号
     * @return categoryIds 下级分类Id字符串，若无二级分类，则返回""
     */
    @Override
    public String getNextExCategoryByParentId(UserInfo userInfo, Integer categoryId) {
        try {
            if (userInfo != null && categoryId != null) {
                String categoryIds = "";
                String sql = "SELECT CategoryId, ParentId, CategoryName, Flag FROM F_EX_ExCategory WHERE Flag = 1 AND ParentId = ?";
                List<ExCategory> list = jdbcTemplate.query(sql, new Object[]{categoryId}, (rs, rowNum) -> {
                    ExCategory cate = new ExCategory();
                    cate.setCategoryId(rs.getInt("CategoryId"));
                    cate.setParentId(rs.getInt("ParentId"));
                    cate.setCategoryName(rs.getString("CategoryName"));
                    cate.setFlag(rs.getInt("Flag"));
                    return cate;
                });
                if (list != null&&list.size()>0) {
                    for (ExCategory r : list) {
                        categoryIds = categoryIds + r.getCategoryId() + ",";
                    }
                    categoryIds = categoryIds.substring(0, categoryIds.length() - 1);
                }

                return categoryIds;

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
     * @param userInfo   调用接口用户信息
     * @param categoryId 一级分类编号
     * @return IdList
     */
    @Override
    public List<Integer> getAllExCategoryByParentId(UserInfo userInfo, Integer categoryId) {
        try {
            if (userInfo != null && categoryId != null) {
                List<Integer> listAll = new ArrayList<>();
                List<Integer> list = new ArrayList<>();
                list.add(categoryId);
                do {
                    String sql = "SELECT CategoryId FROM F_EX_ExCategory WHERE Flag = 1 AND ParentId IN (:param)";

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
     * @param userInfo     调用接口用户信息
     * @param categoryName 分类名称
     * @return exCategory
     */
    private ExCategory getExCategoryDetailByCategoryName(UserInfo userInfo, String categoryName) {
        try {
            if (userInfo != null && StringUtils.hasText(categoryName)) {

                String sql = "SELECT CategoryId, ParentId, CategoryName, Flag FROM F_EX_ExCategory WHERE CategoryName = ?";
                try {
                    ExCategory exCategory = jdbcTemplate.queryForObject(sql, new Object[]{categoryName}, (rs, rowNum) -> {
                        ExCategory cate = new ExCategory();
                        cate.setCategoryId(rs.getInt("CategoryId"));
                        cate.setParentId(rs.getInt("ParentId"));
                        cate.setCategoryName(rs.getString("CategoryName"));
                        cate.setFlag(rs.getInt("Flag"));
                        return cate;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据目录名称获取分类详情成功!");
                    return exCategory;
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

