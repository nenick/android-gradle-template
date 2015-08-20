package com.example.project.specs;

import com.example.project.robolectric.RoboTestCase;
import com.example.project.tools.RoboContactListPage;
import com.example.project.tools.TestContactData;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactListSpec extends RoboTestCase {

    RoboContactListPage roboContactListPage = new RoboContactListPage();

    @Test
    public void showContactDetails() {
        givenPageHasContacts();
        roboContactListPage.list().entry(0).click();
    }

    private void givenPageHasContacts() {
        TestContactData.createRandomeContacts(3);
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isPositive();
    }
}
