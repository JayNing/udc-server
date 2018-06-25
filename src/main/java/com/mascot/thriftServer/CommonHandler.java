package com.mascot.thriftServer;

import com.interfaces.mascot.BasicService;
import com.mascot.utils.funInterface.ExecuteFuc;
import com.thrift.common.body.ResponseInfo;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.ResponseCode;
import com.thrift.common.head.UDCHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 公共处理
 * Created by liujinren on 2017/8/10.
 */
public class CommonHandler {
    private static final Log logger = LogFactory.getLog(CommonHandler.class);

    // SpringBean 对象
    @Autowired
    protected BasicService basicService;

    /**
     * 校验tokenId
     * @param tokenId
     * @return
     */
    protected Boolean checkTokenId(String tokenId){
        if (StringUtils.hasText(tokenId)){
            try {
                return basicService.checkTokenId(tokenId);
            } catch (TException e) {
                logger.error("向用户中心校验tokenId 异常", e);
            }
        }
        return false;
    }

    protected UserInfo getUserInfoByTokenId(String tokenId){
        if (StringUtils.hasText(tokenId)){
            try {
                return basicService.getUserInfoByTokenId(tokenId);
            } catch (TException e) {
                logger.error("向用户中心校验tokenId 异常");
            }
        }
        return null;
    }

    /**
     * 校验请求者信息
     * @param head
     * @param fuc 执行函数
     * @param resp 返回信息
     * @param <T> 返回参数
     * @return
     */
    protected <T> T checkUserInfo(UDCHead head, ExecuteFuc<T> fuc, ResponseInfo resp){
        // 校验登录
        if (head != null && StringUtils.hasText(head.getTokenId())) {
            Boolean aBoolean = checkTokenId(head.getTokenId());
            if (aBoolean) {
                UserInfo userInfo = getUserInfoByTokenId(head.getTokenId());
                if (userInfo != null) {
                    try {
                        T execute = fuc.execute(userInfo);
                        resp.setCode(ResponseCode.Succeed.getValue());
                        resp.setMessage("OK");
                        return execute;
                    } catch (TException e) {
                        logger.error("ExecuteFuc 执行异常", e);
                    }
                } else {
                    resp.setCode(ResponseCode.LoginException.getValue());
                    resp.setMessage("登录异常");
                }
            } else {
                resp.setCode(ResponseCode.NotLogin.getValue());
                resp.setMessage("用户未登录！");
            }
        } else {
            resp.setCode(ResponseCode.TokenIdNotFound.getValue());
            resp.setMessage("tokenId未发现！");
        }
        return null;
    }

    /**
     * 校验请求者是否为本平台非企业用户信息
     * @param head
     * @param fuc 执行函数
     * @param resp 返回信息
     * @param <T> 返回参数
     * @return
     */
    protected <T> T checkUserInfoNoEnterprise(UDCHead head, ExecuteFuc<T> fuc, ResponseInfo resp){
        // 校验登录
        if (head != null && StringUtils.hasText(head.getTokenId())) {
            Boolean aBoolean = checkTokenId(head.getTokenId());
            if (aBoolean) {
                UserInfo userInfo = getUserInfoByTokenId(head.getTokenId());
                if (userInfo != null) {

                    // TODO 待完成


                } else {
                    resp.setCode(ResponseCode.LoginException.getValue());
                    resp.setMessage("登录异常");
                }
            } else {
                resp.setCode(ResponseCode.NotLogin.getValue());
                resp.setMessage("用户未登录！");
            }
        } else {
            resp.setCode(ResponseCode.TokenIdNotFound.getValue());
            resp.setMessage("tokenId未发现！");
        }
        return null;
    }

}
