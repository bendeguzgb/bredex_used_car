package com.bendeguz.usedcar.service;

import com.bendeguz.usedcar.exception.CommonServiceException;
import com.bendeguz.usedcar.exception.UserAlreadyExistsException;
import com.bendeguz.usedcar.model.Advertiser;
import com.bendeguz.usedcar.repository.AdvertiserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertiserService {

    private final AdvertiserRepository advertiserRepository;

    public List<Advertiser> getAllAdvertisers() {
        return advertiserRepository.findAll();
    }

    public Advertiser getAdvertiserById(Long id) {
        return advertiserRepository.findById(id).orElse(null);
    }

    public Advertiser createAdvertiser(Advertiser newAdvertiser) {
        Advertiser advertiser = advertiserRepository.findByEmailIgnoreCase(newAdvertiser.getEmail());

        if (advertiser != null) {
            throw new UserAlreadyExistsException(String.format("User already exists with this email: '%s'!", newAdvertiser.getEmail()));
        }

        return advertiserRepository.save(newAdvertiser);
    }

    public Advertiser updateAdvertiser(Long id, Advertiser advertiser) {
        if (advertiserRepository.existsById(id)) {
            advertiser.setId(id);
            return advertiserRepository.save(advertiser);
        }
        return null;
    }

    public void deleteAdvertiser(Long id) {
        advertiserRepository.deleteById(id);
    }
}
