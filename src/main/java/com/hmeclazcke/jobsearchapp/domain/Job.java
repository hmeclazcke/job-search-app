package com.hmeclazcke.jobsearchapp.domain;

public record Job(
        String title,
        String company,
        String location,
        String source
) {
};
