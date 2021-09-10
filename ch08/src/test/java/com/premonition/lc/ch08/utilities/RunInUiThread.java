package com.premonition.lc.ch08.utilities;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RunInUiThread {
    boolean value() default true;
}
