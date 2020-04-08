package com.sharecare.qualityhealth.dto;

import java.util.Set;

import com.sharecare.qualityhealth.entity.SamplePageOffer;
import com.sharecare.qualityhealth.entity.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SamplePageOfferDTO {
	
	private Long id;
	private String imagePath;
	private String title;
	private String description;
	private Set<Tag> tags;
	private String link;
	private Boolean active;
	
	public SamplePageOfferDTO(SamplePageOffer samplePageOffer) {
		id = samplePageOffer.getId();
		imagePath = samplePageOffer.getImagePath();
		title = samplePageOffer.getTitle();
		description = samplePageOffer.getDescription();
		tags = samplePageOffer.getTags();
		link = samplePageOffer.getLink();
		active = samplePageOffer.getActive();
	}
}
