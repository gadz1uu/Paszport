package com.example.paszport;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Paszport";

    private EditText etNumer, etImie, etNazwisko;

    private RadioGroup rgKolorOczu;

    private ImageView ivZdjecie, ivOdcisk;

    private Button btnOk;

    private TextView tvMessage;

    private PassportValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNumer = findViewById(R.id.etNumer);
        etImie = findViewById(R.id.etImie);
        etNazwisko = findViewById(R.id.etNazwisko);
        rgKolorOczu = findViewById(R.id.rgKolorOczu);
        ivZdjecie = findViewById(R.id.ivZdjecie);
        ivOdcisk = findViewById(R.id.ivOdcisk);
        btnOk = findViewById(R.id.btnOk);
        tvMessage = findViewById(R.id.tvMessage);

        validator = new PassportValidator();

        showImages("000");

        etNumer.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                showImages(etNumer.getText().toString().trim());
            }
        });

        btnOk.setOnClickListener(v -> handleOk());
    }

    private void handleOk() {
        String imie = etImie.getText().toString();
        String nazwisko = etNazwisko.getText().toString();
        RadioButton rbChecked = findViewById(rgKolorOczu.getCheckedRadioButtonId());
        String kolorOczu = rbChecked.getText().toString();

        String message;
        if (!validator.isDataComplete(imie, nazwisko)) {
            message = "Wprowadź dane";
        } else {
            message = validator.buildMessage(imie.trim(), nazwisko.trim(), kolorOczu);
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.d(TAG, message);
        tvMessage.setText(message);
    }

    private void showImages(String numer) {
        setImage(ivZdjecie, validator.getPhotoFileName(numer));
        setImage(ivOdcisk, validator.getFingerprintFileName(numer));
    }

    private void setImage(ImageView imageView, String fileName) {
        try (InputStream stream = getAssets().open(fileName)) {
            imageView.setImageBitmap(BitmapFactory.decodeStream(stream));
        } catch (IOException e) {
            imageView.setImageDrawable(null);
        }
    }
}
