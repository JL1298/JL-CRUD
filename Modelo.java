package Siniestro;

public class Modelo {

    private int id;
    private String accidente;
    private String robo;
    private String danoParcial;
    private String estado;

    public Modelo() {
    }

    public Modelo(int id, String accidente, String robo, String danoParcial, String estado) {
        this.id = id;
        this.accidente = accidente;
        this.robo = robo;
        this.danoParcial = danoParcial;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccidente() {
        return accidente;
    }

    public void setAccidente(String accidente) {
        this.accidente = accidente;
    }

    public String getRobo() {
        return robo;
    }

    public void setRobo(String robo) {
        this.robo = robo;
    }

    public String getDanoParcial() {
        return danoParcial;
    }

    public void setDanoParcial(String danoParcial) {
        this.danoParcial = danoParcial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}