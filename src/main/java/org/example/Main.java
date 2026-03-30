package org.example;

import java.util.Comparator;
import java.util.List;

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

        /*
        2. Reducción de Datos
        ○ Calcular el precio promedio de los productos de la categoría "Hogar",
        pero solo considerando aquellos con stock disponible.


        * Tener cuidado donde se hace el git init
        *
        *
        *
        * */


    }
}