package deepdive.impl;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ActualChange is a marker annotation for methods which change
 * the value of an Actual. 
 */
@NotMustBeOff
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface ActualChange
{
}
