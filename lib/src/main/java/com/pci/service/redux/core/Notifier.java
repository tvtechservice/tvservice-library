package com.pci.service.redux.core;



public interface Notifier {
     void notify( State state,  Action action);
     void stateChangeNotify( State prevState,  State newState,  Action action);
     void verboseNotify( State state,  Action action);
}
