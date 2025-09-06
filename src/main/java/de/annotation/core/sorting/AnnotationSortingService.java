package de.annotation.core.sorting;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import de.annotation.core.compare.AnnotationLengthComparator;
import de.annotation.core.settings.AnnotationSortingAppSettings;
import de.annotation.core.settings.AnnotationSortingApplicationState;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Service to handle sorting of Annotations.
 */
@Slf4j
public class AnnotationSortingService {

  private final AnnotationSortingApplicationState settings = AnnotationSortingAppSettings.getInstance().getState();

  private List<String> sort(List<PsiAnnotation> annotationsToSort) {
    return annotationsToSort.stream()
            .sorted(AnnotationLengthComparator.compareByVisualWidth())
            .map(PsiElement::getText)
            .toList();
  }

  /**
   * Get clean Name of annotation.
   * @param annotation annotation to get name of.
   * @return Name of annotation
   */
  private String getAnnotationName(PsiAnnotation annotation) {
    String[] qualifiedName = Objects.requireNonNull(annotation.getQualifiedName()).split("\\.");
    return "@" + qualifiedName[qualifiedName.length - 1];
  }

  /**
   * Check if a group of annotations contains one or more of the excluded annotations.
   *
   * @param annotations annotationgroup to check
   * @return if the group contains an excluded annotation
   */
  private boolean groupContainsExcludedAnnotations(List<PsiAnnotation> annotations) {
    return annotations.stream()
            .anyMatch(psiAnnotation ->
                    settings.getExcludedAnnotations().contains(getAnnotationName(psiAnnotation))
            );
  }

  /**
   * Rearrange all class-level annotations of given PsiClass.
   *
   * @param psiProject project the class is located in
   * @param psiClass class the annotations should be rearranged in
   */
  public void rearrangeAllClassLevelAnnotations(Project psiProject, PsiClass psiClass) {
    sortAnnotationsByModifierList(psiProject, psiClass.getModifierList());
  }

  /**
   * Rearrange all method-level annotations of given PsiMethod.
   *
   * @param psiProject project the class of the method is located in
   * @param psiMethod method the annotations should be rearranged on
   */
  public void rearrangeAllMethodLevelAnnotations(Project psiProject, PsiMethod psiMethod) {
    sortAnnotationsByModifierList(psiProject, psiMethod.getModifierList());
  }

  private void sortAnnotationsByModifierList(Project psiProject, PsiModifierList modifierList) {
    if (modifierList == null) return;

    List<PsiAnnotation> annotations = Arrays.asList(modifierList.getAnnotations());

    if (annotations.size() <= 1 || groupContainsExcludedAnnotations(annotations)) {
      return;
    }

    List<String> currentOrder = annotations.stream()
            .map(PsiAnnotation::getText)
            .toList();

    List<String> sortedOrder = sort(annotations);

    if (sortedOrder.equals(currentOrder)) {
      return;
    }

    PsiElementFactory factory = JavaPsiFacade.getInstance(psiProject).getElementFactory();

    sortedOrder.forEach(wanted -> {
      String actual = currentOrder.get(sortedOrder.indexOf(wanted));

      if (!wanted.equals(actual)) {
        PsiAnnotation old = annotations.get(sortedOrder.indexOf(wanted));
        PsiAnnotation replacement = factory.createAnnotationFromText(wanted, modifierList);
        old.replace(replacement);
      }
    });
  }

}
