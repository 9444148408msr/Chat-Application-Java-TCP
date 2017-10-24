//chatclient
import java.io.*;
import java.net.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class chatclient extends Frame implements ActionListener
{
	//static ServerSocket ss;
	static Socket soc;
	static DataInputStream dis;
	static DataOutputStream dos;
	
	static String cmsg="connecting....";
	
	static TextArea ta;
	static List sh;
	static Button bt;
	static TextField tf;
	static Panel pta,pb,cpan;
	
	chatclient(String fname)
	{
		super(fname);
		setSize(350,450);
		setResizable(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setVisible(true);
		setBackground(new Color(200,200,200));
		pta=new Panel(new FlowLayout(FlowLayout.CENTER));
		pb=new Panel(new FlowLayout(FlowLayout.CENTER));
		sh=new List(15);
		sh.setSize(250,200);
		sh.add(" !                                welcome  \t                                     !");
		sh.add("");
		sh.add("hello");
		//ta=new TextArea(15,40);
		//ta.setEditable(false);
		//ta.append("--chat--");
		tf=new TextField(35);
		bt=new Button("send");
		pta.setSize(300,300);
		pta.setLocation(50,50);
		pb.setSize(50,50);
		pb.setLocation(300,200);
		//pta.add(ta);
		pta.add(sh);
		pb.add(tf);
		pb.add(bt);
		pb.setBackground(Color.green);
		add(pta);
		add(pb);
		
		tf.addActionListener(this);
		bt.addActionListener(this);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){setVisible(false);dispose();System.exit(0);
			try{
			dis.close();soc.close();}catch(Exception e){}
		}});
		
	}
	
	
	public void paint(Graphics g)
	{
		g.drawString(cmsg,20,350);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try{
			sh.add("me : "+tf.getText());
			dos.writeUTF(tf.getText());
			tf.setText("");
			repaint();
		}catch(Exception e){}
		repaint();
	}
	
	public static void main(String args[]) throws Exception
	{
		chatclient c=new chatclient("client");
		/*ss=new ServerSocket(3000);*/
		soc=new Socket("localhost",3000);
		
		dis=new DataInputStream(soc.getInputStream());
		dos=new DataOutputStream(soc.getOutputStream());
		cmsg="Connected";
		while(true)
		{
			Thread.sleep(10);
			sh.add("client : "+dis.readUTF());
		}
	}
}