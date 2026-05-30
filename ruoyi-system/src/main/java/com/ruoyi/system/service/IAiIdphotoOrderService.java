package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AiIdphotoOrder;
import com.ruoyi.system.domain.vo.IdPhotoGenerateRequest;
import com.ruoyi.system.domain.vo.IdPhotoGenerateResponse;

/**
 * AI证件照订单 Service 接口
 *
 * @author ruoyi
 */
public interface IAiIdphotoOrderService
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
     * 生成证件照（创建订单并调用AI处理）
     *
     * @param request 生成请求
     * @return 生成结果
     */
    IdPhotoGenerateResponse generate(IdPhotoGenerateRequest request);

    /**
     * 支付订单
     *
     * @param orderNo 订单号
     * @param payType 支付方式
     * @return 结果
     */
    int payOrder(String orderNo, String payType);

    /**
     * 取消过期订单
     *
     * @return 结果
     */
    int cancelExpiredOrders();
}
