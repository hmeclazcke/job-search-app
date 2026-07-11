package com.hmeclazcke.jobsearchapp.application.port.out;

import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

public interface JobSource {
    public List<Job> search(JobSearchCriteria criteria);
}
