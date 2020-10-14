Executing program:
``` shell script
gradlew(.bat) run --args="program arguments"
```
Example arguments(generates test and displays graph):
``` shell script
gradlew(.bat) run --args="-n 4 -m 2 -t 100 -p"
```
Arguments list:
``` shell script
gradlew(.bat) run --args="--help"
```
Running unit tests:
``` shell script
gradlew(.bat) unitTest
```
Running integration tests:
``` shell script
gradlew(.bat) integraionTest
```
**Input file format**:
Each line contains number of page (0 <= page < max pages) to be loaded into memory.

**Output format**:
Each line contains 3 numbers separated by spaces.
1st is response to input line from FIFO memory,
2nd is from LRU memory,
3rd is from OPT memory.
Output contains page number if page had to be unloaded in order to load a new page or -1 if no pages were unloaded.

If test generator is being used, test generator will print test to console.

If -p flag is present script will draw the chart. You can export it by right clicking on it. 

data/ directory contains sample inputs and outputs with names formatted like {input/output}{test number}\_{max pages}\_{ram pages}.