package com.ruoyi.web.kf.controller;

import java.util.List;
import java.util.UUID;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.kf.domain.KfOpenApiKey;
import com.ruoyi.web.kf.mapper.KfOpenApiKeyMapper;

@RestController
@RequestMapping("/kf/openkey")
public class KfOpenApiKeyController extends BaseController
{
    @Autowired
    private KfOpenApiKeyMapper kfOpenApiKeyMapper;

    @PreAuthorize("@ss.hasPermi('kf:openkey:list')")
    @GetMapping("/list")
    public TableDataInfo list(KfOpenApiKey query)
    {
        startPage();
        List<KfOpenApiKey> list = kfOpenApiKeyMapper.selectKfOpenApiKeyList(query != null ? query : new KfOpenApiKey());
        for (KfOpenApiKey k : list)
        {
            k.setApiSecret(maskSecret(k.getApiSecret()));
        }
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('kf:openkey:query')")
    @GetMapping("/{keyId}")
    public AjaxResult getInfo(@PathVariable Long keyId)
    {
        KfOpenApiKey k = kfOpenApiKeyMapper.selectKfOpenApiKeyByKeyId(keyId);
        if (k != null)
        {
            k.setApiSecret(maskSecret(k.getApiSecret()));
        }
        return success(k);
    }

    @PreAuthorize("@ss.hasPermi('kf:openkey:add')")
    @Log(title = "开放API密钥", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KfOpenApiKey row)
    {
        String secret = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
        if (secret.length() > 64)
        {
            secret = secret.substring(0, 64);
        }
        String keyLabel = "kf_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        row.setApiKey(keyLabel);
        row.setApiSecret(secret);
        if (row.getEnabled() == null)
        {
            row.setEnabled("0");
        }
        row.setCreateBy(getUsername());
        row.setCreateTime(DateUtils.getNowDate());
        kfOpenApiKeyMapper.insertKfOpenApiKey(row);
        KfOpenApiKey safe = new KfOpenApiKey();
        safe.setKeyId(row.getKeyId());
        safe.setApiKey(row.getApiKey());
        safe.setName(row.getName());
        AjaxResult ok = success(safe);
        ok.put("plainSecret", secret);
        ok.put("hint", "plainSecret 仅本次返回；开放接口请求头 Authorization: Bearer <plainSecret>");
        return ok;
    }

    @PreAuthorize("@ss.hasPermi('kf:openkey:edit')")
    @Log(title = "开放API密钥", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KfOpenApiKey row)
    {
        row.setUpdateBy(getUsername());
        row.setUpdateTime(DateUtils.getNowDate());
        row.setApiSecret(null);
        return toAjax(kfOpenApiKeyMapper.updateKfOpenApiKey(row));
    }

    @PreAuthorize("@ss.hasPermi('kf:openkey:remove')")
    @Log(title = "开放API密钥", businessType = BusinessType.DELETE)
    @DeleteMapping("/{keyId}")
    public AjaxResult remove(@PathVariable Long keyId)
    {
        return toAjax(kfOpenApiKeyMapper.deleteKfOpenApiKeyByKeyId(keyId));
    }

    private static String maskSecret(String s)
    {
        if (s == null || s.length() < 8)
        {
            return "****";
        }
        return "****" + s.substring(s.length() - 4);
    }
}
