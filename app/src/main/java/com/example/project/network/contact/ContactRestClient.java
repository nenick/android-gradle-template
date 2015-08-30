package com.example.project.network.contact;

import com.example.project.network.json.ContactListJson;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
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
    ResponseEntity<Void> updateContact(String id) throws RestClientException;

    @Delete("/contacts/{id}")
    ResponseEntity<Void> deleteContact(String id) throws RestClientException;
}
