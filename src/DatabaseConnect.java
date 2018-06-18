import java.io.IOException;
import java.sql.*;
import java.util.Vector;

/**
 * JDBC를 이용하여 Java와 Oracle DB 사이를 연결해주는 클래스
 */
public class DatabaseConnect {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
    private static final String PASS = "system";
    private static final String USER = "system";

    public Connection getConn() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USER,PASS);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * @param id : 로그인 화면에 입력된 ID
     * @return : 해당 ID에 맞는 Password
     * 입력된 ID의 Password를 DB로부터 가져오는 함수
     */
    public String getPassword(String id) {
        String pw = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select pw from tbl_user where id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                pw = rs.getString("pw");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return pw;
    }

    public GroupData getGroup(int gnumber) {
        GroupData groupData = new GroupData();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select * from tbl_group where gnumber = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, gnumber);
            rs = ps.executeQuery();

            rs.next();

            groupData.setGnumber(rs.getInt("gnumber"));
            groupData.setName(rs.getString("name"));
            groupData.setInterest(rs.getString("interest"));
            groupData.setMaster(rs.getString("master"));
            groupData.setIntro(rs.getString("intro"));

        }catch(Exception e) {
            e.printStackTrace();
        }

        return groupData;
    }

    public Vector getBoardList(int gnum) {
        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select * from tbl_board where gnumber = " + gnum;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                //int gnumber = rs.getInt("gnumber");
                String name = rs.getString("name");
                String content = rs.getString("content");
                String id = rs.getString("id");
                //Object bfile = rs.getObject("bfile");

                Vector row = new Vector();
               // row.add(gnumber);
                row.add(name);
                row.add(content);
                row.add(id);
              //  row.add(bfile);

                data.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public Vector getGroupList() {
        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select * from tbl_group";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                int gnumber = rs.getInt("gnumber");
                String name = rs.getString("name");
                String interest = rs.getString("interest");
                String master = rs.getString("master");
                String intro = rs.getString("intro");

                Vector row = new Vector();
                row.add(gnumber);
                row.add(name);
                row.add(interest);
                row.add(master);

                data.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param userData
     * @return
     * 회원가입 시 사용자 정보 DB에 삽입하는 함수
     */
    public boolean insertUser(UserData userData) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "INSERT INTO tbl_user(id,pw,name,interest,intro) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, userData.getId());
            ps.setString(2, userData.getPwd());
            ps.setString(3, userData.getName());
            ps.setString(4, userData.getInterest());
            ps.setString(5, userData.getIntro());

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    /**
     * 생성된 모임을 DB에 삽입 해주는 함수
     * @param groupData
     * @return
     */
    public boolean insertGroup(GroupData groupData) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        System.out.println(groupData.getGnumber() + "/" + groupData.getName() + "/" + groupData.getInterest() + "/" + groupData.getIntro());
        try {
            con = getConn();

            String sql = "INSERT INTO tbl_group(gnumber,name,interest,master,intro) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, groupData.getGnumber());
            ps.setString(2, groupData.getName());
            ps.setString(3, groupData.getInterest());
            ps.setString(4, groupData.getMaster());
            ps.setString(5, groupData.getIntro());

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        if((ok == true) && joinGroup(groupData.getGnumber(), groupData.getMaster()))
            ok = true;
        else
            ok = false;

        return ok;
    }

    public boolean joinGroup(int gnumber, String id) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();

            String sql = "INSERT INTO tbl_group_member(gnumber,id) VALUES(?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, gnumber);
            ps.setString(2, id);

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    public boolean checkGroupMember(int gnumber, String id) {
        String check = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "SELECT id FROM tbl_group_member WHERE gnumber=? AND id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, gnumber);
            ps.setString(2, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                check = rs.getString("id");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        if(check == null)  return false;
        else return true;
    }

    public int countGroupMember(int gnumber) {
        int count = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();

            String sql = "SELECT count(*) as member_count FROM tbl_group_member WHERE gnumber=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, gnumber);
            rs = ps.executeQuery();

            rs.next();

            count = rs.getInt("member_count");
            System.out.println(count);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return count;
    }
    /**
     * 만들어진 모임의 개수를 세어주는 함수
     * 모임의 개수는 모임 번호를 입력하는데 사용
     * @return
     */
    public int getGroupCount(){
        int gnumber = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();

            String sql = "SELECT count(*) as gnumber FROM tbl_group";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            rs.next();

            gnumber = rs.getInt("gnumber") + 1;
            System.out.println(gnumber);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return gnumber;
    }

    public boolean deleteMember(String id,String pwd) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "DELETE FROM tb_staff WHERE id =? AND pwd=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pwd);
            int r = ps.executeUpdate();

            if(r>0)
                ok = true;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean insertBoard(BoardData groupData) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

       System.out.println(groupData.getGnumber() + "/" + groupData.getName() + "/" + groupData.getContent() + "/" + groupData.getId());
        try {
            con = getConn();

            String sql = "INSERT INTO tbl_board VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, groupData.getGnumber());
            ps.setString(2, groupData.getName());
            ps.setString(3, groupData.getContent());
            ps.setString(4, groupData.getId());

            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        /*if((ok == true) && joinGroup(groupData.getGnumber(), groupData.getMaster()))
            ok = true;
        else
            ok = false;*/

        return ok;
    }
    public String getMaster(int gnumber) {
        String whoMaster = null;
        Vector datas = new Vector();
        Connection conn = null;
        PreparedStatement pss = null;
        ResultSet rss = null;

        try {
            conn = getConn();
            String sqls = "select master from tbl_group where gnumber=" + gnumber;
            pss= conn.prepareStatement(sqls);
            rss = pss.executeQuery();

            while(rss.next()) {
                String master = rss.getString("master");
                whoMaster = master;
            }
        } catch (Exception e) { e.printStackTrace(); }

        return whoMaster;
    }

    public Vector getGroupMember(int gnumber) {
        String whoMaster = null;
        Vector datas = new Vector();
        Connection conn = null;
        PreparedStatement pss = null;
        ResultSet rss = null;

        try {
            conn = getConn();
            String sqls = "select master from tbl_group where gnumber=" + gnumber;
            pss= conn.prepareStatement(sqls);
            rss = pss.executeQuery();

            while(rss.next()) {
                String master = rss.getString("master");
               /* Vector rows = new Vector();
                rows.add(master);
                datas.add(rows);*/
               whoMaster = master;
            }
        } catch (Exception e) { e.printStackTrace(); }


        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "select id from tbl_group_member where gnumber=" + gnumber;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                String id = rs.getString("id");
                Vector row = new Vector();
                row.add(id);
                if(id.equals(whoMaster)) {
                    row.add("방장");
                } else { row.add("회원"); }
                data.add(row);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return data;

    }
    public boolean deleteUser(int gnum, String id) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();

            String sql = "DELETE FROM TBL_GROUP_MEMBER WHERE gnumber=? AND id=?";


            ps = con.prepareStatement(sql);
            ps.setInt(1, gnum);
            ps.setString(2, id);
            int r = ps.executeUpdate();

            if(r>0) {
                ok = true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }
}
