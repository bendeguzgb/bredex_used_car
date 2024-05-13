package com.bendeguz.usedcar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthAndRefreshToken {
    private String authToken;
    private String refreshToken;
}
