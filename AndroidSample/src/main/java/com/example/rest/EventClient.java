package com.example.rest;

import com.example.json.Event;
import com.example.json.EventList;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(rootUrl = "http://company.com/ajax/services", converters = { MappingJacksonHttpMessageConverter.class })
public interface EventClient {

    @Get("/events")
    EventList getEvents();

    @Get("/events/{id}")
    Event getEvent(long id);

    @Post("/events")
    void addEvent(Event event);

    @Put("/events")
    void updateEvent(Event event);

    @Delete("/events/{id}")
    Event deleteEventById(long id);
}