package com.enefit.metering.models;

/**
 * A simple DTO for returning metering point id and address.
 */
public class MeteringPointDto {

    private Long meteringPointId;
    private String address;

    public MeteringPointDto(Long meteringPointId, String address) {
        this.meteringPointId = meteringPointId;
        this.address = address;
    }

    public Long getMeteringPointId() {
        return meteringPointId;
    }

    public void setMeteringPointId(Long meteringPointId) {
        this.meteringPointId = meteringPointId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MeteringPointDTO{" +
                "meteringPointId=" + meteringPointId +
                ", address='" + address + '\'' +
                '}';
    }
}
