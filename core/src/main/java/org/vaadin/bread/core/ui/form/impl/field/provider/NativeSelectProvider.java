package org.vaadin.bread.core.ui.form.impl.field.provider;

import java.util.Collection;

import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.NativeSelect;

/**
 * @author Alejandro Duarte
 */
public class NativeSelectProvider<T> extends AbstractListingProvider<NativeSelect<T>, T> {

    public NativeSelectProvider(Collection<T> items) {
        super(items);
    }

    public NativeSelectProvider(String caption, Collection<T> items) {
        super(caption, items);
    }

    public NativeSelectProvider(String caption, Collection<T> items, ItemCaptionGenerator<T> itemCaptionGenerator) {
        super(caption, items, itemCaptionGenerator);
    }

    @Override
    protected NativeSelect<T> buildAbstractListing() {
        NativeSelect<T> field = new NativeSelect<>(caption, items);
        field.setItemCaptionGenerator(itemCaptionGenerator);
        return field;
    }

}
