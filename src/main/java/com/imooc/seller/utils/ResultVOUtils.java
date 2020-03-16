package com.imooc.seller.utils;


import com.imooc.seller.VO.ResultVO;
import com.imooc.seller.enums.ResultEnums;

public class ResultVOUtils {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnums.SUCCESS.getCode());
        resultVO.setMsg(ResultEnums.SUCCESS.getMessage());
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
