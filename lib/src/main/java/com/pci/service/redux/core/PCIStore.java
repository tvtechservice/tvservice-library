package com.pci.service.redux.core;

import android.content.Context;


import com.pci.service.redux.notifier.NotifierRoot;
import com.pci.service.redux.reducer.ReducerRoot;
import com.pci.service.redux.state.PCIState;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class PCIStore extends AsyncStore {

    private static PCIStore singleton;

    private final NotifierRoot mNotifierRoot;

    public static PCIStore getInstance( final Context context) {
        if (singleton == null) {
            synchronized (PCIStore.class) {
                if (singleton == null) {
                    singleton = new PCIStore(
                        PCIState.from(context),
                        new ReducerRoot(),
                        new NotifierRoot());
                }
            }
        }
        return singleton;
    }

    private PCIStore( PCIState state,  ReducerRoot reducerRoot,  NotifierRoot notifierRoot) {
        super(state, reducerRoot, notifierRoot);
        mNotifierRoot = notifierRoot;
    }

    public void addNotifier(String name, PCINotifier notifier) {
        mNotifierRoot.addSubNotifier(name, notifier);
    }

    public boolean removeNotifier(String name) {
        return mNotifierRoot.removeSubNotifier(name);
    }

    public void addStateChangeNotifier(String name, PCIStateChangeNotifier notifier) {
        mNotifierRoot.addSubStateChangeNotifier(name, notifier);
    }

    public boolean removeStateChangeNotifier(String name) {
        return mNotifierRoot.removeSubStateChangeNotifier(name);
    }

    public void addVerboseNotifier(String name, PCINotifier notifier) {
        mNotifierRoot.addSubVerboseNotifier(name, notifier);
    }

    public boolean removeVerboseNotifier(String name) {
        return mNotifierRoot.removeSubVerboseNotifier(name);
    }

}
