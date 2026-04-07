package org.example;

import java.util.*;
import java.util.function.Predicate;
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

        //Ejercicio 7
        double promedio7 =  lista.stream()
                                .mapToDouble(Producto::getPrecio)
                                .average()
                                .orElse(0.0);
        long unidades =    lista.stream()
                                .filter(p -> p.getPrecio() > promedio7)
                                .mapToLong(Producto::getStock)
                                .sum();

        System.out.println("\nEl stock total de los productos solo contando los que tienen precio mayor a " + promedio7 + " es: " + unidades);

        //Ejercicio 8
        Map<String, Integer> stockPorCategoria = lista.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista1 -> lista1.size() >= 3
                                        ? lista1.stream()
                                        .mapToInt(Producto::getStock)
                                        .sum()
                                        : null
                        )
                ));

        stockPorCategoria.values().removeIf(v -> v == null);

        stockPorCategoria.forEach((categoria, stock) ->
                System.out.println(categoria + ": " + stock + " unidades")
        );
        //Ejercicio 9
        List<Producto> listaConDescuento = lista.stream()
                                                .filter(p -> p.getStock() >= 20)
                                                .map(p -> new Producto(p.getNombre(),p.getPrecio() * 0.85, p.getCategoria(), p.getStock()))
                                                .collect(Collectors.toList());

        System.out.println("\nLista con precios con descuento: ");
        listaConDescuento.forEach(p -> System.out.println(p.getNombre() + " - Stock: " + p.getStock() + " - Precio: " + p.getPrecio()));

        //Ejercicio 10
        /*double gananciaElectronica = lista.stream()
                                        .filter(p -> p.getCategoria().equals("Electrónica"))
                                        .mapToDouble(p -> p.getPrecio() * 0.35 * p.getStock())
                                        .sum();
        double gananciaProductos = lista.stream()
                                        .filter(p -> !(p.getCategoria().equals("Electrónica")))
                                        .mapToDouble(p -> p.getPrecio() * 0.55 * p.getStock())
                                        .sum();
        System.out.println("\nLa ganancia total si se vendieran todos los productos seria de: " + (gananciaElectronica + gananciaProductos));*/

        double gananciaTotal = lista.stream()
                .mapToDouble(p -> {
                    double porcentaje = p.getCategoria().equalsIgnoreCase("Electrónica") ? 0.35 : 0.55;
                    return p.getPrecio() * porcentaje * p.getStock();
                })
                .sum();

        System.out.println("\nLa ganancia total si se vendieran todos los productos seria de: " + gananciaTotal);





    }
}