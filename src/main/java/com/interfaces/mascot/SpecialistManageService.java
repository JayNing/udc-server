package com.interfaces.mascot;

import com.thrift.common.body.SpecialistInfo;
import com.thrift.common.body.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 专家管理接口
 *
 * @author zhangmengyu
 * 2018/4/24
 */
public interface SpecialistManageService extends BasicService {

    /**
     * 获取专家列表信息
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页 --- 不可为空
     * @param pageSize  当前页数据条数 --- 不可为空
     * @return list
     */
    List<SpecialistInfo> getSpecialistInfo(UserInfo userInfo, Integer pageIndex, Integer pageSize);

    /**
     * 获取专家列表信息总条数
     *
     * @param userInfo  调用接口用户信息
     * @return list
     */
    Integer getSpecialistCount(UserInfo userInfo);

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
    Integer saveSpecialistInfo(UserInfo userInfo, String headUrl, String speName, String jobTitle, String speProfile, String honors);

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
    Integer updateSpecialistInfo(UserInfo userInfo, Integer speId, String headUrl, String speName, String jobTitle, String speProfile, String honors);

    /**
     * 通过专家id查询专家详情信息
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @return specialistInfo
     */
    SpecialistInfo getSpecialistInfoDetailBySpeId(UserInfo userInfo, Integer speId);

    /**
     * 删除专家信息
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家流水号
     * @return result
     */
    Integer deleteSpecialistInfo(UserInfo userInfo, Integer speId);

    /**
     * 新增专家咨询
     *
     * @param userInfo      调用接口用户信息
     * @param speId         专家编号
     * @param consultUserId 咨询者编号
     * @param content       咨询内容
     * @return result
     */
    Integer saveSpeConsult(UserInfo userInfo, Integer speId, Integer consultUserId, String content);

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
    List<Map<String, Object>> querySpeConsult(UserInfo userInfo, String title, String realName, Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

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
    Integer querySpeConsultCount(UserInfo userInfo, String title, String realName, Date startTime, Date endTime);

    /**
     * 专家咨询详情
     *
     * @param userInfo  调用接口用户信息
     * @param consultId 咨询编号
     * @return result
     */
    Map<String, Object> querySpeConsultDetail(UserInfo userInfo, Integer consultId);

    /**
     * 点赞专家
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @param userId   点赞者编号
     * @return result 1：点赞成功 2：取消成功 其他失败
     */
    Integer likeSpecialist(UserInfo userInfo, Integer speId, Integer userId);

    /**
     * 查询用户与专家点赞图标标志
     *
     * @param userInfo 调用接口用户信息
     * @param speId    专家编号
     * @param userId   点赞者编号
     * @return flag：1：点赞状态（蓝色图标），2：未赞状态（灰色图标），null：异常
     */
    Integer getSpeLikeFlag(UserInfo userInfo, Integer speId, Integer userId);

}
