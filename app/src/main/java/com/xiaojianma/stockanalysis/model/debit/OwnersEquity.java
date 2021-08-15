package com.xiaojianma.stockanalysis.model.debit;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 所有者权益：共有14个科目
 */
public class OwnersEquity {

    private Map<String, BigDecimal> map = new ConcurrentHashMap<>();

    private String 股本 = "股本";

    // 包含：优先股、永续债
    private String 其他权益工具 = "其他权益工具";

    private String 优先股 = "优先股";

    private String 永续债 = "永续债";

    private String 资本公积 = "资本公积";

    private String 减库存股 = "减库存股";

    private String 其他综合收益 = "其他综合收益";

    private String 专项储备 = "专项储备";

    private String 盈余公积 = "盈余公积";

    private String 一般风险准备 = "一般风险准备";

    private String 未分配利润 = "未分配利润";

    private String 归属于母公司所有者权益合计 = "归属于母公司所有者权益合计";

    private String 少数股东权益 = "少数股东权益";

    private String 所有者权益合计 = "所有者权益合计";

    public String get股本() {
        return 股本;
    }

    public void set股本(String value) {
        map.put(股本, new BigDecimal(value));
    }

    public String get其他权益工具() {
        return 其他权益工具;
    }

    public void set其他权益工具(String value) {
        map.put(其他权益工具, new BigDecimal(value));
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

    public String get资本公积() {
        return 资本公积;
    }

    public void set资本公积(String value) {
        map.put(资本公积, new BigDecimal(value));
    }

    public String get减库存股() {
        return 减库存股;
    }

    public void set减库存股(String value) {
        map.put(减库存股, new BigDecimal(value));
    }

    public String get其他综合收益() {
        return 其他综合收益;
    }

    public void set其他综合收益(String value) {
        map.put(其他综合收益, new BigDecimal(value));
    }

    public String get专项储备() {
        return 专项储备;
    }

    public void set专项储备(String value) {
        map.put(专项储备, new BigDecimal(value));
    }

    public String get盈余公积() {
        return 盈余公积;
    }

    public void set盈余公积(String value) {
        map.put(盈余公积, new BigDecimal(value));
    }

    public String get一般风险准备() {
        return 一般风险准备;
    }

    public void set一般风险准备(String value) {
        map.put(一般风险准备, new BigDecimal(value));
    }

    public String get未分配利润() {
        return 未分配利润;
    }

    public void set未分配利润(String value) {
        map.put(未分配利润, new BigDecimal(value));
    }

    public String get归属于母公司所有者权益合计() {
        return 归属于母公司所有者权益合计;
    }

    public void set归属于母公司所有者权益合计(String value) {
        map.put(归属于母公司所有者权益合计, new BigDecimal(value));
    }

    public String get少数股东权益() {
        return 少数股东权益;
    }

    public void set少数股东权益(String value) {
        map.put(少数股东权益, new BigDecimal(value));
    }

    public String get所有者权益合计() {
        return 所有者权益合计;
    }

    public void set所有者权益合计(String value) {
        map.put(所有者权益合计, new BigDecimal(value));
    }
}
