package de.annotation.core.settings.presentation;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.TitledSeparator;
import com.intellij.util.ui.FormBuilder;
import de.annotation.core.config.AnnotationSortingConstants;
import de.annotation.core.settings.presentation.components.EnableSortingComponent;
import de.annotation.core.settings.presentation.components.ExcludeAnnotationComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AnnotationSortingAppSettingsConfigurable implements Configurable {

  private ExcludeAnnotationComponent excludeAnnotationComponent;

  private EnableSortingComponent enableSortingComponent;

  @Override
  public @Nls String getDisplayName() {
    return AnnotationSortingConstants.SETTINGS_TITLE;
  }

  @Override
  public @Nullable JComponent createComponent() {
    enableSortingComponent = new EnableSortingComponent();
    excludeAnnotationComponent = new ExcludeAnnotationComponent();

    return FormBuilder.createFormBuilder()
            .addComponent(new TitledSeparator("General"))
            .addComponent(enableSortingComponent.toJComponent())
            .addVerticalGap(10)
            .addComponent(new TitledSeparator("Exclude Annotation"))
            .addComponent(excludeAnnotationComponent.toJComponent())
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
  }

  @Override
  public boolean isModified() {
    return enableSortingComponent.isModified()
            || excludeAnnotationComponent.isModified();
  }

  @Override
  public void apply() {
    enableSortingComponent.apply();
    excludeAnnotationComponent.apply();
  }

  @Override
  public void reset() {
    enableSortingComponent.reset();
    excludeAnnotationComponent.reset();
  }

  @Override
  public void disposeUIResources() {
    enableSortingComponent = null;
    excludeAnnotationComponent = null;
  }
}
