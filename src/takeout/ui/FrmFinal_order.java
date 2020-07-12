package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FrmFinal_order extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("ѡ���ջ���ַ");
//	private Button btnFull = new Button("ѡ�������Ż�");//�����Ż�Ҫ��Ҫ�Զ����أ���
	private Button btnCoupon = new Button("ѡ���Ż�ȯ");
	
	
	public FrmFinal_order(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCoupon);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	//	this.getContentPane().add(new JScrollPane(this.otable), BorderLayout.CENTER);
		
		//��Ļ������ʾ
		this.setSize(800, 450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnOk.addActionListener(this);
		this.btnCoupon.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
}
