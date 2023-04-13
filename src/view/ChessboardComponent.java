package view;


import controller.GameController;
import model.*;
import view.ChessComponent;
import view.animalChessComponent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private ChessGameFrame chessGameFrame;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();
    private boolean isAIPlaying = false;

    private GameController gameController;

    public ChessboardComponent(int chessSize, ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateSets();
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();

        // Clear all the chess components
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }


        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
//                    System.out.println(chessPiece.getOwner());
                    if (chessPiece.getName().equals("Elephant")){
                        gridComponents[i][j].add(
                            new ElephantChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Lion")){
                        gridComponents[i][j].add(
                            new LionChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Tiger")){
                        gridComponents[i][j].add(
                            new TigerChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Leopard")){
                        gridComponents[i][j].add(
                            new LeopardChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Wolf")){
                        gridComponents[i][j].add(
                            new WolfChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Dog")){
                        gridComponents[i][j].add(
                            new DogChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Cat")){
                        gridComponents[i][j].add(
                            new CatChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    } else if (chessPiece.getName().equals("Rat")){
                        gridComponents[i][j].add(
                            new RatChessComponent(
                                chessPiece.getOwner(),
                                CHESS_SIZE));
                    }
                }
            }
        }

    }

    public void initiateSets(){
        riverCell.clear();
        trapCell.clear();
        densCell.clear();

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(7,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));

        densCell.add(new ChessboardPoint(0,3));
        densCell.add(new ChessboardPoint(8,3));
    }

    public void initiateGridComponents() {
        //清空所有的格子
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    //颜色设置为A9907E
                    cell = new CellComponent(GridType.RIVER, calculatePoint(i, j), CHESS_SIZE);
                } else if (trapCell.contains(temp)) {
                    //颜色设置为617143
                    cell = new CellComponent(GridType.TRAP, calculatePoint(i, j), CHESS_SIZE);
                } else if (densCell.contains(temp)) {
                    //颜色设置为89375F
                    cell = new CellComponent(GridType.DENS, calculatePoint(i, j), CHESS_SIZE);
                } else {
                    //颜色设置为57C5B6
                    cell = new CellComponent(GridType.LAND, calculatePoint(i, j), CHESS_SIZE);
                }
                this.add(cell);
                gridComponents[i][j] = cell;

                cell.setHoverListener(new HoverListener() {
                    @Override
                    public void onHovered(CellComponent cellComponent) {
                        // Handle hover event here
                        cell.setHovered(true);
                        repaint();
                    }

                    @Override
                    public void onExited(CellComponent cellComponent) {
                        // Handle exit event here
                        cell.setHovered(false);
                        repaint();
                    }
                });
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    //胜利弹窗，可以选择重新开始或者退出弹窗
    public void showWinDialog(String winner) {
        int result = JOptionPane.showConfirmDialog(this, winner + " Win! Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            gameController.restart();
        }
    }

    public void undoStep(Step step) {
        ChessboardPoint from = step.getFrom();
        ChessboardPoint to = step.getTo();
        ChessPiece fromChessPiece = step.getFromChessPiece();
        ChessPiece toChessPiece = step.getToChessPiece();
        if (toChessPiece != null) {
            removeChessComponentAtGrid(to);
            if (toChessPiece.getName().equals("Elephant")){
                setChessComponentAtGrid(to, new ElephantChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Lion")){
                setChessComponentAtGrid(to, new LionChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Tiger")){
                setChessComponentAtGrid(to, new TigerChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Leopard")){
                setChessComponentAtGrid(to, new LeopardChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Wolf")){
                setChessComponentAtGrid(to, new WolfChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Dog")){
                setChessComponentAtGrid(to, new DogChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Cat")){
                setChessComponentAtGrid(to, new CatChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            } else if (toChessPiece.getName().equals("Rat")){
                setChessComponentAtGrid(to, new RatChessComponent(toChessPiece.getOwner(), CHESS_SIZE));
            }
        } else {
            removeChessComponentAtGrid(to);
        }

        if (fromChessPiece.getName().equals("Elephant")){
            setChessComponentAtGrid(from, new ElephantChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Lion")){
            setChessComponentAtGrid(from, new LionChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Tiger")){
            setChessComponentAtGrid(from, new TigerChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Leopard")){
            setChessComponentAtGrid(from, new LeopardChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Wolf")){
            setChessComponentAtGrid(from, new WolfChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Dog")){
            setChessComponentAtGrid(from, new DogChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Cat")){
            setChessComponentAtGrid(from, new CatChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Rat")){
            setChessComponentAtGrid(from, new RatChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        }


    }

    public void runStep(Step step) {
        ChessboardPoint from = step.getFrom();
        ChessboardPoint to = step.getTo();
        ChessPiece fromChessPiece = step.getFromChessPiece();
        //检查 to 上是否有棋子，如果有，先移除
        if (getGridComponentAt(to).getComponents().length > 0) {
            removeChessComponentAtGrid(to);
        }
        if (fromChessPiece.getName().equals("Elephant")){
            setChessComponentAtGrid(to, new ElephantChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Lion")){
            setChessComponentAtGrid(to, new LionChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Tiger")){
            setChessComponentAtGrid(to, new TigerChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Leopard")){
            setChessComponentAtGrid(to, new LeopardChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Wolf")){
            setChessComponentAtGrid(to, new WolfChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Dog")){
            setChessComponentAtGrid(to, new DogChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Cat")){
            setChessComponentAtGrid(to, new CatChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        } else if (fromChessPiece.getName().equals("Rat")){
            setChessComponentAtGrid(to, new RatChessComponent(fromChessPiece.getOwner(), CHESS_SIZE));
        }
        removeChessComponentAtGrid(from);
    }

    public void showValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint validMove : validMoves) {
            CellComponent cellComponent = getGridComponentAt(validMove);
            cellComponent.setValidMove(true);
            paintImmediately(this.getBounds());
        }
    }

    public void hideValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint validMove : validMoves) {
            CellComponent cellComponent = getGridComponentAt(validMove);
            cellComponent.setValidMove(false);
//            System.out.println("hide valid move" + validMove);
        }
    }

    public void showSelectedPoint(ChessboardPoint selectedPoint) {
        CellComponent cellComponent = getGridComponentAt(selectedPoint);
        cellComponent.setHovered(true);
        paintImmediately(this.getBounds());
    }

    public void hideSelectedPoint(ChessboardPoint selectedPoint) {
        CellComponent cellComponent = getGridComponentAt(selectedPoint);
        cellComponent.setHovered(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED && isAIPlaying == false) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }

    public boolean isAIPlaying() {
        return isAIPlaying;
    }

    public void setAIPlaying(boolean AIPlaying) {
        isAIPlaying = AIPlaying;
    }
}
