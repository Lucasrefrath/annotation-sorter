package de.annotation.core.settings.presentation.components;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static de.annotation.core.settings.presentation.config.SettingTextConstants.*;

/**
 * UI component for exclude annotation settings.
 */
public class ExcludeAnnotationSettingsComponent extends AnnotationSortingSettingComponent {

  private final CollectionListModel<String> list;

  private final JBList<String> listComponent;

  private final JScrollPane scrollPane;

  public ExcludeAnnotationSettingsComponent() {
    list = new CollectionListModel<>();
    listComponent = new JBList<>(list);

    listComponent.setEmptyText(EXCLUDE_ANNOTATION_SETTINGS_EMPTY_TEXT);

    scrollPane = ScrollPaneFactory.createScrollPane(listComponent);
    scrollPane.setPreferredSize(new Dimension(250, 300));
  }

  /**
   * Create buttongroup for interacting with exclusion list.
   *
   * @return buttongroup as JComponent
   */
  private @NotNull JPanel getActionButtonGroup() {
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton addButton = new JButton(EXCLUDE_ANNOTATION_SETTINGS_ADD_BUTTON_LABEL);
    JButton removeButton = new JButton(EXCLUDE_ANNOTATION_SETTINGS_REMOVE_BUTTON_LABEL);

    addButton.setToolTipText(EXCLUDE_ANNOTATION_SETTINGS_ADD_BUTTON_TOOLTIP);
    removeButton.setToolTipText(EXCLUDE_ANNOTATION_SETTINGS_REMOVE_BUTTON_TOOLTIP);

    addButton.addActionListener(e -> {
      String value = JOptionPane.showInputDialog(
              EXCLUDE_ANNOTATION_SETTINGS_ADD_DIALOG_HEADING,
              EXCLUDE_ANNOTATION_SETTINGS_ANNOTATION_PREFIX
      );
      if (value != null && !value.isBlank()) {
        list.add(modifyString(value));
      }
    });

    removeButton.addActionListener(e -> {
      int index = listComponent.getSelectedIndex();
      if (index >= 0) {
        list.remove(index);
      }
    });

    buttons.add(addButton);
    buttons.add(removeButton);
    return buttons;
  }

  private String modifyString(String s) {
    String result = s.trim();

    if (!result.startsWith(EXCLUDE_ANNOTATION_SETTINGS_ANNOTATION_PREFIX)) {
      result = EXCLUDE_ANNOTATION_SETTINGS_ANNOTATION_PREFIX + result;
    }

    result = result.replaceAll(" ", "");

    return result;
  }

  private java.util.List<String> getAnnotations() {
    java.util.List<String> result = new ArrayList<>();
    for (int i = 0; i < list.getSize(); i++) {
      result.add(list.getElementAt(i));
    }
    return result;
  }

  private void setAnnotations(List<String> values) {
    list.removeAll();
    values.forEach(list::add);
  }

  @Override
  public JComponent toJComponent() {
    JBLabel description = new JBLabel(EXCLUDE_ANNOTATION_SETTINGS_SUMMARY);
    description.setForeground(UIUtil.getContextHelpForeground());

    return FormBuilder.createFormBuilder()
            .addComponent(description)
            .addComponent(scrollPane)
            .addComponentToRightColumn(getActionButtonGroup())
            .getPanel();
  }

  @Override
  public boolean isModified() {
    return !this.getAnnotations().equals(settings.getExcludedAnnotations());
  }

  @Override
  public void apply() {
    settings.setExcludedAnnotations(this.getAnnotations());
  }

  @Override
  public void reset() {
    this.setAnnotations(settings.getExcludedAnnotations());
  }

}
