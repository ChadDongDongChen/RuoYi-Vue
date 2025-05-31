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
import com.ruoyi.web.creb.domain.CrabDevice;
import com.ruoyi.web.creb.service.ICrabDeviceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境监测设备Controller
 * 
 * @author chendong
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/creb/device")
public class CrabDeviceController extends BaseController
{
    @Autowired
    private ICrabDeviceService crabDeviceService;

    /**
     * 查询环境监测设备列表
     */
    @PreAuthorize("@ss.hasPermi('creb:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrabDevice crabDevice)
    {
        startPage();
        List<CrabDevice> list = crabDeviceService.selectCrabDeviceList(crabDevice);
        return getDataTable(list);
    }

    /**
     * 导出环境监测设备列表
     */
    @PreAuthorize("@ss.hasPermi('creb:device:export')")
    @Log(title = "环境监测设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrabDevice crabDevice)
    {
        List<CrabDevice> list = crabDeviceService.selectCrabDeviceList(crabDevice);
        ExcelUtil<CrabDevice> util = new ExcelUtil<CrabDevice>(CrabDevice.class);
        util.exportExcel(response, list, "环境监测设备数据");
    }

    /**
     * 获取环境监测设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('creb:device:query')")
    @GetMapping(value = "/{deviceId}")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId)
    {
        return success(crabDeviceService.selectCrabDeviceByDeviceId(deviceId));
    }

    /**
     * 新增环境监测设备
     */
    @PreAuthorize("@ss.hasPermi('creb:device:add')")
    @Log(title = "环境监测设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrabDevice crabDevice)
    {
        return toAjax(crabDeviceService.insertCrabDevice(crabDevice));
    }

    /**
     * 修改环境监测设备
     */
    @PreAuthorize("@ss.hasPermi('creb:device:edit')")
    @Log(title = "环境监测设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrabDevice crabDevice)
    {
        return toAjax(crabDeviceService.updateCrabDevice(crabDevice));
    }

    /**
     * 删除环境监测设备
     */
    @PreAuthorize("@ss.hasPermi('creb:device:remove')")
    @Log(title = "环境监测设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable Long[] deviceIds)
    {
        return toAjax(crabDeviceService.deleteCrabDeviceByDeviceIds(deviceIds));
    }
}
