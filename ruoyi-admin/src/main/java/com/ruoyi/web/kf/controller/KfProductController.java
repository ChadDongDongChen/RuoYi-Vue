package com.ruoyi.web.kf.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.kf.domain.KfProduct;
import com.ruoyi.web.kf.service.IKfProductService;

@RestController
@RequestMapping("/kf/product")
public class KfProductController extends BaseController
{
    @Autowired
    private IKfProductService kfProductService;

    @PreAuthorize("@ss.hasPermi('kf:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(KfProduct kfProduct)
    {
        startPage();
        List<KfProduct> list = kfProductService.selectKfProductList(kfProduct);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('kf:product:export')")
    @Log(title = "客服商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KfProduct kfProduct)
    {
        List<KfProduct> list = kfProductService.selectKfProductList(kfProduct);
        ExcelUtil<KfProduct> util = new ExcelUtil<>(KfProduct.class);
        util.exportExcel(response, list, "客服商品");
    }

    @PreAuthorize("@ss.hasPermi('kf:product:query')")
    @GetMapping("/{productId}")
    public AjaxResult getInfo(@PathVariable Long productId)
    {
        return success(kfProductService.selectKfProductByProductId(productId));
    }

    @PreAuthorize("@ss.hasPermi('kf:product:add')")
    @Log(title = "客服商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KfProduct kfProduct)
    {
        kfProduct.setCreateBy(getUsername());
        kfProduct.setCreateTime(DateUtils.getNowDate());
        return toAjax(kfProductService.insertKfProduct(kfProduct));
    }

    @PreAuthorize("@ss.hasPermi('kf:product:edit')")
    @Log(title = "客服商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KfProduct kfProduct)
    {
        kfProduct.setUpdateBy(getUsername());
        kfProduct.setUpdateTime(DateUtils.getNowDate());
        return toAjax(kfProductService.updateKfProduct(kfProduct));
    }

    @PreAuthorize("@ss.hasPermi('kf:product:remove')")
    @Log(title = "客服商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(kfProductService.deleteKfProductByProductIds(productIds));
    }
}
