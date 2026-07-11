package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto.LinkedInJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpLinkedInApiClient implements JobClient<LinkedInJobDto> {

    @Override
    public List<LinkedInJobDto> search(JobSearchCriteria criteria) {
        return List.of();
    }
}
