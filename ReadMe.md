# MatrixSolver
## What Do It?
You guessed right! - It solves matrices! This implements a basic college pre-calculus algorthim of solving matrices using two recursive functions.

## How to Do It?
### Compile the thing
There is a build script **build.sh**
Run the following
```
chmod +x build.sh
./build.sh
```
This will generate class code in a build/matrixsolver/ directory. It will also generate a jar file in the build/ directory. This jar file can then be run with the following command.
```
cd build
java -jar Solver.jar
```
Note that if a build already exists it will be moved into a directory old_build/

You can also move the entire matrixsolver folder into the src/ folder of a NetBeans project and use NetBeans to build and run.

### Run it
The main program has a basic CLI. It will give you a few options:
```
Application mode? (0 - normal, 1 - inverse, 2 - solve for variables): ??
```
Enter 0 to solve any matrix so long as it has more columns than rows.
Enter 1 to solve for the inverse matrix which requires twice as many columns as rows.
Enter 2 to solve for variables (x, y, z, etc...) Which requires one more row than column.
```
How many rows in the matrix?: ??
How many columns in the matrix?: ??
```
Fill in when asked. Depending on the first input it may or may not ask for both but may auto fill in according to the requirements.
If you give a combination that does not satisfy the row/column requirements the program will stop/
```
Generate random numbers to fill? (y/n): ??
Be verbose? (y/n): ??
```
Instead of filling in numbers you can choose to have the matrix filled randomly. It's kinda fun to generate a huge matrix randomly and watch it solve.
If you select be verbose the solver will print every step of the solve. If you do not select verbose it will print out the initial matrix and the solved matrix.
If you do not select random numbers you will need to fill in the matrix.
It will prompt you to fill it in row by row, from left to right. It will ask for the top row, then the second, to the bottom.

## Notes
### Using Windows
The build script is catered for linux and macOS. If you need to run on windows open build.sh and run the commands at the bottom, *javac* and *jar*, which should be able to run in windows. Make sure the jar command is executed in the build directory otherwise it will not be able to find the main class.

### ToDo
Add comments, make code prettier.
