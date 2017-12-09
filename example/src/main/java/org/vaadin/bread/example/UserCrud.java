/**
 * 
 */
package org.vaadin.bread.example;

import org.vaadin.bread.example.base.repo.GroupRepository;
import org.vaadin.bread.example.base.repo.JPAService;
import org.vaadin.bread.example.base.repo.UserRepository;
import org.vaadin.bread.example.model.Group;
import org.vaadin.bread.example.model.User;
import org.vaadin.bread.example.model.UserFilter;
import org.vaadin.bread.ui.crud.CrudListener;
import org.vaadin.bread.ui.crud.CrudOperation;
import org.vaadin.bread.ui.crud.FilterOperation;
import org.vaadin.bread.ui.crud.impl.GridCrud;
import org.vaadin.bread.ui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.bread.ui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.bread.ui.form.impl.form.factory.GridLayoutCrudFormFactory;
import org.vaadin.bread.ui.form.impl.form.factory.GridLayoutFormFactory;
import org.vaadin.bread.ui.layout.impl.HorizontalSplitCrudLayout;

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
public class UserCrud extends GridCrud<User> implements CrudListener<User> {

    private UserFilter filterBean = new UserFilter();
    
	public UserCrud() {
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

        formFactory.setErrorListener((op, obj, e) -> {e.printStackTrace(); Notification.show("ERROR: " + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);});

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
