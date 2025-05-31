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
import com.ruoyi.web.creb.domain.CrabPool;
import com.ruoyi.web.creb.service.ICrabPoolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 螃蟹养殖池信息Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/pool")
public class CrabPoolController extends BaseController
{
    @Autowired
    private ICrabPoolService crabPoolService;

    /**
     * 查询螃蟹养殖池信息列表
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabPool crabPool)
    {
        startPage();
        List<CrabPool> list = crabPoolService.selectCrabPoolList(crabPool);
        return getDataTable(list);
    }

    /**
     * 导出螃蟹养殖池信息列表
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:export')")
    @Log(title = "螃蟹养殖池信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabPool crabPool)
    {
        List<CrabPool> list = crabPoolService.selectCrabPoolList(crabPool);
        ExcelUtil<CrabPool> util = new ExcelUtil<CrabPool>(CrabPool.class);
        util.exportExcel(response, list, "螃蟹养殖池信息数据");
    }

    /**
     * 获取螃蟹养殖池信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:query')")
    @GetMapping(value = "/{poolId}")
    public AjaxResult getInfo(@PathVariable("poolId") Long poolId)
    {
        return success(crabPoolService.selectCrabPoolByPoolId(poolId));
    }

    /**
     * 新增螃蟹养殖池信息
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:add')")
    @Log(title = "螃蟹养殖池信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabPool crabPool)
    {
        return toAjax(crabPoolService.insertCrabPool(crabPool));
    }

    /**
     * 修改螃蟹养殖池信息
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:edit')")
    @Log(title = "螃蟹养殖池信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabPool crabPool)
    {
        return toAjax(crabPoolService.updateCrabPool(crabPool));
    }

    /**
     * 删除螃蟹养殖池信息
     */
    @PreAuthorize("@ss.hasPermi('creb:pool:remove')")
    @Log(title = "螃蟹养殖池信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{poolIds}")
    public AjaxResult remove(@PathVariable Long[] poolIds)
    {
        return toAjax(crabPoolService.deleteCrabPoolByPoolIds(poolIds));
    }
}
