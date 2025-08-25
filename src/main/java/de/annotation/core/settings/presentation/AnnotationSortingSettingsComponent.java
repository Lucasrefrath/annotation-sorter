package de.annotation.core.settings.presentation;

import lombok.Getter;

import javax.swing.*;

public class AnnotationSortingSettingsComponent {

  @Getter
  private final JPanel mainPanel;

  private final JCheckBox enableSortingCheckBox;

  public AnnotationSortingSettingsComponent() {
    mainPanel = new JPanel();
    enableSortingCheckBox = new JCheckBox("Sort Annotations");
    mainPanel.add(enableSortingCheckBox);
  }

  public boolean getOption() {
    return enableSortingCheckBox.isSelected();
  }

  public void setOption(boolean value) {
    enableSortingCheckBox.setSelected(value);
  }

}
