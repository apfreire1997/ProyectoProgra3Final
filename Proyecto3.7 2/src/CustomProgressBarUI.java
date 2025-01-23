import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CustomProgressBarUI extends BasicProgressBarUI {
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        int barRectWidth = progressBar.getWidth();
        int barRectHeight = progressBar.getHeight();
        int amountFull = getAmountFull(progressBar.getInsets(), barRectWidth, barRectHeight);


        int progress = progressBar.getValue();
        Color fillColor;

        if (progress <= 33) {
            fillColor = new Color(218, 73, 58); // Rojo
        } else if (progress <= 66) {
            fillColor = new Color(241, 154, 56); // Naranja
        } else {
            fillColor = new Color(101, 212, 110); // Verde
        }


        g2d.setColor(progressBar.getBackground());
        g2d.fillRect(0, 0, barRectWidth, barRectHeight);


        g2d.setColor(fillColor);
        g2d.fillRect(0, 0, amountFull, barRectHeight);


        String progressText = progress + "%";
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(progressText);
        int stringHeight = fontMetrics.getHeight();
        g2d.setColor(Color.BLACK);
        g2d.drawString(progressText,
                (barRectWidth - stringWidth) / 2,
                (barRectHeight + stringHeight) / 2 - 2);
    }
}
