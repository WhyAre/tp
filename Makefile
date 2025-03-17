GRADLE := ./gradlew

current: test

fmt:
	$(GRADLE) spotlessApply

test:
	$(GRADLE) test

run:
	$(GRADLE) run
