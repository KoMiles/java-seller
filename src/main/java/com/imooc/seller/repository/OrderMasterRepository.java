package com.imooc.seller.repository;

import com.imooc.seller.dataobject.OrderMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    List<OrderMaster> findByBuyerOpenid(String buyerOpenid);



}
