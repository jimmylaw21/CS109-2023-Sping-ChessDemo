package controller;

import model.Chessboard;
import model.PlayerColor;
import model.Step;

import java.util.Collections;
import java.util.List;

public class AIPlayer {
  private GameMode gameMode;
  private Chessboard model;

  public AIPlayer(GameMode gameMode, Chessboard model) {
    this.gameMode = gameMode;
    this.model = model;
  }

  public Step generateMove(PlayerColor color) {
    // 生成一个合适的AI走棋步骤
    // 简化起见，这里仅选择一个合法的随机移动
    if (gameMode == GameMode.AI_1) {
      return generateMove1(color);
    } else if (gameMode == GameMode.AI_2) {
      return generateMove2(color);
    } else if (gameMode == GameMode.AI_3) {
      return generateMove3(color);
    }
    return null;
  }

  // 随机
  private Step generateMove1(PlayerColor color) {
    List<Step> steps = model.getValidSteps(color);
    if (steps.size() > 0) {
      return steps.get((int) (Math.random() * steps.size()));
    }
    return null;
  }
  // 贪心
  private Step generateMove2(PlayerColor color) {
    List<Step> steps = model.getValidStepsWithValue(color);
    //从大到小排序
    Collections.sort(steps);
    //打印value
    for (Step step : steps) {
      System.out.print(step.getValue() + " ");
    }
    System.out.println();
    if (steps.size() > 0) {
      //找到最大的几个value，从中随机选一个
      int max = steps.get(0).getValue();
      int count = 0;
      for (Step step : steps) {
        if (step.getValue() == max) {
          count++;
        } else {
          break;
        }
      }
      return steps.get((int) (Math.random() * count));
    }
    return null;
  }
  // 搜索
  private Step generateMove3(PlayerColor color) {
    return null;
  }
}
