package view;

import controller.Server;
import controller.User;
import view.UI.ImagePanel;
import view.UI.RoundButton;
import view.UI.RoundLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.List;

public class SettingGameFrame extends JFrame {

  private final int WIDTH;
  private final int HEIGHT;
  private final int BUTTON_WIDTH = 200;
  private final int BUTTON_HEIGHT = 50;
  private Server server;
  private User user;
  private MainGameFrame mainGameFrame;
  private ChessGameFrame chessGameFrame;
  private ImagePanel mainPanel;

  // TODO: 1.登录界面 2.排行榜 3.音量调整 4.背景切换 5.棋盘切换 6.规则介绍

  private RoundLabel mainLabel;
  private RoundButton loginButton;
  private RoundButton rankButton;
  private RoundButton volumeButton;
  private RoundButton backgroundButton;
  private RoundButton chessboardButton;
  private RoundButton ruleButton;
  private RoundButton backButton;

  public SettingGameFrame(int width, int height, JFrame mainFrame) {
    setTitle("2023 CS109 Project Demo"); //设置标题
    this.WIDTH = width;
    this.HEIGHT = height;

    if (mainFrame instanceof MainGameFrame) {
      this.mainGameFrame = (MainGameFrame) mainFrame;
      this.server = mainGameFrame.getServer();
      this.user = mainGameFrame.getUser();
    } else if (mainFrame instanceof ChessGameFrame) {
      this.chessGameFrame = (ChessGameFrame) mainFrame;
      this.server = chessGameFrame.getServer();
      this.user = chessGameFrame.getUser();
    } else {
      System.out.println("Error: mainFrame is not a MainGameFrame or ChessGameFrame");
    }



    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null); // Center the window.
//    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
    setLayout(null);

    initComponents();
    addBackgroundImage();
    setupLayout();
  }

  private void addBackgroundImage() {
    mainPanel = new ImagePanel(getClass().getResource("/gameBg.jpg"));
    setContentPane(mainPanel);
    mainPanel.setLayout(null);
  }

  private void initComponents() {
    addMainLabel();
    addLoginButton();
    addRankButton();
    addVolumeButton();
    addBackgroundButton();
    addChessboardButton();
    addRuleButton();
    addBackButton();
  }

  private void addMainLabel() {
    mainLabel = new RoundLabel("Settings", getBackground(), 20);
    mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
    mainLabel.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
    mainLabel.setVerticalAlignment(SwingConstants.CENTER);
  }

  private void addLoginButton() {
    loginButton = new RoundButton("Login");
    loginButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    loginButton.setFont(new Font("Arial", Font.BOLD, 24));
    loginButton.addActionListener(e -> {
      // 创建登录或注册的对话框
      JDialog loginDialog = new JDialog(this, "Login", true);
      loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      loginDialog.setSize(300, 200);
      loginDialog.setLocationRelativeTo(this);

      // 添加用户名和密码的输入框
      JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
      inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      inputPanel.add(new JLabel("Username:"));
      JTextField usernameField = new JTextField();
      inputPanel.add(usernameField);
      inputPanel.add(new JLabel("Password:"));
      JPasswordField passwordField = new JPasswordField();
      inputPanel.add(passwordField);

      // 添加登录,注册和取消按钮
      JButton loginButton = new JButton("Login");
      JButton registerButton = new JButton("Register");
      JButton cancelButton = new JButton("Cancel");
      loginButton.addActionListener(e1 -> {
        // TODO: 进行登录操作
        if (server.verifyUser(usernameField.getText(), passwordField.getText())) {
          user = server.getUser(usernameField.getText());
          updateLabel(usernameField.getText());
          JOptionPane.showMessageDialog(loginDialog, "Login successfully!");
          loginDialog.dispose();
        } else {
          JOptionPane.showMessageDialog(loginDialog, "Username or password is incorrect!");
        }
      });
      registerButton.addActionListener(e1 -> {
        // TODO: 进行注册操作
        if (server.verifyUser(usernameField.getText(), passwordField.getText())) {
          JOptionPane.showMessageDialog(loginDialog, "Username already exists!");
        } else {
          try {
            server.registerUser(usernameField.getText(), passwordField.getText());
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
          user = server.getUser(usernameField.getText());
          updateLabel(usernameField.getText());
          JOptionPane.showMessageDialog(loginDialog, "Register successfully!");
          loginDialog.dispose();
        }
      });
      cancelButton.addActionListener(e1 -> loginDialog.dispose());

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel.add(loginButton);
      buttonPanel.add(registerButton);
      buttonPanel.add(cancelButton);

      // 将所有组件添加到对话框中
      loginDialog.add(inputPanel, BorderLayout.CENTER);
      loginDialog.add(buttonPanel, BorderLayout.SOUTH);
      loginDialog.setVisible(true);
    });
  }


  private void addRankButton() {
    rankButton = new RoundButton("Rank");
    rankButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    rankButton.setFont(new Font("Arial", Font.BOLD, 24));
    rankButton.addActionListener(e -> {
      List<User> topPlayers = new ArrayList<>();
      if (mainGameFrame != null) {
        PriorityQueue<User> queue = mainGameFrame.getServer().getRankQueue();
        for (int i = 0; i < 5 && !queue.isEmpty(); i++) {
          topPlayers.add(queue.poll());
        }
      } else if (chessGameFrame != null) {
        PriorityQueue<User> queue = chessGameFrame.getServer().getRankQueue();
        for (int i = 0; i < 5 && !queue.isEmpty(); i++) {
          topPlayers.add(queue.poll());
        }
      }
      // 显示前五名的排名信息
      showRankDialog(topPlayers);

      // 将List<User> topPlayers中的User加回到优先队列中
      if (mainGameFrame != null) {
        PriorityQueue<User> queue = mainGameFrame.getServer().getRankQueue();
        for (User player : topPlayers) {
          queue.add(player);
        }
      } else if (chessGameFrame != null) {
        PriorityQueue<User> queue = chessGameFrame.getServer().getRankQueue();
        for (User player : topPlayers) {
          queue.add(player);
        }
      }
    });
  }

  private void showRankDialog(List<User> topPlayers) {
    // 创建JDialog对象
    JDialog dialog = new JDialog(this, "Rank", true);
    dialog.setSize(400, 300);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(null);
    dialog.setLayout(new BorderLayout());

    // 创建表头
    Object[] columnNames = {"Rank", "Name", "Score"};
    // 创建表格数据
    Object[][] rowData = new Object[topPlayers.size()][3];
    for (int i = 0; i < topPlayers.size(); i++) {
      User player = topPlayers.get(i);
      rowData[i][0] = i + 1;
      rowData[i][1] = player.getName();
      rowData[i][2] = player.getScore();
    }

    // 创建JTable对象
    JTable table = new JTable(rowData, columnNames);
    table.setEnabled(false);  // 禁用表格编辑功能
    JScrollPane scrollPane = new JScrollPane(table);

    // 将表格添加到弹窗中
    dialog.add(scrollPane, BorderLayout.CENTER);

    // 显示弹窗
    dialog.setVisible(true);
  }

  private void addVolumeButton() {
    volumeButton = new RoundButton("Volume");
    volumeButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    volumeButton.setFont(new Font("Arial", Font.BOLD, 24));
  }

  private void addBackgroundButton() {
    backgroundButton = new RoundButton("Background");
    backgroundButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    backgroundButton.setFont(new Font("Arial", Font.BOLD, 24));
    backgroundButton.addActionListener(e -> {
      System.out.println("backgroundButton clicked");
      if (chessGameFrame == null) {
        System.out.println("chessGameFrame is null");
        return;
      }
      // 创建背景选择对话框
      JDialog backgroundDialog = new JDialog(this, "Background", true);
      backgroundDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      backgroundDialog.setSize(300, 200);
      backgroundDialog.setLocationRelativeTo(this);


      // 添加背景选择按钮
      JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
      inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JButton background1Button = new JButton("Background 1");
      JButton background2Button = new JButton("Background 2");
      JButton background3Button = new JButton("Background 3");
      JButton background4Button = new JButton("Background 4");
      background1Button.addActionListener(e1 -> {
        backgroundDialog.dispose();
        chessGameFrame.setBackgroundImage(1);
      });
      background2Button.addActionListener(e1 -> {
        backgroundDialog.dispose();
        chessGameFrame.setBackgroundImage(2);
      });
      background3Button.addActionListener(e1 -> {
        backgroundDialog.dispose();
        chessGameFrame.setBackgroundImage(3);
      });
      background4Button.addActionListener(e1 -> {
        backgroundDialog.dispose();
        chessGameFrame.setBackgroundImage(4);
      });
      inputPanel.add(background1Button);
      inputPanel.add(background2Button);
      inputPanel.add(background3Button);
      inputPanel.add(background4Button);

      backgroundDialog.add(inputPanel, BorderLayout.CENTER);
      backgroundDialog.setVisible(true);
    });
  }

  private void addChessboardButton() {
    chessboardButton = new RoundButton("Chessboard");
    chessboardButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    chessboardButton.setFont(new Font("Arial", Font.BOLD, 24));
  }

  private void addRuleButton() {
    ruleButton = new RoundButton("Rule");
    ruleButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    ruleButton.setFont(new Font("Arial", Font.BOLD, 24));

    // Add an ActionListener to the ruleButton
    ruleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showGameRules();
      }
    });
  }

  private void showGameRules() {
    String gameRules = "Introduction\n" +
        "Jungle or Dou Shou Qi (斗兽棋), is a modern Chinese board game with an obscure history, as\n" +
        "shown in the Figure 1. The game is played on a 7×9 board and is popular with children in the\n" +
        "Far East. Jungle is a two-player strategy game and has been cited by The Playboy Winner's\n" +
        "Guide to Board Games as resembling the Western game Stratego.\n" +
        "Pieces\n" +
        "Each player owns 8 animal pieces representing different animals of various ranks, and higher\n" +
        "ranked animals can capture the animals of lower or equal rank. But there is a special case that\n" +
        "eleplant cannot capture the rat while the rat can capture the eleplant.\n" +
        "(Eleplant>Lion>Tiger>Leopard>Wolf>Dog>Cat>Rat)\n" +
        "Each player moves alternatively, and all pieces can move one square horizontally or\n" +
        "vertically, but not diagonally. As shown in the table, there are some special movements for\n" +
        "lion, tiger and rat. These will be explained in detail:\n" +
        " Entering the river: \n" +
        "  The rat is the only animal that may go onto a water square. \n" +
        "  After entering the river, the rat cannot be captured by any piece on land. \n" +
        "  Also, the rat in river cannot capture the eleplant on the land. \n" +
        " Jumping across the river: \n" +
        "  The lion and tiger can jump over a river vertically or horizonally. They jump from a \n" +
        " square on one edge of the river to the next non-water square on the other side. \n" +
        "  If that square contains an enemy piece of equal or lower rank, the lion or tiger \n" +
        " capture it as part of their jump.\n" +
        "  However, a jumping move is blocked (not permitted) if a rat of either color \n" +
        "currently occupies any of the intervening water squares." +
        "Chessboard \n" +
        "Jungle game has an interesting chessboard with differerent terrains including dens, traps and \n" +
        "rivers. After the initialization, the pieces start on squares with pictures corresponding to their \n" +
        "animals, which are invariably shown on the Jungle board.\n" +
        "The three kinds of special terrains are explained as:\n" +
        " Dens(兽穴): It is not allowed that the piece enters its own den. If the player's piece enters \n" +
        "the dens of his/her opponent, then the player wins,.\n" +
        " Trap(陷阱): If a piece entering the opponents's trap, then its rank is reduced into 0 \n" +
        "temporarily before exiting. The trapped piece could be captured by any pieces of the \n" +
        "defensing side.\n" +
        "  River(河流): They are located in the center of the chessboard, each comprising 6 squares in a \n" +
        "2×3 rectangle. Only rats could enter the river, and lions and tigers could jump across the \n" +
        "river. \n" +
        "Rules\n" +
        "1. Game Initialization: At the beginning, all 16 pieces are put into the chessboard as the initial \n" +
        "state. The initial state is shown in Figure 6.\n" +
        "2. Game Progress: The player with blue moves first. Two players take the turn alternatively until \n" +
        "the game is finished. When a player takes turn, he/she can select one of his pieces and do \n" +
        "one of the following:\n" +
        " Moving one square horizontally or vertically. For lion, tiger and rat, they also have special \n" +
        "movements related to the river squares, which have been introduced previously.\n" +
        " Capturing an opposing piece. In all captures, the captured pieces is removed from the board \n" +
        "and the square is occupied by the capturing piece. A piece can capture any enemy piece \n" +
        "following the rules introduced in \"Pieces\".\n" +
        "3. Game Termination: A player loses the game if one of the following happens:\n" +
        " The den is entered by his/her opponents.\n" +
        " All of his/her pieces are captured and he/her is unable to do any movement.";

    JTextArea textArea = new JTextArea(gameRules);
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    textArea.setMargin(new Insets(10, 10, 10, 10));
    textArea.setCaretPosition(0);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 300));

    JOptionPane.showMessageDialog(
        null,
        scrollPane,
        "游戏规则",
        JOptionPane.INFORMATION_MESSAGE
    );
  }

  private void addBackButton() {
    backButton = new RoundButton("Back");
    backButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    backButton.setFont(new Font("Arial", Font.BOLD, 24));
    backButton.addActionListener(e -> {
      dispose();
    });
  }

  public void updateLabel(String text) {
    mainLabel.setText(text);
  }

  private void setupLayout(){
    mainPanel.add(mainLabel);
    mainPanel.add(loginButton);
    mainPanel.add(rankButton);
    mainPanel.add(volumeButton);
    mainPanel.add(backgroundButton);
    mainPanel.add(chessboardButton);
    mainPanel.add(ruleButton);
    mainPanel.add(backButton);

    mainLabel.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50);
    loginButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + BUTTON_HEIGHT + 35);
    rankButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 2 * (BUTTON_HEIGHT + 35));
    volumeButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 3 * (BUTTON_HEIGHT + 35));
    backgroundButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 4 * (BUTTON_HEIGHT + 35));
    chessboardButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 5 * (BUTTON_HEIGHT + 35));
    ruleButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 6 * (BUTTON_HEIGHT + 35));
    backButton.setLocation(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 8 - 50 + 7 * (BUTTON_HEIGHT + 35));

    if (mainGameFrame != null) {
      updateLabel(mainGameFrame.getUser().getName());
    } if (chessGameFrame != null){
      updateLabel(chessGameFrame.getUser().getName());
    }
  }
}
