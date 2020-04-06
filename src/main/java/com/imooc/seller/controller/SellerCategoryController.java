package com.imooc.seller.controller;

import com.imooc.seller.dataobject.ProductCategory;
import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.form.CategoryForm;
import com.imooc.seller.service.CategoryService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author komiles@163.com
 * @date 2020-04-06 21:31
 */
@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView categoryList(Map<String, Object> map)
    {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categoryList", productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
            Map<String, Object> map)
    {
        if(categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 保存
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map)
    {
        if(bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
        try{
            ProductCategory productCategory = new ProductCategory();
            if(productCategory.getCategoryId() != null) {
                productCategory = categoryService.findOne(productCategory.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            categoryService.save(productCategory);
        } catch (SellerException e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnums.CATEGORY_SAVE_SUCCESS.getMessage());
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);

    }
}
