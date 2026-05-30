package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI证件照规格表 ai_idphoto_spec
 *
 * @author ruoyi
 */
public class AiIdphotoSpec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规格ID */
    private Long specId;

    /** 规格名称 */
    @Excel(name = "规格名称")
    private String specName;

    /** 宽度(像素) */
    @Excel(name = "宽度(像素)")
    private Integer widthPx;

    /** 高度(像素) */
    @Excel(name = "高度(像素)")
    private Integer heightPx;

    /** 宽度(毫米) */
    @Excel(name = "宽度(毫米)")
    private BigDecimal widthMm;

    /** 高度(毫米) */
    @Excel(name = "高度(毫米)")
    private BigDecimal heightMm;

    /** 单价(元) */
    @Excel(name = "单价(元)")
    private BigDecimal price;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer sortOrder;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态")
    private String status;

    public Long getSpecId()
    {
        return specId;
    }

    public void setSpecId(Long specId)
    {
        this.specId = specId;
    }

    public String getSpecName()
    {
        return specName;
    }

    public void setSpecName(String specName)
    {
        this.specName = specName;
    }

    public Integer getWidthPx()
    {
        return widthPx;
    }

    public void setWidthPx(Integer widthPx)
    {
        this.widthPx = widthPx;
    }

    public Integer getHeightPx()
    {
        return heightPx;
    }

    public void setHeightPx(Integer heightPx)
    {
        this.heightPx = heightPx;
    }

    public BigDecimal getWidthMm()
    {
        return widthMm;
    }

    public void setWidthMm(BigDecimal widthMm)
    {
        this.widthMm = widthMm;
    }

    public BigDecimal getHeightMm()
    {
        return heightMm;
    }

    public void setHeightMm(BigDecimal heightMm)
    {
        this.heightMm = heightMm;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("specId", getSpecId())
            .append("specName", getSpecName())
            .append("widthPx", getWidthPx())
            .append("heightPx", getHeightPx())
            .append("widthMm", getWidthMm())
            .append("heightMm", getHeightMm())
            .append("price", getPrice())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
