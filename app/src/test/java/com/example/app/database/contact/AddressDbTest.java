package com.example.app.database.contact;

import com.example.app.RoboTestCase;
import com.example.app.database.AddressDb;
import com.example.app.database.AddressDb_;
import com.example.app.database.ContactDb;
import com.example.app.database.ContactDb_;
import com.example.app.database.provider.address.AddressContentValues;
import com.example.app.database.provider.address.AddressCursor;
import com.example.app.database.provider.contact.ContactContentValues;

import org.junit.Before;
import org.junit.Test;

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