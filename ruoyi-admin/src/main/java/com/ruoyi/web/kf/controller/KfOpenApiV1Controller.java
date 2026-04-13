package com.ruoyi.web.kf.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.web.kf.domain.KfCustomer;
import com.ruoyi.web.kf.domain.KfMessage;
import com.ruoyi.web.kf.domain.KfProduct;
import com.ruoyi.web.kf.mapper.KfCustomerMapper;
import com.ruoyi.web.kf.mapper.KfMessageMapper;
import com.ruoyi.web.kf.service.IKfProductService;

/**
 * 对外开放 REST（需 Bearer / X-Api-Key = 库中 api_secret）
 */
@RestController
@RequestMapping("/open/api/v1")
public class KfOpenApiV1Controller extends BaseController
{
    @Autowired
    private IKfProductService kfProductService;

    @Autowired
    private KfCustomerMapper kfCustomerMapper;

    @Autowired
    private KfMessageMapper kfMessageMapper;

    @GetMapping("/products")
    public R<TableDataInfo> openProducts(KfProduct query)
    {
        if (query == null)
        {
            query = new KfProduct();
        }
        query.setStatus("0");
        startPage();
        List<KfProduct> list = kfProductService.selectKfProductList(query);
        return R.ok(getDataTable(list));
    }

    @GetMapping("/customers")
    public R<TableDataInfo> openCustomers(KfCustomer query)
    {
        startPage();
        List<KfCustomer> list = kfCustomerMapper.selectKfCustomerList(query != null ? query : new KfCustomer());
        List<KfCustomer> masked = new ArrayList<>();
        for (KfCustomer c : list)
        {
            KfCustomer m = new KfCustomer();
            m.setCustomerId(c.getCustomerId());
            m.setExternalUserid(maskExternalId(c.getExternalUserid()));
            m.setOpenKfid(c.getOpenKfid());
            m.setNickname(c.getNickname());
            m.setFirstContactTime(c.getFirstContactTime());
            m.setLastActiveTime(c.getLastActiveTime());
            masked.add(m);
        }
        return R.ok(getDataTable(masked));
    }

    @GetMapping("/messages")
    public R<TableDataInfo> openMessages(KfMessage query)
    {
        startPage();
        List<KfMessage> list = kfMessageMapper.selectKfMessageList(query != null ? query : new KfMessage());
        List<KfMessage> masked = new ArrayList<>();
        for (KfMessage m : list)
        {
            KfMessage x = new KfMessage();
            x.setMessageId(m.getMessageId());
            x.setMsgid(m.getMsgid());
            x.setExternalUserid(maskExternalId(m.getExternalUserid()));
            x.setOpenKfid(m.getOpenKfid());
            x.setDirection(m.getDirection());
            x.setMsgtype(m.getMsgtype());
            x.setContent(m.getContent());
            x.setSendTime(m.getSendTime());
            x.setCreateTime(m.getCreateTime());
            masked.add(x);
        }
        return R.ok(getDataTable(masked));
    }

    private static String maskExternalId(String id)
    {
        if (id == null || id.length() <= 8)
        {
            return "***";
        }
        return id.substring(0, 6) + "***";
    }
}
