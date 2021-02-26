package org.dominokit.domino.ui.forms;

import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.dropdown.DropDownMenu;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.icons.Icon;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Styles;
import org.dominokit.domino.ui.utils.ElementUtil;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class Select<T> extends AbstractSelect<T, T, Select<T>> {

    private SelectOption<T> selectedOption;

    public static <T> Select<T> create() {
        return new Select<>();
    }

    public static <T> Select<T> create(String label) {
        return new Select<>(label);
    }

    public static <T> Select<T> create(String label, List<SelectOption<T>> options) {
        return new Select<>(label, options);
    }

    public static <T> Select<T> create(List<SelectOption<T>> options) {
        return new Select<>(options);
    }

    public static <T extends Enum<T>> Select<T> ofEnum(String label, T[] values) {
        Select<T> select = create(label);
        for (T value : values) {
            select.appendChild(SelectOption.create(value, value.name(), value.toString()));
        }
        return select;
    }

    public Select() {
        setOptionRenderer(new SingleOptionRenderer());
    }

    public Select(String label) {
        super(label);
        setOptionRenderer(new SingleOptionRenderer());
    }

    public Select(List<SelectOption<T>> options) {
        super(options);
        setOptionRenderer(new SingleOptionRenderer());
    }

    public Select(String label, List<SelectOption<T>> options) {
        super(label, options);
        setOptionRenderer(new SingleOptionRenderer());
    }

    @Override
    public DropDownMenu getOptionsMenu() {
        return super.getOptionsMenu();
    }

    @Override
    public Select<T> select(SelectOption<T> option, boolean silent) {
        if (selectedOption != null)
            if (!option.isEqualNode(selectedOption.element()))
                selectedOption.deselect();
        floatLabel();
        this.selectedOption = option;
        option.select();
        valuesContainer.setTextContent(option.getDisplayValue());
        hidePlaceholder();
        if (!silent)
            onSelection(option);
        return this;
    }

    public SelectOption<T> getSelectedOption() {
        return selectedOption;
    }

    @Override
    public Select<T> setValue(T value, boolean silent) {
        for (SelectOption<T> option : getOptions()) {
            if (Objects.equals(option.getValue(), value)) {
                select(option, silent);
            }
        }
        return this;
    }

    @Override
    public T getValue() {
        return isSelected() ? getSelectedOption().getValue() : null;
    }

    @Override
    public String getStringValue() {
        SelectOption<T> selectedOption = getSelectedOption();
        if (nonNull(selectedOption)) {
            return selectedOption.getDisplayValue();
        }
        return null;
    }

    @Override
    public Select<T> clear() {
        this.selectedOption = null;
        return super.clear();
    }

    public int getSelectedIndex() {
        return options.indexOf(getSelectedOption());
    }

    @Override
    protected void scrollToSelectedOption() {
        if (nonNull(selectedOption)) {
            ElementUtil.scrollIntoParent(selectedOption.element(), getOptionsMenu().getMenuElement().element());
        }
    }

    private class SingleOptionRenderer implements OptionRenderer<T> {

        @Override
        public HTMLElement element(SelectOption<T> option) {
            Icon checkMark = Icons.ALL.check().styler(style1 -> style1.add(Styles.pull_right)
                    .add("select-option-check-mark"));
            FlexItem checkMarkFlexItem = FlexItem.create();
            checkMarkFlexItem.appendChild(checkMark);
            option.getOptionLayoutElement()
                    .appendChild(checkMarkFlexItem);

            checkMark.toggleDisplay(option.isSelected());
            option.addSelectionHandler(selectable -> checkMark.toggleDisplay(selectable.isSelected()));
            return option.element();
        }
    }
}
