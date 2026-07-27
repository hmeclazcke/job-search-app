package com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.client;

import com.hmeclazcke.jobsearchapp.adapter.out.common.JobClient;
import com.hmeclazcke.jobsearchapp.adapter.out.internaljobs.document.InternalJobDocument;
import com.hmeclazcke.jobsearchapp.domain.JobSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MongoInternalJobClient implements JobClient<InternalJobDocument> {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<InternalJobDocument> search(JobSearchCriteria criteria) {
        Query query = new Query();
        List<Criteria> filters = new ArrayList<>();

        if (hasText(criteria.text())) {
            filters.add(Criteria.where("title")
                    .regex(Pattern.quote(criteria.text().trim()), "i"));
        }

        if (hasText(criteria.location())) {
            filters.add(Criteria.where("location")
                    .regex(Pattern.quote(criteria.location().trim()), "i"));
        }

        if (Boolean.TRUE.equals(criteria.remote())) {
            filters.add(Criteria.where("location").regex("remote", "i"));
        }

        if (!filters.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(filters.toArray(Criteria[]::new)));
        }

        try {
            return mongoTemplate.find(query, InternalJobDocument.class);
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
