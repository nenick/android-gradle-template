package com.example.project.database.contact;

import android.database.sqlite.SQLiteException;

import com.example.project.RoboTestCase;
import com.example.project.database.address.AddressContentValues;
import com.example.project.database.address.AddressCursor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDbTest extends RoboTestCase {

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
    public void queryById() {
        givenAddressAtDatabase(addressContentValues);
        AddressCursor addressCursor = addressDb.queryById(addressId);
        assertThat(addressCursor.getCount()).isEqualTo(1);
    }

    @Test
    public void queryById_correctIds() {
        givenContactReference();
        givenAddressAtDatabase(addressContentValues);
        assertThat(contactId).isNotEqualTo(1);
        assertThat(addressId).isNotEqualTo(contactId);

        AddressCursor addressCursor = addressDb.queryById(addressId);
        addressCursor.moveToFirst();

        assertThat(addressCursor.getId()).isEqualTo(addressId);
        assertThat(addressCursor.getContactId()).isEqualTo(contactId);
    }

    private void givenContactReference() {
        contactId = contactDb.insert(new ContactContentValues().putFirstName("May"));
        addressContentValues.putContactId(contactId);
    }

    private void givenAddressAtDatabase(AddressContentValues contentValues) {
        addressId = addressDb.insert(contentValues);
    }
}