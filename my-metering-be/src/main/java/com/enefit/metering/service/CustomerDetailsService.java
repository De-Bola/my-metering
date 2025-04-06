package com.enefit.metering.service;

import com.enefit.metering.repository.CustomerRepository;
import com.enefit.metering.utils.MaskingUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(" Customer with " +
                        MaskingUtil.mask(username) + " not found"));
    }

}
