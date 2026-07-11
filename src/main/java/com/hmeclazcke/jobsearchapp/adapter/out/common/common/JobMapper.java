package com.hmeclazcke.jobsearchapp.adapter.out.common.common;

import com.hmeclazcke.jobsearchapp.domain.Job;

public interface JobMapper<T> {
    public Job toDomain(T dto);
}
