import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {
    private String id;
    private Vector group_col;
    private JTable group_table;
    private JButton createButton, mygroupButton, logoutButton;
    private DatabaseConnect databaseConnect;

    public MainFrame(String id) {
        super("이거모임?");
        this.id = id;
        databaseConnect = new DatabaseConnect();

        group_col = new Vector<String>();
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
        JScrollPane scrollPane = new JScrollPane(group_table);
        add(scrollPane);
        scrollPane.setBounds(0, 0, 400, 400);

        createButton = new JButton("모임생성");
        createButton.setBounds(450, 10, 100, 30);
        createButton.addActionListener(this);
        mygroupButton = new JButton("내 모임");
        mygroupButton.setBounds(450, 50, 100, 30);
        logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(450, 90, 100, 30);
        logoutButton.addActionListener(this);

        add(createButton);
        add(mygroupButton);
        add(logoutButton);

        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createButton) {
            new GroupCreateFrame(id);
            this.dispose();
        } else if(e.getSource() == logoutButton) {
            new LoginFrame();
            this.dispose();
        }
    }
}
