package com.hmeclazcke.jobsearchapp.application.service;

import com.hmeclazcke.jobsearchapp.application.port.out.JobProvider;
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
class JobSearchServiceTest {

    private static final Instant ANY_PUBLISHED_AT = Instant.EPOCH;

    @Mock
    private JobProvider firstJobProvider;

    @Mock
    private JobProvider secondJobProvider;

    @Test
    void combinesResultsFromAllProviders() {
        JobSearchCriteria criteria = new JobSearchCriteria(
                "java",
                "Argentina",
                true
        );

        Job firstJob = new Job(
                "1",
                "jobicy",
                "Java Developer",
                "Acme",
                "Argentina",
                ANY_PUBLISHED_AT
        );

        Job secondJob = new Job(
                "2",
                "linkedin",
                "Backend Developer",
                "Globex",
                "Remote",
                ANY_PUBLISHED_AT
        );

        when(firstJobProvider.search(criteria))
                .thenReturn(List.of(firstJob));

        when(secondJobProvider.search(criteria))
                .thenReturn(List.of(secondJob));

        JobSearchService service = new JobSearchService(
                List.of(firstJobProvider, secondJobProvider)
        );

        List<Job> jobs = service.search(criteria);

        assertThat(jobs).containsExactly(firstJob, secondJob);

        verify(firstJobProvider).search(criteria);
        verify(secondJobProvider).search(criteria);
    }

    @Test
    void keepsResultsWhenAProviderReturnsEmptyList() {
        JobSearchCriteria criteria = new JobSearchCriteria(
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
                ANY_PUBLISHED_AT
        );

        when(firstJobProvider.search(criteria))
                .thenReturn(List.of());

        when(secondJobProvider.search(criteria))
                .thenReturn(List.of(job));

        JobSearchService service = new JobSearchService(
                List.of(firstJobProvider, secondJobProvider)
        );

        List<Job> jobs = service.search(criteria);

        assertThat(jobs).containsExactly(job);

        verify(firstJobProvider).search(criteria);
        verify(secondJobProvider).search(criteria);
    }
}