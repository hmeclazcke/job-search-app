package com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "Job")
public record InternalJobDocument(
        @Id String id,
        String title,
        String company,
        String location,
        Instant creationDate
) {
}