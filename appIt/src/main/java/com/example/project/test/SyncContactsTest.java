package com.example.project.test;

import com.example.project.EspressoTestCase;
import com.example.project.pages.EspContactListPage;
import com.example.project.pages.EspEditContactPage;
import com.example.project.views.contact_list.ContactListActivity_;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncContactsTest extends EspressoTestCase<ContactListActivity_> {

    EspContactListPage contactListPage = new EspContactListPage();

    @Test
    public void testSyncContacts() {
        givenListHasNoContacts();
        whenSyncContacts();

        onView(withText("Sync done")).inRoot(withDecorView(not(is(activityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

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
