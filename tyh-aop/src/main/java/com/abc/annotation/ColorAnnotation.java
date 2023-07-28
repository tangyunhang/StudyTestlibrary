package com.abc.annotation;

import java.lang.annotation.*;

@Target ({ElementType.FIELD,ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
@Inherited
public @interface ColorAnnotation {
    enum COLOR{RED,GREEN}
    String value() default " xx color";
    COLOR color() default COLOR.RED;
}
