package com.example.project.views.contac_list;

import android.content.Intent;

import com.example.project.RobolectricTestCase;
import com.example.project.business.contact.CreateContactFunction_;
import com.example.project.testdata.TestContactData;
import com.example.project.views.contact_details.DetailActivity_;
import com.example.project.views.contact_edit.EditActivity_;
import com.github.tomakehurst.wiremock.http.Fault;

import org.junit.Test;
import org.robolectric.shadows.ShadowToast;

import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
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
        givenPageWithoutContacts();
        whenClickCreateContact();
        thenCreateNewContactIsShownInNewPage();
    }

    @Test
    public void refreshContactListOnNewContact() {
        givenPageWithoutContacts();
        whenInsertNewContact();
        thenPageHasContacts();
    }

    @Test
    public void syncContacts() {
        givenPageWithoutContacts();
        whenClickSyncContact();
        thenPageHasContacts();
        thenToastWasShown("Sync done");
    }

    @Test
    public void syncContactsFail() {
        givenContactsRequestAnswerWithError(500);
        givenPageWithoutContacts();
        whenClickSyncContact();
        thenToastWasShown("Sync failed: 500 Internal Server Error");
    }

    @Test
    public void syncContactsWithConnectionLost() {
        givenContactsRequestAnswerWithFault(Fault.RANDOM_DATA_THEN_CLOSE);
        givenPageWithoutContacts();
        whenClickSyncContact();
        thenToastWasShown("Sync failed: Unknown status code [-1] null");
    }

    private void givenContactsRequestAnswerWithFault(Fault fault) {
        stubFor(get(urlEqualTo("/contacts")).willReturn(aResponse().withFault(fault)));
    }

    private void givenContactsRequestAnswerWithError(int statusCode) {
        stubFor(get(urlEqualTo("/contacts")).willReturn(aResponse().withStatus(statusCode)));
    }

    private void thenToastWasShown(String expectedMessage) {
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(expectedMessage);
    }

    private void whenClickSyncContact() {
        roboContactListPage.syncContacts().click();
    }

    private void thenPageHasContacts() {
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

    private void givenPageWithoutContacts() {
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isZero();
    }

    private void givenPageHasContacts() {
        TestContactData.createRandomeContacts(3);
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isPositive();
    }
}
