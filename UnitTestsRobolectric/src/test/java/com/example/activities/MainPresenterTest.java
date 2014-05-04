package com.example.activities;

import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainPresenterTest extends UnitTestSpecification {

    @Mock
    MainActivity view;

    @InjectMocks
    MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void dummy() throws Exception {

    }
}
