package com.enefit.metering.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public class MarketPriceData {
    private List<MarketPriceDataPoint> data;

    public MarketPriceData() {
    }

    public MarketPriceData(List<MarketPriceDataPoint> data) {
        this.data = data;
    }

    public List<MarketPriceDataPoint> getData() {
        return data;
    }

    public void setData(List<MarketPriceDataPoint> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarketPriceData{" +
                "data=" + data +
                '}';
    }
}
