package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.mapper;

import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.Job;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobicyJobMapperTest {

    private final JobicyJobMapper mapper = new JobicyJobMapper();

    @Test
    void mapsJobicyJobDtoToDomainJob() {
        JobicyJobDto dto = new JobicyJobDto(
                123L,
                "https://jobicy.com/jobs/123",
                "Java Backend Developer",
                "Acme Corp",
                "Argentina",
                "2026-07-24T10:15:30+00:00"
        );

        Job job = mapper.toDomain(dto);

        assertThat(job.externalId()).isEqualTo("123");
        assertThat(job.source()).isEqualTo("jobicy");
        assertThat(job.title()).isEqualTo("Java Backend Developer");
        assertThat(job.company()).isEqualTo("Acme Corp");
        assertThat(job.location()).isEqualTo("Argentina");
        assertThat(job.creationDate()).isEqualTo(Instant.parse("2026-07-24T10:15:30Z"));
    }
}