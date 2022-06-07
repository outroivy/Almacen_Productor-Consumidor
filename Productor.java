package Leon_Mont;

import java.util.Random;

public class Productor extends Thread {

    private Almacen almacen;
    private int uni, id;
    private int depositado, suma;
    private String camion;
    private final static Random generador = new Random();
    private boolean suspendido, muerto;
    private volatile boolean isRunning = true;

    public Productor(Almacen a, int uni, String camion, int id) {
        almacen = a;
        this.uni = uni;
        this.camion = camion;
        depositado = 0;
        this.id = id;
        suspendido = false;
        muerto = false;
    }

    public boolean isDead() {
        return muerto;
    }

    public void setStateDead(boolean muerto) {
        this.muerto = muerto;
    }

    public void setSuspension(boolean suspension) {
        this.suspendido = suspension;
    }

    public boolean isSuspended() {
        return suspendido;
    }

    public void run() {
        while (isRunning) {
            enSuspension();
            try {
                Thread.sleep(generador.nextInt(2000));
                depositado += almacen.establecer(uni, camion, suspendido, isRunning);
                if (camion.contains("León")) {
                    Pantalla.hilosL.setValueAt(depositado, id, 1);
                    Pantalla.progresoA1.setValue(almacen.obtenerTotalA() * 100 / 1000);
                    Pantalla.totalL.setText("Total. León: " + almacen.obtenerTotalA());
                } else {
                    Pantalla.hilosM.setValueAt(depositado, id, 1);
                    Pantalla.progresoA2.setValue(almacen.obtenerTotalA() * 100 / 1000);
                    Pantalla.totalM.setText("Total. Monterrey: " + almacen.obtenerTotalA());
                }
            } catch (InterruptedException excepcion) {
                interrupt();
            }
        }

    }

    public synchronized void parar() {
        suspendido = true;
    }

    public synchronized void reanudar() {
        suspendido = false;
        notifyAll();
    }

    public synchronized void enSuspension() {
        while (suspendido == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
    
    public void matar(){
        isRunning = (false);
    }
}
