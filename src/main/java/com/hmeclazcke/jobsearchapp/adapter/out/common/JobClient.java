package com.hmeclazcke.jobsearchapp.adapter.out.common;

import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

@FunctionalInterface
public interface JobClient<T> {
    List<T> search(JobSearchCriteria criteria);
}
