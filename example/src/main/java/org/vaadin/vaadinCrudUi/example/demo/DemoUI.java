package org.vaadin.vaadinCrudUi.example.demo;

import java.util.List;

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
import org.vaadin.vaadinCrudUi.example.repo.UserRepository;

import com.vaadin.data.provider.DataProvider;
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
    	User filterBean = new User();
        GridLayoutFormFactory<User, FilterOperation> filterFormFactory = new GridLayoutFormFactory<>(User.class, FilterOperation.values(), 4, 2);
        filterFormFactory.setVisibleProperties(FilterOperation.APPLY, "id", "name", "birthDate", "email", "phoneNumber", "active", "mainGroup");
        Component filterForm = filterFormFactory.buildNewForm(FilterOperation.APPLY, filterBean, true);
        
        GridCrud<User> crud = new GridCrud<User>(User.class, new HorizontalSplitCrudLayout()) {
        	@Override
        	public void refreshGrid() {
//        		grid.getDataProvider().fetch(query)
        		super.refreshGrid();
        	}
        };
        crud.setCrudListener(this);
        crud.getCrudLayout().addFilterComponent(filterForm);

        GridLayoutCrudFormFactory<User> formFactory = new GridLayoutCrudFormFactory<>(User.class, 2, 2);
        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);
        formFactory.setJpaTypeForJpaValidation(JPAService.getFactory().getMetamodel().managedType(User.class));

        formFactory.setErrorListener((e) -> {e.printStackTrace(); Notification.show("ERROR: " + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);});

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "name", "birthDate", "email", "phoneNumber", "groups", "active", "mainGroup");
        formFactory.setVisibleProperties(CrudOperation.ADD, "name", "birthDate", "email", "phoneNumber", "groups", "password", "mainGroup", "active");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "name", "birthDate", "email", "phoneNumber", "password", "groups", "active", "mainGroup");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "name", "email", "phoneNumber");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("name", "birthDate", "email", "phoneNumber", "mainGroup", "active");
        crud.getGrid().getColumn("mainGroup").setRenderer(group -> group == null ? "" : ((Group) group).getName(), new TextRenderer());
//        ((Grid.Column<User, Date>) crud.getGrid().getColumn("birthDate")).setRenderer(new DateRenderer("%1$tY-%1$tm-%1$te"));

        formFactory.setFieldType("password", PasswordField.class);
        formFactory.setFieldCreationListener("birthDate", field -> ((DateField) field).setDateFormat("yyyy-MM-dd"));

        formFactory.setFieldProvider("groups", new CheckBoxGroupProvider<Group>("Groups", GroupRepository.findAll(), Group::getName));
        formFactory.setFieldProvider("mainGroup", new ComboBoxProvider<Group>("Main Group", GroupRepository.findAll(), Group::getName));

        formFactory.setButtonCaption(CrudOperation.ADD, "Add new user");
        crud.setRowCountCaption("%d user(s) found");
        
        return crud;
    }

    @Override
    public User add(User user) {
        UserRepository.save(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getId().equals(5l)) {
            throw new RuntimeException("A simulated error has occurred");
        }
        return UserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        UserRepository.delete(user);
    }

    @Override
    public DataProvider<User, ?> getDataProvider() {
    	return DataProvider.fromCallbacks(
    			q -> {
    				List<User> list = UserRepository.findAll();
    				return list.subList(q.getOffset(), Math.min(q.getOffset()+q.getLimit(), list.size())).stream();
    			}
    			, q -> UserRepository.findAll().size()
    			);
    }

}
