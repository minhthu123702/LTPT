package thuvien;

public class Sach {
    private String maSach, tenSach, tacGia, nhaXuatBan;
    private int namXuatBan;
    private double gia;

    public Sach(String maSach, String tenSach, String tacGia, String nhaXuatBan, int namXuatBan, double gia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.gia = gia;
    }

    public String getMaSach() { return maSach; }
    public String getTenSach() { return tenSach; }
    public String getTacGia() { return tacGia; }
    public String getNhaXuatBan() { return nhaXuatBan; }
    public int getNamXuatBan() { return namXuatBan; }
    public double getGia() { return gia; }
}