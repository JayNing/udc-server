package com.interfaces.usercenter;

import com.thrift.common.body.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 公共服务接口
 * Created by liujinren on 2017/7/10.
 */
public interface BasicService {

    /**
     * 获取用户列表信息
     * @param paramMap
     * @param sql
     * @return
     */
    List<UserInfo> queryUserList(Map<String, Object> paramMap, String sql);

}
