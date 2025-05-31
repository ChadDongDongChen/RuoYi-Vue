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
import com.ruoyi.web.creb.domain.CrabGrowth;
import com.ruoyi.web.creb.service.ICrabGrowthService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 螃蟹生长记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/growth")
public class CrabGrowthController extends BaseController
{
    @Autowired
    private ICrabGrowthService crabGrowthService;

    /**
     * 查询螃蟹生长记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabGrowth crabGrowth)
    {
        startPage();
        List<CrabGrowth> list = crabGrowthService.selectCrabGrowthList(crabGrowth);
        return getDataTable(list);
    }

    /**
     * 导出螃蟹生长记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:export')")
    @Log(title = "螃蟹生长记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabGrowth crabGrowth)
    {
        List<CrabGrowth> list = crabGrowthService.selectCrabGrowthList(crabGrowth);
        ExcelUtil<CrabGrowth> util = new ExcelUtil<CrabGrowth>(CrabGrowth.class);
        util.exportExcel(response, list, "螃蟹生长记录数据");
    }

    /**
     * 获取螃蟹生长记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(crabGrowthService.selectCrabGrowthByRecordId(recordId));
    }

    /**
     * 新增螃蟹生长记录
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:add')")
    @Log(title = "螃蟹生长记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabGrowth crabGrowth)
    {
        return toAjax(crabGrowthService.insertCrabGrowth(crabGrowth));
    }

    /**
     * 修改螃蟹生长记录
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:edit')")
    @Log(title = "螃蟹生长记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabGrowth crabGrowth)
    {
        return toAjax(crabGrowthService.updateCrabGrowth(crabGrowth));
    }

    /**
     * 删除螃蟹生长记录
     */
    @PreAuthorize("@ss.hasPermi('creb:growth:remove')")
    @Log(title = "螃蟹生长记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(crabGrowthService.deleteCrabGrowthByRecordIds(recordIds));
    }
}
