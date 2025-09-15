package com.functionalprogramming.basics;

import java.util.*;
import java.util.function.*;

/**
 * Lambda Expressions - The Foundation of Functional Programming in Java
 * 
 * A lambda expression is a short block of code which takes in parameters and returns a value.
 * Lambda expressions are similar to methods, but they do not need a name and can be implemented
 * right in the body of a method.
 * 
 * Syntax: (parameters) -> expression
 *         (parameters) -> { statements; }
 */
public class LambdaExpressions {
    
    public static void main(String[] args) {
        System.out.println("=== Lambda Expressions Examples ===\n");
        
        // Example 1: Basic Lambda with Runnable
        demonstrateBasicLambda();
        
        // Example 2: Lambda with parameters
        demonstrateParameterizedLambda();
        
        // Example 3: Lambda with collections
        demonstrateCollectionLambda();
        
        // Example 4: Lambda with functional interfaces
        demonstrateFunctionalInterfaceLambda();
        
        // Example 5: Lambda with multiple statements
        demonstrateMultiStatementLambda();
    }
    
    /**
     * Example 1: Basic Lambda Expression
     * 
     * Before Java 8, we had to create anonymous inner classes for simple tasks.
     * Lambda expressions make this much cleaner and more readable.
     */
    private static void demonstrateBasicLambda() {
        System.out.println("1. Basic Lambda Expression:");
        
        // Traditional way (before Java 8)
        Runnable traditionalRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("  Traditional: Hello from anonymous class!");
            }
        };
        
        // Lambda way (Java 8+)
        Runnable lambdaRunnable = () -> System.out.println("  Lambda: Hello from lambda!");
        
        // Execute both
        traditionalRunnable.run();
        lambdaRunnable.run();
        System.out.println();
    }
    
    /**
     * Example 2: Lambda with Parameters
     * 
     * Lambdas can take parameters. The type can be inferred by the compiler
     * in most cases, making the code more concise.
     */
    private static void demonstrateParameterizedLambda() {
        System.out.println("2. Lambda with Parameters:");
        
        // Lambda with one parameter
        Consumer<String> greeter = name -> System.out.println("  Hello, " + name + "!");
        greeter.accept("Alice");
        greeter.accept("Bob");
        
        // Lambda with two parameters
        BinaryOperator<Integer> adder = (a, b) -> a + b;
        System.out.println("  5 + 3 = " + adder.apply(5, 3));
        
        // Lambda with explicit types (when needed)
        BinaryOperator<Integer> multiplier = (Integer a, Integer b) -> a * b;
        System.out.println("  4 * 6 = " + multiplier.apply(4, 6));
        System.out.println();
    }
    
    /**
     * Example 3: Lambda with Collections
     * 
     * Lambdas work beautifully with collections, making operations
     * more readable and functional.
     */
    private static void demonstrateCollectionLambda() {
        System.out.println("3. Lambda with Collections:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        // Traditional way
        System.out.println("  Traditional approach:");
        for (String name : names) {
            if (name.length() > 4) {
                System.out.println("    " + name.toUpperCase());
            }
        }
        
        // Lambda way
        System.out.println("  Lambda approach:");
        names.stream()
             .filter(name -> name.length() > 4)
             .map(String::toUpperCase)
             .forEach(name -> System.out.println("    " + name));
        System.out.println();
    }
    
    /**
     * Example 4: Lambda with Functional Interfaces
     * 
     * Java provides many built-in functional interfaces in java.util.function
     * that work perfectly with lambda expressions.
     */
    private static void demonstrateFunctionalInterfaceLambda() {
        System.out.println("4. Lambda with Functional Interfaces:");
        
        // Predicate<T> - takes one argument, returns boolean
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("  Is 4 even? " + isEven.test(4));
        System.out.println("  Is 7 even? " + isEven.test(7));
        
        // Function<T, R> - takes one argument, returns a result
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("  Length of 'Hello': " + stringLength.apply("Hello"));
        
        // Supplier<T> - takes no arguments, returns a result
        Supplier<Double> randomNumber = () -> Math.random();
        System.out.println("  Random number: " + randomNumber.get());
        
        // Consumer<T> - takes one argument, returns void
        Consumer<String> printer = s -> System.out.println("  Printing: " + s);
        printer.accept("Functional Programming is awesome!");
        System.out.println();
    }
    
    /**
     * Example 5: Lambda with Multiple Statements
     * 
     * When you need multiple statements in a lambda, use curly braces
     * and explicit return statement.
     */
    private static void demonstrateMultiStatementLambda() {
        System.out.println("5. Lambda with Multiple Statements:");
        
        // Lambda with multiple statements
        Function<String, String> processor = text -> {
            System.out.println("    Processing: " + text);
            String processed = text.toUpperCase().trim();
            System.out.println("    Processed: " + processed);
            return processed;
        };
        
        String result = processor.apply("  hello world  ");
        System.out.println("  Final result: " + result);
        System.out.println();
    }
    
    /**
     * Key Takeaways:
     * 
     * 1. Lambda expressions make code more concise and readable
     * 2. They work with functional interfaces (interfaces with one abstract method)
     * 3. Type inference reduces boilerplate code
     * 4. They enable functional programming style in Java
     * 5. Perfect for working with collections and streams
     * 
     * Common Functional Interfaces:
     * - Predicate<T>: boolean test(T t)
     * - Function<T,R>: R apply(T t)
     * - Consumer<T>: void accept(T t)
     * - Supplier<T>: T get()
     * - UnaryOperator<T>: T apply(T t)
     * - BinaryOperator<T>: T apply(T t, T u)
     */
}
