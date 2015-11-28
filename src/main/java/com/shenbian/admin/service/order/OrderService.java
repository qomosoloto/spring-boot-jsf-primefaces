package com.shenbian.admin.service.order;

import com.shenbian.ng.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qomo on 15-10-19.
 */
public interface OrderService extends JpaRepository<Order,Long>,OrderServiceExt{
}
