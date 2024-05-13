package com.bendeguz.usedcar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyConfigProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
