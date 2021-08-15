package com.xiaojianma.stockanalysis.model.debit;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非流动负债：共有22个科目
 */
public class FixedDebt {
    private Map<String, BigDecimal> map = new ConcurrentHashMap<>();

    private String 保险合同准备金 = "保险合同准备金";

    private String 长期借款 = "长期借款";

    // 包含：优先股、永续债
    private String 应付债券 = "应付债券";

    private String 优先股 = "优先股";

    private String 永续债 = "永续债";

    private String 租赁负债 = "租赁负债";

    private String 长期应付款 = "长期应付款";

    private String 长期应付职工薪酬 = "长期应付职工薪酬";

    private String 预计负债 = "预计负债";

    private String 递延收益 = "递延收益";

    private String 递延所得税负债 = "递延所得税负债";

    private String 其他非流动负债 = "其他非流动负债";

    private String 非流动负债合计 = "非流动负债合计";

    public String get保险合同准备金() {
        return 保险合同准备金;
    }

    public void set保险合同准备金(String value) {
        map.put(保险合同准备金, new BigDecimal(value));
    }

    public String get长期借款() {
        return 长期借款;
    }

    public void set长期借款(String value) {
        map.put(长期借款, new BigDecimal(value));
    }

    public String get应付债券() {
        return 应付债券;
    }

    public void set应付债券(String value) {
        map.put(应付债券, new BigDecimal(value));
    }

    public String get优先股() {
        return 优先股;
    }

    public void set优先股(String value) {
        map.put(优先股, new BigDecimal(value));
    }

    public String get永续债() {
        return 永续债;
    }

    public void set永续债(String value) {
        map.put(永续债, new BigDecimal(value));
    }

    public String get租赁负债() {
        return 租赁负债;
    }

    public void set租赁负债(String value) {
        map.put(租赁负债, new BigDecimal(value));
    }

    public String get长期应付款() {
        return 长期应付款;
    }

    public void set长期应付款(String value) {
        map.put(长期应付款, new BigDecimal(value));
    }

    public String get长期应付职工薪酬() {
        return 长期应付职工薪酬;
    }

    public void set长期应付职工薪酬(String value) {
        map.put(长期应付职工薪酬, new BigDecimal(value));
    }

    public String get预计负债() {
        return 预计负债;
    }

    public void set预计负债(String value) {
        map.put(预计负债, new BigDecimal(value));
    }

    public String get递延收益() {
        return 递延收益;
    }

    public void set递延收益(String value) {
        map.put(递延收益, new BigDecimal(value));
    }

    public String get递延所得税负债() {
        return 递延所得税负债;
    }

    public void set递延所得税负债(String value) {
        map.put(递延所得税负债, new BigDecimal(value));
    }

    public String get其他非流动负债() {
        return 其他非流动负债;
    }

    public void set其他非流动负债(String value) {
        map.put(其他非流动负债, new BigDecimal(value));
    }

    public String get非流动负债合计() {
        return 非流动负债合计;
    }

    public void set非流动负债合计(String value) {
        map.put(非流动负债合计, new BigDecimal(value));
    }
}
