package de.annotation.core.settings.presentation;

import com.intellij.openapi.options.Configurable;
import de.annotation.core.config.AnnotationSortingConstants;
import de.annotation.core.settings.AnnotationSortingAppSettings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AnnotationSortingAppSettingsConfigurable implements Configurable {

  private AnnotationSortingSettingsComponent component;

  @Override
  public @Nls String getDisplayName() {
    return AnnotationSortingConstants.SETTINGS_TITLE;
  }

  @Override
  public @Nullable JComponent createComponent() {
    component = new AnnotationSortingSettingsComponent();
    return component.getMainPanel();
  }

  @Override
  public boolean isModified() {
    AnnotationSortingAppSettings settings = AnnotationSortingAppSettings.getInstance();
    return component.getOption() != settings.getState().isSortingEnabled();
  }

  @Override
  public void apply() {
    AnnotationSortingAppSettings.getInstance().getState().setSortingEnabled(component.getOption());
  }

  @Override
  public void reset() {
    component.setOption(AnnotationSortingAppSettings.getInstance().getState().isSortingEnabled());
  }

  @Override
  public void disposeUIResources() {
    component = null;
  }
}
