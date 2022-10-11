import javax.swing.*;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener, WindowListener {

    private JFrame frame;
    private JPanel panel;
    private JButton submit_button;
    private JTextField business_entry;
    private JTextArea output_field;
    private JScrollPane scrollV;
    private JRadioButton review_check;
    private JComboBox<String> review_dropdown;
    private JRadioButton attributes_check;
    private ButtonGroup business_options = new ButtonGroup();
    private JButton record_button;
    private static String business_name;
    private static dbSetup db_info;
    private static Connection conn;
    private static Statement stmt;
    private static String output;
    private static boolean review_bool = false;
    private static boolean attributes_bool = false;
    private static String review_star_bound = "";
    private PrintWriter writer;


    public GUI() {
        frame = new JFrame();
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        frame.addWindowListener(this);

        /*************
         Column 0
        *************/
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Business Name:"),gbc);

        gbc.gridx++;
        business_entry = new JTextField(10);
        panel.add(business_entry, gbc);

        gbc.gridx++;
        submit_button = new JButton("Submit");
        panel.add(submit_button, gbc);
        submit_button.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        review_check = new JRadioButton("Reviews");
        review_check.addActionListener(this);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(review_check, gbc);

        gbc.gridx++;
        String[] review_opts = new String[] {"N\\A","5", "4+", "3+", "2+", "1+"};
        review_dropdown = new JComboBox<>(review_opts);
        review_dropdown.addActionListener(this);
        panel.add(review_dropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        attributes_check = new JRadioButton("Attributes");
        gbc.anchor = GridBagConstraints.WEST;
        attributes_check.addActionListener(this);
        panel.add(attributes_check, gbc);

        business_options.add(review_check);
        business_options.add(attributes_check);

        gbc.gridy++;
        panel.add(new JSeparator(), gbc);

        // gbc.gridy++;
        // general_label = new JLabel("General Category Search");
        // general_label.

        gbc.gridy++;
        gbc.gridwidth=4;    
        output_field = new JTextArea(30, 40);
        output_field.setEditable(false);
        scrollV = new JScrollPane (output_field);
        scrollV.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollV, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        record_button = new JButton("Record in a file");
        record_button.addActionListener(this);
        panel.add(record_button, gbc);
        

        Dimension d = new Dimension();
        d.height = 600;
        d.width = 1000;
        frame.add(panel);
        frame.setMinimumSize(d);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Yelp Database Dive");
        ImageIcon img = new ImageIcon("logo.png");
        frame.setIconImage(img.getImage());
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == submit_button) {
                business_name = business_entry.getText();
            
            stmt = conn.createStatement();
            output = "";
            
            
            if(review_bool == true){
                if(!review_star_bound.equals("")){
                    review_star_bound = review_star_bound.substring(0,1);
                }
                else{
                    review_star_bound = "0";
                }
                String query = "SELECT \"name\", \"Address\".\"city\", \"Address\".\"state\", \"Review\".\"stars\", \"Review\".\"text\" FROM \"Business\" NATURAL JOIN \"Review\" NATURAL JOIN \"Address\" WHERE \"name\" LIKE" 
                + "\'%" + business_name +"%\' AND \"Review\".\"stars\" >= " + review_star_bound;
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    output += result.getString("name");
                    output += ", ";
                    output += result.getString("city");
                    output += ", ";
                    output += result.getString("state");
                    output += ", ";
                    output += result.getString("stars");
                    output += ", ";
                    String t = result.getString("text");
                    t = t.replaceAll("\n","");
                    output += t;
                    output += "\n";
                }
                String output_header = "You have asked for " + business_name + " and selected Reviews for " + review_star_bound +"+" + "\n\nBusiness Name, User Name, City, State, Stars \n";
                output = output_header + output;
                output_field.setText(output);
                review_bool = false;
                business_options.clearSelection();
            }
            else if(attributes_bool==true){
                String query = "SELECT  \"name\", \"business_id\", \"Address\".\"city\", \"Address\".\"state\", \"Attributes\".\"GoodForKids\", \"Attributes\".\"BusinessAcceptsCreditCard\" FROM \"Business\" NATURAL JOIN \"Attributes\" NATURAL JOIN \"Address\" WHERE \"name\" LIKE" 
                + "\'%" + business_name +"%\'";
                //System.out.println(query);
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    output += result.getString("name");
                    output += ", ";
                    // output += result.getString("business_id");
                    // output += ", ";
                    output += result.getString("city");
                    output += ", ";
                    output += result.getString("state");
                    output += ", ";
                    String s = result.getString("GoodForKids");
                    if(s == null){
                        output+="No information";
                    }
                    else if(s.equals("t")){
                        output+= "True";
                    }
                    else if(s.equals("f")){
                        output+="False";
                    }
                    output += ", ";
                    String s2 = result.getString("BusinessAcceptsCreditCard");
                    if( s2 == null){
                        output+="No information";
                    }
                    else if(s2.equals("t")){
                        output+= "True";
                    }
                    else if(s2.equals("f")){
                        output+="False";
                    }
                    output += "\n";
                }
                String output_header = "You have asked for " + business_name + " and selected Attributes \n\nBusiness Name, City, State, Good for Kids, Accepts Credit Card \n";
                output = output_header + output;
                output_field.setText(output);
                attributes_bool = false;
                business_options.clearSelection();
            }
            else if(business_name != null){

                String query = "SELECT \"name\", \"business_id\", \"Address\".\"city\", \"Address\".\"state\" FROM \"Business\" NATURAL JOIN \"Address\" WHERE \"name\" LIKE" 
                + "\'%" + business_name +"%\'";
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    output += result.getString("name");
                    output += ", ";
                    // output += result.getString("business_id");
                    // output += ", ";
                    output += result.getString("city");
                    output += ", ";
                    output += result.getString("state");
                    output += "\n";
                }
                String output_header = "You have asked for " + business_name + "\n\nBusiness Name, City, State \n";
                output = output_header + output;
                output_field.setText(output);
            }

            else if (business_name == null){
                output_field.setText("Please enter a business name for this query");
            }
        }
            // System.out.println(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No results found");
            ex.printStackTrace();
        }


        try{
            if(e.getSource()==review_check){
                review_bool = true;
            }

        } catch (Exception ex1){
            JOptionPane.showMessageDialog(null, "Problems with radiobutton");
            ex1.printStackTrace();
        }


        try{
            if(e.getSource()==review_dropdown){
                String selectedStar = (String) review_dropdown.getSelectedItem();
 
                if (selectedStar.equals("1+")) {
                    review_star_bound = selectedStar;
                } else if (selectedStar.equals("2+")) {
                    review_star_bound = selectedStar;
                } else if (selectedStar.equals("3+")) {
                    review_star_bound = selectedStar;
                } else if (selectedStar.equals("4+")) {
                    review_star_bound = selectedStar;
                } else if (selectedStar.equals("5")) {
                    review_star_bound = selectedStar;
                }
                else if (selectedStar.equals("N\\A")) {
                    review_star_bound = "0";
                }
            }
        } catch (Exception ex2){
            JOptionPane.showMessageDialog(null, "Problems with dropdown");
            ex2.printStackTrace();
        }

        
        try{
            if(e.getSource()==attributes_check){
                attributes_bool = true;
            }

        } catch (Exception ex3){
            JOptionPane.showMessageDialog(null, "Problems with radiobutton");
            ex3.printStackTrace();
        }


        try{
            if(e.getSource()==record_button){
                writer = new PrintWriter("data.csv", "UTF-8");
                writer.println(output);
                writer.close();
            }
        } catch(Exception ex4){
            JOptionPane.showMessageDialog(null, "Problems with writing to file");
            ex4.printStackTrace();
        }
    }

    /************
    Close connection to database
    ***********/
    @Override
    public void windowClosing(WindowEvent e) {
        try {
                conn.close();
                JOptionPane.showMessageDialog(null,"Connection Closed.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
            }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }
    public void windowDeactivated(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    }
    public static void main(String[] args) {
        db_info = new dbSetup();

        /***********
        Create connection to database
        **********/
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce_315_910_g9_db", db_info.user, db_info.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        new GUI();
    }
}

