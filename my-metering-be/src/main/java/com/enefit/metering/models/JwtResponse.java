package com.enefit.metering.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents a JWT response containing token details and additional response data.
 *
 * @param <T> the type of additional response data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtResponse<T> {

    private String token;
    private String refreshToken;
    private String expiresIn;
    private T response;

    /**
     * Default constructor.
     */
    public JwtResponse() {
    }

    /**
     * Constructs a JwtResponse with the specified details.
     *
     * @param token        the JWT token.
     * @param refreshToken the refresh token.
     * @param expiresIn    the expiry time as a string.
     * @param response     additional response data.
     */
    public JwtResponse(String token, String refreshToken, String expiresIn, T response) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.response = response;
    }

    /**
     * Returns the JWT token.
     *
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param refreshToken the refresh token to set.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Returns the expiry time.
     *
     * @return the expiry time as a string.
     */
    public String getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the expiry time.
     *
     * @param expiresIn the expiry time to set.
     */
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Returns the additional response data.
     *
     * @return the response data.
     */
    public T getResponse() {
        return response;
    }

    /**
     * Sets the additional response data.
     *
     * @param response the response data to set.
     */
    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", response=" + response +
                '}';
    }
}
