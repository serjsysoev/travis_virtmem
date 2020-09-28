Executing program:
``` shell script
gradlew(.bat) run --args="program arguments"
```
Arguments list:
``` shell script
gradlew(.bat) run --args="--help"
```
Running unit tests:
``` shell script
gradlew(.bat) test
```
**Input file format**:
Each line contains number of page to be loaded into memory.

**Output format**:
Each line contains 3 numbers.
1st is response to input line from FIFO memory,
2nd is from LRU memory,
3rd is from OPT memory.
Output contains page number if page had to be unloaded in order to lad new page or -1 if no pages were unloaded.

data/ directory contains sample inputs.