package de.annotation.core.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Optional;

public class PsiHelper {

  public static Optional<PsiClass> getPsiClass(AnActionEvent event) {
    Editor editor = event.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);

    if (editor == null || psiFile == null) {
      return Optional.empty();
    }

    final int offset = editor.getCaretModel().getOffset();
    PsiElement elementAtCaret = psiFile.findElementAt(offset);

    return Optional.ofNullable(
            PsiTreeUtil.getParentOfType(elementAtCaret, PsiClass.class)
    );
  }

}
