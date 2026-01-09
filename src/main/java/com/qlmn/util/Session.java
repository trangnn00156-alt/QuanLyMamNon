package com.qlmn.util;

/**
 * Lưu thông tin người dùng đang đăng nhập.
 * Dùng biến static để có thể truy cập ở mọi nơi trong chương trình.
 */
public class Session {
    public static String username;  // Tên đăng nhập hoặc họ tên
    public static String role;      // Vai trò (Admin, GiaoVien, PhuHuynh)
    public static String maPH;      // Mã phụ huynh (nếu đăng nhập là phụ huynh)

    // Gán giá trị khi đăng nhập
    public static void set(String user, String roleName, String maPhuHuynh) {
        username = user;
        role = roleName;
        maPH = maPhuHuynh;
    }

    // Xóa thông tin khi đăng xuất
    public static void clear() {
        username = null;
        role = null;
        maPH = null;
    }
}
