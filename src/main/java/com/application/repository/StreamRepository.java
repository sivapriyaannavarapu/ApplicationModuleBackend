package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Stream;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer>{

	Optional<Stream> findByStreamName(String streamName);
}
