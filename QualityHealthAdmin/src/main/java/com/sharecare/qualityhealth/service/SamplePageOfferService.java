package com.sharecare.qualityhealth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharecare.qualityhealth.dto.SamplePageOfferDTO;
import com.sharecare.qualityhealth.entity.SamplePageOffer;
import com.sharecare.qualityhealth.entity.Tag;
import com.sharecare.qualityhealth.exception.NotFoundException;
import com.sharecare.qualityhealth.exception.RequiredException;
import com.sharecare.qualityhealth.repository.SamplePageOfferRepository;
import com.sharecare.qualityhealth.repository.TagRepository;

@Service
public class SamplePageOfferService {

	@Autowired
	private SamplePageOfferRepository samplePageOfferRepository;

	@Autowired
	private TagRepository tagRepository;

	public List<SamplePageOfferDTO> getAllSamplePageOffer() {
		return samplePageOfferRepository.findAll().stream().map(offer -> new SamplePageOfferDTO(offer))
				.collect(Collectors.toList());
	}

	public void deleteSamplePageOffer(Long id) {
		SamplePageOffer samplePageOffer;
		if (id != null && id != 0) {
			Optional<SamplePageOffer> alreadyExist = samplePageOfferRepository.findById(id);
			if (alreadyExist.isPresent()) {
				samplePageOffer = alreadyExist.get();
				samplePageOfferRepository.delete(samplePageOffer);
				// Deleting associated tags if not being used.
				samplePageOffer.getTags().parallelStream().forEachOrdered(tag -> {
					System.out.println("Size" + tag.getSamplePageOffer().size());
					if (tag.getSamplePageOffer().size() <= 1) {
						tagRepository.delete(tag);
					}
				});
			} else {
				throw new NotFoundException("No sample found with the given ID: " + id);
			}
		} else {
			throw new RequiredException("Sample Id is required");
		}

	}

	public SamplePageOfferDTO activateOrDeactivateSamplePageOffer(Long id, boolean flag) {
		SamplePageOffer samplePageOffer;
		if (id != null && id != 0) {
			Optional<SamplePageOffer> alreadyExist = samplePageOfferRepository.findById(id);
			if (alreadyExist.isPresent()) {
				samplePageOffer = alreadyExist.get();
				samplePageOffer.setActive(flag);
			} else {
				throw new NotFoundException("No sample found with the given ID: " + id);
			}
		} else {
			throw new NotFoundException("No sample found with the given ID: " + id);
		}
		return new SamplePageOfferDTO(samplePageOfferRepository.save(samplePageOffer));
	}

	public List<SamplePageOfferDTO> getRandomizedSamplePageOffersbasedOnCount(Long count) {
		if (count > 0) {
			List<SamplePageOffer> samplesList = samplePageOfferRepository.findAll();
			return getRandomizedListBasedOnCount(samplesList, samplesList.size(), count).stream()
					.map(offer -> new SamplePageOfferDTO(offer)).collect(Collectors.toList());
		} else {
			throw new NotFoundException("No sample selected with count " + count);
		}
	}

	private static <T> List<T> getRandomizedListBasedOnCount(List<T> samplesList, int size, Long count) {
		if (count < size) {
			List<T> suffleList = new ArrayList<T>();
			Random rand = new Random();
			for (int i = 0; i < count; i++) {
				int randomIndex = rand.nextInt(samplesList.size());
				T randomElement = samplesList.get(randomIndex);
				samplesList.remove(randomIndex);
				suffleList.add(randomElement);
			}
			return suffleList;
		} else {
			return samplesList;
		}
	}

	public SamplePageOfferDTO getSamplePageOffer(Long id) {
		SamplePageOffer samplePageOffer = new SamplePageOffer();
		if (id != null && id != 0) {
			Optional<SamplePageOffer> alreadyExist = samplePageOfferRepository.findById(id);
			if (alreadyExist.isPresent()) {
				samplePageOffer = alreadyExist.get();
			} else {
				throw new NotFoundException("No sample found with the given ID: " + id);
			}
		} else {
			throw new RequiredException("Sample Id is required");
		}
		return new SamplePageOfferDTO(samplePageOffer);
	}

	public SamplePageOfferDTO createOrUpdateSamplePageOffer(SamplePageOfferDTO samplePageOfferDTO) {
		SamplePageOffer samplePageOffer = new SamplePageOffer();
		Boolean existing = false;
		if (samplePageOfferDTO.getId() != null && samplePageOfferDTO.getId() != 0) {
			Optional<SamplePageOffer> alreadyExist = samplePageOfferRepository.findById(samplePageOfferDTO.getId());
			if (alreadyExist.isPresent()) {
				samplePageOffer = alreadyExist.get();
				existing = true;
			} else {
				throw new NotFoundException("No sample found with the given ID: " + samplePageOfferDTO.getId());
			}
		}
		samplePageOffer.setImagePath(samplePageOfferDTO.getImagePath());
		samplePageOffer.setTitle(samplePageOfferDTO.getTitle());
		samplePageOffer.setDescription(samplePageOfferDTO.getDescription());
		samplePageOffer.setLink(samplePageOfferDTO.getLink());
		samplePageOffer.setActive(samplePageOfferDTO.getActive());
		Set<Tag> oldTags = samplePageOffer.getTags();
		Set<Tag> tagsToSave = samplePageOfferDTO.getTags().stream().map(tag -> {
			// if tag ID and tag text is present
			if (tag.getId() != null && tag.getId() != 0 && StringUtils.isNotBlank(tag.getText())) {
				Optional<Tag> existingTag = tagRepository.findByIdAndTextIgnoreCase(tag.getId(), tag.getText());
				if (!existingTag.isPresent()) {
					throw new NotFoundException(
							"No tag found with the given Sample ID: " + tag.getId() + ", Tag text: " + tag.getText());
				}
				return existingTag.get();
			}
			// if only tagId is present
			if (tag.getId() != null && tag.getId() != 0 && StringUtils.isBlank(tag.getText())) {
				Optional<Tag> existingTag = tagRepository.findById(tag.getId());
				if (!existingTag.isPresent()) {
					throw new NotFoundException("No tag found with the given Sample ID: " + tag.getId());
				}
				return existingTag.get();
			} else {
				Optional<Tag> existingTag = tagRepository.findByTextIgnoreCase(tag.getText());
				if (existingTag.isPresent()) {
					return existingTag.get();
				}
			}
			return tag;
		}).collect(Collectors.toSet());

		samplePageOffer.setTags(tagsToSave);

		// Deleting unused tags for existing SamplePageOffer.
		if (existing) {
			Set<Tag> unusedTags = oldTags.stream()
					.filter(oldTag -> tagsToSave.stream().noneMatch(x -> x.getText().equals(oldTag.getText())))
					.collect(Collectors.toSet());
			// Deleting
			unusedTags.stream().forEach(tag -> {
				if (tag.getSamplePageOffer().size() <= 1) {
					tagRepository.delete(tag);
				}
			});
		}

		return new SamplePageOfferDTO(samplePageOfferRepository.save(samplePageOffer));
	}
}
