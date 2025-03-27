package com.interview.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicSearchCardsModel {

    @Inject
    private String title;

    @Inject
    private String apiServlet;

    @Inject
    public List<GenreListModel> genres;

    public String getTitle() {
        return title;
    }

    public String getApiServlet() {
        return apiServlet;
    }

    public List<GenreListModel> getGenres() {

        return genres;
    }
}
