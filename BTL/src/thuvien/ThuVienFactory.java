package thuvien;

public abstract class ThuVienFactory {
    protected String tenThuVien;
    protected String diaChi;

    public abstract ThuVien createThuVien();
}
