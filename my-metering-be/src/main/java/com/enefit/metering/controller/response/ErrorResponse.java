package com.enefit.metering.controller.response;

import java.time.Instant;

/**
 * Represents an error response returned by the API.
 */
public class ErrorResponse {

    private String code;
    private String message;
    private Instant timestamp;

    /**
     * Default constructor.
     */
    public ErrorResponse() {
    }

    /**
     * Constructs an ErrorResponse with the specified code, message, and timestamp.
     *
     * @param code      the error code.
     * @param message   the error message.
     * @param timestamp the time the error occurred.
     */
    public ErrorResponse(String code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Returns the error code.
     *
     * @return the error code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the error code.
     *
     * @param code the error code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the error message.
     *
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the timestamp when the error occurred.
     *
     * @return the timestamp.
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
