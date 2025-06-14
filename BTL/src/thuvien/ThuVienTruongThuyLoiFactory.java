package thuvien;

public class ThuVienTruongThuyLoiFactory extends ThuVienFactory {
    public ThuVienTruongThuyLoiFactory() {
        this.tenThuVien = "Thư viện Trường Thủy Lợi";
    }

    @Override
    public ThuVien createThuVien() {
        return  new ThuVienTruongThuyLoi();
    }
}