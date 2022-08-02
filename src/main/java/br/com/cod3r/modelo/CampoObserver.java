package br.com.cod3r.modelo;
@FunctionalInterface
public interface CampoObserver {
    public void eventoOcorreu(Campo campo, CampoEvento evento);
}
