package com.example.project.database.contact;

import android.database.sqlite.SQLiteException;

import com.example.project.RoboTestCase;
import com.example.project.database.provider.address.AddressContentValues;
import com.example.project.database.provider.contact.ContactContentValues;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDbInsertTest extends RoboTestCase {

    ContactDb contactDb;
    AddressDb addressDb;
    long contactId;
    long addressId;

    AddressContentValues addressContentValues = new AddressContentValues()
            .putStreet("My Street")
            .putNumber("42a")
            .putCity("Berlin")
            .putCountry("Berlin")
            .putState("Germany")
            .putPostalcode("12345");

    @Before
    public void setup() {
        addressDb = AddressDb_.getInstance_(context);
        contactDb = ContactDb_.getInstance_(context);
        givenContactReference();
    }

    @Test
    public void insert() {
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test(expected = SQLiteException.class)
    public void insert_contactId_isMandatory() {
        addressContentValues.putContactId(0);
        whenAddressIsInserted(addressContentValues);
    }

    @Test(expected = SQLiteException.class)
    public void insert_contactId_mustExist() {
        long notExistingContact = 42;
        assertThat(notExistingContact).isNotEqualTo(contactId);
        addressContentValues.putContactId(notExistingContact);
        whenAddressIsInserted(addressContentValues);
    }

    @Test
    public void insert_street_isNullable() {
        addressContentValues.putStreet(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_number_isNullable() {
        addressContentValues.putNumber(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_city_isNullable() {
        addressContentValues.putCity(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_country_isNullable() {
        addressContentValues.putCountry(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_state_isNullable() {
        addressContentValues.putState(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    @Test
    public void insert_postalcode_isNullable() {
        addressContentValues.putPostalcode(null);
        whenAddressIsInserted(addressContentValues);
        thenInsertWasSuccessful();
    }

    private void thenInsertWasSuccessful() {
        assertThat(addressId).isPositive();
    }

    private void givenContactReference() {
        contactId = contactDb.insert(new ContactContentValues().putFirstName("May"));
        addressContentValues.putContactId(contactId);
    }

    private void whenAddressIsInserted(AddressContentValues contentValues) {
        givenAddressAtDatabase(contentValues);
    }

    private void givenAddressAtDatabase(AddressContentValues contentValues) {
        addressId = addressDb.insert(contentValues);
    }
}