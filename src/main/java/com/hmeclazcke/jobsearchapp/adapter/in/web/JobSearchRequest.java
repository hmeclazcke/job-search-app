package com.hmeclazcke.jobsearchapp.adapter.in.web;

public record JobSearchRequest(
        String text,
        String location,
        Boolean remote
) {
}