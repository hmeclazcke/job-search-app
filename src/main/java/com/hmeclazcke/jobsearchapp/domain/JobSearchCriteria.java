package com.hmeclazcke.jobsearchapp.domain;

public record JobSearchCriteria(
        String text,
        String location,
        Boolean remote
) {
};
