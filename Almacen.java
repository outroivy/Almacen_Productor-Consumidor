package Leon_Mont;

public class Almacen {

    private int capacidad = 1000, total, consumidoL, producidoL, consumidoM, producidoM;
    private String nombre;

    public Almacen(String nombre) {
        this.nombre = nombre;
        total = 0;
        consumidoL = 0;
        producidoL = 0;
        consumidoM = 0;
        producidoM = 0;
    }

    public void setConsumidoL(int consumidoL) {
        this.consumidoL = consumidoL;
    }

    public void setProducidoL(int producidoL) {
        this.producidoL = producidoL;
    }

    public void setConsumidoM(int consumidoM) {
        this.consumidoM = consumidoM;
    }

    public void setProducidoM(int producidoM) {
        this.producidoM = producidoM;
    }

    public synchronized int obtener(int valor, String camion, boolean suspendido, boolean vivo) throws InterruptedException {
        if (suspendido || !vivo) {
            return 0;
        } else {
            while (total == 0) {
                if (camion.contains("León")) {
                    Pantalla.almacen2.append("Camión " + camion + " trata de consumir. Almacen vacío. Camión espera.\n");
                } else {
                    Pantalla.almacen1.append("Camión " + camion + " trata de consumir. Almacen vacío. Camión espera.\n");
                }
                wait();
            }
            if ((total - valor) > 0) {
                total -= valor;
                if (camion.contains("León")) {
                    Pantalla.almacen2.append("Camión " + camion + " consume " + valor + ". \n");
                    consumidoL += valor;
                    Pantalla.hilosLT.setValueAt(consumidoL, 0, 2);
                } else {
                    Pantalla.almacen1.append("Camión " + camion + " consume " + valor + ", \n");
                    consumidoM += valor;
                    Pantalla.hilosMT.setValueAt(consumidoM, 0, 2);
                }
            }

            notifyAll();
            return valor;
        }
    }

    public synchronized int establecer(int valor, String camion, boolean suspendido, boolean vivo) throws InterruptedException {
        if (suspendido || !vivo) {
            return 0;
        } else {
            while (total == capacidad) {
                if (camion.contains("León")) {
                    Pantalla.almacen1.append("Camión " + camion + "  trata de producir. Almacen lleno. Camión espera.\n");
                } else {
                    Pantalla.almacen2.append("Camión " + camion + " trata de producir. Almacen lleno. Camión espera.\n");
                }
                wait();
            }
            if ((total + valor) <= capacidad) {
                total += valor;
                if (camion.contains("León")) {
                    Pantalla.almacen1.append("Camión " + camion + " produce " + valor + ". \n");
                    producidoL += valor;
                    Pantalla.hilosLT.setValueAt(producidoL, 0, 1);
                } else {
                    Pantalla.almacen2.append("Camión " + camion + " produce " + valor + ". \n");
                    producidoM += valor;
                    Pantalla.hilosMT.setValueAt(producidoM, 0, 1);
                }
            } else {
                wait();
            }

            notifyAll();
            return valor;
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int obtenerTotalA() {
        return total;
    }

    public int getConsumidoL() {
        return consumidoL;
    }

    public int getProducidoL() {
        return producidoL;
    }

    public int getConsumidoM() {
        return consumidoM;
    }

    public int getProducidoM() {
        return producidoM;
    }

}
