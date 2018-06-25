package com.mascot.service.impl;

import com.interfaces.mascot.*;
import com.thrift.common.body.Department;
import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.RoleInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.AppType;
import com.thrift.common.define.MascotMessageType;
import com.thrift.common.define.RespType;
import com.thrift.common.define.ResponseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * 账号管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/23
 */
@Service(value = "userManageService")
public class UserManageServiceImpl extends BasicServiceImpl implements UserManageService {

    private final static Log logger = LogFactory.getLog(UserManageServiceImpl.class);

    @Autowired
    DepartmentManageService departmentManageService;

    @Autowired
    RoleManageService roleManageService;

    @Autowired
    ModuleManageService moduleManageService;

    @Autowired
    MessageManageService messageManageService;

    /**
     * 获取账号管理列表信息
     *
     * @param userInfo     调用接口用户信息
     * @param name         用户姓名（登录名/姓名）
     * @param departmentId 所属部门编号
     * @param pageIndex    当前页 --- 不可为空
     * @param pageSize     当前页数据条数 --- 不可为空
     * @return list
     */
    @Override
    public List<Map<String, Object>> getUserInfo(UserInfo userInfo, String name, Integer departmentId, Integer pageIndex, Integer pageSize) {
        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                String sql;
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                Object[] obj;

                if (StringUtils.hasText(name)) {
                    List<Integer> userIdList;
                    if (departmentId != null) {
                        //1.若对部门和姓名进行搜索
                        //根据部门查询本地系统下的用户列表
                        sql = "SELECT u.UserId,R.RoleId,R.RoleName,d.DepartmentName FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON d.DepartmentId = r.DepartmentId LEFT JOIN F_PUB_UserRoleRel UR ON UR.UserId = u.UserId LEFT JOIN F_PUB_RoleInfo R ON UR.RoleId = R.RoleId WHERE r.DepartmentId = ? GROUP BY u.UserId ORDER BY u.UserId DESC";
                        List<Map<String, Object>> listMascList1 = jdbcTemplate.queryForList(sql, departmentId);
                        if (listMascList1 != null && listMascList1.size() > 0) {
                            //组装参数
                            userIdList = new ArrayList<>();
                            for (Map<String, Object> map : listMascList1) {
                                userIdList.add((Integer) map.get("UserId"));
                            }
                        } else {
                            //该部门查询为空
                            return null;
                        }

                    } else {
                        //2.若只对姓名进行搜索
                        userIdList = null;
                    }
                    //组装姓名搜索参数
                    List<String> realNameList = new ArrayList<>();
                    realNameList.add(name);
                    //根据姓名查询用户中心的用户列表
                    List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, userIdList, null, realNameList, null, null, pageIndex, pageSize);

                    //对列表进行循环，组装相应字段，放入最终的列表中
                    for (UserInfo userCenterInfo : listUsCenter) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("UserId", userCenterInfo.getUserId());
                        map.put("RealName", userCenterInfo.getRealName());
                        map.put("LoginName", userCenterInfo.getLoginName());
                        Integer dpmtId = (Integer) getUserInfoDetailByUserId(userInfo, userCenterInfo.getUserId()).get("DepartmentId");
                        Department dp = departmentManageService.getDepartmentDetail(userInfo, dpmtId);
                        map.put("DepartmentName", dp == null ? null : dp.getDepartmentName());
                        list.add(map);
                    }

                } else {
                    if (departmentId != null) {
                        //3.若只对部门进行搜索
                        sql = "SELECT u.UserId,R.RoleId,R.RoleName,d.DepartmentName FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON d.DepartmentId = r.DepartmentId LEFT JOIN F_PUB_UserRoleRel UR ON UR.UserId = u.UserId LEFT JOIN F_PUB_RoleInfo R ON UR.RoleId = R.RoleId WHERE r.DepartmentId = ? GROUP BY u.UserId ORDER BY u.UserId DESC LIMIT ?,?";
                        obj = new Object[]{departmentId, row, pageSize};
                    } else {
                        //4.无条件搜索
                        sql = "SELECT u.UserId,R.RoleId,R.RoleName,d.DepartmentName FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON d.DepartmentId = r.DepartmentId LEFT JOIN F_PUB_UserRoleRel UR ON UR.UserId = u.UserId LEFT JOIN F_PUB_RoleInfo R ON UR.RoleId = R.RoleId GROUP BY u.UserId ORDER BY u.UserId DESC LIMIT ?,?";
                        obj = new Object[]{row, pageSize};
                    }

                    //查出本地部门和用户编号信息
                    List<Map<String, Object>> listMascList = jdbcTemplate.queryForList(sql, obj);
                    List<Integer> listUsId = new ArrayList<>();
                    for (Map<String, Object> mapMasc : listMascList) {
                        listUsId.add((Integer) mapMasc.get("UserId"));
                    }
                    //查询用户中心用户信息
                    List<UserInfo> usCenterList = userInfoService.getUserListByUserIds(listUsId);
                    for (UserInfo usIf : usCenterList) {
                        for (Map<String, Object> map : listMascList) {
                            if (usIf.getUserId() == (Integer) map.get("UserId")) {
                                map.put("RealName", usIf.getRealName());
                                map.put("LoginName", usIf.getLoginName());
                                list.add(map);
                            }
                        }
                    }
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询账号列表成功!");
                return list;

            } else {
                logger.info("==========>Service查询账号列表必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询账号列表异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 获取账号管理列表信息总条数
     *
     * @param userInfo     调用接口用户信息
     * @param name         用户姓名（登录名/别名）
     * @param departmentId 所属部门编号
     * @return count
     */
    @Override
    public Integer getUserInfoCount(UserInfo userInfo, String name, Integer departmentId) {
        try {
            if (userInfo != null) {
                String sql;
                Object[] obj;
                Integer count;

                if (StringUtils.hasText(name)) {
                    List<Integer> userIdList;
                    if (departmentId != null) {
                        //1.若对部门和姓名进行搜索
                        //根据部门查询本地系统下的用户列表
                        sql = "SELECT u.UserId,R.RoleId,R.RoleName,d.DepartmentName FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON d.DepartmentId = r.DepartmentId LEFT JOIN F_PUB_UserRoleRel UR ON UR.UserId = u.UserId LEFT JOIN F_PUB_RoleInfo R ON UR.RoleId = R.RoleId WHERE r.DepartmentId = ? GROUP BY u.UserId";
                        List<Map<String, Object>> listMascList1 = jdbcTemplate.queryForList(sql, departmentId);
                        if (listMascList1 != null && listMascList1.size() > 0) {
                            //组装参数
                            userIdList = new ArrayList<>();
                            for (Map<String, Object> map : listMascList1) {
                                userIdList.add((Integer) map.get("UserId"));
                            }
                        } else {
                            //该部门查询为空
                            return null;
                        }
                    } else {
                        //2.若只对姓名进行搜索
                        userIdList = null;
                    }
                    //组装姓名搜索参数
                    List<String> userNameList = new ArrayList<>();
                    userNameList.add(name);
                    //根据姓名查询用户中心的用户列表
                    count = userInfoService.getUserInfoCountWithPage(AppType.AppType_UDC_SYN, userIdList, userNameList, null, null, null);

                } else {
                    if (departmentId != null) {
                        //3.若只对部门进行搜索
                        sql = "SELECT COUNT(1) FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId WHERE r.DepartmentId = ?";
                        count = jdbcTemplate.queryForObject(sql, new Object[]{departmentId}, Integer.class);
                    } else {
                        //4.无条件搜索
                        sql = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                        count = jdbcTemplate.queryForObject(sql, Integer.class);
                    }
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询账号列表总条数成功!");
                return count;
            } else {
                logger.info("==========>Service查询账号列表总条数必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service查询账号列表总条数异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 新增用户
     *
     * @param userInfo     调用接口用户信息
     * @param loginName    用户名/登录名
     * @param passWd       密码
     * @param realName     真实姓名
     * @param departmentId 部门编号
     * @param roleIdList   角色编号
     * @param moduleIdList 模块权限 --- 一期可为空
     * @return result -1：用户中心已存在该用户；-2：用户中心插入失败；1：成功
     */
    @Override
    public Integer saveUserInfo(UserInfo userInfo, String loginName, String passWd, String realName, Integer departmentId, List<Integer> roleIdList, List<Integer> moduleIdList) {
        try {
            Integer result;
            if (userInfo != null && departmentId != null && StringUtils.hasText(loginName) && StringUtils.hasText(passWd) && StringUtils.hasText(realName) && roleIdList != null && roleIdList.size() > 0) {
                //1.通过登录名验证用户中心信息是否存在
                if (userInfoService.checkLoginName(loginName)) {
                    //用户中心已存在该用户
                    result = -1;
                } else {
                    //用户中心不存在该用户
                    UserInfo usCenter = new UserInfo();
                    usCenter.setLoginName(loginName);
                    usCenter.setPasswd(passWd);
                    usCenter.setRealName(realName);
                    List<AppType> sysList = new ArrayList<>();
                    sysList.add(AppType.AppType_UDC_SYN);
                    usCenter.setSystemType(sysList);

                    //2.在用户中心插入新用户
                    Map<RespType, Object> respMap = userInfoService.addUserInfo(usCenter);
                    if ((Integer) respMap.get(RespType.RespType_RespCode) == ResponseCode.Succeed.getValue()) {
                        //3.用户中心插入成功，进行本系统用户表插入操作
                        List<String> loginNameList = new ArrayList<>();
                        loginNameList.add(loginName);
                        List<UserInfo> usCenterList = userInfoService.getUserInfoByLoginName(loginNameList);
                        Integer userId = usCenterList.get(0).getUserId();
                        String sql = "INSERT INTO F_PUB_UserInfo(UserId, UserScore) VALUES (?,0)";
                        jdbcTemplate.update(sql, userId);

                        //4.插入用户部门关联表

                        //查询是否存在userId
                        Map<String, Object> map1;
                        try {
                            map1 = jdbcTemplate.queryForMap("SELECT PkId FROM F_PUB_UserDepRel WHERE UserId = ?", userId);
                        } catch (EmptyResultDataAccessException e) {
                            map1 = null;
                        }

                        if (map1 != null) {
                            //若存在，删除原关联
                            jdbcTemplate.update("DELETE FROM F_PUB_UserDepRel where UserId = ?", userId);
                        }
                        String sql2 = "INSERT INTO F_PUB_UserDepRel(UserId,DepartmentId) VALUES (?,?)";
                        jdbcTemplate.update(sql2, userId, departmentId);

                        //5.插入用户角色表
                        //5.2删除原关联
                        jdbcTemplate.update("DELETE FROM F_PUB_UserRoleRel where UserId = ?", userId);

                        //5.3批量插入用户角色关联表
                        String sql3 = "INSERT INTO F_PUB_UserRoleRel(UserId,RoleId) VALUES (?,?)";
                        jdbcTemplate.batchUpdate(sql3, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1, userId);
                                ps.setInt(2, roleIdList.get(i));
                            }

                            public int getBatchSize() {
                                return roleIdList.size();
                            }
                        });

                        //6.插入用户模块表
                        if (moduleIdList != null && moduleIdList.size() > 0) {
                            List<Integer> alllist = new ArrayList<>();
                            Set<Integer> set = new HashSet<>();
                            //6.1去重---过滤与角色所拥有的重复的权限模块
                            //根据userId查询roleId
                            List<RoleInfo> roleInfo = getRoleInfoByUserId(userInfo, userId);
                            if (roleInfo != null) {
                                //根据roleId查询List<ModuleInfo>
                                List<ModuleInfo> list1 = roleManageService.getModuleInfoByRoleId(userInfo, roleIdList);
                                if (list1 != null && list1.size() > 0) {
                                    List<Integer> list2 = new ArrayList<>();
                                    for (ModuleInfo mo : list1) {
                                        list2.add(mo.getModuleId());
                                    }

                                    set.addAll(list2);
                                }
                            }
                            //合并两个list并去重
                            set.addAll(moduleIdList);
                            alllist.addAll(set);
                            //6.2删除原关联
                            jdbcTemplate.update("DELETE FROM F_PUB_UserModuleRel where UserId = ?", userId);

                            String sql4 = "INSERT INTO F_PUB_UserModuleRel(UserId,ModuleId) VALUES (?,?)";
                            //6.3批量插入用户模块关联表
                            jdbcTemplate.batchUpdate(sql4, new BatchPreparedStatementSetter() {
                                public void setValues(PreparedStatement ps, int i) throws SQLException {
                                    ps.setInt(1, userId);
                                    ps.setInt(2, alllist.get(i));
                                }

                                public int getBatchSize() {
                                    return moduleIdList.size();
                                }
                            });
                        } else {
                            //删除原关联
                            jdbcTemplate.update("DELETE FROM F_PUB_UserModuleRel where UserId = ?", userId);
                        }

                        result = 1;
                    } else {
                        //用户中心插入失败
                        result = -2;
                    }
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>调用Service新增用户接口成功!");
                return result;
            } else {
                logger.info("==========>Service新增用户接口必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service新增用户异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑更改用户姓名头像部门
     *
     * @param userInfo     调用接口用户信息
     * @param userId       用户编号
     * @param avatar       头像地址
     * @param realName     真实姓名
     * @param departmentId 部门编号
     * @return result 1：修改成功；-1：用户中心修改失败;-2:无修改内容
     */
    @Override
    public Integer updateUserInfo1(UserInfo userInfo, Integer userId, String avatar, String realName, Integer departmentId) {
        try {
            if (userInfo != null) {

                Integer result;
                //组装用户中心用户信息
                UserInfo usCenter = new UserInfo();

                if (StringUtils.hasText(realName) && StringUtils.hasText(avatar)) {
                    usCenter.setAvatar(avatar);
                    usCenter.setRealName(realName);

                } else if (StringUtils.hasText(avatar)) {
                    usCenter.setAvatar(avatar);
                } else if (StringUtils.hasText(realName)) {
                    usCenter.setRealName(realName);
                } else if (departmentId != null) {
                    //仅修改本地部门关联
                    String sql = "UPDATE F_PUB_UserDepRel set DepartmentId = ? where UserId = ?";
                    Object[] obj = new Object[]{departmentId, userId};
                    jdbcTemplate.update(sql, obj);
                    return 1;
                } else {
                    //无修改内容
                    return -2;
                }

                //用户中心修改用户信息
                if (userInfoService.updateUserInfo(userId, usCenter)) {
                    if (departmentId != null) {
                        String sql = "UPDATE F_PUB_UserDepRel set DepartmentId = ? where UserId = ?";
                        Object[] obj = new Object[]{departmentId, userId};
                        jdbcTemplate.update(sql, obj);
                    }
                    result = 1;
                } else {
                    //用户中心修改失败
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑更改用户成功!");
                return result;
            } else {
                logger.info("==========>Service编辑更改用户接口必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service编辑更改用户异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 编辑更改用户
     *
     * @param userInfo     调用接口用户信息
     * @param userId       用户编号
     * @param avatar       头像地址
     * @param passWd       密码
     * @param realName     真实姓名
     * @param departmentId 部门编号
     * @param roleIdList   角色编号
     * @return result 1：修改成功；-1：用户中心修改失败
     */
    @Override
    public Integer updateUserInfo2(UserInfo userInfo, Integer userId, String avatar, String passWd, String realName, Integer departmentId, List<Integer> roleIdList, List<Integer> moduleIdList) {
        try {
            if (userInfo != null && userId != null && departmentId != null && StringUtils.hasText(passWd) && StringUtils.hasText(realName) && roleIdList != null && roleIdList.size() > 0) {

                //组装用户中心用户信息
                UserInfo usCenter = new UserInfo();
                usCenter.setPasswd(passWd);
                usCenter.setRealName(realName);
                if (StringUtils.hasText(avatar)) {
                    usCenter.setAvatar(avatar);
                }
                Integer result;

                //用户中心修改用户信息
                if (userInfoService.updateUserInfo(userId, usCenter)) {

                    //修改本地部门关联
                    String sql = "UPDATE F_PUB_UserDepRel set DepartmentId = ? where UserId = ?";
                    Object[] obj = new Object[]{departmentId, userId};
                    jdbcTemplate.update(sql, obj);
                    //修改用户角色关联表
                    //删除原关联
                    jdbcTemplate.update("DELETE FROM F_PUB_UserRoleRel where UserId = ?", userId);
                    //批量插入用户角色关联表
                    String sql3 = "INSERT INTO F_PUB_UserRoleRel(UserId,RoleId) VALUES (?,?)";
                    jdbcTemplate.batchUpdate(sql3, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, userId);
                            ps.setInt(2, roleIdList.get(i));
                        }

                        public int getBatchSize() {
                            return roleIdList.size();
                        }
                    });
                    //修改用户模块表
                    if (moduleIdList != null && moduleIdList.size() > 0) {
                        List<Integer> alllist = new ArrayList<>();
                        Set<Integer> set = new HashSet<>();
                        //去重---过滤与角色所拥有的重复的权限模块
                        //根据userId查询roleId
                        List<RoleInfo> roleInfo = getRoleInfoByUserId(userInfo, userId);
                        if (roleInfo != null) {
                            //根据roleId查询List<ModuleInfo>
                            List<ModuleInfo> list1 = roleManageService.getModuleInfoByRoleId(userInfo, roleIdList);
                            if (list1 != null && list1.size() > 0) {
                                List<Integer> list2 = new ArrayList<>();
                                for (ModuleInfo mo : list1) {
                                    list2.add(mo.getModuleId());
                                }

                                set.addAll(list2);

                            }
                        }
                        //合并两个list并去重
                        set.addAll(moduleIdList);
                        alllist.addAll(set);
                        //删除原关联
                        jdbcTemplate.update("DELETE FROM F_PUB_UserModuleRel where UserId = ?", userId);

                        String sql4 = "INSERT INTO F_PUB_UserModuleRel(UserId,ModuleId) VALUES (?,?)";
                        //批量插入角色模块关联表
                        jdbcTemplate.batchUpdate(sql4, new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1, userId);
                                ps.setInt(2, alllist.get(i));
                            }

                            public int getBatchSize() {
                                return moduleIdList.size();
                            }
                        });

                    } else {
                        //删除原关联
                        jdbcTemplate.update("DELETE FROM F_PUB_UserModuleRel where UserId = ?", userId);
                    }

                    result = 1;
                } else {
                    //用户中心修改失败
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑更改用户成功!");
                return result;
            } else {
                logger.info("==========>Service编辑更改用户接口必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service编辑更改用户异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据UserId获取详情信息
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return map
     */
    @Override
    public Map<String, Object> getUserInfoDetailByUserId(UserInfo userInfo, Integer userId) {
        try {
            if (userInfo != null && userId != null) {
                Map<String, Object> map;
                List<Integer> idList = new ArrayList<>();
                idList.add(userId);
                List<UserInfo> usCenterList = userInfoService.getUserListByUserIds(idList);
                String sql = "SELECT u.UserId,u.UserScore,r.DepartmentId,d.DepartmentName FROM F_PUB_UserInfo u LEFT JOIN F_PUB_UserDepRel r ON u.UserId = r.UserId LEFT JOIN F_PUB_Department d ON d.DepartmentId = r.DepartmentId WHERE u.UserId = ? GROUP BY u.UserId";

                if (usCenterList != null && usCenterList.size() > 0) {

                    try {
                        map = jdbcTemplate.queryForMap(sql, userId);

                    } catch (EmptyResultDataAccessException e) {
                        map = null;
                    }
                    if (map != null) {
                        map.put("LoginName", usCenterList.get(0).getLoginName());
                        map.put("RealName", usCenterList.get(0).getRealName());
                        map.put("PassWd", usCenterList.get(0).getPasswd());
                        map.put("Avatar", usCenterList.get(0).getAvatar());

                        List<RoleInfo> roleList = getRoleInfoByUserId(userInfo, userId);
                        List<Integer> roleIdList = new ArrayList<>();
                        List<String> roleNameList = new ArrayList<>();
                        if (roleList != null && roleList.size() > 0) {
                            for (RoleInfo roleInfo : roleList) {
                                roleIdList.add(roleInfo.getRoleId());
                            }
                        }
                        if (roleList != null && roleList.size() > 0) {
                            for (RoleInfo roleInfo : roleList) {
                                roleNameList.add(roleInfo.getRoleName());
                            }
                        }
                        map.put("RoleIdList", roleIdList);
                        map.put("RoleNameList", roleNameList);

                        List<ModuleInfo> mdList = moduleManageService.getModuleInfoByUserId(userInfo, userId);
                        List<Integer> mdIdList = new ArrayList<>();
                        if (mdList != null && mdList.size() > 0) {
                            for (ModuleInfo moduleInfo : mdList) {
                                mdIdList.add(moduleInfo.getModuleId());
                            }
                        }
                        map.put("ModuleIdList", mdIdList);
                    }

                } else {
                    map = null;
                }
                logger.info("======[" + userInfo.getUserId() + "]==========>Service根据UserId获取用户详情成功!");
                return map;
            } else {
                logger.info("==========>Service根据UserId获取用户详情接口必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>根据UserId获取用户详情异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 删除用户
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return result 1:成功；2：用户中心删除失败
     */
    @Override
    public Integer deleteUserInfo(UserInfo userInfo, Integer userId) {
        try {
            if (userInfo != null && userId != null) {
                Integer result;
                //1.删除用户中心用户信息
                if (userInfoService.deleteSystemUser(userId, AppType.AppType_UDC_SYN.getValue())) {
                    //2.若用户中心删除成功，则删除本地系统
                    jdbcTemplate.update("DELETE FROM F_PUB_UserInfo where UserId = ?", userId);
                    //删除部门关联表
                    jdbcTemplate.update("DELETE FROM F_PUB_UserDepRel where UserId = ?", userId);
                    //删除角色关联表
                    jdbcTemplate.update("DELETE FROM F_PUB_UserRoleRel where UserId = ?", userId);
                    //删除模块关联表
                    jdbcTemplate.update("DELETE FROM F_PUB_UserModuleRel where UserId = ?", userId);

                    result = 1;

                } else {
                    result = -1;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除用户成功!");
                return result;
            } else {
                logger.info("==========>Service删除用户接口必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>删除用户异常：");
            ep.printStackTrace();
            return null;
        }


    }


    /**
     * 根据 userId 获取角色列表
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户编号
     * @return roleInfo
     */
    @Override
    public List<RoleInfo> getRoleInfoByUserId(UserInfo userInfo, Integer userId) {

        try {
            if (userInfo != null && userId != null) {
                String sql = "select R.RoleId, R.RoleName, R.CreateTime, R.Flag from F_PUB_RoleInfo R LEFT JOIN F_PUB_UserRoleRel UR ON R.RoleId = UR.RoleId where UR.UserId = ?";

                List<RoleInfo> list = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
                    RoleInfo rol = new RoleInfo();
                    rol.setRoleId(rs.getInt("RoleId"));
                    rol.setRoleName(rs.getString("RoleName"));
                    rol.setCreateTime(rs.getInt("CreateTime"));
                    rol.setFlag(rs.getInt("Flag"));
                    return rol;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service根据 userId 获取角色列表接口调用成功！");
                return list;

            } else {
                logger.info("==========>Service根据 userId 获取角色列表接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据 userId 获取角色列表接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 发布平台公告
     *
     * @param userInfo       调用接口用户信息
     * @param messageContent 消息内容
     * @return result 1：成功，其他:失败
     */
    @Override
    public Integer addNotice(UserInfo userInfo, String title, String messageContent) {
        try {
            if (userInfo == null || !StringUtils.hasText(messageContent) || !StringUtils.hasText(title)) {
                logger.info("==========>Service发布平台公告必传参数为空！");
                return null;
            }
            String userSql = "SELECT UserId FROM F_PUB_UserInfo";
            List<Integer> usList = jdbcTemplate.queryForList(userSql, Integer.class);

            return messageManageService.addMessage(userInfo, MascotMessageType.Notice.getValue(), title, messageContent, usList);


        } catch (Exception ep) {
            logger.debug("======[" + (userInfo == null ? null : userInfo.getUserId()) + "]==========>Service发布平台公告异常：");
            ep.printStackTrace();
            return null;
        }
    }


}

