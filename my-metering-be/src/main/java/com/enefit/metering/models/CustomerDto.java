package com.enefit.metering.models;

import lombok.Builder;

/**
 * Data Transfer Object (DTO) representing a customer.
 * Aligns with the Customer model by including fields such as customerId, firstName, lastName, username, emailAddress, and password.
 */
@Builder
public class CustomerDto {

    private String customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public CustomerDto() {
    }

    /**
     * Constructs a CustomerDto with only emailAddress and password.
     * Typically used for authentication scenarios.
     *
     * @param username the customer's email address.
     * @param password     the customer's password.
     */
    public CustomerDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs a CustomerDto with full details.
     *
     * @param firstName    the customer's first name.
     * @param lastName     the customer's last name.
     * @param username     the customer's username.
     * @param password     the customer's password.
     */
    public CustomerDto(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs a CustomerDto with full details.
     *
     * @param customerId   the customer's unique identifier.
     * @param firstName    the customer's first name.
     * @param lastName     the customer's last name.
     * @param username     the customer's username.
     * @param password     the customer's password.
     */
    public CustomerDto(String customerId, String firstName, String lastName, String username, String password) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the customer's unique identifier.
     *
     * @return the customerId.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer's unique identifier.
     *
     * @param customerId the customerId to set.
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the customer's first name.
     *
     * @return the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the customer's first name.
     *
     * @param firstName the firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the customer's last name.
     *
     * @return the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the customer's last name.
     *
     * @param lastName the lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the customer's username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the customer's username.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the customer's password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the customer's password.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the CustomerDto.
     * The password field is excluded for security purposes.
     *
     * @return a string representation of the CustomerDto.
     */
    @Override
    public String toString() {
        return "CustomerDto{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
