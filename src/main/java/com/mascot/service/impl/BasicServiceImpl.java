package com.mascot.service.impl;

import com.interfaces.usercenter.UserInfoService;

import com.interfaces.mascot.BasicService;
import com.mascot.utils.common.PageUtil;
import com.mascot.utils.common.UdcRpcUtils;
import com.thrift.common.body.UserInfo;
import com.thrift.common.body.UserInfoResp;
import com.thrift.common.body.UserSessionInfo;
import com.thrift.common.head.UDCHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BasicService 实现类
 * Created by liujinren on 2017/7/10.
 */
@Service(value = "basicService")
public class BasicServiceImpl implements BasicService {

    private final static Log logger = LogFactory.getLog(BasicServiceImpl.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    protected PageUtil pageUtil;

    @Autowired
    com.interfaces.file.BasicService fileBasicService;


    @Override
    public Boolean checkTokenId(String tokenId) throws TException {

        UserSessionInfo userSessionInfo = verifyLoginByTokenId(tokenId);

        if (userSessionInfo != null) {
            return true;
        }
        return false;
    }

    @Override
    public UserSessionInfo verifyLoginByTokenId(String tokenId) {
        try {
            UDCHead head = new UDCHead();
            head.setTokenId(UdcRpcUtils.instance().getCurrentServerCode());
            return userInfoService.verifyLoginByTokenId(tokenId);
        } catch (Exception e) {
            logger.error("向用户中心校验tokenId 异常", e);

        }
        return null;
    }

    @Override
    public UserInfo getUserInfoByTokenId(String tokenId) throws TException {
        return null;
    }

    @Override
    public UserInfoResp getUserInfoByIdList(List<Integer> userIdList) {
        return null;
    }


}
