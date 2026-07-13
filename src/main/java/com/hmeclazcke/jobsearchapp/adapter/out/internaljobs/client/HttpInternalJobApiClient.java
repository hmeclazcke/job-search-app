package com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.dto.InternalJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpInternalJobApiClient implements JobClient<InternalJobDto> {
    @Override
    public List<InternalJobDto> search(JobSearchCriteria criteria) {
        //TODO: Continue from here
        return List.of();
    }
}
