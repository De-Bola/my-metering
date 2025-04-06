package com.enefit.metering.models;

import com.fasterxml.jackson.annotation.*;

import java.time.OffsetDateTime;


/**
 * Represents a single market price data point.
 */
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class"
// )
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPriceDataPoint {

    private final double centsPerKwh;
    private final double centsPerKwhWithVat;
    private final double eurPerMwh;
    private final double eurPerMwhWithVat;
    private final OffsetDateTime fromDateTime;
    private final OffsetDateTime toDateTime;

    @JsonCreator
    public MarketPriceDataPoint(
            @JsonProperty("centsPerKwh") double centsPerKwh,
            @JsonProperty("centsPerKwhWithVat") double centsPerKwhWithVat,
            @JsonProperty("eurPerMwh") double eurPerMwh,
            @JsonProperty("eurPerMwhWithVat") double eurPerMwhWithVat,
            @JsonProperty("fromDateTime") OffsetDateTime fromDateTime,
            @JsonProperty("toDateTime") OffsetDateTime toDateTime) {
        this.centsPerKwh = centsPerKwh;
        this.centsPerKwhWithVat = centsPerKwhWithVat;
        this.eurPerMwh = eurPerMwh;
        this.eurPerMwhWithVat = eurPerMwhWithVat;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public double getCentsPerKwh() {
        return centsPerKwh;
    }


    public double getCentsPerKwhWithVat() {
        return centsPerKwhWithVat;
    }

    public double getEurPerMwh() {
        return eurPerMwh;
    }


    public double getEurPerMwhWithVat() {
        return eurPerMwhWithVat;
    }



    public OffsetDateTime getFromDateTime() {
        return fromDateTime;
    }



    public OffsetDateTime getToDateTime() {
        return toDateTime;
    }



    @Override
    public String toString() {
        return "MarketPriceDataPoint{" +
                "centsPerKwh=" + centsPerKwh +
                ", centsPerKwhWithVat=" + centsPerKwhWithVat +
                ", eurPerMwh=" + eurPerMwh +
                ", eurPerMwhWithVat=" + eurPerMwhWithVat +
                ", fromDateTime=" + fromDateTime +
                ", toDateTime=" + toDateTime +
                '}';
    }
}
