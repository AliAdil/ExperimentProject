package com.softnology.experimentproject.classes;

class ServiceLocator {
    private static ServiceLocator instance = null;

    private ServiceLocator() {
    }
/*The Java synchronized keyword is an essential tool in concurrent programming in Java.
Its overall purpose is to only allow one thread at a time into a particular section of
code thus allowing us to protect, for example, variables or data from being corrupted
by simultaneous modifications from different threads*/

    static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    Engine_ServiceLocator getEngine(){
        return  new Engine_ServiceLocator();
    }
}
