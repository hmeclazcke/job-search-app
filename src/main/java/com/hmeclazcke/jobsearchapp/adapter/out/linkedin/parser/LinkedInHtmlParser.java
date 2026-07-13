package com.hmeclazcke.jobsearchapp.adapter.out.linkedin.parser;

import com.hmeclazcke.jobsearchapp.adapter.out.linkedin.dto.LinkedInJobDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Component
public class LinkedInHtmlParser {

    public List<LinkedInJobDto> parse(String html) {

        if (!hasText(html)) {
            return List.of();
        }

        try {
            Document document = Jsoup.parseBodyFragment(html);

            return document
                    .select("div.job-search-card")
                    .stream()
                    .map(this::parseCard)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private LinkedInJobDto parseCard(Element card) {

        try {
            return new LinkedInJobDto(
                    extractJobId(card),
                    extractText(card, ".base-search-card__title"),
                    extractText(card, ".base-search-card__subtitle"),
                    extractText(card, ".job-search-card__location"),
                    extractDate(card)
            );

        } catch (RuntimeException exception) {
            return null;
        }
    }

    private String extractJobId(Element card) {

        String urn = nullIfBlank(
                card.attr("data-entity-urn")
        );

        if (urn == null) {
            return null;
        }

        int lastColonPosition = urn.lastIndexOf(':');

        if (lastColonPosition == -1
                || lastColonPosition == urn.length() - 1) {
            return null;
        }

        return urn.substring(lastColonPosition + 1);
    }

    private String extractText(
            Element card,
            String cssSelector) {

        Element element = card.selectFirst(cssSelector);

        if (element == null) {
            return null;
        }

        return nullIfBlank(element.text());
    }

    private LocalDate extractDate(Element card) {

        Element timeElement = card.selectFirst(
                "time.job-search-card__listdate"
        );

        if (timeElement == null) {
            return null;
        }

        String date = nullIfBlank(
                timeElement.attr("datetime")
        );

        if (date == null) {
            return null;
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    private String nullIfBlank(String value) {

        if (!hasText(value)) {
            return null;
        }

        return value.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}