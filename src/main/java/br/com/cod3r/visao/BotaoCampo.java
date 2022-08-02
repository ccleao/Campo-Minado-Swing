package br.com.cod3r.visao;

import br.com.cod3r.modelo.Campo;
import br.com.cod3r.modelo.CampoEvento;
import br.com.cod3r.modelo.CampoObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObserver, MouseListener {


    private Campo campo;
    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_MARKED = new Color(8, 179, 247);
    private final Color BG_EXPLOSION = new Color(189, 66, 68);
    private final Color TEXTO = new Color(0, 100, 0);

    public BotaoCampo(Campo campo){
        this.campo = campo;
        setBackground(BG_DEFAULT);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        campo.registrarObserver(this);
        addMouseListener(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {

        switch (evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloDefault();
        }

    }

    private void aplicarEstiloDefault() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLOSION);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARKED);
        setForeground(Color.BLACK);
        setText("M");
    }

    private void aplicarEstiloAbrir() {

        if (campo.isMinado()){
            setBackground(BG_EXPLOSION);
            return;
        }
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch (campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !campo.vizinhoSeguro() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }
    public void mouseClicked(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1){
            campo.abrir();
        }else{
            campo.alternarMarcacao();
        }

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
}
