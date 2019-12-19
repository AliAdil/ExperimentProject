package com.softnology.experimentproject.classes;
 class Bus {
    private final BusEngine busEngine;

    Bus(BusEngine _busEngine){
        this.busEngine = _busEngine;
    }

    void start(){
        busEngine.start();
    }

}

class MyApplication{
     public  static  void  main (String[] args){
         BusEngine busEngine = new BusEngine();
         Bus bus  = new Bus(busEngine);
         bus.start();
     }
}
