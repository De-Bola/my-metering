package com.enefit.metering.controller.response;

/**
 * Represents a successful API response.
 *
 * @param <T> the type of data contained in the response.
 */
public class SuccessResponse<T> {

    private String code;
    private T data;
    private String message;

    /**
     * Default constructor.
     */
    public SuccessResponse() {
    }

    /**
     * Constructs a SuccessResponse with the specified data and message.
     *
     * @param data    the data to include in the response.
     * @param message the message accompanying the response.
     */
    public SuccessResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    /**
     * Constructs a SuccessResponse with the specified code, data, and message.
     *
     * @param code    the response code.
     * @param data    the data to include in the response.
     * @param message the message accompanying the response.
     */
    public SuccessResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * Returns the response code.
     *
     * @return the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the response code.
     *
     * @param code the code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the response data.
     *
     * @return the data.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the response data.
     *
     * @param data the data to set.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the response message.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
