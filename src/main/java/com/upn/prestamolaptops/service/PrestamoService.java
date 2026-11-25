package com.upn.prestamolaptops.service;

import com.upn.prestamolaptops.model.Estudiante;
import com.upn.prestamolaptops.model.ResultadoOperacion;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PrestamoService {

    private static final Pattern PATRON_CODIGO_ESTUDIANTE = Pattern.compile("^U\\d{7}$");

    private final Map<String, Estudiante> estudiantes = new LinkedHashMap<>();

    public PrestamoService() {
        estudiantes.put("U2023115", new Estudiante("U2023115", "Estudiante U2023115"));
    }

    public boolean esCodigoEstudianteValido(String codigo) {
        if (codigo == null) {
            return false;
        }
        return PATRON_CODIGO_ESTUDIANTE.matcher(codigo.trim()).matches();
    }

    public ResultadoOperacion registrarEstudiante(String nombre, String codigo) {
        String nombreLimpio = nombre == null ? "" : nombre.trim();
        String codigoLimpio = codigo == null ? "" : codigo.trim();

        if (nombreLimpio.isEmpty() || codigoLimpio.isEmpty()) {
            return ResultadoOperacion.error("Complete nombre y código universitario");
        }

        if (!esCodigoEstudianteValido(codigoLimpio)) {
            return ResultadoOperacion.error("El código debe comenzar con U y tener 8 caracteres");
        }

        estudiantes.put(codigoLimpio, new Estudiante(codigoLimpio, nombreLimpio));
        return ResultadoOperacion.ok("Estudiante registrado correctamente");
    }

    public Estudiante buscarEstudiante(String codigo) {
        if (codigo == null) {
            return null;
        }
        return estudiantes.get(codigo.trim());
    }

    public Collection<Estudiante> listarEstudiantes() {
        return Collections.unmodifiableCollection(estudiantes.values());
    }
}
