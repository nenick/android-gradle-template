package com.example.project.specs;

import com.example.project.RoboTestCase;
import com.example.project.tools.RoboContactListPage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactListSpec extends RoboTestCase {

    RoboContactListPage roboContactListPage = new RoboContactListPage();

    @Test
    public void showContactDetails() {
        roboContactListPage.startPage();
        assertThat(roboContactListPage.list().count()).isPositive();
        roboContactListPage.list().entry(0).click();
    }
}
