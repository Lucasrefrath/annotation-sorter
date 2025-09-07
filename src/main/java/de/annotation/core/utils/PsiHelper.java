package de.annotation.core.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.util.Arrays;
import java.util.List;

/**
 * Helperclass to retrieve PSI-Elements.
 */
public class PsiHelper {

  public static List<PsiClass> getPsiClassesByEvent(AnActionEvent event) {
    PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);

    if (psiFile instanceof PsiJavaFile javaFile) {
      return Arrays.asList(javaFile.getClasses());
    }

    return List.of();
  }



}
