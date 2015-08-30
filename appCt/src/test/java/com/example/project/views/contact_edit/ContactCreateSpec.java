package com.example.project.views.contact_edit;

import com.example.project.database.contact.ContactDb_;
import com.example.project.database.provider.contact.ContactCursor;
import com.example.project.robolectric.RobolectricTestCase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.android.api.Assertions.assertThat;

public class ContactCreateSpec extends RobolectricTestCase {

    RoboContactEditPage contactEditPage = new RoboContactEditPage();

    @Test
    public void testShouldCreateContact() {
        givenDatabaseHasNoContacts();
        whenCreateContact();
        thenCreateContactViewIsClosed();
        thenDatabaseHasContacts(1);
    }

    private void thenDatabaseHasContacts(int expectedCount) {
        ContactCursor contactCursor = ContactDb_.getInstance_(context).queryAll();
        assertThat(contactCursor).hasCount(expectedCount);
    }

    private void thenCreateContactViewIsClosed() {
        assertThat(contactEditPage.editActivity.isFinishing()).isTrue();
    }

    private void whenCreateContact() {
        contactEditPage.startPage();
        contactEditPage.firstName().insert("My First Name");
        contactEditPage.lastName().insert("My Last Name");
        contactEditPage.birthDate().insert("1984-11-11");
        contactEditPage.confirm().click();
    }

    private void givenDatabaseHasNoContacts() {
        ContactCursor contactCursor = ContactDb_.getInstance_(context).queryAll();
        assertThat(contactCursor).hasCount(0);
    }
}
