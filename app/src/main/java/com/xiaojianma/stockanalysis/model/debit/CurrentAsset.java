package com.xiaojianma.stockanalysis.model.debit;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 流动资产: 共有22个科目
 */
public class CurrentAsset {

    private Map<String, BigDecimal> map = new ConcurrentHashMap<>();

    private String 货币资金 = "货币资金";

    private String 结算备付金 = "结算备付金";

    private String 拆出资金 = "拆出资金";

    private String 交易性金融资产 = "交易性金融资产";

    private String 衍生金融资产 = "衍生金融资产";

    private String 应收票据 = "应收票据";

    private String 应收账款 = "应收账款";

    private String 应收款项融资 = "应收款项融资";

    private String 预付款项 = "预付款项";

    private String 应收保费 = "应收保费";

    private String 应收分保账款 = "应收分保账款";

    private String 应收分保合同准备金 = "应收分保合同准备金";

    private String 其他应收款 = "其他应收款";

    private String 应收利息 = "应收利息";

    private String 应收股利 = "应收股利";

    private String 买入返售金融资产 = "买入返售金融资产";

    private String 存货 = "存货";

    private String 合同资产 = "合同资产";

    private String 持有待售资产 = "持有待售资产";

    private String 一年内到期的非流动资产 = "一年内到期的非流动资产";

    private String 其他流动资产 = "其他流动资产";

    private String 流动资产合计 = "流动资产合计";

    public String get货币资金() {
        return 货币资金;
    }

    public void set货币资金(String value) {
        map.put(货币资金, new BigDecimal(value));
    }

    public String get结算备付金() {
        return 结算备付金;
    }

    public void set结算备付金(String value) {
        map.put(结算备付金, new BigDecimal(value));
    }

    public String get拆出资金() {
        return 拆出资金;
    }

    public void set拆出资金(String value) {
        map.put(拆出资金, new BigDecimal(value));
    }

    public String get交易性金融资产() {
        return 交易性金融资产;
    }

    public void set交易性金融资产(String value) {
        map.put(交易性金融资产, new BigDecimal(value));
    }

    public String get衍生金融资产() {
        return 衍生金融资产;
    }

    public void set衍生金融资产(String value) {
        map.put(衍生金融资产, new BigDecimal(value));
    }

    public String get应收票据() {
        return 应收票据;
    }

    public void set应收票据(String value) {
        map.put(应收票据, new BigDecimal(value));
    }

    public String get应收账款() {
        return 应收账款;
    }

    public void set应收账款(String value) {
        map.put(应收账款, new BigDecimal(value));
    }

    public String get应收款项融资() {
        return 应收款项融资;
    }

    public void set应收款项融资(String value) {
        map.put(应收款项融资, new BigDecimal(value));
    }

    public String get预付款项() {
        return 预付款项;
    }

    public void set预付款项(String value) {
        map.put(预付款项, new BigDecimal(value));
    }

    public String get应收保费() {
        return 应收保费;
    }

    public void set应收保费(String value) {
        map.put(应收保费, new BigDecimal(value));
    }

    public String get应收分保账款() {
        return 应收分保账款;
    }

    public void set应收分保账款(String value) {
        map.put(应收分保账款, new BigDecimal(value));
    }

    public String get应收分保合同准备金() {
        return 应收分保合同准备金;
    }

    public void set应收分保合同准备金(String value) {
        map.put(应收分保合同准备金, new BigDecimal(value));
    }

    public String get其他应收款() {
        return 其他应收款;
    }

    public void set其他应收款(String value) {
        map.put(其他应收款, new BigDecimal(value));
    }

    public String get应收利息() {
        return 应收利息;
    }

    public void set应收利息(String value) {
        map.put(应收利息, new BigDecimal(value));
    }

    public String get应收股利() {
        return 应收股利;
    }

    public void set应收股利(String value) {
        map.put(应收股利, new BigDecimal(value));
    }

    public String get买入返售金融资产() {
        return 买入返售金融资产;
    }

    public void set买入返售金融资产(String value) {
        map.put(买入返售金融资产, new BigDecimal(value));
    }

    public String get存货() {
        return 存货;
    }

    public void set存货(String value) {
        map.put(存货, new BigDecimal(value));
    }

    public String get合同资产() {
        return 合同资产;
    }

    public void set合同资产(String value) {
        map.put(合同资产, new BigDecimal(value));
    }

    public String get持有待售资产() {
        return 持有待售资产;
    }

    public void set持有待售资产(String value) {
        map.put(持有待售资产, new BigDecimal(value));
    }

    public String get一年内到期的非流动资产() {
        return 一年内到期的非流动资产;
    }

    public void set一年内到期的非流动资产(String value) {
        map.put(一年内到期的非流动资产, new BigDecimal(value));
    }

    public String get其他流动资产() {
        return 其他流动资产;
    }

    public void set其他流动资产(String value) {
        map.put(其他流动资产, new BigDecimal(value));
    }

    public String get流动资产合计() {
        return 流动资产合计;
    }

    public void set流动资产合计(String value) {
        map.put(流动资产合计, new BigDecimal(value));
    }
}
