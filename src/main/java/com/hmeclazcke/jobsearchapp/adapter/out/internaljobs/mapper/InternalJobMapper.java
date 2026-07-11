package com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.common.common.JobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.dto.InternalJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.springframework.stereotype.Component;

@Component
public class InternalJobMapper implements JobMapper<InternalJobDto> {
    @Override
    public Job toDomain(InternalJobDto dto) {
        return null;
    }
}
