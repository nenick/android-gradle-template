package com.example.project.test;

import com.example.project.EspressoTestCase;
import com.example.project.pages.EspContactListPage;
import com.example.project.pages.EspEditContactPage;
import com.example.project.views.contact_list.ContactListActivity_;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncContactsTest extends EspressoTestCase<ContactListActivity_> {

    EspContactListPage contactListPage = new EspContactListPage();

    @Test
    public void testSyncContacts() {
        givenListHasNoContacts();
        whenSyncContacts();
        thenListHasContacts();
    }

    private void thenListHasContacts() {
        assertThat(contactListPage.contactList().count()).isPositive();
    }

    private void whenSyncContacts() {
        contactListPage.syncContacts().click();
    }

    private void givenListHasNoContacts() {
        assertThat(contactListPage.contactList().count()).isZero();
    }
}
