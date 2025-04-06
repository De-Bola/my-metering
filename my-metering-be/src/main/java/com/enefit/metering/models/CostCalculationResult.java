package com.enefit.metering.models;

import java.util.List;

public class CostCalculationResult {
    private List<MonthlyCostResult> monthlyCosts;

    public CostCalculationResult(List<MonthlyCostResult> monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }

    public List<MonthlyCostResult> getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(List<MonthlyCostResult> monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }
}
