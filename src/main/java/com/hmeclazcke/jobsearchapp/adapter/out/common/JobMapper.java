package com.hmeclazcke.jobsearchapp.adapter.out.common;

import com.hmeclazcke.jobsearchapp.domain.Job;

@FunctionalInterface
public interface JobMapper<T> {
    Job toDomain(T dto);
}
