package com.xiaojianma.stockanalysis.model;

import com.xiaojianma.stockanalysis.model.debit.CurrentAsset;
import com.xiaojianma.stockanalysis.model.debit.CurrentDebt;
import com.xiaojianma.stockanalysis.model.debit.FixedAsset;
import com.xiaojianma.stockanalysis.model.debit.FixedDebt;
import com.xiaojianma.stockanalysis.model.debit.OwnersEquity;
import com.xiaojianma.stockanalysis.model.enums.Type;

import java.math.BigDecimal;

/**
 * 资产负债表
 */
public class AssetDebt {

    // 类型，母公司或合并
    private Type type;

    // 年份
    private String year;

    // 流动资产
    private CurrentAsset 流动资产;

    // 非流动资产
    private FixedAsset 非流动资产;

    // 资产总计
    private BigDecimal 资产总计;

    // 流动负债
    private CurrentDebt 流动负债;

    // 非流动负债
    private FixedDebt 非流动负债;

    // 负债合计
    private BigDecimal 负债合计;

    // 所有者权益
    private OwnersEquity 所有者权益;

    // 所有者权益合计
    private BigDecimal 所有者权益合计;

    // 负债和所有者权益总计
    private BigDecimal 负债和所有者权益总计;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public CurrentAsset get流动资产() {
        return 流动资产;
    }

    public void set流动资产(CurrentAsset 流动资产) {
        this.流动资产 = 流动资产;
    }

    public FixedAsset get非流动资产() {
        return 非流动资产;
    }

    public void set非流动资产(FixedAsset 非流动资产) {
        this.非流动资产 = 非流动资产;
    }

    public BigDecimal get资产总计() {
        return 资产总计;
    }

    public void set资产总计(BigDecimal 资产总计) {
        this.资产总计 = 资产总计;
    }

    public CurrentDebt get流动负债() {
        return 流动负债;
    }

    public void set流动负债(CurrentDebt 流动负债) {
        this.流动负债 = 流动负债;
    }

    public FixedDebt get非流动负债() {
        return 非流动负债;
    }

    public void set非流动负债(FixedDebt 非流动负债) {
        this.非流动负债 = 非流动负债;
    }

    public BigDecimal get负债合计() {
        return 负债合计;
    }

    public void set负债合计(BigDecimal 负债合计) {
        this.负债合计 = 负债合计;
    }

    public OwnersEquity get所有者权益() {
        return 所有者权益;
    }

    public void set所有者权益(OwnersEquity 所有者权益) {
        this.所有者权益 = 所有者权益;
    }

    public BigDecimal get所有者权益合计() {
        return 所有者权益合计;
    }

    public void set所有者权益合计(BigDecimal 所有者权益合计) {
        this.所有者权益合计 = 所有者权益合计;
    }

    public BigDecimal get负债和所有者权益总计() {
        return 负债和所有者权益总计;
    }

    public void set负债和所有者权益总计(BigDecimal 负债和所有者权益总计) {
        this.负债和所有者权益总计 = 负债和所有者权益总计;
    }
}
