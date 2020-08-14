package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.UserManager;
import takeout.model.BeanOrder_detail;
import takeout.model.BeanProduct;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmCustomer extends JDialog implements ActionListener{
	
	public static int generate_orderid=1 ;//�������ɶ����ŵ�flag
	public static int SelectedIndex ;//��ǩ��
	public static String currentorderid ;//��ǰ������
	JPanel[] panel = new JPanel[100];
	JTable proTable[]=new  JTable[100];
	Object tblData[][][]=new Object[100][100][5];//������Ʒ��Ϣ����	
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���빺�ﳵ");
	private Button btnBuy = new Button("�鿴���ﳵ");
	private Button btnMe = new Button("��ͨ��Ա");
	private Button btnEvaluate = new Button("��������");
	private Button btnR_Evaluate = new Button("��������");
	
	Connection conn=null;
    JComponent createPanel( String shopid,int i) throws DbException {
 
    	DefaultTableModel tablmod=new DefaultTableModel();//��ű�ͷ����Ϣ
    	Object tblTitle[]={"��Ʒ���","��Ʒ����","�۸�","�Żݼ۸�","��������"};//��ͷ
    	
    	proTable[i]=new JTable(tablmod);
    	JPanel panel = new JPanel();
  
    	List<BeanProduct> result=new ArrayList<BeanProduct>();//��Ʒ�����
	    try {
	    	conn=DBUtil.getConnection();
	    	String sql="select pro_id,pro_name,pro_price,pro_discount_amount,type_id "
	    			+ "from product where shop_id ='"+shopid+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanProduct u=new BeanProduct();
				u.setPro_id(rs.getString(1));
				u.setPro_name(rs.getString(2));
				u.setPro_price(rs.getDouble(3));
				u.setPro_discount_amount(rs.getDouble(4));
				u.setType_id(rs.getString(5));
				result.add(u);
			}
			for(int j=0;j<result.size();j++){
				tblData[i][j][0]=result.get(j).getPro_id();
				tblData[i][j][1]=result.get(j).getPro_name();
				tblData[i][j][2]=result.get(j).getPro_price();
				tblData[i][j][3]=result.get(j).getPro_discount_amount();
				tblData[i][j][4]=result.get(j).getType_id();
			}
	    }catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
    	
    	tablmod.setDataVector(tblData[i],tblTitle);
    	proTable[i].validate();
    	proTable[i].repaint();		
    	panel.add(new JScrollPane(proTable[i]));
        return panel;
    }
    
    public FrmCustomer() throws BaseException {
    	JFrame jf = new JFrame("����!"+UserManager.currentUser.getUser_id()+"������Ϊ�㵥ҳ�棬��ӭ�㵥");
	    jf.setSize(800, 550);
	    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    jf.setLocationRelativeTo(null);
	    
	    toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    toolBar.add(btnR_Evaluate);
	    toolBar.add(btnEvaluate);
	    toolBar.add(btnMe);
		toolBar.add(btnAdd);
		toolBar.add(btnBuy);
		
		
		//��������࣬��������
	    final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT,JTabbedPane.SCROLL_TAB_LAYOUT);
	    try {
	    	conn=DBUtil.getConnection();
	    	String sql="select shop_id,shop_name from shop group by shop_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			int i=0;
			while(rs.next()){
				panel[i]=(JPanel) createPanel(rs.getString(1),i);
				tabbedPane.addTab(rs.getString(2), new ImageIcon("image//pic2.png"),panel[i]);
				i++;
			}
	    }catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
	    jf.getContentPane().add(toolBar, BorderLayout.SOUTH);
		jf.getContentPane().add(tabbedPane, BorderLayout.NORTH);
	    jf.setVisible(true);
	    
		this.btnMe.addActionListener(this);	    
	    this.btnAdd.addActionListener(this);
		this.btnBuy.addActionListener(this);
		this.btnEvaluate.addActionListener(this);
		this.btnR_Evaluate.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
		
		tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SelectedIndex=tabbedPane.getSelectedIndex();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnAdd){
			
			int index=SelectedIndex;
			int i=proTable[index].getSelectedRow();
			
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ�����빺�ﳵ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				if(generate_orderid==1) {
					FrmCustomer.currentorderid=""+(int)(System.currentTimeMillis()%1000000);
					generate_orderid=0;
				}
				Connection conn=null;
				Connection conn1=null;
				Connection conn2=null;
				try {
					conn=DBUtil.getConnection();
					String sql="select order_id from order_detail where order_id='"+FrmCustomer.currentorderid+"'";
					java.sql.Statement st=conn.createStatement();
					java.sql.ResultSet rs=st.executeQuery(sql);
					if(rs.next()) {//��������
						conn1=DBUtil.getConnection();
						sql="select pro_id from order_detail where pro_id='"+tblData[index][i][0].toString()+"'"
								+ "and order_id='"+FrmCustomer.currentorderid+"'";
						java.sql.Statement st1=conn1.createStatement();
						java.sql.ResultSet rs1=st1.executeQuery(sql);
						if(rs1.next()) {//��Ʒ����
							conn2=DBUtil.getConnection();
							sql="update order_detail set single_quantity=single_quantity+1 where pro_id='"+rs1.getString(1)+"'"
									+ "and order_id='"+FrmCustomer.currentorderid+"'";
							java.sql.Statement st2=conn2.createStatement();
							st2.execute(sql);
							st2.close();
						}else{//��Ʒ������
							BeanOrder_detail u=new BeanOrder_detail();
							u.setOrder_id(FrmCustomer.currentorderid);//������
							u.setPro_id(tblData[index][i][0].toString());//��Ʒ��
							u.setSingle_cost(Double.parseDouble(tblData[index][i][2].toString()));//��Ʒ����
							u.setSingle_discount(Double.parseDouble(tblData[index][i][3].toString()));//��Ʒ�Ż�
							u.setSingle_quantity(1);//��Ʒ����
						
						    (new OrderManager()).createOrd_detail(u);
						}
						rs1.close();
					}
					else {//����������
						BeanOrder_detail u=new BeanOrder_detail();
						u.setOrder_id(FrmCustomer.currentorderid);//������
						u.setPro_id(tblData[index][i][0].toString());//��Ʒ��
						u.setSingle_cost(Double.parseDouble(tblData[index][i][2].toString()));//��Ʒ����
						u.setSingle_discount(Double.parseDouble(tblData[index][i][3].toString()));//��Ʒ�Ż�
						u.setSingle_quantity(1);//��Ʒ����
						
						sql="insert into order_all(order_id) values(?)";
						java.sql.PreparedStatement pst=conn.prepareStatement(sql);
						pst.setString(1, FrmCustomer.currentorderid);
						pst.execute();
						
					    (new OrderManager()).createOrd_detail(u);
					}
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException|BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		else if(e.getSource()==this.btnBuy){
			FrmCheckOrder dlg=new FrmCheckOrder(this,"�鿴���ﳵ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnMe) {
			FrmMe dlg;
			try {
				dlg = new FrmMe(this,"��ͨ��Ա",true);
				dlg.setVisible(true);
			} catch (DbException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else if(e.getSource()==this.btnEvaluate){
			
			FrmEva dlg=new FrmEva(this,"��������",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnR_Evaluate){
			FrmR_Eva dlg=new FrmR_Eva(this,"��������",true);
			dlg.setVisible(true);
		}
    }
}

