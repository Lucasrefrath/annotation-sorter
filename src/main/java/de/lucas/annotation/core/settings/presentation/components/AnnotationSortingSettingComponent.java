package de.lucas.annotation.core.settings.presentation.components;

import de.lucas.annotation.core.settings.AnnotationSortingAppSettings;
import de.lucas.annotation.core.settings.AnnotationSortingApplicationState;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Interface for all setting UI components to implement.
 */
public abstract class AnnotationSortingSettingComponent {

    // Global PlugIn setting storage
    protected final @NotNull AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

    public abstract JComponent toJComponent();

    public abstract boolean isModified();

    public abstract void apply();

    public abstract void reset();

}
