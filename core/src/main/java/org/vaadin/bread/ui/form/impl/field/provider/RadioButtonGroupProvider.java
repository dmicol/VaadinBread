package org.vaadin.bread.ui.form.impl.field.provider;

import java.util.Collection;

import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.RadioButtonGroup;

/**
 * @author Alejandro Duarte
 */
public class RadioButtonGroupProvider<T> extends AbstractListingProvider<RadioButtonGroup<T>, T> {

    public RadioButtonGroupProvider(Collection<T> items) {
        super(items);
    }

    public RadioButtonGroupProvider(String caption, Collection<T> items) {
        super(caption, items);
    }

    public RadioButtonGroupProvider(String caption, Collection<T> items, ItemCaptionGenerator<T> itemCaptionGenerator) {
        super(caption, items, itemCaptionGenerator);
    }

    @Override
    protected RadioButtonGroup<T> buildAbstractListing() {
        RadioButtonGroup<T> field = new RadioButtonGroup<>(caption, items);
        field.setItemCaptionGenerator(itemCaptionGenerator);
        return field;
    }

}
