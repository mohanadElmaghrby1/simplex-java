package simplex.z;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

public class SetEquations extends SimplexDesign {

    private String constrainText[]; // constrains
    private String max; // max equathion 
    private char horizintal[], vertical[];//first row and column in table
    private float table[][], finalTable[][];
    private int counter = 0, serCounter = 0; // to know the number of variable i added to constrains
    private int numberOfBasicVar; // number of basic variable 
    private int helperToPlace = 0;
    private char slack = 'i';
    private char ser = 'p';
    private float finalVal[];

    public SetEquations() {
        super();

        actionOk();

    }

    /**
     * action OK button
     */
    public void actionOk() {
        action = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               try{ setConstrains();
               }
               catch(Exception d){
                   JOptionPane.showMessageDialog(null, "please check your input", "check please", WARNING_MESSAGE);
               }
                dislay();
            }
        };
        ok.addActionListener(action);

    }

    /**
     * get constrains from text field and assign it to String constrainText
     */
    private void setConstrains() {
        constrainText = new String[constrains.length];
        this.max = maxField.getText();

        for (int i = 0; i < constrains.length; i++) {
            constrainText[i] = new String();

            try {
                constrainText[i] = setOperators(constrains[i].getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "check please", WARNING_MESSAGE);
            }

        }
        numberOfBasicConst();
        displayafter();
        setHorzintalAndVertical();
        displyHV();
        setInitialTable();
        displyInitialTable();

        constructTables();
        displyFinalVal();

//        displyInitialTable();
    }

    /**
     *
     * @param ST constrain to edit
     * @return constrains after edit
     */
    private String setOperators(String ST) throws Exception {

        if (ST.contains(">")) {
            throw new Exception("check input please and try again");
//            ST = ST.replace(">", "+1"+ slack+"-1" + ser);
//            //ST = ST.replace(">", "");
//            counter++;
//            serCounter++;
//            ser++;
//            slack++;

        } else if (ST.contains("<")) {
            ST = ST.replace("<", "+1" + slack);
            // ST = ST.replace("<", "");
            slack++;
            counter++;
        } else {
            ST = ST.replace("=", "+1" + slack + "=");

            counter++;
            slack++;
        }
        return ST;

    }

    /**
     * display constrains after edit in console to debug
     */
    private void dislay() {
        for (String constrainText1 : constrainText) {
            System.out.println(constrainText1);
        }
    }

    private void numberOfBasicConst() {
        String arr[] = max.split("[a-h]+");
        this.numberOfBasicVar = arr.length;

    }

    /**
     * set first column and row in the table with basic variable , serPlus ,
     * artificial VAR = [a-h] art&slack =[i-o] ser [p-y]
     */
    private void setHorzintalAndVertical() {
        horizintal = new char[numberOfBasicVar + 1 + serCounter + counter + 2];
        vertical = new char[1 + counter];

        horizintal[0] = vertical[0] = 'z';

        for (int i = 0; i < numberOfBasicVar; i++) {
            horizintal[i + 1] = (char) ('a' + i);
        }
        for (int i = 0; i < counter; i++) {
            horizintal[i + 1 + numberOfBasicVar] = (char) ('i' + i);
            vertical[i + 1] = (char) ('i' + i);
        }
        for (int i = 0; i < serCounter; i++) {
            horizintal[i + 1 + numberOfBasicVar + counter] = (char) ('p' + i);
        }
        horizintal[horizintal.length - 2] = '!'; //! be 
        horizintal[horizintal.length - 1] = '@'; // seta

    }

    private void setInitialTable() {

      
        int m=1;
        if (maxCh.isSelected())
            m=-1;
        table = new float[vertical.length][horizintal.length];
        setIdentityMatrix();
        // set seta and Be to be zero in z row 
        table[0][table[0].length - 1] = 0;
        table[0][table[0].length - 2] = 0;

        //set value of max to table in z row
        //after this  z row now completed  
        
           
        System.out.println("==============");
        String values[] = getMAxVal();
        for (int i = 1; i <= numberOfBasicVar; i++) {
            values[i - 1] = values[i - 1].replace("[ ]+", ""); // to delete spaces 
            table[0][i] = (Integer.parseInt(values[i - 1])) * m;
        }

        // after this intial table completed 
        for (int i = 0; i < constrainText.length; i++) {
            try {
                int arr[] = getConstrainVal(constrainText[i]);
                for (int j = 0; j < horizintal.length - 2; j++) {
                    table[i + 1][j + 1] = arr[j];
                }
                table[i + 1][table[0].length - 2] = arr[arr.length - 1];// set be in all rows 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "please check your input", "check please", WARNING_MESSAGE);
            }

        }
        copy();
        setCeta();
        copy();
    }

    /**
     * cV : vertical char cH : horzintial char
     */
    private void setIdentityMatrix() {
        for (int i = 0; i < vertical.length; i++) {
            for (int j = i; j < horizintal.length - 2; j++) { //-2 for seta and be 
                if (vertical[i] == horizintal[j]) {

                    for (int v = 0; v < vertical.length; v++) {
                        table[v][j] = 0;
                    }
                    table[i][j] = 1; // set identity element 
                }
            }
        }

    }

    /**
     * delete letters and return numbers as array of String
     *
     * @return value of basic variable in max z equation
     */
    private String[] getMAxVal() {

        String array[] = max.split("[+||-]");
        try {

            for (int i = 0; i < array.length; i++) {
                array[i] = array[i].replaceAll("[a-h]+", "");

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "please check your input", "check please", WARNING_MESSAGE);
        }
        return array;

    }

    /**
     *
     * @return value of basic variable in S.T 3a+2b=5 return [3,2,5]
     */
    private int[] getConstrainVal(String st) throws Exception {

        int arrayOfVal[] = new int[st.split("[a-z]").length]; // -1 for z value in table
        int indexOfEqual = st.indexOf("=");
        String f = st.substring(indexOfEqual + 1);
        if (Integer.parseInt(f)<0)
            throw new Exception("please check your input");
        arrayOfVal[arrayOfVal.length - 1] = Integer.parseInt(f);
        //arrayOfVal=[ , , 5 ]

        //  3a+2b=5 >>>> 
        st = st.substring(0, indexOfEqual);
        // 3a+2b >>>>>>>

        String a[] = st.replaceAll("[0-9]+", "").split("[+-]");

        String arr[] = st.split("[+]");
        //[3a , 2b]
        int v = 0;
        for (int i = 0; i < arrayOfVal.length - 1 && v < arr.length; i++) { // -1 for be
            arr[v] = arr[v].replaceAll("[a-z ]+", "");

            boolean flag;
            if (!arr[v].contains("-")) {
                arrayOfVal[i] = Integer.parseInt(arr[v]);
                v++;
            } else {
                //0-1    -1-2
                int m = 0;
                String newArr[];
                if (arr[v].indexOf("-") != 0) {
                    newArr = arr[v].split("-");
                    arrayOfVal[i] = Integer.parseInt(newArr[0]);
                    i++;
                    m++;
                } else {
                    newArr = arr[v].split("-");
                }

                //[0,1]   [-1,-2]
                for (int j = m; j < newArr.length; j++) {
                    if ("".equals(newArr[j])) {
                        continue;
                    }
                    newArr[j] = newArr[j].replaceAll("[a-z ]+", "");
                    arrayOfVal[i] = Integer.parseInt(newArr[j]) * -1;
                    if (j + 1 < newArr.length) {
                        i++;
                    }
                }
                v++;

            }

        }
        //arryOfVal [3 , 2 , 5]
        int[] container = new int[horizintal.length - 2];

        container[container.length - 1] = arrayOfVal[arrayOfVal.length - 1];

        for (int i = 0, t = 0; i < horizintal.length - 3; i++) {
            boolean flag = false;
            for (int d = 0; d < a.length; d++) {
                String s = Character.toString(horizintal[i + 1]);
                if (s.equals(a[d])) {
                    container[i] = arrayOfVal[t];
                    t++;
                    flag = true;
                    d = a.length;
                }
            }
            if (!flag) {
                container[i] = 0;
            }
        }

        return container;

    }

    /**
     * setCeta by divide b / smallest index in z row
     */
    private void setCeta() {

        int small = smallestElementInZRow(table);
        for (int i = 0; i < vertical.length; i++) {
            try {
                table[i][table[0].length - 1] = (table[i][table[0].length - 2]) / (table[i][small]);
                if (table[i][table[0].length - 1] < 0) {
                    table[i][table[0].length - 1] = 0;
                }
            } catch (Exception e) {
                table[i][table[0].length - 1] = 0;
            }
        }

    }

    /**
     * find the smaller element in z row
     *
     * @return smaller element index in z row
     */
    private int smallestElementInZRow(float arr[][]) {
        int min = 0;

        for (int i = 0; i < arr[0].length; i++) {
            if (arr[0][i] < arr[0][min]) {
                min = i;
            }
        }

        return min;
    }

    /**
     * find the smallest element in Ceta
     *
     * @return the index of the smaller element
     */
    private int smallestElementInCetaColumn() {
        int min = 0;
        int index = (finalTable[0].length - 1);
        while (finalTable[min][index] == 0 && min < finalTable.length) {
            min++;
        }

        for (int i = min; i < table.length; i++) {
            if (finalTable[i][index] < finalTable[min][index] && finalTable[i][index] != 0) {
                min = i;
            }
        }
        return min;
    }

    /**
     * check z if negative or no
     *
     * @return true if z is still negative
     */
    private boolean zIsMinace() {
        for (int i = 0; i < finalTable[0].length; i++) {
            if (finalTable[0][i] < 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * construct new tables with the exchange and divide and all
     */
    private void constructTables() {

        while (zIsMinace()) {

            int smaalceta = smallestElementInCetaColumn();
            int smallz = smallestElementInZRow(finalTable);
            makeTableZero();
            // interchange vertical with horizintal 
            interchange(smaalceta, smallz);
            divideRowWithInterchangeElement(smaalceta, smallz);
            completeTable();
            setIdentityMatrix();

            finalVal = new float[vertical.length];
            finalVal[0] = table[0][table[0].length - 2];
            for (int i = 1; i < vertical.length; i++) {
                finalVal[i] = finalTable[i][finalTable[0].length - 1];
            }
            copy();
            if (zIsMinace()) {
                setCeta();
            }
            copy();
            //==================
            displyInitialTable();

        }
        displyHV();

    }

    /**
     * *
     * complete initial table cross X
     */
    private void completeTable() {

        int smallInCeta = smallestElementInCetaColumn();
        int smallInZ = smallestElementInZRow(finalTable);
        for (int i = 0; i < vertical.length; i++) {
            for (int j = 0; j < horizintal.length - 1; j++) {
                if (i == smallInCeta) {
                    continue;
                }
                double first = finalTable[i][j] * finalTable[smallInCeta][smallInZ];
                double second = finalTable[smallInCeta][j] * finalTable[i][smallInZ];
                double sub = first - second;
                double interchange = finalTable[smallInCeta][smallInZ];
                double divid = sub / interchange;
                table[i][j] = (float) divid;
//                table[i][j] = (finalTable[i][j] * finalTable[smallInCeta][smallInZ]
//                        - finalTable[smallInCeta][j] * finalTable[i][smallInZ]) / finalTable[smallInCeta][smallInZ];
            }
        }

    }

    /**
     *
     * @param index1 index of interchange element in vertical
     * @param index2 index of interchange element in horizintal
     */
    private void interchange(int index1, int index2) {
        vertical[index1] = horizintal[index2];
    }

    /**
     * divide all row of interchange element with interchange element
     *
     * @param row row index of interchange element
     * @param column index of interchange element
     */
    private void divideRowWithInterchangeElement(int row, int column) {

        for (int i = 0; i < horizintal.length - 1; i++) {
            table[row][i] = (finalTable[row][i] / finalTable[row][column]);
        }

    }

    /**
     * copy table to final table
     */
    private void copy() {
        finalTable = new float[table.length][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                finalTable[i][j] = table[i][j];
            }
        }

    }

    /**
     * assign all value of table to zero
     */
    private void makeTableZero() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = 0;
            }
        }
    }

    private void displayafter() {
        System.out.println("number of basic var = " + numberOfBasicVar);
        System.out.println("ser counter = " + serCounter);
        System.out.println("counter = " + counter);

    }

    private void displyHV() {
        for (char h : horizintal) {
            System.out.print(" " + h);
        }
        for (char v : vertical) {
            System.out.print(" * " + v);
        }
    }

    private void displyZ() {
        for (int i = 0; i < horizintal.length; i++) {
            System.out.println("\nz = ");
            System.out.print(" " + table[0][i]);
        }
    }

    private void displyInitialTable() {
        System.out.println("");
        for (float a[] : finalTable) {
            for (int i = 0; i < a.length; i++) {
                System.out.print(a[i] + " ");
            }
            System.out.println("");
        }
        System.out.println("*******************");
    }

    private void displyFinalVal() {

        for (int i = 0; i < finalVal.length; i++) {
            if ((vertical[i] >= 'a' && vertical[i] <= 'h') || vertical[i] == 'z') {
                System.out.println(vertical[i] + "= " + finalVal[i]);
            }
        }
    }

}
