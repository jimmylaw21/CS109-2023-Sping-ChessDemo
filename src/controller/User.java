package controller;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
  private String name;
  private String password;
  private int score;

  public User() {
    this.name = "default";
    this.password = "default";
    this.score = 0;
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    this.score = 0;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  @Override
  public int compareTo(User o) {
    return this.score - o.score;
  }
}
