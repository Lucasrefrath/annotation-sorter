package de.annotation.core.actions;

import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class TestAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    PsiFile file = anActionEvent.getData(CommonDataKeys.PSI_FILE);
    Language lang = anActionEvent.getData(CommonDataKeys.PSI_FILE).getLanguage();
    String languageTag = "+[" + lang.getDisplayName().toLowerCase() + "]";
    System.out.println("TestAction");
    System.out.println(languageTag);
  }

}
