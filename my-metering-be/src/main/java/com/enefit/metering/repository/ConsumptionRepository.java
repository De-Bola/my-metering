package com.enefit.metering.repository;

import com.enefit.metering.models.Consumption;
import com.enefit.metering.models.MonthlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing {@link Consumption} entities.
 * Provides methods to query consumption data by associated metering point.
 */
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

    /**
     * Finds consumption records by the given metering point ID.
     *
     * @param meteringPointId the unique identifier of the metering point.
     * @return a {@link List<Consumption>} containing the consumption records associated with the metering point.
     */
    List<Consumption> findByMeteringPointMeteringPointId(Long meteringPointId);

//    @Query(value = "SELECT DATE_TRUNC('month', c.consumptionTime) AS month, " +
//            "SUM(c.amount) AS totalConsumption " +
//            "FROM Consumption c " +
//            "WHERE c.meteringPoint.meteringPointId = :meteringPointId " +
//            "GROUP BY DATE_TRUNC('month', c.consumptionTime) " +
//            "ORDER BY month")
//@Query("SELECT function('DATE_TRUNC', 'month', c.consumptionTime) as month, " +
//        "SUM(c.amount) as totalConsumption " +
//        "FROM Consumption c " +
//        "WHERE c.meteringPoint.meteringPointId = :meteringPointId " +
//        "GROUP BY function('DATE_TRUNC', 'month', c.consumptionTime) " +
//        "ORDER BY month")
@Query("SELECT function('timezone', '+03:00', function('DATE_TRUNC', 'month', c.consumptionTime)) as month, " +
        "SUM(c.amount) as totalConsumption " +
        "FROM Consumption c " +
        "WHERE c.meteringPoint.meteringPointId = :meteringPointId " +
        "GROUP BY function('timezone', '+03:00', function('DATE_TRUNC', 'month', c.consumptionTime)) " +
        "ORDER BY month")
    List<MonthlyConsumption> findMonthlyConsumptionSum(@Param("meteringPointId") Long meteringPointId);
}
