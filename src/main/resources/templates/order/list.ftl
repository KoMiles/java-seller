<html xmlns="http://www.w3.org/1999/html">
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
<#--    商品列表-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-condensed table-bordered">
                        <thead>
                        <tr>
                            <th>订单Id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>支付状态</th>
                            <th>订单状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.getContent() as orderDTO>
                            <tr>
                                <th>${orderDTO.getOrderId()}</th>
                                <th>${orderDTO.getBuyerName()}</th>
                                <th>${orderDTO.getBuyerPhone()}</th>
                                <th>${orderDTO.getBuyerAddress()}</th>
                                <th>${orderDTO.getOrderAmount()}</th>
                                <th>${orderDTO.getPayStatusEnums().getMessage()}</th>
                                <th>${orderDTO.getOrderStatusEnums().getMessage()}</th>
                                <th>${orderDTO.getCreateTime()}</th>

                                <th><a href="/sell/seller/order/detail?orderId=${orderDTO.getOrderId()}">详情</a></th>
                                <#if orderDTO.getOrderStatusEnums().getMessage() == "新订单">
                                    <th><a href="/sell/seller/order/cancel?orderId=${orderDTO.getOrderId()}">取消</a></th>
                                    <#else>
                                        <th></th>
                                </#if>
                            </tr>

                        </#list>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <#--    分页部分-->
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <ul class="pagination">
                    <#if currentPage gt 1 >
                        <li>
                            <a href="/sell/seller/order/list?page=${currentPage-1}&size=${pageSize}">上一页</a>
                        </li>
                    <#else>
                        <li class="disabled"><a>上一页</a></li>
                    </#if>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a>${index}</a></li>
                        <#elseif index gt currentPage+3>
                                <li><a href="#">...</a></li>
                                <#break >
                        <#elseif index lt currentPage-3>
                            <#continue >
                        <#elseif index == currentPage-3>
                            <li><a href="#">...</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${pageSize}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage lt orderDTOPage.totalPages>
                        <li>
                            <a href="/sell/seller/order/list?page=${currentPage+1}&size=${pageSize}">下一页</a>
                        </li>
                    <#else>
                            <li class="disabled"><a href="#">下一页</a></li>
                    </#if>


                </ul>
            </div>
        </div>
    </div>
</div>