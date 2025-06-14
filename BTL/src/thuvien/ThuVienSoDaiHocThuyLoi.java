package thuvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.sql.*;

public class ThuVienSoDaiHocThuyLoi implements ThuVien {
    private DefaultTableModel model;
    private JTable bookTable;

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
            model = new DefaultTableModel();
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
        }
    }

    public void themSach() {
        JTextField tenSachField = new JTextField();
        JTextField tacGiaField = new JTextField();
        JTextField namXuatBanField = new JTextField();
        JTextField maNXBField = new JTextField();
        JTextField giaField = new JTextField();
        JTextField theLoaiField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tên sách:"));
        panel.add(tenSachField);
        panel.add(new JLabel("Tác giả:"));
        panel.add(tacGiaField);
        panel.add(new JLabel("Năm xuất bản:"));
        panel.add(namXuatBanField);
        panel.add(new JLabel("Mã NXB:"));
        panel.add(maNXBField);
        panel.add(new JLabel("Giá:"));
        panel.add(giaField);
        panel.add(new JLabel("Thể loại:"));
        panel.add(theLoaiField);

        tenSachField.requestFocusInWindow(); 

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Thêm sách mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (tenSachField.getText().trim().isEmpty() ||
                tacGiaField.getText().trim().isEmpty() ||
                namXuatBanField.getText().trim().isEmpty() ||
                maNXBField.getText().trim().isEmpty() ||
                giaField.getText().trim().isEmpty() ||
                theLoaiField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tenSach = tenSachField.getText().trim();
                String tacGia = tacGiaField.getText().trim();
                int namXuatBan = Integer.parseInt(namXuatBanField.getText().trim());
                int maNXB = Integer.parseInt(maNXBField.getText().trim());
                double gia = Double.parseDouble(giaField.getText().trim());
                String theLoai = theLoaiField.getText().trim();

                String query = "INSERT INTO Sach (TenSach, TacGia, NamXuatBan, MaNXB, Gia, TheLoai) VALUES ('" +
                        tenSach + "', '" + tacGia + "', " + namXuatBan + ", " + maNXB + ", " + gia + ", '" + theLoai + "')";
                DBConnect.executeUpdate(query);

                hienThiThongTinSach();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Năm, Mã NXB và Giá phải là số!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void suaSach() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để sửa.");
            return;
        }

        // Bổ sung kiểm tra số cột hợp lệ:
        if (model.getColumnCount() < 7) {
            JOptionPane.showMessageDialog(null, "Lỗi dữ liệu: Số lượng cột không hợp lệ.");
            return;
        }

        try {
            int maSach = Integer.parseInt(model.getValueAt(row, 0).toString());
            String tenSachHienTai = model.getValueAt(row, 1).toString();
            String tacGiaHienTai = model.getValueAt(row, 2).toString();
            int maNXBHienTai = Integer.parseInt(model.getValueAt(row, 3).toString());
            int namXuatBanHienTai = Integer.parseInt(model.getValueAt(row, 4).toString());
            double giaHienTai = Double.parseDouble(model.getValueAt(row, 5).toString());
            String theLoaiHienTai = model.getValueAt(row, 6).toString();

            JTextField tenSachField = new JTextField(tenSachHienTai);
            JTextField tacGiaField = new JTextField(tacGiaHienTai);
            JTextField maNXBField = new JTextField(String.valueOf(maNXBHienTai));
            JTextField namXuatBanField = new JTextField(String.valueOf(namXuatBanHienTai));
            JTextField giaField = new JTextField(String.valueOf(giaHienTai));
            JTextField theLoaiField = new JTextField(theLoaiHienTai);

            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            panel.add(new JLabel("Tên sách:"));
            panel.add(tenSachField);
            panel.add(new JLabel("Tác giả:"));
            panel.add(tacGiaField);
            panel.add(new JLabel("Mã NXB:"));
            panel.add(maNXBField);
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
                    maNXBField.getText().trim().isEmpty() ||
                    namXuatBanField.getText().trim().isEmpty() ||
                    giaField.getText().trim().isEmpty() ||
                    theLoaiField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    String tenSach = tenSachField.getText().trim();
                    String tacGia = tacGiaField.getText().trim();
                    int maNXB = Integer.parseInt(maNXBField.getText().trim());
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
                    JOptionPane.showMessageDialog(null, "Mã NXB, Năm xuất bản và Giá phải là số!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi sửa sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi dữ liệu bảng! Vui lòng kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    @Override
    public void xoaSach() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa.");
            return;
        }

        int maSach = (int) model.getValueAt(row, 0);
        String query = "DELETE FROM Sach WHERE MaSach = " + maSach;
        DBConnect.executeUpdate(query);

        hienThiThongTinSach(); 
    }

    @Override
    public void timKiem() {
        String searchTerm = JOptionPane.showInputDialog("Nhập tên sách để tìm kiếm:");
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String tenSach = (String) model.getValueAt(i, 1);
            if (tenSach != null && tenSach.toLowerCase().contains(searchTerm.toLowerCase())) {
                bookTable.setRowSelectionInterval(i, i);
                found = true;
                break;
            }
        }
        if (!found) JOptionPane.showMessageDialog(null, "Không tìm thấy sách!");
    }

    @Override
    public void themNXB() {
        String tenNXB = JOptionPane.showInputDialog("Nhập tên nhà xuất bản:");
        String diaChi = JOptionPane.showInputDialog("Nhập địa chỉ NXB:");
        String soDienThoai = JOptionPane.showInputDialog("Nhập số điện thoại NXB:");
        String email = JOptionPane.showInputDialog("Nhập email NXB:");

        String query = "INSERT INTO NhaXuatBan (TenNXB, DiaChi, SoDienThoai, Email) VALUES ('"
                + tenNXB + "', '" + diaChi + "', '" + soDienThoai + "', '" + email + "')";
        DBConnect.executeUpdate(query);
    }

    @Override
    public void suaNXB() {
        JOptionPane.showMessageDialog(null, "Hãy tạo chức năng sửa/xóa NXB ở bảng NhaXuatBan riêng!");
    }

    @Override
    public void xoaNXB() {
        JOptionPane.showMessageDialog(null, "Hãy tạo chức năng sửa/xóa NXB ở bảng NhaXuatBan riêng!");
    }
}
