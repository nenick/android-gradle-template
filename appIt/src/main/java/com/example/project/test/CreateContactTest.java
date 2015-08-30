package com.example.project.test;

import com.example.project.EspressoTestCase;
import com.example.project.pages.EspContactListPage;
import com.example.project.pages.EspEditContactPage;
import com.example.project.views.contact_list.ContactListActivity_;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateContactTest extends EspressoTestCase<ContactListActivity_> {

    EspContactListPage contactListPage = new EspContactListPage();

    @Test
    public void testCreateNewContact() {
        givenListHasNoContacts();
        whenAddContact();
        thenListHasContacts();
    }

    private void thenListHasContacts() {
        assertThat(contactListPage.contactList().count()).isPositive();
    }

    private void whenAddContact() {
        EspEditContactPage editContactPage = contactListPage.createContact().click();
        editContactPage.firstName().insert("My First Name");
        editContactPage.lastName().insert("My Last Name");
        editContactPage.birthDate().insert("1984-11-11");
        contactListPage =  editContactPage.confirm().click();
    }

    private void givenListHasNoContacts() {
        assertThat(contactListPage.contactList().count()).isZero();
    }
}
