package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.common.JobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class JobicyJobMapper implements JobMapper<JobicyJobDto> {

    @Override
    public Job toDomain(JobicyJobDto dto) {
        return new Job(
                dto.id().toString(),
                "jobicy",
                dto.jobTitle(),
                dto.companyName(),
                dto.jobGeo(),
                OffsetDateTime.parse(dto.pubDate()).toInstant()
        );
    }
}