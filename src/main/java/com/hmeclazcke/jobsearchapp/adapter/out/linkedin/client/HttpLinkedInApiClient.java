package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto.LinkedInJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;

import java.util.List;

public class HttpLinkedInApiClient implements JobClient<LinkedInJobDto> {

    @Override
    public List<LinkedInJobDto> search(JobSearchCriteria criteria) {
        //TODO:
        follow from here.
        return List.of();
    }
}
