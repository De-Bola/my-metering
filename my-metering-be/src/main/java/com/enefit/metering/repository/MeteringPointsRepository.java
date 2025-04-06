package com.enefit.metering.repository;

import com.enefit.metering.models.MeteringPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link MeteringPoint} entities.
 * Provides methods to query metering points associated with a specific customer.
 */
public interface MeteringPointsRepository extends JpaRepository<MeteringPoint, Long> {

    /**
     * Finds metering points by the given customer ID.
     *
     * @param customerId the unique identifier of the customer.
     * @return a {@link List<MeteringPoint>} containing the metering points for the specified customer.
     */
    List<MeteringPoint> findMeteringPointsByCustomerCustomerId(Long customerId);
}
