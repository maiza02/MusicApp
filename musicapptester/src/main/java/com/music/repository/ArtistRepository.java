package com.music.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Artist;

/**
 * Repository interface for Artist entity.
 * Provides CRUD operations and custom queries for artists.
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Finds all artists whose names contain the given query string (case-insensitive).
     *
     * @param query the search string
     * @return list of artists matching the query
     */
    List<Artist> findByArtistnameContainingIgnoreCase(String query);

    /**
     * Finds a single artist by their name (case-insensitive).
     *
     * @param artistname the artist name
     * @return the matching Artist, or null if not found
     */
    Artist findByArtistnameIgnoreCase(String artistname);
}
