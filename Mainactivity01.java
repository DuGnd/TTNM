package com.example.helloproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    // =========================
    // KHAI BÁO GIAO DIỆN
    // =========================

    EditText editHoTen, editNgaySinh;

    RadioGroup rgGioiTinh;
    RadioButton rbNam, rbNu;

    CheckBox chkDocSach, chkBongDa, chkAmNhac;

    Spinner spChucVu;

    Button btnThem, btnSua, btnXoa, btnThongKe;

    ListView lvNhanVien;


    // =========================
    // DANH SÁCH NHÂN VIÊN
    // =========================

    ArrayList<String> listNhanVien =
            new ArrayList<>();

    ArrayAdapter<String> adapter;

    // Vị trí nhân viên đang được chọn
    int viTriChon = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Hiển thị giao diện XML
        setContentView(R.layout.activity_main);

        // Ánh xạ các View
        getWidget();

        // Tạo dữ liệu cho Spinner
        String[] chucVu = {
                "Nhân viên",
                "Quản lý",
                "Trưởng phòng"
        };

        // Tạo Adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        chucVu
                );

        // Giao diện danh sách Spinner
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        // Gắn Adapter vào Spinner
        spChucVu.setAdapter(spinnerAdapter);


        // =========================
        // TẠO ADAPTER CHO LISTVIEW
        // =========================

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listNhanVien
        );

        // Gắn Adapter vào ListView
        lvNhanVien.setAdapter(adapter);


        // =========================
        // BẮT SỰ KIỆN
        // =========================

        btnThem.setOnClickListener(this);
        btnSua.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
        btnThongKe.setOnClickListener(this);

        editNgaySinh.setOnClickListener(this);


        // Chọn một nhân viên trong danh sách
        lvNhanVien.setOnItemClickListener(
                (parent, view, position, id) -> {

                    viTriChon = position;

                    // Đưa dữ liệu lên Form
                    hienThiNhanVien(position);
                }
        );
    }


    // =========================
    // ÁNH XẠ VIEW
    // =========================

    private void getWidget() {

        editHoTen =
                findViewById(R.id.editHoTen);

        editNgaySinh =
                findViewById(R.id.editNgaySinh);

        rgGioiTinh =
                findViewById(R.id.rgGioiTinh);

        rbNam =
                findViewById(R.id.rbNam);

        rbNu =
                findViewById(R.id.rbNu);

        chkDocSach =
                findViewById(R.id.chkDocSach);

        chkBongDa =
                findViewById(R.id.chkBongDa);

        chkAmNhac =
                findViewById(R.id.chkAmNhac);

        spChucVu =
                findViewById(R.id.spChucVu);

        btnThem =
                findViewById(R.id.btnThem);

        btnSua =
                findViewById(R.id.btnSua);

        btnXoa =
                findViewById(R.id.btnXoa);

        btnThongKe =
                findViewById(R.id.btnThongKe);

        lvNhanVien =
                findViewById(R.id.lvNhanVien);
    }


    // =========================
    // XỬ LÝ CLICK
    // =========================

    @Override
    public void onClick(View v) {

        // Chọn ngày sinh
        if (v == editNgaySinh) {

            chonNgaySinh();
        }

        // Thêm
        else if (v == btnThem) {

            themNhanVien();
        }

        // Sửa
        else if (v == btnSua) {

            suaNhanVien();
        }

        // Xóa
        else if (v == btnXoa) {

            xoaNhanVien();
        }

        // Thống kê
        else if (v == btnThongKe) {

            thongKe();
        }
    }


    // =========================
    // CHỌN NGÀY SINH
    // =========================

    private void chonNgaySinh() {

        Calendar calendar =
                Calendar.getInstance();

        int ngay =
                calendar.get(Calendar.DAY_OF_MONTH);

        int thang =
                calendar.get(Calendar.MONTH);

        int nam =
                calendar.get(Calendar.YEAR);


        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,

                        (view, year, month, dayOfMonth) -> {

                            // month bắt đầu từ 0
                            String ngaySinh =
                                    String.format(
                                            "%02d/%02d/%04d",
                                            dayOfMonth,
                                            month + 1,
                                            year
                                    );

                            editNgaySinh.setText(
                                    ngaySinh
                            );
                        },

                        nam,
                        thang,
                        ngay
                );

        dialog.show();
    }


    // =========================
    // LẤY GIỚI TÍNH
    // =========================

    private String getGioiTinh() {

        if (rbNam.isChecked()) {

            return "Nam";
        }

        if (rbNu.isChecked()) {

            return "Nữ";
        }

        return "";
    }


    // =========================
    // LẤY SỞ THÍCH
    // =========================

    private String getSoThich() {

        String result = "";


        if (chkDocSach.isChecked()) {

            result += "Đọc sách";
        }


        if (chkBongDa.isChecked()) {

            if (!result.equals("")) {

                result += ", ";
            }

            result += "Bóng đá";
        }


        if (chkAmNhac.isChecked()) {

            if (!result.equals("")) {

                result += ", ";
            }

            result += "Âm nhạc";
        }


        if (result.equals("")) {

            result = "Không có";
        }


        return result;
    }


    // =========================
    // KIỂM TRA DỮ LIỆU
    // =========================

    private boolean kiemTraDuLieu() {

        // Kiểm tra họ tên
        if (editHoTen.getText()
                .toString()
                .trim()
                .equals("")) {

            Toast.makeText(
                    this,
                    "Vui lòng nhập họ tên!",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        // Kiểm tra ngày sinh
        if (editNgaySinh.getText()
                .toString()
                .trim()
                .equals("")) {

            Toast.makeText(
                    this,
                    "Vui lòng chọn ngày sinh!",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        // Kiểm tra giới tính
        if (getGioiTinh().equals("")) {

            Toast.makeText(
                    this,
                    "Vui lòng chọn giới tính!",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        return true;
    }


    // =========================
    // LẤY DỮ LIỆU TỪ FORM
    // =========================

    private String getDuLieu() {

        String hoTen =
                editHoTen.getText()
                        .toString()
                        .trim();

        String ngaySinh =
                editNgaySinh.getText()
                        .toString()
                        .trim();

        String gioiTinh =
                getGioiTinh();

        String soThich =
                getSoThich();

        String chucVu =
                spChucVu.getSelectedItem()
                        .toString();


        // Ghép thông tin nhân viên
        return hoTen
                + " | " + ngaySinh
                + " | " + gioiTinh
                + " | " + soThich
                + " | " + chucVu;
    }


    // =========================
    // THÊM NHÂN VIÊN
    // =========================

    private void themNhanVien() {

        if (!kiemTraDuLieu()) {

            return;
        }


        // Lấy dữ liệu từ Form
        String nhanVien =
                getDuLieu();


        // Thêm vào danh sách
        listNhanVien.add(nhanVien);


        // Cập nhật ListView
        adapter.notifyDataSetChanged();


        Toast.makeText(
                this,
                "Đã thêm nhân viên!",
                Toast.LENGTH_SHORT
        ).show();


        // Xóa Form
        xoaTrang();
    }


    // =========================
    // HIỂN THỊ NHÂN VIÊN
    // =========================

    private void hienThiNhanVien(
            int position) {

        String data =
                listNhanVien.get(position);


        // Tách chuỗi thông tin
        String[] arr =
                data.split(" \\| ");


        // Họ tên
        editHoTen.setText(arr[0]);


        // Ngày sinh
        editNgaySinh.setText(arr[1]);


        // Giới tính
        if (arr[2].equals("Nam")) {

            rbNam.setChecked(true);

        } else {

            rbNu.setChecked(true);
        }


        // Xóa trạng thái Checkbox cũ
        chkDocSach.setChecked(false);
        chkBongDa.setChecked(false);
        chkAmNhac.setChecked(false);


        // Khôi phục sở thích
        if (arr[3].contains("Đọc sách")) {

            chkDocSach.setChecked(true);
        }

        if (arr[3].contains("Bóng đá")) {

            chkBongDa.setChecked(true);
        }

        if (arr[3].contains("Âm nhạc")) {

            chkAmNhac.setChecked(true);
        }


        // Khôi phục chức vụ
        if (arr[4].equals("Nhân viên")) {

            spChucVu.setSelection(0);

        } else if (arr[4].equals("Quản lý")) {

            spChucVu.setSelection(1);

        } else {

            spChucVu.setSelection(2);
        }
    }


    // =========================
    // SỬA NHÂN VIÊN
    // =========================

    private void suaNhanVien() {

        // Chưa chọn nhân viên
        if (viTriChon == -1) {

            Toast.makeText(
                    this,
                    "Hãy chọn nhân viên cần sửa!",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // Kiểm tra dữ liệu
        if (!kiemTraDuLieu()) {

            return;
        }


        // Lấy dữ liệu mới
        String nhanVien =
                getDuLieu();


        // Thay dữ liệu cũ
        listNhanVien.set(
                viTriChon,
                nhanVien
        );


        // Cập nhật danh sách
        adapter.notifyDataSetChanged();


        Toast.makeText(
                this,
                "Đã sửa nhân viên!",
                Toast.LENGTH_SHORT
        ).show();


        xoaTrang();

        viTriChon = -1;
    }


    // =========================
    // XÓA NHÂN VIÊN
    // =========================

    private void xoaNhanVien() {

        // Chưa chọn nhân viên
        if (viTriChon == -1) {

            Toast.makeText(
                    this,
                    "Hãy chọn nhân viên cần xóa!",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // Xóa nhân viên
        listNhanVien.remove(
                viTriChon
        );


        // Cập nhật ListView
        adapter.notifyDataSetChanged();


        Toast.makeText(
                this,
                "Đã xóa nhân viên!",
                Toast.LENGTH_SHORT
        ).show();


        xoaTrang();

        viTriChon = -1;
    }


    // =========================
    // THỐNG KÊ
    // =========================

    private void thongKe() {

        int tong =
                listNhanVien.size();

        int nam = 0;
        int nu = 0;

        int nhanVien = 0;
        int quanLy = 0;
        int truongPhong = 0;


        // Duyệt danh sách
        for (String data :
                listNhanVien) {

            String[] arr =
                    data.split(" \\| ");


            // Đếm giới tính
            if (arr[2].equals("Nam")) {

                nam++;

            } else if (arr[2].equals("Nữ")) {

                nu++;
            }


            // Đếm chức vụ
            if (arr[4].equals("Nhân viên")) {

                nhanVien++;

            } else if (arr[4].equals("Quản lý")) {

                quanLy++;

            } else if (arr[4].equals("Trưởng phòng")) {

                truongPhong++;
            }
        }


        // Tạo nội dung thống kê
        String ketQua =
                "THỐNG KÊ NHÂN VIÊN"
                + "\n--------------------"
                + "\nTổng số: " + tong
                + "\nNam: " + nam
                + "\nNữ: " + nu
                + "\nNhân viên: " + nhanVien
                + "\nQuản lý: " + quanLy
                + "\nTrưởng phòng: " + truongPhong;


        // Hiển thị kết quả
        Toast.makeText(
                this,
                ketQua,
                Toast.LENGTH_LONG
        ).show();
    }


    // =========================
    // XÓA FORM
    // =========================

    private void xoaTrang() {

        editHoTen.setText("");

        editNgaySinh.setText("");

        rgGioiTinh.clearCheck();

        chkDocSach.setChecked(false);

        chkBongDa.setChecked(false);

        chkAmNhac.setChecked(false);

        spChucVu.setSelection(0);
    }
}
