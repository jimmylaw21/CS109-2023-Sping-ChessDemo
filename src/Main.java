import controller.GameController;
import controller.Server;
import controller.User;
import model.Chessboard;
import view.ChessGameFrame;
import view.MainGameFrame;
import music.MusicThread;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Server server = new Server();
            User user = new User();
            MainGameFrame mainFrame = new MainGameFrame(800, 1000, server, user);
            mainFrame.setVisible(true);
        });

//        // 创建音乐线程实例
//        MusicThread musicThread = new MusicThread("background_music.wav", true);
//
//        // 创建线程并启动
//        Thread music = new Thread(musicThread);
//        music.start();
//
//        // 调整音量
//        musicThread.setVolume(0.5f); // 设置音量为一半
//        float volume = musicThread.getVolume(); // 获取当前音量
//        System.out.println("volume: " + volume);
    }
    // TODO: 1. music and sound effect 2. platform 3. AI 4. network
}
