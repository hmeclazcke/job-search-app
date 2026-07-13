package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto.LinkedInJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class LinkedInJobMapper implements JobMapper<LinkedInJobDto> {
    @Override
    public Job toDomain(LinkedInJobDto dto) {
        return new Job(
                dto.id(),
                "linkedin",
                dto.title(),
                dto.company(),
                dto.location(),
                (dto.publishedDate() != null) ?
                        dto.publishedDate().atStartOfDay().toInstant(ZoneOffset.UTC) : null
        );
    }
}