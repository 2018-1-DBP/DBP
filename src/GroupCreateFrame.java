import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupCreateFrame extends JFrame implements ActionListener {
    private String id;

    JTextField tfInterest, tfName;
    JTextArea taIntro;
    JButton btnInsert, btnCancel;

    GridBagLayout gb;
    GridBagConstraints gbc;

    public GroupCreateFrame(String id) {
        super("모임생성");
        this.id = id;

        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bName = new JLabel("모임 이름 :");
        tfName = new JTextField(20);
        gbAdd(bName, 0, 0, 1, 1);
        gbAdd(tfName, 1, 0, 3, 1);

        JLabel bInterest = new JLabel("모임 관심사 : ");
        tfInterest = new JTextField(20);
        gbAdd(bInterest, 0, 1, 1, 1);
        gbAdd(tfInterest, 1, 1, 3, 1);

        JLabel bIntro = new JLabel("모임 소개 :");
        taIntro = new JTextArea(5, 20);
        gbAdd(bIntro, 0, 2, 1, 1);
        gbAdd(taIntro, 1, 2, 3, 1);

        JPanel pButton = new JPanel();
        btnInsert = new JButton("모임생성");
        btnCancel = new JButton("뒤로가기");

        pButton.add(btnInsert);
        pButton.add(btnCancel);
        gbAdd(pButton, 0, 3, 4, 1);

        btnInsert.addActionListener(this);
        btnCancel.addActionListener(this);

        setSize(600, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void gbAdd(JComponent c, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInsert) {
            insertGroup();
            this.dispose();
        } else if(e.getSource() == btnCancel) {
            new MainFrame(id);
            this.dispose();
        }
    }


    private void insertGroup() {
        GroupData groupData = getViewData();

        if(groupData.getGnumber() != -1) {
            DatabaseConnect databaseConnect = new DatabaseConnect();
            boolean ok = databaseConnect.insertGroup(groupData);

            if (ok) {
                JOptionPane.showMessageDialog(this, "모임 생성!");
                new MainFrame(id);
            } else {
                JOptionPane.showMessageDialog(this, "모임 생성 실패");
            }
        } else
            JOptionPane.showMessageDialog(this, "모임 생성 실패");
    }

    public GroupData getViewData() {
        DatabaseConnect databaseConnect = new DatabaseConnect();
        GroupData groupData = new GroupData();

        int gnumber = databaseConnect.getGroupCount();
        String name = tfName.getText();
        String interest = tfInterest.getText();
        String intro = taIntro.getText();
        String master = id;

        groupData.setGnumber(gnumber);
        groupData.setName(name);
        groupData.setInterest(interest);
        groupData.setMaster(master);
        groupData.setIntro(intro);

        return groupData;
    }
}
