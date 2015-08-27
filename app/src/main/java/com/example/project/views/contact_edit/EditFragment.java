package com.example.project.views.contact_edit;

import com.example.project.R;
import com.example.project.business.contact.CreateContactFunction;
import com.example.project.business.contact.QueryContactFunction;
import com.example.project.database.contact.ContactModel;
import com.example.project.views.common.mvp.BaseFragmentPresenter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;

@EFragment(R.layout.fragment_edit)
public class EditFragment extends BaseFragmentPresenter {

    @Bean
    EditView editView;

    @Bean
    CreateContactFunction createContactFunction;

    @Bean
    QueryContactFunction queryContactFunction;

    private EditConfirmedListener editConfirmedListener;
    private long contactId;

    public void setEditConfirmedListener(EditConfirmedListener editConfirmedListener) {
        this.editConfirmedListener = editConfirmedListener;
    }

    public void onShowContact(long contactId) {
        this.contactId = contactId;
        if(contactId != 0) {
            loadAndShowContactDetails();
        }
    }

    private void loadAndShowContactDetails() {
        ContactModel contact = queryContactFunction.apply(contactId);
        editView.setFirstName(contact.getFirstName());
        editView.setLastName(contact.getLastName());
        editView.setBirthDate(dateString(contact.getBirthdate()));
    }

    @Click(R.id.confirm)
    public void onClickConfirm() {
        String firstName = editView.getFirstName();
        String lastName = editView.getLastName();
        String birthDate = editView.getBirthDate();

        createOrUpdateContact(firstName, lastName, date(birthDate));
        editConfirmedListener.onEditConfirmed();
    }

    @Background
    void createOrUpdateContact(String firstName, String lastName, Date birthDate) {
        createContactFunction.apply(firstName, lastName, birthDate);
    }

    private String dateString(Date date) {
        if (date == null) {
            return "";
        } else {
            return date.toString();
        }
    }

    private Date date(String dateString) {
        if(StringUtils.isNotEmpty(dateString)) {
            return DateTime.parse(dateString).toDate();
        } else {
            return null;
        }
    }

}
