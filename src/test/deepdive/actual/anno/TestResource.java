package deepdive.actual.anno;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * A annotation used in deepdive self tests. 
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface TestResource {
    String name() default "";

    
    boolean shareable() default true;

    
    String description() default "";
}
