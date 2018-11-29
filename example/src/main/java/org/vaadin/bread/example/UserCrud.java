/**
 * 
 */
package org.vaadin.bread.example;

import java.util.ArrayList;
import java.util.Arrays;

import org.vaadin.bread.core.ui.bread.ItemListListener;
import org.vaadin.bread.core.ui.bread.impl.GridBread;
import org.vaadin.bread.core.ui.filter.FilterComponent;
import org.vaadin.bread.core.ui.form.CrudOperation;
import org.vaadin.bread.core.ui.form.FilterOperation;
import org.vaadin.bread.core.ui.form.OperationAction;
import org.vaadin.bread.core.ui.form.impl.field.provider.ComboBoxProvider;
import org.vaadin.bread.core.ui.form.impl.form.factory.FormFactoryBuilder;
import org.vaadin.bread.core.ui.form.impl.form.factory.GridLayoutFormFactory;
import org.vaadin.bread.core.ui.form.impl.form.factory.ReflectionFilterFormFactory;
import org.vaadin.bread.core.ui.layout.impl.WindowBasedBreadLayout;
import org.vaadin.bread.example.base.repo.GroupRepository;
import org.vaadin.bread.example.base.repo.JPAService;
import org.vaadin.bread.example.base.repo.UserRepository;
import org.vaadin.bread.example.model.Group;
import org.vaadin.bread.example.model.User;
import org.vaadin.bread.example.model.UserFilter;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;

/**
 * @author Dmitrij Colautti
 *
 */
@SuppressWarnings("serial")
public class UserCrud extends GridBread<User> implements ItemListListener<User> {

    private UserFilter filterBean = new UserFilter();
	private Component reflectionFilterForm;
    
	public UserCrud() {
		super(User.class, new WindowBasedBreadLayout());
		
		getBreadLayout().setCaption("UTENTE");
		
		// build filter
        GridLayoutFormFactory<UserFilter> filterFormFactory = 
        		new FormFactoryBuilder<UserFilter, GridLayoutFormFactory<UserFilter>>()
        		.gridLayoutFilter(UserFilter.class, 4, 2)
        		.build();
        
        filterFormFactory.getConfiguration(FilterOperation.APPLY).setOperationActionListener(FilterOperation.APPLY, (e)-> {
        	refreshGrid();
        	getBreadLayout().setFiltersVisible(false);
        });
        filterFormFactory.getConfiguration(FilterOperation.APPLY).setOperationActionListener(FilterOperation.EMPTY, (e)-> {
        	filterBean.clear();
        	filterFormFactory.getBinder().readBean(filterBean);
        });
        filterFormFactory.buildSensitiveDefaults();

        Component filterForm = filterFormFactory.buildNewForm(FilterOperation.APPLY, filterBean, false);
        setItemListListener(this);
        getBreadLayout().addFilterComponent("filtro",filterForm);
        
        //generic filter
        ReflectionFilterFormFactory rfff = new ReflectionFilterFormFactory();
        rfff.getConf().setOperationActionListener(FilterOperation.APPLY, (e)-> {
        	setDataProvider(getReflectionFilterDataProvider());
        	refreshGrid();
        	getBreadLayout().setFiltersVisible(false);
        });
        rfff.getConf().setOperationActionListener(FilterOperation.EMPTY, (e)-> {
        	setDataProvider(getDataProvider());
        	filterBean.clear();
        	filterFormFactory.getBinder().readBean(filterBean);
        });
        reflectionFilterForm = rfff.buildNewForm(User.class);
        getBreadLayout().addFilterComponent("filtro2", reflectionFilterForm);    	

        // build form
        GridLayoutFormFactory<User> formFactory = new FormFactoryBuilder<User, GridLayoutFormFactory<User>>()
        		.gridLayoutBread(User.class, 2, 2).build();
        setCrudFormFactory(formFactory);

        formFactory.setJpaTypeForJpaValidation(JPAService.getFactory().getMetamodel().managedType(User.class));
        formFactory.buildSensitiveDefaults();
        
        formFactory.setErrorListener((opm, opa, obj, e) -> {e.printStackTrace(); Notification.show("ERROR: " + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);});
        formFactory.setVisibleProperties(CrudOperation.DELETE, "name", "email", "phoneNumber");

        formFactory.setFieldType("password", PasswordField.class);
        formFactory.setFieldCreationListener("birthDate", field -> ((DateField) field).setDateFormat("yyyy-MM-dd"));
        formFactory.setFieldProvider("mainGroup", new ComboBoxProvider<Group>("Main Group", GroupRepository.findAll(), Group::getName));

        formFactory.setButtonCaption(CrudOperation.ADD, "Add new user");

        
        getGridItemList().getGrid().setColumns("name", "birthDate", "email", "phoneNumber", "mainGroup", "active");
        getGridItemList().getGrid().getColumn("mainGroup").setRenderer(group -> group == null ? "" : ((Group) group).getName(), new TextRenderer());
//        ((Grid.Column<User, Date>) crud.getGrid().getColumn("birthDate")).setRenderer(new DateRenderer("%1$tY-%1$tm-%1$te"));
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
    			, q -> 
    				UserRepository.findAll(q.getFilter().orElse(null), q.getOffset(), q.getLimit()).size()
    			);
    	
    	ConfigurableFilterDataProvider<User, Void, UserFilter> everythingConfigurable = dp.withConfigurableFilter();
    	everythingConfigurable.setFilter(filterBean);
    	
    	return everythingConfigurable;
    }

    public ConfigurableFilterDataProvider<User, Void, Component> getReflectionFilterDataProvider() {
    	
    	CallbackDataProvider<User, Component> dp = new CallbackDataProvider<User, Component>(
    			q -> {
    				FilterComponent c = (FilterComponent)((VerticalLayout)q.getFilter().orElse(null)).getComponent(0);
    				return UserRepository.findAll(c.getValue(), q.getOffset(), q.getLimit()).stream();
    			}
    			, q -> {
    				FilterComponent c = (FilterComponent)((VerticalLayout)q.getFilter().orElse(null)).getComponent(0);
    				return UserRepository.findAll(c.getValue(), q.getOffset(), q.getLimit()).size();
    			}
    			);
    	
    	ConfigurableFilterDataProvider<User, Void, Component> everythingConfigurable = dp.withConfigurableFilter();
    	everythingConfigurable.setFilter(reflectionFilterForm);
    	
    	return everythingConfigurable;
    }
}
