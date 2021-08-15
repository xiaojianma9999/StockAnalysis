package com.xiaojianma.stockanalysis.model.debit;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非流动资产: 共20个科目
 */
public class FixedAsset {

    private Map<String, BigDecimal> map = new ConcurrentHashMap<>();

    // 发放贷款和垫款
    private String 发放贷款和垫款 = "发放贷款和垫款";

    // 债权投资
    private String 债权投资 = "债权投资";

    // 其他债权投资
    private String 其他债权投资 = "其他债权投资";

    // 长期应收款
    private String 长期应收款 = "长期应收款";

    // 长期股权投资
    private String 长期股权投资 = "长期股权投资";

    // 其他权益工具投资
    private String 其他权益工具投资 = "其他权益工具投资";

    // 其他非流动金融资产
    private String 其他非流动金融资产 = "其他非流动金融资产";

    // 投资性房地产
    private String 投资性房地产 = "投资性房地产";

    // 固定资产
    private String 固定资产 = "固定资产";

    // 在建工程
    private String 在建工程 = "在建工程";

    // 生产性生物资产
    private String 生产性生物资产 = "生产性生物资产";

    // 油气资产
    private String 油气资产 = "油气资产";

    // 使用权资产
    private String 使用权资产 = "使用权资产";

    // 无形资产
    private String 无形资产 = "无形资产";

    // 开发支出
    private String 开发支出 = "开发支出";

    // 商誉
    private String 商誉 = "商誉";

    // 长期待摊费用
    private String 长期待摊费用 = "长期待摊费用";

    // 递延所得税资产
    private String 递延所得税资产 = "递延所得税资产";

    // 其他非流动资产
    private String 其他非流动资产 = "其他非流动资产";

    // 非流动资产合计
    private String 非流动资产合计 = "非流动资产合计";

    public BigDecimal get发放贷款和垫款() {
        return map.get(发放贷款和垫款);
    }

    public void set发放贷款和垫款(String value) {
        map.put(发放贷款和垫款, new BigDecimal(value));

    }

    public BigDecimal get债权投资() {
        return map.get(债权投资);
    }

    public void set债权投资(String 债权投资) {
        this.债权投资 = 债权投资;
    }

    public BigDecimal get其他债权投资() {
        return map.get(其他债权投资);
    }

    public void set其他债权投资(String value) {
        map.put(其他债权投资, new BigDecimal(value));
    }

    public BigDecimal get长期应收款() {
        return map.get(长期应收款);
    }

    public void set长期应收款(String value) {
        map.put(长期应收款, new BigDecimal(value));
    }

    public BigDecimal get长期股权投资() {
        return map.get(长期股权投资);
    }

    public void set长期股权投资(String value) {
        map.put(长期股权投资, new BigDecimal(value));
    }

    public BigDecimal get其他权益工具投资() {
        return map.get(其他权益工具投资);
    }

    public void set其他权益工具投资(String value) {
        map.put(其他权益工具投资, new BigDecimal(value));
    }

    public BigDecimal get其他非流动金融资产() {
        return map.get(其他非流动金融资产);
    }

    public void set其他非流动金融资产(String value) {
        map.put(其他非流动金融资产, new BigDecimal(value));
    }

    public BigDecimal get投资性房地产() {
        return map.get(投资性房地产);
    }

    public void set投资性房地产(String value) {
        map.put(投资性房地产, new BigDecimal(value));
    }

    public BigDecimal get固定资产() {
        return map.get(固定资产);
    }

    public void set固定资产(String value) {
        map.put(固定资产, new BigDecimal(value));
    }

    public BigDecimal get在建工程() {
        return map.get(在建工程);
    }

    public void set在建工程(String value) {
        map.put(在建工程, new BigDecimal(value));
    }

    public BigDecimal get生产性生物资产() {
        return map.get(生产性生物资产);
    }

    public void set生产性生物资产(String value) {
        map.put(生产性生物资产, new BigDecimal(value));
    }

    public BigDecimal get油气资产() {
        return map.get(油气资产);
    }

    public void set油气资产(String value) {
        map.put(油气资产, new BigDecimal(value));
    }

    public BigDecimal get使用权资产() {
        return map.get(使用权资产);
    }

    public void set使用权资产(String value) {
        map.put(使用权资产, new BigDecimal(value));
    }

    public BigDecimal get无形资产() {
        return map.get(无形资产);
    }

    public void set无形资产(String value) {
        map.put(无形资产, new BigDecimal(value));
    }

    public BigDecimal get开发支出() {
        return map.get(开发支出);
    }

    public void set开发支出(String value) {
        map.put(开发支出, new BigDecimal(value));
    }

    public BigDecimal get商誉() {
        return map.get(商誉);
    }

    public void set商誉(String value) {
        map.put(商誉, new BigDecimal(value));
    }

    public BigDecimal get长期待摊费用() {
        return map.get(长期待摊费用);
    }

    public void set长期待摊费用(String value) {
        map.put(长期待摊费用, new BigDecimal(value));
    }

    public BigDecimal get递延所得税资产() {
        return map.get(递延所得税资产);
    }

    public void set递延所得税资产(String value) {
        map.put(递延所得税资产, new BigDecimal(value));
    }

    public BigDecimal get其他非流动资产() {
        return map.get(其他非流动资产);
    }

    public void set其他非流动资产(String value) {
        map.put(其他非流动资产, new BigDecimal(value));
    }

    public BigDecimal get非流动资产合计() {
        return map.get(非流动资产合计);
    }

    public void set非流动资产合计(String value) {
        map.put(非流动资产合计, new BigDecimal(value));
    }
}
