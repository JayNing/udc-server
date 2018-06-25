package com.mascot.controller;

import com.interfaces.file.BasicService;
import com.interfaces.mascot.AuthorityManager;
import com.interfaces.usercenter.UserInfoService;
import com.mascot.utils.common.SpringBean;
import com.thrift.common.body.FileInfo;
import com.thrift.common.body.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * 测试
 * Created by liujinren on 2017/7/25.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AuthorityManager authorityManager;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String getSystemInfo(Model model){

        System.out.println(authorityManager.getTest("reqTest"));

//        // 模拟访问用户中心
//        UDCHead SERVER_HEAD = new UDCHead("EnterpriseServer", null, null, null, null, null);
//        try {
//            UserService.Client client = udcUserCenter.getClient();
//            List<String> emailList = new ArrayList<>();
//            System.out.println("======> " + client.getUserInfoByEmail(SERVER_HEAD, emailList));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 模拟访问 MQ
//        try {
//            //UdcMqUtils.instance().sendMessage(null, null, null, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 模拟访问文件系统
//        try {
//            FileInfoResp fileInfo = udcFileSystem.getClient().getFileInfo("/1/2017/8/21/8104b5f2-1ff5-4b5e-b375-5729d057fc36.png");
//            System.out.println("===> " + fileInfo.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return "basic";
    }

    @RequestMapping(value = "/req/test")
    @ResponseBody
    public String test() {

        System.out.println("==============/req/test================");

        System.out.println("==============测试： 远程用户中心================");
        UserInfoService userInfoService = (UserInfoService) SpringBean.getObject("userInfoService");
        UserSessionInfo userSessionInfo = userInfoService.verifyLoginByTokenId(UUID.randomUUID().toString());
        System.out.println("remote usercenter---->>>>>>" + userSessionInfo);


        System.out.println("==============测试： 远程文件系统================");
        BasicService fileSystem = (BasicService) SpringBean.getObject("fileBasicService");
        FileInfo fileInfo = fileSystem.getFileInfo("/1/2017/8/21/8104b5f2-1ff5-4b5e-b375-5729d057fc36.png");
        System.out.println("remote fileSystem---->>>>>>" + (fileInfo != null ?fileInfo.toString(): null));

        return "OK";
    }
}
