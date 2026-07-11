package com.hmeclazcke.jobsearchapp.application.service;

import com.hmeclazcke.jobsearchapp.application.merger.JobMerger;
import com.hmeclazcke.jobsearchapp.application.port.in.SearchJobs;
import com.hmeclazcke.jobsearchapp.application.port.out.JobSource;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class JobSearchService implements SearchJobs {
    private final List<JobSource> jobSources;
    private final JobMerger jobMerger;
    private final JobSearchCriteria searchCriteria;

    public JobSearchService(List<JobSource> jobSources, JobMerger jobMerger, JobSearchCriteria searchCriteria) {
        this.jobSources = jobSources;
        this.jobMerger = jobMerger;
        this.searchCriteria = searchCriteria;
    }


    @Override
    public List<Job> search(JobSearchCriteria criteria) {

        List<Job> jobs = new ArrayList<>();

        for (JobSource jobSource : jobSources) {
            jobs.addAll(jobSource.search(searchCriteria));
        }

        return jobMerger.merge(jobs);
    }
}
