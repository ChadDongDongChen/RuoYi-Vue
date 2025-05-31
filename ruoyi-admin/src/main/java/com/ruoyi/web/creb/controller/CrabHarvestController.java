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
import com.ruoyi.web.creb.domain.CrabHarvest;
import com.ruoyi.web.creb.service.ICrabHarvestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收获记录Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/harvest")
public class CrabHarvestController extends BaseController
{
    @Autowired
    private ICrabHarvestService crabHarvestService;

    /**
     * 查询收获记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabHarvest crabHarvest)
    {
        startPage();
        List<CrabHarvest> list = crabHarvestService.selectCrabHarvestList(crabHarvest);
        return getDataTable(list);
    }

    /**
     * 导出收获记录列表
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:export')")
    @Log(title = "收获记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabHarvest crabHarvest)
    {
        List<CrabHarvest> list = crabHarvestService.selectCrabHarvestList(crabHarvest);
        ExcelUtil<CrabHarvest> util = new ExcelUtil<CrabHarvest>(CrabHarvest.class);
        util.exportExcel(response, list, "收获记录数据");
    }

    /**
     * 获取收获记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(crabHarvestService.selectCrabHarvestByRecordId(recordId));
    }

    /**
     * 新增收获记录
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:add')")
    @Log(title = "收获记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabHarvest crabHarvest)
    {
        return toAjax(crabHarvestService.insertCrabHarvest(crabHarvest));
    }

    /**
     * 修改收获记录
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:edit')")
    @Log(title = "收获记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabHarvest crabHarvest)
    {
        return toAjax(crabHarvestService.updateCrabHarvest(crabHarvest));
    }

    /**
     * 删除收获记录
     */
    @PreAuthorize("@ss.hasPermi('creb:harvest:remove')")
    @Log(title = "收获记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(crabHarvestService.deleteCrabHarvestByRecordIds(recordIds));
    }
}
