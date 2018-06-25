package com.mascot.service.impl;

import com.interfaces.mascot.ModuleManageService;
import com.interfaces.mascot.RoleManageService;
import com.interfaces.mascot.UserManageService;
import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.RoleInfo;
import com.thrift.common.body.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 模块管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/13
 */
@Service(value = "moduleManageService")
public class ModuleManageServiceImpl extends BasicServiceImpl implements ModuleManageService {

    private final static Log logger = LogFactory.getLog(ModuleManageServiceImpl.class);
    @Autowired
    UserManageService userManageService;

    @Autowired
    RoleManageService roleManageService;

    /**
     * 新增模块
     *
     * @param userInfo 调用接口用户信息
     * @return result  1：成功
     * -1：请先输入上级模块的权限码！
     * -2：上级分类为空！
     * -3：模块码名称重复
     * -4：权限码重复
     * -5：异常
     */
    @Override
    public Integer addModuleInfo(UserInfo userInfo, Integer parentId, String moduleCode, String ModuleName) {
        try {
            if (userInfo != null && StringUtils.hasText(ModuleName) && StringUtils.hasText(moduleCode)) {
                parentId = parentId==null?0:parentId;
                if (parentId != 0) {
                    //若不是一级分类，则获取上级目录信息
                    String sql1 = "SELECT ModuleId, ParentId, ModuleName, ModuleCode FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleId = ?";

                    try {
                        ModuleInfo moduleInfo = jdbcTemplate.queryForObject(sql1, new Object[]{parentId}, (rs, rowNum) -> {
                            ModuleInfo mod = new ModuleInfo();
                            mod.setModuleId(rs.getInt("ModuleId"));
                            mod.setParentId(rs.getInt("ParentId"));
                            mod.setModuleName(rs.getString("ModuleName"));
                            mod.setModuleCode(rs.getString("ModuleCode"));
                            return mod;
                        });

                        if (moduleInfo.getModuleCode() != null) {
                            moduleCode = moduleInfo.getModuleCode() + "-" + moduleCode;
                        } else {
                            logger.debug("请先输入上级模块的权限码！");
                            return -1;
                        }

                    } catch (EmptyResultDataAccessException e) {
                        logger.debug("上级分类为空！");
                        return -2;
                    }
                }
                String sql2 = "SELECT ModuleId, ParentId, ModuleName, ModuleCode,Flag FROM F_PUB_ModuleInfo WHERE ModuleName = ?";
                Map<String, Object> oldModule;
                try {
                    oldModule = jdbcTemplate.queryForObject(sql2, new Object[]{ModuleName}, (rs, rowNum) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ModuleId", rs.getInt("ModuleId"));
                        map.put("ParentId", rs.getInt("ParentId"));
                        map.put("ModuleName", rs.getString("ModuleName"));
                        map.put("ModuleCode", rs.getString("ModuleCode"));
                        map.put("Flag", rs.getInt("Flag"));
                        return map;
                    });
                } catch (EmptyResultDataAccessException e) {
                    oldModule = null;
                }

                String sql3 = "SELECT ModuleId FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleCode = ?";

                Integer codeId;
                try {
                    codeId = jdbcTemplate.queryForObject(sql3, new Object[]{moduleCode}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    codeId = null;
                }

                if (oldModule == null && codeId == null) {
                    String sql = "INSERT INTO F_PUB_ModuleInfo(ParentId,ModuleName,ModuleCode,Flag,CreateTime) VALUES(?,?,?,1,?) ";
                    jdbcTemplate.update(sql, parentId, ModuleName, moduleCode, new Date());
                } else if (oldModule != null && (Integer) oldModule.get("Flag") == 2 && codeId == null) {
                    String sql = "UPDATE F_PUB_ModuleInfo SET ModuleCode = ?,Flag = 1 WHERE ModuleId = ?";
                    jdbcTemplate.update(sql, moduleCode, oldModule.get("ModuleId"));
                } else if (oldModule != null && (Integer) oldModule.get("Flag") == 1) {
                    //模块码名称重复
                    return -3;
                } else if (codeId != null) {
                    //权限码重复
                    return -4;
                } else {
                    //异常
                    return -5;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增模块成功！");
                return 1;

            } else {
                logger.info("==========>Service新增模块必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增模块异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 更改模块
     *
     * @param userInfo 调用接口用户信息
     * @return result  1：成功
     * -1：请先输入上级模块的权限码！
     * -2：上级分类为空！
     * -3：模块码名称重复
     * -4：权限码重复
     * -5：异常
     */
    @Override
    public Integer updateModuleInfo(UserInfo userInfo, Integer parentId, Integer moduleId, String moduleCode, String ModuleName) {
        try {
            if (userInfo != null && parentId != null && moduleId != null && StringUtils.hasText(ModuleName) && StringUtils.hasText(moduleCode)) {
                Integer result;

                if (parentId != 0) {
                    //若不是一级分类，则获取上级目录信息
                    String sql1 = "SELECT ModuleId, ParentId, ModuleName, ModuleCode FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleId = ?";

                    try {
                        ModuleInfo moduleInfo = jdbcTemplate.queryForObject(sql1, new Object[]{parentId}, (rs, rowNum) -> {
                            ModuleInfo mod = new ModuleInfo();
                            mod.setModuleId(rs.getInt("ModuleId"));
                            mod.setParentId(rs.getInt("ParentId"));
                            mod.setModuleName(rs.getString("ModuleName"));
                            mod.setModuleCode(rs.getString("ModuleCode"));
                            return mod;
                        });

                        if (moduleInfo.getModuleCode() != null) {
                            moduleCode = moduleInfo.getModuleCode() + "-" + moduleCode;
                        } else {
                            logger.debug("请先输入上级模块的权限码！");
                            return -1;
                        }

                    } catch (EmptyResultDataAccessException e) {
                        logger.debug("上级分类为空！");
                        return -2;
                    }
                }
                String sql2 = "SELECT ModuleId, ParentId, ModuleName, ModuleCode FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleName = ?";
                Map<String, Object> oldModule;
                try {
                    oldModule = jdbcTemplate.queryForMap(sql2, ModuleName);
                } catch (EmptyResultDataAccessException e) {
                    oldModule = null;
                }

                String sql3 = "SELECT ModuleId FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleCode = ?";

                Integer codeId;
                try {
                    codeId = jdbcTemplate.queryForObject(sql3, new Object[]{moduleCode}, Integer.class);
                } catch (EmptyResultDataAccessException e) {
                    codeId = null;
                }

                if (oldModule == null && codeId == null) {
                    String sql = "UPDATE F_PUB_ModuleInfo SET ModuleCode = ?,ModuleName = ? WHERE ModuleId = ?";
                    jdbcTemplate.update(sql, moduleCode, ModuleName, moduleId);
                } else if (oldModule != null && moduleId.equals(oldModule.get("ModuleId")) && codeId == null) {
                    String sql = "UPDATE F_PUB_ModuleInfo SET ModuleCode = ? WHERE ModuleId = ?";
                    jdbcTemplate.update(sql, moduleCode, moduleId);
                } else if (oldModule != null && moduleId.equals(oldModule.get("ModuleId"))) {
                    //模块码名称重复
                    return -3;
                } else if (codeId != null) {
                    //权限码重复
                    return -4;
                } else {
                    //异常
                    return -5;
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service更改模块成功！");
                return 1;

            } else {
                logger.info("==========>Service更改模块必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service更改模块异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 删除模块
     *
     * @param userInfo 调用接口用户信息
     * @return result 共删除的条数
     */
    @Override
    public Integer deleteModuleInfo(UserInfo userInfo, Integer moduleId) {
        try {
            if (userInfo != null && moduleId != null) {
                //获取所有下级
                List<Integer> moduleIdList = getModuleInfoByParentId(userInfo, moduleId);
                moduleIdList.add(moduleId);
                String sql = "DELETE FROM F_PUB_ModuleInfo WHERE ModuleId IN (:param)";

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("param", moduleIdList);

                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                Integer result = jdbc.update(sql, paramMap);

                logger.info("======[" + userInfo.getUserId() + "]==========>Service更改模块成功！");
                return result;

            } else {
                logger.info("==========>Service更改模块必传参数为空！");
                return null;
            }
        } catch (
                Exception ep)

        {
            logger.debug("==========>Service更改模块异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据上级目录获取下一级目录
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 上级分类编号
     * @return list
     */
    @Override
    public List<Map<String, Object>> getNextModuleInfoByParentId(UserInfo userInfo, Integer moduleId) {
        try {
            if (userInfo != null && moduleId != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                String sql = "SELECT ModuleId,ModuleName,ModuleCode,ParentId FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ParentId = ?";
                List<Map<String, Object>> list1 = jdbcTemplate.query(sql, new Object[]{moduleId}, (rs, rowNum) -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("ModuleId", rs.getInt("ModuleId"));
                    m.put("ModuleName", rs.getString("ModuleName"));
                    m.put("ModuleCode", rs.getString("ModuleCode"));
                    m.put("ParentId", rs.getInt("ParentId"));
                    return m;
                });
                if (list1 != null && list1.size() > 0) {
                    for (Map<String, Object> map : list1) {
                        String sql2 = "SELECT ModuleId FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ParentId = ?";
                        List<Integer> list2 = jdbcTemplate.queryForList(sql2, new Object[]{map.get("ModuleId")}, Integer.class);
                        //1.需要加加号样式，2不需要加加号样式
                        Integer isPlus = (list2 != null && list2.size() > 0) ? 1 : 2;
                        map.put("IsPlus", isPlus);
                        list.add(map);
                    }
                }

                return list;

            } else {
                logger.debug("==========>Service根据上级目录获取下一级目录必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据上级目录获取下一级目录列表异常：");
            ep.printStackTrace();
            return null;
        }
    }

    /**
     * 根据上级目录获取所有下级目录
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 一级分类编号
     * @return moduleIdList
     */
    @Override
    public List<Integer> getModuleInfoByParentId(UserInfo userInfo, Integer moduleId) {
        try {
            if (userInfo != null && moduleId != null) {
                List<Integer> listAll = new ArrayList<>();
                List<Integer> list = new ArrayList<>();
                list.add(moduleId);
                do {
                    String sql = "SELECT ModuleId FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ParentId IN (:param)";

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
     * 获取所有模块列表
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public List<ModuleInfo> getAllModuleInfo(UserInfo userInfo) {
        try {
            if (userInfo != null) {
                List<ModuleInfo> list = getCircleModuleInfoByParentId(0);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取所有模块列表成功！");
                return list;

            } else {
                logger.info("==========>Service获取所有模块列表必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取所有模块列表异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据 moduleId 获取模块信息
     *
     * @param userInfo 调用接口用户信息
     * @param moduleId 模块码自增主键
     * @return list
     */
    @Override
    public ModuleInfo getModuleInfoByModuleId(UserInfo userInfo, Integer moduleId) {
        try {
            if (userInfo != null && moduleId != null) {

                String sql = "SELECT ModuleId, ParentId, ModuleName, ModuleCode FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ModuleId = ?";
                try {
                    ModuleInfo moduleInfo = jdbcTemplate.queryForObject(sql, new Object[]{moduleId}, (rs, rowNum) -> {
                        ModuleInfo mod = new ModuleInfo();
                        mod.setModuleId(rs.getInt("ModuleId"));
                        mod.setParentId(rs.getInt("ParentId"));
                        mod.setModuleName(rs.getString("ModuleName"));
                        mod.setModuleCode(rs.getString("ModuleCode"));
                        return mod;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据moduleId查询模块成功！");
                    return moduleInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service根据moduleId查询模块结果为空！");
                    return null;
                }

            } else {
                logger.info("==========>Service根据moduleId查询模块必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service根据moduleId查询模块列表异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 根据 userId 获取模块信息
     *
     * @param userInfo 调用接口用户信息
     * @param userId   用户Id
     * @return list
     */
    @Override
    public List<ModuleInfo> getModuleInfoByUserId(UserInfo userInfo, Integer userId) {
        try {
            if (userInfo != null && userId != null) {
                List<ModuleInfo> list1 = new ArrayList<>();
                //1.根据userId查询roleId
                List<RoleInfo> roleInfoList = userManageService.getRoleInfoByUserId(userInfo, userId);
                List<Integer> roleIdList = new ArrayList<>();
                if (roleInfoList != null) {
                    for (RoleInfo roleInfo : roleInfoList) {
                        roleIdList.add(roleInfo.getRoleId());
                    }
                    //2.根据roleId查询List<ModuleInfo>
                    list1 = roleManageService.getModuleInfoByRoleId(userInfo, roleIdList);
                }

                //3.根据userId查询List<ModuleInfo>
                String sql = "select ModuleId, ParentId, ModuleName, ModuleCode from F_PUB_ModuleInfo where ModuleId in (select ModuleId from F_PUB_UserModuleRel where UserId = ?)";
                List<ModuleInfo> list2 = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
                    ModuleInfo mod = new ModuleInfo();
                    mod.setModuleId(rs.getInt("ModuleId"));
                    mod.setParentId(rs.getInt("ParentId"));
                    mod.setModuleName(rs.getString("ModuleName"));
                    mod.setModuleCode(rs.getString("ModuleCode"));
                    return mod;
                });
                //4.合并两个list并去重
                Set<ModuleInfo> set = new HashSet<>();
                List<ModuleInfo> list = new ArrayList<>();
                if(list1!=null&&list1.size()>0){
                    set.addAll(list1);
                }
                if(list2!=null&&list2.size()>0){
                    set.addAll(list2);
                }
                list.addAll(set);


                logger.info("======[" + userInfo.getUserId() + "]==========>Service根据userId查询模块列表成功！");
                return list;
            } else {
                logger.info("==========>Service根据userId查询模块列表必传参数为空1");
                return null;
            }
        } catch (Exception e) {
            logger.debug("==========>Service根据userId查询模块列表异常：");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据上级目录无限获取下级目录
     *
     * @param parentId 一级分类编号
     * @return moduleIdList
     */
    private List<ModuleInfo> getCircleModuleInfoByParentId(Integer parentId) throws Exception {
        if (parentId == null) {
            throw new Exception("==========>Service根据上级目录无限获取下级目录必传参数父类Id为空！");
        }

        String sql = "SELECT ModuleId,ParentId,ModuleName,ModuleCode FROM F_PUB_ModuleInfo WHERE Flag = 1 AND ParentId = ?";
        List<ModuleInfo> allList = new ArrayList<>();

        List<ModuleInfo> list = jdbcTemplate.query(sql, new Object[]{parentId}, (rs, rowNum) -> {
            ModuleInfo mod = new ModuleInfo();
            mod.setModuleId(rs.getInt("ModuleId"));
            mod.setParentId(rs.getInt("ParentId"));
            mod.setModuleName(rs.getString("ModuleName"));
            mod.setModuleCode(rs.getString("ModuleCode"));
            return mod;
        });

        if (list != null && list.size() != 0) {
            for (ModuleInfo moduleInfo : list) {
                moduleInfo.setSubModelList(getCircleModuleInfoByParentId(moduleInfo.getModuleId()));
                allList.add(moduleInfo);
            }
        }
        return allList;

    }

}

