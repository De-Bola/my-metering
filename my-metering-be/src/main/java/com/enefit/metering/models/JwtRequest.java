package com.enefit.metering.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents a JWT authentication request containing the username and password.
 */
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class"
//)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtRequest {

    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public JwtRequest() {
    }

    /**
     * Constructs a JwtRequest with the specified username and password.
     *
     * @param username the username (or email) used for authentication.
     * @param password the password used for authentication.
     */
    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
