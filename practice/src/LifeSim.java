import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class LifeSim extends JFrame {

    private static final long	serialVersionUID	= 3400265056061021539L;

    private LifePanel			lifePanel			= null;
    private JButton				button1				= null;
    private JButton				button2				= null;
    private JButton				button3				= null;
    private JSlider				slider				= null;

    public LifeSim(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        lifePanel = new LifePanel();
        // размеры поля
        lifePanel.initialize(100, 70);
        add(lifePanel);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        button1 = new JButton("Запустить");
        toolBar.add(button1);
        button2 = new JButton("Очистить поле");
        toolBar.add(button2);
        button3 = new JButton("Испортить");
        toolBar.add(button3);

        // бегунок, регулирующий скорость симуляции (задержка в мс между шагами симуляции)
        slider = new JSlider(1, 200);
        slider.setValue(50);
        lifePanel.setUpdateDelay(slider.getValue());
        slider.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                lifePanel.setUpdateDelay(slider.getValue());
            }
        });

        toolBar.addSeparator();
        toolBar.add(new JLabel(" Быстро"));
        toolBar.add(slider);
        toolBar.add(new JLabel("Медленно"));

        // запуск/остановка симуляции; попутно меняется надпись на кнопке
        button1.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (lifePanel.isSimulating()) {
                    lifePanel.stopSimulation();
                    button1.setText("Запустить");
                } else {
                    lifePanel.startSimulation();
                    button1.setText("Остановить");
                }
            }
        });
        // очистка поля
        button2.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                synchronized (lifePanel.getLifeModel()) {
                    lifePanel.getLifeModel().clear();
                    lifePanel.repaint();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                synchronized (lifePanel.getLifeModel()) {
                    lifePanel.getLifeModel().setRand();
                    lifePanel.repaint();
                }
            }
        });
        button1.setMaximumSize(new Dimension(100, 50));
        button2.setMaximumSize(new Dimension(100, 50));
        button3.setMaximumSize(new Dimension(100, 50));
        slider.setMaximumSize(new Dimension(300, 50));
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LifeSim("LifeSim");
            }
        });
    }

}
