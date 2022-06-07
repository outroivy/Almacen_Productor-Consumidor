package Leon_Mont;

import java.util.Random;


public class Consumidor extends Thread {

    private Almacen almacen;
    private final static Random generador = new Random();
    private int uni, id, tomado = 0;
    private String camion;
    protected boolean suspendido, muerto;
    private volatile boolean isRunning = true;

    public Consumidor(Almacen a, int uni, String camion, int id) {
        almacen = a;
        this.uni = uni;
        this.camion = camion;
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

    public boolean isSuspended() {
        return suspendido;
    }

    public void setSuspension(boolean suspension) {
        this.suspendido = suspension;
    }

    @Override
    public void run() {
        while (isRunning) {
            enSuspension();
            try {
                Thread.sleep(generador.nextInt(2000));
                tomado += almacen.obtener(uni, camion, suspendido, isRunning);
                if (camion.contains("León")) {
                    Pantalla.hilosL.setValueAt(tomado, id, 2);
                } else {
                    Pantalla.hilosM.setValueAt(tomado, id, 2);
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
