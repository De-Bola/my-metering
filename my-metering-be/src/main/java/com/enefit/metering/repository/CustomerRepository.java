package com.enefit.metering.repository;

import com.enefit.metering.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Customer} entities.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their email address.
     *
     * @param username the email address of the customer.
     * @return an {@link Optional} containing the found customer or empty if none found.
     */
    Optional<Customer> findByUsername(String username);

    /**
     * Checks whether a customer exists with the given email address.
     *
     * @param username the email address to check.
     * @return true if a customer exists with the given email address; false otherwise.
     */
    boolean existsByUsername(String username);
}
