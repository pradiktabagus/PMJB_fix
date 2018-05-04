package pengaduan.pmjb.pengaduan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import pengaduan.pmjb.ApiVolley;
import pengaduan.pmjb.MainActivity;
import pengaduan.pmjb.Manifest;
import pengaduan.pmjb.R;
import pengaduan.pmjb.aduan.AduanActivity;
import pengaduan.pmjb.aduan.aduan;

public class FormMengaduActivity extends AppCompatActivity {
    ImageButton imageButtonBack, imageButtonPerluPerbaikan, imageButtonMengkhawatirkan;
    TextView Nama,telp,alamat,textViewdetail;
    String nama,telepon,almt,uri;
    int kondisi;
    Double longi,lat;
    Button btnPilihGambar, btPlaceAPI;
    ImageView imageViewPilihGambar;
    private int PLACE_PICKER_REQUEST = 1;
    final int CODE_GALLERY_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formmengadu);
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        imageButtonPerluPerbaikan = (ImageButton)findViewById(R.id.perluPerbaikan);
        imageButtonMengkhawatirkan = (ImageButton)findViewById(R.id.menghawatirkan);

        imageButtonPerluPerbaikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonPerluPerbaikan.setBackground(getResources().getDrawable(R.drawable.rectanglebackgroundpress));
                imageButtonMengkhawatirkan.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
                kondisi=0;
            }
        });
        imageButtonMengkhawatirkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonPerluPerbaikan.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
                imageButtonMengkhawatirkan.setBackground(getResources().getDrawable(R.drawable.rectanglebackgroundpress));
                kondisi=1;
            }
        });
        imageButtonBack = (ImageButton)findViewById(R.id.back_mengadu);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormMengaduActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
        btnPilihGambar = (Button)findViewById(R.id.upload_btn);
        imageViewPilihGambar = (ImageView)findViewById(R.id.img_upload);
        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        FormMengaduActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });
        textViewdetail = (TextView)findViewById(R.id.lokasi_detail);
        btPlaceAPI = (Button)findViewById(R.id.mark_lokasi);
        btPlaceAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(FormMengaduActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageView save=(ImageView)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final JSONObject json= new JSONObject();
                try{
                    json.put("latitude",lat);
                    json.put("longtitude",longi);
                    json.put("id_firebase",currentFirebaseUser.getUid());
                    json.put("img",uri);
                    json.put("kondisi",kondisi);

                    //Log.d("jason",json.toString());
                    //Toast.makeText(this, "succes make jsonedit profile", Toast.LENGTH_SHORT).show();
                } catch (JSONException e){
                    e.printStackTrace();
                }

                ApiVolley req = new ApiVolley(FormMengaduActivity.this, json, "POST", "http://192.168.88.3/adminpmjb/api/pengaduan/create_pengaduan", "", "", 0, new ApiVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(FormMengaduActivity.this,"Berhasil Membuat aduan",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(String result) {
                        Toast.makeText(FormMengaduActivity.this,"gagal Membuat aduan",Toast.LENGTH_SHORT);
                    }
                });

                Intent i = new Intent(FormMengaduActivity.this, AduanActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //Toast.makeText(FormMengaduActivity.this,"Berhasil Membuat aduan",Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPilihGambar.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);  //Thos is what compresses image hence loosing bytes
                byte[] imageBytes = baos.toByteArray();
                uri = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                LatLng latlng=place.getLatLng();
                longi =latlng.longitude;
                lat=latlng.latitude;
                String toastMsg = String.format("Place ini: %s", place.getLatLng());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
