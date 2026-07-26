package com.hmeclazcke.jobsearchapp.config;

import com.hmeclazcke.jobsearchapp.adapter.out.common.GenericJobProvider;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.client.HttpInternalJobApiClient;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.mapper.InternalJobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.client.HttpJobicyApiClient;
import com.hmeclazcke.jobsearchapp.adapter.out.jobicy.mapper.JobicyJobMapper;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.client.HttpLinkedInApiClient;
import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.mapper.LinkedInJobMapper;
import com.hmeclazcke.jobsearchapp.application.port.out.JobProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    //////////////////////////////////////////////////////////////////////////////////////
    /* Registers each job provider as a JobProvider bean by combining its client and mapper.
     * Spring automatically collects all JobProvider beans into a List<JobProvider>
     * and injects that list into JobSearchService.
     */

    @Bean
    public JobProvider jobicyJobProvider(
            HttpJobicyApiClient client,
            JobicyJobMapper mapper) {
        return new GenericJobProvider<>(client, mapper);
    }

    @Bean
    public JobProvider linkedinJobProvider(
            HttpLinkedInApiClient client,
            LinkedInJobMapper mapper) {
        return new GenericJobProvider<>(client, mapper);
    }

    @Bean
    public JobProvider internalJobProvider(
            HttpInternalJobApiClient client,
            InternalJobMapper mapper) {
        return new GenericJobProvider<>(client, mapper);
    }
    //////////////////////////////////////////////////////////////////////////////////////


}