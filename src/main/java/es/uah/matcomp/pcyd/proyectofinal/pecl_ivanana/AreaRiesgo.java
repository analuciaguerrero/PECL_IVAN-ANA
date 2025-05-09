package es.uah.matcomp.pcyd.proyectofinal.pecl_ivanana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import java.util.*;

public class AreaRiesgo {

    private final String identificadorZona;
    private final int id;
    private final List<Humano> habitantes = new ArrayList<>();
    private final List<Zombie> infectados = new ArrayList<>();
    private final ReentrantLock controlAcceso = new ReentrantLock(true);
    private final Random dado = new Random();

    private static int contadorZonas = 0;

    public AreaRiesgo(String nombreZona) {
        this.identificadorZona = nombreZona;
        this.id = contadorZonas++;
    }

    public String getNombre() {
        return identificadorZona;
    }

    public int getId() {
        return id;
    }

    public void añadirHumano(Humano h) {
        controlAcceso.lock();
        try {
            habitantes.add(h);
        } finally {
            controlAcceso.unlock();
        }
    }

    public void eliminarHumano(Humano h) {
        controlAcceso.lock();
        try {
            habitantes.remove(h);
        } finally {
            controlAcceso.unlock();
        }
    }

    public void añadirZombie(Zombie z) {
        controlAcceso.lock();
        try {
            infectados.add(z);
        } finally {
            controlAcceso.unlock();
        }
    }

    public void eliminarZombie(Zombie z) {
        controlAcceso.lock();
        try {
            infectados.remove(z);
        } finally {
            controlAcceso.unlock();
        }
    }

    public Optional<Humano> obtenerObjetivoAleatorio() {
        controlAcceso.lock();
        try {
            if (habitantes.isEmpty()) return Optional.empty();
            int pos = dado.nextInt(habitantes.size());
            return Optional.of(habitantes.get(pos));
        } finally {
            controlAcceso.unlock();
        }
    }

    public List<Humano> verHumanos() {
        controlAcceso.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(habitantes));
        } finally {
            controlAcceso.unlock();
        }
    }

    public List<Zombie> verZombis() {
        controlAcceso.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(infectados));
        } finally {
            controlAcceso.unlock();
        }
    }
}

