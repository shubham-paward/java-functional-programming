# Java Functional Programming Examples

This project demonstrates Java functional programming concepts in a progressive manner, suitable for both beginners and experienced developers.

## Table of Contents

1. [What is Functional Programming?](#what-is-functional-programming)
2. [Project Structure](#project-structure)
3. [Getting Started](#getting-started)
4. [Learning Path](#learning-path)
5. [Key Concepts Covered](#key-concepts-covered)

## What is Functional Programming?

Functional Programming (FP) is a programming paradigm that treats computation as the evaluation of mathematical functions. In Java, this means:

- **Functions as First-Class Citizens**: Functions can be passed as arguments, returned from other functions, and assigned to variables
- **Immutability**: Data doesn't change after creation
- **Pure Functions**: Functions that always return the same output for the same input and have no side effects
- **Higher-Order Functions**: Functions that take other functions as parameters or return functions

## Project Structure

```
src/main/java/com/functionalprogramming/
├── basics/           # Core concepts for beginners
│   ├── LambdaExpressions.java
│   ├── MethodReferences.java
│   └── FunctionalInterfaces.java
├── intermediate/     # Intermediate concepts
│   ├── Streams.java
│   ├── Optional.java
│   └── Collectors.java
├── advanced/         # Advanced concepts
│   ├── Monads.java
│   ├── Currying.java
│   └── Composition.java
└── examples/         # Real-world examples
    ├── DataProcessing.java
    ├── EventHandling.java
    └── FunctionalDesign.java
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Examples

```bash
# Compile the project
mvn compile

# Run specific examples
mvn exec:java -Dexec.mainClass="com.functionalprogramming.basics.LambdaExpressions"
mvn exec:java -Dexec.mainClass="com.functionalprogramming.intermediate.Streams"

# Run all tests
mvn test
```

## Learning Path

### 1. Basics (Start Here)
- **Lambda Expressions**: Anonymous functions that can be passed around
- **Method References**: Shorthand for lambda expressions
- **Functional Interfaces**: Interfaces with exactly one abstract method

### 2. Intermediate
- **Streams**: Functional-style operations on collections
- **Optional**: Handling null values safely
- **Collectors**: Reducing streams to collections

### 3. Advanced
- **Monads**: Wrapper types that provide context
- **Currying**: Breaking down functions with multiple parameters
- **Composition**: Combining functions to create new ones

### 4. Real-World Examples
- **Data Processing**: Processing collections functionally
- **Event Handling**: Using functional approaches for events
- **Functional Design**: Designing applications with functional principles

## Key Concepts Covered

### Lambda Expressions
```java
// Traditional approach
Runnable r1 = new Runnable() {
    public void run() {
        System.out.println("Hello World");
    }
};

// Lambda approach
Runnable r2 = () -> System.out.println("Hello World");
```

### Streams
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<String> filtered = names.stream()
    .filter(name -> name.length() > 4)
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

### Optional
```java
Optional<String> name = Optional.ofNullable(getName());
String result = name.orElse("Default Name");
```

## Why Learn Functional Programming?

1. **Cleaner Code**: More readable and concise
2. **Better Error Handling**: Optional and Either types
3. **Parallel Processing**: Easier to write concurrent code
4. **Testability**: Pure functions are easier to test
5. **Industry Demand**: Many modern frameworks use FP concepts

## Next Steps

1. Start with the `basics` package
2. Run each example and understand the output
3. Modify the examples to experiment
4. Move to `intermediate` and `advanced` packages
5. Try the real-world examples in the `examples` package

Happy Learning! 🚀
