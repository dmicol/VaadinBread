package org.vaadin.bread.example.springJpa;

import org.vaadin.bread.core.ui.bread.Bread;
import org.vaadin.bread.core.ui.bread.ItemListListener;
import org.vaadin.bread.example.UserCrud;
import org.vaadin.bread.example.base.repo.JPAService;
import org.vaadin.bread.example.base.repo.UserRepository;
import org.vaadin.bread.example.model.User;
import org.vaadin.bread.example.model.UserFilter;
import org.vaadin.jetty.VaadinJettyServer;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Dmitrij Colautti
 */
public class SpringJpaBasedUI extends UI implements ItemListListener<User> {

    public static void main(String[] args) throws Exception {
        JPAService.init();
        VaadinJettyServer server = new VaadinJettyServer(8080, SpringJpaBasedUI.class);
        server.start();
    }

    private TabSheet tabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        setContent(tabSheet);

        addCrud(getConfiguredCrud(), "Configured");
        tabSheet.setSelectedTab(2);
    }

    private void addCrud(Bread<User> crud, String caption) {
        VerticalLayout layout = new VerticalLayout(crud);
        layout.setSizeFull();
        layout.setMargin(true);
        tabSheet.addTab(layout, caption);
    }

	private Bread<User> getConfiguredCrud() {
    	
    	return new UserCrud();
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
