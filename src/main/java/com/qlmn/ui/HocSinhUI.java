package com.qlmn.ui;

import com.qlmn.model.HocSinh;
import com.qlmn.service.HocSinhService;
import com.qlmn.util.SharedData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HocSinhUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaHS, txtHoTen, txtNamSinh, txtMaLop, txtMaPH, txtSearch;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai, btnTim;
    
    private HocSinhService hocSinhService = new HocSinhService();
    private String vaiTro;

    public HocSinhUI(String vaiTro) {
        this.vaiTro = vaiTro;
        setTitle("QUẢN LÝ HỒ SƠ HỌC SINH - Vai trò: " + vaiTro);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        addEvents();
        
        // Lấy dữ liệu đúng theo vai trò khi vừa mở máy
        napDuLieu(""); 
        
        // Ẩn/Hiện nút dựa trên vai trò
        phanQuyenButtons();
    }

    private void initUI() {
        // --- Phần nhập liệu (West) ---
        JPanel pnlLeft = new JPanel(new GridBagLayout());
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));
        pnlLeft.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaHS = new JTextField(15);
        txtHoTen = new JTextField(15);
        txtNamSinh = new JTextField(15);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        txtMaLop = new JTextField(15);
        txtMaPH = new JTextField(15);

        addComp(pnlLeft, new JLabel("Mã HS:"), gbc, 0, 0);
        addComp(pnlLeft, txtMaHS, gbc, 1, 0);
        addComp(pnlLeft, new JLabel("Họ Tên:"), gbc, 0, 1);
        addComp(pnlLeft, txtHoTen, gbc, 1, 1);
        addComp(pnlLeft, new JLabel("Năm sinh:"), gbc, 0, 2);
        addComp(pnlLeft, txtNamSinh, gbc, 1, 2);
        addComp(pnlLeft, new JLabel("Giới tính:"), gbc, 0, 3);
        addComp(pnlLeft, cboGioiTinh, gbc, 1, 3);
        addComp(pnlLeft, new JLabel("Mã Lớp:"), gbc, 0, 4);
        addComp(pnlLeft, txtMaLop, gbc, 1, 4);
        addComp(pnlLeft, new JLabel("Mã PH:"), gbc, 0, 5);
        addComp(pnlLeft, txtMaPH, gbc, 1, 5);

        // --- Phần bảng dữ liệu (Center) ---
        String[] cols = {"Mã HS", "Họ Tên", "Năm Sinh", "Tuổi", "Giới Tính", "Mã Lớp", "Mã PH"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);

        // --- Thanh tìm kiếm (Top) ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        btnTim = new JButton("Tìm tên");
        pnlSearch.add(new JLabel("Tìm kiếm: "));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnTim);

        // --- Nút chức năng (Bottom) ---
        JPanel pnlButtons = new JPanel();
        btnThem = new JButton("Thêm Mới");
        btnSua = new JButton("Cập Nhật");
        btnXoa = new JButton("Xóa");
        btnTaiLai = new JButton("Làm Mới");
        pnlButtons.add(btnThem); pnlButtons.add(btnSua); 
        pnlButtons.add(btnXoa); pnlButtons.add(btnTaiLai);

        add(pnlLeft, BorderLayout.WEST);
        add(sp, BorderLayout.CENTER);
        add(pnlSearch, BorderLayout.NORTH);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void addComp(JPanel p, JComponent c, GridBagConstraints g, int x, int y) {
        g.gridx = x; g.gridy = y; p.add(c, g);
    }

    private void addEvents() {
        btnThem.addActionListener(e -> themHocSinh());
        btnTaiLai.addActionListener(e -> { clearFields(); napDuLieu(""); });
        btnTim.addActionListener(e -> napDuLieu(txtSearch.getText()));

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                txtMaHS.setText(model.getValueAt(row, 0).toString());
                txtHoTen.setText(model.getValueAt(row, 1).toString());
                txtNamSinh.setText(model.getValueAt(row, 2).toString());
                cboGioiTinh.setSelectedItem(model.getValueAt(row, 4).toString());
                txtMaLop.setText(model.getValueAt(row, 5).toString());
                txtMaPH.setText(model.getValueAt(row, 6).toString());
            }
        });
    }

    // PHÂN QUYỀN NÚT BẤM
    private void phanQuyenButtons() {
        if ("PhuHuynh".equalsIgnoreCase(vaiTro)) {
            btnThem.setVisible(false);
            btnSua.setVisible(false);
            btnXoa.setVisible(false);
            // Phụ huynh không được sửa thông tin chi tiết
            txtMaHS.setEditable(false);
            txtHoTen.setEditable(false);
        } else if ("GiaoVien".equalsIgnoreCase(vaiTro)) {
            btnThem.setVisible(true);
            btnSua.setVisible(true);
            btnXoa.setVisible(false); // Giáo viên không được xóa
        } else {
            // Admin: Hiện tất cả
            btnThem.setVisible(true);
            btnSua.setVisible(true);
            btnXoa.setVisible(true);
        }
    }

    // SỬA LỖI LỌC DỮ LIỆU TẠI ĐÂY
    private void napDuLieu(String query) {
        model.setRowCount(0);
        List<HocSinh> ds;
        
        // Lấy ID người dùng đang đăng nhập từ SharedData
        String userId = SharedData.nguoiDungDangNhap.getTenDangNhap();

        if (query.isEmpty()) {
            // Gọi Service với đúng Vaitro và UserId của người đang dùng máy
            ds = hocSinhService.getHocSinhByRole(this.vaiTro, userId);
        } else {
            ds = hocSinhService.findByName(query);
        }

        for (HocSinh hs : ds) {
            model.addRow(new Object[]{
                hs.getMaHocSinh(), hs.getTenHocSinh(), hs.getNamSinh(),
                hs.tinhTuoi(), hs.getGioiTinh(), hs.getMaLop(), hs.getMaPH()
            });
        }
    }

    private void themHocSinh() {
        try {
            HocSinh hs = new HocSinh(
                txtMaHS.getText().trim(), txtHoTen.getText().trim(),
                Integer.parseInt(txtNamSinh.getText().trim()),
                cboGioiTinh.getSelectedItem().toString(),
                txtMaLop.getText().trim(), txtMaPH.getText().trim()
            );

            if (hocSinhService.themHocSinh(hs)) {
                JOptionPane.showMessageDialog(this, "Thêm học sinh thành công!");
                napDuLieu("");
                clearFields();
            } else {
                int tuoi = hs.tinhTuoi();
                String msg = (tuoi < 3 || tuoi > 5) ? "Lỗi: Bé " + tuoi + " tuổi (Chỉ nhận 3-5 tuổi)" : "Lỗi trùng mã/CSDL";
                JOptionPane.showMessageDialog(this, msg, "Lỗi Nghiệp Vụ", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    private void clearFields() {
        txtMaHS.setText(""); txtHoTen.setText(""); txtNamSinh.setText("");
        txtMaLop.setText(""); txtMaPH.setText(""); txtSearch.setText("");
    }
}