package de.annotation.core.settings;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining parameters to be persisted throughout IDE restarts.
 */
@Getter
@Setter
public class AnnotationSortingApplicationState {

  // List of all annotations that should be excluded from sorting.
  private List<String> excludedAnnotations = new ArrayList<>();

  // Tag if sorting is globally enabled.
  private boolean sortingEnabled = true;

}
