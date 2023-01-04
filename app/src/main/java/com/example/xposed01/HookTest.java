package com.example.xposed01;

import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {

    private static final String HOOK_APP_NAME = "com.ss.android.ugc.aweme.lite";

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Log.i("HookTest", lpparam.packageName);
        //性能优化，避免操作无关app
        if (!lpparam.packageName.equals(HOOK_APP_NAME))
            return;
        if (lpparam.packageName.equals(HOOK_APP_NAME)) {
            log(" 劫持成功！！!");

            log("XposedMainInit handleLoadPackage 执行");
            log("Loaded app: " + lpparam.packageName);
            XposedHelpers.findAndHookMethod("com.ss.android.ugc.aweme.splash.SplashActivity",//hook的类
                    lpparam.classLoader,
                    "onResume", // 被Hook的函数
                    //Map.class, 被Hook函数的第一个参数  (此处没有，只是举个例子)
                    //String.class, 被Hook函数的第二个参数String
                    new XC_MethodHook() {
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            // 参数获取
                            log("入口函数执行");
                            //参数1
//                            log("beforeHookedMethod map:" + param.args[0]);
//                            //参数2
//                            log("beforeHookedMethod hash_key:" + param.args[1]);
//                            //函数返回值
//                            log("beforeHookedMethod result:" + param.getResult());
                        }

                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            log("afterHookedMethod result:" + param.getResult());
//                            param.setResult("你已被劫持");
                        }
                    });
        }
    }

    private void log(String s) {
        XposedBridge.log(s);
    }
}