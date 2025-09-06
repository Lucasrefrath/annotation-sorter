package de.annotation.core.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import de.annotation.core.settings.AnnotationSortingAppSettings;
import de.annotation.core.settings.AnnotationSortingApplicationState;
import de.annotation.core.sorting.AnnotationSortingService;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

import static de.annotation.core.utils.PsiHelper.getPsiClass;

public class SortClassAnnotationsAction extends AnAction {

  private final AnnotationSortingService sortingService = new AnnotationSortingService();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

    if (!settings.isSortingEnabled()) {
      return;
    }

    Project project = e.getProject();
    Optional<PsiClass> psiClassOptional = getPsiClass(e);

    if (psiClassOptional.isEmpty() || project == null) {
      return;
    }

    PsiClass psiClass = psiClassOptional.get();

    WriteCommandAction.runWriteCommandAction(project, () -> {
      sortingService.rearrangeAllClassLevelAnnotations(project, psiClass);

      Arrays.stream(psiClass.getMethods())
              .forEach(psiMethod -> sortingService.rearrangeAllMethodLevelAnnotations(project, psiMethod));
    });
  }

}
