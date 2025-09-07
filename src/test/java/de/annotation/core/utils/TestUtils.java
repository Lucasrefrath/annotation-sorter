package de.annotation.core.utils;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.testFramework.TestActionEvent;

import static junit.framework.TestCase.assertNotNull;

public class TestUtils {

  /**
   * Execute {@link de.annotation.core.actions.SortClassAnnotationsAction SortClassAnnotationsAction} in Testenviroment.
   */
  public static void performSortingAction() {
    AnAction action = ActionManager.getInstance().getAction("SortClassAnnotations");
    assertNotNull("Action not found, check plugin.xml id", action);

    AnActionEvent event = TestActionEvent.createTestEvent(action);
    action.actionPerformed(event);
  }

}
