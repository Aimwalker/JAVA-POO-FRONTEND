package com.br.pdvfrontend.view;

import com.br.pdvfrontend.util.HttpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnPrimeiroAcesso; // NOVO BOTÃO

    public LoginView() {
        // Configurações básicas da janela
        setTitle("Login - PDV Posto de Combustível");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centralizar na tela
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento

        // Componentes da tela
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = new JTextField(15);
        add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtSenha = new JPasswordField(15);
        add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupar duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        btnEntrar = new JButton("Entrar");
        add(btnEntrar, gbc);

        // NOVO: Botão "Primeiro Acesso"
        gbc.gridy = 3; // Uma linha abaixo do botão Entrar
        btnPrimeiroAcesso = new JButton("Primeiro Acesso");
        add(btnPrimeiroAcesso, gbc);

        // Ação do botão
        btnEntrar.addActionListener(e -> realizarLogin());
        
        // Ação do novo botão
        btnPrimeiroAcesso.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidade de Primeiro Acesso (Cadastro) será implementada aqui.", "Primeiro Acesso", JOptionPane.INFORMATION_MESSAGE);
            // TODO: Implementar navegação para a tela de cadastro de usuário
        });
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        // Desabilita o botão para evitar cliques duplos
        btnEntrar.setEnabled(false);
        btnEntrar.setText("Autenticando...");

        // Executa a chamada de rede em uma thread separada para não travar a UI
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return HttpClient.getInstance().autenticar(usuario, senha);
            }

            @Override
            protected void done() {
                try {
                    boolean sucesso = get();
                    if (sucesso) {
                        // Abre a tela principal e fecha a de login
                        new TelaPrincipal().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginView.this, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(LoginView.this, "Falha na comunicação com o servidor.", "Erro de Rede", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Reabilita o botão
                    btnEntrar.setEnabled(true);
                    btnEntrar.setText("Entrar");
                }
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
