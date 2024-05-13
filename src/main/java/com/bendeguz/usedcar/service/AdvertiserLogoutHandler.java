package com.bendeguz.usedcar.service;

import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.repository.AdvertiserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdvertiserLogoutHandler implements LogoutHandler {

    private final AdvertiserRepository advertiserRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // todo: How do you log someone out when using jwt? As far as I know, it is not possible.
        try {
            UserDetails userDetail = ((UserDetails) authentication.getPrincipal());

            Advertiser user = advertiserRepository.findByEmailIgnoreCase(userDetail.getUsername());
            user.setLastLogout(Instant.now());
            advertiserRepository.flush();
        } catch (Exception e) {
            log.error("Exception during updating last logout of user!", e);
        }
    }
}
