package com.hmeclazcke.jobsearchapp.adapter.out.common;

import com.hmeclazcke.jobsearchapp.domain.Job;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericJobProviderTest {

    private static final Instant ANY_PUBLISHED_AT = Instant.EPOCH;

    @Mock
    private JobClient<TestJobDto> jobClient;

    @Mock
    private JobMapper<TestJobDto> jobMapper;

    @Test
    void mapsDtosReturnedByClientToDomainJobs() {
        JobSearchCriteria criteria = new JobSearchCriteria(
                "java",
                "Argentina",
                true
        );

        TestJobDto firstDto = new TestJobDto("1");
        TestJobDto secondDto = new TestJobDto("2");

        Job firstJob = new Job(
                "1",
                "test",
                "Java Developer",
                "Acme",
                "Argentina",
                ANY_PUBLISHED_AT
        );

        Job secondJob = new Job(
                "2",
                "test",
                "Backend Developer",
                "Globex",
                "Remote",
                ANY_PUBLISHED_AT
        );

        when(jobClient.search(criteria))
                .thenReturn(List.of(firstDto, secondDto));

        when(jobMapper.toDomain(firstDto))
                .thenReturn(firstJob);

        when(jobMapper.toDomain(secondDto))
                .thenReturn(secondJob);

        GenericJobProvider<TestJobDto> provider = new GenericJobProvider<>(
                jobClient,
                jobMapper
        );

        List<Job> jobs = provider.search(criteria);

        assertThat(jobs).containsExactly(firstJob, secondJob);

        verify(jobClient).search(criteria);
        verify(jobMapper).toDomain(firstDto);
        verify(jobMapper).toDomain(secondDto);
    }

    @Test
    void filtersOutNullDtosBeforeMapping() {
        JobSearchCriteria criteria = new JobSearchCriteria(
                "java",
                "Argentina",
                true
        );

        TestJobDto dto = new TestJobDto("1");

        Job job = new Job(
                "1",
                "test",
                "Java Developer",
                "Acme",
                "Argentina",
                ANY_PUBLISHED_AT
        );
        when(jobClient.search(criteria))
                .thenReturn(Arrays.asList(null, dto));

        when(jobMapper.toDomain(dto))
                .thenReturn(job);

        GenericJobProvider<TestJobDto> provider = new GenericJobProvider<>(
                jobClient,
                jobMapper
        );

        List<Job> jobs = provider.search(criteria);

        assertThat(jobs).containsExactly(job);

        verify(jobClient).search(criteria);
        verify(jobMapper).toDomain(dto);
    }

    private record TestJobDto(String id) {
    }
}