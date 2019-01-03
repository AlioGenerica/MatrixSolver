/*******************************************************************************
 * Joshua Spisak
 * 4/15/2018
 * Program: MatrixSolver
 * File: Matrix
 * Purpose: To make a class that holds the matrix and can have operations
 *  performed on it.
 ******************************************************************************/
package matrixsolver;

/**
 *
 * @author joshua.spisak
 */
interface matrixable<T> {
    matrixable times(matrixable factor); //returns value * factor
    matrixable plus(matrixable added); //returns value + addend
    matrixable reciprocal(); //returns 1/value
    String toString(); //string form of value
    String clone(); //clones value
    boolean equals(matrixable test); //test if equals
    matrixable zero(); //zero value of type
    matrixable one(); //one value of type;
}


public class Matrix {
    Number[][] matrix; //the matrix... what if I told you... this could be
                       //any Object that implements matrixable... cool right :)
    
    //solutions tracking
    boolean infinite = false;
    boolean error = false;
    
    //kinda like length, rows
    public int rows() {
        return matrix.length;
    }
    
    //kinda like length, columns
    public int columns() {
        //if matrix has rows.... then return length of row 1 as columns, else 0
        return matrix.length > 0 ? matrix[0].length : 0;
    }
    
    //initializes empty matrix of n rows and columns... all set to zero.
    public Matrix(int rows, int columns)  {
        matrix = new Number[rows][columns];
        
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = Number.zero();
    }
    
    //sets value of entry in matrix
    public void setValue(int i, int j, Number value) {
        if(i >= matrix.length || j >= matrix[i].length)
            return;
        
        matrix[i][j] = value;
    }
    
    //returns value of entry in matrix
    public Number getValue(int i, int j) {
        if(i >= matrix.length || j >= matrix[i].length) //if out of bounds
            return Number.zero().clone(); //return 0
        
        return matrix[i][j].clone();//returns clone to prevent source
                                    //being changed by reference
    }
    
    //multiplies a row by a factor, returns String descip of step done
    public String multiplyRow(Number factor, int rowNum) {
        if(rowNum >= matrix.length) //checks if not in bounds
            return "Error"; //error...
        
        //multiplies each number in row by the factor
        for(int i = 0; i < matrix[rowNum].length; i++) {
            matrix[rowNum][i].times(factor);
        }
        
        //tests changed row for errors
        testForError(rowNum);
        return factor + "R" + (rowNum + 1) + " -> R" + (rowNum + 1);
    }
    
    //adds row * a multiple to another row, returns string descript of step
    public String addRows(Number factor, int rowFrom, int rowTo) {
        if(rowFrom >= matrix.length || rowTo >= matrix.length) //checks if not in bounds
            return "Error"; //error...
        
        //for each column
        for(int i = 0; i < matrix[rowFrom].length; i++) {
            //number in rowTo plus... number in rowFrom * the multiple
            matrix[rowTo][i].plus(matrix[rowFrom][i].clone().times(factor));
        }
        
        //tests changed row for errors
        testForError(rowTo);
        return factor + "R" + (rowFrom + 1) + " + R" + (rowTo + 1) + " -> R" + (rowTo + 1);
    }
    
    public String switchRows(int rowFrom, int rowTo) {
        if(rowFrom >= matrix.length || rowTo >= matrix.length) //checks if not in bounds
            return "Error"; //error...
        
        //standard switcharoo :)
        Number[] temp = matrix[rowTo];
        matrix[rowTo] = matrix[rowFrom];
        matrix[rowFrom] = temp;
        
        return "R" + (rowFrom + 1) + " <--> R" + (rowTo + 1);
    }
    
    //prints the matrix to channel ch
    public void print(Channel ch) {
        for (int i = 0; i < matrix.length; i++) {
            printRow(i, ch); //print each row...
        }
    }
    
    //prints row row to channel ch
    public void printRow(int row, Channel ch) {
        ch.say("[ "); //bracket
        for (int j = 0; j < matrix[row].length; j++) {
            if(j == matrix.length) //if halfway through row, print divider
                ch.say("  |");
            ch.say(String.format("%3s", matrix[row][j])); //print number
        }
        ch.sayln("  ]"); //end bracket
    }
    
    
    //tests matrix as a whole for errors
    public int testForError() {
        for(int i = 0; i < matrix.length; i++)
            if(testForError(i) != -1) //test each row for errors
                return i;
        return -1; //negative on errors
    }
    
    //tests row row for errors
    public int testForError(int row) {
        if(row >= matrix.length)
            return 3; //error 3, out of bounds
        int loc = getLeadingOne(row); //gets location of leading one/digit
        
        //scenario infinite solutions
        // 0 0 0 | 0
        if(loc > matrix[row].length) {
            infinite = true;
            return 2;
        }
        
        //scenario no solution
        // 0 0 0 | 1
        if(Math.abs(loc) >= matrix.length) { //Math.abs(loc) gets location of
            error = true;                    //leading digit, not just 1
            return 1;
        }
        
        return -1; //negative on errors
    }
    
    //gets leading 1 of row row
    public int getLeadingOne(int row) {
        if(row >= matrix.length) //out of bounds...
            return matrix.length; //no solution
        
        for(int i = 0; i < matrix[row].length; i++)
            if(matrix[row][i].equals(Number.one())) { //if digit is 1
                return i; //return location of digit in row
            } else if(!matrix[row][i].equals(Number.zero())) {
                return -i; //return negative index of first non-zero number
            }
        
        if(matrix.length > 0)
            return matrix[0].length; //if all 0's... 
        else
            return 1; //infinite solutions
    }
    
    //tests columns for being gaussed
    public boolean isGaussed(int column) {
        for(int i = column; i < matrix.length; i++) {
            if(i == column) {
                if(!matrix[i][column].equals(Number.one())) //1 at i, i
                    return false; //if not, false
            } else {
                if(!matrix[i][column].equals(Number.zero())) //0's after that
                    return false; //if not, false
            }
        }
        return true; // no errors, is gaussed
    }
    
    //if matrix has solution
    public boolean hasSolution() {
        return (!error); //if no errors...
    } 
    
    //if matrix has infinite solutions
    public boolean hasInfiniteSolutions() {
        return infinite;
    }
}

