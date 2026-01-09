package com.qlmn.service;

import com.qlmn.model.LopHoc;
import com.qlmn.util.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**

* Unit test cho class LopHocService.
* Dùng SQLite in-memory database để test CRUD.
  */
  public class LopHocServiceTest {

  private static LopHocService service;
  

  @BeforeAll
  public static void setUpClass() throws Exception {
  Connection conn = DBConnection.getConnection();
  Statement stmt = conn.createStatement();

  
   // Tạo bảng LopHoc tạm
   stmt.execute("CREATE TABLE IF NOT EXISTS LopHoc (" +
           "MaLop TEXT PRIMARY KEY, " +
           "TenLop TEXT, " +
           "Khoi TEXT, " +
           "SiSo INTEGER, " +
           "MaGV TEXT)");

   stmt.close();
   conn.close();

   service = new LopHocService();
  

  }

  @BeforeEach
  public void setUp() throws Exception {
  // Xoá sạch dữ liệu trước mỗi test
  Connection conn = DBConnection.getConnection();
  Statement stmt = conn.createStatement();
  stmt.execute("DELETE FROM LopHoc");
  stmt.close();
  conn.close();
  }

  @Test
  public void testThemLopHoc() {
  LopHoc lop = new LopHoc("L01", "Lớp 1A", "1", 40, "GV01");
  boolean result = service.themLopHoc(lop);
  assertTrue(result, "Them lop hoc phai thanh cong");

  
   List<LopHoc> ds = service.getAllLopHoc();
   assertEquals(1, ds.size());
   assertEquals("Lớp 1A", ds.get(0).getTenLop());
  

  }

  @Test
  public void testGetAllLopHoc() {
  service.themLopHoc(new LopHoc("L01", "Lớp 1A", "1", 40, "GV01"));
  service.themLopHoc(new LopHoc("L02", "Lớp 2A", "2", 45, "GV02"));

  
   List<LopHoc> ds = service.getAllLopHoc();
   assertEquals(2, ds.size());
  

  }

  @Test
  public void testCapNhatLopHoc() {
  service.themLopHoc(new LopHoc("L01", "Lớp 1A", "1", 40, "GV01"));

  
   LopHoc lopSua = new LopHoc("L01", "Lớp 1B", "1", 50, "GV03");
   boolean result = service.capNhatLopHoc(lopSua);
   assertTrue(result, "Cap nhat lop hoc phai thanh cong");

   List<LopHoc> ds = service.getAllLopHoc();
   LopHoc lop = ds.get(0);
   assertEquals("Lớp 1B", lop.getTenLop());
   assertEquals(50, lop.getSiSo());
   assertEquals("GV03", lop.getMaGV());
  

  }

  @Test
  public void testXoaLopHoc() {
  service.themLopHoc(new LopHoc("L01", "Lớp 1A", "1", 40, "GV01"));
  boolean result = service.xoaLopHoc("L01");
  assertTrue(result, "Xoa lop hoc phai thanh cong");

  
   List<LopHoc> ds = service.getAllLopHoc();
   assertTrue(ds.isEmpty(), "Danh sach lop phai rong sau khi xoa");
  

  }
  }
