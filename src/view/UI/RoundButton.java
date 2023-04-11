package view.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {

  public RoundButton(String text) {
    super(text);
    setContentAreaFilled(false);
    setFocusPainted(false);

    // Create a rounded border with padding
    int radius = 20;
    int padding = 8;
    Border border = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
    Border roundedBorder = new RoundBorder(getForeground()); // 使用RoundBorder创建圆角边框
    Border compound = BorderFactory.createCompoundBorder(roundedBorder, border);
    setBorder(compound);

    // Add a mouse listener to change the background color when the button is pressed
    buttonAbove(this);
  }

  private void buttonAbove(JButton button) {
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {

        setBackground(new Color(52, 102, 164));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setBackground(UIManager.getColor("Button.background"));
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
    g2.dispose();

    super.paintComponent(g);
  }
}