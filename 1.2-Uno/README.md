# Compiling

Extract `rebeccaturner-PA1-Part2.zip` and enter the directory, as you’ve
presumably already done if you’re reading this.

From the directory with this file (`README.md`) and the `pom.yaml`, you can
compile the project either with [Maven][mvn] or directly.

## Maven

    mvn compile

Will compile the sources and place the `.class` files in `target/classes`. Note
that this project uses [Takari’s][takari] [Polyglot for Maven][polyglot] to
so I can write `pom.yaml` because I hate XML.

Alternatively,

    mvn package

Will generate a source zip `rebeccaturner-PA1-Part2.zip` as well as an
executable .jar in `target/`.

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

and run with

    java UnoGame

Testing, however, is another manner; see the note above and let me know if you
find a way to run JUnit 5 tests without using some dependency management system
(Maven, Gradle, etc.).

# Running

Run with something like `java -cp target/classes UnoGame` or `java -jar
target/rebeccaturner-PA1-Part2.jar`. Try `--help` for help and usage information.

# Extras

I wrote some extra classes. Sorry! I think they’re all well-justified though.
Normally, people would use something like [Guava][guava] for a lot of this
stuff, but since the point of this class is largely to make this stuff by hand
ourselves, I can’t use that. Unfortunately, this means you have to read more code.
I feel bad about that! But I also find it very hard to justify sacrificing
usability and extensibility to implement arbitrary guidelines.

* `CircularList` and `CircularOrderedList` represent a circular list where the
  element after the last element is the first element; both supply
  `ListIterator`s in a regular flavor (which iterates the list once) and an
  infinite flavor with `.infiniteIterator()` (which continues in a circular
  fashion for as long as you’d like. Make sure the list isn't empty, though, if
  you’re using one, because you can get some weird effects. These are used to
  implement the `PlayerCircle` class.
* `Hand` represents an Uno hand and is used within the `Player` class; it seemed
  natural to avoid joining the concept of a player and their hand, so I factored
  it out. I think it makes the API a lot clearer and makes it obvious what
  operations manipulate a player and what operations manipulate the player’s
  hand, but I’ll admit the line is blurred.

Also, I rewrote `UnoCard`; it was fragile in a way that made invariants easy to
break and buggy code hard to write; I made all the fields public and final to
enforce immutability both within and outside of the class. Everything else works
the same but the code looks a lot less ridiculous. All the `is...()` methods are
cut out and code like `card.special == UnoCard.Special.DrawTwo` instead (it’s
null-safe!).

I also changed a few method names for consistency — the assignment specifies
`Queue.getSize()` and `PlayerCircle.getSize()` but `SinglyLinkedList.size()`
(not to mention how “insert” and “add” are used with little differentiation in
method names, but I didn’t mess with those). I renamed the size methods to
`size()` for compatibility with [`java.util.Collection`][collection] and its
dozens of extremely common subclasses. `DoublyLinkedOrderedList.getHead` returns
a `T` instead of a `DoublyLinkedNode<T>` to preserve encapsulation. (Was that a
typo in the original assignment?)

[mvn]: https://maven.apache.org/
[polyglot]: https://github.com/takari/polyglot-maven
[takari]: https://github.com/takari
[param]: https://junit.org/junit5/docs/5.0.0-M4/user-guide/#writing-tests-parameterized-tests
[nullableEquals]: https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#containsKey-java.lang.Object-
[guava]: https://github.com/google/guava/wiki
[collection]: https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#size--
