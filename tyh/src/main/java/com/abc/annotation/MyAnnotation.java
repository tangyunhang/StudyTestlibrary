package com.abc.annotation;

import java.lang.annotation.*;

@Target ({ElementType.TYPE,ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyAnnotation {

    String value() default "ff";
}
