package com.matthew.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AppAspect {

    @Pointcut("call(* java.io.PrintStream.println(java.lang.String))")
    public void printStreamPrintln() {}

    @Around("printStreamPrintln()")
    public void substitute(ProceedingJoinPoint point) throws Throwable {
        String originalArgument = (String)point.getArgs()[0];
        String newArgument = "Computer says no";

        point.proceed(new Object[]{ newArgument });
    }
}
