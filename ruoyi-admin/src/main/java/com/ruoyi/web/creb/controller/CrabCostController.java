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
import com.ruoyi.web.creb.domain.CrabCost;
import com.ruoyi.web.creb.service.ICrabCostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 养殖成本记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/cost")
public class CrabCostController extends BaseController
{
    @Autowired
    private ICrabCostService crabCostService;

    /**
     * 查询养殖成本记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabCost crabCost)
    {
        startPage();
        List<CrabCost> list = crabCostService.selectCrabCostList(crabCost);
        return getDataTable(list);
    }

    /**
     * 导出养殖成本记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:export')")
    @Log(title = "养殖成本记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabCost crabCost)
    {
        List<CrabCost> list = crabCostService.selectCrabCostList(crabCost);
        ExcelUtil<CrabCost> util = new ExcelUtil<CrabCost>(CrabCost.class);
        util.exportExcel(response, list, "养殖成本记录数据");
    }

    /**
     * 获取养殖成本记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(crabCostService.selectCrabCostByRecordId(recordId));
    }

    /**
     * 新增养殖成本记录
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:add')")
    @Log(title = "养殖成本记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabCost crabCost)
    {
        return toAjax(crabCostService.insertCrabCost(crabCost));
    }

    /**
     * 修改养殖成本记录
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:edit')")
    @Log(title = "养殖成本记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabCost crabCost)
    {
        return toAjax(crabCostService.updateCrabCost(crabCost));
    }

    /**
     * 删除养殖成本记录
     */
    @PreAuthorize("@ss.hasPermi('creb:cost:remove')")
    @Log(title = "养殖成本记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(crabCostService.deleteCrabCostByRecordIds(recordIds));
    }
}
