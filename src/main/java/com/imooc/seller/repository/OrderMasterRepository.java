package com.imooc.seller.repository;

import com.imooc.seller.dataobject.OrderMaster;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);


    ArrayList<OrderMaster> findByBuyerOpenid(String buyerOpenId);
}
