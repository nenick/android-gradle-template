package com.example.app.database.provider.address;

import com.example.app.database.provider.base.BaseModel;

import android.support.annotation.Nullable;

/**
 * Data model for the {@code address} table.
 */
public interface AddressModel extends BaseModel {

    /**
     * Get the {@code contact_id} value.
     */
    long getContactId();

    /**
     * Get the {@code street} value.
     * Can be {@code null}.
     */
    @Nullable
    String getStreet();

    /**
     * Get the {@code number} value.
     * Can be {@code null}.
     */
    @Nullable
    String getNumber();

    /**
     * Get the {@code city} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCity();

    /**
     * Get the {@code country} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCountry();

    /**
     * Get the {@code state} value.
     * Can be {@code null}.
     */
    @Nullable
    String getState();

    /**
     * Get the {@code postalcode} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPostalcode();
}
