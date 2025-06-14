package thuvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ThuVienTruongThuyLoi implements ThuVien {
    private List<Sach> danhSachSach = new ArrayList<>();
    private DefaultTableModel model;
    private JTable bookTable;

    public ThuVienTruongThuyLoi() {
        danhSachSach.add(new Sach("S001", "Giải tích 1", "Nguyễn Văn A", "Giáo Dục", 2020, 35000));
        danhSachSach.add(new Sach("S002", "Lập trình C", "Lê Văn B", "Khoa Học", 2021, 45000));
    }

    @Override
    public void setBookTable(JTable table) {
        this.bookTable = table;
    }

    @Override
    public void hienThiThongTinSach() {
        String query = "SELECT s.MaSach, s.TenSach, s.TacGia, n.TenNXB, s.NamXuatBan, s.Gia, s.TheLoai " +
                       "FROM Sach s JOIN NhaXuatBan n ON s.MaNXB = n.MaNXB";
        ResultSet rs = DBConnect.executeQuery(query);

        try {
            // Tạo lại model với đủ 7 cột
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã sách");
            model.addColumn("Tên sách");
            model.addColumn("Tác giả");
            model.addColumn("NXB");
            model.addColumn("Năm");
            model.addColumn("Giá");
            model.addColumn("Thể loại");

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("MaSach");
                row[1] = rs.getString("TenSach");
                row[2] = rs.getString("TacGia");
                row[3] = rs.getString("TenNXB");
                row[4] = rs.getInt("NamXuatBan");
                row[5] = rs.getDouble("Gia");
                row[6] = rs.getString("TheLoai");

                model.addRow(row);
            }

            bookTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void themSach() {
        Map<String, Integer> nxbMap = new LinkedHashMap<>();
        try {
            ResultSet rs = DBConnect.executeQuery("SELECT MaNXB, TenNXB FROM NhaXuatBan");
            while (rs.next()) {
                nxbMap.put(rs.getString("TenNXB"), rs.getInt("MaNXB"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thể tải danh sách NXB!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField tenSachField = new JTextField();
        JTextField tacGiaField = new JTextField();
        JComboBox<String> nxbCombo = new JComboBox<>(nxbMap.keySet().toArray(new String[0]));
        JTextField namXuatBanField = new JTextField();
        JTextField giaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tên sách:"));
        panel.add(tenSachField);
        panel.add(new JLabel("Tác giả:"));
        panel.add(tacGiaField);
        panel.add(new JLabel("Nhà xuất bản:"));
        panel.add(nxbCombo);
        panel.add(new JLabel("Năm xuất bản:"));
        panel.add(namXuatBanField);
        panel.add(new JLabel("Giá:"));
        panel.add(giaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm sách mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (tenSachField.getText().trim().isEmpty() ||
                tacGiaField.getText().trim().isEmpty() ||
                namXuatBanField.getText().trim().isEmpty() ||
                giaField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tenSach = tenSachField.getText().trim();
                String tacGia = tacGiaField.getText().trim();
                int maNXB = nxbMap.get(nxbCombo.getSelectedItem().toString());
                int namXuatBan = Integer.parseInt(namXuatBanField.getText().trim());
                double gia = Double.parseDouble(giaField.getText().trim());

                String query = "INSERT INTO Sach (TenSach, TacGia, MaNXB, NamXuatBan, Gia) VALUES ('"
                        + tenSach + "', '" + tacGia + "', " + maNXB + ", " + namXuatBan + ", " + gia + ")";
                DBConnect.executeUpdate(query);

                hienThiThongTinSach();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Năm và giá phải là số!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách vào CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }


    public void suaSach() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa.");
            return;
        }
        // Lấy dữ liệu cũ từ model của bảng
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        int maSach = Integer.parseInt(model.getValueAt(row, 0).toString());
        String tenSachHienTai = model.getValueAt(row, 1).toString();
        String tacGiaHienTai = model.getValueAt(row, 2).toString();
        String nhaXuatBanHienTai = model.getValueAt(row, 3).toString();
        int namXuatBanHienTai = Integer.parseInt(model.getValueAt(row, 4).toString());
        double giaHienTai = Double.parseDouble(model.getValueAt(row, 5).toString());
        String theLoaiHienTai = model.getColumnCount() > 6 ? model.getValueAt(row, 6).toString() : "";

        // Lấy danh sách NXB từ DB (tương tự như themSach)
        Map<String, Integer> nxbMap = new LinkedHashMap<>();
        try {
            ResultSet rs = DBConnect.executeQuery("SELECT MaNXB, TenNXB FROM NhaXuatBan");
            while (rs.next()) {
                nxbMap.put(rs.getString("TenNXB"), rs.getInt("MaNXB"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thể tải danh sách NXB!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField tenSachField = new JTextField(tenSachHienTai);
        JTextField tacGiaField = new JTextField(tacGiaHienTai);
        JComboBox<String> nxbCombo = new JComboBox<>(nxbMap.keySet().toArray(new String[0]));
        nxbCombo.setSelectedItem(nhaXuatBanHienTai);
        JTextField namXuatBanField = new JTextField(String.valueOf(namXuatBanHienTai));
        JTextField giaField = new JTextField(String.valueOf(giaHienTai));
        JTextField theLoaiField = new JTextField(theLoaiHienTai);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tên sách:"));
        panel.add(tenSachField);
        panel.add(new JLabel("Tác giả:"));
        panel.add(tacGiaField);
        panel.add(new JLabel("Nhà xuất bản:"));
        panel.add(nxbCombo);
        panel.add(new JLabel("Năm xuất bản:"));
        panel.add(namXuatBanField);
        panel.add(new JLabel("Giá:"));
        panel.add(giaField);
        panel.add(new JLabel("Thể loại:"));
        panel.add(theLoaiField);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Sửa thông tin sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (tenSachField.getText().trim().isEmpty() ||
                tacGiaField.getText().trim().isEmpty() ||
                namXuatBanField.getText().trim().isEmpty() ||
                giaField.getText().trim().isEmpty() ||
                theLoaiField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tenSach = tenSachField.getText().trim();
                String tacGia = tacGiaField.getText().trim();
                int maNXB = nxbMap.get(nxbCombo.getSelectedItem().toString());
                int namXuatBan = Integer.parseInt(namXuatBanField.getText().trim());
                double gia = Double.parseDouble(giaField.getText().trim());
                String theLoai = theLoaiField.getText().trim();

                String query = "UPDATE Sach SET TenSach = '" + tenSach + "', TacGia = '" + tacGia +
                               "', MaNXB = " + maNXB +
                               ", NamXuatBan = " + namXuatBan +
                               ", Gia = " + gia +
                               ", TheLoai = '" + theLoai + "' WHERE MaSach = " + maSach;
                DBConnect.executeUpdate(query);

                hienThiThongTinSach();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Năm xuất bản và Giá phải là số!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public void xoaSach() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel(); // Lấy model trực tiếp từ bảng
        int maSach = Integer.parseInt(model.getValueAt(row, 0).toString());
        String query = "DELETE FROM Sach WHERE MaSach = " + maSach;
        DBConnect.executeUpdate(query);
        hienThiThongTinSach(); 
    }

    @Override
    public void timKiem() {
        String searchTerm = JOptionPane.showInputDialog("Nhập tên sách để tìm kiếm:");
        boolean found = false;
        for (int i = 0; i < danhSachSach.size(); i++) {
            Sach sach = danhSachSach.get(i);
            if (sach.getTenSach().toLowerCase().contains(searchTerm.toLowerCase())) {
                bookTable.setRowSelectionInterval(i, i);
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sách!");
        }
    }

    @Override
    public void themNXB() {
        JOptionPane.showMessageDialog(null, "Chức năng thêm NXB chỉ hỗ trợ ở chế độ cơ sở dữ liệu!");
    }

    @Override
    public void suaNXB() {
        JOptionPane.showMessageDialog(null, "Chức năng sửa NXB chỉ hỗ trợ ở chế độ cơ sở dữ liệu!");
    }

    @Override
    public void xoaNXB() {
        JOptionPane.showMessageDialog(null, "Chức năng xóa NXB chỉ hỗ trợ ở chế độ cơ sở dữ liệu!");
    }
}
