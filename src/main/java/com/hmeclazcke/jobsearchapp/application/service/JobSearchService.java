package com.hmeclazcke.jobsearchapp.application.service;

import com.hmeclazcke.jobsearchapp.application.port.in.SearchJobsUseCase;
import com.hmeclazcke.jobsearchapp.application.port.out.JobProvider;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jobSearchService")
@RequiredArgsConstructor
public class JobSearchService implements SearchJobsUseCase {

    // Spring automatically injects all JobProvider beans registered in AppConfig into this list at startup.
    private final List<JobProvider> jobProviders;

    @Override
    public List<Job> search(JobSearchCriteria criteria) {

        return jobProviders.stream()
                .flatMap(jobProvider -> jobProvider.search(criteria).stream())
                .toList();

    }
}
