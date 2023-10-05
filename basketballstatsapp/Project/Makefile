RunTests:
	mvn test
Compile:
	mvn compile
Clean:
	mvn clean
	-rm original-basketballstats.jar
Package:
	mvn package -DskipTests
	git tag 1.0.0

make all:
	make Compile
	make Clean
	make RunTests
	make Package