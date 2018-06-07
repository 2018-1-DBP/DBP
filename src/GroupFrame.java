import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class GroupFrame extends JFrame {
    private String id;

    public GroupFrame(int gnumber, String id) {
        super("이거 모임?");
        this.id = id;

        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
