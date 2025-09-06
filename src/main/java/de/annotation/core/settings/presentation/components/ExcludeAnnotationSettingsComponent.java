package de.annotation.core.settings.presentation.components;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    listComponent.setFont(UIUtil.getToolbarFont());

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
      String value = waitForUserInput();

      if (value != null && !value.isBlank()) {
        validatePackageFormat(value)
                .ifPresent(list::add);
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

  private Optional<String> validatePackageFormat(String input) {
    if (input.startsWith(".") || input.endsWith(".") || input.contains(" ") || !input.contains(".")) {
      return Optional.empty();
    }
    return Optional.of(input);
  }

  private String waitForUserInput() {
    Project project = ProjectManager.getInstance().getOpenProjects().length > 0
            ? ProjectManager.getInstance().getOpenProjects()[0]
            : null;

    if (project == null) {
      return JOptionPane.showInputDialog(
              EXCLUDE_ANNOTATION_SETTINGS_ADD_DIALOG_HEADING,
              "com."
      );
    }

    TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project)
            .createWithInnerClassesScopeChooser(
                    EXCLUDE_ANNOTATION_SETTINGS_ADD_DIALOG_HEADING,
                    GlobalSearchScope.allScope(project),
                    PsiClass::isAnnotationType,
                    null
            );

    chooser.showDialog();

    return chooser.getSelected().getQualifiedName();
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
    JBLabel description1 = new JBLabel("- If a class or method is annotated with one or more of the excluded annotations, sorting will be skipped.");
    description1.setForeground(UIUtil.getContextHelpForeground());
    description1.setFont(JBUI.Fonts.smallFont());

    JBLabel description2 = new JBLabel("- Annotations must be specified by it's package name (xyz.xyz.xyz.AnnotationName).");
    description2.setForeground(UIUtil.getContextHelpForeground());
    description2.setFont(JBUI.Fonts.smallFont());

    JBLabel description3 = new JBLabel("- When accessing the settings from an opened project you can select them by searching your used dependencies packages.");
    description3.setForeground(UIUtil.getContextHelpForeground());
    description3.setFont(JBUI.Fonts.smallFont());

    JBLabel description4 = new JBLabel("- When accessing the settings from main menu you need to enter the FQDN manually.");
    description4.setForeground(UIUtil.getContextHelpForeground());
    description4.setFont(JBUI.Fonts.smallFont());

    return FormBuilder.createFormBuilder()
            .addComponent(description1)
            .addComponent(description2)
            .addComponent(description3)
            .addComponent(description4)
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
