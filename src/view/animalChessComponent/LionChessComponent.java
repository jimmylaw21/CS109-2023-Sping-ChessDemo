package view.animalChessComponent;

import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class LionChessComponent extends ChessComponent {
  private ImageIcon gifImage;

  public LionChessComponent(PlayerColor owner, int size) {
    super(owner, size);

    // Load the GIF image
    gifImage = new ImageIcon(getClass().getResource("/Animal Supporter Asset Pack/Majestic Moose/MajesticMooseIdleSide.gif"));
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
