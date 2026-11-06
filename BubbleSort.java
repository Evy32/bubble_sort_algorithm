import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BubbleSort {

    // Una pasada: compara cada par de vecinos; el menor se queda, el mayor sigue "burbujeando" a la derecha.
    static List<Integer> pasada(List<Integer> xs) {
        return xs.size() < 2
            ? xs
            : anteponer(Math.min(xs.get(0), xs.get(1)),
                        pasada(anteponer(Math.max(xs.get(0), xs.get(1)), xs.subList(2, xs.size()))));
    }

    // shortcut: anteponer copia la lista (O(n)) y la recursión usa la pila; para listas grandes usar la versión in-place de Codigo_Java/Ordenamientos.java
    static List<Integer> ordenar(List<Integer> xs) {
        return Optional.of(pasada(xs))
            .filter(p -> !p.equals(xs))                                  // hubo intercambios: falta ordenar
            .map(p -> Stream.concat(
                    ordenar(p.subList(0, p.size() - 1)).stream(),        // el último ya quedó en su lugar
                    Stream.of(p.get(p.size() - 1))).toList())
            .orElse(xs);                                                 // sin intercambios: ya está ordenada
    }

    static List<Integer> anteponer(Integer x, List<Integer> xs) {
        return Stream.concat(Stream.of(x), xs.stream()).toList();
    }

    static String describir(List<Integer> original, List<Integer> ordenada) {
        return original + " -> " + ordenada
            + (ordenada.equals(original.stream().sorted().toList()) ? "  OK" : "  ERROR");
    }

    public static void main(String[] args) {
        Stream.of(
                List.of(5, 1, 4, 2, 8),
                List.of(9, 7, 5, 3, 1),
                List.of(1, 2, 3, 4),
                List.of(3, 1, 3, 2, 1),
                List.of(7),
                List.<Integer>of())
            .map(xs -> describir(xs, ordenar(xs)))
            .forEach(System.out::println);
    }
}
