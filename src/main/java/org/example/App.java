package org.example;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class App
{
    int numer_testu;

    void wypisz_metody(Class<?> c) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method: methods) {
            System.out.println(method.getName());
            Annotation[] annotations = method.getAnnotations();
            if (method.isAnnotationPresent(Opis.class)) {
                method.getAnnotation(Opis.class).wersja();
            }
            for (Annotation annotation: annotations) {
                System.out.println(annotation);
            }
        }
    }

    void wypisz_pola(Class<?> c) {
        Field[] fields = c.getDeclaredFields();
        for (Field field: fields) {
            System.out.println(field);
            System.out.println(field.getType().getSimpleName());
        }
    }

    String wCudzyslowie(String s) {
        return "\"" + s + "\"";
    }

    String argumenty(Method method) {
        String[] args = method.getAnnotation(TestMetody.class).argumenty();
        String wynik = "";
        int i;
        Parameter[] parameters = method.getParameters();
        if (parameters.length > 0) {
            for (i = 0; i < method.getParameterCount() - 1; i++) {
                if (parameters[i].getType().getSimpleName().equals("String")) {
                    wynik += wCudzyslowie(args[i]) + ",";
                } else {
                    wynik += args[i] + ",";
                }
            }
            if (parameters[i].getType().getSimpleName().equals("String")) {
                wynik += wCudzyslowie(args[i]);
            } else {
                wynik += args[i];
            }
        }
        return wynik;
    }

    String spodziewanyWynik(Method method) {
        String wynik = "";
        if (method.getReturnType().getSimpleName().equals("String")) {
            wynik += wCudzyslowie(method.getAnnotation(TestMetody.class).spodziewany_wynik());
        } else {
            wynik += method.getAnnotation(TestMetody.class).spodziewany_wynik();
        }
        return wynik;
    }

    void generujTest(Method method) {
        if (method.isAnnotationPresent(TestMetody.class)) {
            System.out.println("\n  @Test");
            System.out.println("  public void test_" + method.getName() + "_" + (numer_testu++) + "() {");
            System.out.println("    assertEquals(" + spodziewanyWynik(method) + "," + "obj." + method.getName()
                    + "("
                    + argumenty(method)
                    + ")"
                    + ");");
            System.out.println("  }");
        }
    }

    String wartosci_pol(Class<?> c) {
        String wynik = "";
        Field[] fields = c.getDeclaredFields();
        int i;
        for (i = 0; i < fields.length-1; i++) {
            if (fields[i].getType().getSimpleName().equals("String")) {
                wynik += wCudzyslowie(c.getAnnotation(TestKlasy.class).wartosci_pol()[i]) + ",";
            } else {
                wynik += c.getAnnotation(TestKlasy.class).wartosci_pol()[i];
            }
        }
        if (fields[i].getType().getSimpleName().equals("String")) {
            wynik += wCudzyslowie(c.getAnnotation(TestKlasy.class).wartosci_pol()[i]) + ",";
        } else {
            wynik += c.getAnnotation(TestKlasy.class).wartosci_pol()[i];
        }
        return wynik;
    }

    void generujPrzyklad(Class<?> c) {
        if (c.isAnnotationPresent(TestKlasy.class)) {
            System.out.println("\n  " + c.getSimpleName() + " obj = new " + c.getSimpleName() + "(" + wartosci_pol(c) + ");");
        }
    }

    void generujTesty(Class<?> c) {
        System.out.println("package org.example;");
        System.out.println("\nimport org.junit.Test;");
        System.out.println("import static org.junit.Assert.*;");

        System.out.println("\npublic class AppTest {");

        generujPrzyklad(c);

        for (Method method: c.getDeclaredMethods()) {
            generujTest(method);
        }

        System.out.println("}\n");
    }

    public static void main( String[] args )
    {

        App app = new App();

        try {
            Class<?> c = Class.forName("org.example.Osoba");
            app.generujTesty(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
