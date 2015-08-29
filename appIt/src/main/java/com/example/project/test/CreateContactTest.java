package com.example.project.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.project.database.ExampleSQLiteOpenHelper;
import com.example.project.database.address.AddressSelection;
import com.example.project.database.contact.ContactSelection;
import com.example.project.pages.EspContactListPage;
import com.example.project.pages.EspEditContactPage;
import com.example.project.views.contact_list.ContactListActivity_;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class CreateContactTest {

    @Rule
    public ActivityTestRule<ContactListActivity_> activityRule = new ActivityTestRule<>(ContactListActivity_.class);

    EspContactListPage contactListPage = new EspContactListPage();

    @BeforeClass
    public static void setup() {
        new ContactSelection().delete(InstrumentationRegistry.getContext().getContentResolver());
        new AddressSelection().delete(InstrumentationRegistry.getContext().getContentResolver());
    }

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
