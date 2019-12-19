package com.softnology.experimentproject.classes;
/*  Dependency inversion principle is a software design principle which provides us the guidelines to write loosely coupled classes.
    According to the definition of Dependency inversion principle:
    # High-level modules should not depend on low-level modules. Both should depend on abstractions.
    # Abstractions should not depend upon details. Details should depend upon abstractions.*/
class Car {
//his is not an example of dependency injection because the Car class is constructing its own Engine.

/*
    https://developer.android.com/training/dependency-injection#automated-di
    Automated dependency injection
    Reflection-based solutions that connect dependencies at runtime.
    Static solutions that generate the code to connect dependencies at compile time.
*/

    private Engine engine = new Engine();

    void start(){
        engine.start();
    }


}
class MyApp{
    public static void main(String [] args){
        Car car = new Car();
        car.start();
    }
}

