package br.net.ricardotakemura.search.view.impl;

import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.presenter.impl.SimpleSearchPresenter;
import br.net.ricardotakemura.search.util.StringUtils;
import br.net.ricardotakemura.search.view.SearchView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

/**
 * Classe de visão do sistema via IDE (Interface gráfica — bônus)
 */
public class IDESearchView extends JFrame implements SearchView, ActionListener {

    private final SearchPresenter presenter;
    private final JTextField searchTextField;
    private final JButton searchButton;
    private final JTextArea resultTextArea;
    private final JLabel statusLabel;
    private long startTime;
    private String words;

    /**
     * Construtor (<i>default</i>)
     */
    public IDESearchView() {
        presenter = new SimpleSearchPresenter(this);
        searchTextField = new JTextField(50);
        searchTextField.setEnabled(false);
        searchButton = new JButton("Buscar");
        searchButton.addActionListener(this);
        searchButton.setEnabled(false);
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setAutoscrolls(true);
        resultTextArea.setBackground(Color.BLACK);
        resultTextArea.setForeground(Color.GREEN);
        statusLabel = new JLabel("Indexando...");
        var topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Palavras:"));
        topPanel.add(searchTextField);
        topPanel.add(searchButton);
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        setTitle("Desenvolvedor (a) de Busca - Desafio");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        startTime = System.currentTimeMillis();
        words = searchTextField.getText();
        presenter.search(words);
    }

    /**
     * @see SearchView#onCreatedIndex()
     */
    @Override
    public void onCreatedIndex() {
        new Thread(() -> {
            statusLabel.setText("Carregando...");
            presenter.loadIndexes();
        }).start();
    }

    /**
     * @see SearchView#onFailedIndex(Exception)
     */
    @Override
    public void onFailedIndex(Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar/criar arquivo de índice");
        setVisible(false);
    }

    /**
     * @see SearchView#onLoadedIndex()
     */
    @Override
    public void onLoadedIndex() {
        new Thread(() -> {
            statusLabel.setText("Pronto.");
            searchButton.setEnabled(true);
            searchTextField.setEnabled(true);
        }).start();
    }

    /**
     * @see SearchView#onSearchResult(Set)
     */
    @Override
    public void onSearchResult(Set<File> result) {
        var duration = System.currentTimeMillis() - startTime;
        if (result.isEmpty()) {
            resultTextArea.setText(String.format("Não foram encontradas ocorrências pelo termo \"%s\".\n", words));
        } else if (result.size() == 1) {
            resultTextArea.setText(String.format("Foi encontrada %d ocorrência pelo termo \"%s\".\n", result.size(), words));
            resultTextArea.append(String.format("O arquivo que possui \"%s\" é:\n", words));
            result.forEach(it -> resultTextArea.append(it.getPath() + "\n"));
            resultTextArea.setCaretPosition(0);
        } else {
            resultTextArea.setText(String.format("Foram encontradas %d ocorrências pelo termo \"%s\".\n", result.size(), words));
            resultTextArea.append(String.format("Os arquivos que possuem \"%s\" são:\n", words));
            result.forEach(it -> resultTextArea.append(it.getPath() + "\n"));
            resultTextArea.setCaretPosition(0);
        }
        statusLabel.setText(String.format("Tempo de resposta: %dms.", duration));
    }

    /**
     * @see SearchView#start(String...)
     */
    @Override
    public void start(String... args) {
        if (args.length == 1 && StringUtils.isNotBlank(args[0])) {
            setVisible(true);
            new Thread(() -> {
                statusLabel.setText("Indexando...");
                presenter.createIndexes(new File(args[0]));
            }).start();
        } else {
            System.err.println("Comando inválido.");
            System.err.println("Para executar esta aplicação, faça:");
            System.err.println("\"java -jar search.jar <diretorio>\" -- sendo <diretorio> o diretório com arquivos a serem indexados");
        }
    }
}
