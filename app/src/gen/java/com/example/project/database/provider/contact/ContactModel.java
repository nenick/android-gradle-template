package com.example.project.database.provider.contact;

import com.example.project.database.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code contact} table.
 */
public interface ContactModel extends BaseModel {

    /**
     * Get the {@code first_name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getFirstName();

    /**
     * Get the {@code last_name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getLastName();

    /**
     * Get the {@code birthdate} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getBirthdate();
}
