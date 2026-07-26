package com.hmeclazcke.jobsearchapp.adapter.in.web;

import com.hmeclazcke.jobsearchapp.application.port.in.SearchJobsUseCase;
import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobSearchControllerTest {

    private static final Instant ANY_CREATION_DATE = Instant.EPOCH;

    @Mock
    private SearchJobsUseCase searchJobsUseCase;

    @Test
    void mapsRequestToCriteriaAndReturnsResponses() {
        JobSearchRequest request = new JobSearchRequest(
                "java",
                "Argentina",
                true
        );

        JobSearchCriteria expectedCriteria = new JobSearchCriteria(
                "java",
                "Argentina",
                true
        );

        Job job = new Job(
                "1",
                "jobicy",
                "Java Developer",
                "Acme",
                "Argentina",
                ANY_CREATION_DATE
        );

        when(searchJobsUseCase.search(expectedCriteria))
                .thenReturn(List.of(job));

        JobSearchController controller = new JobSearchController(searchJobsUseCase);

        List<JobSearchResponse> responses = controller.search(request);

        assertThat(responses).containsExactly(
                new JobSearchResponse(
                        "1",
                        "jobicy",
                        "Java Developer",
                        "Acme",
                        "Argentina",
                        ANY_CREATION_DATE
                )
        );

        verify(searchJobsUseCase).search(expectedCriteria);
    }
}