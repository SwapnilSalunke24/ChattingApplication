package com.ChattingApplication;

import javax.swing.*; //swing package import
import javax.swing.border.EmptyBorder;
import java.awt.*; //awt package for color
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Calendar; //import Calender
import java.net.*;


public class Server implements ActionListener {
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame(); //frame object
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0,450,70);
        p1.setLayout(null);
        f.add(p1);

        //back image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/icons/3.png")); //load image
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT); //set scale size
        ImageIcon i3 = new ImageIcon(i2); //load scale image
        JLabel back = new JLabel(i3); //add scale image to label
        back.setBounds(5,20,25,25);
        p1.add(back); //add

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/icons/server.png")); //load image
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT); //set scale size
        ImageIcon i6 = new ImageIcon(i5); //load scale image
        JLabel profile = new JLabel(i6); //add scale image to label
        profile.setBounds(40,10,50,50);
        p1.add(profile); //add

        //video image
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("com/icons/video.png")); //load image
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT); //set scale size
        ImageIcon i9 = new ImageIcon(i8); //load scale image
        JLabel video = new JLabel(i9); //add scale image to label
        video.setBounds(300,20,30,30);
        p1.add(video); //add

        //phone image
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("com/icons/phone.png")); //load image
        Image i11 = i10.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT); //set scale size
        ImageIcon i12 = new ImageIcon(i11); //load scale image
        JLabel phone = new JLabel(i12); //add scale image to label
        phone.setBounds(360,20,35,30);
        p1.add(phone); //add

        //option image
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("com/icons/3icon.png")); //load image
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT); //set scale size
        ImageIcon i15 = new ImageIcon(i14); //load scale image
        JLabel options = new JLabel(i15); //add scale image to label
        options.setBounds(420,20,10,25);
        p1.add(options); //add

        //Profile name
        JLabel name = new JLabel("User1");
        name.setBounds(110,15,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        //Frame
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        //TextField
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("Stika Small", Font.PLAIN,16));
        f.add(text);

        //Send Button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Stika Small", Font.PLAIN,16));
        f.add(send);

        //Profile Status
        JLabel status = new JLabel("Online");
        status.setBounds(110,30,100,35);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);

        f.setSize(450,700); //set size of frame
        f.setLocation(200, 50); //set location
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE); //set background color



        f.setVisible(true); //visible frame

    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());   //right alignment of msg
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);   //vertical alignment
            vertical.add(Box.createVerticalStrut(15)); //vertical alignment space

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText(" ");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 26));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String args[]){

        new Server();
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
               Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream()); //read msg
                dout = new DataOutputStream(s.getOutputStream()); //send msg

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
