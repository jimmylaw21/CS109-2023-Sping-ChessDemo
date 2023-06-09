package view;

import controller.GameController;
import controller.GameMode;
import controller.Server;
import controller.User;
import model.Chessboard;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;

public class MainGameFrame extends JFrame {
  private final int WIDTH;
  private final int HEIGHT;
  private ImagePanel mainPanel;
  private JLabel titleLabel;
  private JButton singlePlayerButton;
  private JButton multiPlayerButton;
  private JButton settingsButton;
  private JButton exitButton;
  private Server server;
  private User user;

  public MainGameFrame(int width, int height, Server server, User user) {
    this.WIDTH = width;
    this.HEIGHT = height;
    this.server = server;
    this.user = user;

    initFrame();
    initComponents();
    setupLayout();
    setupListeners();
  }

  private void initFrame() {
    setTitle("2023 CS109 Project Demo");
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void initComponents() {
    mainPanel = new ImagePanel(getClass().getResource("/MainBackground.jpg"));
    setContentPane(mainPanel);
    mainPanel.setLayout(new GridBagLayout());

    titleLabel = createTitle("斗兽棋");
    singlePlayerButton = createButton("单人游戏");
    multiPlayerButton = createButton("多人游戏");
    settingsButton = createButton("设置");
    exitButton = createButton("退出");
  }

  private JLabel createTitle(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("微软雅黑", Font.BOLD, 64));
    //字体颜色为B8621B
    label.setForeground(new Color(184, 98, 27));
    return label;
  }

  private JButton createButton(String text) {
    JButton button = new RoundButton(text);
    button.setPreferredSize(new Dimension(200, 50));
    button.setFont(new Font("微软雅黑", Font.PLAIN, 24));
    return button;
  }

  private void setupLayout() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Add title to the layout
    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);


    // Add buttons to the layout
    addButtonToLayout(singlePlayerButton, gbc, 0, 1);
    addButtonToLayout(multiPlayerButton, gbc, 0, 2);
    addButtonToLayout(settingsButton, gbc, 0, 3);
    addButtonToLayout(exitButton, gbc, 0, 4);

  }

  private void addButtonToLayout(JButton button, GridBagConstraints gbc, int x, int y) {
    gbc.gridx = x;
    gbc.gridy = y;
    mainPanel.add(button, gbc);
  }

  private void setupListeners() {
    // Add listeners for buttons

    singlePlayerButton.addActionListener(e -> {
      SwingUtilities.invokeLater(() -> {
            dispose();
            ChessGameFrame ChessGameFrame = new ChessGameFrame(800, 1000, server, user);
            GameController gameController = new GameController(ChessGameFrame.getChessboardComponent(), new Chessboard(), GameMode.AI_2);
            ChessGameFrame.setVisible(true);
            this.dispose();
        });
      });
    multiPlayerButton.addActionListener(e -> {
      SwingUtilities.invokeLater(() -> {
            dispose();
            ChessGameFrame ChessGameFrame = new ChessGameFrame(800, 1000, server, user);
            GameController gameController = new GameController(ChessGameFrame.getChessboardComponent(), new Chessboard(), GameMode.LOCAL_PLAYER);
            ChessGameFrame.setVisible(true);

        });
      });
    settingsButton.addActionListener(e -> {
      SwingUtilities.invokeLater(() -> {
            SettingGameFrame SettingGameFrame = new SettingGameFrame(500, 750, this);
            SettingGameFrame.setVisible(true);
        });
      });
    exitButton.addActionListener(e -> System.exit(0));
  }

  public Server getServer() {
    return server;
  }

  public User getUser() {
    return user;
  }
}





