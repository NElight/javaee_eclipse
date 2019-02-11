package com.light.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

class MyFrame {
	
	public MyFrame() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame jFrame = new JFrame("hello world swing");
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("hello world");
		jFrame.getContentPane().add(label);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
}

class LoginFrame{
	
	public LoginFrame() {
		JFrame jFrame = new JFrame("login example");
		jFrame.setSize(350, 200);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		JPanel jPanel = new JPanel();
		jFrame.add(jPanel);
		jPanel.setLayout(null);
		JLabel userLabel = new JLabel("User: ");
		userLabel.setBounds(10, 20, 80, 25);
		jPanel.add(userLabel);
		JTextField userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		jPanel.add(userText);
		// 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        jPanel.add(passwordLabel);

        /* 
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        jPanel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        jPanel.add(loginButton);
		
        jFrame.setVisible(true);
		
	}
}

class CounterFrame extends Frame implements ActionListener, WindowListener {
	private Label countLabel;
	private TextField countTF;
	private Button countBtn;
	private int count;
	
	public CounterFrame() {
		setLayout(new FlowLayout());
		addWindowListener(this);
		countLabel = new Label("counter");
		add(countLabel);
		countTF = new TextField(count + "", 10);
		countTF.setEditable(false);
		add(countTF);
		
		countBtn = new Button("count");
		countBtn.addActionListener(this);
		add(countBtn);
		setTitle("count frame");
		setSize(250, 100);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		++count;
		countTF.setText(count + "");
	}
	
	@Override
   public void windowClosing(WindowEvent evt) {
      System.exit(0);  // Terminate the program
   }
 
   // Not Used, BUT need to provide an empty body to compile.
   @Override 
   public void windowOpened(WindowEvent evt) { }
   @Override 
   public void windowClosed(WindowEvent evt) { }
   // For Debugging
   @Override 
   public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
   @Override 
   public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
   @Override 
   public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
   @Override 
   public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
	
}


public class TestWindow {
	public static void main(String[] args) {
//		new LoginFrame();
		new CounterFrame();
	}
}
