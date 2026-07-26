package com.hmeclazcke.jobsearchapp.adapter.out.common.common;

import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

public interface JobClient<T> {
    List<T> search(JobSearchCriteria criteria);
}
