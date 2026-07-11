package com.hmeclazcke.jobsearchapp.adapter.out.common.common;

import com.hmeclazcke.jobsearchapp.application.port.out.JobSource;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

public class GenericJobSource<T> implements JobSource {

    private final JobClient<T> jobClient;
    private final JobMapper<T> jobMapper;

    public GenericJobSource(JobClient<T> jobClient, JobMapper<T> jobMapper) {
        this.jobClient = jobClient;
        this.jobMapper = jobMapper;
    }


    @Override
    public List<Job> search(JobSearchCriteria criteria) {
        return jobClient.search(criteria)
                .stream()
                .map(jobMapper::toDomain)
                .toList();
    }
}
