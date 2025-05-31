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
import com.ruoyi.web.creb.domain.CrabBatch;
import com.ruoyi.web.creb.service.ICrabBatchService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 螃蟹批次信息Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/batch")
public class CrabBatchController extends BaseController
{
    @Autowired
    private ICrabBatchService crabBatchService;

    /**
     * 查询螃蟹批次信息列表
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabBatch crabBatch)
    {
        startPage();
        List<CrabBatch> list = crabBatchService.selectCrabBatchList(crabBatch);
        return getDataTable(list);
    }

    /**
     * 导出螃蟹批次信息列表
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:export')")
    @Log(title = "螃蟹批次信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabBatch crabBatch)
    {
        List<CrabBatch> list = crabBatchService.selectCrabBatchList(crabBatch);
        ExcelUtil<CrabBatch> util = new ExcelUtil<CrabBatch>(CrabBatch.class);
        util.exportExcel(response, list, "螃蟹批次信息数据");
    }

    /**
     * 获取螃蟹批次信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:query')")
    @GetMapping(value = "/{batchId}")
    public AjaxResult getInfo(@PathVariable("batchId") Long batchId)
    {
        return success(crabBatchService.selectCrabBatchByBatchId(batchId));
    }

    /**
     * 新增螃蟹批次信息
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:add')")
    @Log(title = "螃蟹批次信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabBatch crabBatch)
    {
        return toAjax(crabBatchService.insertCrabBatch(crabBatch));
    }

    /**
     * 修改螃蟹批次信息
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:edit')")
    @Log(title = "螃蟹批次信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabBatch crabBatch)
    {
        return toAjax(crabBatchService.updateCrabBatch(crabBatch));
    }

    /**
     * 删除螃蟹批次信息
     */
    @PreAuthorize("@ss.hasPermi('creb:batch:remove')")
    @Log(title = "螃蟹批次信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable Long[] batchIds)
    {
        return toAjax(crabBatchService.deleteCrabBatchByBatchIds(batchIds));
    }
}
