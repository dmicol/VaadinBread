/**
 * 
 */
package org.vaadin.vaadinCrudUi.example.demo;

import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.FilterOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.crudui.form.impl.form.factory.GridLayoutCrudFormFactory;
import org.vaadin.crudui.form.impl.form.factory.GridLayoutFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;
import org.vaadin.vaadinCrudUi.example.repo.Group;
import org.vaadin.vaadinCrudUi.example.repo.GroupRepository;
import org.vaadin.vaadinCrudUi.example.repo.JPAService;
import org.vaadin.vaadinCrudUi.example.repo.User;
import org.vaadin.vaadinCrudUi.example.repo.UserFilter;
import org.vaadin.vaadinCrudUi.example.repo.UserRepository;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.renderers.TextRenderer;

/**
 * @author Dmitrij Colautti
 *
 */
public class ConfiguredCrud extends GridCrud<User> implements CrudListener<User> {

    private UserFilter filterBean = new UserFilter();
    
	public ConfiguredCrud() {
		super(User.class, new HorizontalSplitCrudLayout());
		
		// build filter
        GridLayoutFormFactory<UserFilter, FilterOperation> filterFormFactory = new GridLayoutFormFactory<>(UserFilter.class, FilterOperation.values(), 4, 2);
        filterFormFactory.setVisibleProperties(FilterOperation.APPLY, "name", "birthDateFrom", "birthDateTo");
        filterFormFactory.setUseBeanValidation(true);
        filterFormFactory.getConfiguration(FilterOperation.APPLY).setOperationListener(FilterOperation.APPLY, (e)-> {
        	refreshGrid();
        });
        filterFormFactory.getConfiguration(FilterOperation.APPLY).setOperationListener(FilterOperation.EMPTY, (e)-> {
        	filterBean.clear();
        	refreshGrid();
        });
        
        Component filterForm = filterFormFactory.buildNewForm(FilterOperation.APPLY, filterBean, false);
        setCrudListener(this);
        getCrudLayout().addFilterComponent(filterForm);

        // build form
        GridLayoutCrudFormFactory<User> formFactory = new GridLayoutCrudFormFactory<>(User.class, 2, 2);
        setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);
        formFactory.setJpaTypeForJpaValidation(JPAService.getFactory().getMetamodel().managedType(User.class));

        formFactory.setErrorListener((e) -> {e.printStackTrace(); Notification.show("ERROR: " + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);});

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "name", "birthDate", "email", "phoneNumber", "groups", "active", "mainGroup");
        formFactory.setVisibleProperties(CrudOperation.ADD, "name", "birthDate", "email", "phoneNumber", "groups", "password", "mainGroup", "active");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "name", "birthDate", "email", "phoneNumber", "password", "groups", "active", "mainGroup");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "name", "email", "phoneNumber");

        formFactory.setDisabledProperties("id");

        getGrid().setColumns("name", "birthDate", "email", "phoneNumber", "mainGroup", "active");
        getGrid().getColumn("mainGroup").setRenderer(group -> group == null ? "" : ((Group) group).getName(), new TextRenderer());
//        ((Grid.Column<User, Date>) crud.getGrid().getColumn("birthDate")).setRenderer(new DateRenderer("%1$tY-%1$tm-%1$te"));

        formFactory.setFieldType("password", PasswordField.class);
        formFactory.setFieldCreationListener("birthDate", field -> ((DateField) field).setDateFormat("yyyy-MM-dd"));

        formFactory.setFieldProvider("groups", new CheckBoxGroupProvider<Group>("Groups", GroupRepository.findAll(), Group::getName));
        formFactory.setFieldProvider("mainGroup", new ComboBoxProvider<Group>("Main Group", GroupRepository.findAll(), Group::getName));

        formFactory.setButtonCaption(CrudOperation.ADD, "Add new user");
        setRowCountCaption("%d user(s) found");

	};
	

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
    public ConfigurableFilterDataProvider<User, Void, UserFilter> getDataProvider() {
    	
    	CallbackDataProvider<User, UserFilter> dp = new CallbackDataProvider<User, UserFilter>(
    			q -> {
    				return UserRepository.findAll(q.getFilter().orElse(null), q.getOffset(), q.getLimit()).stream();
    			}
    			, q -> UserRepository.findAll(q.getFilter().orElse(null), q.getOffset(), q.getLimit()).size()
    			);
    	
    	ConfigurableFilterDataProvider<User, Void, UserFilter> everythingConfigurable = dp.withConfigurableFilter();
    	everythingConfigurable.setFilter(filterBean);
    	
    	return everythingConfigurable;
    }
}
