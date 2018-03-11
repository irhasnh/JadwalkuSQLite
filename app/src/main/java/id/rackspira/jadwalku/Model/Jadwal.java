package id.rackspira.jadwalku.Model;

/*
 * Created by kikiosha on 3/5/18.
 */

public class Jadwal {
    private String id;
    private String namaMakul;
    private String dosen;
    private String ruang;
    private String jamMulai;
    private String jamSelesai;
    private String hari;

    public Jadwal() {
    }

    public Jadwal(String id, String namaMakul, String dosen, String ruang, String jamMulai, String jamSelesai, String hari) {
        this.id = id;
        this.namaMakul = namaMakul;
        this.dosen = dosen;
        this.ruang = ruang;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.hari = hari;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaMakul() {
        return namaMakul;
    }

    public void setNamaMakul(String namaMakul) {
        this.namaMakul = namaMakul;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
