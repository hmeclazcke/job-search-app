package com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.common.JobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.document.InternalJobDocument;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.springframework.stereotype.Component;

@Component
public class InternalJobMapper implements JobMapper<InternalJobDocument> {

    @Override
    public Job toDomain(InternalJobDocument document) {
        return new Job(
                document.id(),
                "internal",
                document.title(),
                document.company(),
                document.location(),
                document.creationDate()
        );
    }
}