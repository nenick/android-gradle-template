package com.example.project.views.contac_list;

import android.content.Intent;

import com.example.project.business.contact.CreateContactFunction_;
import com.example.project.RobolectricTestCase;
import com.example.project.testdata.TestContactData;
import com.example.project.views.contact_details.DetailActivity_;
import com.example.project.views.contact_edit.EditActivity_;

import org.junit.Test;

import java.util.Date;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactListSpec extends RobolectricTestCase {

    RoboContactListPage roboContactListPage = new RoboContactListPage();

    @Test
    public void showContactDetails() {
        givenPageHasContacts();
        whenClickListItem();
        thenDetailsAreShownInNewPage();
    }

    @Test
    public void openCreateContact() {
        givenPageWithoutContent();
        whenClickCreateContact();
        thenCreateNewContactIsShownInNewPage();
    }

    @Test
    public void refreshContactListOnNewContact() {
        givenPageWithoutContent();
        whenInsertNewContact();
        thenPageHasContact();
    }

    private void thenPageHasContact() {
        assertThat(roboContactListPage.list().count()).isPositive();
    }

    private void whenInsertNewContact() {
        CreateContactFunction_.getInstance_(context).apply("A", "B", new Date());
    }

    private void thenCreateNewContactIsShownInNewPage() {
        Intent intent = roboContactListPage.nextStartedActivity();
        assertThat(intent).hasComponent(context, EditActivity_.class);
        assertThat(intent.getLongExtra(EditActivity_.CONTACT_ID_EXTRA, 0)).isZero();
    }

    private void whenClickCreateContact() {
        roboContactListPage.createContact().click();
    }

    private void whenClickListItem() {
        roboContactListPage.list().entry(0).click();
    }

    private void thenDetailsAreShownInNewPage() {
        Intent intent = roboContactListPage.nextStartedActivity();
        assertThat(intent).hasComponent(context, DetailActivity_.class);
    }

    private void givenPageWithoutContent() {
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isZero();
    }

    private void givenPageHasContacts() {
        TestContactData.createRandomeContacts(3);
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isPositive();
    }
}
