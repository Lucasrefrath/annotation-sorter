package de.annotation.core.settings.presentation.components;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExcludeAnnotationComponent extends AnnotationSortingSettingComponent {

  private final CollectionListModel<String> list;

  private final JBList<String> listComponent;

  private final JScrollPane scrollPane;

  public ExcludeAnnotationComponent() {
    list = new CollectionListModel<>();
    listComponent = new JBList<>(list);

    listComponent.setEmptyText("No Annotations to exclude. Add one with the button below.");

    scrollPane = ScrollPaneFactory.createScrollPane(listComponent);
    scrollPane.setPreferredSize(new Dimension(250, 300));
  }

  private @NotNull JPanel getActionButtonGroup() {
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton addButton = new JButton("Add");
    JButton removeButton = new JButton("Remove");

    addButton.setToolTipText("Add new Annotation to exclude from sorting.");
    removeButton.setToolTipText("Remove Annotation from being excluded from sorting.");

    addButton.addActionListener(e -> {
      String value = JOptionPane.showInputDialog("Enter annotation:", "@");
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

    if (!result.startsWith("@")) {
      result = "@" + result;
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
    return FormBuilder.createFormBuilder()
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
