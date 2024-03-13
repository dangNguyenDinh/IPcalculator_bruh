/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package run;


import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import src.MyIP;
/**
 *
 * @author dangVuDinh
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame1 = new JFrame("Calculator");
        JLabel label1 = new JLabel("IP");
        JTextField textf1 = new JTextField("");
        JLabel label2 = new JLabel("Prefix length");
        JTextField textf2 = new JTextField("");
        JLabel label3 = new JLabel("Subnet mask: ");
        JTextField textf3 = new JTextField("");
        JLabel label4 = new JLabel("Network address: ");
        JTextField textf4 = new JTextField("");
        JLabel label5 = new JLabel("Broad cast: ");
        JTextField textf5 = new JTextField("");
        JLabel label6 = new JLabel("First address: ");
        JTextField textf6 = new JTextField("");
        JLabel label7 = new JLabel("Last address: ");
        JTextField textf7 = new JTextField("");
        JButton btn1 = new JButton("Add this result to file");
        JButton btn2 = new JButton("Calculate");
        JButton btn3 = new JButton("Show all");
        
        
        //init
        File file1 = new File("src\\src\\savedIP.txt");
        try{
            file1.createNewFile();
        }catch(Exception e){
            System.out.println(e);
        }
        //reader
        FileInputStream inputStream = new FileInputStream(file1);
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //writer
        FileOutputStream outputStream = new FileOutputStream(file1, true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        //IP object
        MyIP ip1 = new MyIP();
        
        //btn1
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String IP = textf1.getText();
                String preLen = textf2.getText();
                if(IP.equals("") || preLen.equals("")){
                    JOptionPane.showMessageDialog(frame1, "Chưa điền hết input!");
                }else{
                    try{
                        Integer.parseInt(preLen);
                        ip1.setIp(IP, Integer.parseInt(preLen));
                        try {
                            String a1 = textf3.getText();
                            String a2 = textf4.getText();
                            String a3 = textf5.getText();
                            String a4 = textf6.getText();
                            String a5 = textf7.getText();
                            if(a1.equals("")){
                                JOptionPane.showMessageDialog(frame1, "Chưa tính toán!");
                            }else{
                                ip1.writeToFile(file1, bufferedWriter, a1, a2, a3, a4, a5);
                            }
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(frame1, "IP hoặc prefix length không hợp lệ!");
                        }
                        
                    }catch(Exception e1){
                        JOptionPane.showMessageDialog(frame1, "Prefix length sai định dạng!");
                    }
                }
            }
        });
 
        //btn2
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IP = textf1.getText();
                String preLen = textf2.getText();
                if(IP.equals("") || preLen.equals("")){
                    JOptionPane.showMessageDialog(frame1, "Chưa nhập hết input!");
                }else{
                    try{
                        Integer.parseInt(preLen);
                        try {
                            ip1.setIp(IP, Integer.parseInt(preLen));
                            textf3.setText(ip1.calculateSubnetMask());
                            textf4.setText(ip1.calculateNetworkAddress());
                            textf5.setText(ip1.calculateBroadcast());
                            textf6.setText(ip1.calculateFirstAddress());
                            textf7.setText(ip1.calculateLastAddress());
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(frame1, "IP hoặc prefix length không hợp lệ!");
                        }
                        
                    }catch(Exception e1){
                        JOptionPane.showMessageDialog(frame1, "Prefix length sai định dạng!");
                    }
                    
                }
                
             }
        });
        
        //btn3
        btn3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 // Lấy tọa độ của frame1
                int x = frame1.getX();
                int y = frame1.getY();

                // Tạo JFrame 2 và đặt tọa độ dựa trên frame1
                JFrame frame2 = new JFrame("IP showing");
                frame2.setSize(300, 200);
                frame2.setLocation(x + 50, y + 50); // frame2 nằm cách frame1 một khoảng 50 điểm theo cả hai hướng
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame2.setVisible(true);
                //textArea hiển thị IP trong file
                JTextArea jta = new JTextArea();
                jta.setEditable(false);
                // Tạo JScrollPane để JTextArea có thể cuộn
                JScrollPane scrollPane = new JScrollPane(jta);
                //read from file
                String line;
                
                try {
                    inputStream.getChannel().position(0);
                    while ((line = bufferedReader.readLine()) != null) {
                        // Thêm dữ liệu từ tệp vào JTextArea
                        jta.append(line + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println("Read Error");
                }
                frame2.add(scrollPane);
                
                
            }
            
        });
        
        //label1
        label1.setBounds(15, 10, 100, 20);// X, Y, width, height
        label2.setBounds(275, 10, 100, 20);// X, Y, width, height
        label3.setBounds(25, 80, 150, 20);// X, Y, width, height
        label4.setBounds(25, 110, 150, 20);// X, Y, width, height
        label5.setBounds(25, 140, 150, 20);// X, Y, width, height
        label6.setBounds(25, 170, 150, 20);// X, Y, width, height
        label7.setBounds(25, 200, 150, 20);// X, Y, width, height
        
        //textf1
        textf1.setBounds(10, 30, 200, 20);
        textf2.setBounds(270, 30, 100, 20);
        textf3.setBounds(150, 80, 200, 20);
        textf3.setEditable(false);
        textf4.setBounds(150, 110, 200, 20);
        textf4.setEditable(false);
        textf5.setBounds(150, 140, 200, 20);
        textf5.setEditable(false);
        textf6.setBounds(150, 170, 200, 20);
        textf6.setEditable(false);
        textf7.setBounds(150, 200, 200, 20);
        textf7.setEditable(false);
        //btn
        btn1.setBounds(10, 240, 160, 18);
        btn2.setBounds(180, 240, 90, 18);
        btn3.setBounds(280, 240, 90, 18);
        
        //frame1
        frame1.add(label1);
        frame1.add(label2);
        frame1.add(label3);
        frame1.add(label4);
        frame1.add(label5);
        frame1.add(label6);
        frame1.add(label7);
        frame1.setBackground(Color.BLUE);
        frame1.setSize(400, 310);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(null);
        frame1.setVisible(true);
        frame1.add(textf1);
        frame1.add(textf2);
        frame1.add(textf3);
        frame1.add(textf4);
        frame1.add(textf5);
        frame1.add(textf6);
        frame1.add(textf7);
        frame1.add(btn1);
        frame1.add(btn2);
        frame1.add(btn3);
        frame1.setResizable(false); // Ngăn chặn phóng to frame

    }
}
