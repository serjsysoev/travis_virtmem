import os
import subprocess

files = [file for file in os.listdir('data') if file[0] == 'i']

test_count, result = 0, 0
for file in files:
    test_count += 1
    passed = False

    test, n, m = file[5:].split('_')
    try:
        proc = subprocess.run(["java", "-jar", "build/libs/travis_virtmem.jar",
                               "-i", os.path.join("data", file),
                               "-n", n, "-m", m], capture_output=True)
        stdout = proc.stdout.decode("utf8")
    except:
        stdout = ""

    with open(os.path.join("data", f"output{test}_{n}_{m}")) as f:
        if stdout.split() == f.read().split():
            passed = True

    print(f"Test {test}: {passed}")
    result += passed

print()
print(f"Passed {result} out of {test_count} tests")
exit(test_count == result)
