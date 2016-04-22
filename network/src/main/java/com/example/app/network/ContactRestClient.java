package com.example.app.network;

import com.example.app.network.json.ContactListJson;


import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

@Rest(converters = {MappingJackson2HttpMessageConverter.class}, interceptors = {RootUrlInterceptor.class})
public interface ContactRestClient {

    @Get("/contacts")
    ResponseEntity<ContactListJson> getContacts() throws RestClientException;

    @Post("/contacts")
    ResponseEntity<Void> createContact() throws RestClientException;

    @Put("/contacts/{id}")
    ResponseEntity<Void> updateContact(@Path String id) throws RestClientException;

    @Delete("/contacts/{id}")
    ResponseEntity<Void> deleteContact(@Path String id) throws RestClientException;
}
