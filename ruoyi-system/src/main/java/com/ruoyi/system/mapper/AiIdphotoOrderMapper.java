package com.ruoyi.system.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.AiIdphotoOrder;

/**
 * AI证件照订单 Mapper 接口
 *
 * @author ruoyi
 */
public interface AiIdphotoOrderMapper
{
    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    List<AiIdphotoOrder> selectAiIdphotoOrderList(AiIdphotoOrder order);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    AiIdphotoOrder selectAiIdphotoOrderByOrderNo(String orderNo);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单信息
     */
    AiIdphotoOrder selectAiIdphotoOrderById(Long orderId);

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int insertAiIdphotoOrder(AiIdphotoOrder order);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int updateAiIdphotoOrder(AiIdphotoOrder order);

    /**
     * 更新订单支付状态
     *
     * @param orderNo 订单号
     * @param status 新状态
     * @param payTime 支付时间
     * @return 结果
     */
    int updatePayStatus(@Param("orderNo") String orderNo, @Param("status") String status, @Param("payTime") Date payTime);

    /**
     * 更新订单结果图
     *
     * @param orderNo 订单号
     * @param resultUrl 结果图URL
     * @return 结果
     */
    int updateResultUrl(@Param("orderNo") String orderNo, @Param("resultUrl") String resultUrl);

    /**
     * 查询过期未支付订单
     *
     * @param now 当前时间
     * @return 订单集合
     */
    List<AiIdphotoOrder> selectExpiredOrders(@Param("now") Date now);
}
