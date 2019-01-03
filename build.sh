#!/bin/bash

# See if build already exists
if [ -d "build" ] || [ -f "Solver.jar"]; then
    # Make old_build folder if !exist
    if [ ! -d "old_build" ]; then
        mkdir old_build
    else
	# either build or Solver.jar exists
        # we only want to remove everything
        # if we have a replacement Solver.jar
        if [ -f "Solver.jar" ]; then
            rm -rf old_build/
            mkdir old_build
        else
            rm -rf old_build/build/
        fi
    fi
    
    # Now that we have a backup folder let's store stuff
    if [ -d "build" ]; then
        mv build/ old_build/build/
    fi

    if [ -f "Solver.jar" ]; then
        mv Solver.jar old_build/
    fi
fi

# make new build directory
mkdir build
# generate class files
javac -d ./build matrixsolver/*.java

# go to build directory
cd build
# generate jar file
jar -cvfe Solver.jar matrixsolver.MatrixSolver matrixsolver/*.class
# go back
cd ..
