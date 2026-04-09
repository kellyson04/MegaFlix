package com.MegaFlixTV.MegaFlix.repository.specification;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class MovieSpecification {

    public static Specification<Movie> titleContains (String title) {
        return (root, query , builder) ->
        builder.like
                (builder.upper(root.get("movie")), "%" + title.toUpperCase() + "%"
        );
    }

    public static Specification<Movie> genreContains (String genre) {
        return (root , query , builder) ->
                builder.like
                        (builder.upper(root.get("genre")), "%" + genre.toUpperCase() + "%"
        );
    }

    public static Specification<Movie> durationGreaterThanOrEqualTo (Double minDuration) {
        return (root , query , builder) ->
                builder.greaterThanOrEqualTo(root.get("duration"), minDuration);
    }

    public static Specification<Movie> durationLessThanOrEqualTo (Double maxDuration) {
        return (root, query , builder) ->
                builder.lessThanOrEqualTo(root.get("duration"), maxDuration);
    }

    public static Specification<Movie> yearEqualsTo (Integer releaseYear) {
        return (root , query , builder) ->
                builder.equal(root.get("releaseYear"), releaseYear);
    }

    public static Specification<Movie> ratingEqualsTo (Double rating) {
        return (root, query , builder) ->
                builder.equal(root.get("rating"), rating);
    }
}

