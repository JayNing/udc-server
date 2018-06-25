package com.mascot.thriftServer;

import com.thrift.common.body.ResponseInfo;
import com.thrift.common.define.ResponseCode;
import com.thrift.mascot.server.MascotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 * Created by Jerry on 2018/1/23.
 */
//@Service(value = "mascotServiceHandler")
public class MascotServiceHandler extends CommonHandler implements MascotService.Iface {

    private static final Log logger = LogFactory.getLog(MascotServiceHandler.class);

    @Override
    public ResponseInfo ping() throws TException {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setCode(ResponseCode.Succeed.getValue());
        responseInfo.setMessage("OK");
        return responseInfo;
    }




}
