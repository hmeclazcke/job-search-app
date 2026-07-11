package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpJobicyApiClient implements JobClient<JobicyJobDto> {
    @Override
    public List<JobicyJobDto> search(JobSearchCriteria criteria) {
        return List.of();
    }
}
