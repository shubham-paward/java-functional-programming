# Java Functional Programming Learning Guide

## 🎯 Learning Path for Beginners to Advanced

This guide will take you from zero to hero in Java functional programming, with practical examples and real-world applications.

## 📚 Table of Contents

1. [Getting Started](#getting-started)
2. [Learning Path](#learning-path)
3. [Key Concepts](#key-concepts)
4. [Practice Exercises](#practice-exercises)
5. [Common Pitfalls](#common-pitfalls)
6. [Advanced Topics](#advanced-topics)
7. [Resources](#resources)

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Basic understanding of Java syntax
- Familiarity with collections (List, Set, Map)

### Setup
```bash
# Clone or download the project
cd java-functional-programming

# Compile the project
mvn compile

# Run examples
mvn exec:java -Dexec.mainClass="com.functionalprogramming.basics.LambdaExpressions"
```

## 📖 Learning Path

### Phase 1: Fundamentals (Week 1-2)

#### 1.1 Lambda Expressions
**Start with:** `src/main/java/com/functionalprogramming/basics/LambdaExpressions.java`

**What you'll learn:**
- What are lambda expressions
- How to write basic lambdas
- Lambda syntax and best practices
- When to use lambdas vs anonymous classes

**Key Concepts:**
```java
// Basic lambda
Runnable r = () -> System.out.println("Hello World");

// Lambda with parameters
Function<String, Integer> getLength = s -> s.length();

// Lambda with multiple statements
Function<String, String> processor = text -> {
    System.out.println("Processing: " + text);
    return text.toUpperCase();
};
```

**Practice:**
- Convert anonymous classes to lambdas
- Write lambdas for common operations (filtering, mapping, etc.)

#### 1.2 Method References
**Study:** `src/main/java/com/functionalprogramming/basics/MethodReferences.java`

**What you'll learn:**
- Four types of method references
- When to use method references vs lambdas
- Static, instance, and constructor references

**Key Concepts:**
```java
// Static method reference
Function<String, Integer> parseInt = Integer::parseInt;

// Instance method reference
Consumer<String> printer = System.out::println;

// Constructor reference
Function<String, Person> personFactory = Person::new;
```

**Practice:**
- Convert lambdas to method references where possible
- Use method references in stream operations

#### 1.3 Functional Interfaces
**Study:** `src/main/java/com/functionalprogramming/basics/FunctionalInterfaces.java`

**What you'll learn:**
- Built-in functional interfaces
- Creating custom functional interfaces
- Function composition and chaining

**Key Concepts:**
```java
// Built-in interfaces
Predicate<String> isLong = s -> s.length() > 5;
Function<String, Integer> getLength = String::length;
Consumer<String> printer = System.out::println;
Supplier<String> generator = () -> "Hello";

// Custom interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b, String operation);
}
```

**Practice:**
- Create custom functional interfaces for your domain
- Chain functions using default methods

### Phase 2: Intermediate (Week 3-4)

#### 2.1 Streams
**Study:** `src/main/java/com/functionalprogramming/intermediate/Streams.java`

**What you'll learn:**
- Stream creation and operations
- Intermediate vs terminal operations
- Parallel streams
- Performance considerations

**Key Concepts:**
```java
// Basic stream operations
List<String> result = names.stream()
    .filter(name -> name.length() > 4)
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Parallel processing
long sum = numbers.parallelStream()
    .mapToLong(Integer::longValue)
    .sum();
```

**Practice:**
- Process collections using streams
- Implement complex data transformations
- Use parallel streams for performance

#### 2.2 Optional
**Study:** `src/main/java/com/functionalprogramming/intermediate/Optional.java`

**What you'll learn:**
- Safe null handling
- Optional operations (map, filter, flatMap)
- Best practices for Optional usage

**Key Concepts:**
```java
// Safe null handling
Optional<String> name = Optional.ofNullable(getName());
String result = name.orElse("Default Name");

// Chaining operations
String theme = userService.findUserById(1)
    .map(User::getProfile)
    .map(Profile::getSettings)
    .map(Settings::getTheme)
    .orElse("default");
```

**Practice:**
- Replace null checks with Optional
- Chain Optional operations safely
- Handle missing data gracefully

#### 2.3 Collectors
**Study:** `src/main/java/com/functionalprogramming/intermediate/Collectors.java`

**What you'll learn:**
- Transforming streams to collections
- Grouping and partitioning data
- Statistical operations
- Custom collectors

**Key Concepts:**
```java
// Grouping
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));

// Partitioning
Map<Boolean, List<Integer>> evenOdd = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

// Statistics
IntSummaryStatistics stats = numbers.stream()
    .collect(Collectors.summarizingInt(Integer::intValue));
```

**Practice:**
- Group data by different criteria
- Calculate statistics and summaries
- Create custom collectors

### Phase 3: Advanced (Week 5-6)

#### 3.1 Real-world Applications
**Study:** `src/main/java/com/functionalprogramming/examples/DataProcessing.java`

**What you'll learn:**
- Applying FP to real problems
- Data processing pipelines
- Error handling in functional style
- Performance optimization

**Key Concepts:**
```java
// Complex data processing
Map<String, Double> avgSalary = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingInt(Employee::getSalary)
    ));

// Safe data validation
List<ValidatedPerson> valid = rawData.stream()
    .map(this::parsePerson)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
```

**Practice:**
- Implement data processing pipelines
- Handle errors functionally
- Optimize performance

## 🎯 Key Concepts

### 1. Immutability
- Don't modify data after creation
- Use immutable collections
- Return new objects instead of modifying existing ones

### 2. Pure Functions
- Same input always produces same output
- No side effects
- Easy to test and reason about

### 3. Function Composition
- Combine simple functions to create complex ones
- Use `andThen()` and `compose()` methods
- Build reusable, composable functions

### 4. Lazy Evaluation
- Streams are lazy by default
- Operations only execute when terminal operation is called
- Enables efficient processing of large datasets

### 5. Declarative Programming
- Focus on what you want to achieve, not how
- Use streams and functional operations
- More readable and maintainable code

## 🏋️ Practice Exercises

### Beginner Exercises

1. **Lambda Basics**
   - Convert all anonymous classes to lambdas
   - Write lambdas for common operations (add, multiply, compare)

2. **Method References**
   - Convert lambdas to method references where possible
   - Use constructor references

3. **Functional Interfaces**
   - Create custom functional interfaces
   - Chain functions using default methods

### Intermediate Exercises

1. **Stream Processing**
   - Process a list of employees to find average salary by department
   - Filter and transform data using streams
   - Use parallel streams for large datasets

2. **Optional Handling**
   - Replace null checks with Optional
   - Chain Optional operations safely
   - Handle missing data gracefully

3. **Data Grouping**
   - Group data by different criteria
   - Calculate statistics and summaries
   - Use partitioning for binary classification

### Advanced Exercises

1. **Data Processing Pipeline**
   - Implement a complete data processing pipeline
   - Handle errors functionally
   - Optimize performance

2. **Custom Collectors**
   - Create custom collectors for specific needs
   - Implement complex reduction operations

3. **Event Processing**
   - Process streams of events
   - Implement real-time analytics
   - Handle backpressure and error recovery

## ⚠️ Common Pitfalls

### 1. Overusing Lambdas
```java
// Don't do this
if (list.stream().anyMatch(x -> x.equals("test"))) {
    // ...
}

// Do this instead
if (list.contains("test")) {
    // ...
}
```

### 2. Ignoring Performance
```java
// Be careful with parallel streams
// They have overhead and aren't always faster
List<String> result = smallList.stream()  // Don't parallelize small lists
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

### 3. Misusing Optional
```java
// Don't use Optional for collections
Optional<List<String>> optionalList = Optional.of(list);  // Wrong

// Use empty collections instead
List<String> emptyList = Collections.emptyList();  // Right
```

### 4. Not Handling Exceptions
```java
// Don't ignore exceptions in lambdas
list.stream()
    .map(s -> Integer.parseInt(s))  // Can throw NumberFormatException
    .collect(Collectors.toList());

// Handle exceptions properly
list.stream()
    .map(this::safeParseInt)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
```

## 🚀 Advanced Topics

### 1. Monads
- Understanding the monad pattern
- Implementing custom monads
- Error handling with Either type

### 2. Currying
- Breaking down functions with multiple parameters
- Partial application
- Function factories

### 3. Function Composition
- Advanced composition techniques
- Pipeline patterns
- Reactive programming

### 4. Performance Optimization
- Stream optimization techniques
- Memory usage patterns
- Parallel processing strategies

## 📚 Resources

### Books
- "Java 8 in Action" by Raoul-Gabriel Urma
- "Functional Programming in Java" by Pierre-Yves Saumont
- "Effective Java" by Joshua Bloch

### Online Resources
- [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/)
- [Baeldung Java Functional Programming](https://www.baeldung.com/java-functional-programming)
- [Java Streams Tutorial](https://www.baeldung.com/java-8-streams)

### Practice Platforms
- [LeetCode](https://leetcode.com/) - Algorithm problems
- [HackerRank](https://www.hackerrank.com/) - Coding challenges
- [Codewars](https://www.codewars.com/) - Functional programming katas

## 🎉 Next Steps

1. **Master the Basics**: Ensure you understand lambda expressions, method references, and functional interfaces
2. **Practice with Streams**: Work through the stream examples and exercises
3. **Apply to Real Projects**: Use functional programming in your actual work
4. **Explore Advanced Topics**: Dive into monads, currying, and advanced patterns
5. **Join the Community**: Participate in functional programming discussions and meetups

## 🤝 Contributing

Feel free to contribute to this project by:
- Adding more examples
- Improving documentation
- Fixing bugs
- Suggesting new features

Happy Learning! 🚀
