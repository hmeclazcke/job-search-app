package com.hmeclazcke.jobsearchapp.application.service;

import com.hmeclazcke.jobsearchapp.application.port.in.SearchJobsUseCase;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CachedJobSearchService implements SearchJobsUseCase {

    private final SearchJobsUseCase delegate;

    public CachedJobSearchService(
            @Qualifier("jobSearchService") SearchJobsUseCase delegate) {
        this.delegate = delegate;
    }

    // Decorates the real search service with cache behavior.
    @Override
    @Cacheable(
            cacheNames = "job-searches",
            key = "#root.target.cacheKey(#criteria)",
            unless = "#result.isEmpty()"
    )
    public List<Job> search(JobSearchCriteria criteria) {
        return delegate.search(criteria);
    }

    public String cacheKey(JobSearchCriteria criteria) {
        return normalize(criteria.text()) + ":"
                + normalize(criteria.location()) + ":"
                + Boolean.TRUE.equals(criteria.remote());
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}