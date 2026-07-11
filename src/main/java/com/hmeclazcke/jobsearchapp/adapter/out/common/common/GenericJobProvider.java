package com.hmeclazcke.jobsearchapp.adapter.out.common.common;

import com.hmeclazcke.jobsearchapp.application.port.out.JobProvider;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenericJobProvider<T> implements JobProvider {

    private final JobClient<T> jobClient;
    private final JobMapper<T> jobMapper;

    @Override
    public List<Job> search(JobSearchCriteria criteria) {
        return jobClient.search(criteria)
                .stream()
                .map(jobMapper::toDomain)
                .toList();
    }
}
