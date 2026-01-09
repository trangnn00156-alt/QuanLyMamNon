package com.qlmn.ui;

import com.qlmn.model.GiaoVien;
import com.qlmn.service.GiaoVienService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GiaoVienUI extends JFrame {
    private JTable tblGiaoVien;
    private JTextField txtMaGV, txtHoTen, txtNgaySinh, txtSDT, txtEmail, txtTrinhDo;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private final GiaoVienService giaoVienService = new GiaoVienService();
    private String vaiTro;

    public GiaoVienUI(String vaiTro) {
        this.vaiTro = vaiTro;
        setTitle("Hệ thống quản lý mầm non - Quản lý Giáo Viên");
        setSize(1100, 650); // Tăng kích thước để rộng rãi hơn
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initModernUI();
        loadData();
        addEvents();
        phanQuyen();
    }

    private void initModernUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(70, 130, 180));
        JLabel lblTitle = new JLabel("DANH SÁCH GIÁO VIÊN");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. LEFT PANEL: INPUT FORM ---
        JPanel pnlLeft = new JPanel(null);
        pnlLeft.setPreferredSize(new Dimension(350, 0));
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Thông tin chi tiết"));

        // Hàm tiện ích để tạo Label và TextField nhanh
        String[] labels = {"Mã GV:", "Họ Tên:", "Ngày Sinh:", "Giới Tính:", "SĐT:", "Email:", "Trình Độ:"};
        int y = 40;
        
        txtMaGV = createField(pnlLeft, "Mã GV:", y); y += 65;
        txtHoTen = createField(pnlLeft, "Họ Tên:", y); y += 65;
        txtNgaySinh = createField(pnlLeft, "Ngày Sinh (yyyy-MM-dd):", y); y += 65;
        
        JLabel lblGT = new JLabel("Giới Tính:");
        lblGT.setBounds(20, y, 150, 20);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh.setBounds(20, y + 25, 310, 30);
        pnlLeft.add(lblGT); pnlLeft.add(cboGioiTinh);
        y += 65;

        txtSDT = createField(pnlLeft, "SĐT:", y); y += 65;
        txtEmail = createField(pnlLeft, "Email:", y); y += 65;
        txtTrinhDo = createField(pnlLeft, "Trình Độ:", y);

        add(pnlLeft, BorderLayout.WEST);

        // --- 3. CENTER PANEL: TABLE ---
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setBorder(new EmptyBorder(0, 10, 10, 10));

        String[] columns = {"Mã GV", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Email", "Trình Độ"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblGiaoVien = new JTable(model);
        tblGiaoVien.setRowHeight(25);
        tblGiaoVien.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tblGiaoVien.getTableHeader().setBackground(new Color(240, 240, 240));
        
        pnlCenter.add(new JScrollPane(tblGiaoVien), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // --- 4. SOUTH PANEL: BUTTONS ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setBackground(Color.WHITE);

        btnThem = createButton("Thêm Mới", new Color(46, 204, 113));
        btnSua = createButton("Cập Nhật", new Color(52, 152, 219));
        btnXoa = createButton("Xóa Bỏ", new Color(231, 76, 60));
        btnLamMoi = createButton("Làm Mới", new Color(149, 165, 166));

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // Hàm bổ trợ tạo TextField đồng bộ với Form Đăng Nhập
    private JTextField createField(JPanel panel, String label, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(20, y, 250, 20);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JTextField txt = new JTextField();
        txt.setBounds(20, y + 25, 310, 30);
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        
        panel.add(lbl);
        panel.add(txt);
        return txt;
    }

    // Hàm bổ trợ tạo Button đẹp
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void phanQuyen() {
        if (!"Admin".equalsIgnoreCase(vaiTro)) {
            btnThem.setEnabled(false);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnThem.setBackground(Color.LIGHT_GRAY);
            btnSua.setBackground(Color.LIGHT_GRAY);
            btnXoa.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void loadData() {
        List<GiaoVien> ds = giaoVienService.getAll();
        DefaultTableModel model = (DefaultTableModel) tblGiaoVien.getModel();
        model.setRowCount(0);
        for (GiaoVien gv : ds) {
            model.addRow(new Object[]{
                gv.getMaGV(), gv.getHoTen(), gv.getNgaySinh(), gv.getGioiTinh(),
                gv.getSdt(), gv.getEmail(), gv.getTrinhDo()
            });
        }
    }

    private void addEvents() {
        btnThem.addActionListener(e -> themGiaoVien());
        btnXoa.addActionListener(e -> xoaGiaoVien());
        btnLamMoi.addActionListener(e -> {
            clearFields();
            loadData();
        });
        
        // Sự kiện click vào dòng trong bảng để hiện lên ô nhập
        tblGiaoVien.getSelectionModel().addListSelectionListener(e -> {
            int row = tblGiaoVien.getSelectedRow();
            if (row >= 0) {
                txtMaGV.setText(tblGiaoVien.getValueAt(row, 0).toString());
                txtHoTen.setText(tblGiaoVien.getValueAt(row, 1).toString());
                txtNgaySinh.setText(tblGiaoVien.getValueAt(row, 2).toString());
                cboGioiTinh.setSelectedItem(tblGiaoVien.getValueAt(row, 3).toString());
                txtSDT.setText(tblGiaoVien.getValueAt(row, 4).toString());
                txtEmail.setText(tblGiaoVien.getValueAt(row, 5).toString());
                txtTrinhDo.setText(tblGiaoVien.getValueAt(row, 6).toString());
                txtMaGV.setEditable(false); // Không cho sửa mã
            }
        });
    }

    private void clearFields() {
        txtMaGV.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtTrinhDo.setText("");
        txtMaGV.setEditable(true);
        tblGiaoVien.clearSelection();
    }

    private void themGiaoVien() {
        // ... (Giữ nguyên logic của bạn) ...
        try {
            GiaoVien gv = new GiaoVien(
                txtMaGV.getText().trim(), txtHoTen.getText().trim(),
                txtNgaySinh.getText().trim(), cboGioiTinh.getSelectedItem().toString(),
                txtSDT.getText().trim(), txtEmail.getText().trim(), txtTrinhDo.getText().trim()
            );
            if (giaoVienService.themGiaoVien(gv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
                clearFields();
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
    }

    private void xoaGiaoVien() {
        String maGV = txtMaGV.getText().trim();
        if (maGV.isEmpty()) return;
        if (JOptionPane.showConfirmDialog(this, "Xóa giáo viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (giaoVienService.xoaGiaoVien(maGV)) {
                loadData();
                clearFields();
            }
        }
    }
}