package view;

import view.UI.RoundBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CellComponent extends JPanel {
    private Color background;
    private Color validMoveColor = Color.decode("#FBFFB1");
    private GridType gridType;
    private BufferedImage image;
    private boolean validMove;
    private boolean hovered;
    private int cornerRadius;

    // Add this member variable to the CellComponent class
    private HoverListener hoverListener;

    public CellComponent(GridType gridType, Point location, int size) {
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        cornerRadius = size / 4;
        this.gridType = gridType;

        // Load the image based on the background color
//        try {
            if (gridType.equals(GridType.RIVER)) {
                background = Color.decode("#57C5B6");
            } else if (gridType.equals(GridType.LAND)) {
                background = Color.decode("#A9907E");
            } else if (gridType.equals(GridType.TRAP)) {
                background = Color.decode("#7AA874");
            } else if (gridType.equals(GridType.DENS)) {
                background = Color.decode("#D14D72");
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Add mouse listener to this cell component
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverListener != null) {
                    hoverListener.onHovered(CellComponent.this);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverListener != null) {
                   hoverListener.onExited(CellComponent.this);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Trigger the click event in ChessboardComponent
                getParent().dispatchEvent(new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiersEx(), getLocation().x + e.getX(), getLocation().y + e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        if (validMove || hovered) {
            g2d.setColor(validMoveColor);
        } else {
            g2d.setColor(background);
        }

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1, this.getWidth() - 1, this.getHeight() - 1, cornerRadius, cornerRadius);
        g2d.fill(roundedRectangle);
        RoundBorder roundBorder = new RoundBorder(Color.BLACK, 15, 15);
        setBorder(roundBorder);

        // Draw the image with rounded corners
//        if (image != null) {
//            g2d.setClip(roundedRectangle);
//            g2d.drawImage(image, 1, 1, this.getWidth() - 1, this.getHeight() - 1, this);
//        }
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    // Add this setter method to the CellComponent class
    public void setHoverListener(HoverListener hoverListener) {
        this.hoverListener = hoverListener;
    }
}
