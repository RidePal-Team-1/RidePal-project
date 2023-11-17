package com.example.ridepal.filters;

import com.example.ridepal.models.Playlist;
import org.springframework.data.jpa.domain.Specification;

public class PlaylistSpecifications {

    public PlaylistSpecifications() {
    }

    public static Specification<Playlist> title(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

//    public static Specification<Playlist> genre(String genre) {
//        return (root, query, builder) -> builder.like(root.get("genre"), "%" + genre + "%");
//    }

    public static Specification<Playlist> minRank(double minRank) {
        return (root, query, builder) -> builder.equal(root.get("rank"), minRank);
    }

    public static Specification<Playlist> maxRank(double maxRank) {
        return (root, query, builder) -> builder.equal(root.get("rank"), maxRank);
    }

    public static Specification<Playlist> betweenRank(double minRank, double maxRank) {
        return (root, query, builder) -> builder.between(root.get("rank"), minRank, maxRank);
    }
}
