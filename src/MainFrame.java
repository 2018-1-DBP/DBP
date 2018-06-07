import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener, MouseListener {
    private String id;
    private Vector group_col;
    private JTable group_table;
    private JButton conButton, createButton, infoButton, logoutButton;
    private DatabaseConnect databaseConnect;

    private int gnumber = -1;

    private JFrame infoFrame;
    private JButton joinButton, cancelButton;

    public MainFrame(String id) {
        super("이거모임?");
        this.id = id;
        databaseConnect = new DatabaseConnect();

        group_col = new Vector<String>();
        group_col.add("No.");
        group_col.add("모임 이름");
        group_col.add("관심사");
        group_col.add("모임장");

        DefaultTableModel model = new DefaultTableModel(databaseConnect.getGroupList(), group_col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        group_table = new JTable(model);
        group_table.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane(group_table);
        add(scrollPane);
        scrollPane.setBounds(0, 0, 400, 400);

        conButton = new JButton("모임접속");
        conButton.setBounds(450, 10, 100, 30);
        conButton.addActionListener(this);
        infoButton = new JButton("모임가입");
        infoButton.setBounds(450, 50, 100, 30);
        infoButton.addActionListener(this);
        createButton = new JButton("모임생성");
        createButton.setBounds(450, 90, 100, 30);
        createButton.addActionListener(this);
        logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(450, 130, 100, 30);
        logoutButton.addActionListener(this);

        add(conButton);
        add(infoButton);
        add(createButton);
        add(logoutButton);

        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void groupInfoFrame(int gnumber) {
        infoFrame = new JFrame("모임정보");
        GroupData groupData = databaseConnect.getGroup(gnumber);
        Boolean check = databaseConnect.checkGroupMember(groupData.getGnumber(), id);
        int count = databaseConnect.countGroupMember(groupData.getGnumber());

        System.out.println(groupData.getName());

        JLabel nameLabel = new JLabel("모임이름 :");
        nameLabel.setBounds(20, 10, 80, 30);
        JLabel getNameLabel = new JLabel(groupData.getName());
        getNameLabel.setBounds(100, 10, 330, 30);

        JLabel masterLabel = new JLabel("모임장 :");
        masterLabel.setBounds(20, 50, 80, 30);
        JLabel getMasterLabel = new JLabel(groupData.getMaster());
        getMasterLabel.setBounds(100, 50, 330, 30);

        JLabel interestLabel = new JLabel("관심사 :");
        interestLabel.setBounds(20, 90, 80, 30);
        JLabel getInterestLabel = new JLabel(groupData.getInterest());
        getInterestLabel.setBounds(100, 90, 330, 30);

        JLabel introLabel = new JLabel("모임소개 :");
        introLabel.setBounds(20, 130, 80, 30);
        JLabel getIntroLabel = new JLabel(groupData.getIntro());
        getIntroLabel.setBounds(100, 130, 330, 30);

        JLabel countLabel = new JLabel("모임원 수 :");
        countLabel.setBounds(20, 170, 80, 30);
        JLabel getCountLabel = new JLabel(String.valueOf(count));
        getCountLabel.setBounds(100, 170, 330, 30);

        JPanel pButton = new JPanel();
        joinButton = new JButton("가입");
        cancelButton = new JButton("닫기");

        pButton.add(joinButton);
        pButton.add(cancelButton);
        pButton.setBounds(90, 220, 150, 40);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == joinButton) {
                    Boolean success = databaseConnect.joinGroup(groupData.getGnumber(), id);
                    if (success == true) {
                        JOptionPane.showMessageDialog(infoFrame, "모임가입 성공");
                        infoFrame.dispose();
                    } else
                        JOptionPane.showMessageDialog(infoFrame, "모임가입 실패");
                }
            }
        });
        cancelButton.addActionListener(this);

        if(check == true)
            joinButton.setEnabled(false);

        infoFrame.add(nameLabel);
        infoFrame.add(getNameLabel);
        infoFrame.add(masterLabel);
        infoFrame.add(getMasterLabel);
        infoFrame.add(interestLabel);
        infoFrame.add(getInterestLabel);
        infoFrame.add(introLabel);
        infoFrame.add(getIntroLabel);
        infoFrame.add(countLabel);
        infoFrame.add(getCountLabel);
        infoFrame.add(pButton);

        infoFrame.setLayout(null);
        infoFrame.setSize(350, 350);
        infoFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == conButton) {
            Boolean check = databaseConnect.checkGroupMember(gnumber, id);
            if(check == true) {
                new GroupFrame(gnumber, id);
                dispose();
            }
            else
                JOptionPane.showMessageDialog(this, "모임에 가입해주세요");
        } else if(e.getSource() == createButton) {
            new GroupCreateFrame(id);
            this.dispose();
        } else if(e.getSource() == logoutButton) {
            new LoginFrame();
            this.dispose();
        } else if(e.getSource() == infoButton) {
            if(gnumber == -1)
                JOptionPane.showMessageDialog(this, "모임을 선택해주세요");
            else
                groupInfoFrame(gnumber);
        } else if(e.getSource() == cancelButton) {
            infoFrame.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int rowIndex = group_table.getSelectedRow();
        gnumber = (int) group_table.getValueAt(rowIndex, 0);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
