import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

public class JavaSweeper extends JFrame
{
    private Game game;
    private  JPanel panel;
    private JLabel label;
    private final int COLS=9;
    private final int ROWS=9;
    private final int IMAZE_SIZE=50;
    private final int BOMBS=10;
    public static void main(String[] args)
    {
        //Окно
        new JavaSweeper();
    }
    private JavaSweeper()
    {
        game = new Game(COLS,ROWS,BOMBS);
        game.start();
        setImages();
        initlabel();
        initPanel();
        initFrame();

    }
    private void initlabel()
    {
        label= new JLabel("Welcome");
        add(label,BorderLayout.SOUTH);
    }
    private void initPanel()
    {
        panel=new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord:Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x *IMAZE_SIZE,coord.y*IMAZE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAZE_SIZE;
                int y = e.getY() / IMAZE_SIZE;
                Coord coord = new Coord(x,y);
                if(e.getButton()==MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton()==MouseEvent.BUTTON2)
                    game.start();
                if(e.getButton()==MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }

            private String getMessage()
            {
                switch (game.getState())
                {
                    case PLAYED :return "think Twice";
                    case BOMBED: return "You lose!";
                    case WINNER: return "Win!";
                    default: return "Welcome";
                }
            }
        });
        //Размер поля
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAZE_SIZE,
                Ranges.getSize().y*IMAZE_SIZE));
        add(panel);
    }
    private void initFrame()
    {
        //Закрытие при нажатии на крестик
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaSweeper");
        setResizable(false);
        setVisible(true);
        pack();
        //По центру
        setLocationRelativeTo(null);
    }

    private void setImages()
    {
        for (Box box:Box.values())
            box.image=getImage(box.name().toLowerCase());
    }
    private Image getImage(String name)
    {
        String filename = "img/" +name + ".png";
        //Указали папку как ресурс и обращаемся к ней, чтоб получить картинку
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
