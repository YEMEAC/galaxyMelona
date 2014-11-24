/*
  Representar la posicion de una x,y 
 */

package com.idi.Entity;

public  class Posicion {
    
    int x;
    int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Posicion(){
        x=0;
        y=0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    } 
}
