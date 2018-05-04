package pengaduan.pmjb;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pengaduan.pmjb.aduan.AduanActivity;
import pengaduan.pmjb.pengaduan.FormMengaduActivity;
import pengaduan.pmjb.pengaduan.maps.MapsActivity;
import pengaduan.pmjb.profil.ProfilActivity;

public class MainActivity extends AppCompatActivity {
    private CardView imageButtonLogout;
    Dialog dialog;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    TextView textView;
    CardView imageButtonMengadu, imageButtonAduan, imageButtonProfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //popup
        dialog = new Dialog(this);
        //font selamat datang
        textView = (TextView)findViewById(R.id.welcome);
        Typeface quicksandregular = Typeface.createFromAsset(getAssets(),"font/Quicksand-Regular.otf");
        textView.setTypeface(quicksandregular);
        //font diPMJB
        textView = (TextView)findViewById(R.id.spm);
        Typeface quicksandbold = Typeface.createFromAsset(getAssets(),"font/Quicksand-Bold.otf");
        textView.setTypeface(quicksandbold);
        //font profil
        textView = (TextView)findViewById(R.id.username);
        Typeface quicksandlightuser = Typeface.createFromAsset(getAssets(),"font/Quicksand-Regular.otf");
        textView.setTypeface(quicksandlightuser);
        //font pengaduan
        textView = (TextView)findViewById(R.id.mengadu);
        Typeface quicksandlightmengadu = Typeface.createFromAsset(getAssets(),"font/Quicksand-Regular.otf");
        textView.setTypeface(quicksandlightmengadu);
        //font recent
        textView = (TextView)findViewById(R.id.recent);
        Typeface quicksandlightrecent = Typeface.createFromAsset(getAssets(),"font/Quicksand-Regular.otf");
        textView.setTypeface(quicksandlightrecent);
        //font keluar
        textView = (TextView)findViewById(R.id.keluar);
        Typeface quicksandlightkeluar = Typeface.createFromAsset(getAssets(),"font/Quicksand-Regular.otf");
        textView.setTypeface(quicksandlightkeluar);
        //intent profil
        imageButtonProfil = (CardView)findViewById(R.id.user);
        imageButtonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ProfilActivity.class);
                startActivity(intent);
            }
        });
        //intent mengadu
        imageButtonMengadu = (CardView) findViewById(R.id.pengaduan);
        imageButtonMengadu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        FormMengaduActivity.class);
                startActivity(intent);
            }
        });

        //intent aduan
        imageButtonAduan = (CardView) findViewById(R.id.history);
        imageButtonAduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        AduanActivity.class);
                startActivity(intent);
            }
        });
        //logout button
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };
        imageButtonLogout = (CardView) findViewById(R.id.logout);
        imageButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }
}
