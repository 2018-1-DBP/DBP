import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 로그인 화면
 */

public class LoginFrame extends JFrame implements ActionListener {
    JPanel loginPanel, buttonPanel;
    JButton registerButton, loginButton;
    JLabel titleLabel, idLabel, pwLabel;
    JTextField idText, pwText;

    public static void main(String[] args) {
        new LoginFrame();
    }

    public LoginFrame() {
        super("이거모임?");

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2, 2));
        loginPanel.setBounds(200, 120, 200, 160);

        idLabel = new JLabel("아이디");
        pwLabel = new JLabel("비밀번호");
        idText = new JTextField(10);
        pwText = new JTextField(10);

        loginPanel.add(idLabel);
        loginPanel.add(idText);
        loginPanel.add(pwLabel);
        loginPanel.add(pwText);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(200, 280, 200, 120);

        registerButton = new JButton("회원가입");
        loginButton = new JButton("로그인");

        registerButton.addActionListener(this);
        loginButton.addActionListener(this);

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        add(loginPanel);
        add(buttonPanel);
        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton) {
            new RegisterFrame();
        } else if(e.getSource() == loginButton) {
            DatabaseConnect databaseConnect = new DatabaseConnect();
            String input_id = idText.getText();
            String input_pw = pwText.getText();
            String db_pw = databaseConnect.getPassword(input_id);

            if(input_id.equals("") || input_pw.equals("")) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
            } else {
                loginSuccess(input_pw, db_pw);
            }
        }
    }

    /**
     * @param input_pw : 로그인 화면에 입력된 Password
     * @param db_pw : DB에 입력되어 있는 Password
     * 입력된 Password와 DB에 있는 Password를 비교하여 같으면 로그인
     * db_pw가 null이면 ID 다시 입력
     * input_pw와 db_pw가 다르면 로그인 실패, 같으면 로그인 성공
     */
    public void loginSuccess(String input_pw, String db_pw) {
        if(db_pw  == null)
            JOptionPane.showMessageDialog(this, "아이디를 확인해주세요.");
        else if(input_pw.equals(db_pw)) {
            new MainFrame(idText.getText());
            this.dispose();
        }
        else
            JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요.");
    }
}
