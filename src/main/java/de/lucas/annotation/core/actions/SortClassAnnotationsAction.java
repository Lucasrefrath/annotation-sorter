package de.lucas.annotation.core.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import de.lucas.annotation.core.settings.AnnotationSortingAppSettings;
import de.lucas.annotation.core.settings.AnnotationSortingApplicationState;
import de.lucas.annotation.core.sorting.AnnotationSortingService;
import de.lucas.annotation.core.utils.PsiHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Intellij-Platform Event that performs the annotationsorting when triggered.
 */
public class SortClassAnnotationsAction extends AnAction {

    private final AnnotationSortingService sortingService = new AnnotationSortingService();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

        if (!settings.isSortingEnabled()) {
            return;
        }

        Project project = e.getProject();
        List<PsiClass> psiClasses = PsiHelper.getPsiClassesByEvent(e);

        WriteCommandAction.runWriteCommandAction(project, () -> {
            psiClasses.forEach(psiClass -> {
                handleClassAndMethods(project, psiClass);

                Arrays.stream(psiClass.getAllInnerClasses())
                        .forEach(innerClass -> {
                            handleClassAndMethods(project, innerClass);
                        });
            });
        });
    }

    /**
     * Workflow regarding sorting for one class.
     *
     * @param project the project located in
     * @param psiClass the PsiClass element to execute the workflow for
     */
    private void handleClassAndMethods(Project project, PsiClass psiClass) {
        sortingService.rearrangeAllClassLevelAnnotations(project, psiClass);

        Arrays.stream(psiClass.getMethods())
                .forEach(psiMethod -> sortingService.rearrangeAllMethodLevelAnnotations(project, psiMethod));
    }

}
