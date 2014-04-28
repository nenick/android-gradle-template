package com.example.activities;

import com.example.test.support.UnitTestSpecification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MainActivityTest extends UnitTestSpecification {

    @InjectMocks
    private MainActivity activity = new MainActivity();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldUseCorrectLayout() throws Exception {

    }
}
