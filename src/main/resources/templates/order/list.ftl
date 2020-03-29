<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--    商品列表-->
    <div class="container">
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
                            <th>详情</th>
                            <th>取消</th>
                        </tr>

                    </#list>

                    </tbody>
                </table>
            </div>
        </div>
    </div>

<#--    分页部分-->
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <ul class="pagination">
                    <li>
                        <a href="#">上一页</a>
                    </li>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <li><a href="#">${index}</a></li>
                    </#list>

                    <li>
                        <a href="#">下一页</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>