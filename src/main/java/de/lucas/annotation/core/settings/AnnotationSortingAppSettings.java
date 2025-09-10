package de.lucas.annotation.core.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

/**
 * PersistentStateComponent implementation to access {@link AnnotationSortingApplicationState} as Singleton.
 */
@Service(Service.Level.APP)
@State(
        name = "AnnotationSortingPlugInSettings",
        storages = @Storage("AnnotationSortingPlugInSettings.xml")
)
public final class AnnotationSortingAppSettings implements PersistentStateComponent<AnnotationSortingApplicationState> {

    private AnnotationSortingApplicationState state = new AnnotationSortingApplicationState();

    public static AnnotationSortingAppSettings getInstance() {
        return ApplicationManager
                .getApplication()
                .getService(AnnotationSortingAppSettings.class);
    }

    public @NotNull AnnotationSortingApplicationState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull AnnotationSortingApplicationState state) {
        this.state = state;
    }

}

