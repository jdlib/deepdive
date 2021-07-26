# Deep Dive User Guide

This user guide describes the fluent API of Deep Dive, it's basic assert methods,
fine points about error messages and AssertionErrors and how to create own
assertion objects.

- [Introduction](#introduction)
- [Fluent API](#fluent-api)
- [Basic assertions](#basic-assertions)
- [Error messages](#error-messages)
- [AssertionErrors](#assertionerrors)
- [Own Actual implementations](#own-actual-implementations)

## Introduction

A unit test computes values and asserts that these actual values meet the expectations,
else the test fails (usually by throwing `AssertionErrors`).

`org.junit.Assert` and `òrg.testng.Assert` offer a basic set of assert methods to test expectations, 
e.g. to test if an actual value equals the expected value. Deep Dive offers an [own version of basic assert methods](#basic-assertions) 
to replace JUnit or TestNG assertions.

A fluent assertion API goes beyond basic assert methods: Given an actual computed value
such an API provides a type specific assertion object which allows you to test properties of the actual value.
Deep Dive offers a [fluent assertion API](#fluent-api) like AssertJ or Google Truth but additionally allows you to
dive deep, i.e. going back and forth between different assertion objects. This results in a small library and API
while at the same time allows to express a lot of assertions.


## Fluent API

As starting point import the `expectThat` methods from `deepdive.ExpectThat`  

```java
import static deepdive.ExpectThat.*;
```
Now given actual values

```java
String s = ...
Map<Integer,String> map = ...
```
test your expectations on these values:

```java
expectThat(s)
    .startsWith("a")
    .endsWith("z");
expectThat(map)
    .containsKey(1)
    .containsValue("p")
    .size(4);
```

These two calls of `expectThat` return a `deepdive.actual.lang.StringActual` and a `deepdive.actual.util.MapActual`
which provide type specific assertion methods. Both are derived from `deepdive.actual.Actual`. 
But given the factory methods in `ExpectThat` you will rarely need to create an `Actual` object by yourself.


#### Diving Deep    
 
Besides methods to test expectations `Actual` classes may provide additional methods which return
other `Actual` objects. This allows you to dive deep into subproperties of the starting actual value. We 
recommend to use indentation to clarify when switching of `Actual` objects takes place:

```java
Map<Integer,String> map = ...
expectThat(map)            // returns a new MapActual to test the map value
    .size()                // returns a new IntegerActual for the Map size
        .greater(5)        
        .less(10)          
        .back()            // going back to the MapActual
    .keySet()              // returns a new SetActual for the keyset of the map
    	.contains()        // returns a new ContainsActual to test elements of a Collection
            .someOf(3, 5) 
    	    .noneOf(2, 4, 6)
    	    .back()        // going back to the SetActual
    	.back()            // going back to the MapActual
    .containsValue("a");
```

#### Not-Mode    
Any assertion method in an `Actual` can be negated by a preceding call to `not()`     

```java
expectThat("abc")
    .contains("b")
    .not().contains("z");
```

#### Soft Assertions
Usually if an assertion method fails it will throw an `AssertionError` therefore immediately terminate the test method.
Deep Dive allows you to bundle assertion calls and run them all even if some assertions fail.
At the end eventually an AssertionError containing information about all failed assertions is thrown.  

```java
String s = ... 
expectThat(s)                   // returns a deepdive.actual.lang.StringActual
    .all(actual -> actual       // actual is the StringActual passed to the lambda
        .startsWith("A")        // may fail but will not throw an error
        .length().greater(5))   // will be executed even if the previous assertion startsWith("A") fails
    .contains("B");             // will not be executed if soft assertion all() fails 
```

## Basic Assertions
`org.junit.Assert` or `org.testng.Assert` give you a basic API to test expectations:

```java		
import static org.junit.Assert.assertEquals;
    
String actual = ...
assertEquals("abc", actual); // throws an AssertionError if actual does not equal "abc"
```
    
If you test expectations using the Deep Dive fluent API based on `Actual` classes you don't need any basic assertion methods.
But still basic assertions are usefull from time to time - and the Actual classes itself implement their assertions using 
basic assertion methods.
   
Deep Dive provides a basic assertions API similar to JUnit and TestNG which comes in three flavors with respect how to 
include it in your code. To differentiate from JUnit and TestNG and to make
transition to Deep Dive easier it uses `expect` instead of `assert` as prefix for the assertion methods:

#### ExpectStatic
Use `deepdive.ExpectStatic` as a replacement for the static methods defined in `org.junit.Assert` or `org.testng.Assert`:
 
```java		
import static deepdive.ExpectStatic.expectEqual;

String actual = ...
expectEqual("abc", actual);
```

#### ExpectInterface
Implement `deepdive.ExpectInterface` to inherit basic assertion methods (e.g. in a test base class).
This is convenient if you don't want to write the import statement for `ExpectStatic` in each class
which uses basic assertions:

```java
import org.junit.Test;
import deepdive.ExpectInterface;

public abstract class TestBase implements ExpectInterface {
}

public class CalculatorTest extends TestBase {
    @Test public void test() {
        String actual = ...
        expectEqual("abc", actual); // inherited from ExpectInterface
    }
}
```
    
#### ExpectProtected    
Derive from `deepdive.ExpectProtected` to inherit basic assertion methods with a `protected` modifier:
Here we want to use assertion methods within derived classes but not include them in their public API.
The prototypical use case for this flavor are `Actual` classes which offer type specific public assertion methods for their 
actual value and internally implement these methods using basic assertions:
	 
```java
import deepdive.ExpectProtected;

public class Actual<T,BACK,IMPL> extends ExpectProtected {
    ...
     
    /** Asserts that the actual value is equal to the expected value.*/
    public IMPL equal(Object expected)
    {
        expectEqual(expected, valueOrNull());  // calls ExpectProtected.expectEqual
        return self();
    }
}
```

<a name="msg"></a>
## Error messages

Here is the challenge: Suppose an assertion of an unit test fails - can you find and fix the error using only the error message
and the failure stacktrace? Or do you need to debug the test to diagnose the error?

Informative error messages are key to good unit tests, last but not least for their psychological impact: Does
the test code (judged by its failure messages) feel obscure or clear to you? Therefore Deep Dive error messages 
try to bring as much usefull information as possible into error messages.

#### It adopts the style of Google Truth error messages:
- messages can span multiple lines
- values are reported as `<title>: <value>`
- subsequent value lines are nicely aligned.

#### Details why an equality assertion fails are included:
If arrays, sets, lists or maps are tested on equality to an expected value, the difference between actual
  and expected value is described in great detail.

#### Rich context when diving deep:
Diving deep into `Actual` objects will provide the full context of the starting actual value down to failing sub assertion.

#### Specify context in basic assertions
Many basic assertion methods have an overloaded form with an additional `context` parameter (think JUnit `message` parameter)
which is included in the error message when the assertion fails.
The type of the context parameter is `CharSequence`. Of course you can pass a `String` object but additionally
any CharSequence will do. To avoid heavy construction of error contexts which are not used if the assertion succeeds
this allows for relative cheap, lazily built error contexts as demonstrated by `deepdive.Context`. 
  
  
## AssertionErrors
  
Deep Dive recognizes if [opentest4j](https://github.com/ota4j-team/opentest4j) (also part of JUnit5) or [JUnit](https://junit.org/junit5/) 
are in the classpath and if so uses their `AssertionError` implementations since they are recognized and supported by IDEs (e.g. 
used by Eclipse to show the *CompareActualWithExpected* dialog):   
* `org.opentest4j.AssertionFailedError` for assertion failures
* `org.opentest4j.MultipleFailuresError` for multiple assertion failures in soft mode
* `org.junit.ComparisonFailure` if `org.opentest4j.AssertionFailedError` is not available
* else `java.lang.AssertionError`.
   
If you want to use other `AssertionErrors` you can implement and set an own `deepdive.impl.ErrorFactory` to achieve that. 
		
		
## Own Actual implementations
Deep Dive provides `Actual` implementations for core JDK classes, but you might want to develop
`Actual` implementations for classes in your code base, especially for classes with a lot
of test cases: In this case it really pays off to base tests on `Actuals` since you get 
clean, readable test code and at the same time cover a lot of assertions.

To create an own `Actual` implementation for a class you can use `deepdive.tool.ActualGenerator` as a starting point. Just 
- include your own code and the deepdive jar into the classpath,
- call `ActualGenerator`, 
- pass as argument the fully qualified name of your class,
- copy or redirect the output to a new java source file for the `Actual` implementation,
- and finally tweak the generated class to your needs

```
java -cp <classpath> deepdive.tool.ActualGenerator org.example.CalculationResult >CalculationResultActual.java  
```

#### Notes about the type parameters of `Actual` and derived implementations
The `Actual` base class has three type parameters:

| Type Param | Description |
| :--------- | :---------- |
| `<T>`      | the type of the tested actual value |
| `<BACK>`   | the type of the owner object which constructed the Actual |
| `<IMPL>`   | the implementation class of the Actual as recursive type bound |

#### Examples of derived Actual implementations:

`public class NumberActual<T extends Number,BACK,IMPL extends NumberActual<T,BACK,IMPL>>   extends Actual<T,BACK,IMPL>`
- we restrict `T` to be a `Number`
- we restrict `IMPL` to extend `NumberActual<T,BACK,IMPL>`

`public class StringActual<BACK,IMPL extends StringActual<BACK,IMPL>> extends Actual<String,BACK,IMPL>`
- since `String` is final we don't need a type parameter `T`
- we allow for derived implementations of `StringActual` and therefore declare a `IMPL` type parameter.

`public class ListActual<ELEM,T extends List<ELEM>,BACK,IMPL extends ListActual<ELEM,T,BACK,IMPL>> extends CollectionActual<ELEM,T,BACK,IMPL>`
- `ListActual` tests `java.util.List` objects. 
- we add an additional type parameter `ELEM` for the type of the list elements
- somebody might be interested in testing Lists of a certain class therefore we declare `T` to extends `List<ELEM>`
- we allow for derived implementations of `ListActual` and therefore declare a `IMPL` type parameter.

`public class ShoppingCartActual<BACK> extends Actual<ShoppingCart,BACK,ShoppingCartActual<BACK>>` 
- an extremely reduced example for an `Actual` implementation:
- we don't have to deal with derived values of class `ShoppingCart`, so there is no type parameter `T`
- we don't have to deal with derived implementations from `ShoppingCartActual`, so there is no type parameter `IMPL`
- (we might even drop type parameter `BACK` if `ShoppingCartActual` is never constructed with a `BACK` object, and use `Void` as parent parameter type). 
	

