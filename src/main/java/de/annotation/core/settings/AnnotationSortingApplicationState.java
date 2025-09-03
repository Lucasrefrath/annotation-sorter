package de.annotation.core.settings;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnnotationSortingApplicationState {

  private List<String> excludedAnnotations = new ArrayList<>();

  private boolean sortingEnabled = true;

}
