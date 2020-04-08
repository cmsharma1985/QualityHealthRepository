package com.sharecare.qualityhealth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharecare.qualityhealth.dto.SamplePageOfferDTO;
import com.sharecare.qualityhealth.service.SamplePageOfferService;

@RestController
@RequestMapping("/samplePageOffer")
public class SamplePageOfferController {

	@Autowired
	SamplePageOfferService samplePageOfferService;

	@GetMapping("/list")
	public List<SamplePageOfferDTO> getAllSamplePageOffer() {
		return samplePageOfferService.getAllSamplePageOffer();
	}
	
	@GetMapping("/list/{id}")
	public SamplePageOfferDTO getSamplePageOffer(@PathVariable Long id) {
		return samplePageOfferService.getSamplePageOffer(id);
	}
	
	@GetMapping("/randomlist/{count}")
	public List<SamplePageOfferDTO> getRandomizedSamplePageOffersbasedOnCount(@PathVariable Long count) {
		return samplePageOfferService.getRandomizedSamplePageOffersbasedOnCount(count);
	}

	@DeleteMapping("/list/{id}")
	public void deleteSamplePageOffer(@PathVariable Long id) {
		samplePageOfferService.deleteSamplePageOffer(id);
	}

	@PutMapping("/list/save")
	public SamplePageOfferDTO createOrUpdateSamplePageOffer(@RequestBody SamplePageOfferDTO samplePageOfferDTO) {
		return samplePageOfferService.createOrUpdateSamplePageOffer(samplePageOfferDTO);
	}
	
	@PutMapping("/list/{id}/{flag}")
	public SamplePageOfferDTO activateOrDeactivateSamplePageOffer(@PathVariable Long id, @PathVariable boolean flag) {
		return samplePageOfferService.activateOrDeactivateSamplePageOffer(id,flag);
	}
}
