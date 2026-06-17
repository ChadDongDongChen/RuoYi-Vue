package com.ruoyi.web.photo.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.photo.mapper.PhotoOrderMapper;
import com.ruoyi.web.photo.domain.PhotoOrder;
import com.ruoyi.web.photo.service.IPhotoOrderService;

@Service
public class PhotoOrderServiceImpl implements IPhotoOrderService
{
    @Autowired
    private PhotoOrderMapper photoOrderMapper;

    @Override
    public PhotoOrder selectOrderByOrderNo(String orderNo)
    {
        return photoOrderMapper.selectOrderByOrderNo(orderNo);
    }

    @Override
    public List<PhotoOrder> selectOrdersByUserId(Long userId)
    {
        return photoOrderMapper.selectOrdersByUserId(userId);
    }

    @Override
    public int createOrder(PhotoOrder order)
    {
        return photoOrderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(PhotoOrder order)
    {
        return photoOrderMapper.updateOrder(order);
    }

    @Override
    public int payOrder(String orderNo)
    {
        PhotoOrder order = photoOrderMapper.selectOrderByOrderNo(orderNo);
        if (order == null)
        {
            return -1;
        }
        order.setStatus("paid");
        order.setPayTime(new Date());
        order.setPayType("wechat");
        return photoOrderMapper.updateOrder(order);
    }
}
