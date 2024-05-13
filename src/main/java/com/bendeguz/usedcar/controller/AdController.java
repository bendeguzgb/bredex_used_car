package com.bendeguz.usedcar.controller;

import com.bendeguz.usedcar.model.Ad;
import com.bendeguz.usedcar.service.AdService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchAd(
            @RequestParam(name = "name", required = false, defaultValue = "") @Length(max = 20) String brand,
            @RequestParam(name = "type", required = false, defaultValue = "") @Length(max = 20) String type,
            @RequestParam(name = "price", required = false) Long price) {
        List<String> adLinks = adService.findAdsByBrandAndTypeAndPrice(brand, type, price);
        return ResponseEntity.ok(adLinks);
    }

    @GetMapping
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads = adService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
        Ad ad = adService.getAdById(id);
        if (ad != null) {
            return ResponseEntity.ok(ad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ad> createAd(@RequestBody Ad ad) {
        Ad createdAd = adService.createAd(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        String currentUserEmail = authentication.getName();

        adService.deleteAd(id, currentUserEmail);
        return ResponseEntity.noContent().build();
    }
}