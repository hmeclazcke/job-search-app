package com.hmeclazcke.jobsearchapp.application.merger;

import com.hmeclazcke.jobsearchapp.domain.Job;

import java.util.List;

public class DefaultJobMerger implements JobMerger{
    @Override
    public List<Job> merge(List<Job> jobs) {
        return List.of();
    }
}
