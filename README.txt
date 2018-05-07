
# run the tests
sbt test

# assemble a fat jar
sbt assembly

# run the app (from the directory containing the fat jar)
java -cp "tennis-assembly-1.0.jar" Main 0.5

