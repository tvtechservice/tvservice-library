package com.pci.service.redux.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;


import com.pci.service.redux.action.ActionType;
import com.pci.service.redux.state.PCIState;
import com.pci.service.util.PCILog;

public class AsyncStore {

      private HandlerThread workerThread;
      private Handler workerHandler;
      private Handler mainHandler;

      private State state;
      private Reducer reducer;
      private Notifier notifier;

    public AsyncStore(  State initialState,   Reducer reducer,   Notifier notifier) {
        this.state = initialState;
        this.reducer = reducer;
        this.notifier = notifier;
        workerThread = new HandlerThread("store-worker");
        workerThread.start();
        workerHandler = new Handler(workerThread.getLooper());
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void dispatch(  final Action action) {
        final AsyncStore store = this;
        workerHandler.post(new Runnable() {
            @Override
            public void run() {
                final State state = store.getState();
                PCILog.d("[DEBUG][State %8s] Dispatch %s(%s)", state.getType(), action.getClass().getSimpleName(), action.getType());


                final State processedState = reducer.reduce(state, action);
                if(doesActionModifyState(action.getType())) {
                    store.setState(processedState);
                    store.stateChangeNotify(state, processedState, action);
                    store.notify(action);
                }
                store.verboseNotify(action);
            }
        });
    }

    public void dispatchTriggeredByInternalRedux(  final Action action) {
        final com.pci.service.redux.core.AsyncStore store = this;
        workerHandler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                final State state = store.getState();
                PCILog.d("[DEBUG][State %8s] Dispatch(TriggeredByInternalRedux) %s(%s)", state.getType(), action.getClass().getSimpleName(), action.getType());

                final State processedState = reducer.reduce(state, action);
                if(doesActionModifyState(action.getType())) {
                    store.setState(processedState);
                    store.stateChangeNotify(state, processedState, action);
                    store.notify(action);
                }
                store.verboseNotify(action);
            }
        });
    }


    public void setState(  final State newState) {
        PCILog.d("[DEBUG][State %8s] => %s", this.state.getType(), newState.getType());
        final PCIState.Type prevStateLevel = this.state.getType();
        final PCIState.Type nextStateLevel = newState.getType();
        if (prevStateLevel == nextStateLevel) {
            this.state = newState;
            this.state.onKeep();
        } else {
            this.state.onLeave(nextStateLevel);
            this.state = newState;
            this.state.onEnter(prevStateLevel);
        }

//        this.state.writePersistent();
    }


    public State getState() {
        return this.state;
    }


    private void notify(  final Action action) {
        final State state = this.state;
        mainHandler.post(() -> notifier.notify(state, action));
    }


    private void stateChangeNotify(  final State prevState,   final State newState,   final Action action) {
        mainHandler.post(() -> notifier.stateChangeNotify(prevState, newState, action));
    }


    public void verboseNotify(  final Action action) {
        final State state = this.state;
        mainHandler.post(() -> notifier.verboseNotify(state, action));
    }

    private boolean doesActionModifyState(final ActionType actionType) {
//        if (actionType == ActionType.PCI_PUSH_RECEIVED ||
//                actionType == ActionType.PCI_SCAN_SOUND ||
//                actionType == ActionType.PCI_SET_AVOID_APP_PERMISSION_MONITOR ||
//                actionType == ActionType.PCI_UPLOAD_ALL_PACKAGES ||
//                actionType == ActionType.PCI_UPLOAD_PCI_PACKAGES ||
//                actionType == ActionType.PCI_UPLOAD_PUSH_RESULT ||
//                actionType == ActionType.PCI_UPLOAD_PUSH_TRIGGERED_APP_INSTALL) {
//            return false;
//        }
//        else return true;
        return true;
    }
}
