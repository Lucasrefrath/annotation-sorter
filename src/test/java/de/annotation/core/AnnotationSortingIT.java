package de.annotation.core;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.annotation.core.settings.AnnotationSortingAppSettings;

import java.util.List;

import static de.annotation.core.utils.TestUtils.performSortingAction;

public class AnnotationSortingIT extends BasePlatformTestCase {

  public void testSortingWithoutExclusions() {
    AnnotationSortingAppSettings.getInstance().getState().setSortingEnabled(true);
    AnnotationSortingAppSettings.getInstance().getState().setExcludedAnnotations(List.of());

    String before = """
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                @javax.annotation.processing.Generated("Test")
                public class TestClass {

                    @javax.annotation.processing.Generated("Test")
                    @java.lang.Deprecated
                    void testMethod() {}
                }
                """;

    String expected = """
                @javax.annotation.processing.Generated("Test")
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                public class TestClass {

                    @java.lang.Deprecated
                    @javax.annotation.processing.Generated("Test")
                    void testMethod() {}
                }
                """;

    myFixture.configureByText("TestClass.java", before);

    performSortingAction();

    myFixture.checkResult(expected);
  }

  public void testSkipSortingOnDisabled() {
    AnnotationSortingAppSettings.getInstance().getState().setSortingEnabled(false);
    AnnotationSortingAppSettings.getInstance().getState().setExcludedAnnotations(List.of());

    String before = """
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                @javax.annotation.processing.Generated("Test")
                public class TestClass {

                    @javax.annotation.processing.Generated("Test")
                    @java.lang.Deprecated
                    void testMethod() {}
                }
                """;

    myFixture.configureByText("TestClass.java", before);

    performSortingAction();

    myFixture.checkResult(before);
  }

  public void testSkipGroupIfContainingExcludedAnnotation() {
    AnnotationSortingAppSettings.getInstance().getState().setSortingEnabled(true);
    AnnotationSortingAppSettings.getInstance().getState().setExcludedAnnotations(
            List.of("java.lang.Deprecated")
    );

    String before = """
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                @javax.annotation.processing.Generated("Test")
                public class TestClass {

                    @javax.annotation.processing.Generated("Test Test Test")
                    @java.lang.Deprecated
                    void testMethod() {}
                }
                """;

    String expected = """
                @javax.annotation.processing.Generated("Test")
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                public class TestClass {

                    @javax.annotation.processing.Generated("Test Test Test")
                    @java.lang.Deprecated
                    void testMethod() {}
                }
                """;

    myFixture.configureByText("TestClass.java", before);

    performSortingAction();

    myFixture.checkResult(expected);
  }

  public void testSortingInInnerClass() {
    AnnotationSortingAppSettings.getInstance().getState().setSortingEnabled(true);
    AnnotationSortingAppSettings.getInstance().getState().setExcludedAnnotations(
            List.of("java.lang.Deprecated")
    );

    String before = """
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                @javax.annotation.processing.Generated("Test")
                public class TestClass {
                
                    @javax.annotation.processing.Generated("Test Mid")
                    @javax.annotation.processing.Generated("Test Long")
                    @javax.annotation.processing.Generated("Test")
                    private class InnerTestClass {}
                }
                """;

    String expected = """
                @javax.annotation.processing.Generated("Test")
                @javax.annotation.processing.Generated("Test Mid")
                @javax.annotation.processing.Generated("Test Long")
                public class TestClass {
                
                    @javax.annotation.processing.Generated("Test")
                    @javax.annotation.processing.Generated("Test Mid")
                    @javax.annotation.processing.Generated("Test Long")
                    private class InnerTestClass {}
                }
                """;

    myFixture.configureByText("TestClass.java", before);

    performSortingAction();

    myFixture.checkResult(expected);
  }

}
