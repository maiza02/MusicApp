package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Album;
import com.music.entity.Artist;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	List<Album> findByAlbumnameContainingIgnoreCase(String query);

	Album findByAlbumnameIgnoreCaseAndArtist(String albumName, Artist artist);

}
