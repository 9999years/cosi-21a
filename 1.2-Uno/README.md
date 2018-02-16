# Compiling

Extract `rebeccaturner-PA1-Part1.zip` and enter the directory, as you’ve
presumably already done if you’re reading this.

From the directory with this file (`README.md`) and the `pom.yaml`, you can
compile the project either with [Maven][mvn] or directly.

## Maven

    mvn compile

Will compile the sources and place the `.class` files in `target/classes`. Note
that this project uses [Takari’s][takari] [Polyglot for Maven][polyglot] to
so I can write `pom.yaml` because I, personally, hate XML.

Alternatively,

    mvn package

Will generate a source zip `rebeccaturner-PA1-Part1.zip` in `target/`.

Testing is easy too, with

    mvn test

But note that JUnit 5 is required and [parameterized tests][param] are used, so
`org.junit.jupiter.junit-jupiter-params` is required as well; Maven will take
care of this for you but as far as I know it’s essentially impossible to
hand-install JUnit 5.

## Direct compilation

Fortunately, there are no external dependencies or whatnot, so compiling without
Maven is easy too! Just

    cd src/main/java
    javac *.java

Testing, however, is another manner; see the note above and let me know if you
find a way to run JUnit 5 tests without using some dependency management system
(Maven, Gradle, etc.).

[mvn]: https://maven.apache.org/
[polyglot]: https://github.com/takari/polyglot-maven
[takari]: https://github.com/takari
[param]: https://junit.org/junit5/docs/5.0.0-M4/user-guide/#writing-tests-parameterized-tests
