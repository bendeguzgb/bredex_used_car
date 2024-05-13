package com.bendeguz.usedcar.controller;

import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.service.AdvertiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advertiser")
@RequiredArgsConstructor
public class AdvertiserController {

    private final AdvertiserService advertiserService;

    @GetMapping
    public ResponseEntity<List<Advertiser>> getAllAdvertisers() {
        List<Advertiser> advertisers = advertiserService.getAllAdvertisers();
        return ResponseEntity.ok(advertisers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advertiser> getAdvertiserById(@PathVariable Long id) {
        Advertiser advertiser = advertiserService.getAdvertiserById(id);
        if (advertiser != null) {
            return ResponseEntity.ok(advertiser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}