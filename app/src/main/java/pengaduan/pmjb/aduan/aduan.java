package pengaduan.pmjb.aduan;

import java.util.Date;

/**
 * Created by dikta on 26/10/2017.
 */

public class aduan {
   private String id_pengaduan,img, detail_lokasi, id_firebase,waktu;

   int kondisi,status;
   Double latitude,longtitude;
   public aduan(){

   }
   public aduan(String id_pengaduan, Double latitude, String img, Double longtitude, String detail_lokasi, String id_firebase,int kondisi,int status,String waktu){
       this.longtitude = longtitude;
       this.id_pengaduan = id_pengaduan;
       this.latitude = latitude;
       this.img = img;
       this.detail_lokasi = detail_lokasi;
       this.id_firebase = id_firebase;
       this.kondisi=kondisi;
       this.waktu=waktu;
       this.status=status;
   }
   public Double getLongtitude(){
       return longtitude;
   }
    public void setLongtitude(Double longtitude){
        this.longtitude = longtitude;
    }
    public String getId_pengaduan(){

        return id_pengaduan;
    }
    public void setId_pengaduan(String id_pengaduan){
        this.id_pengaduan = id_pengaduan;
    }
    public Double getLatitude(){
        return latitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    public String getImg(){
        return img;
    }
    public void setImg(String img){
        this.img = img;
    }

    public String getDetail_lokasi() {
        return detail_lokasi;
    }

    public void setDetail_lokasi(String detail_lokasi) {
        this.detail_lokasi = detail_lokasi;
    }

    public String getId_firebase() {
        return id_firebase;
    }

    public int getkondisi() {return kondisi;}
    public void setkondisi(int kondisi) {this.kondisi=kondisi;}

    public String getwaktu(){return waktu;}
    public void setwaktu(String waktu){this.waktu=waktu;}

    public int getstatus(){return status;}
    public void setStatus(int status){this.status=status;}

    public void setId_firebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

}
