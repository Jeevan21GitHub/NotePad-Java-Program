import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class MyFrame extends JFrame implements ActionListener,ChangeListener{
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner spinner;
    JComboBox comboBox;
    JMenuBar menuBar;
    JButton button;
    JMenu file;
    JMenuItem open;
    JMenuItem save;
    JMenuItem exit;
    
    MyFrame(){
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(600,600);
        this.setLocationRelativeTo(null);

        // Menu section
        menuBar=new JMenuBar();
        file=new JMenu("File");
        
        open=new JMenuItem("Open");
        open.addActionListener(this);
        save=new JMenuItem("Save");
        save.addActionListener(this);
        exit=new JMenuItem("Exit");
        exit.addActionListener(this);



        // /menu
        String[] font=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        comboBox=new JComboBox(font);
        comboBox.setPreferredSize(new Dimension(70,30));
        comboBox.setSelectedItem("Arial");
        comboBox.addActionListener(this);

        button=new JButton("Color");
        button.setPreferredSize(new Dimension(70,30));
        button.addActionListener(this);
        button.setFocusable(false);

        spinner=new JSpinner();
        spinner.setValue(20);
        spinner.setPreferredSize(new Dimension(70,30));
        spinner.addChangeListener(this);

        textArea=new JTextArea();
        textArea.setPreferredSize(new Dimension(400,400));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont((new Font("Arial",Font.PLAIN,40)));

        scrollPane=new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(comboBox);
        this.add(spinner);
        this.add(button);
       
        
        file.add(open);
        file.add(save);
        file.add(exit);
        menuBar.add(file);
        this.add(scrollPane);
        this.setJMenuBar(menuBar);
       
        //this.add(textArea);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            JColorChooser colorChooser=new JColorChooser();
            Color color=colorChooser.showDialog(null, "Color Panel", Color.black);
            textArea.setForeground(color);
        }
        if(e.getSource()==comboBox){
            textArea.setFont(new Font((String)(comboBox.getSelectedItem()),Font.PLAIN,textArea.getFont().getSize()));
        }
        if(e.getSource()==open){
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(null);
            FileNameExtensionFilter filter=new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int response=fileChooser.showOpenDialog(null);
            if(response==JFileChooser.APPROVE_OPTION){
                File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner scanner=null;
                try{
                    scanner=new Scanner(file);
                    if(file.isFile()){
                        while(scanner.hasNextLine()){
                            String line=scanner.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                }
                catch(FileNotFoundException e1){
                    e1.printStackTrace();
                }
                finally{
                    scanner.close();
                }
            }
        }
        if(e.getSource()==save){
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response=fileChooser.showSaveDialog(null);
            if(response==JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter printWriter=null;
                file=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try{
                    printWriter=new PrintWriter(file);
                    printWriter.println(textArea.getText());
                }
                catch(FileNotFoundException ef){
                    ef.printStackTrace();
                }
                finally{
                    printWriter.close();
                }
            }
        }
        if(e.getSource()==exit){
            System.exit(0);
        }

    }
    public void stateChanged(ChangeEvent e){
        textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)spinner.getValue()));
    }
}

class NotePad{
    public static void main(String args[]){
        MyFrame myFrame=new MyFrame();
    }
}