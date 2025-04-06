package com.enefit.metering.models;

import java.math.BigDecimal;

public class MonthlyCostResult {
    private String month;
    private BigDecimal totalConsumption;
    private double marketPrice;
    private double monthlyCost;

    public MonthlyCostResult(String month, BigDecimal totalConsumption, double marketPrice, double monthlyCost) {
        this.month = month;
        this.totalConsumption = totalConsumption;
        this.marketPrice = marketPrice;
        this.monthlyCost = monthlyCost;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(BigDecimal totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }
}
