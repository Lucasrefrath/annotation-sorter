package de.annotation.core.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import de.annotation.core.compare.AnnotationLengthComparator;
import de.annotation.core.settings.AnnotationSortingAppSettings;
import de.annotation.core.settings.AnnotationSortingApplicationState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.annotation.core.utils.PsiHelper.getPsiClass;

public class SortClassAnnotationsAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

    if (!settings.isSortingEnabled()) {
      return;
    }

    System.out.println("Sort Annotations Action");
    Project project = e.getProject();
    Optional<PsiClass> psiClassOptional = getPsiClass(e);

    if (psiClassOptional.isEmpty() || project == null) {
      return;
    }

    PsiClass psiClass = psiClassOptional.get();

    WriteCommandAction.runWriteCommandAction(project, () -> {
      PsiModifierList modifierList = psiClass.getModifierList();
      if (modifierList == null) return;

      PsiAnnotation[] annotations = modifierList.getAnnotations();
      if (annotations.length <= 1) return;

      List<String> sortedOrder = Arrays.stream(annotations)
              .sorted(AnnotationLengthComparator.compareByVisualWidth())
              .map(PsiAnnotation::getText)
              .toList();

      List<String> currentOrder = Arrays.stream(annotations)
              .map(PsiAnnotation::getText)
              .map(String::trim)
              .toList();

      if (sortedOrder.equals(currentOrder)) {
        return;
      }

      PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();

      sortedOrder.forEach(wanted -> {
        String actual = currentOrder.get(sortedOrder.indexOf(wanted));

        if (!wanted.equals(actual)) {
          PsiAnnotation old = annotations[sortedOrder.indexOf(wanted)];
          PsiAnnotation replacement = factory.createAnnotationFromText(wanted, modifierList);
          old.replace(replacement);
        }

      });


    });
  }

}
