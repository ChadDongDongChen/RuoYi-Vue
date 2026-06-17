package com.ruoyi.web.photo.service;

import java.util.List;
import com.ruoyi.web.photo.domain.PhotoOrder;

public interface IPhotoOrderService
{
    PhotoOrder selectOrderByOrderNo(String orderNo);

    List<PhotoOrder> selectOrdersByUserId(Long userId);

    int createOrder(PhotoOrder order);

    int updateOrder(PhotoOrder order);

    int payOrder(String orderNo);
}
