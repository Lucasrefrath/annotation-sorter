package de.lucas.annotation.core.settings.presentation.components;

import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

import static de.lucas.annotation.core.config.SettingTextConstants.*;

/**
 * Ui Component for the General tab.
 */
public class GeneralSettingsComponent extends AnnotationSortingSettingComponent {

    private final JCheckBox enableSortingCheckBox;

    private final JCheckBox performOnReformatCodeCheckBox;

    public GeneralSettingsComponent() {
        enableSortingCheckBox = new JCheckBox(ENABLE_SORTING_SETTINGS_CHECKBOX_LABEL);
        enableSortingCheckBox.setToolTipText(ENABLE_SORTING_SETTINGS_CHECKBOX_TOOLTIP);
        performOnReformatCodeCheckBox = new JCheckBox(PERFORM_ON_REFORMAT_SETTINGS_CHECKBOX_LABEL);
        performOnReformatCodeCheckBox.setToolTipText(PERFORM_ON_REFORMAT_SETTINGS_CHECKBOX_TOOLTIP);
    }

    @Override
    public boolean isModified() {
        return (enableSortingCheckBox.isSelected() != settings.isSortingEnabled())
                || (performOnReformatCodeCheckBox.isSelected() != settings.isExecuteOnReformatCode());
    }

    @Override
    public void apply() {
        settings.setSortingEnabled(enableSortingCheckBox.isSelected());
        settings.setExecuteOnReformatCode(performOnReformatCodeCheckBox.isSelected());
    }

    @Override
    public void reset() {
        enableSortingCheckBox.setSelected(settings.isSortingEnabled());
        performOnReformatCodeCheckBox.setSelected(settings.isExecuteOnReformatCode());
    }

    @Override
    public JComponent toJComponent() {
        return FormBuilder.createFormBuilder()
                .addComponent(enableSortingCheckBox)
                .addComponent(performOnReformatCodeCheckBox)
                .getPanel();
    }

}
