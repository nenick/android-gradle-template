package com.example.project.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.project.pages.EspContactListPage;
import com.example.project.pages.EspCreateContactPage;
import com.example.project.views.contact_list.ContactListActivity_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class CreateContactTest {

    @Rule
    public ActivityTestRule<ContactListActivity_> activityRule = new ActivityTestRule<>(ContactListActivity_.class);

    EspContactListPage contactListPage = new EspContactListPage(activityRule);

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
        EspCreateContactPage createContactPage = contactListPage.createContact().click();
        createContactPage.firstName().insert("My First Name");
        createContactPage.lastName().insert("My Last Name");
        createContactPage.birthDate().insert("11.11.1984");
        contactListPage =  createContactPage.confirm().click();
    }

    private void givenListHasNoContacts() {
        assertThat(contactListPage.contactList().count()).isZero();
    }
}
