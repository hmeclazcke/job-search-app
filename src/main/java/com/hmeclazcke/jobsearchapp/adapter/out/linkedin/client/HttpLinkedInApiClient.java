package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto.LinkedInJobDto;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.parser.LinkedInHtmlParser;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class HttpLinkedInApiClient implements JobClient<LinkedInJobDto> {

    private final RestClient restClient;
    private final LinkedInHtmlParser htmlParser;

    public HttpLinkedInApiClient(
            RestClient.Builder builder,
            @Value("${linkedin.api.base-url}") String baseUrl,
            LinkedInHtmlParser htmlParser) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();

        this.htmlParser = htmlParser;
    }

    @Override
    public List<LinkedInJobDto> search(
            JobSearchCriteria criteria) {

        try {
            String html = restClient
                    .get()
                    .uri(uriBuilder -> {

                        uriBuilder
                                .path(
                                        "/jobs-guest/jobs/api/" +
                                                "seeMoreJobPostings/search"
                                );

                        if (hasText(criteria.text())) {
                            uriBuilder.queryParam(
                                    "keywords",
                                    criteria.text()
                            );
                        }

                        if (hasText(criteria.location())) {
                            uriBuilder.queryParam(
                                    "location",
                                    criteria.location()
                            );
                        }

                        return uriBuilder.build();
                    })
                    .accept(MediaType.TEXT_HTML)
                    .retrieve()
                    .body(String.class);

            return htmlParser.parse(html);

        } catch (RestClientException exception) {
            return List.of();
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}