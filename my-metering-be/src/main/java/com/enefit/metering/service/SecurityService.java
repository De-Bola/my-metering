package com.enefit.metering.service;

public interface SecurityService {
    /**
     * Returns the username of the currently authenticated user.
     *
     * @return the current username.
     */
    String getCurrentUsername();
}
