package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LibretaDeNotas {
    private HashMap<String, ArrayList<Double>> calificaciones;
    private double promedioCurso;
    private final double NOTA_APROBACION = 4.0; // Nota mínima para aprobar

    public LibretaDeNotas() {
        calificaciones = new HashMap<>();
        promedioCurso = 0.0;
    }

    public void ingresarDatos() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar cantidad de alumnos
        int cantidadAlumnos = 0;
        while (cantidadAlumnos <= 0) {
            System.out.print("Ingrese la cantidad de alumnos: ");
            try {
                cantidadAlumnos = Integer.parseInt(scanner.nextLine());
                if (cantidadAlumnos <= 0) {
                    System.out.println("La cantidad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }

        // Solicitar cantidad de notas por alumno
        int cantidadNotas = 0;
        while (cantidadNotas <= 0) {
            System.out.print("Ingrese la cantidad de notas por alumno: ");
            try {
                cantidadNotas = Integer.parseInt(scanner.nextLine());
                if (cantidadNotas <= 0) {
                    System.out.println("La cantidad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }

        // Ingresar datos de cada alumno
        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.print("\nIngrese el nombre del alumno " + (i + 1) + ": ");
            String nombre = scanner.nextLine();

            ArrayList<Double> notas = new ArrayList<>();
            for (int j = 0; j < cantidadNotas; j++) {
                double nota = -1;
                while (nota < 0 || nota > 10) {
                    System.out.print("Ingrese la nota " + (j + 1) + " (0-10): ");
                    try {
                        nota = Double.parseDouble(scanner.nextLine());
                        if (nota < 0 || nota > 10) {
                            System.out.println("La nota debe estar entre 0 y 10.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor ingrese un número válido.");
                    }
                }
                notas.add(nota);
            }

            calificaciones.put(nombre, notas);
        }

        // Calcular promedio del curso
        calcularPromedioCurso();
    }

    private void calcularPromedioCurso() {
        double sumaTotal = 0;
        int cantidadNotas = 0;

        for (ArrayList<Double> notas : calificaciones.values()) {
            for (Double nota : notas) {
                sumaTotal += nota;
                cantidadNotas++;
            }
        }

        if (cantidadNotas > 0) {
            promedioCurso = sumaTotal / cantidadNotas;
        }
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ DE OPCIONES ---");
            System.out.println("1. Mostrar el Promedio de Notas por Estudiante");
            System.out.println("2. Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante");
            System.out.println("3. Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante");
            System.out.println("0. Salir del Menú");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        mostrarPromedios();
                        break;
                    case 2:
                        verificarAprobacion();
                        break;
                    case 3:
                        compararConPromedioCurso();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private void mostrarPromedios() {
        System.out.println("\n--- PROMEDIOS POR ESTUDIANTE ---");
        for (String nombre : calificaciones.keySet()) {
            ArrayList<Double> notas = calificaciones.get(nombre);
            double promedio = calcularPromedio(notas);
            double maxima = encontrarMaxima(notas);
            double minima = encontrarMinima(notas);

            System.out.println("\nEstudiante: " + nombre);
            System.out.println("Promedio: " + String.format("%.2f", promedio));
            System.out.println("Nota máxima: " + maxima);
            System.out.println("Nota mínima: " + minima);
        }
    }

    private void verificarAprobacion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- VERIFICAR APROBACIÓN ---");

        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!calificaciones.containsKey(nombre)) {
            System.out.println("El estudiante no existe en la libreta.");
            return;
        }

        System.out.print("Ingrese la nota a verificar: ");
        double nota;
        try {
            nota = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nota no válida.");
            return;
        }

        if (nota >= NOTA_APROBACION) {
            System.out.println("La nota " + nota + " es APROBATORIA.");
        } else {
            System.out.println("La nota " + nota + " es REPROBATORIA.");
        }
    }

    private void compararConPromedioCurso() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- COMPARAR CON PROMEDIO DEL CURSO ---");

        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!calificaciones.containsKey(nombre)) {
            System.out.println("El estudiante no existe en la libreta.");
            return;
        }

        System.out.print("Ingrese la nota a comparar: ");
        double nota;
        try {
            nota = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nota no válida.");
            return;
        }

        if (nota > promedioCurso) {
            System.out.println("La nota " + nota + " está POR SOBRE el promedio del curso (" + String.format("%.2f", promedioCurso) + ").");
        } else if (nota < promedioCurso) {
            System.out.println("La nota " + nota + " está POR DEBAJO del promedio del curso (" + String.format("%.2f", promedioCurso) + ").");
        } else {
            System.out.println("La nota " + nota + " es IGUAL al promedio del curso (" + String.format("%.2f", promedioCurso) + ").");
        }
    }

    private double calcularPromedio(ArrayList<Double> notas) {
        double suma = 0;
        for (Double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    private double encontrarMaxima(ArrayList<Double> notas) {
        double maxima = Double.MIN_VALUE;
        for (Double nota : notas) {
            if (nota > maxima) {
                maxima = nota;
            }
        }
        return maxima;
    }

    private double encontrarMinima(ArrayList<Double> notas) {
        double minima = Double.MAX_VALUE;
        for (Double nota : notas) {
            if (nota < minima) {
                minima = nota;
            }
        }
        return minima;
    }

    public static void main(String[] args) {
        LibretaDeNotas libreta = new LibretaDeNotas();
        libreta.ingresarDatos();
        libreta.mostrarMenu();
    }
}