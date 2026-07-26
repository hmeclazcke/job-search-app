package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobicyJobMapperTest {

    private static final String JOBICY_PUBLISHED_AT = "2026-07-24T10:15:30+00:00";
    private static final Instant EXPECTED_CREATION_DATE = Instant.parse("2026-07-24T10:15:30Z");

    private final JobicyJobMapper mapper = new JobicyJobMapper();

    @Test
    void mapsJobicyDtoToDomainJob() {
        JobicyJobDto dto = new JobicyJobDto(
                123L,
                "https://jobicy.com/jobs/123",
                "Java Backend Developer",
                "Acme Corp",
                "Argentina",
                JOBICY_PUBLISHED_AT
        );

        Job job = mapper.toDomain(dto);

        assertThat(job.externalId()).isEqualTo("123");
        assertThat(job.source()).isEqualTo("jobicy");
        assertThat(job.title()).isEqualTo("Java Backend Developer");
        assertThat(job.company()).isEqualTo("Acme Corp");
        assertThat(job.location()).isEqualTo("Argentina");
        assertThat(job.creationDate()).isEqualTo(EXPECTED_CREATION_DATE);
    }
}