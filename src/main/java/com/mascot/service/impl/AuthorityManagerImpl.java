package com.mascot.service.impl;

import com.interfaces.mascot.AuthorityManager;
import com.mascot.bean.ResponseInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * Created by liujinren on 2018/4/9.
 */
@Service(value = "authorityManager")
public class AuthorityManagerImpl extends BasicServiceImpl implements AuthorityManager {


    @Override
    public Integer getTest(String str) {

//        List<Integer> query = jdbcTemplate.query("SELECT `UserId`, `NickName`, `HelperUin`, `CreateTime` FROM `UserInfo`", new RowMapper<Integer>() {
//            @Override
//            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return rs.getInt("UserId");
//            }
//        });
//        System.out.println(query.size());



        if (StringUtils.hasText(str))
            return str.length();
        return null;
    }
}
