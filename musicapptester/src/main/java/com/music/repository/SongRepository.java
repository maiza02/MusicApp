package com.music.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Album;
import com.music.entity.Song;

/**
 * Repository interface for Song entity.
 * Provides CRUD operations and custom queries for songs.
 */
public interface SongRepository extends JpaRepository<Song, Long> {

    /**
     * Finds all songs whose names contain the given query string (case-insensitive).
     *
     * @param query the search string
     * @return list of songs matching the query
     */
    List<Song> findBySongnameContainingIgnoreCase(String query);

    /**
     * Finds all songs associated with a specific album.
     *
     * @param album the album
     * @return list of songs in the album
     */
    List<Song> findByAlbum(Album album);

    /**
     * Finds a single song by its name (case-insensitive) and associated album.
     *
     * @param songname the song name
     * @param album the album
     * @return the matching Song, or null if not found
     */
    Song findBySongnameIgnoreCaseAndAlbum(String songname, Album album);
}
