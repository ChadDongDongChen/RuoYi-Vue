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
import com.ruoyi.web.creb.domain.CrabAlert;
import com.ruoyi.web.creb.service.ICrabAlertService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常预警记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/alert")
public class CrabAlertController extends BaseController
{
    @Autowired
    private ICrabAlertService crabAlertService;

    /**
     * 查询异常预警记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabAlert crabAlert)
    {
        startPage();
        List<CrabAlert> list = crabAlertService.selectCrabAlertList(crabAlert);
        return getDataTable(list);
    }

    /**
     * 导出异常预警记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:export')")
    @Log(title = "异常预警记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabAlert crabAlert)
    {
        List<CrabAlert> list = crabAlertService.selectCrabAlertList(crabAlert);
        ExcelUtil<CrabAlert> util = new ExcelUtil<CrabAlert>(CrabAlert.class);
        util.exportExcel(response, list, "异常预警记录数据");
    }

    /**
     * 获取异常预警记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:query')")
    @GetMapping(value = "/{alertId}")
    public AjaxResult getInfo(@PathVariable("alertId") Long alertId)
    {
        return success(crabAlertService.selectCrabAlertByAlertId(alertId));
    }

    /**
     * 新增异常预警记录
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:add')")
    @Log(title = "异常预警记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabAlert crabAlert)
    {
        return toAjax(crabAlertService.insertCrabAlert(crabAlert));
    }

    /**
     * 修改异常预警记录
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:edit')")
    @Log(title = "异常预警记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabAlert crabAlert)
    {
        return toAjax(crabAlertService.updateCrabAlert(crabAlert));
    }

    /**
     * 删除异常预警记录
     */
    @PreAuthorize("@ss.hasPermi('creb:alert:remove')")
    @Log(title = "异常预警记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alertIds}")
    public AjaxResult remove(@PathVariable Long[] alertIds)
    {
        return toAjax(crabAlertService.deleteCrabAlertByAlertIds(alertIds));
    }
}
