package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {
    private Chessboard model;
    private ChessboardComponent view;
    private GameMode gameMode;
    private AIPlayer aiPlayer;
    private PlayerColor currentPlayer;
    private int turnCount = 1;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private PlayerColor winner;
    private List<Step> stepList;
    private List<ChessboardPoint> validMoves;


    public GameController(ChessboardComponent view, Chessboard model, GameMode gameMode) {

        stepList = new LinkedList<>();
        validMoves = new ArrayList<>();

        this.view = view;
        this.model = model;
        this.gameMode = gameMode;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        if (gameMode == GameMode.AI_1 || gameMode == GameMode.AI_2 || gameMode == GameMode.AI_3) {
            aiPlayer = new AIPlayer(gameMode, model);
        }
    }

    public void restart() {
        model.initPieces();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        view.repaint();
        currentPlayer = PlayerColor.BLUE;
        winner = null;
        selectedPoint = null;
        turnCount = 1;
        stepList.clear();
        validMoves.clear();
        view.getChessGameFrame().updateStatus(String.format("Turn %d: %s's turn", turnCount, currentPlayer));
    }

    public void undo() {
        if (stepList.isEmpty()) {
            return;
        }
        Step step = stepList.remove(stepList.size() - 1);
        model.undoStep(step);
        view.undoStep(step);
        view.repaint();
        swapColor(true);
    }

    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view.getChessGameFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                outputStream.writeObject(stepList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view.getChessGameFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                List<Step> stepList = (List<Step>) inputStream.readObject();
                restart();
                for (Step step : stepList) {
                    model.runStep(step);
                    view.runStep(step);
//                    view.repaint();
                    swapColor(false);
                    try {
                        Thread.sleep(250);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.stepList = stepList;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // after a valid move swap the player
    private void swapColor(boolean isUndo) {
        if (!isUndo) {
            turnCount++;
        } else {
            turnCount--;
        }
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        view.getChessGameFrame().updateStatus(String.format("Turn %d: %s's turn", turnCount, currentPlayer));
    }

    private void densWin() {
        winner = currentPlayer;
        System.out.println("Winner is " + winner);
        view.getChessGameFrame().recordWin();
        view.showWinDialog(String.valueOf(winner));
    }

    private void annihilateWin() {
        if (model.checkAnnihilate(currentPlayer)) {
            winner = currentPlayer;
            System.out.println("Winner is " + winner);
            view.getChessGameFrame().recordWin();
            view.showWinDialog(String.valueOf(winner));
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            hideValidMoves();

            Step step = model.recordStep(selectedPoint, point, currentPlayer, turnCount);
            stepList.add(step);

            //如果是进入陷阱格子，降低棋子的等级至0
            model.solveTrap(selectedPoint, point);

            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));

            //如果是进入巢穴格子，结束游戏
            if (model.solveDens(point)) {
                densWin();
            }

            selectedPoint = null;
            swapColor(false);
            view.repaint();
            aiMove();
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();

                showValidMoves(point);
            }
        } else if (selectedPoint.equals(point)) {
            hideValidMoves();

            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            hideValidMoves();

            Step step = model.recordStep(selectedPoint, point, currentPlayer, turnCount);
            stepList.add(step);

            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));

            annihilateWin();

            selectedPoint = null;
            swapColor(false);
            view.repaint();
            aiMove();
        }
    }

    public void showValidMoves(ChessboardPoint point) {
        validMoves = model.getValidMoves(point);
        view.showValidMoves(validMoves);
    }

    public void hideValidMoves() {
        view.hideValidMoves(validMoves);
    }

    private void aiMove() {
        if (aiPlayer != null && currentPlayer != PlayerColor.BLUE) {
            Step aiStep = aiPlayer.generateMove(currentPlayer);
            if (aiStep != null) {
                selectedPoint = aiStep.getFrom();
                view.showSelectedPoint(selectedPoint);

                Timer timer1 = new Timer(1000, e -> {
                    view.hideSelectedPoint(selectedPoint);
                    showValidMoves(selectedPoint);
                });

                timer1.setRepeats(false);
                timer1.start();

                // 使用javax.swing.Timer创建一个定时器
                Timer timer2 = new Timer(2000, e -> {
                    hideValidMoves();
                    selectedPoint = null;

                    stepList.add(aiStep);

                    // 如果是进入陷阱格子，降低棋子的等级至0
                    model.solveTrap(aiStep.getFrom(), aiStep.getTo());

                    model.runStep(aiStep);
                    view.runStep(aiStep);
                    view.repaint();

                    // 检查是否赢了
                    if (model.solveDens(aiStep.getTo())) {
                        densWin();
                    } else {
                        annihilateWin();
                    }

                    swapColor(false);
                });

                // 设置定时器只执行一次
                timer2.setRepeats(false);
                timer2.start();
            }
        }
    }
}
