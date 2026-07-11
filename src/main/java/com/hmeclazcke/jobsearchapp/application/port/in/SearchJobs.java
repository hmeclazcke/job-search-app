package com.hmeclazcke.jobsearchapp.application.port.in;

import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

public interface SearchJobs {
    List<Job> search(JobSearchCriteria criteria);
}
