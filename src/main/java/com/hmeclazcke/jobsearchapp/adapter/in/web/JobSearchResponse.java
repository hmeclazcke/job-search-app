package com.hmeclazcke.jobsearchapp.adapter.in.web;

import com.hmeclazcke.jobsearchapp.domain.Job;

import java.time.Instant;

public record JobSearchResponse(
        String externalId,
        String source,
        String title,
        String company,
        String location,
        Instant creationDate
) {

    public static JobSearchResponse from(Job job) {
        return new JobSearchResponse(
                job.externalId(),
                job.source(),
                job.title(),
                job.company(),
                job.location(),
                job.creationDate()
        );
    }
}