package com.mascot.utils.funInterface;

import com.thrift.common.body.UserInfo;
import org.apache.thrift.TException;

@FunctionalInterface
public interface ExecuteFuc<T> {

    T execute(UserInfo userInfo) throws TException;

}



