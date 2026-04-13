package com.ruoyi.web.kf.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.web.kf.domain.KfMessage;
import com.ruoyi.web.kf.mapper.KfMessageMapper;

@RestController
@RequestMapping("/kf/message")
public class KfMessageController extends BaseController
{
    @Autowired
    private KfMessageMapper kfMessageMapper;

    @PreAuthorize("@ss.hasPermi('kf:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(KfMessage query)
    {
        startPage();
        List<KfMessage> list = kfMessageMapper.selectKfMessageList(query);
        return getDataTable(list);
    }
}
