/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espoch.programareactivo;

import io.reactivex.Observable;
import java.util.Scanner;

public class ProgramaReactivo {

    public static void main(String[] args) {
        // Crear un flujo reactivo con RxJava para la función trigonométrica
        Observable<String> trigFunctionObservable = Observable.create(emitter -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la función trigonométrica (sen, cos, tan, cot, sec): ");
            String trigFunction = scanner.nextLine().toLowerCase();
            emitter.onNext(trigFunction);
            emitter.onComplete();
        });

        // Crear un flujo reactivo con RxJava para el argumento
        Observable<String> argumentObservable = Observable.create(emitter -> {
            Scanner argumentScanner = new Scanner(System.in);
            System.out.print("Ingrese el argumento : ");
            String argument = argumentScanner.nextLine();
            emitter.onNext(argument);
            emitter.onComplete();
        });

        // Iniciar el temporizador antes del cálculo de derivadas trigonométricas
        long startTime = System.nanoTime();

        // Combinar los flujos reactivos y calcular la derivada trigonométrica
        Observable.zip(trigFunctionObservable, argumentObservable, (trigFunction, argument) -> {
            String baseExpression = parseArgument(argument);
            String derivative = getTrigDerivative(trigFunction, baseExpression);
            return "Derivada de " + trigFunction + ": " + getAdjustedDerivative(derivative, argument, trigFunction);
        })
        .subscribe(System.out::println, Throwable::printStackTrace);

        // Detener el temporizador después del cálculo de derivadas trigonométricas
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        // Imprimir el tiempo transcurrido
        System.out.println("Tiempo de ejecución para el cálculo de derivadas: " + elapsedTime + " nanosegundos");
    }

    private static String parseArgument(String argument) {
        // Implementar la lógica para manejar expresiones compuestas aquí
        // En este ejemplo, simplemente se devuelve el argumento sin cambios
        return argument;
    }

    private static String getTrigDerivative(String trigFunction, String baseExpression) {
        // Implementar la lógica para derivadas trigonométricas aquí
        switch (trigFunction) {
            case "sen":
                return "cos(" + baseExpression + ")";
            case "cos":
                return "sin(" + baseExpression + ")";
            case "tan":
                return "sec^2(" + baseExpression + ")";
            case "cot":
                return "csc^2(" + baseExpression + ")";
            case "sec":
                return "sec(" + baseExpression + ") tan(" + baseExpression + ")";
            default:
                return "Función trigonométrica no válida";
        }
    }

    private static String getAdjustedDerivative(String derivative, String argument, String trigFunction) {
        // Buscar el término que contiene 'x' en el argumento
        String[] terms = argument.split("\\+");
        StringBuilder adjustedDerivative = new StringBuilder();

        for (String term : terms) {
            if (term.contains("x")) {
                // Obtener el coeficiente y el exponente de 'x' en el término
                String[] parts = term.split("\\*x(\\^)?");

                // Inicializar el coeficiente y el exponente
                int coefficient = 1;
                int exponent = 1;

                // Verificar si hay partes después de la división
                if (parts.length > 0) {
                    coefficient = parseCoefficient(parts[0].trim());

                    // Verificar si hay exponente especificado
                    if (parts.length > 1 && parts[1] != null && !parts[1].isEmpty()) {
                        exponent = Integer.parseInt(parts[1].trim());
                    }
                }

                // Calcular el nuevo coeficiente y exponente después de la derivada
                if (coefficient != 0) {
                    int newCoefficient = coefficient * exponent;
                    int newExponent = exponent - 1;

                    // Construir el término ajustado en la derivada
                    if (newExponent > 0) {
                        if (adjustedDerivative.length() > 0) {
                            adjustedDerivative.append(" + ");
                        }
                        adjustedDerivative.append(newCoefficient).append("*x^").append(newExponent);
                    } else {
                        if (adjustedDerivative.length() > 0) {
                            adjustedDerivative.append(" + ");
                        }
                        adjustedDerivative.append(newCoefficient);
                    }
                }
            }
        }

        // Ajustar el signo "-" para "cos" y "cot"
        if (trigFunction.equals("cos") || trigFunction.equals("cot")) {
            if (adjustedDerivative.length() > 0) {
                adjustedDerivative.insert(0, "-");
            } else {
                adjustedDerivative.append("-");
            }
        }

        // Si no se encontró ningún término con 'x', simplemente añadir la derivada sin cambios
        if (adjustedDerivative.length() > 0) {
            return adjustedDerivative.toString() + " *" + derivative;
        } else {
            return derivative;
        }
    }

    private static int parseCoefficient(String part) {
        try {
            return Integer.parseInt(part.trim());
        } catch (NumberFormatException e) {
            // Si no se puede parsear como número, asumir que es 0
            return 0;
        }
    }
}
