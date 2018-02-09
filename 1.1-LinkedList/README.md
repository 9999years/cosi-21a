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

Will generate an executable jar `rebeccaturner-PA1-Part1.jar` (and a source zip)
in `target/`, which can be run with

    java -jar target/rebeccaturner-PA1-Part1.jar

## Direct compilation

Fortunately, there are no external dependencies or whatnot, so compiling without
Maven is easy too! Just

    cd src/main/java
    javac *.java

[mvn]: https://maven.apache.org/
[polyglot]: https://github.com/takari/polyglot-maven
[takari]: https://github.com/takari
