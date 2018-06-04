import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 회원가입 화면
 */
public class RegisterFrame extends JFrame implements ActionListener {
    JTextField tfId, tfName, tfInterest;
    JPasswordField pfPwd;
    JTextArea taIntro;
    JButton btnInsert, btnCancel;

    GridBagLayout gb;
    GridBagConstraints gbc;

    public RegisterFrame() {
        createUI();

    }

    private void createUI() {
        this.setTitle("회원가입");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bId = new JLabel("아이디 : ");
        tfId = new JTextField(20);
        gbAdd(bId, 0, 0, 1, 1);
        gbAdd(tfId, 1, 0, 3, 1);

        JLabel bPwd = new JLabel("비밀번호 : ");
        pfPwd = new JPasswordField(20);
        gbAdd(bPwd, 0, 1, 1, 1);
        gbAdd(pfPwd, 1, 1, 3, 1);

        JLabel bName = new JLabel("별명 :");
        tfName = new JTextField(20);
        gbAdd(bName, 0, 2, 1, 1);
        gbAdd(tfName, 1, 2, 3, 1);

        JLabel bInterest = new JLabel("관심사 :");
        tfInterest = new JTextField(20);
        gbAdd(bInterest, 0, 3, 1, 1);
        gbAdd(tfInterest, 1, 3, 3, 1);

        JLabel bIntro = new JLabel("자기소개");
        taIntro = new JTextArea(5, 20);
        gbAdd(bIntro, 0, 4, 1, 1);
        gbAdd(taIntro, 1, 4, 3, 1);

        JPanel pButton = new JPanel();
        btnInsert = new JButton("가입");
        btnCancel = new JButton("취소");

        pButton.add(btnInsert);
        pButton.add(btnCancel);
        gbAdd(pButton, 0, 5, 4, 1);

        btnInsert.addActionListener(this);
        btnCancel.addActionListener(this);

        setSize(350, 350);
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
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnInsert) {
            insertUser();
        } else if (ae.getSource() == btnCancel) {
            this.dispose();
        }
    }

    private void insertUser() {
        UserData userData = getViewData();
        DatabaseConnect databaseConnect = new DatabaseConnect();
        boolean ok = databaseConnect.insertUser(userData);

        if (ok) {
            JOptionPane.showMessageDialog(this, "회원가입에 성공했습니다.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다.");
        }
    }

    public UserData getViewData() {
        UserData userData = new UserData();
        String id = tfId.getText();
        String pwd = pfPwd.getText();
        String name = tfName.getText();
        String interest = tfInterest.getText();
        String intro = taIntro.getText();

        userData.setId(id);
        userData.setPwd(pwd);
        userData.setName(name);
        userData.setInterest(interest);
        userData.setIntro(intro);

        return userData;
    }

}
