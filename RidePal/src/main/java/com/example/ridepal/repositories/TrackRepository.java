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


    @Query(value = "SELECT t.artist_id, MAX(t.track_id) AS track_id, MAX(t.track_title) AS track_title, MAX(t.preview_url) AS preview_url, MAX(t.playtime) AS playtime, MAX(t.album_id) AS album_id, MAX(t.rank) AS rank, MAX(t.genre_id) AS genre_id FROM tracks t JOIN genres g ON t.genre_id = g.genre_id WHERE g.genre_name = :genre GROUP BY t.artist_id ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    Set<Track> findTrackByGenreAndDistinctArtist(@Param("genre") String genre, @Param("limit") int limit);

    @Query(value = "SELECT t.artist_id, MAX(t.track_id) AS track_id, MAX(t.track_title) AS track_title, MAX(t.preview_url) AS preview_url, MAX(t.playtime) AS playtime, MAX(t.album_id) AS album_id, MAX(t.rank) AS rank, MAX(t.genre_id) AS genre_id FROM (SELECT * from TRACKS ORDER BY RANK DESC) t JOIN genres g ON t.genre_id = g.genre_id WHERE g.genre_name = :genre GROUP BY t.artist_id  LIMIT :limit", nativeQuery = true)
    Set<Track> findTopTrackByGenreAndDistinctArtist(@Param("genre") String genre, @Param("limit") int limit);

    @Query(value = "SELECT t.artist_id, t.track_id, t.track_title, t.preview_url, t.playtime, t.album_id, t.rank, t.genre_id FROM tracks t JOIN genres g ON t.genre_id = g.genre_id WHERE g.genre_name = :genre  ORDER BY RAND() LIMIT :limit ", nativeQuery = true)
    Set<Track> findTrackByGenre(@Param("genre") String genre, @Param("limit") int limit);

    @Query(value = "SELECT t.artist_id, t.track_id, t.track_title, t.preview_url, t.playtime, t.album_id, t.rank, t.genre_id FROM (SELECT * from TRACKS ORDER BY RANK DESC) t JOIN genres g ON t.genre_id = g.genre_id WHERE g.genre_name = :genre  ORDER BY t.rank DESC LIMIT :limit ", nativeQuery = true)
    Set<Track> findTopTrackByGenre(@Param("genre") String genre, @Param("limit") int limit);
}
