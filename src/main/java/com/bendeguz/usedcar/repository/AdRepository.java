package com.bendeguz.usedcar.repository;

import com.bendeguz.usedcar.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAdsByBrandIgnoreCaseContaining(String brand);
    List<Ad> findAdsByBrandIgnoreCase(String brand);
    List<Ad> findAdsByBrandIgnoreCaseContainingAndTypeIgnoreCaseContaining(String brand, String type);
    List<Ad> findAdsByBrandIgnoreCaseContainingAndTypeIgnoreCaseContainingAndPrice(String brand, String type, Long price);
}

