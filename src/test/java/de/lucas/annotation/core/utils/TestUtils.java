package de.lucas.annotation.core.utils;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.testFramework.TestActionEvent;
import de.lucas.annotation.core.actions.SortClassAnnotationsAction;

import static junit.framework.TestCase.assertNotNull;

public class TestUtils {

    /**
     * Execute {@link SortClassAnnotationsAction SortClassAnnotationsAction} in Testenviroment.
     */
    public static void performSortingAction() {
        AnAction action = ActionManager.getInstance().getAction("SortClassAnnotations");
        assertNotNull("Action not found, check plugin.xml id", action);

        AnActionEvent event = TestActionEvent.createTestEvent(action);
        action.actionPerformed(event);
    }

    /**
     * Execute {@link SortClassAnnotationsAction SortClassAnnotationsAction} in Testenviroment.
     */
    public static void performReformatCodeAction() {
        AnAction action = ActionManager.getInstance().getAction("ReformatCode");
        assertNotNull("Reformat Code not found", action);

        AnActionEvent event = TestActionEvent.createTestEvent(action);

        ActionManager.getInstance().tryToExecute(
                action,
                event.getInputEvent(),
                null,
                ActionPlaces.UNKNOWN,
                true
        );
    }

}
