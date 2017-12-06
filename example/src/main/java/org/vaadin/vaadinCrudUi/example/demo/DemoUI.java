package org.vaadin.vaadinCrudUi.example.demo;

import org.vaadin.crudui.crud.Crud;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.FilterOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.form.impl.form.factory.GridLayoutCrudFormFactory;
import org.vaadin.crudui.form.impl.form.factory.GridLayoutFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;
import org.vaadin.jetty.VaadinJettyServer;
import org.vaadin.vaadinCrudUi.example.repo.Group;
import org.vaadin.vaadinCrudUi.example.repo.GroupRepository;
import org.vaadin.vaadinCrudUi.example.repo.JPAService;
import org.vaadin.vaadinCrudUi.example.repo.User;
import org.vaadin.vaadinCrudUi.example.repo.UserFilter;
import org.vaadin.vaadinCrudUi.example.repo.UserRepository;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;

/**
 * @author Alejandro Duarte
 */
public class DemoUI extends UI implements CrudListener<User> {

    public static void main(String[] args) throws Exception {
        JPAService.init();
        VaadinJettyServer server = new VaadinJettyServer(8080, DemoUI.class);
        server.start();
    }

    private TabSheet tabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        setContent(tabSheet);

        addCrud(getDefaultCrud(), "Default");
        addCrud(getDefaultCrudWithFixes(), "Default (with fixes)");
        addCrud(getConfiguredCrud(), "Configured");
        tabSheet.setSelectedTab(2);
    }

    private void addCrud(Crud<User> crud, String caption) {
        VerticalLayout layout = new VerticalLayout(crud);
        layout.setSizeFull();
        layout.setMargin(true);
        tabSheet.addTab(layout, caption);
    }

    private Crud<User> getDefaultCrud() {
    	GridCrud<User> gridCrud = new GridCrud<>(User.class);
    	gridCrud.setCrudListener(this);
        return gridCrud;
    }

    private Crud<User> getDefaultCrudWithFixes() {
        GridCrud<User> crud = new GridCrud<>(User.class);
        crud.setCrudListener(this);
        crud.getCrudFormFactory().setFieldProvider("groups", new CheckBoxGroupProvider<>(GroupRepository.findAll()));
        crud.getCrudFormFactory().setFieldProvider("mainGroup", new ComboBoxProvider<>(GroupRepository.findAll()));

        return crud;
    }

	private Crud<User> getConfiguredCrud() {
    	
    	return new ConfiguredCrud();
    }

    @Override
    public User add(User user) {
        UserRepository.save(user);
        return user;
    }

    @Override
    public User update(User user) {
        return UserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        UserRepository.delete(user);
    }

    @Override
    public CallbackDataProvider<User, UserFilter> getDataProvider() {
    	
    	return new CallbackDataProvider<User, UserFilter>(
    			q -> {
    				return UserRepository.findAll().stream();
    			}
    			, q -> UserRepository.findAll().size()
    			);
    }

}
