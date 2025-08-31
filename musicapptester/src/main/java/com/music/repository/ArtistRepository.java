package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

	List<Artist> findByArtistnameContainingIgnoreCase(String query);
	Artist findByArtistnameIgnoreCase(String artistname);

	
}
