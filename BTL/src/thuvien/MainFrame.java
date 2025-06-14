package thuvien;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private ThuVien thuVien;
    private JTable bookTable;
    private ThuVien thuVienTruong;
    private ThuVien thuVienSo;

    public MainFrame() {
        setTitle("Quản lý thư viện đa nguồn");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        thuVienTruong = new ThuVienTruongThuyLoiFactory().createThuVien();
        thuVienSo = new ThuVienSoDaiHocThuyLoiFactory().createThuVien();

        thuVien = thuVienTruong;

        bookTable = new JTable();
        thuVien.setBookTable(bookTable);

    
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(320, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(220, 220, 220));

        JLabel lblTitle = new JLabel("Quản lý thư viện");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnThuVienTruong = new JButton("Thư viện Trường Thủy Lợi");
        btnThuVienTruong.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnThuVienTruong.setFocusPainted(false);
        btnThuVienTruong.setAlignmentX(Component.CENTER_ALIGNMENT); 

        JButton btnThuVienSo = new JButton("Thư viện Số Đại học Thủy Lợi");
        btnThuVienSo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnThuVienSo.setFocusPainted(false);
        btnThuVienSo.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(lblTitle);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(btnThuVienTruong);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(btnThuVienSo);
        leftPanel.add(Box.createVerticalGlue());


        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(bookTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnThemSach = new JButton("+ Thêm");
        JButton btnSuaSach = new JButton("− Sửa");
        JButton btnXoaSach = new JButton("🗃 Xóa");
        JButton btnTimKiem = new JButton("🔍 Tìm kiếm");

        bottomPanel.add(btnThemSach);
        bottomPanel.add(btnSuaSach);
        bottomPanel.add(btnXoaSach);
        bottomPanel.add(btnTimKiem);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnThuVienTruong.addActionListener(e -> {
            thuVien = thuVienTruong;
            thuVien.setBookTable(bookTable);
            thuVien.hienThiThongTinSach();
        });
        btnThuVienSo.addActionListener(e -> {
            thuVien = thuVienSo;
            thuVien.setBookTable(bookTable);
            thuVien.hienThiThongTinSach();
        });

        btnThemSach.addActionListener(e -> thuVien.themSach());
        btnSuaSach.addActionListener(e -> thuVien.suaSach());
        btnXoaSach.addActionListener(e -> thuVien.xoaSach());
        btnTimKiem.addActionListener(e -> thuVien.timKiem());

        thuVien.hienThiThongTinSach();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
