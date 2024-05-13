package com.bendeguz.usedcar.controller;

import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.model.AuthAndRefreshToken;
import com.bendeguz.usedcar.model.RefreshTokenDto;
import com.bendeguz.usedcar.service.AdvertiserService;
import com.bendeguz.usedcar.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdvertiserService advertiserService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Advertiser> signUpUser(@RequestBody @Valid Advertiser advertiser) {
        Advertiser createdAdvertiser = advertiserService.createAdvertiser(advertiser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdAdvertiser);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthAndRefreshToken> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshAuthToken(refreshTokenDto.getRefreshToken()));
    }
}
