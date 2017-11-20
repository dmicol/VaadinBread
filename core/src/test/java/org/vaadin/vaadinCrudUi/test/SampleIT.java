package org.vaadin.vaadinCrudUi.test;

import org.junit.Test;

public class SampleIT extends AutoLogin {

    @Test
    public void INTEGRATION_TEST()
    {
        System.out.println("INTEGRATION TEST START");

        Helper helper = new Helper();

        helper.get("BUTTON");
        helper.getInput("CHECKBOX");
        helper.getInput("COMBOBOX");
        helper.getInput("TEXTFIELD");

        System.out.println("INTEGRATION TEST FINISH");
    }

}
