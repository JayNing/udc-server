package com.mascot.service.impl;

import com.interfaces.mascot.DepartmentManageService;
import com.thrift.common.body.Department;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 部门管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/27
 */
@Service(value = "departmentManageService")
public class DepartmentManageServiceImpl extends BasicServiceImpl implements DepartmentManageService {

    private final static Log logger = LogFactory.getLog(DepartmentManageServiceImpl.class);

    /**
     * 获取部门列表
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public List<Department> getDepartmentList(UserInfo userInfo) {
        try {
            if (userInfo != null) {

                String sql = "SELECT DepartmentId, DepartmentName, Description, ParentDepId FROM F_PUB_Department WHERE Flag = 1";
                List<Department> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
                    Department department = new Department();
                    department.setDepartmentId(rs.getInt("DepartmentId"));
                    department.setDepartmentName(rs.getString("DepartmentName"));
                    department.setDescription(rs.getString("Description"));
                    department.setParentDepId(rs.getInt("ParentDepId"));
                    return department;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取部门列表成功!");
                return list;

            } else {
                logger.info("==========>Service根据moduleId获取部门列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取部门列表列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 新增部门
     *
     * @param userInfo       调用接口用户信息
     * @param departmentName 部门名称
     * @return result -1:已存在同名部门
     */
    @Override
    public Integer addDepartment(UserInfo userInfo, String departmentName) {
        try {
            if (userInfo != null && StringUtils.hasText(departmentName)) {
                Department depmt = getDepartmentDetailByDepartmentName(userInfo, departmentName);
                Integer result;
                if (depmt == null) {
                    String sql1 = "INSERT INTO F_PUB_Department(DepartmentName,Description,ParentDepId,Flag) VALUES (?,?,?,1)";
                    result = jdbcTemplate.update(sql1, departmentName, null, null);
                } else if (depmt.getFlag() == 2) {
                    String sql2 = "update F_PUB_Department set Flag = 1 where DepartmentId = ?";
                    result = jdbcTemplate.update(sql2, depmt.getDepartmentId());
                } else {
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增部门成功!");
                return result;

            } else {
                logger.info("==========>Service新增部门必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增部门异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 获取部门详情
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return department
     */
    @Override
    public Department getDepartmentDetail(UserInfo userInfo, Integer departmentId) {
        try {
            if (userInfo != null && departmentId != null) {

                String sql = "SELECT DepartmentId, DepartmentName, Description, ParentDepId, Flag FROM F_PUB_Department WHERE DepartmentId = ?";
                try {
                    Department department = jdbcTemplate.queryForObject(sql, new Object[]{departmentId}, (rs, rowNum) -> {
                        Department dep = new Department();
                        dep.setDepartmentId(rs.getInt("DepartmentId"));
                        dep.setDepartmentName(rs.getString("DepartmentName"));
                        dep.setDescription(rs.getString("Description"));
                        dep.setParentDepId(rs.getInt("ParentDepId"));
                        dep.setFlag(rs.getInt("Flag"));
                        return dep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取部门详情成功!");
                    return department;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service获取部门详情接口查询结果为空！");
                    e.printStackTrace();
                    return null;
                }

            } else {
                logger.info("==========>Service获取部门详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取部门详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑修改部门
     *
     * @param userInfo       调用接口用户信息
     * @param departmentId   部门编号
     * @param departmentName 部门名称
     * @return result
     */
    @Override
    public Integer updateDepartment(UserInfo userInfo, Integer departmentId, String departmentName) {
        try {
            if (userInfo != null && departmentId != null && StringUtils.hasText(departmentName)) {

                String sql = "update F_PUB_Department set DepartmentName = ? where DepartmentId = ?";
                Integer result = jdbcTemplate.update(sql, departmentName, departmentId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改部门成功!");
                return result;

            } else {
                logger.info("==========>Service编辑修改部门必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改部门异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 删除部门
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return result
     */
    @Override
    public Integer deleteDepartment(UserInfo userInfo, Integer departmentId) {
        try {
            if (userInfo != null && departmentId != null) {

                String sql = "update F_PUB_Department set Flag = 2 where DepartmentId = ?";
                Integer result = jdbcTemplate.update(sql, departmentId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除部门成功!");
                return result;

            } else {
                logger.info("==========>Service删除部门必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除部门异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据部门名称获取部门详情
     *
     * @param userInfo       调用接口用户信息
     * @param departmentName 部门名称
     * @return department
     */
    public Department getDepartmentDetailByDepartmentName(UserInfo userInfo, String departmentName) {
        try {
            if (userInfo != null && StringUtils.hasText(departmentName)) {

                String sql = "SELECT DepartmentId, DepartmentName, Description, ParentDepId, Flag FROM F_PUB_Department WHERE DepartmentName = ?";
                try {
                    Department department = jdbcTemplate.queryForObject(sql, new Object[]{departmentName}, (rs, rowNum) -> {
                        Department dep = new Department();
                        dep.setDepartmentId(rs.getInt("DepartmentId"));
                        dep.setDepartmentName(rs.getString("DepartmentName"));
                        dep.setDescription(rs.getString("Description"));
                        dep.setParentDepId(rs.getInt("ParentDepId"));
                        dep.setFlag(rs.getInt("Flag"));
                        return dep;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据部门名称获取部门详情成功!");
                    return department;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据部门名称获取部门详情接口查询结果为空！");
                    return null;
                }

            } else {
                logger.info("==========>Service根据部门名称获取部门详情必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据部门名称获取部门详情列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 根据部门Id获取部门成员列表
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return department
     */
    @Override
    public List<UserInfo> getUserInfoByDepartment(UserInfo userInfo, Integer departmentId) {
        try {
            if (userInfo != null && departmentId != null) {

                String sql = "SELECT UserId FROM F_PUB_UserDepRel WHERE DepartmentId = ?";
                List<Integer> idList = jdbcTemplate.queryForList(sql,new Object[]{departmentId},Integer.class);
                List<UserInfo> list;
                if(idList!=null&&idList.size()>0){
                    list = userInfoService.getUserListByUserIds(idList);
                }else{
                    list = null;
                }
               return list;

            } else {
                logger.info("==========>Service根据部门Id获取部门成员必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据部门Id获取部门成员列表异常：");
            ep.printStackTrace();
            return null;
        }

    }

}

