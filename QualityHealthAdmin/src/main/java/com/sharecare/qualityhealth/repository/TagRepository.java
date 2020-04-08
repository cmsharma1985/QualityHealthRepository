package com.sharecare.qualityhealth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharecare.qualityhealth.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	public Optional<Tag> findByTextIgnoreCase(String text);
	public Optional<Tag> findByIdAndTextIgnoreCase(Long id, String text);

}
