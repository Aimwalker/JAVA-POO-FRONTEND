package com.br.pdvfrontend.view;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class MainApp {
    public static void main(String[] args) {
        // Configura o Look and Feel moderno para toda a aplicação
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Falha ao inicializar o Look and Feel.");
            e.printStackTrace();
        }

        // Inicia a aplicação pela tela de login, que é o ponto de entrada correto
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
