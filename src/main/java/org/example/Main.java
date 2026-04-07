package org.example;

import java.util.*;
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

        //EJERCICIO 4
        Optional<String> prod = lista.stream()
                                    .filter(p -> p.getCategoria().equalsIgnoreCase("deportes"))
                                    .filter(p -> p.getStock() > 10)
                                    .map(p -> p.getNombre().toLowerCase())
                                    .findFirst();
        System.out.println(prod.orElse("Producto Inexistente."));

        //EJERCICIO 5
        try{
            Optional<Producto> masBarato = lista.stream()
                    .min(Comparator.comparingDouble(
                            p -> p.getPrecio() * p.getStock()
                    ));
            System.out.println("El producto mas barato es: " + masBarato);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Ejercicio 6
        List<String> stockProd = lista.stream()
                                        .filter(p -> p.getStock() > 0)
                                        .filter(p -> p.getNombre().length() > 5)
                                        .map(p -> p.getNombre())
                                        .sorted()
                                        .collect(Collectors.toList());

        System.out.println("\nLista de productos ordenados alfabeticamente: ");
        stockProd.forEach(System.out::println);



    }
}