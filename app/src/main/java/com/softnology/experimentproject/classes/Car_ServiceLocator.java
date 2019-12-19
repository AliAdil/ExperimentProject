package com.softnology.experimentproject.classes;

public class Car_ServiceLocator {
    private Engine_ServiceLocator engine = ServiceLocator.getInstance().getEngine();

    public void start(){
        engine.start();
    }
}


class  MyApplication_ServiceLocator{
    public static void main (String [] args){
        Car_ServiceLocator car = new Car_ServiceLocator();
        car.start();
    }
}
