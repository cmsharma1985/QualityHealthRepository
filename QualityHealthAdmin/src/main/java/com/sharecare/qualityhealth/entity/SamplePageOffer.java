package com.sharecare.qualityhealth.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity(name = "SamplePageOffer")
@Table(name = "SAMPLE_PAGE_OFFER", uniqueConstraints = { @UniqueConstraint(columnNames = "OFFER_ID") })
public class SamplePageOffer implements Serializable {

	private static final long serialVersionUID = -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OFFER_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "IMAGEPATH", unique = false, nullable = false, length = 100)
	private String imagePath;

	@Column(name = "TITLE", unique = false, nullable = false, length = 100)
	private String title;

	@Column(name = "DESCRIPTION", unique = false, nullable = false, length = 1000)
	private String description;

	@ManyToMany(targetEntity = Tag.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "OFFER_TAG", joinColumns = {
			@JoinColumn(name = "OFFER_ID", referencedColumnName = "OFFER_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID") })

	private Set<Tag> tags;

	@Column(name = "LINK", unique = false, nullable = false, length = 100)
	private String link;

	@Column(name = "ACTIVE", unique = false, nullable = false, length = 100)
	private Boolean active;

}
