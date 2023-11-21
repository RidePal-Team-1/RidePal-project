package com.example.ridepal.repositories;

import com.example.ridepal.models.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>, JpaSpecificationExecutor<Track> {
    List<Track> findAll(Sort sort);

    Track findById(int id);

    @Query("SELECT AVG(t.playtime) FROM Track t " +
            "JOIN Genre g ON t.genre.name = :genre ")
    int findAveragePlayTimeForGenre(@Param("genre") String genre);


    @Query(value = "SELECT DISTINCT t.artist_id, t.track_id, t.track_title, t.preview_url, t.playtime, t.album_id, t.rank, t.genre_id FROM tracks t JOIN genres g ON t.genre_id = g.genre_id WHERE g.genre_name = :genre  ORDER BY RAND() LIMIT :limit ", nativeQuery = true)
    Set<Track> findTrackByGenre(@Param("genre") String genre, @Param("limit") int limit);
}
