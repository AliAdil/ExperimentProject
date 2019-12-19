package com.softnology.experimentproject.classes;

class Car_FieldInjection {

    private Engine_FieldInjection engine;

    void setEngine(Engine_FieldInjection engine) {
        this.engine = engine;
    }

    void start(){
        engine.start();
    }
}

class MyApp_FieldInjection{
    public static void main (String[] args){
        Car_FieldInjection car = new Car_FieldInjection();
        car.setEngine(new Engine_FieldInjection());
        car.start();
    }
}
