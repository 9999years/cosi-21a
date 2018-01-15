# Building simulation

Most of it should be pretty clear. The only weird part is the ArrayQueue class
which is, like it sounds, a Queue implemented with an array. Hey, the assignment
said arrays only! The Elevator class clearly requires a Queue of Jobs, even if
the assignment didn't say it, and the Queue needs to be able to dynamically
resize upwards, enough behavior not inherent to an elevator (and complex enough)
that it makes sense to factor it out into its own class.

# General program flow

A client would construct a building either explicitly with a floor count in mind
(`new Building(19)`) or without any arguments to get the default floor count
(10).

Next, given a `Person` P, the `Building.enterElevator(Person p, int floor)`
method is used to add them to the lobby with a given floor in mind for their
destination. `Building.enterElevator` creates a `Job` on the elevator to be
processed at the building's owner's leisure.

When `Building.startElevator` is called, `Elevator.processAllJobs` is
called, which picks up and deposits all the waiting passengers in order,
removing their jobs from the queue as necessary. This involves the elevator
moving to the lobby, moving to the destination floor, and calling
`Building.enterFloor` to deposit the passenger on their floor, storing them in
their floor's array (-queue).

# Compiling

Extract `rebeccaturner-PA0.zip` and enter the directory, as you’ve presumably
already done if you’re reading this. You can compile with [Maven][1] or just via
the command-line (except for the tests).

    mvn compile

Will compile the sources and place the `.class` files in `target/classes`.

[1]: https://maven.apache.org/
