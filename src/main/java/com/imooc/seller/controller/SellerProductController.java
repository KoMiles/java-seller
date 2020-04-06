package com.imooc.seller.controller;

import com.imooc.seller.VO.ProductInfoVO;
import com.imooc.seller.dataobject.ProductCategory;
import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.form.ProductForm;
import com.imooc.seller.service.CategoryService;
import com.imooc.seller.service.ProductInfoService;
import com.imooc.seller.utils.KeyUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author komiles@163.com
 * @date 2020-04-06 19:14
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
            Map<String, Object> map)
    {
        if(!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categoryList", productCategoryList);

        return new ModelAndView("product/index",map);

    }

    @GetMapping("/list")
    public ModelAndView orderList(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map)
    {

        PageRequest pageRequest = PageRequest.of(page,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);

        List<Integer> categoryIdList = productInfoPage.stream().map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryIdList);
        List<ProductInfoVO>  productInfoVOList= new ArrayList<>();

        for(ProductInfo productInfo:productInfoPage) {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            for(ProductCategory productCategory: productCategoryList) {
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                    productInfoVO.setProductCategory(productCategory);
                }
            }
            BeanUtils.copyProperties(productInfo,productInfoVO);
            productInfoVOList.add(productInfoVO);
        }
        map.put("productInfoPage", productInfoVOList);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", productInfoPage.getTotalPages());

        return new ModelAndView("product/list",map);
    }

    /**
     * 下架商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam(value = "productId", defaultValue = "0") String productId,
            Map<String, Object> map)
    {

        try{
            productInfoService.offSale(productId);
        }catch (SellerException e){
            log.error("【卖家端上架商品】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.PRODUCT_OFFSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");

        return new ModelAndView("common/success",map);
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam(value = "productId", defaultValue = "0") String productId,
            Map<String, Object> map)
    {
        try{
            productInfoService.onSale(productId);
        }catch (SellerException e){
            log.error("【卖家端上架商品】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.PRODUCT_ONSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    /**
     * 保存和更新
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String, Object> map)
    {
        if(bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{
            if(!StringUtils.isEmpty(productForm.getProductId())) { // 修改
                productInfo = productInfoService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);
        } catch (SellerException e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.PRODUCT_SAVE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }


}
