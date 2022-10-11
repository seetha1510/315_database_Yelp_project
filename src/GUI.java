import javax.swing.*;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;


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

    private JTextField q2_text_field;
    private JButton q2_check;
    private static boolean q2_bool = false;
    private static String q2_user_name;
    private static String q2_user_id ="";

    private JTextField q3_state_input;
    private JButton q3_submit;

    private JTextField q4_city_entry;
    private JTextField q4_state_entry;
    private JButton q4_submit_button;

    private JTextField q5_city_entry;
    private JButton q5_submit_button;

    private JTextField q6_city_entry;
    private JTextField q6_user_entry;
    private JButton q6_submit_button;


    // private JTextField q1_bus1_entry;
    // private JTextField q1_bus2_entry;
    // private JButton q1_submit_button;
    // to declare private variables for the query questions, do q1_state or q2_city. 
    private JTextField q1_res1_field;
    private JTextField q1_city1_field;
    private JTextField q1_state1_field;
    private JTextField q1_res2_field;
    private JTextField q1_city2_field;
    private JTextField q1_state2_field;
    private JButton q1_submit;


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

        // gbc.gridy++;
        // panel.add(new JSeparator(), gbc);

        // gbc.gridy++;
        // general_label = new JLabel("General Category Search");
        // general_label.

        // gbc.gridy++;
        // panel.add(new JLabel("Question 1:"),gbc);

        // gbc.gridy++;
        // q1_bus1_entry = new JTextField(10);
        // panel.add(q1_bus1_entry, gbc);

        // gbc.gridx++;
        // q1_bus2_entry = new JTextField(10);
        // panel.add(q1_bus2_entry, gbc);

        // gbc.gridx++;
        // q1_submit_button = new JButton("Submit");
        // panel.add(q1_submit_button, gbc);
        // q1_submit_button.addActionListener(this);

        gbc.gridy++;
        panel.add(new JLabel("Question 1:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("Restaurant:"), gbc);
        gbc.gridx++;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx++;
        panel.add(new JLabel("State:"), gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        q1_res1_field = new JTextField(10);
        panel.add(q1_res1_field, gbc);
        gbc.gridx++;
        q1_city1_field = new JTextField(10);
        panel.add(q1_city1_field, gbc);
        gbc.gridx++;
        q1_state1_field = new JTextField(10);
        panel.add(q1_state1_field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        q1_res2_field = new JTextField(10);
        panel.add(q1_res2_field, gbc);
        gbc.gridx++;
        q1_city2_field = new JTextField(10);
        panel.add(q1_city2_field, gbc);
        gbc.gridx++;
        q1_state2_field = new JTextField(10);
        panel.add(q1_state2_field, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        q1_submit = new JButton("Submit");
        panel.add(q1_submit, gbc);
        q1_submit.addActionListener(this);

        gbc.gridx=0;
        gbc.gridy++;
        panel.add(new JLabel("Question 2:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("User ID:"), gbc);
        gbc.gridx++;
        q2_text_field = new JTextField(10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(q2_text_field, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        q2_check = new JButton("Submit");
        panel.add(q2_check, gbc);
        q2_check.addActionListener(this);

        gbc.gridx=0;
        gbc.gridy++;
        panel.add(new JLabel("Question 3:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("State:"),gbc);
        gbc.gridx++;
        q3_state_input = new JTextField(10);
        panel.add(q3_state_input, gbc);
        gbc.gridx++;
        q3_submit = new JButton("Submit");
        panel.add(q3_submit, gbc);
        q3_submit.addActionListener(this);
        gbc.gridx = 0;

        gbc.gridy++;
        panel.add(new JLabel("Question 4:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("City:"),gbc);
        gbc.gridx++;
        q4_city_entry = new JTextField(10);
        panel.add(q4_city_entry, gbc);
        gbc.gridx++;
        q4_submit_button = new JButton("Submit");
        panel.add(q4_submit_button, gbc);
        q4_submit_button.addActionListener(this);
        gbc.gridy++;
        gbc.gridx =0;
        panel.add(new JLabel("State:"),gbc);
        gbc.gridx++;
        //gbc.gridx++;
        q4_state_entry = new JTextField(10);
        panel.add(q4_state_entry, gbc);

        

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Question 5:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("City:"),gbc);
        gbc.gridx++;
        q5_city_entry = new JTextField(10);
        panel.add(q5_city_entry, gbc);
        gbc.gridx++;
        q5_submit_button = new JButton("Submit");
        panel.add(q5_submit_button, gbc);
        q5_submit_button.addActionListener(this);


        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Question Bonus:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("City:"),gbc);
        gbc.gridx++;
        q6_city_entry = new JTextField(10);
        panel.add(q6_city_entry, gbc);
        gbc.gridx++;
        q6_submit_button = new JButton("Submit");
        panel.add(q6_submit_button, gbc);
        q6_submit_button.addActionListener(this);
        gbc.gridx =0;
        gbc.gridy++;
        panel.add(new JLabel("User:"),gbc);
        gbc.gridx++;
        q6_user_entry = new JTextField(10);
        panel.add(q6_user_entry, gbc);


        gbc.gridx =10;
        gbc.gridy=0;
        gbc.gridwidth=4;  
        gbc.gridheight=24;  
        output_field = new JTextArea(30,40);
        output_field.setEditable(false);
        scrollV = new JScrollPane (output_field);
        scrollV.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollV, gbc);


        gbc.gridx = 10;
        gbc.gridy = 26;
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
        
        // question 1 try. 
        try{
            if (e.getSource()==q1_submit){
                String res1;
                String res2;
                String city1;
                String city2;
                String state1;
                String state2;
                String res1_final="";
                String res2_final="";

                res1 = q1_res1_field.getText();
                city1 = q1_city1_field.getText();
                state1 = q1_state1_field.getText();
                res2 = q1_res2_field.getText();
                city2 = q1_city2_field.getText();
                state2 = q1_state2_field.getText();

                

                for(int i = 0; i < res1.length(); i++)
                {
                    if(res1.charAt(i) == '\'') {
                        res1_final += "''";
                    }
                    else {
                        res1_final += res1.charAt(i);
                    }
                }

                for(int i = 0; i < res2.length(); i++)
                {
                    if(res2.charAt(i) == '\'') {
                        res2_final += "''";
                    }
                    else {
                        res2_final += res2.charAt(i);
                    }
                }
                String q1_bus_1 ="";
                String q1_bus_2="";

                String q1_bus1 = "SELECT business_id FROM \"Restaurants\" WHERE name = '" + res1_final + "' AND city = '" + city1 + "' AND state ='" + state1 + "' LIMIT 1";
                String q1_bus2 = "SELECT business_id FROM \"Restaurants\" WHERE name = '" + res2_final + "' AND city = '" + city2 + "' AND state ='" + state2 + "' LIMIT 1";
                Statement sm1 = conn.createStatement();
                ResultSet r = sm1.executeQuery(q1_bus1);
                if(r.next()){
                    q1_bus_1 = r.getString("business_id");
                }
                r = sm1.executeQuery(q1_bus2);
                if(r.next()){
                    q1_bus_2 = r.getString("business_id");
                }


                String output ="";
                stmt = conn.createStatement();
                Statement stmt__2 = conn.createStatement();
                // String q1_bus_1 = q1_bus1_entry.getText();
                // String q1_bus_2 = q1_bus2_entry.getText();
                String q_ncu1 = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id ='" + q1_bus_1 +"'";
                String q_ncu2 = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id ='" + q1_bus_2 +"'";
                //System.out.println(q_ncu1);
                ResultSet resl1 = stmt.executeQuery(q_ncu1);
                ResultSet resl2 = stmt__2.executeQuery(q_ncu2);
                if(!resl1.next() || !resl2.next()){
                    //System.out.println("helo");
                    output+="No chain possible";
                    output_field.setText(output);
                }
                else{
                    //System.out.println("helllo test");
                    int depth = 1;
                    //String query = "SELECT user_id FROM \"common_user\" WHERE business_id = '"+ q1_bus_1 +"' AND business_id_2 = '" + q1_bus_2 + "' LIMIT 1";
                    String query = "SELECT business_id FROM \"Temp_Relations\" WHERE user_id IN (SELECT user_id FROM \"Temp_Relations\" WHERE business_id = '" + q1_bus_1 +"') AND business_id = '" + q1_bus_2 +"'";
                    String query_inside = "SELECT business_id FROM \"Temp_Relations\" WHERE user_id IN (SELECT user_id FROM \"Temp_Relations\" WHERE business_id = '" + q1_bus_1 +"')" ; 
                    String query2_part1 = "SELECT business_id FROM \"Temp_Relations\" WHERE user_id IN (SELECT user_id FROM \"Temp_Relations\" WHERE business_id IN(";
                    String query2_part2 = ")) AND business_id = '" + q1_bus_2 +"'";
                    ResultSet result_r1 = stmt.executeQuery(query);
                    //System.out.println("nice1");
                    if(!result_r1.next()){
                        //output+="no user on r1";
                        //System.out.println("nice3");
                        while(!result_r1.next()){
                            String query2 = query2_part1 + query_inside + query2_part2;
                            //stmt2 = conn.createStatement();
                            System.out.println(query2);
                            result_r1 = stmt.executeQuery(query2);
                            depth += 1;
                            query_inside = query2_part1 + query_inside + "))";
                        }
                        // output += "Depth is: ";
                        // output+= depth;
                        // output_field.setText(output);

                        String base_start = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id = '" + q1_bus_1 + "'";
                        String bus_add = "SELECT business_id FROM \"Temp_Relations\" WHERE user_id IN (";
                        String user_add = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id IN (";
                        String base_end = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id = '" + q1_bus_2 + "' AND user_id IN (";
                        int reverse_depth = (depth * 2 ) -1 ;
                        //String query_in = base_start;
                        //System.out.println("Reverse_depht " + reverse_depth + "\nDepth "+ depth);
                        String query_in = "";
                        String query3 = "";
                        Vector<Vector<String>> list = new Vector<Vector<String>>(reverse_depth);
                        //System.out.println("nice2");
                        for (int i =0; i<reverse_depth; i++){
                            query_in = "";
                            for(int j=0; j<reverse_depth-i; j++){
                                if((j % 2) ==1){
                                    query_in = bus_add + query_in + ")";
                                }
                                else if (j != 0){
                                    query_in = user_add + query_in + ")";
                                }
                                else {
                                    query_in = base_start;
                                }
                            }

                            if((i % 2) ==1){
                                //System.out.println("Hello");
                                //System.out.println(i);
                                query3 = bus_add + query3 + ")" + " AND business_id IN (" + query_in + ")";
                            }
                            else if (i != 0){
                                //System.out.println("Here");
                                //System.out.println(i);
                                query3 = user_add + query3 + ")" + " AND user_id IN (" + query_in + ")";
                            }
                            else {
                                //System.out.println("got here!");
                                query3 = base_end + query_in + ")";
                            }

                            //System.out.println(query3);
                            //System.out.println(query_in);
                            Statement stmt_rev = conn.createStatement();
                            ResultSet result_rev = stmt_rev.executeQuery(query3); 
                            Vector<String> vec = new Vector<String>();
                            list.add(vec);
                            while(result_rev.next()){
                                if((i%2) == 0){
                                    String v = "'" + result_rev.getString("user_id") + "'";
                                    vec.add(v);
                                }
                                else{
                                    String v = "'" + result_rev.getString("business_id") + "'";
                                    vec.add(v);
                                }
                            }
                            //System.out.println(i + " " + vec);
                        }
                        Vector<String> final_chain = new Vector<String>();
                        final_chain.add(q1_bus_1);
                        final_chain.add((list.get(reverse_depth-1)).get(0));
                        //System.out.println((list.get(0)).size());
                        query_in ="";
                        for(int k=1; k<reverse_depth; k++){
                            for(int l=0; l<k+1; l++){
                                if((l % 2) ==1){
                                    query_in = bus_add + query_in + ")";
                                }
                                else if (l != 0){
                                    query_in = user_add + query_in + ")";
                                }
                                else {
                                    query_in = base_start;
                                }
                            }
                            String query_f="";
                            if ((k %2) ==0) {
                                String w = (list.get(reverse_depth-1-k)).toString();
                                w = w.substring(1, w.length()-1);
                                query_f = "SELECT user_id FROM \"Temp_Relations\" WHERE business_id =" +final_chain.get(k) + " AND user_id IN (" +  w + ") LIMIT 1";
                            }
                            else{
                                String w = (list.get(reverse_depth-1-k)).toString();
                                w = w.substring(1, w.length()-1);
                                query_f =  "SELECT business_id FROM \"Temp_Relations\" WHERE user_id =" + final_chain.get(k) + " AND business_id IN (" +  w + ") LIMIT 1";
                            }
                            Statement stmt_f = conn.createStatement();
                            //System.out.println("\nHeoj");
                            //System.out.println(k);
                            //System.out.println(query_f);
                            ResultSet result_f = stmt_f.executeQuery(query_f); 
                            
                            
                            if(result_f.next()){
                                if((k%2) == 0){
                                    String j2 = "'" + result_f.getString("user_id") + "'";
                                    final_chain.add(j2);
                                    //System.out.println("user: "+ j2);
                                }
                                else{
                                    String j2 = "'" + result_f.getString("business_id") + "'";
                                    final_chain.add(j2);
                                    //System.out.println("business: "+ j2);
                                }
                            }
                        }
                        String j1 = "'" + q1_bus_2 + "'";
                        final_chain.add(j1);
                        output+="\n";
                        output+="Chain is: ";
                        Statement stmt_c = conn.createStatement();
                        for(int m=0; m<final_chain.size(); m++){
                            if(m%2 == 0 ){
                                String bus_1_q =  "SELECT business_name FROM \"Restaurants\" WHERE business_id ='" + final_chain.get(m) + "'";
                                ResultSet r_c = stmt_c.executeQuery(bus_1_q);
                                if(r_c.next()){
                                    String bus_name = r_c.getString("business_name");
                                    output+= bus_name;
                                    output+=" -> \n";
                                }
                                
                            }
                            else{
                                String user_1_q =  "SELECT name FROM \"User_\" WHERE user_id ='" + final_chain.get(m) + "'";
                                ResultSet r_c = stmt_c.executeQuery(user_1_q);
                                if(r_c.next()){
                                    String user_name = r_c.getString("business_name");
                                    output+= user_name;
                                    output+=" -> \n";
                                }
                            }
                        }
                        output_field.setText(output);
                    }
                    else{
                        //System.out.println("niiiiiiiiice");
                        if(result_r1.next()){
                            //String q1_common_user = result_r1.getString("user_id");
                            String user_1_q1 =  "SELECT user_id FROM \"common_user\" WHERE business_id ='" + q1_bus_1 + "' AND business_id_2 ='" + q1_bus_2 + "'";
                            //System.out.println("ice");
                            Statement stmt_c1 = conn.createStatement();
                            ResultSet r_c1 = stmt_c1.executeQuery(user_1_q1);
                            //System.out.println("best");
                            if(r_c1.next()){
                                String user_name1 = r_c1.getString("user_id");
                                output+= user_name1;
                                output+=" -> \n";
                            }
                        }
                        output_field.setText(output);
                    }
                }
            }
        } catch(Exception ex5){
            JOptionPane.showMessageDialog(null, "Problems with opening database");
            ex5.printStackTrace();
        }


        /**********
         Check if source of button press was Q2 button
         **********/
        try {
            if (e.getSource() == q2_check) {

                stmt = conn.createStatement();
                // String q2_user_name = q2_text_field.getText();
                // String qu1 = "SELECT user_id FROM \"User_\" WHERE name = '" + q2_user_name + "' LIMIT 1";
                // ResultSet r_ = stmt.executeQuery(qu1);
                // if(r_.next()){
                //     q2_user_id =r_.getString("user_id");
                // }
                q2_user_id = q2_text_field.getText();
                //System.out.println("Q2: "+ q2_user_id);
                String user_name = "";
                int review_count = 0;
                String yelping_since = "";
                String output = "";
                String sample_review = "";
                String business_id = "";
                String business_name = "";
                int count = 0;
                int useful = 0;
                int funny = 0;
                int cool = 0;
                double stars = 0;


                String query = "SELECT \"name\", \"review_count\", \"yelping_since\" FROM \"User\" WHERE " +
                        "\"user_id\" = '" + q2_user_id + "'";

                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    user_name = result.getString("name");
                    review_count = result.getInt("review_count");
                    yelping_since = result.getString("yelping_since");
                }

                output += "User ID: " + q2_user_id + "\n";
                output += "User Name: " + user_name + "\n";
                output += "Yelping Since: " + yelping_since + "\n";
                output += "Review Count: " + review_count + "\n";

                if (review_count < 5) {
                    output += "This user has fewer than five reviews and therefore no summary statistics\n";
                } else {
                    query = "SELECT \"text\", \"business_id\" FROM \"Review\" WHERE \"user_id\" = '" + q2_user_id + "' LIMIT 1";
                    result = stmt.executeQuery(query);
                    while (result.next()) {
                        sample_review = result.getString("text");
                        business_id = result.getString("business_id");
                    }

                    query = "SELECT \"name\" FROM \"Business\" WHERE \"business_id\" = '" + business_id + "'";
                    result = stmt.executeQuery(query);
                    while (result.next()) {
                        business_name = result.getString("name");
                    }

                    output += "Sample Review for " + business_name + ": \n" + sample_review + "\n";

                    query = "SELECT \"stars\", \"useful\", \"funny\", \"cool\" FROM \"Review\" WHERE \"user_id\"  = '"
                            + q2_user_id + "'";
                    result = stmt.executeQuery(query);
                    while (result.next()) {
                        stars += result.getDouble("stars");
                        useful += result.getInt("useful");
                        funny += result.getInt("funny");
                        cool += result.getInt("cool");
                        count++;
                    }
                    double avg_stars = stars / count;
                    double avg_useful = (double)useful / count;
                    double avg_funny = (double)funny /count;
                    double avg_cool = (double)cool / count;

                    output += "Average Stars: " + avg_stars + "\n";
                    output += "Average Useful Comments: " + avg_useful + "\n";
                    output += "Average Funny Comments: " + avg_funny + "\n";
                    output += "Average Cool Comments: " + avg_cool + "\n";
                }

                output_field.setText(output);
            }
        } catch (Exception except) {
            JOptionPane.showMessageDialog(null, "Something went wrong in Q2");
            except.printStackTrace();
        }


        try{
            if(e.getSource()==q3_submit)
            {
                String state_name;
                ArrayList<String> business_name_array = new ArrayList<String>();
                ArrayList<Double> rating_array = new ArrayList<Double>();
                ArrayList<Double> lon_array = new ArrayList<Double>();
                ArrayList<Double> lat_array = new ArrayList<Double>();
                ArrayList<String> est_franchises = new ArrayList<String>();
                ArrayList<String> franchises_array = new ArrayList<String>();
                ArrayList<Double> fran_rating_array = new ArrayList<Double>();
                ArrayList<Double> fran_lon_array = new ArrayList<Double>();
                ArrayList<Double> fran_lat_array = new ArrayList<Double>();
                ArrayList<String> potential_names = new ArrayList<String>();
                ArrayList<Double> potential_distance = new ArrayList<Double>();
                ArrayList<String> top_5_names = new ArrayList<String>();
                ArrayList<Double> top_5_distance = new ArrayList<Double>();

                stmt = conn.createStatement();
                output = "";

                state_name = q3_state_input.getText();
                String query = "SELECT \"name\", \"stars\", \"Address\".\"longitude\", \"Address\".\"latitude\" FROM \"Restaurants\" NATURAL JOIN \"Address\" WHERE \"Address\".\"state\" LIKE " + "\'%" + state_name + "%\'";
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    business_name_array.add(result.getString("name"));
                    rating_array.add(Double.parseDouble(result.getString("stars")));
                    lon_array.add(Double.parseDouble(result.getString("longitude")));
                    lat_array.add(Double.parseDouble(result.getString("latitude")));
                }
                int curr_franchise = 0;
                boolean is_fran = false;
                boolean is_est = false;
                for(int i = 0; i < business_name_array.size(); i++)
                {
                    is_fran = false;
                    is_est = false;
                    int num_businesses = 0;
                    for(int j = 0; j < est_franchises.size(); j++)
                    {
                        if(business_name_array.get(i).equals(est_franchises.get(j)))
                        {
                            is_est = true;
                            break;
                        }
                    }
                    franchises_array.add(business_name_array.get(i));
                    fran_rating_array.add(rating_array.get(i));
                    fran_lon_array.add(lon_array.get(i));
                    fran_lat_array.add(lat_array.get(i));
                    if(!is_est)
                    {
                        for(int j = i + 1; j < business_name_array.size(); j++)
                        {
                            if(business_name_array.get(j).equals(business_name_array.get(i)))
                            {
                                if(!is_est)
                                {
                                    est_franchises.add(business_name_array.get(i));
                                }
                                is_est = true;
                                is_fran = true;
                                franchises_array.add(business_name_array.get(j));
                                fran_rating_array.add(rating_array.get(j));
                                fran_lon_array.add(lon_array.get(j));
                                fran_lat_array.add(lat_array.get(j));
                            }
                        }
                        Double sum = 0.0;
                        Double avg_rating = 0.0;
                        for(int j = 0; j < fran_rating_array.size(); j++)
                        {
                            sum += fran_rating_array.get(j);
                        }
                        avg_rating = sum / fran_rating_array.size();
                        if(avg_rating >= 3.5 && franchises_array.size() > 1)
                        {
                            Double longest_distance = 0.0;
                            Double distance = 0.0;
                            for(int j = 0; j < franchises_array.size(); j++)
                            {
                                for(int k = j; k < franchises_array.size(); k++)
                                {
                                    distance = Math.sqrt(Math.pow(fran_lat_array.get(j) - fran_lat_array.get(k), 2) + Math.pow(fran_lon_array.get(j) - fran_lon_array.get(k), 2));
                                    if(distance > longest_distance)
                                    {
                                        longest_distance = distance;
                                    }
                                }
                            }
                            potential_names.add(franchises_array.get(0));
                            potential_distance.add(longest_distance);
                        }
                    }
                    franchises_array.clear();
                    fran_rating_array.clear();
                    fran_lon_array.clear();
                    fran_lat_array.clear();
                }

                output += "Restaurants with largest spread and at least a 3.5 average rating in " + state_name + ":\n\n";

                if(potential_names.size() >= 5)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        Double highest_distance = 0.0;
                        int index = 0;
                        for(int j = 0; j < potential_names.size(); j++)
                        {
                            if(potential_distance.get(j) > highest_distance)
                            {
                                highest_distance = potential_distance.get(j);
                                index = j;
                            }
                        }

                        output += potential_names.get(index) + "\n";

                        potential_names.remove(index);
                        potential_distance.remove(index);
                    }
                }
                else
                {
                    for(int i = 0; i < potential_names.size(); i++)
                    {
                        Double highest_distance = 0.0;
                        int index = 0;
                        for(int j = 0; j < potential_names.size(); j++)
                        {
                            if(potential_distance.get(j) > highest_distance)
                            {
                                highest_distance = potential_distance.get(j);
                                index = j;
                            }
                        }

                        output += potential_names.get(index) + "\n";

                        potential_names.remove(index);
                        potential_distance.remove(index);
                    }
                }
                
                output_field.setText(output);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No results found");
            ex.printStackTrace();
        }

        try{
            if(e.getSource()==q4_submit_button){
                String output ="";
                stmt = conn.createStatement();
                String q4_city = q4_city_entry.getText();
                String q4_state = q4_state_entry.getText();
                String checking_message = "Please wait. Looking for Restaurant with most tips in " + q4_city + ", " + q4_state + "...";
                output_field.setText(checking_message);
                if(q4_state.length() > 3 || q4_state.length()<2){
                    output_field.setText("Please enter 2/3 letter abbreviation of state.");
                }
                else{
                    String query1 = "CREATE OR REPLACE VIEW \"City_Restaurants\" AS SELECT business_id, name, city, state FROM \"NonFranchise_Restaurants\" WHERE city = '" + 
                    q4_city + "' AND state = '" + q4_state + "'";
                    //System.out.println(query1);
                    stmt.execute(query1);
                    String query2 = "SELECT business_id FROM \"City_Restaurants\"";
                    ResultSet result1 = stmt.executeQuery(query2);
                    long max_tips_num =0;
                    String max_tips_business_id = "";
                    while (result1.next()) {
                        Statement stmt2 = conn.createStatement();
                        String q4_business_id = result1.getString("business_id");
                        String query3 = "SELECT COUNT( * ) as \"Number_of_Tips\" FROM \"Tip\" WHERE business_id='"+ q4_business_id +"'";
                        ResultSet result2 = stmt2.executeQuery(query3);
                        if(result2.next()){
                            long num_tips = result2.getLong("Number_of_Tips");
                            if(num_tips>max_tips_num){
                                max_tips_business_id = q4_business_id;
                                max_tips_num=num_tips;
                            }
                        }
                    }
                    String query4 = "SELECT text FROM \"Tip\" WHERE business_id='" + max_tips_business_id+"' LIMIT 30";
                    ResultSet result3 = stmt.executeQuery(query4);
                    output="Non-franchise Restaurant in "+ q4_city+ " that has most number of tips: ";
                    String query5 = "SELECT name FROM \"City_Restaurants\" WHERE business_id='"+max_tips_business_id+"'";
                    Statement stmt3 =conn.createStatement(); 
                    ResultSet result4 = stmt3.executeQuery(query5); 
                    if(result4.next()){
                        output+= result4.getString("name");
                    }
                    output += "\nTips for this business:\n";
                    while(result3.next()){
                        output+=result3.getString("text");
                        output+="\n";
                    }
                    stmt.execute("DROP VIEW \"City_Restaurants\"");
                    output_field.setText(output);
                }
            }
        } catch(Exception ex9){
            JOptionPane.showMessageDialog(null, "Problems with accessing database");
            ex9.printStackTrace();
        }

        try{
            if(e.getSource()==q5_submit_button){
                String city_name = q5_city_entry.getText(); //gotta read this in on a box
                String output ="";
                Statement stmt = conn.createStatement();
                if(city_name == ""){
                    //can't do it, I don't know what to put here
                    output+="Please enter a city name for q5";
                }
                else{
                //Code is not right
                String q5_query = "SELECT \"name\", \"Hours\".\"hours.Monday\", \"Hours\".\"hours.Tuesday\", \"Hours\".\"hours.Wednesday\", \"Hours\".\"hours.Thrusday\", \"Hours\".\"hours.Friday\", \"Hours\".\"hours.Saturday\", \"Hours\".\"hours.Sunday\","+ 
                " \"Checkin\".\"date\" FROM \"Restaurants\" NATURAL JOIN \"Hours\" NATURAL JOIN \"Checkin\" WHERE \"Restaurants\".\"city\" LIKE " + "\'%" + city_name + "%\'";
                ResultSet q5_result = stmt.executeQuery(q5_query);
            
                ArrayList<String> q5_restaurants = new ArrayList<String>();
                ArrayList<Integer> q5_interactions = new ArrayList<Integer>();
                while (q5_result.next()) {
                    ArrayList<String> q5_hours = new ArrayList<String>();
                    String q5_res = q5_result.getString("name");
                    q5_hours.add(q5_result.getString("hours.Monday"));
                    q5_hours.add(q5_result.getString("hours.Tuesday"));
                    q5_hours.add(q5_result.getString("hours.Wednesday"));
                    q5_hours.add(q5_result.getString("hours.Thrusday"));
                    q5_hours.add(q5_result.getString("hours.Friday"));
                    q5_hours.add(q5_result.getString("hours.Saturday"));
                    q5_hours.add(q5_result.getString("hours.Sunday"));
            
                    boolean q5_hourCheck = false;
                    ArrayList<String> q5_earlyHours = new ArrayList<String>();
                    ArrayList<String> q5_lateHours = new ArrayList<String>();
                    String temp ="";
                    for (int day = 0; day < 7; day++) {
                        // try{
                        //     temp = q5_hours.get(day);
                        // }
                        // catch (Exception e13){
                        //     temp="";
                        // }
                        temp = q5_hours.get(day);
                        if(temp == null){
                            temp ="";
                        }
                        //System.out.println("Hel: "+ temp);
                        if (temp.length() == 0){continue;}
                        boolean early = true;
                        boolean minutes = false;
                        String earlyTime = "";
                        String earlyTimeMin = "";
                        String lateTime = "";
                        String lateTimeMin = "";
                        for (int letter = 0; letter < (q5_hours.get(day)).length(); letter++) {
                            if (q5_hours.get(day).charAt(letter) == ':') {
                                minutes = true;
                            } else if (q5_hours.get(day).charAt(letter) == '-') {
                                early = false;
                                minutes = false;
                            } else if (early && !minutes) {
                                earlyTime += q5_hours.get(day).charAt(letter);
                            } else if (early && minutes) {
                                earlyTimeMin += q5_hours.get(day).charAt(letter);
                            } else if (!early && !minutes) {
                                lateTime += q5_hours.get(day).charAt(letter);
                            } else {
                                lateTimeMin += q5_hours.get(day).charAt(letter);
                            }
                        }
                        
                        float earlyTimeF = Float.parseFloat(earlyTime) + (Float.parseFloat(earlyTimeMin) / 100);
                        float lateTimeF = Float.parseFloat(lateTime) + (Float.parseFloat(lateTimeMin) / 100);
                        if (earlyTimeF <= 6.00 || lateTimeF >= 10.00) {
                            q5_hourCheck = true;
                        }
                        
                    }
            
                    String q5_intr = q5_result.getString("date");
                    if (q5_hourCheck) {
                        int q5_numInteractions = 1;
            
                        if (q5_intr == "") {
                            //skip it, 0 interactions aint no good
                        } else {
                            char q5_c[] = new char[q5_intr.length()];     
                            for (int i = 0; i < q5_intr.length(); i++) {  
                                q5_c[i] = q5_intr.charAt(i);  
                                if( q5_c[i] == ',' ) {
                                    q5_numInteractions += 1;  
                                }
                            } 
                            q5_restaurants.add(q5_res);
                            q5_interactions.add(q5_numInteractions);
                        
                        }
                    }
                }
            
                int q5_max1 = -1;
                int q5_max2 = -1;
                int q5_max3 = -1;
                for (int i = 0; i < q5_restaurants.size(); i++) {
                    if (i == 0) { //no maxes are set
                        q5_max1 = i;
                    } else if (i == 1) { //one max is set
                        if (q5_interactions.get(q5_max1) >= q5_interactions.get(i)) {
                            q5_max2 = i;
                        } else {
                            q5_max2 = q5_max1;
                            q5_max1 = i;
                        }
                    } else if (i == 2) { //two max is set
                        if (q5_interactions.get(q5_max1) < q5_interactions.get(i)) {
                            q5_max3 = q5_max2;
                            q5_max2 = q5_max1;
                            q5_max1 = i;
                        } else if (q5_interactions.get(q5_max2) < q5_interactions.get(i)) {
                            q5_max3 = q5_max2;
                            q5_max2 = i;
                        }
                    } else { //anything after
                        if (q5_interactions.get(q5_max1) < q5_interactions.get(i)) {
                            q5_max3 = q5_max2;
                            q5_max2 = q5_max1;
                            q5_max1 = i;
                        } else if (q5_interactions.get(q5_max2) < q5_interactions.get(i)) {
                            q5_max3 = q5_max2;
                            q5_max2 = i;
                        } else if (q5_interactions.get(q5_max3) < q5_interactions.get(i)) {
                            q5_max3 = i;
                        }
                    }
                    //System.out.println(q5_max1 + " " + q5_max2 + " " + q5_max3);
                }
                output += "\nRestaurant with most number of interaction that is also open early mornings or late nights:\n";
                output+=q5_restaurants.get(q5_max1);
                output += "\n\nRestaurant with second most number of interaction that is also open early mornings or late nights:\n";
                output+=q5_restaurants.get(q5_max2);
                output += "\n\nRestaurant with third most number of interaction that is also open early mornings or late nights:\n";
                output+=q5_restaurants.get(q5_max3);
                output_field.setText(output);
                }
            }
            
        } catch (Exception ex10){
            JOptionPane.showMessageDialog(null, "Problems with accessing database");
            ex10.printStackTrace();
        }
        try{
            if(e.getSource()==q6_submit_button){
                // code
                //String q6_user_name = q6_user_entry.getText();
                // String que1 = "SELECT user_id FROM \"User_\" WHERE name = '" + q6_user_name + "' LIMIT 1";
                // ResultSet r_1 = stmt.executeQuery(que1);
                // if(r_1.next()){
                //     user=r_.getString("user_id");
                // }
                output = "";
                String city = q6_city_entry.getText();
                String user = q6_user_entry.getText();
                String tempOutput = "";
                stmt =conn.createStatement();
                // query all friends for our user
                String view = "create or replace view temp_review as select \"business_id\", \"stars\", \"user_id\" from \"Restaurant_Review\" where \"city\" = '" + city + "' and \"stars\" >= 3";
                stmt.execute(view);
                String friends = "SELECT \"friends\" FROM \"User_\" WHERE \"user_id\" = '" + user + "'";

                ResultSet result1 = stmt.executeQuery(friends);
                while (result1.next()) {
                if (result1 == null) {
                    tempOutput += "No friends for given user";
                } else {
                        tempOutput += result1.getString("friends");
                    }
                }
                String[] elements = tempOutput.split(", ");
                List<String> friendResults = Arrays.asList(elements);
                ArrayList<String> friendResultsArrLst = new ArrayList<String>(friendResults);
                // for (String id1 : friendResultsArrLst) {
                //     System.out.println("Hel: ");
                //     System.out.println(id1);
                // }
                tempOutput = "";
                // search through friends list, adding business_id to new array
                for (String friends2 : friendResultsArrLst) {
                    String restaurant = "SELECT business_id FROM temp_review WHERE user_id = '" + friends2 + "'";
                    ResultSet result2 = stmt.executeQuery(restaurant);
                    // System.out.println("hello");
                    while (result2.next()) {
                        tempOutput += result2.getString("business_id");
                        tempOutput += ",";
                        // System.out.println(tempOutput);
                    }
                }
                // System.out.println("test1");
                String[] elements2 = tempOutput.split(","); //**********************
                List<String> restaurantResults = Arrays.asList(elements2);
                ArrayList<String> restaurantLst = new ArrayList<String>(restaurantResults);
                Map<String, Integer> resMap = new HashMap<String, Integer>();
                // create map to count the number of occurrences of a business in a friends list
                for (String i : restaurantLst) {
                    Integer j = resMap.get(i);
                    resMap.put(i, (j == null) ? 1 : j + 1);
                }
                /* DEBUG STATEMENT */
                // for (String id : resMap.keySet()) {
                //     System.out.println("Hel: ");
                //     System.out.println(id);
                // }
                /* END STATEMENT */
                // System.out.println("test4");
                Map.Entry<String, Integer> maxEntry = null;
                for (Map.Entry<String, Integer> busEntry : resMap.entrySet()) {
                    if (maxEntry == null || busEntry.getValue().compareTo(maxEntry.getValue()) > 0) {
                        maxEntry = busEntry;
                }
                }
                // determine business to be returned
                String finalRes = "SELECT \"Restaurants\".name FROM \"Restaurants\" WHERE \"business_id\" = " + maxEntry.getKey() + "LIMIT 1";
                ResultSet result3 = stmt.executeQuery(finalRes);
                output += "Restaurant with most friend recommendations in given city is for particular user is: \n";
                while (result3.next()) {
                    output += result3.getString("name");
                }
                output_field.setText(output);
                String dropview = "drop view temp_review";
                stmt.execute(dropview);
            }


        } catch(Exception ex11){
            JOptionPane.showMessageDialog(null, "Problems with accessing database");
            ex11.printStackTrace();
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

