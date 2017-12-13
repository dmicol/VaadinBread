package org.vaadin.bread.example.base;

import org.vaadin.bread.example.GroupCrud;
import org.vaadin.bread.example.UserCrud;
import org.vaadin.bread.example.base.repo.JPAService;
import org.vaadin.bread.example.model.Group;
import org.vaadin.bread.example.model.User;
import org.vaadin.bread.ui.crud.Crud;
import org.vaadin.jetty.VaadinJettyServer;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Dmitrij Colautti
 */
public class BaseUI extends UI {

    public static void main(String[] args) throws Exception {
        JPAService.init();
        VaadinJettyServer server = new VaadinJettyServer(8080, BaseUI.class);
        server.start();
    }

    private TabSheet tabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        setContent(tabSheet);

        addCrud(getUserCrud(), "User");
        addCrud(getGroupCrud(), "Group");
    }

    private void addCrud(Crud<?> crud, String caption) {
        VerticalLayout layout = new VerticalLayout(crud);
        layout.setSizeFull();
        layout.setMargin(true);
        tabSheet.addTab(layout, caption);
    }

	private Crud<User> getUserCrud() {
    	
    	return new UserCrud();
    }

	private Crud<Group> getGroupCrud() {
    	
    	return new GroupCrud();
    }

}
