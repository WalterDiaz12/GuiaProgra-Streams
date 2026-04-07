package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Producto> lista = Producto.cargarProductos();

        //EJERCICIO 1
        List<String> nuevaLista = lista.stream()
                                         .filter(p -> p.getCategoria().equals("Electrónica"))
                                         .filter(p -> p.getPrecio() > 1000)
                                         .sorted(Comparator.comparing(Producto::getPrecio).reversed())
                                         .map(p -> p.getNombre() + " - $" + p.getPrecio())
                                         .toList();
        System.out.println(nuevaLista);
        //EJERCICIO 2
        double promedio =   lista.stream()
                             .filter(p -> p.getCategoria().equalsIgnoreCase("Hogar"))
                             .filter(p -> p.getStock() > 0)
                             .mapToDouble(Producto::getPrecio)
                             .average().orElse(0.0);

        System.out.println(promedio);

        //EJERCICIO 3
        Map<String, Producto> productoMasCaro = lista.stream()
                            .collect(Collectors.groupingBy(
                                    Producto::getCategoria,
                                    Collectors.collectingAndThen(
                                            Collectors.maxBy(Comparator.comparingDouble(Producto::getPrecio)),
                                            Optional::get
                                    ))
                            );
        System.out.println(productoMasCaro);
    }
}