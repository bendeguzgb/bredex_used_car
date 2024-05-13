package com.bendeguz.usedcar.service;

import com.bendeguz.usedcar.exception.CommonServiceException;
import com.bendeguz.usedcar.model.AuthAndRefreshToken;
import com.bendeguz.usedcar.model.RefreshToken;
import com.bendeguz.usedcar.repository.AdvertiserRepository;
import com.bendeguz.usedcar.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt-refresh-token-expiration-in-seconds}")
    private Integer expiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AdvertiserRepository advertiserRepository;
    private final JwtTokenService jwtTokenService;

    public AuthAndRefreshToken refreshAuthToken(String refreshToken) {
        RefreshToken savedRefreshToken = findByToken(refreshToken).orElseThrow(() -> new CommonServiceException("Refresh Token is not in DB!!"));
        verifyExpiration(savedRefreshToken);

        String newAuthToken = jwtTokenService.generateAuthToken(savedRefreshToken.getAdvertiser().getEmail());

        String newRefreshToken = UUID.randomUUID().toString();
        savedRefreshToken.setToken(newRefreshToken);
        savedRefreshToken.setExpiryDate(Instant.now().plusSeconds(expiration));
        refreshTokenRepository.flush();

        return AuthAndRefreshToken.builder()
                .authToken(newAuthToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .advertiser(advertiserRepository.findByEmailIgnoreCase(email))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(expiration))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public void verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new CommonServiceException("The refresh token has expired!");
        }
    }
}
