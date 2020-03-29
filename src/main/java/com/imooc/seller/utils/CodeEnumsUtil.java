package com.imooc.seller.utils;


import com.imooc.seller.enums.CodeEnums;

/**
 * @author komiles@163.com
 * @date 2020-03-29 23:48
 */
public class CodeEnumsUtil {

    public static <T extends CodeEnums>T getByCode(Integer code, Class<T> enumClass)
    {
        for(T newEnum: enumClass.getEnumConstants()) {
            if(newEnum.getCode().equals(code)) {
                return newEnum;
            }
        }
        return null;
    }
}
