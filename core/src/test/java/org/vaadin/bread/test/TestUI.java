package org.vaadin.bread.test;

import org.vaadin.bread.core.ui.bread.Bread;
import org.vaadin.bread.core.ui.bread.BreadListener;
import org.vaadin.bread.core.ui.bread.impl.GridBread;
import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.bread.core.ui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.bread.core.ui.form.impl.form.factory.GridLayoutFormFactory;
import org.vaadin.bread.core.ui.layout.impl.HorizontalSplitBreadLayout;
import org.vaadin.bread.test.repo.Group;
import org.vaadin.bread.test.repo.GroupRepository;
import org.vaadin.bread.test.repo.JPAService;
import org.vaadin.bread.test.repo.User;
import org.vaadin.bread.test.repo.UserRepository;
import org.vaadin.jetty.VaadinJettyServer;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.VaadinRequest;
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
public class TestUI extends UI implements BreadListener<User> {

    public static void main(String[] args) throws Exception {
        JPAService.init();
        VaadinJettyServer server = new VaadinJettyServer(8080, TestUI.class);
        server.start();
    }

    private TabSheet tabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        setContent(tabSheet);

        addBread(getDefaultBread(), "Default");
        addBread(getDefaultBreadWithFixes(), "Default (with fixes)");
        addBread(getConfiguredBread(), "Configured");
    }

    private void addBread(Bread crud, String caption) {
        VerticalLayout layout = new VerticalLayout(crud);
        layout.setSizeFull();
        layout.setMargin(true);
        tabSheet.addTab(layout, caption);
    }

    private Bread getDefaultBread() {
    	GridBread<User> gridBread = new GridBread<>(User.class);
    	gridBread.setBreadListener(this);
        return gridBread;
    }

    private Bread getDefaultBreadWithFixes() {
        GridBread<User> bread = new GridBread<>(User.class);
        bread.setBreadListener(this);
        bread.getCrudFormFactory().setFieldProvider("groups", new CheckBoxGroupProvider<>(GroupRepository.findAll()));
        bread.getCrudFormFactory().setFieldProvider("mainGroup", new ComboBoxProvider<>(GroupRepository.findAll()));

        return bread;
    }

    private Bread getConfiguredBread() {
        GridBread<User> crud = new GridBread<>(User.class, new HorizontalSplitBreadLayout());
        crud.setBreadListener(this);

        GridLayoutFormFactory<User> formFactory = new GridLayoutFormFactory<>(User.class, CrudOperation.values(), 2, 2);
        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);
        formFactory.setJpaTypeForJpaValidation(JPAService.getFactory().getMetamodel().managedType(User.class));

        formFactory.setErrorListener((opm, opa, obj, e) -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

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

        formFactory.setFieldProvider("groups", new CheckBoxGroupProvider<>("Groups", GroupRepository.findAll(), Group::getName));
        formFactory.setFieldProvider("mainGroup", new ComboBoxProvider<>("Main Group", GroupRepository.findAll(), Group::getName));

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
    			q -> UserRepository.findAll().subList(q.getOffset(), q.getOffset()+q.getLimit()).stream()
    			, q -> UserRepository.findAll().size()
    			);
    }

}
