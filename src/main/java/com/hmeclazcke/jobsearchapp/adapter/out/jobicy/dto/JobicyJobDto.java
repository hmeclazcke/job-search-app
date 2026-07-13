package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JobicyJobDto(
        Long id,
        String url,
        String jobTitle,
        String companyName,
        String jobGeo,
        String pubDate
) {
}