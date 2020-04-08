package com.sharecare.qualityhealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharecare.qualityhealth.entity.SamplePageOffer;

@Repository
public interface SamplePageOfferRepository extends JpaRepository<SamplePageOffer, Long> {

}
