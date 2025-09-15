#!/bin/bash

echo "=== Java Functional Programming Demo ==="
echo "======================================="
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

echo "Compiling project..."
mvn compile -q

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

echo "Compilation successful!"
echo

echo "Running Basic Functional Programming Examples..."
echo "=============================================="
mvn exec:java -Dexec.mainClass="com.functional.demo.FunctionalProgrammingBasics" -q

echo
echo "Running Advanced Functional Programming Examples..."
echo "================================================="
mvn exec:java -Dexec.mainClass="com.functional.demo.AdvancedFunctionalConcepts" -q

echo
echo "Running Practical Examples..."
echo "============================"
mvn exec:java -Dexec.mainClass="com.functional.demo.PracticalExamples" -q

echo
echo "Running Tests..."
echo "==============="
mvn test -q

echo
echo "Demo completed successfully!"
