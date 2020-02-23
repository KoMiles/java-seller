package com.imooc.seller.controller;

import com.imooc.seller.VO.ProductInfoVO;
import com.imooc.seller.VO.ProductVO;
import com.imooc.seller.VO.ResultVO;
import com.imooc.seller.dataobject.ProductCategory;
import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.service.impl.CategoryServiceImpl;
import com.imooc.seller.service.impl.ProductInfoServiceImpl;
import com.imooc.seller.utils.ResultVOUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public ResultVO list()
    {
        // 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findOnlineList(ProductStatusEnums.UP.getCode());
//        System.out.println(productInfoList.toString());

//         查询类目（一次性查询）
//        List<Integer> categoryIdList = new ArrayList<>();
//        for(ProductInfo productInfo : productInfoList) {
//            categoryIdList.add(productInfo.getCategoryType());
//        }
//        categoryService.findByCategoryTypeIn(categoryIdList);
//         精简写法 lambda 表达式
        List<Integer> categoryIdList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryIdList);
        // 数据拼装

        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtils.success(productVOList);
    }
}
