package thuvien;

public class ThuVienSoDaiHocThuyLoiFactory extends ThuVienFactory {
    public ThuVienSoDaiHocThuyLoiFactory() {
        this.tenThuVien = "Thư viện số Đại học Thủy Lợi";
      
    }

    @Override
    public ThuVien createThuVien() {
        return new ThuVienSoDaiHocThuyLoi();
    }
}
