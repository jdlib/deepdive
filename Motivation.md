# Motivation

This article describes why you want to use a fluent assertion API in general and Deep Dive in particular. 

## Assertions in JUnit and TestNG

[JUnit](https://junit.org/junit5/) and [TestNG](https://testng.org/doc/) are great frameworks to run unit tests
with good IDE support and integration into automated builds.

Besides their runtime engines they also provide basic assertion methods 
defined in `org.junit.Assert` and `org.testng.Assert` to test expectations on computed values. 
But only relying on these basic assertions will easily lead to test code which is a quagmire of
assertion statements, with poor readability and maintainability.
Besides, error messages of these basic assertions all too often are not very helpful to spot the error
and in the extreme require to debug the test code for further diagnosis.

And let's admit it: Writing JUnit or TestNG assertions requires heavy typing and isn't exactly fun.

## Enter fluent assertions

Alex Ruiz with his [FEST Assert](https://github.com/alexruiz/fest-assert-2.x) 
to my knowledge pioneered the idea of a fluent assertion API to overcome the shortcoming of JUnit style assertions.
Joel Costigliola forked it to create [AssertJ](https://assertj.github.io/doc/) and together
with his contributors greatly enhanced the API and coverage of the JDK and other libraries. Lately [Google Truth](https://truth.dev
) published their somewhat reduced version of a fluent assertion API. Not to mention Kotlin test libraries that offer fluent APIs 
while at the same time build on Kotlins elegant type system.

All these libraries offer type specific assertion classes which allow to issue assertions for properties
of an actual value of a certain type. Here is an AssertJ example:
   
```java
import static org.assertj.core.api.Assertions.assertThat;

String s = compute(...);
assertThat(s)         // returns a org.assertj.core.api.StringAssert
    .startsWith("A")  // tests a string property
    .contains("123")  // tests a string property
    .endsWith("Z");   // tests a string property
```    
	
If an assertion method fails then the assertion object knows the context and can 
create a helpful error message. The fluent API brings fun into
writing unit tests since code completion of IDEs makes it a breeze to type.
And the resulting code is dense, clean and easy to read, therefore improves maintainability
of the test code.    

## What Deep Dive brings to the party

#### 1. Basic Assertions 
Like `org.junit.Assert` or `org.testng.Assert` it provides a [basic set of assertion methods](UserGuide.md#user-content-basic).

#### 2. Fluent Assertion API 
Like AssertJ it provides a [fluent assertion API](UserGuide.md#user-content-fluent) which covers JDK core classes and can be easily 
extended to any class under test.

#### 3. Allows deep dives into assertion objects
Deep Dive additionally allows you to go back and forth between different assertion objects,
resulting in a smaller API and at the same time greatly increase expressivness:

```java
import static deepdive.ExpectThat.expectThat;
 
String s = compute(...)
expectThat(s)         // returns a deepdive.actual.lang.StringActual
    .startsWith("A")  // tests a string property
    .length()         // returns a deepdive.actual.lang.IntegerActual
        .greater(5)   // tests a string length property
        .lessEq(10)   // tests a string length property
        .back()       // diving up to StringActual 
    .endsWith("Z");   // tests a string property
```
 

> Compare this with AssertJ's `AbstractStringAssert` which defines methods 
> - `hasSize(int)`
> - `hasSizeLessThan(int)`
> - `hasSizeLessThanOrEqualTo(int)`
> - `hasSizeGreaterThan(int)`
> - `hasSizeGreaterThanOrEqualTo(int)` 
>
> to test String lengths: The consequence is that the API of the assert class for Strings gets crowded, duplicates
> functionality of the assert class for Integers without covering all possible assertions on the string length.
> 
> Deep Dive's `StringActual` defines `length(int)` for a simple equality assertion and `length()` (returning a `IntegerActual`) for
> complex assertion on the String length.
>
> The result: A trimmed down API which has greater assertion power than AssertJ.

#### 4. Not-Mode: Negate any assertion
Every Deep Dive assertion can be turned into its negated form by a preceding call to `not()` therefore doubling available 
assertions without bloating the API:
    
```java
import static deepdive.ExpectThat.*;
 
String s = compute(...)
expectThat(s)  // returns deepdive.actual.lang.StringActual
    .startsWith("A")
    .not().startsWith("B");
```
          
> Compare this to AssertJs API which in most cases offers 
> - one method for the base assertion: `StringAssert.startsWith(String)`
> - and another one for the negated form: `StringAssert.doesNotStartWith(CharSequence)`  

#### 5. Coverage of JDK core classes 
Deep Dive offers Actual implementations for JDK core classes which can be used out of the box. 

#### 6. Generate Actual implementations for your domain classes
Deep Dive makes it easy to [create](UserGuide.md#own-actual-implementations) Actual implementations for your own domain classes. 
