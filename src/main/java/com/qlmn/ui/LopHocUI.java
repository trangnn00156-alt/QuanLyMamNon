package com.qlmn.ui;

import com.qlmn.model.LopHoc;
import com.qlmn.service.LopHocService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LopHocUI extends JFrame {
    private JTable tblLopHoc;
    private DefaultTableModel model;
    private JTextField txtMaLop, txtTenLop, txtKhoi, txtSiSo, txtMaGV, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai, btnTimKiem;
    private final LopHocService lopHocService = new LopHocService();
    private final String vaiTro;

    public LopHocUI(String vaiTro) {
        this.vaiTro = vaiTro;
        setTitle("HỆ THỐNG MẦM NON - QUẢN LÝ LỚP HỌC");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initModernUI();
        napDuLieu(""); // Nạp toàn bộ lúc đầu
        addEvents();
        phanQuyen();
    }

    private void initModernUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // --- 1. HEADER (Tích hợp Tìm kiếm) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(155, 89, 182));
        pnlHeader.setPreferredSize(new Dimension(0, 60));

        JLabel lblTitle = new JLabel("  QUẢN LÝ LỚP HỌC");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        // Cụm tìm kiếm
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        pnlSearch.setOpaque(false);
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("Tìm Mã Lớp 🔍");
        pnlSearch.add(new JLabel("<html><font color='white'>Mã lớp:</font></html>"));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);
        pnlHeader.add(pnlSearch, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. LEFT PANEL: INPUT ---
        JPanel pnlLeft = new JPanel(null);
        pnlLeft.setPreferredSize(new Dimension(320, 0));
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Chi tiết lớp học"));

        int y = 40;
        txtMaLop = createField(pnlLeft, "Mã lớp học:", y); y += 65;
        txtTenLop = createField(pnlLeft, "Tên lớp học:", y); y += 65;
        txtKhoi = createField(pnlLeft, "Khối học (Mầm/Chồi/Lá):", y); y += 65;
        txtSiSo = createField(pnlLeft, "Sĩ số tối đa:", y); y += 65;
        txtMaGV = createField(pnlLeft, "Mã GV chủ nhiệm:", y);
        add(pnlLeft, BorderLayout.WEST);

        // --- 3. CENTER PANEL: TABLE ---
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setBorder(new EmptyBorder(0, 5, 5, 10));

        String[] columns = {"Mã lớp", "Tên lớp", "Khối", "Sĩ số tối đa", "Mã GV"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLopHoc = new JTable(model);
        tblLopHoc.setRowHeight(28);
        pnlCenter.add(new JScrollPane(tblLopHoc), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // --- 4. SOUTH PANEL: BUTTONS ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlButtons.setBackground(new Color(245, 246, 250));
        btnThem = createButton("Thêm Lớp", new Color(46, 204, 113));
        btnSua = createButton("Cập Nhật", new Color(52, 152, 219));
        btnXoa = createButton("Xóa Lớp", new Color(231, 76, 60));
        btnTaiLai = createButton("Làm Mới", new Color(149, 165, 166));

        pnlButtons.add(btnThem); pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa); pnlButtons.add(btnTaiLai);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private JTextField createField(JPanel panel, String label, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(20, y, 250, 20);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        JTextField txt = new JTextField();
        txt.setBounds(20, y + 25, 280, 30);
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        panel.add(lbl); panel.add(txt);
        return txt;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder());
        return btn;
    }

    private void napDuLieu(String maLop) {
    model.setRowCount(0);
    List<LopHoc> ds;
    
    // Xu ly truong hop maLop bi null hoac chi co khoang trang
    if (maLop == null || maLop.trim().isEmpty()) {
        System.out.println("DEBUG: Dang goi ham getAllLopHoc...");
        ds = lopHocService.getAllLopHoc();
    } else {
        System.out.println("DEBUG: Dang tim kiem lop voi ma: " + maLop);
        ds = lopHocService.findByMaLop(maLop);
    }

    if (ds == null) {
        System.out.println("DEBUG: Danh sach tra ve bi NULL!");
    } else if (ds.isEmpty()) {
        System.out.println("DEBUG: Danh sach rong (Size = 0). Hay kiem tra table LopHoc trong MySQL!");
    } else {
        System.out.println("DEBUG: Tim thay " + ds.size() + " lop hoc.");
        for (LopHoc lop : ds) {
            model.addRow(new Object[]{
                lop.getMaLop(), 
                lop.getTenLop(), 
                lop.getKhoi(), 
                lop.getSiSo(), 
                lop.getMaGV()
            });
        }
    }
}

    private void addEvents() {
    // Nút Tìm Kiếm
    btnTimKiem.addActionListener(e -> napDuLieu(txtTimKiem.getText().trim()));

    // Nút Thêm: Kiểm tra trống trước khi gọi Service
    btnThem.addActionListener(e -> {
        if (txtMaLop.getText().isEmpty() || txtTenLop.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên lớp!");
            return;
        }
        themLopHoc();
    });

    // Nút Cập nhật
    btnSua.addActionListener(e -> {
        if (tblLopHoc.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lớp trong bảng để sửa!");
            return;
        }
        suaLopHoc();
    });

    // Nút Xóa
    btnXoa.addActionListener(e -> xoaLopHoc());

    // Nút Làm mới
    btnTaiLai.addActionListener(e -> {
        clearFields();
        napDuLieu("");
    });
}
    private void phanQuyen() {
        if (!"Admin".equalsIgnoreCase(vaiTro)) {
            btnThem.setVisible(false); btnSua.setVisible(false); btnXoa.setVisible(false);
            txtMaLop.setEditable(false); txtTenLop.setEditable(false); txtKhoi.setEditable(false);
            txtSiSo.setEditable(false); txtMaGV.setEditable(false);
        }
    }

    private void clearFields() {
        txtMaLop.setText(""); txtTenLop.setText(""); txtKhoi.setText("");
        txtSiSo.setText(""); txtMaGV.setText(""); txtTimKiem.setText("");
        txtMaLop.setEditable(true);
    }

    private void themLopHoc() {
        try {
            LopHoc lop = new LopHoc(txtMaLop.getText().trim(), txtTenLop.getText().trim(),
                    txtKhoi.getText().trim(), Integer.parseInt(txtSiSo.getText().trim()), txtMaGV.getText().trim());
            if (lopHocService.themLopHoc(lop)) {
                JOptionPane.showMessageDialog(this, "Thêm lớp học thành công!");
                napDuLieu(""); // SỬA LỖI DÒNG 172: Thêm cặp ngoặc kép rỗng
                clearFields();
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage()); }
    }

    private void suaLopHoc() {
        try {
            LopHoc lop = new LopHoc(txtMaLop.getText().trim(), txtTenLop.getText().trim(),
                    txtKhoi.getText().trim(), Integer.parseInt(txtSiSo.getText().trim()), txtMaGV.getText().trim());
            if (lopHocService.capNhatLopHoc(lop)) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật thông tin lớp!");
                napDuLieu(""); // SỬA LỖI DÒNG 184 (hoặc 182 tùy máy): Thêm cặp ngoặc kép rỗng
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi cập nhật!"); }
    }

    private void xoaLopHoc() {
        String maLop = txtMaLop.getText().trim();
        if (maLop.isEmpty()) return;
        if (JOptionPane.showConfirmDialog(this, "Xóa lớp này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (lopHocService.xoaLopHoc(maLop)) {
                napDuLieu(""); // SỬA LỖI DÒNG 194 (hoặc 192 tùy máy): Thêm cặp ngoặc kép rỗng
                clearFields();
            }
        }
    }
    
    
}