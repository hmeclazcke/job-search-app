package com.hmeclazcke.jobsearchapp.adapter.out.common.common;

import com.hmeclazcke.jobsearchapp.domain.Job;

public interface JobMapper<T> {
    Job toDomain(T dto);
}
