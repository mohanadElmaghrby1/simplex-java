/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author compustar
 */


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mo2men
 */
public class Dualconvert extends JFrame {


    private JPanel largepanel;//panel cotain other panels
    private JPanel obfpanel;//objective function panel
    private JPanel conpanel;//condition panel
    private JPanel buttunpanel;//buttun panel
    private JPanel matrix_con_array;//array of condition panel
    private JButton ok;
    private JButton cancel;
    private JButton back;
    private JComboBox maxandmincombobox;
    private JComboBox most_and_less_combobox[];
    private JTextField objectivefunctiontextfield[];
    private JTextField result_jtextfiled[];
    private JTextField matrix_jtextfiald[][];

    static double objectivefunctionarray[];
    static double new_objectivefunctionarray[];
    static double matrix_value_array[][];
    static double new_matrix_value_array[][];

    static double result_value_array[];
    static int Put_value_insteadof_most_and_less[];
    int select=0;
    int n;
    private JLabel obflabel;
    private JLabel matrixlabel;
    private JLabel random_jlabel;
    private int con;
    
    ////////////////////////////////////////////////////////////////////////////constructor

    public Dualconvert(int num, int condition) {


        super("DUAl Frame");//to put title to the main frame
        con = condition;
        n=num;
        this.setBackground(Color.WHITE);
        this.setLocation(400, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //////////////////////////////Ceartion and layout of panels  :--
        //objectivefunctionarray=new double[objectivefunctiontextfield.length];


        largepanel = new JPanel();
        buttunpanel = new JPanel();
        conpanel = new JPanel();
        obfpanel = new JPanel();
        matrix_con_array = new JPanel();


        largepanel.setLayout(new GridLayout(3, 1));//layout tomain panel
        matrix_con_array.setLayout(new GridLayout(condition, num));
        obfpanel.setLayout(new FlowLayout(FlowLayout.LEFT));//layout to objective function panel
        obfpanel.setBorder(new TitledBorder("Objective Function"));
        conpanel.setLayout(new GridLayout(1, 3));//layout to conditon panel
        conpanel.setBorder(new TitledBorder("Conditions"));
        buttunpanel.setLayout(new FlowLayout(FlowLayout.CENTER));//layout to buttun panel



        /////////////////////////////////////////////////////////objective function panel :::---
        //////////////////////////////cearte max and min combobox ::--
        maxandmincombobox = new JComboBox(new String[]{"MAX", "MIN"});
        obfpanel.add(maxandmincombobox);

        most_and_less_combobox = new JComboBox[condition];

        result_jtextfiled = new JTextField[condition];

        objectivefunctionarray = new double[num];//to get value in objective function  text field



        result_value_array = new double[condition];// to get value in result text field

        objectivefunctiontextfield = new JTextField[num];
        matrix_jtextfiald = new JTextField[condition][num];
        matrix_value_array = new double[condition][num];//to get value in condition text field






        ////////////////////////////to create array of text field to objective function::--

        for (int i = 0; i < num; i++) {
            objectivefunctiontextfield[i] = new JTextField(4);
            obfpanel.add(objectivefunctiontextfield[i]);
            if (i == num - 1) {
                obflabel = new JLabel("X" + (i + 1));
                obfpanel.add(obflabel);
            } else {
                obflabel = new JLabel("X" + (i + 1) + "+");
                obfpanel.add(obflabel);
            }
        }





        /////////////////////////////////////////////////////////////condition panel ::--

        for (int i = 0; i < condition; i++) {
            for (int j = 0; j < num; j++) {
                matrix_jtextfiald[i][j] = new JTextField(1);
                matrix_con_array.add(matrix_jtextfiald[i][j]);
                if (j == num - 1) {
                    matrixlabel = new JLabel("x" + (j + 1));
                    matrix_con_array.add(matrixlabel);
                    most_and_less_combobox[i] = new JComboBox(new Object[]{">=", "<=", "="});
                    matrix_con_array.add(most_and_less_combobox[i]);
                    random_jlabel = new JLabel(" ");
                    matrix_con_array.add(random_jlabel);
                    result_jtextfiled[i] = new JTextField(2);
                    matrix_con_array.add(result_jtextfiled[i]);
                } else {
                    matrixlabel = new JLabel("x" + (j + 1) + "+");
                    matrix_con_array.add(matrixlabel);
                }
            }
        }



        //////////////////////////////ok buttun and action it ::--
        ok = new JButton("OK");
        ok.setToolTipText("ok");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {



                //---------------------------------------------------------------------------------------------------
                //-------------------------to check if there is one or more text field is empty::::--
                try {

                    //-------------objective function array ::--
                    for (int i = 0; i <n; i++) {
                        objectivefunctionarray[i] = Double.parseDouble(objectivefunctiontextfield[i].getText());
                    }

                    //-------------matrix array ::--
                    for (int i = 0; i < con; i++) {
                        for (int j = 0; j <n ; j++) {
                            matrix_value_array[i][j] = Double.parseDouble(matrix_jtextfiald[i][j].getText());
                        }
                    }
                    //------------double result array ::--
                    for (int i = 0; i < con; i++) {
                        result_value_array[i] = Double.parseDouble(result_jtextfiled[i].getText());
                    }
                    //------------
//                    //---------------to check the value in combo box ::-
//                    for (int i = 0; i < con; i++) {
//                        if (most_and_less_combobox[i].getSelectedItem() == ">=") {
//                            count_of_column = count_of_column + 2;
//                        } else if (most_and_less_combobox[i].getSelectedItem() == "<=") {
//                            count_of_column = count_of_column + 1;
//                        } else {
//                            count_of_column = count_of_column + 1;
//                        }
//
//                    }



                 //--------------
                     ////////////////////////////to check the value in max and min combo box ::--
                    //min=1//max=0
                   if (maxandmincombobox.getSelectedItem().equals("MIN")) {
                      select=1;
                   }


                   Put_value_insteadof_most_and_less=new int[con];
                    //check the value in the combo box of most and less array :;--
                    for (int i = 0; i < most_and_less_combobox.length; i++) {
                        if(most_and_less_combobox[i].getSelectedItem()==">="){
                          Put_value_insteadof_most_and_less[i]=0;
                        }
                         else if(most_and_less_combobox[i].getSelectedItem()=="<="){
                          Put_value_insteadof_most_and_less[i]=1;
                        }
                         else{
                         Put_value_insteadof_most_and_less[i]=2;
                         }

                     }


                    // to modify the matrix
                    for(int Com=0;Com<con;Com++){
                        for (int i = 0; i <n; i++) {
                            if(most_and_less_combobox[Com].getSelectedItem()==">="){
                               matrix_value_array[Com][i]=matrix_value_array[Com][i]*-1;
                               
                            }
                        }
                    }
                   
                   
                   //to modify the result array
                   for(int Com=0;Com<con;Com++){
                      if(Put_value_insteadof_most_and_less[Com]==0){
                               result_value_array[Com]=result_value_array[Com]*-1;
                      }
                   }

                  // create dual objective function ::--
                   new_objectivefunctionarray=new double[con];
                   for(int i=0;i<con;i++){
                     new_objectivefunctionarray[i]=result_value_array[i];

                   }



                   //create the matrix of new dual array matrix ::-
                   new_matrix_value_array=new double[n][con];
                   for(int i=0;i<n;i++){
                       for(int j=0;j<con;j++){
                          new_matrix_value_array[i][j]=matrix_value_array[j][i];
                       }
                   }
                   



                }


                //----------------------------------------------------------------------------------------------------------
                catch (Exception k) {
                    JOptionPane.showMessageDialog(null, "there is no value in objective function ");
                }



               

                System.out.print("max or min : "+select+"\n");
                //----------------------------------------------------------------check
                System.out.print("Objective Function Value \n");

                for (int i = 0; i < objectivefunctiontextfield.length; i++) {
                    System.out.print(objectivefunctionarray[i] + "  ");
                }
                System.out.println("");


                //-----------------
                 System.out.print("Matrix Value \n");
                for (int i = 0; i < con; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print(matrix_value_array[i][j]);
                    }
                    System.out.print("\n");
                }

                //---------------
                 System.out.print("Result Value \n");
                for (int i = 0; i < con; i++) {
                    System.out.print(result_value_array[i] + "\n");
                }


               //---------------
                 System.out.print("dual objective function \n");
                 if(select==1){
                       System.out.print(" MAX F = ");
                    }
                    else{
                       System.out.print(" MIN F = ");

                    }
                for (int i = 0; i < con; i++) {
                    if(i==con-1){
                        System.out.print("("+new_objectivefunctionarray[i] + " y"+(i+1)+ ")" );
                    }
                   else{
                    System.out.print("("+new_objectivefunctionarray[i] + " y"+(i+1)+")" + " +" );
                    }
                }


                 //-----------------
                 System.out.print("\n \n CONDITIONS of dual matrix  \n");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < con; j++) {
                         if(j==con-1){
                        System.out.print("("+new_matrix_value_array[i][j]+ " y"+(j+1)+")");
                         }
                        else{
                       System.out.print("("+new_matrix_value_array[i][j]+ " y"+(j+1)+")"+" + ");
                        }
                        
                    }

                    if(Put_value_insteadof_most_and_less[i]==0){
                      System.out.print(" >= "+ objectivefunctionarray[i]+"\n");
                    }
                    else if(Put_value_insteadof_most_and_less[i]==1){
                      System.out.print(" >= "+ objectivefunctionarray[i]+"\n");
                    }
                    else{
                      System.out.print(" = "+ objectivefunctionarray[i]+"\n");
                    }
                    
                }

                

                //----------------------------------------------------------------end check
            }});


        /////////////////////////////cancel buttun and action it ::--
        cancel = new JButton("CANCEL");
        cancel.setToolTipText("CANCEL");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        /////////////////////////////back buttun and action it ::--
        back = new JButton("BACK");
        back.setToolTipText("back");
        back.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

            }
        });



        ///////////////////////////////add to other :--
        add(largepanel);
        conpanel.add(matrix_con_array);
        largepanel.add(obfpanel);
        largepanel.add(conpanel);



        //////////////////add buttun to buttun panel::
      // buttunpanel.add(back);
        buttunpanel.add(cancel);
        buttunpanel.add(ok);
        largepanel.add(buttunpanel);

        this.pack();


    }
    public static void main(String[] args) {
        String numberOfVar= JOptionPane.showInputDialog(null,"insert number variable in objective function");
        String numberOfConstrain=JOptionPane.showInputDialog(null, "insert number of constrains ");
        Dualconvert ss = new Dualconvert(Integer.parseInt(numberOfVar),Integer.parseInt(numberOfConstrain) );
        ss.setVisible(true);


    }



}
