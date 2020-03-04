package com.ev.rmi.custom.tool;

import com.ev.rmi.custom.model.RpcReqest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class RpcTools {

    /**
     * 通过Rpc对象利用反射调用
     * @return
     */
    public static Object invoke(Object service, RpcReqest reqest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] args = reqest.getArgs();
        Class[] types = new Class[args.length];
        for (int i=0;i<args.length;i++) {
            types[i] = args[i].getClass();
        }

        Method method = service.getClass().getMethod(reqest.getMethodName(), types);
        return method.invoke(service, reqest.getArgs());
    }
}
