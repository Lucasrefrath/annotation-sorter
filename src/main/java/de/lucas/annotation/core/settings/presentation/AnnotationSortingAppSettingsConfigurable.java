package de.lucas.annotation.core.settings.presentation;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.TitledSeparator;
import com.intellij.util.ui.FormBuilder;
import de.lucas.annotation.core.settings.presentation.components.GeneralSettingsComponent;
import de.lucas.annotation.core.settings.presentation.components.ExcludeAnnotationSettingsComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static de.lucas.annotation.core.config.SettingTextConstants.*;

/**
 * Configurable holding all UI for PlugIn settings.
 */
public class AnnotationSortingAppSettingsConfigurable implements Configurable {

    private ExcludeAnnotationSettingsComponent excludeAnnotationSettingsComponent;

    private GeneralSettingsComponent generalSettingsComponent;

    @Override
    public @Nls String getDisplayName() {
        return SETTINGS_TITLE;
    }

    @Override
    public @Nullable JComponent createComponent() {
        generalSettingsComponent = new GeneralSettingsComponent();
        excludeAnnotationSettingsComponent = new ExcludeAnnotationSettingsComponent();

        return FormBuilder.createFormBuilder()
                .addComponent(new TitledSeparator(GENERAL_SETTINGS_HEADING))
                .addComponent(generalSettingsComponent.toJComponent())
                .addVerticalGap(10)
                .addComponent(new TitledSeparator(EXCLUDE_ANNOTATIONS_SETTINGS_HEADING))
                .addComponent(excludeAnnotationSettingsComponent.toJComponent())
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    @Override
    public boolean isModified() {
        return generalSettingsComponent.isModified()
                || excludeAnnotationSettingsComponent.isModified();
    }

    @Override
    public void apply() {
        generalSettingsComponent.apply();
        excludeAnnotationSettingsComponent.apply();
    }

    @Override
    public void reset() {
        generalSettingsComponent.reset();
        excludeAnnotationSettingsComponent.reset();
    }

    @Override
    public void disposeUIResources() {
        generalSettingsComponent = null;
        excludeAnnotationSettingsComponent = null;
    }
}
