package com.imooc.seller.service.impl;

import com.imooc.seller.DTO.CartDTO;
import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.repository.ProductInfoRepository;
import com.imooc.seller.service.ProductInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductInfo> findOnlineList(Integer productStatus) {
        return repository.findProductInfoByProductStatus(ProductStatusEnums.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    // 增加库存
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList) {
            // 判断商品是否存在
            ProductInfo productInfo = repository.getOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellerException(ResultEnums.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    // 减少库存
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList) {
            // 判断商品是否存在
            ProductInfo productInfo = repository.getOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellerException(ResultEnums.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0) {
                throw new SellerException(ResultEnums.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
