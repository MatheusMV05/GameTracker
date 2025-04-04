import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

// Classe que representa uma Conquista com persistência
class Achievement implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private boolean completed;

    public Achievement(String name) {
        this.name = name;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return name + (completed ? " (Concluída)" : " (Pendente)");
    }
}

// Classe que representa um Jogo com persistência
class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Achievement> achievements;

    public Game(String name) {
        this.name = name;
        achievements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }

    public int getTotalAchievements() {
        return achievements.size();
    }

    public int getCompletedAchievements() {
        int count = 0;
        for (Achievement a : achievements) {
            if (a.isCompleted()) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return name + " - " + getCompletedAchievements() + "/" + getTotalAchievements() + " Conquistas Concluídas";
    }
}

// Classe que gerencia os jogos com persistência
class GameTracker implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Game> games;

    public GameTracker() {
        games = new ArrayList<>();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }
}

// Renderizador customizado para exibição dos jogos na JList
class GameCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Game) {
            Game game = (Game)value;
            label.setText(game.toString());
            label.setBorder(new EmptyBorder(5, 5, 5, 5));
        }
        return label;
    }
}

// Renderizador customizado para exibição das conquistas na JList
class AchievementCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Achievement) {
            Achievement ach = (Achievement)value;
            label.setText(ach.toString());
            label.setBorder(new EmptyBorder(5, 5, 5, 5));
        }
        return label;
    }
}

// Classe que constrói a interface gráfica aprimorada do GameTracker
class GameTrackerGUI extends JFrame {
    private static final String DATA_FILE = "gameTrackerData.ser";
    private GameTracker tracker;
    private DefaultListModel<Game> gameListModel;
    private JList<Game> gameList;

    private DefaultListModel<Achievement> achievementListModel;
    private JList<Achievement> achievementList;

    private JLabel statusLabel;

    public GameTrackerGUI() {
        // Carrega os dados salvos ou inicia um novo tracker
        tracker = loadData();
        if (tracker == null) {
            tracker = new GameTracker();
        }

        setTitle("Game Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,650);
        setLocationRelativeTo(null);
        initLookAndFeel();
        initComponents();

        // Salva os dados quando a janela for fechada
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });
    }

    // Configura o LookAndFeel Nimbus para uma interface mais moderna
    private void initLookAndFeel() {
        try {
            for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception e){
            System.out.println("Nimbus Look and Feel não disponível, usando o padrão.");
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Criação da barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Jogo");
        JMenuItem addGameMenuItem = new JMenuItem("Adicionar Jogo");
        JMenuItem removeGameMenuItem = new JMenuItem("Remover Jogo");
        gameMenu.add(addGameMenuItem);
        gameMenu.add(removeGameMenuItem);
        menuBar.add(gameMenu);

        JMenu achievementMenu = new JMenu("Conquista");
        JMenuItem addAchMenuItem = new JMenuItem("Adicionar Conquista");
        JMenuItem markAchMenuItem = new JMenuItem("Marcar Conquista como Concluída");
        achievementMenu.add(addAchMenuItem);
        achievementMenu.add(markAchMenuItem);
        menuBar.add(achievementMenu);

        setJMenuBar(menuBar);

        // Painel para exibição dos jogos
        gameListModel = new DefaultListModel<>();
        for (Game g : tracker.getGames()) {
            gameListModel.addElement(g);
        }
        gameList = new JList<>(gameListModel);
        gameList.setCellRenderer(new GameCellRenderer());
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane gameScrollPane = new JScrollPane(gameList);
        gameScrollPane.setBorder(BorderFactory.createTitledBorder("Jogos"));

        // Painel para exibição das conquistas
        achievementListModel = new DefaultListModel<>();
        achievementList = new JList<>(achievementListModel);
        achievementList.setCellRenderer(new AchievementCellRenderer());
        achievementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane achievementScrollPane = new JScrollPane(achievementList);
        achievementScrollPane.setBorder(BorderFactory.createTitledBorder("Conquistas"));

        // Divisão central entre jogos e conquistas com JSplitPane responsivo
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameScrollPane, achievementScrollPane);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // Painel superior com botões para ações rápidas
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addGameButton = new JButton("Adicionar Jogo");
        JButton removeGameButton = new JButton("Remover Jogo");
        JButton addAchievementButton = new JButton("Adicionar Conquista");
        JButton markAchievementButton = new JButton("Marcar como Concluída");
        buttonPanel.add(addGameButton);
        buttonPanel.add(removeGameButton);
        buttonPanel.add(addAchievementButton);
        buttonPanel.add(markAchievementButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Barra de status para feedback
        statusLabel = new JLabel("Bem-vindo ao Game Tracker!");
        statusLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(statusLabel, BorderLayout.SOUTH);

        // Ações dos botões e menus
        ActionListener addGameAction = e -> addGame();
        addGameMenuItem.addActionListener(addGameAction);
        addGameButton.addActionListener(addGameAction);

        ActionListener removeGameAction = e -> removeGame();
        removeGameMenuItem.addActionListener(removeGameAction);
        removeGameButton.addActionListener(removeGameAction);

        ActionListener addAchievementAction = e -> addAchievement();
        addAchMenuItem.addActionListener(addAchievementAction);
        addAchievementButton.addActionListener(addAchievementAction);

        ActionListener markAchievementAction = e -> markAchievement();
        markAchMenuItem.addActionListener(markAchievementAction);
        markAchievementButton.addActionListener(markAchievementAction);

        // Atualiza a lista de conquistas ao selecionar um jogo
        gameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateAchievementList();
            }
        });
    }

    // Método para adicionar um novo jogo
    private void addGame() {
        String gameName = JOptionPane.showInputDialog(this, "Digite o nome do jogo:", "Adicionar Jogo", JOptionPane.PLAIN_MESSAGE);
        if (gameName != null) {
            gameName = gameName.trim();
            if (!gameName.isEmpty()) {
                Game game = new Game(gameName);
                tracker.addGame(game);
                gameListModel.addElement(game);
                statusLabel.setText("Jogo '" + gameName + "' adicionado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(this, "Nome do jogo não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para remover o jogo selecionado
    private void removeGame() {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o jogo '" + selectedGame.getName() + "'?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tracker.removeGame(selectedGame);
                gameListModel.removeElement(selectedGame);
                achievementListModel.clear();
                statusLabel.setText("Jogo '" + selectedGame.getName() + "' removido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um jogo para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para adicionar uma nova conquista ao jogo selecionado
    private void addAchievement() {
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            String achName = JOptionPane.showInputDialog(this, "Digite o nome da conquista:", "Adicionar Conquista", JOptionPane.PLAIN_MESSAGE);
            if (achName != null) {
                achName = achName.trim();
                if (!achName.isEmpty()) {
                    Achievement ach = new Achievement(achName);
                    selectedGame.addAchievement(ach);
                    achievementListModel.addElement(ach);
                    gameList.repaint();
                    statusLabel.setText("Conquista '" + achName + "' adicionada ao jogo '" + selectedGame.getName() + "'.");
                } else {
                    JOptionPane.showMessageDialog(this, "Nome da conquista não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um jogo para adicionar uma conquista.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para marcar a conquista selecionada como concluída
    private void markAchievement() {
        Game selectedGame = gameList.getSelectedValue();
        Achievement selectedAchievement = achievementList.getSelectedValue();
        if (selectedGame != null && selectedAchievement != null) {
            if (!selectedAchievement.isCompleted()) {
                selectedAchievement.setCompleted(true);
                achievementList.repaint();
                gameList.repaint();
                statusLabel.setText("Conquista '" + selectedAchievement.getName() + "' marcada como concluída.");
            } else {
                JOptionPane.showMessageDialog(this, "Esta conquista já foi marcada como concluída.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma conquista para marcar como concluída.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Atualiza a lista de conquistas com base no jogo selecionado
    private void updateAchievementList() {
        achievementListModel.clear();
        Game selectedGame = gameList.getSelectedValue();
        if (selectedGame != null) {
            for (Achievement ach : selectedGame.getAchievements()) {
                achievementListModel.addElement(ach);
            }
        }
    }

    // Salva os dados em um arquivo
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(tracker);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Carrega os dados do arquivo (se existir)
    private GameTracker loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof GameTracker) {
                return (GameTracker) obj;
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

// Classe principal para iniciar a aplicação
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameTrackerGUI gui = new GameTrackerGUI();
            gui.setVisible(true);
        });
    }
}
