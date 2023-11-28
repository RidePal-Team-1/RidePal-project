package com.example.ridepal.filters.specifications;

import com.example.ridepal.models.Playlist;
import org.springframework.data.jpa.domain.Specification;

public class PlaylistSpecifications {

    public PlaylistSpecifications() {
    }

    public static Specification<Playlist> title(String title) {
        return (root, query, builder) -> builder.like(root.get("title"),  "%" + title + "%");
    }

    public static Specification<Playlist> genre(String name) {
        return (root, query, builder) -> {
            query.distinct(true);
            return builder.like(root.join("genres").get("name"), name + "%");
        };
    }

    public static Specification<Playlist> minDuration(String minDuration) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("playtime"), minDuration);
    }

    public static Specification<Playlist> maxDuration(String maxDuration) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("playtime"), maxDuration);
    }

}
