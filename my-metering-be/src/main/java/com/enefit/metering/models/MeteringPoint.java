package com.enefit.metering.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Entity representing a metering point.
 * Each metering point is associated with one customer.
 */
@Entity
@Table(name = "metering_points")
public class MeteringPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metering_point_id", columnDefinition = "bigint", updatable = false, nullable = false)
    private Long meteringPointId;

    /**
     * Many metering points can belong to one customer.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    /**
     * The address associated with the metering point.
     */
    @Column(name = "address")
    private String address;

    /**
     * Default constructor for JPA.
     */
    public MeteringPoint() {
    }

    public MeteringPoint(Long meteringPointId, Customer customer, String address) {
        this.meteringPointId = meteringPointId;
        this.customer = customer;
        this.address = address;
    }

    public Long getMeteringPointId() {
        return meteringPointId;
    }
    public void setMeteringPointId(Long meteringPointId) {
        this.meteringPointId = meteringPointId;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MeteringPoint{" +
                "meteringPointId=" + meteringPointId +
                ", customerId=" + (customer != null ? customer.getCustomerId() : null) +
                ", address='" + address + '\'' +
                '}';
    }
}
