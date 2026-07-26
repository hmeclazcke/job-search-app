package com.hmeclazcke.jobsearchapp.adapter.out.jobicy.client;

import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.dto.JobicyJobDto;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class HttpJobicyApiClientTest {

    private static final String BASE_URL = "https://jobicy.test";
    private static final String REMOTE_JOBS_PATH = "/api/v2/remote-jobs";
    private static final String JOBICY_PUBLISHED_AT = Instant.EPOCH.toString();

    @Test
    void readsJobsFromSuccessfulResponse() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();

        HttpJobicyApiClient client = new HttpJobicyApiClient(builder, BASE_URL);

        server.expect(requestTo(BASE_URL + REMOTE_JOBS_PATH))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                          "jobs": [
                            {
                              "id": 123,
                              "url": "https://jobicy.com/jobs/123",
                              "jobTitle": "Java Backend Developer",
                              "companyName": "Acme Corp",
                              "jobGeo": "Argentina",
                              "pubDate": "%s"
                            }
                          ]
                        }
                        """.formatted(JOBICY_PUBLISHED_AT), MediaType.APPLICATION_JSON));

        JobSearchCriteria criteria = new JobSearchCriteria(
                null,
                null,
                true
        );

        List<JobicyJobDto> jobs = client.search(criteria);

        assertThat(jobs).hasSize(1);

        JobicyJobDto job = jobs.getFirst();

        assertThat(job.id()).isEqualTo(123L);
        assertThat(job.url()).isEqualTo("https://jobicy.com/jobs/123");
        assertThat(job.jobTitle()).isEqualTo("Java Backend Developer");
        assertThat(job.companyName()).isEqualTo("Acme Corp");
        assertThat(job.jobGeo()).isEqualTo("Argentina");
        assertThat(job.pubDate()).isEqualTo(JOBICY_PUBLISHED_AT);

        server.verify();
    }

    @Test
    void sendsTextAndLocationFiltersToJobicy() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();

        HttpJobicyApiClient client = new HttpJobicyApiClient(builder, BASE_URL);

        server.expect(requestTo(BASE_URL + REMOTE_JOBS_PATH + "?tag=java&geo=buenos-aires"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                          "jobs": []
                        }
                        """, MediaType.APPLICATION_JSON));

        JobSearchCriteria criteria = new JobSearchCriteria(
                "java",
                "Buenos Aires",
                true
        );

        List<JobicyJobDto> jobs = client.search(criteria);

        assertThat(jobs).isEmpty();

        server.verify();
    }

    @Test
    void doesNotCallJobicyForNonRemoteSearches() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();

        HttpJobicyApiClient client = new HttpJobicyApiClient(builder, BASE_URL);

        JobSearchCriteria criteria = new JobSearchCriteria(
                "java",
                "Argentina",
                false
        );

        List<JobicyJobDto> jobs = client.search(criteria);

        assertThat(jobs).isEmpty();

        server.verify();
    }
}