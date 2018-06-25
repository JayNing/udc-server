package com.mascot.service.impl;

import com.interfaces.mascot.ModuleManageService;
import com.interfaces.mascot.RoleManageService;
import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.RoleInfo;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * 权限管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/13
 */
@Service(value = "roleManageService")
public class RoleManageServiceImpl extends BasicServiceImpl implements RoleManageService {

    private final static Log logger = LogFactory.getLog(RoleManageServiceImpl.class);

    @Autowired
    ModuleManageService moduleManageService;

    /**
     * 获取角色列表信息
     *
     * @param userInfo  调用接口用户信息
     * @param roleName  角色姓名
     * @param pageIndex 当前页 --- 不可为空
     * @param pageSize  当前页数据条数 --- 不可为空
     * @return list
     */
    @Override
    public List<RoleInfo> getRoleList(UserInfo userInfo, String roleName, Integer pageIndex, Integer pageSize) {
        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                String sql;
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                Object[] obj;
                if (StringUtils.hasText(roleName)) {
                    //若角色姓名不为空，则通过角色姓名模糊查询角色列表
                    roleName = "%" + roleName + "%";
                    sql = "SELECT RoleId, RoleName FROM F_PUB_RoleInfo WHERE Flag = 1 AND RoleName LIKE ? ORDER BY CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{roleName, row, pageSize};
                } else {
                    //若角色姓名为空，则查询所有角色列表
                    sql = "SELECT RoleId, RoleName FROM F_PUB_RoleInfo WHERE Flag = 1 ORDER BY CreateTime DESC LIMIT ?,?";
                    obj = new Object[]{row, pageSize};
                }

                List<RoleInfo> list = jdbcTemplate.query(sql, obj, (rs, rowNum) -> {
                    RoleInfo roleInfo = new RoleInfo();
                    roleInfo.setRoleId(rs.getInt("RoleId"));
                    roleInfo.setRoleName(rs.getString("RoleName"));
                    return roleInfo;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询角色列表成功!");
                return list;

            } else {
                logger.info("==========>Service查询角色列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service查询角色列表异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 获取角色列表信息总条数
     *
     * @param userInfo 调用接口用户信息
     * @param roleName 角色姓名
     * @return count
     */
    @Override
    public Integer getRoleListCount(UserInfo userInfo, String roleName) {
        try {
            if (userInfo != null) {
                String sql;
                Object[] obj;
                if (StringUtils.hasText(roleName)) {
                    //若角色姓名不为空，则通过角色姓名模糊查询角色列表
                    roleName = "%" + roleName + "%";
                    sql = "SELECT COUNT(1) FROM F_PUB_RoleInfo WHERE Flag = 1 AND RoleName LIKE ?";
                    obj = new Object[]{roleName};
                } else {
                    //若角色姓名为空，则查询所有角色列表
                    sql = "SELECT COUNT(1) FROM F_PUB_RoleInfo WHERE Flag = ?";
                    obj = new Object[]{1};
                }

                Integer count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取角色列表信息总条数成功!");
                return count;

            } else {
                logger.info("==========>Service获取角色列表信息总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取角色列表信息总条数异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 添加角色信息
     *
     * @param userInfo     调用接口用户信息
     * @param roleName     角色姓名---不可为空
     * @param moduleIdList 模块Id列表
     * @return 1 成功；-2：角色名已存在
     */
    @Override
    public Integer saveRoleInfo(UserInfo userInfo, String roleName, List<Integer> moduleIdList) {

        try {
            if (userInfo != null && StringUtils.hasText(roleName) && moduleIdList != null) {
                //新增角色表角色
                RoleInfo ri = getRoleInfoByRoleName(userInfo, roleName);
                if (ri == null) {
                    String sql = "INSERT INTO F_PUB_RoleInfo(RoleName,CreateTime,Flag) VALUES (?,?,?)";
                    jdbcTemplate.update(sql, roleName, new Date(), 1);
                } else if (ri.getFlag() == 2) {
                    String sql2 = "update F_PUB_RoleInfo set CreateTime = ?,Flag = 1 where RoleId = ?";
                    jdbcTemplate.update(sql2, new Date(), ri.getRoleId());
                }else{
                    //角色名已存在
                    return -2;
                }
                //角色表新增成功,则批量新增关联表关联
                String sql = "INSERT INTO F_PUB_RoleModuleRel(RoleId,ModuleId) VALUES (?,?)";
                RoleInfo roleInfo = getRoleInfoByRoleName(userInfo, roleName);
                //获取所有模块Id
                Set<Integer> allModuleSet = new HashSet<>();
                List<Integer> allModuleList = new ArrayList<>();
                for (Integer moduleId : moduleIdList) {
                    List<Integer> moduleList = moduleManageService.getModuleInfoByParentId(userInfo, moduleId);
                    moduleList.add(moduleId);
                    if (moduleList.size() > 0) {
                        allModuleSet.addAll(moduleList);
                    }
                }
                allModuleList.addAll(allModuleSet);

                //删除原关联
                String sql2 = "DELETE FROM F_PUB_RoleModuleRel WHERE RoleId = ?";
                jdbcTemplate.update(sql2, roleInfo.getRoleId());
                //批量插入角色模块关联表
                int[] updateCounts = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, roleInfo.getRoleId());
                        ps.setInt(2, allModuleList.get(i));
                    }

                    public int getBatchSize() {
                        return allModuleList.size();
                    }
                });

                logger.info("======[" + userInfo.getUserId() + "]==========>Service添加角色接口调用成功！");
                return 1;
            } else {
                logger.info("==========>Service添加角色接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service添加角色接口调用异常：");
            ep.printStackTrace();
            return null;
        }


    }


    /**
     * 通过角色名称查询角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleName 角色名称
     * @return roleInfo
     */
    @Override
    public RoleInfo getRoleInfoByRoleName(UserInfo userInfo, String roleName) {

        try {
            if (userInfo != null && StringUtils.hasText(roleName)) {
                String sql = "SELECT RoleId, RoleName,Flag FROM F_PUB_RoleInfo WHERE RoleName = ?";
                try {
                    RoleInfo roleInfo = jdbcTemplate.queryForObject(sql, new Object[]{roleName}, (rs, rowNum) -> {
                        RoleInfo rol = new RoleInfo();
                        rol.setRoleId(rs.getInt("RoleId"));
                        rol.setRoleName(rs.getString("RoleName"));
                        rol.setFlag(rs.getInt("Flag"));
                        return rol;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过roleName查询角色详情接口调用成功！");
                    return roleInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过roleName查询角色详情接口查询结果为空！");
                    return null;
                }
            } else {
                logger.info("==========>Service通过roleName查询角色详情接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service通过roleName查询角色详情接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 通过角色id查询角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleId   角色编号
     * @return roleInfo
     */
    @Override
    public Map<String, Object> getRoleInfoDetailByRoleId(UserInfo userInfo, Integer roleId) {

        try {
            if (userInfo != null && roleId != null) {
                String sql = "SELECT RoleId, RoleName,Flag FROM F_PUB_RoleInfo WHERE RoleId = ?";
                try {
                    Map<String, Object> roleInfo = jdbcTemplate.queryForMap(sql, roleId);
                    List<Integer> roleIdList = new ArrayList<>();
                    roleIdList.add(roleId);
                    List<ModuleInfo> moduleList = getModuleInfoByRoleId(userInfo, roleIdList);
                    roleInfo.put("ModuleList", moduleList);

                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过roleId查询角色详情接口调用成功！");
                    return roleInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过roleId查询角色详情接口查询结果为空！");
                    return null;
                }
            } else {
                logger.info("==========>Service通过roleId查询角色详情接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service通过roleId查询角色详情接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据 moduleCode 获取模块信息
     *
     * @param userInfo   调用接口用户信息
     * @param moduleCode 模块权限码
     * @return moduleInfo
     */
    @Override
    public ModuleInfo getModuleDetailsByModuleCode(UserInfo userInfo, String moduleCode) {

        try {
            if (userInfo != null && StringUtils.hasText(moduleCode)) {
                String sql = "select ModuleId, ParentId, ModuleName, ModuleCode from F_PUB_ModuleInfo where ModuleCode = ? AND Flag = 1";

                try {
                    ModuleInfo moduleInfo = jdbcTemplate.queryForObject(sql, new Object[]{moduleCode}, (rs, rowNum) -> {
                        ModuleInfo mod = new ModuleInfo();
                        mod.setModuleId(rs.getInt("ModuleId"));
                        mod.setParentId(rs.getInt("ParentId"));
                        mod.setModuleName(rs.getString("ModuleName"));
                        mod.setModuleCode(rs.getString("ModuleCode"));
                        return mod;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据moduleCode获取模块详情接口调用成功！");
                    return moduleInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据moduleCode获取模块详情接口查询结果为空！");
                    return null;
                }
            } else {
                logger.info("==========>Service根据moduleCode获取模块详情接口必传参数为空！");
                return null;
            }

        } catch (Exception ep) {
            logger.debug("==========>Service根据moduleCode获取模块详情接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 编辑修改角色信息
     *
     * @param userInfo     调用接口用户信息
     * @param roleName     角色名称
     * @param moduleIdList 模块Id
     * @return result  1：修改成功
     */
    @Override
    public Integer updateRoleInfo(UserInfo userInfo, Integer roleId, String roleName, List<Integer> moduleIdList) {

        try {
            if (userInfo != null && roleId != null && StringUtils.hasText(roleName)) {

                String sql = "SELECT RoleName FROM F_PUB_RoleInfo WHERE Flag = 1 AND RoleId = ?";
                String oldRoleName = jdbcTemplate.queryForObject(sql,new Object[]{roleId},String.class);
                if(!roleName.equals(oldRoleName)){
                    //若需要修改角色名
                    RoleInfo ri = getRoleInfoByRoleName(userInfo, roleName);
                    if (ri == null) {
                        String sql1 = "UPDATE F_PUB_RoleInfo set RoleName = ? where RoleId = ?";
                        jdbcTemplate.update(sql1, roleName, roleId);
                    } else if (ri.getFlag() == 2) {
                        //删除原角色
                        deleteRoleInfo(userInfo,ri.getRoleId());
                        //再更改角色名
                        String sql1 = "UPDATE F_PUB_RoleInfo set RoleName = ? where RoleId = ?";
                        jdbcTemplate.update(sql1, roleName, roleId);
                    }else{
                        //角色名已存在
                        return -2;
                    }
                }

                //修改角色权限
                if (moduleIdList != null && moduleIdList.size() > 0) {
                    //批量新增关联表关联
                    String sql1 = "INSERT INTO F_PUB_RoleModuleRel(RoleId,ModuleId) VALUES (?,?)";
                    //获取所有模块Id
                    Set<Integer> allModuleSet = new HashSet<>();
                    List<Integer> allModuleList = new ArrayList<>();
                    for (Integer moduleId : moduleIdList) {
                        List<Integer> moduleList = moduleManageService.getModuleInfoByParentId(userInfo, moduleId);
                        moduleList.add(moduleId);
                        if (moduleList.size() > 0) {
                            allModuleSet.addAll(moduleList);
                        }
                    }
                    allModuleList.addAll(allModuleSet);

                    //删除原关联
                    String sql2 = "DELETE FROM F_PUB_RoleModuleRel WHERE RoleId = ?";
                    jdbcTemplate.update(sql2, roleId);
                    //批量插入角色模块关联表
                    int[] updateCounts = jdbcTemplate.batchUpdate(sql1, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, roleId);
                            ps.setInt(2, allModuleList.get(i));
                        }

                        public int getBatchSize() {
                            return allModuleList.size();
                        }
                    });
                } else {
                    //删除原关联
                    String sql2 = "DELETE FROM F_PUB_RoleModuleRel WHERE RoleId = ?";
                    jdbcTemplate.update(sql2, roleId);
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改角色信息接口调用成功！======>");
                return 1;
            } else {
                logger.info("==========>Service编辑修改角色信息接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改角色信息接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据 roleId 获取模块信息
     *
     * @param userInfo   调用接口用户信息
     * @param roleIdList 角色流水号
     * @return list
     */
    @Override
    public List<ModuleInfo> getModuleInfoByRoleId(UserInfo userInfo, List<Integer> roleIdList) {

        try {
            if (userInfo != null && roleIdList != null && roleIdList.size() > 0) {
                String sql = "select ModuleId, ParentId, ModuleName, ModuleCode from F_PUB_ModuleInfo where ModuleId in (select ModuleId from F_PUB_RoleModuleRel where RoleId IN (:param))";

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("param", roleIdList);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                List<ModuleInfo> moList = jdbc.query(sql, paramMap, (rs, rowNum) -> {
                    ModuleInfo moduleInfo = new ModuleInfo();
                    moduleInfo.setModuleId(rs.getInt("ModuleId"));
                    moduleInfo.setParentId(rs.getInt("ParentId"));
                    moduleInfo.setModuleName(rs.getString("ModuleName"));
                    moduleInfo.setModuleCode(rs.getString("ModuleCode"));
                    return moduleInfo;
                });
                Set<ModuleInfo> set = new HashSet<>();
                set.addAll(moList);
                List<ModuleInfo> list = new ArrayList<>();
                list.addAll(set);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service根据roleId获取模块详情接口调用成功！");
                return list;

            } else {
                logger.info("==========>Service根据roleId获取模块详情接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据roleId获取模块详情接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据 roleId 获取模块信息(web封装二维数组专用)
     *
     * @param userInfo   调用接口用户信息
     * @param roleIdList 角色流水号
     * @return list
     */
    @Override
    public List<ModuleInfo> getModuleInfoByRoleId2(UserInfo userInfo, List<Integer> roleIdList) {

        try {
            if (userInfo != null && roleIdList != null && roleIdList.size() > 0) {
                String sql = "select ModuleId, ParentId, ModuleName, ModuleCode from F_PUB_ModuleInfo where ModuleId in (select ModuleId from F_PUB_RoleModuleRel where RoleId IN (:param))";

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("param", roleIdList);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                List<ModuleInfo> moList = jdbc.query(sql, paramMap, (rs, rowNum) -> {
                    ModuleInfo moduleInfo = new ModuleInfo();
                    moduleInfo.setModuleId(rs.getInt("ModuleId"));
                    moduleInfo.setParentId(rs.getInt("ParentId"));
                    moduleInfo.setModuleName(rs.getString("ModuleName"));
                    moduleInfo.setModuleCode(rs.getString("ModuleCode"));
                    return moduleInfo;
                });
                Set<ModuleInfo> set = new HashSet<>();
                set.addAll(moList);
                List<ModuleInfo> list = new ArrayList<>();
                list.addAll(set);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service根据roleId获取模块列表(web专用)接口调用成功！");
                return list;

            } else {
                logger.info("==========>Service根据roleId获取模块列表(web专用)接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据roleId获取模块列表(web专用)接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 删除角色信息
     *
     * @param userInfo 调用接口用户信息
     * @param roleId   角色流水号
     * @return result 1:成功
     */
    @Override
    public Integer deleteRoleInfo(UserInfo userInfo, Integer roleId) {

        try {
            if (userInfo != null && roleId != null) {
                String sql = "UPDATE F_PUB_RoleInfo set Flag = 2 where RoleId = ?";
                jdbcTemplate.update(sql, roleId);
                String sql2 = "DELETE FROM F_PUB_RoleModuleRel where RoleId = ?";
                jdbcTemplate.update(sql2, roleId);
                String sql3 = "DELETE FROM F_PUB_UserRoleRel where RoleId = ?";
                jdbcTemplate.update(sql3, roleId);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除角色接口调用成功！=====>");
                return 1;
            } else {
                logger.info("==========>Service删除角色接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除角色接口调用异常！");
            ep.printStackTrace();
            return null;
        }
    }


}

