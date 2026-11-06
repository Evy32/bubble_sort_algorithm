# Bubble Sort (ordenamiento burbuja)

> **Autor:** _<Vanessa Bernal>_

Se presenta la **definición**, el **funcionamiento**, una **traza**, la **eficiencia** (tiempo y espacio), los **casos de uso** y una implementación en Java 17.

## Tabla de contenido

1. [Definición](#1-definición)
2. [Cómo funciona](#2-cómo-funciona)
3. [Pseudocódigo](#3-pseudocódigo)
4. [Traza paso a paso](#4-traza-paso-a-paso)
5. [Eficiencia](#5-eficiencia)
6. [Propiedades](#6-propiedades)
7. [Casos de uso](#7-casos-de-uso)
8. [Ejemplo en Java](#8-ejemplo-en-java)

---

## 1. Definición

**Bubble Sort** es un algoritmo de ordenamiento por comparación que recorre la colección varias veces comparando **pares de elementos vecinos** e intercambiándolos cuando están en el orden incorrecto. Se llama "burbuja" porque, en cada recorrido, el elemento más grande "sube" (burbujea) hasta su posición final al extremo derecho, como una burbuja de aire en el agua.

## 2. Cómo funciona

1. Se recorre la lista de izquierda a derecha comparando `a[j]` con `a[j+1]`.
2. Si `a[j] > a[j+1]`, se intercambian.
3. Al terminar el recorrido (una **pasada**), el mayor de los elementos quedó en la última posición: ya está en su lugar y no se vuelve a revisar.
4. Se repite sobre la parte que falta (una posición menos cada vez).
5. **Corte temprano:** si en una pasada no hubo ningún intercambio, la lista ya está ordenada y el algoritmo termina.

**Invariante:** después de la pasada `k`, los `k` últimos elementos son los `k` mayores y están en su posición definitiva.

## 3. Pseudocódigo

```
bubbleSort(a):
    para fin desde n-1 hasta 1:
        cambio ← falso
        para j desde 0 hasta fin-1:
            si a[j] > a[j+1]:
                intercambiar(a[j], a[j+1])
                cambio ← verdadero
        si no cambio:
            terminar            // ya está ordenada
```

## 4. Traza paso a paso

Arreglo inicial: `[5, 1, 4, 2, 8]`

**Pasada 1** (revisa posiciones 0..4)

| Comparación | ¿Intercambia? | Resultado |
|---|---|---|
| 5 vs 1 | sí | `[1, 5, 4, 2, 8]` |
| 5 vs 4 | sí | `[1, 4, 5, 2, 8]` |
| 5 vs 2 | sí | `[1, 4, 2, 5, 8]` |
| 5 vs 8 | no | `[1, 4, 2, 5, 8]` |

→ el `8` queda fijo al final.

**Pasada 2** (revisa posiciones 0..3)

| Comparación | ¿Intercambia? | Resultado |
|---|---|---|
| 1 vs 4 | no | `[1, 4, 2, 5, 8]` |
| 4 vs 2 | sí | `[1, 2, 4, 5, 8]` |
| 4 vs 5 | no | `[1, 2, 4, 5, 8]` |

→ el `5` queda fijo.

**Pasada 3** (revisa posiciones 0..2)

| Comparación | ¿Intercambia? | Resultado |
|---|---|---|
| 1 vs 2 | no | `[1, 2, 4, 5, 8]` |
| 2 vs 4 | no | `[1, 2, 4, 5, 8]` |

→ no hubo intercambios: **corte temprano**. Resultado: `[1, 2, 4, 5, 8]` con 9 comparaciones (en lugar de las 10 del peor caso).

## 5. Eficiencia

| Caso | Cuándo ocurre | Comparaciones | Tiempo |
|---|---|---|---|
| Mejor | La lista ya está ordenada (con corte temprano) | `n − 1` | **O(n)** |
| Promedio | Orden aleatorio | ≈ `n²/2` | **O(n²)** |
| Peor | La lista está en orden inverso | `n(n − 1)/2` | **O(n²)** |

- **Espacio:** O(1) en la versión clásica (*in-place*: solo intercambia dentro del mismo arreglo).
- En el peor caso también hace `n(n − 1)/2` intercambios, lo que lo hace más lento en la práctica que *Insertion Sort*, aunque ambos sean O(n²).

## 6. Propiedades

| Propiedad | ¿Cumple? | Por qué |
|---|---|---|
| Estable | Sí | Solo intercambia si `a[j] > a[j+1]` (estrictamente), así los iguales conservan su orden relativo. |
| In-place | Sí | No necesita memoria adicional proporcional a `n`. |
| Adaptativo | Sí (con corte temprano) | Si la lista está casi ordenada, termina en pocas pasadas. |
| Por comparación | Sí | Solo usa `>` entre elementos. |

## 7. Casos de uso

- **Enseñanza:** es el algoritmo más fácil de entender y de trazar a mano.
- **Listas muy pequeñas** o **casi ordenadas**, donde el corte temprano lo deja cerca de O(n).
- Verificar si una lista ya está ordenada (una sola pasada sin intercambios).

**No usarlo** para colecciones grandes: algoritmos O(n log n) como *Merge Sort* o *Quick Sort* (o `List.sort` / `Arrays.sort` de Java) son muchísimo más rápidos.

## 8. Ejemplo en Java

Archivo: [`BubbleSort.java`](BubbleSort.java)

Esta versión es **funcional e inmutable**: no usa `for` ni variables mutables; cada pasada devuelve una lista nueva. Las ideas de la teoría se ven directamente en el código:

- `pasada` compara cada par de vecinos: el menor se queda y el mayor sigue "burbujeando" hacia la derecha.
- `ordenar` aplica una pasada; si **no hubo intercambios** (la lista no cambió) termina — es el **corte temprano**. Si hubo, el último elemento ya está en su lugar y se ordena solo el resto — es el **rango que se reduce** en cada pasada.

```java
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
```

**Ejecutar** (desde esta carpeta):

```bash
javac BubbleSort.java && java BubbleSort
```

**Salida:**

```
[5, 1, 4, 2, 8] -> [1, 2, 4, 5, 8]  OK
[9, 7, 5, 3, 1] -> [1, 3, 5, 7, 9]  OK
[1, 2, 3, 4] -> [1, 2, 3, 4]  OK
[3, 1, 3, 2, 1] -> [1, 1, 2, 3, 3]  OK
[7] -> [7]  OK
[] -> []  OK
```

> **Nota:** como cada pasada crea listas nuevas, esta versión gasta más memoria y tiempo que la clásica *in-place* del pseudocódigo (que es O(1) en espacio). La versión clásica con intercambios sobre el arreglo está en [`../Codigo_Java/Ordenamientos.java`](../Codigo_Java/Ordenamientos.java) (método `bubble`).
# bubble_sort_algorithm
