package com.example.ridepal.repositories;

import com.example.ridepal.models.Track;
import com.example.ridepal.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>, JpaSpecificationExecutor<Track> {
    List<Track> findAll(Sort sort);

    Track findById(int id);
}
