package com.qlmn.service;

import com.qlmn.util.SharedData;
import javax.swing.JButton;

public class AuthService {

    /**
     * Hàm phân quyền: Ẩn/Hiện nút dựa trên vai trò của người dùng
     * @param buttons Danh sách các nút chỉ dành cho Admin (Thêm, Sửa, Xóa)
     */
    public static void phanQuyenButtons(JButton... buttons) {
        // Kiểm tra xem đã đăng nhập chưa
        if (SharedData.nguoiDungDangNhap == null) {
            for (JButton btn : buttons) btn.setVisible(false);
            return;
        }

        // Lấy vai trò từ thông tin đã lưu lúc đăng nhập
        String vaiTro = SharedData.nguoiDungDangNhap.getVaiTro();
        boolean isAdmin = "Admin".equalsIgnoreCase(vaiTro);

        // Thực hiện phân quyền
        for (JButton btn : buttons) {
            if (btn != null) {
                btn.setVisible(isAdmin); // Nếu là Admin thì hiện, ngược lại thì ẩn
            }
        }
    }
}