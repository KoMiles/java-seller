package com.imooc.seller.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
//@JsonInclude(Include.NON_NULL)
public class ResultVO<T> {

    private Integer code;

    private String msg = "";

    private T data;
}
