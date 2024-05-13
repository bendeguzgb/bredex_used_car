package com.bendeguz.usedcar.repository;

import com.bendeguz.usedcar.model.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

    Advertiser findByEmailIgnoreCase(String email);
}

