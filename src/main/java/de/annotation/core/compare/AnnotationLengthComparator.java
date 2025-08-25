package de.annotation.core.compare;

import com.intellij.psi.PsiAnnotation;

import java.util.Comparator;

public class AnnotationLengthComparator implements Comparator<PsiAnnotation> {

  @Override
  public int compare(PsiAnnotation o1, PsiAnnotation o2) {
    return getMaxVisualWidth(o1) - getMaxVisualWidth(o2);
  }

  private int getMaxVisualWidth(PsiAnnotation annotation) {
    String text = annotation.getText();
    String[] lines = text.split("\n");
    int max = 0;
    for (String line : lines) {
      int len = line.trim().length();
      if (len > max) {
        max = len;
      }
    }
    return max;
  }

  public static AnnotationLengthComparator compareByVisualWidth() {
    return new AnnotationLengthComparator();
  }

}
