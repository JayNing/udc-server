package com.mascot.service.impl;

import com.interfaces.mascot.SpecialistManageService;
import com.thrift.common.body.ModuleInfo;
import com.thrift.common.body.SpeLike;
import com.thrift.common.body.SpecialistInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.AppType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 专家管理接口实现
 *
 * @author zhangmengyu
 * 2018/4/24
 */
@Service(value = "specialistManageService")
public class SpecialistManageServiceImpl extends BasicServiceImpl implements SpecialistManageService {

    private final static Log logger = LogFactory.getLog(SpecialistManageServiceImpl.class);

    /**
     * 获取专家列表信息
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页 --- 不可为空
     * @param pageSize  当前页数据条数 --- 不可为空
     * @return list
     */
    @Override
    public List<SpecialistInfo> getSpecialistInfo(UserInfo userInfo, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                String sql = "SELECT S.SpeId,S.HeadUrl,S.SpeName,S.JobTitle,S.SpeProfile,S.Honors,S.SpeLikeCount FROM F_SPE_SpecialistInfo S WHERE S.Flag = 1 ORDER BY S.SpeId LIMIT ?,?";
                Integer row = pageUtil.getRow(pageIndex, pageSize);
                Object[] obj = new Object[]{row, pageSize};

                List<SpecialistInfo> list = jdbcTemplate.query(sql, obj, (rs, rowNum) -> {
                    SpecialistInfo specialistInfo = new SpecialistInfo();
                    specialistInfo.setSpeId(rs.getInt("SpeId"));
                    specialistInfo.setHeadUrl(rs.getString("HeadUrl"));
                    specialistInfo.setSpeName(rs.getString("SpeName"));
                    specialistInfo.setJobTitle(rs.getString("JobTitle"));
                    specialistInfo.setSpeProfile(rs.getString("SpeProfile"));
                    specialistInfo.setHonors(rs.getString("Honors"));
                    specialistInfo.setSpeLikeCount(rs.getInt("SpeLikeCount"));
                    return specialistInfo;
                });
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取专家列表信息接口调用成功！");
                return list;
            } else {
                logger.info("==========>Service获取专家列表信息接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取专家列表信息接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 获取专家列表信息总条数
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    @Override
    public Integer getSpecialistCount(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                String sql = "SELECT COUNT(1) FROM F_SPE_SpecialistInfo S WHERE S.Flag = 1 ORDER BY S.SpeId";

                Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service获取专家列表信息总条数接口调用成功！");
                return count;
            } else {
                logger.info("==========>Service获取专家列表信息接口总条数必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service获取专家列表信息接口总条数调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 新增专家
     *
     * @param userInfo   调用接口用户信息
     * @param headUrl    头像 --- 不为空：若用户没有选择自己的头像则采用默认图片
     * @param speName    专家姓名 --- 不为空
     * @param jobTitle   专家职称 --- 不为空
     * @param speProfile 专家简介 --- 不为空
     * @param honors     个人荣誉 --- 不为空
     * @return result
     */
    @Override
    public Integer saveSpecialistInfo(UserInfo userInfo, String headUrl, String speName, String jobTitle, String speProfile, String honors) {

        try {
            if (userInfo != null && StringUtils.hasText(headUrl) && StringUtils.hasText(speName) && StringUtils.hasText(jobTitle) && StringUtils.hasText(speProfile) && StringUtils.hasText(honors)) {
                String sql = "INSERT INTO F_SPE_SpecialistInfo (SpeName,JobTitle,SpeProfile,Honors,HeadUrl,SpeLikeCount,Flag,CreateTime,CreateUserId,LastEditUserId) VALUES(?,?,?,?,?,0,1,?,?,?)";
                Object[] obj = new Object[]{speName, jobTitle, speProfile, honors, headUrl, new Date(), userInfo.getUserId(), userInfo.getUserId()};

                Integer result = jdbcTemplate.update(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增专家接口调用成功！=====> result = " + result);
                return result;
            } else {
                logger.info("==========>Service新增专家接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增专家接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 编辑专家
     *
     * @param userInfo   调用接口用户信息
     * @param speId      专家编号 --- 不为空
     * @param headUrl    修改后头像 --- 不为空：若用户没有选择自己的头像则采用默认图片
     * @param speName    修改后专家姓名 --- 不为空
     * @param jobTitle   修改后专家职称 --- 不为空
     * @param speProfile 修改后专家简介 --- 不为空
     * @param honors     修改后个人荣誉 --- 不为空
     * @return result
     */
    @Override
    public Integer updateSpecialistInfo(UserInfo userInfo, Integer speId, String headUrl, String speName, String jobTitle, String speProfile, String honors) {
        try {
            if (userInfo != null && speId != null && StringUtils.hasText(headUrl) && StringUtils.hasText(speName) && StringUtils.hasText(jobTitle) && StringUtils.hasText(speProfile) && StringUtils.hasText(honors)) {
                String sql = "UPDATE F_SPE_SpecialistInfo SET SpeName = ? , JobTitle = ? , SpeProfile = ? , Honors = ? ,HeadUrl = ? , LastEditUserId = ? where SpeId = ?";
                Object[] obj = new Object[]{speName, jobTitle, speProfile, honors, headUrl, userInfo.getUserId(), speId};
                Integer result = jdbcTemplate.update(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service编辑修改专家接口调用成功！=====> result = " + result);
                return result;
            } else {
                logger.info("==========>Service编辑修改专家接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service编辑修改专家接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 通过专家id查询专家详情信息
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @return specialistInfo
     */
    @Override
    public SpecialistInfo getSpecialistInfoDetailBySpeId(UserInfo userInfo, Integer speId) {
        try {
            if (userInfo != null && speId != null) {
                String sql = "SELECT S.SpeId,S.HeadUrl,S.SpeName,S.JobTitle,S.SpeProfile,S.Honors,S.SpeLikeCount,S.Flag FROM F_SPE_SpecialistInfo S WHERE S.SpeId = ?";
                try {
                    SpecialistInfo specialistInfo = jdbcTemplate.queryForObject(sql, new Object[]{speId}, (rs, rowNum) -> {
                        SpecialistInfo spe = new SpecialistInfo();
                        spe.setSpeId(rs.getInt("SpeId"));
                        spe.setHeadUrl(rs.getString("HeadUrl"));
                        spe.setSpeName(rs.getString("SpeName"));
                        spe.setJobTitle(rs.getString("JobTitle"));
                        spe.setSpeProfile(rs.getString("SpeProfile"));
                        spe.setHonors(rs.getString("Honors"));
                        spe.setSpeLikeCount(rs.getInt("SpeLikeCount"));
                        spe.setFlag(rs.getInt("Flag"));
                        return spe;
                    });
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过专家id查询专家详情信息成功!");
                    return specialistInfo;
                } catch (EmptyResultDataAccessException e) {
                    logger.info("======[" + userInfo.getUserId() + "]==========>Service通过专家id查询专家详情信息为空：");
                    e.printStackTrace();
                    return null;
                }
            } else {
                logger.info("==========>Service通过专家id查询专家详情必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service通过专家id查询专家详情信息异常：");
            ep.printStackTrace();
            return null;
        }


    }

    /**
     * 删除专家信息
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家流水号
     * @return result
     */
    @Override
    public Integer deleteSpecialistInfo(UserInfo userInfo, Integer speId) {
        try {
            if (userInfo != null && speId != null) {
                String sql = "UPDATE F_SPE_SpecialistInfo set Flag = 2 where SpeId = ?";
                Integer result = jdbcTemplate.update(sql, speId);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service删除专家信息成功!");
                return result;
            } else {
                logger.info("==========>Service删除专家信息必传参数为空!");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service删除专家信息异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 新增专家咨询
     *
     * @param userInfo      调用接口用户信息
     * @param speId         专家编号
     * @param consultUserId 咨询者编号
     * @param content       咨询内容
     * @return result
     */
    @Override
    public Integer saveSpeConsult(UserInfo userInfo, Integer speId, Integer consultUserId, String content) {

        try {
            if (userInfo != null && speId != null && consultUserId != null && StringUtils.hasText(content)) {
                String sql = "INSERT INTO F_SPE_SpeConsult (SpeId,ConsultUserId,Content,Createtime,Flag) VALUES(?,?,?,?,1)";
                Object[] obj = new Object[]{speId, consultUserId, content, new Date()};

                Integer result = jdbcTemplate.update(sql, obj);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service新增专家咨询接口调用成功！=====> result = " + result);
                return result;
            } else {
                logger.info("==========>Service新增专家咨询接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service新增专家咨询接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询专家咨询
     *
     * @param userInfo  调用接口用户信息
     * @param title     搜索框参数
     * @param realName  搜索框参数
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return result
     */
    @Override
    public List<Map<String, Object>> querySpeConsult(UserInfo userInfo, String title, String realName, Date startTime, Date endTime, Integer pageIndex, Integer pageSize) {

        try {
            if (userInfo != null && pageIndex != null && pageSize != null) {
                List<Map<String, Object>> list;
                List<Map<String, Object>> allList = new ArrayList<>();

                Integer row = pageUtil.getRow(pageIndex, pageSize);
                String sql;
                Object[] obj;
                if (StringUtils.hasText(realName)) {
                    //组装姓名搜索参数
                    List<String> realNameList = new ArrayList<>();
                    realNameList.add(realName);
                    //根据姓名查询用户中心的用户列表
                    String sql1 = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                    Integer pSize = jdbcTemplate.queryForObject(sql1,Integer.class);
                    List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);

                   if(listUsCenter!=null&&listUsCenter.size()>0){
                       List<Integer> userIdList = new ArrayList<>();
                       for (UserInfo usInfo : listUsCenter) {
                           userIdList.add(usInfo.getUserId());
                       }
                       Map<String, Object> paramMap = new HashMap<>();

                       if(StringUtils.hasText(title)){
                           String paramLike = "%" + title + "%";
                           sql = "SELECT C.ConsultId,C.SpeId,S.SpeName,C.ConsultUserId,C.Content,C.Createtime,DP.DepartmentId,DP.DepartmentName FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = C.ConsultUserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.Flag = 1 AND S.Flag = 1 AND C.Content LIKE :paramLike AND C.ConsultUserId IN (:param) AND C.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY C.Createtime DESC LIMIT :row,:pageSize";
                           paramMap.put("paramLike", paramLike);
                       }else{
                           sql = "SELECT C.ConsultId,C.SpeId,S.SpeName,C.ConsultUserId,C.Content,C.Createtime,DP.DepartmentId,DP.DepartmentName FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = C.ConsultUserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.Flag = 1 AND S.Flag = 1 AND C.ConsultUserId IN (:param) AND C.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW()) ORDER BY C.Createtime DESC LIMIT :row,:pageSize";

                       }
                       paramMap.put("param", userIdList);
                       paramMap.put("startTime", startTime);
                       paramMap.put("endTime", endTime);
                       paramMap.put("row", row);
                       paramMap.put("pageSize", pageSize);

                       NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                       list = jdbc.queryForList(sql, paramMap);
                       //将用户姓名组装到list中
                       for (Map<String, Object> map : list) {
                           for (UserInfo usInfo : listUsCenter) {
                               if (usInfo.getUserId() == (Integer) map.get("ConsultUserId")) {
                                   map.put("RealName", usInfo.getRealName());
                                   allList.add(map);
                               }
                           }
                       }

                   }

                } else {
                    if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT C.ConsultId,C.SpeId,S.SpeName,C.ConsultUserId,C.Content,C.Createtime,DP.DepartmentId,DP.DepartmentName FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = C.ConsultUserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.Flag = 1 AND S.Flag = 1 AND C.Content LIKE ?  AND C.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY C.Createtime DESC LIMIT ?,?";
                        obj = new Object[]{paramLike, startTime, endTime, row, pageSize};
                    }else {
                        sql = "SELECT C.ConsultId,C.SpeId,S.SpeName,C.ConsultUserId,C.Content,C.Createtime,DP.DepartmentId,DP.DepartmentName FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = C.ConsultUserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.Flag = 1 AND S.Flag = 1 AND C.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW()) ORDER BY C.Createtime DESC LIMIT ?,?";
                        obj = new Object[]{startTime, endTime, row, pageSize};
                    }

                    list = jdbcTemplate.queryForList(sql, obj);
                    if (list != null && list.size() > 0) {
                        //组装查询参数
                        List<Integer> idList = new ArrayList<>();
                        for (Map<String, Object> m : list) {
                            idList.add((Integer) m.get("ConsultUserId"));
                        }
                        //查询用户中心用户信息
                        List<UserInfo> usCenter = userInfoService.getUserListByUserIds(idList);
                        //将用户姓名组装到list中
                        for (Map<String, Object> map : list) {
                            for (UserInfo usInfo : usCenter) {
                                if (usInfo.getUserId() == (Integer) map.get("ConsultUserId")) {
                                    map.put("RealName", usInfo.getRealName());
                                    allList.add(map);
                                }
                            }
                        }

                    }
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询专家咨询接口调用成功！");
                return allList;
            } else {
                logger.info("==========>Service查询专家咨询接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service查询专家咨询接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 查询专家咨询总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     搜索框参数
     * @param realName  搜索框参数
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    @Override
    public Integer querySpeConsultCount(UserInfo userInfo, String title, String realName, Date startTime, Date endTime) {

        try {
            if (userInfo != null) {
                Integer count;
                String sql;
                Object[] obj;
                if (StringUtils.hasText(realName)) {
                    //组装姓名搜索参数
                    List<String> realNameList = new ArrayList<>();
                    realNameList.add(realName);
                    //根据姓名查询用户中心的用户列表
                    String sql1 = "SELECT COUNT(1) FROM F_PUB_UserInfo";
                    Integer pSize = jdbcTemplate.queryForObject(sql1,Integer.class);
                    List<UserInfo> listUsCenter = userInfoService.getUserInfoListWithPage(AppType.AppType_UDC_SYN, null, null, realNameList, null, null, 1, pSize);

                    if(listUsCenter!=null&&listUsCenter.size()>0){
                        List<Integer> userIdList = new ArrayList<>();
                        for (UserInfo usInfo : listUsCenter) {
                            userIdList.add(usInfo.getUserId());
                        }
                        Map<String, Object> paramMap = new HashMap<>();

                        if(StringUtils.hasText(title)){
                            String paramLike = "%" + title + "%";
                            sql = "SELECT COUNT(1) FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId  WHERE C.Flag = 1 AND S.Flag = 1 AND C.Content LIKE :paramLike AND C.ConsultUserId IN (:param) AND C.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                            paramMap.put("paramLike", paramLike);
                        }else{
                            sql = "SELECT COUNT(1) FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId  WHERE C.Flag = 1 AND S.Flag = 1 AND C.ConsultUserId IN (:param) AND C.CreateTime BETWEEN IFNULL(:startTime, '') AND IFNULL(:endTime, NOW())";
                        }
                        paramMap.put("param", userIdList);
                        paramMap.put("startTime", startTime);
                        paramMap.put("endTime", endTime);

                        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
                        count = jdbc.queryForObject(sql, paramMap,Integer.class);

                    }else{
                        count = 0;
                    }

                } else {
                    if (StringUtils.hasText(title)) {
                        String paramLike = "%" + title + "%";
                        sql = "SELECT COUNT(1) FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId WHERE C.Flag = 1 AND S.Flag = 1 AND C.Content LIKE ?  AND C.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                        obj = new Object[]{paramLike, startTime, endTime};
                    }else {
                        sql = "SELECT COUNT(1) FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId WHERE C.Flag = 1 AND S.Flag = 1 AND C.CreateTime BETWEEN IFNULL(?, '') AND IFNULL(?, NOW())";
                        obj = new Object[]{startTime, endTime};
                    }
                    count = jdbcTemplate.queryForObject(sql, obj,Integer.class);
                }

                logger.info("======[" + userInfo.getUserId() + "]==========>Service查询专家咨询总条数接口调用成功！");
                return count;
            } else {
                logger.info("==========>Service查询专家咨询总条数接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service查询专家咨询总条数接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 专家咨询详情
     *
     * @param userInfo  调用接口用户信息
     * @param consultId 咨询编号
     * @return result
     */
    @Override
    public Map<String, Object> querySpeConsultDetail(UserInfo userInfo, Integer consultId) {

        try {
            if (userInfo != null) {

                String sql = "SELECT C.ConsultId,C.SpeId,S.SpeName,C.ConsultUserId,C.Content,C.Createtime,DP.DepartmentId,DP.DepartmentName FROM F_SPE_SpeConsult C LEFT JOIN F_SPE_SpecialistInfo S ON C.SpeId = S.SpeId LEFT JOIN F_PUB_UserDepRel UDR ON UDR.UserId = C.ConsultUserId LEFT JOIN F_PUB_Department DP ON DP.DepartmentId = UDR.DepartmentId WHERE C.Flag = 1 AND S.Flag = 1 AND C.ConsultId = ?";

                Map<String, Object> map = jdbcTemplate.queryForMap(sql, consultId);

                //组装查询参数
                List<Integer> idList = new ArrayList<>();
                idList.add((Integer) map.get("ConsultUserId"));

                //查询用户中心用户信息
                List<UserInfo> usCenter = userInfoService.getUserListByUserIds(idList);
                if (usCenter != null && usCenter.size() > 0) {
                    //将用户姓名组装到map中

                    map.put("RealName", usCenter.get(0).getRealName());

                }


                logger.info("======[" + userInfo.getUserId() + "]==========>Service专家咨询详情接口调用成功！");
                return map;
            } else {
                logger.info("==========>Service专家咨询详情接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service专家咨询详情接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 点赞专家
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @param userId   点赞者编号
     * @return result 1：点赞成功 2：取消成功 其他失败
     */
    @Override
    public Integer likeSpecialist(UserInfo userInfo, Integer speId, Integer userId) {

        try {
            if (userInfo != null && speId != null && userId != null) {
                SpeLike speLike;
                //1.查询该用户是否给该专家点过赞
                try {
                    String sql = "SELECT L.LikeId,L.SpeId,L.UserId,L.Flag FROM F_SPE_SpeLike L WHERE L.UserId = ? AND L.SpeId = ?";
                    speLike = jdbcTemplate.queryForObject(sql, new Object[]{userId, speId}, (rs, rowNum) -> {
                        SpeLike sLike = new SpeLike();
                        sLike.setLikeId(rs.getInt("LikeId"));
                        sLike.setSpeId(rs.getInt("SpeId"));
                        sLike.setUserId(rs.getInt("UserId"));
                        sLike.setFlag(rs.getInt("Flag"));
                        return sLike;
                    });
                } catch (EmptyResultDataAccessException e) {
                    speLike = null;
                }
                //2.获取该专家现有赞数
                SpecialistInfo specialistInfo = getSpecialistInfoDetailBySpeId(userInfo, speId);
                Integer speLikeCount = specialistInfo.getSpeLikeCount();
                //3.保存专家点赞表并更新专家获赞数
                Integer result = saveSpeLike(userInfo, speId, userId, speLike, speLikeCount);
                logger.info("======[" + userInfo.getUserId() + "]==========>Service点赞专家接口调用成功！=====> result = " + result);
                return result;
            } else {
                logger.info("==========>Service点赞专家接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service点赞专家接口调用异常：");
            ep.printStackTrace();
            return null;
        }

    }

    /**
     * 保存专家点赞表并更新专家获赞数
     *
     * @param userInfo     调用接口用户信息
     * @param speId        专家编号
     * @param userId       点赞者编号
     * @param speLike      专家列表对象
     * @param speLikeCount 获取该专家现有赞数
     * @return result
     * @throws Exception 专家点赞表出现异常
     */
    @Transactional
    public Integer saveSpeLike(UserInfo userInfo, Integer speId, Integer userId, SpeLike speLike, Integer speLikeCount) throws Exception {

        String sql = "UPDATE F_SPE_SpecialistInfo SET SpeLikeCount = ? where SpeId = ?";
        Integer result;
        if (speLike == null) {
            //若没有对该专家点过赞插入点赞表，并对专家被赞总数+1
            String sql1 = "INSERT INTO F_SPE_SpeLike (SpeId,UserId,Flag) VALUES(?,?,1)";
            jdbcTemplate.update(sql1, speId, userId);
            speLikeCount ++;
            result = 1;
        } else if (speLike.getFlag() == 1) {
            //若之前对该专家点过赞，则将赞取消，并对专家被赞数-1
            String sql2 = "UPDATE F_SPE_SpeLike set Flag = 2 WHERE SpeId = ? AND UserId = ?";
            jdbcTemplate.update(sql2, speId, userId);
            speLikeCount --;
            result = 2;
        } else if (speLike.getFlag() == 2) {
            //若之前对该专家点过赞且取消了，则将赞再次生效，并对专家被赞数+1
            String sql3 = "UPDATE F_SPE_SpeLike set Flag = 1 WHERE SpeId = ? AND UserId = ?";
            jdbcTemplate.update(sql3, speId, userId);
            speLikeCount ++;
            result = 1;
        } else {
            throw new Exception("专家点赞表出现异常！");
        }

        jdbcTemplate.update(sql, speLikeCount, speId);

        return result;

    }

    /**
     * 查询用户与专家点赞图标标志
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @param userId   点赞者编号
     * @return flag：1：点赞状态（蓝色图标），2：未赞状态（灰色图标），null：异常
     */
    @Override
    public Integer getSpeLikeFlag(UserInfo userInfo, Integer speId, Integer userId) {

        try {

            if (userInfo != null && speId != null && userId != null) {
                Integer flag;
                try {
                    String sql = "SELECT L.LikeId,L.SpeId,L.UserId,L.Flag FROM F_SPE_SpeLike L WHERE L.UserId = ? AND L.SpeId = ?";
                    flag = jdbcTemplate.queryForObject(sql, new Object[]{userId, speId}, (rs, rowNum) -> {
                        Integer f = rs.getInt("Flag");
                        return f;
                    });
                } catch (EmptyResultDataAccessException e) {
                    flag = 2;
                }
                return flag;
            } else {
                logger.info("==========>Service查询用户与专家点赞图标标志接口必传参数为空！");
                return null;
            }
        } catch (Exception ep) {
            logger.debug("==========>Service查询用户与专家点赞图标标志接口调用异常！");
            ep.printStackTrace();
            return null;
        }


    }

}

