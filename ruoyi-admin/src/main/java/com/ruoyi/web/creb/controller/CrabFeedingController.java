package com.ruoyi.web.creb.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.web.creb.domain.CrabFeeding;
import com.ruoyi.web.creb.service.ICrabFeedingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投喂记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/feeding")
public class CrabFeedingController extends BaseController
{
    @Autowired
    private ICrabFeedingService crabFeedingService;

    /**
     * 查询投喂记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabFeeding crabFeeding)
    {
        startPage();
        List<CrabFeeding> list = crabFeedingService.selectCrabFeedingList(crabFeeding);
        return getDataTable(list);
    }

    /**
     * 导出投喂记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:export')")
    @Log(title = "投喂记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabFeeding crabFeeding)
    {
        List<CrabFeeding> list = crabFeedingService.selectCrabFeedingList(crabFeeding);
        ExcelUtil<CrabFeeding> util = new ExcelUtil<CrabFeeding>(CrabFeeding.class);
        util.exportExcel(response, list, "投喂记录数据");
    }

    /**
     * 获取投喂记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(crabFeedingService.selectCrabFeedingByRecordId(recordId));
    }

    /**
     * 新增投喂记录
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:add')")
    @Log(title = "投喂记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabFeeding crabFeeding)
    {
        return toAjax(crabFeedingService.insertCrabFeeding(crabFeeding));
    }

    /**
     * 修改投喂记录
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:edit')")
    @Log(title = "投喂记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabFeeding crabFeeding)
    {
        return toAjax(crabFeedingService.updateCrabFeeding(crabFeeding));
    }

    /**
     * 删除投喂记录
     */
    @PreAuthorize("@ss.hasPermi('creb:feeding:remove')")
    @Log(title = "投喂记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(crabFeedingService.deleteCrabFeedingByRecordIds(recordIds));
    }
}
