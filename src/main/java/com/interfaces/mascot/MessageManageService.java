package com.interfaces.mascot;

import com.thrift.common.body.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 消息管理接口
 *
 * @author zhangmengyu
 * 2018/06/02
 */
public interface MessageManageService extends BasicService {

    /**
     * 新增消息
     *
     * @param userInfo       调用接口用户信息
     * @param messageType    消息类型
     * @param messageContent 消息内容
     * @param userIdList     接收消息的人
     * @return result 1：成功; 其他：失败
     */
    Integer addMessage(UserInfo userInfo, Integer messageType, String title, String messageContent, List<Integer> userIdList);

    /**
     * 查询消息
     *
     * @param userInfo        调用接口用户信息
     * @param messageTypeList 类型Id列表
     * @param pageIndex       当前页
     * @param pageSize        每页条数
     * @return list
     */
    List<Map<String, Object>> queryMessage(UserInfo userInfo, List<Integer> messageTypeList, Integer pageIndex, Integer pageSize);

    /**
     * 查询消息总条数
     *
     * @param userInfo        调用接口用户信息
     * @param messageTypeList 类型Id列表
     * @return count
     */
    Integer queryMessageCount(UserInfo userInfo, List<Integer> messageTypeList);

    /**
     * 展示最新一条消息
     *
     * @param userInfo    调用接口用户信息
     * @param messageType 消息类型
     * @return map
     */
    Map<String, Object> queryNewestMessage(UserInfo userInfo, Integer messageType);

    /**
     * 展示消息详情
     *
     * @param userInfo  调用接口用户信息
     * @param messageId 消息Id
     * @return map
     */
    Map<String, Object> queryMessageDetail(UserInfo userInfo, Integer messageId);
}
