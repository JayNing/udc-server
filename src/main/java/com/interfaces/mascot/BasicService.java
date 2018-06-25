package com.interfaces.mascot;


import com.thrift.common.body.UserInfo;
import com.thrift.common.body.UserInfoResp;
import com.thrift.common.body.UserSessionInfo;
import org.apache.thrift.TException;

import java.util.List;

/**
 * 公共服务接口
 * Created by liujinren on 2017/7/10.
 */
public interface BasicService {

    /**
     * 检查tokenId是否有效
     * @param tokenId
     * @return
     */
    Boolean checkTokenId(String tokenId) throws TException;
    /**
     * 校验tokenId
     * @param tokenId
     * @return
     */
    UserSessionInfo verifyLoginByTokenId(String tokenId);

    /**
     * 根据 tokenId 获取用户信息
     * @param tokenId
     * @return
     */
    UserInfo getUserInfoByTokenId(String tokenId) throws TException;

    /**
     * 通过用户Id 获取用户信息
     * @param userIdList
     * @return
     */
    UserInfoResp getUserInfoByIdList(List<Integer> userIdList);

}
