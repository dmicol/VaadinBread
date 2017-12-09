package org.vaadin.bread.test;

import org.junit.After;
import org.junit.Before;

public class AutoLogin {

    @Before
    public void login()
    {
        System.out.println("AUTO LOGIN");
    }

    @After
    public void logout()
    {
        System.out.println("AUTO LOGOUT");
    }
}
