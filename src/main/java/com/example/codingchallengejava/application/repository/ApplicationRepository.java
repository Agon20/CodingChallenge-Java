package com.example.codingchallengejava.application.repository;

import com.example.codingchallengejava.application.entity.InsuranceApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<InsuranceApplication, Long> {
}
