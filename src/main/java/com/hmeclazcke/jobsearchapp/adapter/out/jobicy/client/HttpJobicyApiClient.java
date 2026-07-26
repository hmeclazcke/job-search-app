package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Locale;

@Component
public class HttpJobicyApiClient implements JobClient<JobicyJobDto> {

    private final RestClient restClient;

    public HttpJobicyApiClient(
            RestClient.Builder builder,
            @Value("${jobicy.api.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }


    @Override
    public List<JobicyJobDto> search(JobSearchCriteria criteria) {

        /*
         * Jobicy only provides remote jobs.
         * If the user explicitly requests non-remote jobs,
         * this provider cannot return compatible results.
         */
        if (Boolean.FALSE.equals(criteria.remote())) {
            return List.of();
        }

        JobicyResponse response = restClient
                .get()
                .uri(uriBuilder -> {
                    uriBuilder
                            .path("/api/v2/remote-jobs");

                    if (hasText(criteria.text())) {
                        uriBuilder.queryParam("tag", criteria.text());
                    }

                    if (hasText(criteria.location())) {
                        uriBuilder.queryParam(
                                "geo",
                                normalizeLocation(criteria.location())
                        );
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, httpResponse) -> {
                            // Jobicy returns 404 when no jobs match the filters.
                            // Do not throw an exception; deserialize the response normally.
                        }
                )
                .body(JobicyResponse.class);

        if (response == null || response.jobs() == null) {
            return List.of();
        }

        return response.jobs();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String normalizeLocation(String location) {
        return location
                .trim()
                .toLowerCase(Locale.ROOT)
                .replace(" ", "-");
    }

    // Jackson maps the Jobicy API "jobs" array to this DTO field.
    private record JobicyResponse(
            List<JobicyJobDto> jobs
    ) {
    }
}