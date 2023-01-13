package org.example;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMetody {

    String spodziewany_wynik();
    String[] argumenty();
}
