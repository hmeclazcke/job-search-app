package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.springframework.stereotype.Component;

@Component
public class JobicyJobMapper implements JobMapper<JobicyJobDto> {
    @Override
    public Job toDomain(JobicyJobDto dto) {
        return null;
    }
}
