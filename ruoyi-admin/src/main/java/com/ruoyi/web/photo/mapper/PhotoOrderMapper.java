package com.ruoyi.web.photo.mapper;

import java.util.List;
import com.ruoyi.web.photo.domain.PhotoOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoOrderMapper
{
    PhotoOrder selectOrderByOrderNo(String orderNo);

    List<PhotoOrder> selectOrdersByUserId(@Param("userId") Long userId);

    int insertOrder(PhotoOrder order);

    int updateOrder(PhotoOrder order);

    int updateStatus(@Param("orderNo") String orderNo, @Param("status") String status);
}
