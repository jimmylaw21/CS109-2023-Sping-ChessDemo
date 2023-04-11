package view;

import controller.Server;
import controller.User;
import view.UI.ImagePanel;
import view.UI.RoundButton;
import view.UI.RoundLabel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    private final int ONE_CHESS_SIZE;
    
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 50;
    private Server server;
    private User user;
    private ImagePanel mainPanel;
    private JLabel statusLabel;
    private JButton RestartButton;
    private JButton UndoButton;
    private JButton SaveButton;
    private JButton LoadButton;
    private JButton SettingButton;
    private JButton ExitButton;
    private ChessboardComponent chessboardComponent;
    private String[] bgPaths = {"/gameBg.jpg", "/gameBg2.jpg", "/gameBg3.jpg", "/gameBg4.jpg"};
    public ChessGameFrame(int width, int height, Server server, User user) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;
        this.server = server;
        this.user = user;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        initComponents();
        addBackgroundImage();
        setupLayout();
    }

    private void addBackgroundImage() {
        URL defaultPath = getClass().getResource(bgPaths[0]);
        mainPanel = new ImagePanel(defaultPath);
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
    }

    private void initComponents() {
        addChessboard();
        addLabel();
        addRestartButton();
        addUndoButton();
        addSaveButton();
        addLoadButton();
        addSettingButton();
        addExitButton();
    }

    private void setupLayout(){
        setLayout(null);
        // 设置SettingButton在左上角
        SettingButton.setLocation(0, 0);
        mainPanel.add(SettingButton);

        // 设置Label在顶部
        statusLabel.setLocation(WIDTH / 2 - 150, 0);
        mainPanel.add(statusLabel);

        // 设置ExitButton在右上角
        ExitButton.setLocation(WIDTH - BUTTON_WIDTH, 0);
        mainPanel.add(ExitButton);

        // 设置棋盘在中央
        chessboardComponent.setLocation((WIDTH - (ONE_CHESS_SIZE * 7)) / 2, (HEIGHT - (ONE_CHESS_SIZE * 7)) / 2 - 120);
        mainPanel.add(chessboardComponent);

        // 设置RestartButton在左下角
        RestartButton.setLocation(0, HEIGHT - BUTTON_HEIGHT - 40);
        mainPanel.add(RestartButton);

        // 设置UndoButton在左下角
        UndoButton.setLocation(WIDTH / 3 - BUTTON_WIDTH / 3, HEIGHT - BUTTON_HEIGHT - 40);
        mainPanel.add(UndoButton);

        // 设置SaveButton在右下角
        SaveButton.setLocation(WIDTH * 2 / 3 - BUTTON_WIDTH * 2 / 3, HEIGHT - BUTTON_HEIGHT - 40);
        mainPanel.add(SaveButton);

        // 设置LoadButton在右下角
        LoadButton.setLocation(WIDTH - BUTTON_WIDTH, HEIGHT - BUTTON_HEIGHT - 40);
        mainPanel.add(LoadButton);
    }



    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE, this);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new RoundLabel("Turn 1: BLUE's turn", getBackground(), 20);
        statusLabel.setSize(300, BUTTON_HEIGHT);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        // 居中
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setVerticalAlignment(JLabel.CENTER);
    }

    private void addRestartButton() {
        RestartButton = new RoundButton("Restart");
        RestartButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        RestartButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        RestartButton.addActionListener(e -> {
            System.out.println("Click restart");
            chessboardComponent.getGameController().restart();
        });
    }

    private void addUndoButton() {
        UndoButton = new RoundButton("Undo");
        UndoButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        UndoButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        UndoButton.addActionListener(e -> {
            System.out.println("Click undo");
            chessboardComponent.getGameController().undo();
        });
    }

    private void addSaveButton() {
        SaveButton = new RoundButton("Save");
        SaveButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        SaveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        SaveButton.addActionListener(e -> {
            System.out.println("Click save");
            chessboardComponent.getGameController().save();
        });
    }

    private void addLoadButton() {
        LoadButton = new RoundButton("Load");
        LoadButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        LoadButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        LoadButton.addActionListener(e -> {
            System.out.println("Click load");
            chessboardComponent.getGameController().load();
        });
    }

    private void addSettingButton() {
        SettingButton = new RoundButton("Setting");
        SettingButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        SettingButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        SettingButton.addActionListener(e -> {
            System.out.println("Click setting");
            SwingUtilities.invokeLater(() -> {
                SettingGameFrame settingFrame = new SettingGameFrame(500, 750, this);
                settingFrame.setVisible(true);
            });
        });
    }

    private void addExitButton() {
        ExitButton = new RoundButton("Exit");
        ExitButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        ExitButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        ExitButton.addActionListener(e -> {
            System.out.println("Click exit");
            SwingUtilities.invokeLater(() -> {
                dispose();
                MainGameFrame mainGameFrame = new MainGameFrame(800, 1000, server, user);
                mainGameFrame.setVisible(true);
            });
        });
    }

    public void setBackgroundImage(int index) {
        URL path = getClass().getResource(bgPaths[index - 1]);
        mainPanel.setBackgroundImage(path);
        mainPanel.repaint();
    }

    public void updateStatus(String status) {
        statusLabel.setText(status);
    }

    public void recordWin() {
        server.getRankQueue().remove(user);
        user.setScore(user.getScore() + 1);
        server.getRankQueue().add(user);
    }



    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public Server getServer() {
        return server;
    }

    public User getUser() {
        return user;
    }
}

//    private void setupLayout() {
//        GridBagLayout gridBag = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();
//        setLayout(gridBag);
//        c.fill = GridBagConstraints.BOTH;
//        c.anchor = GridBagConstraints.CENTER;
//        c.insets = new Insets(5, 5, 5, 5);
//
//        // 设置SettingButton在左上角
//        Add_Component(this, gridBag, SettingButton, c, 0, 0, 1, 1, 0, 0);
//
//        // 设置Label在顶部
//        Add_Component(this, gridBag, statusLabel, c, 1, 0, 1, 1, 1, 0);
//
//        // 设置ExitButton在右上角
//        Add_Component(this, gridBag, ExitButton, c, 2, 0, 1, 1, 0, 0);
//
//        // 设置棋盘在中央
//        Add_Component(this, gridBag, chessboardComponent, c, 0, 1, 100, 1, 1, 1);
//
//        // 设置RestartButton在左下角
//        Add_Component(this, gridBag, RestartButton, c, 0, 10, 1, 1, 0, 0);
//
//        // 设置UndoButton在左下角
//        Add_Component(this, gridBag, UndoButton, c, 1, 10, 1, 1, 0, 0);
//
//        // 设置SaveButton在右下角
//        Add_Component(this, gridBag, SaveButton, c, 2, 10, 1, 1, 0, 0);
//
//        // 设置LoadButton在右下角
//        Add_Component(this, gridBag, LoadButton, c, 3, 10, 1, 1, 0, 0);
//    }

//    public static void Add_Component(JFrame jfr,GridBagLayout gbl,Component comp,GridBagConstraints gbc,int gridx,int gridy,int gridheight,int gridwidth,int weight_x,int weight_y)
//    {
//        gbc.weightx=weight_x;
//        gbc.weighty=weight_y;
//        gbc.gridheight=gridheight;
//        gbc.gridwidth=gridwidth;
//        gbc.gridx=gridx;
//        gbc.gridy=gridy;
//        gbl.setConstraints(comp, gbc);
//        jfr.add(comp);
//    }