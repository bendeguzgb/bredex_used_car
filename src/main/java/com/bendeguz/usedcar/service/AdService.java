package com.bendeguz.usedcar.service;

import com.bendeguz.usedcar.exception.CommonServiceException;
import com.bendeguz.usedcar.model.Ad;
import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.repository.AdRepository;
import com.bendeguz.usedcar.repository.AdvertiserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final AdvertiserRepository advertiserRepository;

    public List<String> findAdsByBrandAndTypeAndPrice(String brand, String type, Long price) {
        // todo: this should be dynamic and not hardcoded
        String link = "http://localhost:8080/ad/";
        List<Ad> ads;

        if (price == null) {
            ads = adRepository.findAdsByBrandIgnoreCaseContainingAndTypeIgnoreCaseContaining(brand, type);
        } else {
            ads = adRepository.findAdsByBrandIgnoreCaseContainingAndTypeIgnoreCaseContainingAndPrice(brand, type, price);
        }

        return ads.stream()
                .map(ad -> link + ad.getId())
                .collect(Collectors.toList());
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }

    public Ad createAd(Ad ad) {
        return adRepository.save(ad);
    }

    public Ad updateAd(Long id, Ad ad) {
        if (adRepository.existsById(id)) {
            ad.setId(id);
            return adRepository.save(ad);
        }
        return null;
    }

    public void deleteAd(Long id, String currentUserEmail) throws AccessDeniedException {
        Advertiser advertiser = advertiserRepository.findByEmailIgnoreCase(currentUserEmail);

        Ad adToDelete = getAdById(id);

        if (!adToDelete.getCreated_by().equals(advertiser)) {
            throw new CommonServiceException("Only the user who created the ad can delete it!");
        }

        adRepository.deleteById(id);
    }
}
