/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author joshua.spisak
 */
public class MatrixSolver {
    /**
     * @param args the command line arguments
     */
    public static int mode;
    public static Channel info = new Channel();
    public static Channel main = new Channel();
    
    public static void main(String[] args) {
        Random rand = new Random();
        
        main.sayln("Application Matrix Solver Begining.");
        main.say("Application mode? (0 - normal, 1 - inverse, 2 - solve for variables): ");
        mode = main.nextInt();
        
        main.say("How many rows in the matrix?: ");
        
        int m = main.nextInt();
        int n = 0;
        boolean invert = false;
        
        switch (mode) {
            case 1:
                invert = true;
                n = m*2;
                break;
            case 2:
                n = m + 1;
                break;
            default:
                main.say("How many columns in the matrix?");
                n = main.nextInt();
                break;
        }
        
        main.say("Generate random numbers to fill? (y/n): ");
        boolean random = main.yn();
        int range = 4;
        if(random) {
            main.say("Enter maximum number for generation: ");
            range = main.nextInt();
        }
        
        if(m > n) {
            main.sayln("There are not more columns than rows, unable to solve for RREF!");
            return;
        }
        
        main.say("Be verbose? (y/n): ");
        if(!main.yn())
            info.turnOff();
        
        Matrix matrix = new Matrix(m, n);

        for (int i = 0; i < m; i++) {
            if(!random)
                main.sayln("For row " + (i + 1) + ":");
            for (int j = 0; j < n; j++) {
                if (invert && j >= m) {
                    if (j == i + m) {
                        matrix.setValue(i, j, Number.one());
                    } else {
                        matrix.setValue(i, j, Number.zero());
                    }
                } else {
                    if (random) {
                        matrix.setValue(i, j, new Number(rand.nextInt(range - 1) + 1));
                    } else {
                        main.say("   Enter value in column " + (j + 1) + ": ");
                        matrix.setValue(i, j, new Number(main.nextInt()));
                        matrix.print(main);
                    }
                }
            }
        }
        main.sayln("\nMatrix Entered");
        matrix.print(main);
        main.sayln("\nFinding Solution!");
        //Matrix is entered :)
        //Now to solve
        Solve(matrix);
        
        //matrix.reduce();
        
        main.sayln("\n\nThe matrix is solved! :)");
        matrix.print(main);
    }
    
    public static boolean Solve(Matrix matrix) {
        try {
            return Solve(0, matrix);
        } catch (Exception err) {
            main.say(err.toString());
            matrix.print(main);
        }
        return true;
    }
    
    public static boolean Solve(int i, Matrix matrix) {
        if(i > matrix.columns())
            return false;
        
        
        //cases where there is no solution
        if(matrix.hasInfiniteSolutions()) {
            main.sayln("Infinite solutions.");
            return false;
        }
        
        if(!matrix.hasSolution()) {
            main.sayln("No Solution");
            return false;
        }
        
        if(matrix.isGaussed(i)) {
            if(i == matrix.rows() - 1) {
                //in row Echelon Form
                //now to Reduce!
                switch (mode) {
                    case 2:
                        //matrix.print();
                        main.sayln("In REF :)");
                        break;
                    default:
                        return reduce(i, matrix);
                }
            }
            Solve(i + 1, matrix);
        } else {
            if(matrix.getValue(i, i).equals(Number.one())) {
                for(int j = i + 1; j < matrix.rows(); j++) {
                    if(!matrix.getValue(j, i).equals(Number.zero())) {
                        info.sayln(matrix.addRows(matrix.getValue(j, i).times(new Number(-1)), i, j));
                        matrix.print(info);
                    }
                }
                return Solve(i, matrix);
            } else {
                int f = 0;
                for(int j = i + 1; j < matrix.rows(); j++) {
                    if(matrix.getValue(j, i).equals(Number.one())) {
                        info.sayln(matrix.switchRows(i, j));
                        matrix.print(info);
                        return Solve(i, matrix);
                    }
                    if(!matrix.getValue(j, i).equals(Number.zero()))
                        f = j;
                }
                if(!matrix.getValue(i, i).equals(Number.zero())) {
                    info.sayln(matrix.multiplyRow(matrix.getValue(i, i).reciprical(), i));
                    matrix.print(info);
                } else {
                    info.sayln(matrix.switchRows(f, i));
                    matrix.print(info);
                }
                return Solve(i, matrix);
            }
        }
        return false;
    }
    
    public static boolean reduce(int i, Matrix matrix) {
        if(i == 0)
            return true;
        
        for(int j = i-1; j >= 0; j--) {
            if(!matrix.getValue(j, i).equals(Number.zero())) {
                info.sayln(matrix.addRows(matrix.getValue(j, i).times(new Number(-1)), i, j));
                matrix.print(info);
            }
        }
        return reduce(i - 1, matrix);
    }
}
