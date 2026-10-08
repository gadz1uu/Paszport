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

/**
 * Ekran do wprowadzania danych do paszportu.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Paszport";

    private static final String NUMER_POCZATKOWY = "000";

    private EditText etNumer;
    private EditText etImie;
    private EditText etNazwisko;
    private RadioGroup rgKolorOczu;
    private ImageView ivZdjecie;
    private ImageView ivOdcisk;
    private Button btnOk;
    private TextView tvMessage;

    private PassportValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupWindowInsets();
        findViews();

        validator = new PassportValidator();

        showImages(NUMER_POCZATKOWY);

        etNumer.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                showImages(etNumer.getText().toString().trim());
            }
        });

        btnOk.setOnClickListener(view -> showMessage());
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void findViews() {
        etNumer = findViewById(R.id.etNumer);
        etImie = findViewById(R.id.etImie);
        etNazwisko = findViewById(R.id.etNazwisko);
        rgKolorOczu = findViewById(R.id.rgKolorOczu);
        ivZdjecie = findViewById(R.id.ivZdjecie);
        ivOdcisk = findViewById(R.id.ivOdcisk);
        btnOk = findViewById(R.id.btnOk);
        tvMessage = findViewById(R.id.tvMessage);
    }


    private void showMessage() {
        String imie = etImie.getText().toString();
        String nazwisko = etNazwisko.getText().toString();
        String message;

        if (validator.isDataComplete(imie, nazwisko)) {
            message = validator.buildMessage(imie.trim(), nazwisko.trim(), getEyeColor());
        } else {
            message = getString(R.string.wprowadz_dane);
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.d(TAG, message);
        tvMessage.setText(message);
    }

    private String getEyeColor() {
        RadioButton checkedButton = findViewById(rgKolorOczu.getCheckedRadioButtonId());
        return checkedButton.getText().toString();
    }

    private void showImages(String numer) {
        setImage(ivZdjecie, validator.getPhotoFileName(numer));
        setImage(ivOdcisk, validator.getFingerprintFileName(numer));
    }


    private void setImage(ImageView imageView, String fileName) {
        try (InputStream stream = getAssets().open(fileName)) {
            imageView.setImageBitmap(BitmapFactory.decodeStream(stream));
        } catch (IOException e) {
            Log.d(TAG, "Brak pliku: " + fileName);
            imageView.setImageDrawable(null);
        }
    }
}
