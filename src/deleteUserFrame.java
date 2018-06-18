public class deleteUserFrame {
    private DatabaseConnect databaseConnect;
    public deleteUserFrame(int gnum, String id) {
        databaseConnect = new DatabaseConnect();
        databaseConnect.deleteUser(gnum,id);
    }
}
