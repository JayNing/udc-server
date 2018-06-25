package com.mascot.utils.common;


import com.mascot.exception.ParamsException;

/**
 * Created by j on 2018/3/1.
 */
public class AssertUtil {

    public static   void isTrue(Boolean flag,String msg){
        if(flag){
            throw new ParamsException(msg);
        }
    }
}
