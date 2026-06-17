package com.ruoyi.web.photo.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class PhotoSpec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "规格名称")
    private String specName;

    @Excel(name = "宽度像素")
    private Integer widthPx;

    @Excel(name = "高度像素")
    private Integer heightPx;

    @Excel(name = "宽度毫米")
    private BigDecimal widthMm;

    @Excel(name = "高度毫米")
    private BigDecimal heightMm;

    @Excel(name = "价格")
    private BigDecimal price;

    private Integer sortOrder;

    private Integer enabled;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setSpecName(String specName) { this.specName = specName; }
    public String getSpecName() { return specName; }

    public void setWidthPx(Integer widthPx) { this.widthPx = widthPx; }
    public Integer getWidthPx() { return widthPx; }

    public void setHeightPx(Integer heightPx) { this.heightPx = heightPx; }
    public Integer getHeightPx() { return heightPx; }

    public void setWidthMm(BigDecimal widthMm) { this.widthMm = widthMm; }
    public BigDecimal getWidthMm() { return widthMm; }

    public void setHeightMm(BigDecimal heightMm) { this.heightMm = heightMm; }
    public BigDecimal getHeightMm() { return heightMm; }

    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getPrice() { return price; }

    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Integer getSortOrder() { return sortOrder; }

    public void setEnabled(Integer enabled) { this.enabled = enabled; }
    public Integer getEnabled() { return enabled; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("specName", getSpecName())
            .append("widthPx", getWidthPx())
            .append("heightPx", getHeightPx())
            .append("price", getPrice())
            .append("sortOrder", getSortOrder())
            .append("enabled", getEnabled())
            .toString();
    }
}
