package com.ruoyi.web.kf.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.web.kf.domain.KfCustomer;
import com.ruoyi.web.kf.mapper.KfCustomerMapper;

@RestController
@RequestMapping("/kf/customer")
public class KfCustomerController extends BaseController
{
    @Autowired
    private KfCustomerMapper kfCustomerMapper;

    @PreAuthorize("@ss.hasPermi('kf:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(KfCustomer query)
    {
        startPage();
        List<KfCustomer> list = kfCustomerMapper.selectKfCustomerList(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('kf:customer:query')")
    @GetMapping("/{customerId}")
    public AjaxResult getInfo(@PathVariable Long customerId)
    {
        return success(kfCustomerMapper.selectKfCustomerByCustomerId(customerId));
    }
}
