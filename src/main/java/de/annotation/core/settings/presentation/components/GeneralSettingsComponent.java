package de.annotation.core.settings.presentation.components;

import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

import static de.annotation.core.settings.presentation.config.SettingTextConstants.ENABLE_SORTING_SETTINGS_CHECKBOX_LABEL;
import static de.annotation.core.settings.presentation.config.SettingTextConstants.ENABLE_SORTING_SETTINGS_CHECKBOX_TOOLTIP;

/**
 * Ui Component for the General tab.
 */
public class GeneralSettingsComponent extends AnnotationSortingSettingComponent {

  private final JCheckBox enableSortingCheckBox;

  public GeneralSettingsComponent() {
    enableSortingCheckBox = new JCheckBox(ENABLE_SORTING_SETTINGS_CHECKBOX_LABEL);
    enableSortingCheckBox.setToolTipText(ENABLE_SORTING_SETTINGS_CHECKBOX_TOOLTIP);
  }

  @Override
  public boolean isModified() {
    return enableSortingCheckBox.isSelected() != settings.isSortingEnabled();
  }

  @Override
  public void apply() {
    settings.setSortingEnabled(enableSortingCheckBox.isSelected());
  }

  @Override
  public void reset() {
    enableSortingCheckBox.setSelected(settings.isSortingEnabled());
  }

  @Override
  public JComponent toJComponent() {
    return FormBuilder.createFormBuilder()
            .addComponent(enableSortingCheckBox)
            .getPanel();
  }

}
