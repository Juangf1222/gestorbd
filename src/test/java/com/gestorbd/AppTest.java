package com.gestorbd;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */

import com.gestorbd.modelo.Documento;
import com.gestorbd.servicio.GestorBaseDatos;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AppTest {

    private GestorBaseDatos gestor;

    @Before
    public void inicializar() throws IOException {

        // Carpeta exclusiva para pruebas
        gestor = new GestorBaseDatos("test_data");

        // Crear colección de prueba
        gestor.seleccionarColeccion("pruebas");
    }

    /**
     * PRUEBA 1
     * Verifica inserción de documentos.
     */
    @Test
    public void pruebaInsertarDocumento() throws IOException {

        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("nombre", "Juan");
        campos.put("edad", 20);

        Documento doc = new Documento("1", campos);

        gestor.insertar(doc);

        Documento resultado = gestor.buscarPorId("1");

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getCampos().get("nombre"));
    }

    /**
     * PRUEBA 2
     * Verifica búsqueda por ID.
     */
    @Test
    public void pruebaBuscarPorId() throws IOException {

        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("nombre", "Ana");

        Documento doc = new Documento("2", campos);

        gestor.insertar(doc);

        Documento encontrado = gestor.buscarPorId("2");

        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getCampos().get("nombre"));
    }

    /**
     * PRUEBA 3
     * Verifica actualización de campo.
     */
    @Test
    public void pruebaActualizarCampo() throws IOException {

        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("nombre", "Carlos");

        Documento doc = new Documento("3", campos);

        gestor.insertar(doc);

        gestor.actualizarCampo("3", "nombre", "Pedro");

        Documento actualizado = gestor.buscarPorId("3");

        assertEquals("Pedro", actualizado.getCampos().get("nombre"));
    }

    /**
     * PRUEBA 4
     * Verifica eliminación de documentos.
     */
    @Test
    public void pruebaEliminarDocumento() throws IOException {

        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("nombre", "Laura");

        Documento doc = new Documento("4", campos);

        gestor.insertar(doc);

        gestor.eliminar("4");

        Documento eliminado = gestor.buscarPorId("4");

        assertNull(eliminado);
    }

    /**
     * PRUEBA 5
     * Verifica persistencia tras reinicio.
     */
    @Test
    public void pruebaPersistencia() throws IOException {

        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("nombre", "Persistente");

        Documento doc = new Documento("5", campos);

        gestor.insertar(doc);

        // Simular reinicio
        GestorBaseDatos nuevoGestor = new GestorBaseDatos("test_data");
        nuevoGestor.seleccionarColeccion("pruebas");

        Documento recuperado = nuevoGestor.buscarPorId("5");

        assertNotNull(recuperado);
        assertEquals("Persistente",
                recuperado.getCampos().get("nombre"));
    }
}
