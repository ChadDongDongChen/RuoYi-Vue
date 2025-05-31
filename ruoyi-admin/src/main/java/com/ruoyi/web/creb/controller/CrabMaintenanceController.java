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
import com.ruoyi.web.creb.domain.CrabMaintenance;
import com.ruoyi.web.creb.service.ICrabMaintenanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 养殖池维护记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/maintenance")
public class CrabMaintenanceController extends BaseController
{
    @Autowired
    private ICrabMaintenanceService crabMaintenanceService;

    /**
     * 查询养殖池维护记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabMaintenance crabMaintenance)
    {
        startPage();
        List<CrabMaintenance> list = crabMaintenanceService.selectCrabMaintenanceList(crabMaintenance);
        return getDataTable(list);
    }

    /**
     * 导出养殖池维护记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:export')")
    @Log(title = "养殖池维护记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabMaintenance crabMaintenance)
    {
        List<CrabMaintenance> list = crabMaintenanceService.selectCrabMaintenanceList(crabMaintenance);
        ExcelUtil<CrabMaintenance> util = new ExcelUtil<CrabMaintenance>(CrabMaintenance.class);
        util.exportExcel(response, list, "养殖池维护记录数据");
    }

    /**
     * 获取养殖池维护记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(crabMaintenanceService.selectCrabMaintenanceByRecordId(recordId));
    }

    /**
     * 新增养殖池维护记录
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:add')")
    @Log(title = "养殖池维护记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabMaintenance crabMaintenance)
    {
        return toAjax(crabMaintenanceService.insertCrabMaintenance(crabMaintenance));
    }

    /**
     * 修改养殖池维护记录
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:edit')")
    @Log(title = "养殖池维护记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabMaintenance crabMaintenance)
    {
        return toAjax(crabMaintenanceService.updateCrabMaintenance(crabMaintenance));
    }

    /**
     * 删除养殖池维护记录
     */
    @PreAuthorize("@ss.hasPermi('creb:maintenance:remove')")
    @Log(title = "养殖池维护记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(crabMaintenanceService.deleteCrabMaintenanceByRecordIds(recordIds));
    }
}
