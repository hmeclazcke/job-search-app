package com.hmeclazcke.jobsearchapp.adapter.in.web;

import com.hmeclazcke.jobsearchapp.application.port.in.SearchJobsUseCase;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobSearchController {

    private final SearchJobsUseCase searchJobsUseCase;

    @PostMapping("/search")
    public List<JobSearchResponse> search(@RequestBody JobSearchRequest request) {

        JobSearchCriteria criteria = new JobSearchCriteria(
                request.text(),
                request.location(),
                request.remote()
        );

        return searchJobsUseCase.search(criteria)
                .stream()
                .map(JobSearchResponse::from)
                .toList();
    }
}