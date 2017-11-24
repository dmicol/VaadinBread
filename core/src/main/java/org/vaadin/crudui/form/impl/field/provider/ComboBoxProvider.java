package org.vaadin.crudui.form.impl.field.provider;

import java.util.Collection;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;

/**
 * @author Alejandro Duarte
 */
public class ComboBoxProvider<T> extends AbstractListingProvider<ComboBox<T>, T> {

    public ComboBoxProvider(Collection<T> items) {
        super(items);
    }

    public ComboBoxProvider(String caption, Collection<T> items) {
        super(caption, items);
    }

    public ComboBoxProvider(String caption, Collection<T> items, ItemCaptionGenerator<T> itemCaptionGenerator) {
        super(caption, items, itemCaptionGenerator);
    }

    @Override
    protected ComboBox<T> buildAbstractListing() {
        ComboBox<T> field = new ComboBox<>(caption, items);
        field.setItemCaptionGenerator(itemCaptionGenerator);
        return field;
    }

}
