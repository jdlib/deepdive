# DeepDive Assertions

DeepDive is an assertion library for Java.
It offers a fluent API which allows you to *dive deep*, 
i.e. going back and forth between different assertion objects.

## TL;DR
Tests written with DeepDive look like this:

```java		
import java.io.File;
import static java.nio.StandardCharsets.UTF_8;
import static deepdive.ExpectThat.expectThat;		
	
// inside a test method:		
File dir = ...                     // dir is a File object which we expect to be a directory  
expectThat(dir)                    // starting with a deepdive.actual.io.FileActual to test the directory
    .isDirectory()                 // test a File property
    .name()                        // dive into a deepdive.actual.lang.StringActual to test the File name
    	.startsWith("test_")       // test a File name property
    	.endsWith("_files")        // test a File name property
    	.back()                    // back to the FileActual
    .set().childFile("result.txt") // replace the actual File value with a child File
    .exists()                      // test a File property
    .isFile()                      // test a File property
    .not().isHidden()              // negate any available assertion by a preceding not() 
    .length()                      // dive into a deepdive.actual.lang.LongActual to the test the file length
        .greater(50)               // test a File length property
        .back()                    // back to the FileActual
    .read(UTF_8).lineIterator()    // read file content as lines and dive into a deepdive.actual.util.StringIteratorActual 
        .next()                    // dive into a StringActual for the first line 
            .startsWith("Hold your breath") // tests a line property
            .back()                // back to the StringIteratorActual
        .skip(5)                   // skip 5 lines
        .next()                    // dive into a StringActual to test the next line
            .isLowerCase()         // test a line property
            .back()                // back to the StringIteratorActual
    	.not().hasNext();          // test for iteration end: done!
```
		
## License
DeepDive can be used under the terms of the Gnu Public License v3 or 
the Apache 2.0 license, see [License.md](License.md) for details.

## Dependencies
DeepDive requires Java 11+ and has no external dependencies.
It works great with test engines like [JUnit](https://junit.org/junit5/) or [TestNG](https://testng.org/doc/), 
just transition over from JUnit or TestNG assertions to the ones offered by DeepDive.  

## Add to your build

To add a Maven dependency on DeepDive, use the following dependency declaration:

	<dependency>
		<groupId>io.github.jdlib</groupId>
		<artifactId>deepdive</artifactId>
		<version>1.2.0</version>
		<scope>test</scope>
	</dependency>

or directly download the [latest release](https://github.com/jdlib/deepdive/releases/latest).

## How to use
The [user guide](UserGuide.md) explains how to use DeepDive.
	

## Why to use
Like other Java assertions libraries (e.g. [FEST Assert](https://github.com/alexruiz/fest-assert-2.x), 
[AssertJ](https://assertj.github.io/doc/) and [Google Truth](https://truth.dev)) DeepDive 
provides a fluent API to state assertions.
But it goes beyond those libraries 
- by allowing to *dive deep*, i.e. going back and forth between different assertion objects, 
- providing *not*-mode to easily negate any assertion provided by the API,
- therefore resulting in a small library (~250K) with great assertion power.

For further details please dive into [Motivation.md](Motivation.md).  
 
## Limitations
DeepDive makes heavy use of type parameters and recursive type bounds.
The Eclipse Compiler for Java (ECJ) used by Eclipse IDE seems to be 
[challenged](https://bugs.eclipse.org/bugs/show_bug.cgi?id=574309) by this.
Therefore you may experience compile errors when diving *too deep* into assertion objects.
javac from the JDK is not affected.
