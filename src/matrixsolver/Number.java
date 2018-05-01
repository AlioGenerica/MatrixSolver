/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

/**
 *
 * @author joshua.spisak
 */
public class Number {
    int num;
    int denom;
    
    public Number(int value) {
        this.num = value;
        this.denom = 1;
    }
    
    public Number(int num, int denom) {
        this.num = num;
        this.denom = denom;
        this.reduce();
    }
    
    public Number times(Number factor) {
        if(this.num == 0 || factor.num == 0) {
            this.num = 0;
            this.denom = 1;
            return this;
        }
        this.num = this.num * factor.num;
        this.denom = this.denom * factor.denom;
        this.reduce();
        
        return this;
    }
    
    public Number plus(Number addend) {
        this.num = this.num * addend.denom + addend.num * this.denom;
        this.denom = this.denom * addend.denom;
        this.reduce();
        return this;
    }
    public Number reciprical() {
        if(denom == 0)
            return this.clone();
        
        return new Number(this.denom, this.num);
    }
    
    @Override
    public Number clone() {
        return new Number(this.num, this.denom);
    }
    
    @Override
    public String toString() {
        if(this.denom == 1) {
            return String.format("%4d ", this.num);
        } else {
            return String.format("%2d/%d ", this.num, this.denom);
        }
    }
    
    public boolean equals(Number val) {
        return (this.num == val.num && this.denom == val.denom);
    }
    
    private Number check() {
        if(num == denom) {
            this.num = 1;
            this.denom = 1;
        }
        if(this.num == 0) {
            this.denom = 1;
            return this;
        }
        if(this.denom == 0) {
            this.num = 0;
            this.denom = 1;
            return this;
        }
        
        if(this.denom < 0) {
            this.denom = -this.denom;
            this.num = -this.num;
        }
        return this;
    }
    
    public Number reduce() {
        if(num == denom) {
            this.num = 1;
            this.denom = 1;
        }
        if(this.num == 0) {
            this.denom = 1;
            return this;
        }
        if(this.denom == 0) {
            this.num = 0;
            this.denom = 1;
            return this;
        }
        
        for(int i = 2; i <= Math.sqrt(num) || i <= Math.sqrt(denom); i++) {
            //System.out.println("num1 " + num1 + " num2 " + num2 + " i " + i);
            if(num%i == 0 && denom%i == 0) {
                num = num/i;
                denom = denom/i;
                i--;
            }
            //System.out.println("result " + result + " i " + i + "\n");
        }
        if(num%denom == 0) {
            num = num/denom;
            denom = 1;
        }
        if(denom%num == 0) {
            denom = denom/num;
            num = 1;
        }
        if(this.denom < 0) {
            this.denom = -this.denom;
            this.num = -this.num;
        }
        return this;
    }
    
    public static Number zero() {
        return new Number(0);
    }

    public static Number one() {
        return new Number(1);
    }
}
