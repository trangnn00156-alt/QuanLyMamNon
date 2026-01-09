package com.qlmn.ui;

import com.qlmn.model.PhuHuynh;
import com.qlmn.service.PhuHuynhService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PhuHuynhUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai, btnTim;
    private JTextField txtTimKiem;
    private PhuHuynhService service = new PhuHuynhService();
    private String vaiTro;

    public PhuHuynhUI(String vaiTro) {
        this.vaiTro = vaiTro;
        setTitle("QUẢN LÝ PHỤ HUYNH");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // --- Giao diện tổng thể ---
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // --- 1. Header & Thanh Tìm Kiếm ---
        JPanel pnlTop = new JPanel(new GridLayout(2, 1));
        
        // Tiêu đề
        JPanel pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setBackground(new Color(26, 188, 156)); // Màu xanh ngọc
        pnlTitle.setPreferredSize(new Dimension(0, 50));
        JLabel lblTitle = new JLabel("  QUẢN LÝ THÔNG TIN PHỤ HUYNH");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        pnlTitle.add(lblTitle, BorderLayout.WEST);
        
        // Thanh tìm kiếm
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlSearch.setBackground(new Color(245, 246, 250));
        pnlSearch.add(new JLabel("Tìm kiếm (Mã/Tên): "));
        txtTimKiem = new JTextField(25);
        btnTim = new JButton("Tìm Kiếm");
        btnTim.setBackground(new Color(52, 152, 219));
        btnTim.setForeground(Color.WHITE);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTim);
        
        pnlTop.add(pnlTitle);
        pnlTop.add(pnlSearch);
        add(pnlTop, BorderLayout.NORTH);

        // --- 2. Bảng Dữ Liệu ---
        String[] columns = {"Mã PH", "Họ tên", "Giới tính", "SĐT", "Email", "Địa chỉ"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Nút Chức Năng ---
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlBottom.setBackground(Color.WHITE);
        
        btnThem = createStyledButton("Thêm Mới", new Color(46, 204, 113));
        btnSua = createStyledButton("Cập Nhật", new Color(52, 152, 219));
        btnXoa = createStyledButton("Xóa Bỏ", new Color(231, 76, 60));
        btnTaiLai = createStyledButton("Làm Mới", new Color(149, 165, 166));

        pnlBottom.add(btnThem);
        pnlBottom.add(btnSua);
        pnlBottom.add(btnXoa);
        pnlBottom.add(btnTaiLai);
        add(pnlBottom, BorderLayout.SOUTH);

        // --- Sự Kiện ---
        btnTaiLai.addActionListener(e -> {
            txtTimKiem.setText("");
            napDuLieu();
        });
        
        btnTim.addActionListener(e -> logicTimKiem());
        
        phanQuyen();
        napDuLieu();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(130, 35));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color.darker()));
        return btn;
    }

    private void phanQuyen() {
        if (!"Admin".equalsIgnoreCase(vaiTro)) {
            btnThem.setVisible(false);
            btnSua.setVisible(false);
            btnXoa.setVisible(false);
        }
    }

    private void napDuLieu() {
        model.setRowCount(0);
        List<PhuHuynh> ds = service.getAllPhuHuynh();
        if (ds != null) {
            for (PhuHuynh ph : ds) {
                model.addRow(new Object[]{ph.getMaPH(), ph.getHoTen(), ph.getGioiTinh(), ph.getSdt(), ph.getEmail(), ph.getDiaChi()});
            }
        }
    }

    private void logicTimKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            napDuLieu();
            return;
        }
        model.setRowCount(0);
        List<PhuHuynh> ds = service.getAllPhuHuynh(); 
        for (PhuHuynh ph : ds) {
            if (ph.getHoTen().toLowerCase().contains(keyword.toLowerCase()) || ph.getMaPH().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{ph.getMaPH(), ph.getHoTen(), ph.getGioiTinh(), ph.getSdt(), ph.getEmail(), ph.getDiaChi()});
            }
        }
    }
}