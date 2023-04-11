package view.animalChessComponent;

import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class WolfChessComponent extends ChessComponent {
  private ImageIcon gifImage;

  public WolfChessComponent(PlayerColor owner, int size) {
    super(owner, size);

    // Load the GIF image
    if (owner == PlayerColor.RED) {
      gifImage = new ImageIcon("C:\\Users\\jimmylaw21\\OneDrive - 南方科技大学\\桌面\\CS109-2023-Sping-ChessDemo\\resource\\Animal Supporter Asset Pack\\Timber Wolf\\TimberWolfIdleSide.gif");
    } else {
      gifImage = new ImageIcon("C:\\Users\\jimmylaw21\\OneDrive - 南方科技大学\\桌面\\CS109-2023-Sping-ChessDemo\\resource\\Animal Supporter Asset Pack\\Timber Wolf\\TimberWolfIdleSide.gif");
    }

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw the GIF image
    if (gifImage != null) {
      g.drawImage(gifImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  }
}