package com.hmeclazcke.jobsearchapp.application.merger;

import com.hmeclazcke.jobsearchapp.domain.Job;

import java.util.List;

public interface JobMerger {
    public List<Job> merge(List<Job> jobs);
}
