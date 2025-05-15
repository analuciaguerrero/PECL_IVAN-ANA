package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.ArrayList;
import java.util.Comparator;

public class Clasificacion {
    // Arreglo que contiene las zonas de riesgo donde están los zombis
    private ZonaRiesgo[] zonas;

    // Comparador que ordena los zombis según la cantidad de víctimas, de mayor a menor
    Comparator<Zombie> ordenarPorVictimas = (z1, z2) -> z2.obtenerVictimas() - z1.obtenerVictimas();

    // Constructor que recibe un arreglo de zonas de riesgo
    public Clasificacion(ZonaRiesgo[] zonas){
        this.zonas = zonas;
    }

    // Método que genera una lista con los 3 zombis con más víctimas
    public ArrayList<String> generarTopZombies(){
        // Creamos una lista con todos los zombis de las 4 zonas
        ArrayList<Zombie> todosZombies = new ArrayList<>(zonas[0].getListaZombies());
        todosZombies.addAll(zonas[1].getListaZombies());
        todosZombies.addAll(zonas[2].getListaZombies());
        todosZombies.addAll(zonas[3].getListaZombies());

        // Ordenamos la lista de zombis según las víctimas
        todosZombies.sort(ordenarPorVictimas);

        // Lista para devolver los resultados
        ArrayList<String> topZombies = new ArrayList<>(3);

        // Si no hay zombis, añadimos un mensaje indicativo y dos espacios vacíos
        if(todosZombies.isEmpty()){
            topZombies.add("No hay víctimas registradas");
            topZombies.add("");
            topZombies.add("");
        }
        // Si hay sólo 1 zombi, lo añadimos y completamos con dos espacios vacíos
        else if (todosZombies.size() == 1){
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add("");
            topZombies.add("");
        }
        // Si hay 2 zombis, añadimos ambos y un espacio vacío
        else if(todosZombies.size() == 2){
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(1).getIdZombie() + " - " + todosZombies.get(1).obtenerVictimas() + " muertes");
            topZombies.add("");
        }
        // Si hay 3 o más zombis, añadimos los tres primeros con más víctimas
        else{
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(1).getIdZombie() + " - " + todosZombies.get(1).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(2).getIdZombie() + " - " + todosZombies.get(2).obtenerVictimas() + " muertes");
        }

        // Retornamos la lista con el top de zombis
        return topZombies;
    }
}
