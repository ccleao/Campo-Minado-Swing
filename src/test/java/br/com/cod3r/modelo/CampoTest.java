package br.com.cod3r.modelo;

import br.com.cod3r.excecao.ExplosaoException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CampoTest {
    private Campo campo = new Campo(3, 3);

    @Test
    public void testeVizinhoDistancia1Esquerda() {
        Campo vizinho = new Campo(3, 2);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    public void testeVizinhoDistancia1Direita() {
        Campo vizinho = new Campo(3, 4);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    public void testeVizinhoDistancia1Cima() {
        Campo vizinho = new Campo(2, 3);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    public void testeVizinhoDistancia1Baixo() {
        Campo vizinho = new Campo(4, 3);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }

    @Test
    public void testeVizinhoDistancia2() {
        Campo vizinho = new Campo(2, 2);
        boolean result = campo.addVizinho(vizinho);
        assertTrue(result);
    }
    @Test
    public void testeNaoVizinho() {
        Campo vizinho = new Campo(1, 1);
        boolean result = campo.addVizinho(vizinho);
        assertFalse(result);
    }
    @Test
    public void testeAlternarMarcacao(){
        campo.alternarMarcacao();;
        assertTrue(campo.isMarcado());
    }
    @Test
    public void testeAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();;
        campo.alternarMarcacao();;
        assertFalse(campo.isMarcado());
    }
    @Test
    public void testeValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir());
    }
    @Test
    public void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }
    @Test
    public  void testeAbrirMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }
    @Test
    public void testeAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaoException.class,() -> {
            campo.abrir();
                });
    }
    @Test
    public void testeAbrirComVizinhos(){

        Campo campo11 = new Campo(1, 1);
        Campo campo22 = new Campo(2, 2);

        campo11.addVizinho(campo22);
        campo.addVizinho(campo11);
        campo.abrir();
        assertFalse(campo.isAberto() && campo11.isAberto());

    }
    @Test
    public void testeAbrirComVizinhos2(){

        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 1);
        campo12.minar();

        Campo campo22 = new Campo(2, 2);
        campo22.addVizinho(campo11);
        campo22.addVizinho(campo12);

        campo.addVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && !campo11.isAberto());
    }
}