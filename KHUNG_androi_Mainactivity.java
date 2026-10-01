package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    // =========================
    // 1. KHAI BÁO VIEW
    // =========================

    EditText editA, editB;

    Button btn1, btn2;

    TextView txtKetQua;


    // =========================
    // 2. onCreate
    // =========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Nạp giao diện
        setContentView(R.layout.activity_main);

        // Ánh xạ View
        getWidget();

        // Bắt sự kiện
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }


    // =========================
    // 3. ÁNH XẠ VIEW
    // =========================

    private void getWidget() {

        editA = findViewById(R.id.editA);
        editB = findViewById(R.id.editB);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        txtKetQua = findViewById(R.id.txtKetQua);
    }


    // =========================
    // 4. XỬ LÝ CLICK
    // =========================

    @Override
    public void onClick(View v) {

        try {

            float a = Float.parseFloat(
                    editA.getText().toString()
            );

            float b = Float.parseFloat(
                    editB.getText().toString()
            );


            if (v == btn1) {

                float kq = a + b;

                txtKetQua.setText(
                        "Kết quả: " + kq
                );

                Toast.makeText(
                        this,
                        "Đã thực hiện",
                        Toast.LENGTH_SHORT
                ).show();

            }

            else if (v == btn2) {

                float kq = a - b;

                txtKetQua.setText(
                        "Kết quả: " + kq
                );

                Toast.makeText(
                        this,
                        "Đã thực hiện",
                        Toast.LENGTH_SHORT
                ).show();
            }

        }

        catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Vui lòng nhập dữ liệu hợp lệ",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
