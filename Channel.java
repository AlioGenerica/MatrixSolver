/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixsolver;

import java.util.Scanner;

/**
 *
 * @author joshua.spisak
 */
public class Channel {
    boolean on = true;
    Scanner in;
    
    public Channel() {
        in = new Scanner(System.in);
    }
    
    public boolean isOn() {
        return on;
    }
    
    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public void say(String text) {
        if(on)
            System.out.print(text);
    }
    
    public void sayln(String text) {
        if(on)
            System.out.println(text);
    }
    
    public boolean yn() {
        try {
            String yn = in.next();
            in.nextLine();
            return yn.equalsIgnoreCase("Y");
            
        } catch(Exception err) {
            in.nextLine();
            return false;
        }
    }
    
    public int nextInt() {
        while(true)
            try {
                return in.nextInt();
            } catch(Exception err) {
                in.nextLine();
                sayln("Unable to capture integer: \"" + err +"\"");
                say("Please try again: ");
            }
    }
        
}
