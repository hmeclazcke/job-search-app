package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto;

import java.time.LocalDate;

public record LinkedInJobDto(
        String id,
        String title,
        String company,
        String location,
        LocalDate publishedDate
) {
}