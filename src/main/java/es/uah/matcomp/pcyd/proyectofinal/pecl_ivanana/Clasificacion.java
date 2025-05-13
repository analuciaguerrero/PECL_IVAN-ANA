package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.ArrayList;
import java.util.Comparator;

public class Clasificacion {
    private ZonaRiesgo[] zonas;
    Comparator<Zombie> ordenarPorVictimas = (z1, z2) -> z2.obtenerVictimas() - z1.obtenerVictimas();

    public Clasificacion(ZonaRiesgo[] zonas){
        this.zonas = zonas;
    }

    public ArrayList<String> generarTopZombies(){
        ArrayList<Zombie> todosZombies = new ArrayList<>(zonas[0].getListaZombies());
        todosZombies.addAll(zonas[1].getListaZombies());
        todosZombies.addAll(zonas[2].getListaZombies());
        todosZombies.addAll(zonas[3].getListaZombies());
        todosZombies.sort(ordenarPorVictimas);
        ArrayList<String> topZombies = new ArrayList<>(3);
        if(todosZombies.isEmpty()){
            topZombies.add("No hay víctimas registradas");
            topZombies.add("");
            topZombies.add("");
        }
        else if (todosZombies.size()==1){
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add("");
            topZombies.add("");
        }else if(todosZombies.size()==2){
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(1).getIdZombie() + " - " + todosZombies.get(1).obtenerVictimas() + " muertes");
            topZombies.add("");
        }else{
            topZombies.add(todosZombies.get(0).getIdZombie() + " - " + todosZombies.get(0).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(1).getIdZombie() + " - " + todosZombies.get(1).obtenerVictimas() + " muertes");
            topZombies.add(todosZombies.get(2).getIdZombie() + " - " + todosZombies.get(2).obtenerVictimas() + " muertes");

        }
        return topZombies;
    }
}
