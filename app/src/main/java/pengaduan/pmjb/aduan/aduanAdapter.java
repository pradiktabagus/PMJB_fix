package pengaduan.pmjb.aduan;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pengaduan.pmjb.R;

/**
 * Created by dikta on 26/10/2017.
 */

public class aduanAdapter extends BaseAdapter {
    private Context mContext;
    private List<aduan> aduanList;
    public String address;
    public Integer[]mThumbIds = {
            R.drawable.jajal
    };
    //Constructor
    public aduanAdapter(Context c, List<aduan> adlist){
        mContext = c;
        this.aduanList = adlist;
    }
    @Override
    public int getCount() {
        return aduanList.size();
    }

    @Override
    public Object getItem(int position) {
        aduanList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        TextView textViewLokasi, textViewKondisiJalan, textViewWaktu, textViewProgress;
        ImageView imageViewAduan;
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        List<Address> addresses;
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        final aduan a = aduanList.get(position);

        try {
            addresses = geocoder.getFromLocation(a.getLatitude(), a.getLongtitude(), 1);
             address = addresses.get(0).getAddressLine(0);
        }catch(IOException ex) {
            //Do something with the exception
        }



        if (convertView == null){
            grid = new ListView(mContext);
            grid = inflater.inflate(R.layout.list_aduan, null);
            String gbr = "http://192.168.88.3/adminpmjb/assets/upload/"+a.getImg();
            textViewLokasi = (TextView) grid.findViewById(R.id.nama_lokasi);
            ImageView img = (ImageView) grid.findViewById(R.id.img_thumbnail);
            textViewKondisiJalan = (TextView) grid.findViewById(R.id.kondisi_jalan);
            textViewWaktu = (TextView)grid.findViewById(R.id.waktuaduan);
            textViewProgress = (TextView) grid.findViewById(R.id.progressaduan);
            imageViewAduan = (ImageView) grid.findViewById(R.id.img_thumbnail);

            String hari=a.getwaktu().substring(8,10);
            String bulan=a.getwaktu().substring(5,7);
            String tahun=a.getwaktu().substring(0,4);
            int bl=Integer.parseInt(bulan);
            if(bl==1){bulan="Januari";}
            if(bl==2){bulan="Februari";}
            if(bl==3){bulan="Maret";}
            if(bl==4){bulan="April";}
            if(bl==5){bulan="Mei";}
            if(bl==6){bulan="Juni";}
            if(bl==7){bulan="Juli";}
            if(bl==8){bulan="Agustus";}
            if(bl==9){bulan="September";}
            if(bl==10){bulan="Oktober";}
            if(bl==11){bulan="November";}
            if(bl==12){bulan="Desember";}

            textViewLokasi.setText(address);
            if(a.getkondisi()==0){
                textViewKondisiJalan.setText("Perlu Perbaikan");
            }else{
                textViewKondisiJalan.setText("Mengkhawatirkan");
            }

            if(a.getstatus()==0){
                textViewProgress.setText("UNPROGRESS");
            }else if (a.getstatus()==1){
                textViewProgress.setText("Sedang Dikerjakan");
            }else {
                textViewProgress.setText("Selesai Dikerjakan");
            }

            textViewWaktu.setText(hari+" "+bulan+" "+tahun);
            Picasso.with(mContext)
                    .load(gbr)
                    .placeholder(R.drawable.background_splash) // optional
                    .error(R.drawable.addimage)         // optional
                    .into(img);
        }else {
            grid = (View) convertView;
        }
        return grid;
    }
}
