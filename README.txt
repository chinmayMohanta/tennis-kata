TENNIS KATA

Tennis score implementation design choices: (Keeping multi-tenancy cloud environment in mind)
1. Functional programming (Share nothing - only immutable states) approach
2. Data centric
3. Extensible through polymorphic function closure (lambda functions)


Operational commands
# run the unit tests (100% code coverage)
sbt test

# assemble a fat jar
sbt assembly

# run the app (from the directory containing the fat jar)
java -cp "tennis-assembly-1.0.jar" Main 0.5
