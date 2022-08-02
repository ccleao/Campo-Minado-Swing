package br.com.cod3r.visao;

import br.com.cod3r.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro){
        setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.forEach(c -> add(new BotaoCampo(c)));
        tabuleiro.regitrarObservador(e -> {

            SwingUtilities.invokeLater(() -> {
                if (e.isGanhou()) {
                    JOptionPane.showMessageDialog(this, "You won");
                } else {
                    JOptionPane.showMessageDialog(this, "You lost");
                }
                tabuleiro.reiniciar();
            });
        });

    }
}

