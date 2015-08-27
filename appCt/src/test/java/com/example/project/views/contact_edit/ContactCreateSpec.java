package com.example.project.views.contact_edit;

import com.example.project.robolectric.RoboTestCase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactCreateSpec extends RoboTestCase {

    RoboContactEditPage contactEditPage = new RoboContactEditPage();

    @Test
    public void testShouldCreateContact() {
        contactEditPage.startPage();
        contactEditPage.firstName().insert("My First Name");
        contactEditPage.lastName().insert("My Last Name");
        contactEditPage.birthDate().insert("1984-11-11");
        contactEditPage.confirm().click();
        assertThat(contactEditPage.editActivity.isFinishing()).isTrue();
    }
}
