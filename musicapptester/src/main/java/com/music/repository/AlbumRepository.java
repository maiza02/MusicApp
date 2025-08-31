package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Album;
import com.music.entity.Artist;

/**
 * Repository interface for Album entity.
 * Provides CRUD operations and custom queries for albums.
 */
public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * Finds albums whose names contain the given query string (case-insensitive).
     *
     * @param query the search string
     * @return list of albums matching the query
     */
    List<Album> findByAlbumnameContainingIgnoreCase(String query);

    /**
     * Finds a single album by its name (case-insensitive) and associated artist.
     *
     * @param albumName the album name
     * @param artist the artist of the album
     * @return the matching Album, or null if not found
     */
    Album findByAlbumnameIgnoreCaseAndArtist(String albumName, Artist artist);
}
