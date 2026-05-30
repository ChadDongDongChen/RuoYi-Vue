package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.AiIdphotoOrder;
import com.ruoyi.system.domain.AiIdphotoSpec;
import com.ruoyi.system.domain.vo.IdPhotoGenerateRequest;
import com.ruoyi.system.domain.vo.IdPhotoGenerateResponse;
import com.ruoyi.system.mapper.AiIdphotoOrderMapper;
import com.ruoyi.system.service.IAiIdphotoOrderService;
import com.ruoyi.system.service.IAiIdphotoSpecService;
import com.ruoyi.system.service.IIdPhotoAiService;

/**
 * AI证件照订单 Service 实现
 *
 * @author ruoyi
 */
@Service
public class AiIdphotoOrderServiceImpl implements IAiIdphotoOrderService
{
    private static final Logger log = LoggerFactory.getLogger(AiIdphotoOrderServiceImpl.class);

    @Autowired
    private AiIdphotoOrderMapper orderMapper;

    @Autowired
    private IAiIdphotoSpecService specService;

    @Autowired
    private IIdPhotoAiService idPhotoAiService;

    @Override
    public List<AiIdphotoOrder> selectAiIdphotoOrderList(AiIdphotoOrder order)
    {
        return orderMapper.selectAiIdphotoOrderList(order);
    }

    @Override
    public AiIdphotoOrder selectAiIdphotoOrderByOrderNo(String orderNo)
    {
        return orderMapper.selectAiIdphotoOrderByOrderNo(orderNo);
    }

    @Override
    public IdPhotoGenerateResponse generate(IdPhotoGenerateRequest request)
    {
        // 1. 查询规格
        AiIdphotoSpec spec = specService.selectAiIdphotoSpecById(request.getSpecId());
        if (spec == null)
        {
            throw new ServiceException("证件照规格不存在");
        }
        if ("1".equals(spec.getStatus()))
        {
            throw new ServiceException("该证件照规格已停用");
        }

        // 2. 生成订单号
        String orderNo = generateOrderNo();

        // 3. 创建订单 (userId = 0 表示未登录)
        AiIdphotoOrder order = new AiIdphotoOrder();
        order.setOrderNo(orderNo);
        order.setUserId(0L);
        order.setSpecId(spec.getSpecId());
        order.setSpecName(spec.getSpecName());
        order.setBackground(request.getBackground() != null ? request.getBackground() : "蓝底");
        order.setBeauty(request.getBeauty() != null ? request.getBeauty() : "自然");
        order.setSuit(request.getSuit() != null ? request.getSuit() : "不开启");
        order.setOriginalUrl(request.getOriginalUrl());
        order.setAmount(spec.getPrice());
        order.setStatus("0"); // 待支付
        order.setExpireTime(new Date(System.currentTimeMillis() + 30 * 60 * 1000)); // 30分钟过期

        orderMapper.insertAiIdphotoOrder(order);

        // 4. 调用AI处理
        String resultUrl = idPhotoAiService.process(
                request.getOriginalUrl(), spec, order.getBackground(), order.getBeauty(), order.getSuit());

        // 5. 更新结果图
        orderMapper.updateResultUrl(orderNo, resultUrl);

        // 6. 组装响应
        IdPhotoGenerateResponse response = new IdPhotoGenerateResponse();
        response.setOrderNo(orderNo);
        response.setResultUrl(resultUrl);
        response.setOriginalUrl(request.getOriginalUrl());
        response.setSpecName(spec.getSpecName());
        response.setAmount(spec.getPrice());
        response.setStatus("0");

        log.info("证件照生成成功: orderNo={}, spec={}, amount={}", orderNo, spec.getSpecName(), spec.getPrice());
        return response;
    }

    @Override
    public int payOrder(String orderNo, String payType)
    {
        AiIdphotoOrder order = orderMapper.selectAiIdphotoOrderByOrderNo(orderNo);
        if (order == null)
        {
            throw new ServiceException("订单不存在");
        }
        if (!"0".equals(order.getStatus()))
        {
            throw new ServiceException("订单状态异常, 当前状态: " + order.getStatus());
        }

        return orderMapper.updatePayStatus(orderNo, "1", new Date());
    }

    @Override
    public int cancelExpiredOrders()
    {
        List<AiIdphotoOrder> expiredOrders = orderMapper.selectExpiredOrders(new Date());
        int count = 0;
        for (AiIdphotoOrder order : expiredOrders)
        {
            orderMapper.updatePayStatus(order.getOrderNo(), "2", null);
            count++;
        }
        log.info("取消过期订单数量: {}", count);
        return count;
    }

    /**
     * 生成唯一订单号: IDPH + 时间戳 + 随机串
     */
    private String generateOrderNo()
    {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return "IDPH" + timestamp + random;
    }
}
