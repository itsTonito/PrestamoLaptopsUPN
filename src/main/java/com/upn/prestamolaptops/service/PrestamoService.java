package com.upn.prestamolaptops.service;

import com.upn.prestamolaptops.model.EstadoLaptop;
import com.upn.prestamolaptops.model.Estudiante;
import com.upn.prestamolaptops.model.Laptop;
import com.upn.prestamolaptops.model.ResultadoOperacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PrestamoService {

    private static final Pattern PATRON_CODIGO_ESTUDIANTE = Pattern.compile("^U\\d{7}$");

    private final Map<String, Estudiante> estudiantes = new LinkedHashMap<>();
    private final Map<String, Laptop> laptops = new LinkedHashMap<>();

    public PrestamoService() {
        estudiantes.put("U2023115", new Estudiante("U2023115", "Estudiante U2023115"));
        inicializarInventario();
    }

    private void inicializarInventario() {
        agregarLaptop(new Laptop("LT-001", "HP ProBook 450", EstadoLaptop.PRESTADA, "U2023115"));
        agregarLaptop(new Laptop("LT-002", "Dell Latitude 5420", EstadoLaptop.DISPONIBLE, null));
        agregarLaptop(new Laptop("LT-003", "Lenovo ThinkPad E14", EstadoLaptop.DISPONIBLE, null));
        agregarLaptop(new Laptop("LT-005", "Lenovo ThinkPad", EstadoLaptop.DISPONIBLE, null));
    }

    private void agregarLaptop(Laptop laptop) {
        laptops.put(laptop.getCodigo(), laptop);
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

    public List<Laptop> listarLaptops() {
        return new ArrayList<>(laptops.values());
    }

    public List<Laptop> listarLaptopsDisponibles() {
        List<Laptop> disponibles = new ArrayList<>();
        for (Laptop laptop : laptops.values()) {
            if (laptop.estaDisponible()) {
                disponibles.add(laptop);
            }
        }
        return disponibles;
    }

    public Laptop buscarLaptop(String codigoLaptop) {
        if (codigoLaptop == null) {
            return null;
        }
        return laptops.get(codigoLaptop.trim());
    }

    public boolean tienePrestamoActivo(String codigoEstudiante) {
        return buscarLaptopPorEstudiante(codigoEstudiante) != null;
    }

    public Laptop buscarLaptopPorEstudiante(String codigoEstudiante) {
        if (codigoEstudiante == null) {
            return null;
        }
        String codigoLimpio = codigoEstudiante.trim();
        for (Laptop laptop : laptops.values()) {
            if (!laptop.estaDisponible() && codigoLimpio.equals(laptop.getCodigoEstudianteAsignado())) {
                return laptop;
            }
        }
        return null;
    }

    public ResultadoOperacion registrarPrestamo(String nombreEstudiante, String codigoEstudiante, String codigoLaptop) {
        ResultadoOperacion validacionEstudiante = registrarEstudiante(nombreEstudiante, codigoEstudiante);
        if (!validacionEstudiante.isExitoso()) {
            return validacionEstudiante;
        }

        if (codigoLaptop == null || codigoLaptop.trim().isEmpty()) {
            return ResultadoOperacion.error("No hay laptops disponibles para préstamo");
        }

        Laptop laptop = buscarLaptop(codigoLaptop);
        if (laptop == null) {
            return ResultadoOperacion.error("Laptop no encontrada en el inventario");
        }

        if (!laptop.estaDisponible()) {
            return ResultadoOperacion.error("La laptop ya está asignada a otro estudiante");
        }

        String codigoLimpio = codigoEstudiante.trim();

        if (tienePrestamoActivo(codigoLimpio)) {
            return ResultadoOperacion.error("El estudiante ya tiene un préstamo activo");
        }

        laptop.setEstado(EstadoLaptop.PRESTADA);
        laptop.setCodigoEstudianteAsignado(codigoLimpio);

        return ResultadoOperacion.ok(
                "Préstamo registrado: " + laptop.getCodigo() + " asignada a " + codigoLimpio
        );
    }
}
