package controller;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 这个Server现在只是一个简单的Map，用来存储用户信息
 * 它还没有实现网络功能，所以现在只能在本地运行
 * 但是可以改成实现网络功能，开启一个服务器，让多个客户端连接
 * 并对每个连接了的客户端开启一个ClientHandler线程来处理客户端的请求
 */
public class Server implements Serializable {
  private final URL USERS_FILE = getClass().getResource("/users.txt");
  private Map<String, String> userMap = new HashMap<String, String>();
  private PriorityQueue<User> rankQueue = new PriorityQueue<>();
  public Server() {
    loadUsers();
  }

  public void registerUser(String key, String value) throws IOException {
    User user = new User(key, value);
    rankQueue.add(user);
    userMap.put(key, value);
    saveUsers();
  }

  public boolean verifyUser(String key, String value) {
    // 查找用户
    if (userMap.containsKey(key)) {
      // 验证密码
      if (userMap.get(key).equals(value)) {
        return true;
      }
    }
    return false;
  }

  private void saveUsers() throws IOException {
    URLConnection connection = USERS_FILE.openConnection();
    connection.setDoOutput(true);
    try (ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream())) {
      outputStream.writeObject(userMap);
      outputStream.writeObject(rankQueue);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadUsers() {
    try (ObjectInputStream inputStream = new ObjectInputStream(USERS_FILE.openStream())) {
      userMap = (Map<String, String>)inputStream.readObject();
      rankQueue = (PriorityQueue<User>)inputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public Map<String, String> getUserMap() {
    return userMap;
  }

  public PriorityQueue<User> getRankQueue() {
    return rankQueue;
  }

  public User getUser(String username) {
    for (User user : rankQueue) {
      if (user.getName().equals(username)) {
        return user;
      }
    }
    return null;
  }

}
