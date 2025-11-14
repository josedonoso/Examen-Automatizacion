package com.examen.pruebas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculadoraTest {
    @Test
    void testSumaSimple(){
        //verificar que 2 + 2 = 4
        assertEquals(4,2+2, "La suma debe ser correcta");
    }
}
