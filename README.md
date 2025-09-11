# Annotation Sorter
IntelliJ IDEA plugin for sorting annotations in Java classes.

## Features

#### Which annotations will be sorted?
- All annotations decorating the currently selected Java class in the editor
- All annotations decorating any inner class
- All annotations decorating any method
- [future feature] All annotations decorating fields

#### How can annotations be sorted?
- By length
  - Single-line annotations are sorted by their total length
  - Multi-line annotations are sorted by the length of their longest line

#### How can sorting be triggered?
- By executing IntelliJ’s built-in `Reformat Code` action
- By using a custom keyboard or mouse shortcut

## Configuration
To configure the custom shortcut to execute the sorting go to `Settings > Keymap` 
and define a shortcut under `Plugins > Annotation Manager > Sort Annotation`.

The following plugin settings are available under `Settings > Annotation Sorting`:

#### Sort Annotations
Enables annotation sorting when checked.  
If unchecked, the plugin does nothing.

#### Perform Sorting on 'Reformat Code'
When enabled, the plugin performs sorting whenever IntelliJ’s built-in `Reformat Code` action is executed.

#### Exclude Annotations
Specify annotation types to be excluded from sorting.  
If any annotation group contains one of these types, the entire group will be ignored.  
Excluded annotations must be defined by their fully qualified class name (FQDN).  
When accessing the settings in a project context, a menu will scan all project dependencies for available annotations, allowing you to select them without knowing their FQDN.


## Local Build
Coming soon...

## License
This repository is licensed under the MIT License. You may not use it except in compliance with the License. You may obtain a copy of the License at [https://opensource.org/license/mit](https://opensource.org/license/mit).
