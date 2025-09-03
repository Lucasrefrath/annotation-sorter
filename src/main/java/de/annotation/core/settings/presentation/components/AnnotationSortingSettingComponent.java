package de.annotation.core.settings.presentation.components;

import de.annotation.core.settings.AnnotationSortingAppSettings;
import de.annotation.core.settings.AnnotationSortingApplicationState;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public abstract class AnnotationSortingSettingComponent {

  protected final @NotNull AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

  public abstract JComponent toJComponent();

  public abstract boolean isModified();

  public abstract void apply();

  public abstract void reset();

}
