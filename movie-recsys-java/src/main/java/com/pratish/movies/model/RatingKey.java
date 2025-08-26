package com.pratish.movies.model;

import lombok.*;
import java.io.Serializable;

/**
 * Composite primary key for Rating (userId + movieId).
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class RatingKey implements Serializable {
    private Integer userId;
    private Integer movieId;
}
