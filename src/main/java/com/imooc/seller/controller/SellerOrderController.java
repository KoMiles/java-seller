package com.imooc.seller.controller;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.OrderStatusEnums;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.OrderService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author komiles@163.com
 * @date 2020-03-29 21:18
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView orderList(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map)
    {

        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("pageSize", size);

        return new ModelAndView("order/list",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "orderId", defaultValue = "0") String orderId,
            Map<String, Object> map)
    {
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
        }catch (SellerException e){
            log.error("【卖家端取消订单】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail",map);

    }

    /**
     * 订单取消
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancle(@RequestParam(value = "orderId",defaultValue = "0") String orderId, Map<String,String> map)
    {
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellerException e){
            log.error("【卖家端取消订单】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");

        return new ModelAndView("common/success",map);
    }

    /**
     * 订单完结
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId",defaultValue = "0") String orderId, Map<String,String> map)
    {
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellerException e){
            log.error("【卖家端完结订单】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");

        return new ModelAndView("common/success",map);
    }

}
