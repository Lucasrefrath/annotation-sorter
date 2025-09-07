package de.annotation.core.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import de.annotation.core.settings.AnnotationSortingAppSettings;
import de.annotation.core.settings.AnnotationSortingApplicationState;
import de.annotation.core.sorting.AnnotationSortingService;
import de.annotation.core.utils.PsiHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

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
      psiClasses.forEach(psiClass -> handleClass(project, psiClass));
    });
  }

  private void handleClass(Project project, PsiClass psiClass) {
    sortingService.rearrangeAllClassLevelAnnotations(project, psiClass);

    Arrays.stream(psiClass.getMethods())
            .forEach(psiMethod -> sortingService.rearrangeAllMethodLevelAnnotations(project, psiMethod));

    Arrays.stream(psiClass.getAllInnerClasses())
            .forEach(innerClass -> handleClass(project, innerClass));
  }

}
