package Chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Server  implements ActionListener{
	
	JTextField chatBox;
	JPanel ChatArea;
	static JFrame frame = new JFrame();
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	Server(){
		frame.setLayout(null);
		frame.setTitle("Chat Application");
        
		
		    JPanel p1 = new JPanel();
			p1.setBackground(new Color(7,94,84));
			p1.setBounds(0, 0, 450, 70);
			p1.setLayout(null);
			frame.add(p1);
			

			ImageIcon icon_back = new ImageIcon(getClass().getResource("/icons/3.png"));
			Image i2 = icon_back.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
			ImageIcon icon1 = new ImageIcon(i2);
			JLabel back = new JLabel(icon1);
			back.setBounds(5,20,25,25);
			p1.add(back);
			
			back.addMouseListener((MouseListener) new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					System.exit(0);
				}
			});
			
			ImageIcon icon_profile = new ImageIcon(getClass().getResource("/icons/1.png"));
			Image i3 = icon_profile.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
			ImageIcon icon2 = new ImageIcon(i3);
			JLabel profile = new JLabel(icon2);
			profile.setBounds(50,10,50,50);
			p1.add(profile);
			
			ImageIcon icon_video = new ImageIcon(getClass().getResource("/icons/video.png"));
			Image i4 = icon_video.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
			ImageIcon icon3 = new ImageIcon(i4);
			JLabel video = new JLabel(icon3);
			video.setBounds(300,20,30,30);
			p1.add(video);
			
			ImageIcon icon_phone = new ImageIcon(getClass().getResource("/icons/phone.png"));
			Image i5 = icon_phone.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
			ImageIcon icon4 = new ImageIcon(i5);
			JLabel phone = new JLabel(icon4);
			phone.setBounds(360,20,35,35);
			p1.add(phone);
			
			ImageIcon icon_dot = new ImageIcon(getClass().getResource("/icons/3icon.png"));
			Image i6 = icon_dot.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
			ImageIcon icon5 = new ImageIcon(i6);
			JLabel dot = new JLabel(icon5);
			dot.setBounds(410,20,10,25);
			p1.add(dot);
			
			JLabel Name = new JLabel("Ganesh ");
			Name.setBounds(110, 15, 100, 18);
			Name.setForeground(Color.WHITE);
			Name.setFont(new Font("SAN_SERIF",Font.BOLD,17));
			p1.add(Name);
			
			JLabel Status = new JLabel("Online");
			Status.setBounds(110, 35, 100, 18);
			Status.setForeground(Color.WHITE);
			Status.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
			p1.add(Status);
			
			
			ChatArea = new JPanel();
			ChatArea.setBounds(5,75,440,570);
			frame.add(ChatArea);
			
			chatBox = new JTextField();
			chatBox.setBounds(5,655,310,40);
			chatBox.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
			frame.add(chatBox);
			
			JButton send = new JButton("Send");
			send.setBounds(320,655,123,40);
			send.setBackground(new Color(7,94,84));
			send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
			send.setForeground(Color.WHITE);
			frame.add(send);
			
			send.addActionListener(this);
            
        
			frame.setSize(450,700);
			frame.setLocation(200,50);
			frame.setUndecorated(true);
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String mess = chatBox.getText();
		JPanel message = formatLabel(mess);
		ChatArea.setLayout(new BorderLayout());
		
		JPanel right = new JPanel(new BorderLayout());
		right.add(message,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		
		ChatArea.add(vertical,BorderLayout.PAGE_START);
		
		try {
			dout.writeUTF(mess);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		chatBox.setText("");
		
		frame.repaint();
		frame.invalidate();
		frame.validate();
		
	}
	
	public static JPanel formatLabel (String mess)
	{
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + mess + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
		
	}
	
	public static void main(String[] args)
	{
		new Server();
		
		try 
		{
			ServerSocket skt = new ServerSocket(6001);
			while(true) {
				Socket socket = skt.accept();
				DataInputStream din = new DataInputStream(socket.getInputStream());
				dout = new DataOutputStream(socket.getOutputStream());
				
					while(true)
					{
						String msg = din.readUTF();
						JPanel panel = formatLabel(msg);
						
						JPanel left = new JPanel(new BorderLayout());
						left.add(panel,BorderLayout.LINE_START);
						vertical.add(left);
						frame.validate();
						
					}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
