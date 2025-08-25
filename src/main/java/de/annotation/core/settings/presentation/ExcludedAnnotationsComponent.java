package de.annotation.core.settings.presentation;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExcludedAnnotationsComponent {

  private final JPanel panel;
  private final JBList<String> list;
  private final DefaultListModel<String> listModel;

  public ExcludedAnnotationsComponent() {
    panel = new JPanel(new BorderLayout());

    listModel = new DefaultListModel<>();
    list = new JBList<>(listModel);
    panel.add(new JBScrollPane(list), BorderLayout.CENTER);

    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton addButton = new JButton("Add");
    JButton removeButton = new JButton("Remove");

    addButton.addActionListener(e -> {
      String value = JOptionPane.showInputDialog(panel, "Enter annotation:");
      if (value != null && !value.isBlank()) {
        listModel.addElement(value.trim());
      }
    });

    removeButton.addActionListener(e -> {
      int index = list.getSelectedIndex();
      if (index >= 0) {
        listModel.remove(index);
      }
    });

    buttons.add(addButton);
    buttons.add(removeButton);
    panel.add(buttons, BorderLayout.SOUTH);
  }

  public JPanel getPanel() {
    return panel;
  }

  public List<String> getAnnotations() {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < listModel.size(); i++) {
      result.add(listModel.get(i));
    }
    return result;
  }

  public void setAnnotations(List<String> values) {
    listModel.clear();
    values.forEach(listModel::addElement);
  }
}

