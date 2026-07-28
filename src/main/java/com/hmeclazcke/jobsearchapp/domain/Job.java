package com.hmeclazcke.jobsearchapp.domain;

import java.io.Serializable;
import java.time.Instant;

public record Job(
        String externalId,
        String source,
        String title,
        String company,
        String location,
        Instant creationDate
) implements Serializable { // Required for Java-based cache serialization.
};
