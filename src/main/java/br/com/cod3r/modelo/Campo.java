package br.com.cod3r.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObserver> observers = new ArrayList<>();


    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
        public void registrarObserver(CampoObserver observer){
        observers.add(observer);
        }
    private void notificarOberserver(CampoEvento evento){
        observers.stream()
                .forEach(o -> o.eventoOcorreu(this, evento));
    }

    boolean addVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int detalGeral = deltaColuna + deltaLinha;

        if (detalGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (detalGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;

            if (marcado){
                notificarOberserver(CampoEvento.MARCAR);
            }else notificarOberserver(CampoEvento.DESMARCAR);
        }
    }
    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
               notificarOberserver(CampoEvento.EXPLODIR);
               return true;
            }
            setAberto(true);
            aberto = true;
            notificarOberserver(CampoEvento.ABRIR);
            if (vizinhoSeguro()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    public boolean vizinhoSeguro() {
        return vizinhos.stream()
                .noneMatch(v -> v.minado);
    }

    void minar(){
        minado = true;
    }
    public boolean isMinado(){
        return minado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto){
            notificarOberserver(CampoEvento.ABRIR);
        }
    }

    public boolean isAberto(){
        return aberto;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
    public boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }
    public int minasNaVizinhanca(){
        return (int) vizinhos.stream()
                .filter(v -> v.minado)
                .count();
    }
    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
        notificarOberserver(CampoEvento.REINICIAR);
    }
}


