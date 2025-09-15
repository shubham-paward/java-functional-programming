# Contributing to Java Functional Programming Examples

Thank you for your interest in contributing to this project! This document provides guidelines for contributing to the Java Functional Programming Examples repository.

## 🤝 How to Contribute

### 1. Fork the Repository
- Click the "Fork" button on the GitHub repository page
- Clone your forked repository to your local machine

### 2. Create a Feature Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Your Changes
- Follow the existing code style and conventions
- Add comprehensive comments and documentation
- Include tests for new functionality
- Update documentation as needed

### 4. Test Your Changes
```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Run specific examples
mvn exec:java -Dexec.mainClass="com.functionalprogramming.basics.LambdaExpressions"
```

### 5. Commit Your Changes
```bash
git add .
git commit -m "Add: Brief description of your changes"
```

### 6. Push and Create Pull Request
```bash
git push origin feature/your-feature-name
```
Then create a Pull Request on GitHub.

## 📝 Code Style Guidelines

### Java Code Style
- Use meaningful variable and method names
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Add comprehensive comments explaining complex logic
- Keep methods focused and single-purpose
- Use functional programming patterns where appropriate

### Documentation
- Add JavaDoc comments for public methods
- Include inline comments for complex logic
- Update README.md if adding new examples
- Maintain consistent formatting

### Example Structure
```java
/**
 * Brief description of the example
 * 
 * This example demonstrates [specific concept] using functional programming.
 * It shows how to [specific use case] in a practical way.
 */
public class YourExample {
    
    public static void main(String[] args) {
        System.out.println("=== Your Example Title ===\n");
        
        // Example 1: Basic concept
        demonstrateBasicConcept();
        
        // Example 2: Advanced concept
        demonstrateAdvancedConcept();
    }
    
    /**
     * Example 1: Basic concept demonstration
     * 
     * This method shows [specific concept] with clear examples.
     */
    private static void demonstrateBasicConcept() {
        System.out.println("1. Basic Concept:");
        
        // Your implementation here
        
        System.out.println();
    }
}
```

## 🎯 Types of Contributions

### New Examples
- Add new functional programming examples
- Cover different domains (e.g., machine learning, IoT, etc.)
- Include both basic and advanced concepts
- Provide real-world use cases

### Improvements
- Fix bugs or issues
- Improve existing examples
- Add better error handling
- Optimize performance

### Documentation
- Improve existing documentation
- Add more detailed explanations
- Create tutorials or guides
- Translate documentation

### Tests
- Add unit tests for existing examples
- Improve test coverage
- Add integration tests
- Add performance tests

## 🚫 What Not to Contribute

- Code that doesn't follow functional programming principles
- Examples without proper documentation
- Code that doesn't compile or run
- Duplicate examples
- Code with poor error handling

## 📋 Pull Request Guidelines

### Before Submitting
- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] Code follows style guidelines
- [ ] Documentation is updated
- [ ] Examples run successfully
- [ ] No duplicate code

### Pull Request Template
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Code improvement

## Testing
- [ ] Code compiles
- [ ] Tests pass
- [ ] Examples run successfully

## Screenshots (if applicable)
Add screenshots of example outputs

## Additional Notes
Any additional information about the changes
```

## 🐛 Reporting Issues

### Bug Reports
When reporting bugs, please include:
- Description of the issue
- Steps to reproduce
- Expected behavior
- Actual behavior
- Environment details (Java version, OS, etc.)
- Screenshots if applicable

### Feature Requests
When requesting features, please include:
- Description of the feature
- Use case and motivation
- Proposed implementation approach
- Examples of how it would be used

## 📞 Getting Help

- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For questions and general discussion
- **Pull Request Comments**: For specific code review feedback

## 🙏 Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes
- Project documentation

## 📝 License

By contributing, you agree that your contributions will be licensed under the same MIT License that covers the project.

---

Thank you for contributing to the Java Functional Programming Examples project! 🚀
