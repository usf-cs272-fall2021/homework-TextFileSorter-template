TextFileSorter
=================================================

![Points](../../blob/badges/points.svg)

For this homework, you will explore using the [Comparable](https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/lang/Comparable.html) and [Comparator](https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/util/Comparator.html) interfaces, and anonymous inner classes, non-static inner classes, and static nested classes to sort text files by different properties.

## Hints ##

Below are some hints that may help with this homework assignment:

  - Focus first on implementing the [Comparable](https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/lang/Comparable.html) interface for the `TextFile` static nested class. You will need to modify the declaration of this class and add method(s) for this part.

    :warning: *There will be at least one warning in the test code until you properly implement the [Comparable](https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/lang/Comparable.html) interface!*

  - The [Comparator](https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/util/Comparator.html) members require you to (1) create either an anonymous inner class, non-static inner class, or static nested class and (2) initialize an instance of that class. You may add additional classes, methods, and/or members for this part as needed.

  - Remember, to initialize an instance of a non-static inner class, you need an instance of the outer class first. See the [Java Tutorials: Nested Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html) tutorial for examples. One example they provide is:

      ```java
      OuterClass outerObject = new OuterClass();
      OuterClass.InnerClass innerObject = outerObject.new InnerClass();
      ```

      This can be combined into a single line as follows:

      ```java
       OuterClass.InnerClass example = new OuterClass().new InnerClass();
       ```

  - This homework will not be directly used by any project. However, it is useful for demonstrating different approaches to sorting data, which may come in handy for project 2 when sorting search results.

These hints are *optional*. There may be multiple approaches to solving this homework.

## Requirements ##

See the Javadoc and `TODO` comments in the template code in the `src/main/java` directory for additional details. You must pass the tests provided in the `src/test/java` directory. Do not modify any of the files in the `src/test` directory.

See the [Homework Guides](https://usf-cs272-fall2021.github.io/guides/homework/) for additional details on homework requirements and submission.
