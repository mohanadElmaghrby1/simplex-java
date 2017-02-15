package simplex.z;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

/**
 *
 * @author mohanad elmaghrby
 */
public class SimplexDesign extends JFrame {

    public JPanel panel1, panel2;
    public JLabel equation, constr;
    public JTextField constrains[], maxField;

    public ActionListener action;
    public JButton ok;
    public JCheckBox maxCh, minCh;
    public ButtonGroup groub = new ButtonGroup();

    public SimplexDesign() {
        //setFrame();
        try {
            setPanleComponent();
        } catch (Exception e) {

        }
    }

    public void setFrame() {
        setSize(500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("  Simplex Z  ");

        setLayout(new GridLayout(1, 1));

    }

    public void setPanleComponent() {

        String numberOfConstrains = JOptionPane.showInputDialog(null, "insert number of constrains  ", "S.T", INFORMATION_MESSAGE);
        int num = Integer.parseInt(numberOfConstrains);
        constrains = new JTextField[num];
        panel2 = new JPanel(new GridLayout(1, 2));
        panel1 = new JPanel(new GridLayout(10, 1, 5, 10));
        maxCh = new JCheckBox("max");
        // max.setBounds(50, 10, 10, 10);
        minCh = new JCheckBox("mini");
        
        
        groub.add(maxCh);
        groub.add(minCh);
        
        panel2.add(maxCh);
        panel2.add(minCh);
       
        panel1.add(panel2);
        panel2.setBackground(new Color(150,150,150)); 
       
        panel1.add(new JLabel("equation"));

        maxField = new JTextField("equation here ex. 1a+2b");
        maxField.setBackground(new Color(100, 100, 100));
        panel1.add(maxField);

        panel1.add(new JLabel("S.T"));

        for (int i = 0; i < num; i++) {
            constrains[i] = new JTextField("insert constrain " + (i + 1));
            constrains[i].setSize(new Dimension(5, 20));
            constrains[i].setBackground(new Color(100, 100, 100));
            panel1.add(constrains[i]);
        }

        ok = new JButton("ok");

        ok.setBackground(new Color(150, 150, 150));
        panel1.setBackground(new Color(150, 150, 150));
        panel1.add(ok);
        setFrame();
        //   Image bg = new ImageIcon("C:\\Users\\mohanad elmaghrby\\Documents\\NetBeansProjects\\GoodDesign\\or.jpg").getImage();

        add(panel1);
        recolorButtons(ok);
        ok.setBorderPainted(false);
        delete(maxField);

    }

    private void recolorButtons(JButton C) {
        C.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                C.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                C.setBackground(new Color(150, 150, 150));
            }
        });
    }

    private void delete(JTextField C) {
        C.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (maxField.getText().equals("equation here ex. 1a+2b"))
                      maxField.setText("");
               
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
//                if (maxField.getText()=="equation here ex. 1a+2b")
//                    maxField.setText("equation here ex. 1a+2b");
                 if (maxField.getText().equals(""))
                     maxField.setText("equation here ex. 1a+2b");

            }
        });
    }

}
