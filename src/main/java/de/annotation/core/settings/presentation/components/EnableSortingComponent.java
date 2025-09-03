package de.annotation.core.settings.presentation.components;

import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class EnableSortingComponent extends AnnotationSortingSettingComponent {

  private final JCheckBox enableSortingCheckBox;

  public EnableSortingComponent() {
    enableSortingCheckBox = new JCheckBox("Sort Annotations");
    enableSortingCheckBox.setToolTipText("Enables sorting of annotations.");
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
