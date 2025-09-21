package com.microshop.notification.repository;

import com.microshop.notification.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    // Each user will have only one preference row
    Optional<Preference> findByUserId(Long userId);
}
