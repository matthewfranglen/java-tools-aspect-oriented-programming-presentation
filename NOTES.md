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

This should provide enough context for Spring Aspect Oriented Programming for the discussion of the implementation to make sense.
It's important to realise that Spring Aspect Oriented Programming is quite limited, and a full Aspect Oriented Programming implementation allows for full and arbitrary alteration of code.
We will discuss Aspect J which is such an implementation.

### How is this achieved?

A lot of cool stuff in spring is based around dynamically generated classes which conform to interfaces, allowing them to be substituted for the original code.
If you think back to the Spring Dependency Injection presentation, the session scoped bean was implemented as a dynamic proxy.
That was one example of this technique.
The implementation of Aspect Oriented Programming is another.

#### Custom Class Loader

Spring uses it's own class loader to load all classes.
This allows it to create a proxy which will invoke the appropriate code.

[go through example of this]

### Aspect J and full Aspect Oriented Programming

Aspect J is capable of altering basically anything.
It can change the definitions of classes and interfaces.
It can advise reads and writes of variables.
It can advise method invocation.

It does this by fully altering the code that is loaded.
It can do this at compile time, using a custom compiler, or at run time using a custom class loader.

#### Parsing into an Abstract Syntax Tree

To understand how it can do this it's good to understand how compilers work.
Aspect J is a full compiler and it happens to compile bytecode and an Aspect Oriented Program into bytecode.

Once Aspect J has loaded the advice it can then work on applying the advice to the bytecode.
To do this it must have a representation of the bytecode that it can work with.
This is achieved by parsing the code.
Parsing produces an Abstract Syntax Tree.

[show "parse" of simple expression]

It happens that expressions in bytecode are very shallow, and so the abstract syntax tree is relatively simple.
This makes it easier to work with.
