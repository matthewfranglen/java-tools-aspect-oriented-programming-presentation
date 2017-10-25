Overview
--------

This will cover how stuff works under the hood:

- Spring AOP, proxy pattern, dynamic proxies in java
- code coverage, byte/source code injection, java agents
- sampling vs profiling

Spring Aspect Oriented Programming
----------------------------------

[Show what problem slide]

### What problem does this solve?

This allows implementation of cross cutting concerns without repetition.

[Show cross cutting concerns slide]

Applications have paths through them.
Different paths have different behaviours.
Viewing the home page is quite different to logging in.

There are behaviours which you always want to have.
 * Security
 * Logging
 * Audit

For these to be effective they must be implemented universally and consistently.
Writing repetitive code is prone to error.
Aspect Oriented Programming allows you to implement cross cutting concerns without repetition.

### What is Aspect Oriented Programming?

Since Aspect Oriented Programming is only rarely used it's best to actually introduce it properly rather than immediately jump to implementation.

#### Terms

 * Join Point is a point in your base application which is a suitable target.
 * Pointcut is a way to identify one or more Join Points in your application.
 * Advice is an action to take.
 * Weaving is the application of Advice to Pointcuts.

Spring Aspect Oriented programming is implemented using dynamically generated proxies.
This allows the invocation of methods to be advised.
In practice this is usually sufficient, when it is not there are more sophisticated Aspect Oriented Programming tools available.

#### Pointcuts

Pointcuts are created by annotating methods.
The annotation controls the join points that are matched.
This following pointcut would match the execution of the method named transfer.
```
@Pointcut("execution(* transfer(..))")// the pointcut expression
private void anyOldTransfer() {}// the pointcut signature
```

You can combine pointcuts by referring to the name of the method that was annotated.
This allows more complex pointcuts to be broken down.
```
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {}

@Pointcut("within(com.xyz.someapp.trading..*)")
private void inTrading() {}

@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {}
```

Pointcuts are in classes so that you can reference them when you wish to apply advice.
```
@Aspect
class PointcutCollection {
    @Pointcut("execution(* transfer(..))")// the pointcut expression
    private void anyOldTransfer() {}// the pointcut signature
}
```

#### Advice

In Spring Aspect Oriented Programming you can only advise methods.
That means that the advice you give is limited to what you can replace the method call with.

 * You can substitute the method call
 * You can run code before
 * You can alter the return value
 * You can inspect exceptions

##### Advice and Weaving

Advice comes with the pointcut to advise, so the declaration of advice is enough to trigger weaving.

```
@Aspect
public class AfterReturningExample {

        @AfterReturning(
                pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",
                returning="retVal")
        public void doAccessCheck(Object retVal) {
                // ...
        }

}
```
