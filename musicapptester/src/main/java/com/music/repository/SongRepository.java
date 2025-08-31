package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Album;
import com.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

	List<Song> findBySongnameContainingIgnoreCase(String query);
	List<Song> findByAlbum(Album album);
	Song findBySongnameIgnoreCaseAndAlbum(String songname, Album album);


}
