package com.sharecare.qualityhealth.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "Tag")
@Table(name = "TAG", uniqueConstraints = { @UniqueConstraint(columnNames = "TAG_ID") })
public class Tag implements Serializable {
	private static final long serialVersionUID = -279063372846798580L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TAG_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "TEXT", unique = false, nullable = false, length = 100)
	private String text;

	@JsonIgnore
	@ManyToMany(mappedBy = "tags", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<SamplePageOffer> samplePageOffer;

	@Override
	public String toString() {
		return "Tag [id=" + id + ", text=" + text + "]";
	}
}
