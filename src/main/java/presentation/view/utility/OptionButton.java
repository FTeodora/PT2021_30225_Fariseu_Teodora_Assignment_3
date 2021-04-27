package presentation.view.utility;

import presentation.view.windows.AppWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class OptionButton extends JButton {
    public OptionButton(String optionName,String imageName){
        this(imageName);
        setText(optionName);
        setHorizontalAlignment(SwingConstants.LEADING);
        setVerticalTextPosition(SwingConstants.CENTER);
        setFont(AppWindow.BUTTON_FONT);


    }
    public OptionButton(String imageName){
        setBackground(AppWindow.MAIN_COLOR);
        setBorder(new EmptyBorder(0,0,0,0));
        setForeground(AppWindow.TEXT_COLOR);
        try
        {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(imageName);
            BufferedImage image = ImageIO.read(input);

            setIcon(new ImageIcon(image));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
