package com.xiaojianma.stockanalysis.model.debit;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 流动负债: 共有25个科目
 */
public class CurrentDebt {

    private Map<String, BigDecimal> map = new ConcurrentHashMap<>();

    private String 流动负债 = "流动负债";

    private String 短期借款 = "短期借款";

    private String 向中央银行借款 = "向中央银行借款";

    private String 拆入资金 = "拆入资金";

    private String 交易性金融负债 = "交易性金融负债";

    private String 衍生金融负债 = "衍生金融负债";

    private String 应付票据 = "应付票据";

    private String 应付账款 = "应付账款";

    private String 预收款项 = "预收款项";

    private String 合同负债 = "合同负债";

    private String 卖出回购金融资产款 = "卖出回购金融资产款";

    private String 吸收存款及同业存放 = "吸收存款及同业存放";

    private String 代理买卖证券款 = "代理买卖证券款";

    private String 代理承销证券款 = "代理承销证券款";

    private String 应付职工薪酬 = "应付职工薪酬";

    private String 应交税费 = "应交税费";

    // 包含：应付利息、应付股利
    private String 其他应付款 = "其他应付款";

    private String 应付利息 = "应付利息";

    private String 应付股利 = "应付股利";

    private String 应付手续费及佣金 = "应付手续费及佣金";

    private String 应付分保账款 = "应付分保账款";

    private String 持有待售负债 = "持有待售负债";

    private String 一年内到期的非流动负债 = "一年内到期的非流动负债";

    private String 其他流动负债 = "其他流动负债";

    private String 流动负债合计 = "流动负债合计";

    public String get流动负债() {
        return 流动负债;
    }

    public void set流动负债(String value) {
        map.put(流动负债, new BigDecimal(value));
    }

    public String get短期借款() {
        return 短期借款;
    }

    public void set短期借款(String value) {
        map.put(短期借款, new BigDecimal(value));
    }

    public String get向中央银行借款() {
        return 向中央银行借款;
    }

    public void set向中央银行借款(String value) {
        map.put(向中央银行借款, new BigDecimal(value));
    }

    public String get拆入资金() {
        return 拆入资金;
    }

    public void set拆入资金(String value) {
        map.put(拆入资金, new BigDecimal(value));
    }

    public String get交易性金融负债() {
        return 交易性金融负债;
    }

    public void set交易性金融负债(String value) {
        map.put(交易性金融负债, new BigDecimal(value));
    }

    public String get衍生金融负债() {
        return 衍生金融负债;
    }

    public void set衍生金融负债(String value) {
        map.put(衍生金融负债, new BigDecimal(value));
    }

    public String get应付票据() {
        return 应付票据;
    }

    public void set应付票据(String value) {
        map.put(应付票据, new BigDecimal(value));
    }

    public String get应付账款() {
        return 应付账款;
    }

    public void set应付账款(String value) {
        map.put(应付账款, new BigDecimal(value));
    }

    public String get预收款项() {
        return 预收款项;
    }

    public void set预收款项(String value) {
        map.put(预收款项, new BigDecimal(value));
    }

    public String get合同负债() {
        return 合同负债;
    }

    public void set合同负债(String value) {
        map.put(合同负债, new BigDecimal(value));
    }

    public String get卖出回购金融资产款() {
        return 卖出回购金融资产款;
    }

    public void set卖出回购金融资产款(String value) {
        map.put(卖出回购金融资产款, new BigDecimal(value));
    }

    public String get吸收存款及同业存放() {
        return 吸收存款及同业存放;
    }

    public void set吸收存款及同业存放(String value) {
        map.put(吸收存款及同业存放, new BigDecimal(value));
    }

    public String get代理买卖证券款() {
        return 代理买卖证券款;
    }

    public void set代理买卖证券款(String value) {
        map.put(代理买卖证券款, new BigDecimal(value));
    }

    public String get代理承销证券款() {
        return 代理承销证券款;
    }

    public void set代理承销证券款(String value) {
        map.put(代理承销证券款, new BigDecimal(value));
    }

    public String get应付职工薪酬() {
        return 应付职工薪酬;
    }

    public void set应付职工薪酬(String value) {
        map.put(应付职工薪酬, new BigDecimal(value));
    }

    public String get应交税费() {
        return 应交税费;
    }

    public void set应交税费(String value) {
        map.put(应交税费, new BigDecimal(value));
    }

    public String get其他应付款() {
        return 其他应付款;
    }

    public void set其他应付款(String value) {
        map.put(其他应付款, new BigDecimal(value));
    }

    public String get应付利息() {
        return 应付利息;
    }

    public void set应付利息(String value) {
        map.put(应付利息, new BigDecimal(value));
    }

    public String get应付股利() {
        return 应付股利;
    }

    public void set应付股利(String value) {
        map.put(应付股利, new BigDecimal(value));
    }

    public String get应付手续费及佣金() {
        return 应付手续费及佣金;
    }

    public void set应付手续费及佣金(String value) {
        map.put(应付手续费及佣金, new BigDecimal(value));
    }

    public String get应付分保账款() {
        return 应付分保账款;
    }

    public void set应付分保账款(String value) {
        map.put(应付分保账款, new BigDecimal(value));
    }

    public String get持有待售负债() {
        return 持有待售负债;
    }

    public void set持有待售负债(String value) {
        map.put(持有待售负债, new BigDecimal(value));
    }

    public String get一年内到期的非流动负债() {
        return 一年内到期的非流动负债;
    }

    public void set一年内到期的非流动负债(String value) {
        map.put(一年内到期的非流动负债, new BigDecimal(value));
    }

    public String get其他流动负债() {
        return 其他流动负债;
    }

    public void set其他流动负债(String value) {
        map.put(其他流动负债, new BigDecimal(value));
    }

    public String get流动负债合计() {
        return 流动负债合计;
    }

    public void set流动负债合计(String value) {
        map.put(流动负债合计, new BigDecimal(value));
    }
}
