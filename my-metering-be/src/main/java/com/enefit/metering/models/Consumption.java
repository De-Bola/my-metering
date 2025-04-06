package com.enefit.metering.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity representing consumption data for a metering point.
 * Each metering point can have many consumption records.
 * This entity is cacheable.
 */
@Entity
@Table(name = "consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumption_id", columnDefinition = "bigint", updatable = false, nullable = false)
    private Long consumptionId;

    /**
     * Many consumption records can belong to one metering point.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metering_point_id", nullable = false)
    private MeteringPoint meteringPoint;

    /**
     * The amount consumed.
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * The unit for the consumption amount (e.g., kWh).
     */
    @Column(name = "amount_unit")
    private String amountUnit;

    /**
     * The timestamp when the consumption was recorded.
     */
    @Column(name = "consumption_time")
    private LocalDateTime consumptionTime;

    /**
     * Default constructor for JPA.
     */
    public Consumption() {
    }

    /**
     * Constructs a Consumption record with the specified details.
     *
     * @param meteringPoint   the associated metering point.
     * @param amount          the consumption amount.
     * @param amountUnit      the unit for the consumption.
     * @param consumptionTime the timestamp when the consumption occurred.
     */
    public Consumption(MeteringPoint meteringPoint, Double amount, String amountUnit, LocalDateTime consumptionTime) {
        this.meteringPoint = meteringPoint;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.consumptionTime = consumptionTime;
    }

    public Long getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Long consumptionId) {
        this.consumptionId = consumptionId;
    }

    public MeteringPoint getMeteringPoint() {
        return meteringPoint;
    }

    public void setMeteringPoint(MeteringPoint meteringPoint) {
        this.meteringPoint = meteringPoint;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(String amountUnit) {
        this.amountUnit = amountUnit;
    }

    public LocalDateTime getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(LocalDateTime consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "consumptionId=" + consumptionId +
                ", meteringPointId=" + (meteringPoint != null ? meteringPoint.getMeteringPointId() : null) +
                ", amount=" + amount +
                ", amountUnit='" + amountUnit + '\'' +
                ", consumptionTime=" + consumptionTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consumption that)) return false;
        return Objects.equals(consumptionId, that.consumptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumptionId);
    }
}
