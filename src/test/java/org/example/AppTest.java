package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    Osoba obj = new Osoba("Abc",2000);

    @Test
    public void test_nazwisko_0() {
        assertEquals("Abc",obj.nazwisko());
    }

    @Test
    public void test_wiek_1() {
        assertEquals(23,obj.wiek("2023"));
    }

    @Test
    public void test_wiek_2() {
        assertEquals(20,obj.wiek(2020));
    }
}
