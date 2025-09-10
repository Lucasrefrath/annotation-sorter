package de.lucas.annotation.core.compare;

import com.intellij.psi.PsiAnnotation;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Comparator implementation to compare two PsiAnnotations by maximum visual width.
 */
public class AnnotationLengthComparator implements Comparator<PsiAnnotation> {

    @Override
    public int compare(PsiAnnotation o1, PsiAnnotation o2) {
        return getMaxVisualWidth(o1) - getMaxVisualWidth(o2);
    }

    /**
     * Calculate maximum visual width for given Annotation.
     *
     * <pre>
     * For single-lined annotations: total length
     * For multi-lined annotations: length of longest line
     * </pre>
     *
     * @param annotation the annotation to calculate for
     * @return maximum visual width as integer
     */
    private int getMaxVisualWidth(PsiAnnotation annotation) {
        String text = annotation.getText();
        String[] lines = text.split("\n");

        return Arrays.stream(lines)
                .map(String::trim)
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    public static AnnotationLengthComparator compareByVisualWidth() {
        return new AnnotationLengthComparator();
    }

}
