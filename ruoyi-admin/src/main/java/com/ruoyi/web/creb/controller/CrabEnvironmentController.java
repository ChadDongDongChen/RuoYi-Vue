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
import com.ruoyi.web.creb.domain.CrabEnvironment;
import com.ruoyi.web.creb.service.ICrabEnvironmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境数据记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/environment")
public class CrabEnvironmentController extends BaseController
{
    @Autowired
    private ICrabEnvironmentService crabEnvironmentService;

    /**
     * 查询环境数据记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabEnvironment crabEnvironment)
    {
        startPage();
        List<CrabEnvironment> list = crabEnvironmentService.selectCrabEnvironmentList(crabEnvironment);
        return getDataTable(list);
    }

    /**
     * 导出环境数据记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:export')")
    @Log(title = "环境数据记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabEnvironment crabEnvironment)
    {
        List<CrabEnvironment> list = crabEnvironmentService.selectCrabEnvironmentList(crabEnvironment);
        ExcelUtil<CrabEnvironment> util = new ExcelUtil<CrabEnvironment>(CrabEnvironment.class);
        util.exportExcel(response, list, "环境数据记录数据");
    }

    /**
     * 获取环境数据记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:query')")
    @GetMapping(value = "/{dataId}")
    public AjaxResult getInfo(@PathVariable("dataId") Long dataId)
    {
        return success(crabEnvironmentService.selectCrabEnvironmentByDataId(dataId));
    }

    /**
     * 新增环境数据记录
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:add')")
    @Log(title = "环境数据记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabEnvironment crabEnvironment)
    {
        return toAjax(crabEnvironmentService.insertCrabEnvironment(crabEnvironment));
    }

    /**
     * 修改环境数据记录
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:edit')")
    @Log(title = "环境数据记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabEnvironment crabEnvironment)
    {
        return toAjax(crabEnvironmentService.updateCrabEnvironment(crabEnvironment));
    }

    /**
     * 删除环境数据记录
     */
    @PreAuthorize("@ss.hasPermi('creb:environment:remove')")
    @Log(title = "环境数据记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dataIds}")
    public AjaxResult remove(@PathVariable Long[] dataIds)
    {
        return toAjax(crabEnvironmentService.deleteCrabEnvironmentByDataIds(dataIds));
    }
}
