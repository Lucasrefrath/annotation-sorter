package de.lucas.annotation.core.actions;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.AnActionResult;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import de.lucas.annotation.core.settings.AnnotationSortingAppSettings;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * ActionListener that listens for the "reformat Code" action to be called. Than performs the {@link SortClassAnnotationsAction}.
 */
public class ReformatCodeActionListener implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        if (!AnnotationSortingAppSettings.getInstance().getState().isExecuteOnReformatCode()) {
            return CompletableFuture.completedFuture(null);
        }

        ApplicationManager.getApplication().getMessageBus().connect(project)
                .subscribe(AnActionListener.TOPIC, new AnActionListener() {

                    @Override
                    public void afterActionPerformed(@NotNull AnAction action,
                                                     @NotNull AnActionEvent event,
                                                     @NotNull AnActionResult result) {
                        String actionId = ActionManager.getInstance().getId(action);
                        if ("ReformatCode".equals(actionId)) {
                            System.out.println("Reformat code action started");
                            performAnnotationSortingAction(event);
                        }
                    }

                });
        return CompletableFuture.completedFuture(null);
    }

    private void performAnnotationSortingAction(AnActionEvent actionEvent) {
        AnAction sortClassAnnotationsAction = ActionManager.getInstance().getAction("SortClassAnnotations");
        if (sortClassAnnotationsAction != null) {
            sortClassAnnotationsAction.actionPerformed(actionEvent);
        }
    }
}
