package com.mfdcs.crack;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MFDCHook implements IXposedHookLoadPackage {

    private static final String TARGET_PACKAGE = "com.mfdcs";
    private static final String TARGET_CLASS = "com.mfdcs.MfdcApp";
    private static final String TARGET_METHOD = "getPremiere";

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(TARGET_PACKAGE)) {
            return;
        }

        XposedBridge.log("MFDC Hook: Loaded package " + lpparam.packageName);

        // Hook the getPremiere method in com.mfdcs.MfdcApp
        XposedHelpers.findAndHookMethod(TARGET_CLASS, lpparam.classLoader, TARGET_METHOD, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // Log the original return value (for debugging)
                XposedBridge.log("MFDC Hook: Intercepting " + TARGET_METHOD + ". Original return value: " + param.getResult());
                
                // Force the return value to "1" to grant premium access
                param.setResult("1");
                
                // Log the new return value
                XposedBridge.log("MFDC Hook: Changed return value to: " + param.getResult());
            }
        });

        XposedBridge.log("MFDC Hook: Hooked " + TARGET_CLASS + "." + TARGET_METHOD);
    }
}
