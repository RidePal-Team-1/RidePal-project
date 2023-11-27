package com.example.ridepal.repositories;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer>, JpaSpecificationExecutor<Playlist> {
    Page<Playlist> findAll(Specification<Playlist> filters, Pageable pageable);

    Playlist findById(int id);

    @Query(value = "SELECT * FROM playlists p WHERE p.user_id = :id", nativeQuery = true)
    List<Playlist> getUserPlaylists(@Param("id") int id);

    @Query(value = "select * from playlists order by `rank` desc limit 10", nativeQuery = true)
    List<Playlist> getTopPlaylists();

    @Query(value = "select count(*) from playlists", nativeQuery = true)
    int getPlaylistsCount();

    @Query(value = "select count(*) from playlists_genres p where p.genre_id = :genre", nativeQuery = true)
    int getPlaylistsCountByGenre(@Param("genre") long id);
}
