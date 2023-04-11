package view;

import controller.Server;
import controller.User;
import view.UI.ImagePanel;
import view.UI.RoundButton;
import view.UI.RoundLabel;

import javax.swing.*;
import java.awt.*;
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
    mainPanel = new ImagePanel("C:\\Users\\jimmylaw21\\OneDrive - 南方科技大学\\桌面\\CS109-2023-Sping-ChessDemo\\resource\\gameBg.jpg");
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
          server.registerUser(usernameField.getText(), passwordField.getText());
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
    //展示游戏规则
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
