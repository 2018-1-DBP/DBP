import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 로그인 화면
 */

public class LoginFrame extends JFrame implements ActionListener {
    JPanel buttonPanel;
    JButton registerButton, loginButton;
    JLabel idLabel, pwLabel;
    JTextField idText;
    JPasswordField pwText;

    public static void main(String[] args) {
        new LoginFrame();
    }

    public LoginFrame() {
        super("이거모임?");

        idLabel = new JLabel("아이디");
        idLabel.setBounds(190, 100, 80, 40);
        idText = new JTextField(10);
        idText.setBounds(260, 100, 150, 40);

        pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(190, 150, 80, 40);
        pwText = new JPasswordField(10);
        pwText.setBounds(260, 150, 150, 40);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(195, 220, 210, 120);

        registerButton = new JButton("회원가입");
        registerButton.setPreferredSize(new Dimension(100, 30));
        loginButton = new JButton("로그인");
        loginButton.setPreferredSize(new Dimension(100, 30));

        registerButton.addActionListener(this);
        loginButton.addActionListener(this);

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        add(idLabel);
        add(idText);
        add(pwLabel);
        add(pwText);
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
