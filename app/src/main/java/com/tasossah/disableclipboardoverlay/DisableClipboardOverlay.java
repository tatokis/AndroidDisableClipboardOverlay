package com.tasossah.disableclipboardoverlay;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook;

import android.content.ClipData;
import android.util.Log;

public class DisableClipboardOverlay implements IXposedHookLoadPackage {
    static String tag = "DisableClipboardOverlay";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui")) {
            return;
        }

        Log.d(tag, "Hooking into systemui");

        XposedHelpers.findAndHookMethod("com.android.systemui.clipboardoverlay.ClipboardListener", lpparam.classLoader,
                "shouldSuppressOverlay", ClipData.class, String.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        Log.d(tag, "Done!");
    }
}
