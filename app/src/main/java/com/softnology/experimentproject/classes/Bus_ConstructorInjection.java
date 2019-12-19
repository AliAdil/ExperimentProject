package com.softnology.experimentproject.classes;
 class Bus_ConstructorInjection {
    private final BusEngine_ConstructorInjection busEngineConstructorInjection;

    Bus_ConstructorInjection(BusEngine_ConstructorInjection _busEngineConstructorInjection){
        this.busEngineConstructorInjection = _busEngineConstructorInjection;
    }

    void start(){
        busEngineConstructorInjection.start();
    }

}

class MyApplication{
     public  static  void  main (String[] args){
         BusEngine_ConstructorInjection busEngineConstructorInjection = new BusEngine_ConstructorInjection();
         Bus_ConstructorInjection busConstructorInjection = new Bus_ConstructorInjection(busEngineConstructorInjection);
         busConstructorInjection.start();
     }
}
