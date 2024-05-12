/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

import java.util.concurrent.Semaphore;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class Progreso extends Thread {
     private final Semaphore semaforoSprint;
    private final int semana;
     private final int porcentaje;
    private final JProgressBar sprint;
    private final JProgressBar total;
    
    private volatile boolean detenerHilo = false;

    public Progreso(Semaphore semaforoSprint, int semana, int porcentaje, JProgressBar sprint, JProgressBar total) {
        this.semaforoSprint = semaforoSprint;
        this.semana = semana;
        this.porcentaje = porcentaje;
        this.sprint = sprint;
        this.total = total;
    }
    public void detener() {
        detenerHilo = true;
    }
    

public void run() {
    try {
        System.out.println("El hilo está esperando hasta que se complete el sprint.");
        semaforoSprint.acquire();

        int i = 0;
        int duracionEnDias = semana * 7;

        // Ajuste en el cálculo del tiempo de espera
        int tiempoEspera = 10 * duracionEnDias; // 1 día -> 1000 milisegundos

        while (i <= 100) {
            Thread.sleep(tiempoEspera);
            sprint.setValue(i);
            i++;

            if (i == 100) {
                total.setValue(total.getValue() + porcentaje);
            }
        }
       
       
        System.out.println("Ha terminado el avance de un sprint en el proyecto");
        semaforoSprint.release();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

}
