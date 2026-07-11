package com.hmeclazcke.jobsearchapp.domain;

import java.time.Instant;

public record Job(
        String externalId,
        String source,
        String title,
        String company,
        String location,
        Instant creationDate
) {
};
