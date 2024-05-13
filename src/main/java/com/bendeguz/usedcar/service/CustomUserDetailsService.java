package com.bendeguz.usedcar.service;

import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.repository.AdvertiserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdvertiserRepository advertiserRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Advertiser advertiser = advertiserRepository.findByEmailIgnoreCase(email);

        if (advertiser == null) {
            throw new UsernameNotFoundException("User with this email was not found!");
        }

        return User.builder()
                .username(advertiser.getEmail())
                .password(advertiser.getPassword())
                .authorities(Set.of())
                .build();
    }
}
