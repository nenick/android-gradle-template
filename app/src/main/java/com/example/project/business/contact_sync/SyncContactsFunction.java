package com.example.project.business.contact_sync;

import com.example.project.business.contact.CreateContactFunction;
import com.example.project.business.contact.QueryContactFunction;
import com.example.project.database.provider.contact.ContactModel;
import com.example.project.network.contact.ContactRestClient;
import com.example.project.network.json.ContactJson;
import com.example.project.network.json.ContactListJson;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@EBean
public class SyncContactsFunction {

    @RestService
    ContactRestClient contactRestClient;

    @Bean
    QueryContactFunction queryContactFunction;

    @Bean
    CreateContactFunction createContactFunction;

    public static class Result {
        public boolean successful;

        public Result(boolean successful) {
            this.successful = successful;
        }
    }

    public Result apply() {
        ResponseEntity<ContactListJson> contacts = contactRestClient.getContacts();

        if (contacts.getStatusCode() == HttpStatus.OK) {
            syncContactsToDatabase(contacts.getBody());
            return new Result(true);
        } else {
            return new Result(false);
        }
    }

    private void syncContactsToDatabase(ContactListJson contacts) {
        for (ContactJson contact : contacts.getContacts()) {
            ContactModel contactModel = queryContactFunction.applyByUid(contact.getId());
            if(contactModel == null) {
                String uid = contact.getId();
                String firstName = contact.getFirstName();
                String lastName = contact.getLastName();
                Date birthDate = DateTime.parse(contact.getBirthDate()).toDate();
                createContactFunction.apply(uid, firstName, lastName, birthDate);
            }
        }
    }
}
