package com.simple.mcghealth.activities;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.NguonDinhDuong;
import com.simple.mcghealth.adapters.NguonDinhDuongAdapter;

import java.util.ArrayList;
import java.util.Random;

public class NDDuongActivity extends AppCompatActivity {
    LinearLayout LNTT;
    Boolean LinVisibily;
    ArrayList<NguonDinhDuong> NguonDinhDuongArrayList;
    NguonDinhDuongAdapter adapter;
    ImageView imgAn;
    LinearLayout linBackgr;
    ListView listView;
    TextView txtLieuLuong;
    TextView txtLoiIch;
    TextView txtNguon;
    TextView txtTenNdd;
    TextView txtThongTin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_ndd);
        anhXa();
        addNDD();
        docNguonDinhDuong();
    }

    private void anhXa() {
        this.listView = (ListView) findViewById(R.id.listviewndd);
        this.LNTT = (LinearLayout) findViewById(R.id.linearthongtin);
        this.LNTT.setVisibility(4);
        this.LinVisibily = false;
        this.txtTenNdd = (TextView) findViewById(R.id.textviewtennddtt);
        this.txtNguon = (TextView) findViewById(R.id.textviewnguontt);
        this.txtThongTin = (TextView) findViewById(R.id.textviewthongtintt);
        this.txtLoiIch = (TextView) findViewById(R.id.textviewloiichtt);
        this.txtLieuLuong = (TextView) findViewById(R.id.textviewlieuluongtt);
        this.imgAn = (ImageView) findViewById(R.id.imageviewanttt);
        this.linBackgr = (LinearLayout) findViewById(R.id.linearlyaoutbackground);
        this.NguonDinhDuongArrayList = new ArrayList<>();
        this.adapter = new NguonDinhDuongAdapter(this, R.layout.row_nguondinhduong, this.NguonDinhDuongArrayList);
        this.listView.setAdapter(this.adapter);
    }

    private void docNguonDinhDuong() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                NDDuongActivity.this.LNTT.setVisibility(0);
                NDDuongActivity.this.LinVisibily = true;
                NDDuongActivity.this.LNTT.setAnimation(AnimationUtils.loadAnimation(NDDuongActivity.this, R.anim.anim2));
                switch (new Random().nextInt(3)) {
                    case 0:
                        NDDuongActivity.this.linBackgr.setBackgroundResource(R.drawable.hinhnen1);
                        break;
                    case 1:
                        NDDuongActivity.this.linBackgr.setBackgroundResource(R.drawable.hinhnen2);
                        break;
                    case 2:
                        NDDuongActivity.this.linBackgr.setBackgroundResource(R.drawable.hinhnen3);
                        break;
                    case 3:
                        NDDuongActivity.this.linBackgr.setBackgroundResource(R.drawable.hinhnen4);
                        break;
                }
                NDDuongActivity.this.txtNguon.setBackground((Drawable) null);
                NDDuongActivity.this.txtLieuLuong.setBackgroundColor(-1);
                NDDuongActivity.this.txtLoiIch.setBackgroundColor(-1);
                NDDuongActivity.this.txtThongTin.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getNguon());
                NDDuongActivity.this.txtTenNdd.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getTieude());
                NDDuongActivity.this.txtThongTin.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getNguon());
                NDDuongActivity.this.txtNguon.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        NDDuongActivity.this.txtNguon.setBackground((Drawable) null);
                        NDDuongActivity.this.txtLieuLuong.setBackgroundColor(-1);
                        NDDuongActivity.this.txtLoiIch.setBackgroundColor(-1);
                        NDDuongActivity.this.txtThongTin.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getNguon());
                    }
                });
                NDDuongActivity.this.txtLoiIch.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        NDDuongActivity.this.txtLoiIch.setBackground((Drawable) null);
                        NDDuongActivity.this.txtLieuLuong.setBackgroundColor(-1);
                        NDDuongActivity.this.txtNguon.setBackgroundColor(-1);
                        NDDuongActivity.this.txtThongTin.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getLoiich());
                    }
                });
                NDDuongActivity.this.txtLieuLuong.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        NDDuongActivity.this.txtLieuLuong.setBackground((Drawable) null);
                        NDDuongActivity.this.txtNguon.setBackgroundColor(-1);
                        NDDuongActivity.this.txtLoiIch.setBackgroundColor(-1);
                        NDDuongActivity.this.txtThongTin.setText(NDDuongActivity.this.NguonDinhDuongArrayList.get(i).getLieuLuong());
                    }
                });
            }
        });
        this.imgAn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NDDuongActivity.this.LNTT.setAnimation(AnimationUtils.loadAnimation(NDDuongActivity.this, R.anim.anim));
                NDDuongActivity.this.LNTT.setVisibility(4);
                NDDuongActivity.this.LinVisibily = false;
            }
        });
        this.txtThongTin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.txtTenNdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    private void addNDD() {
        this.NguonDinhDuongArrayList.clear();
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Protein", R.drawable.protein, "Trứng, thịt, cá, sữa và các sản phẩm từ sữa, quả bơ, các loại hạt, ngủ cốc, đậu nành...", "Cung cấp axit amin không thay thế, là đơn vị cấu trúc cơ thể, cơ bắp…cung cấp khoảng 20% năng lượng, duy trì một cơ thể khỏe mạnh.", "- 0.75 g/kg cho phụ nữ trưởng thành\n- 0.84 g/kg cho nam trường thành\n- ~1g/kg cho phụ nữ mang thai và cho con bú, hoặc người già trên 70."));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Carbohydrate",R.drawable.carbs, "- Tinh bột, chất xơ, pectin và glycogen (bánh mì, đậu, ngũ cốc, mì ống, khoai tây, rau, quả hạch).\n- Glucose, fructose, galactose, sucrose và maltose (trái cây, sữa, kẹo, bánh ngọt, bia, đồ ăn nhanh).\n", "Cung cấp 45-65% calo  năng lượng cho cơ thể.\nCải thiện tiêu hóa (nhờ pectin, chất xơ).\nGiúp tăng trưởng cơ bắp (nhờ glycogen).\nGây ra sự thay đổi đường huyết, quá trình trao đổi chất, cân nặng.\n", "- Duy trì cân nặng: 100-150 gam carbs mỗi ngày.\n- Giảm cân: 50-100 gam carbs mỗi ngày.\n- Giảm cân nhanh: 20-50gam carbs mỗi ngày.\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Chất béo", R.drawable.chatbeo, "Nguồn gốc động vật như mỡ (mỡ thịt, mỡ cá), sữa và chất béo có nguồn gốc thực vật như dầu (dầu có trong các loại hạt, loại quả)\nchất béo chuyển hoá có trong các thực phẩm chế biến sẵn (chiên rán, pizza..)\n", "Nguồn sinh năng lượng quan trọng, là dung môi tốt để hòa tan các vitamin, là thành phần cấu tạo màng tế bào, các tổ chức liên kết. Giúp cho sự phát triển sớm về trí tuệ và thể lực của trẻ em, chất béo có hương vị thơm ngon tạo cảm giác ngon miệng.", "Phụ thuộc vào mục tiêu về calo của bạn với\nChế độ chất béo vừa phải:\n1,500 calo: ~ 58–67 gam chất béo mỗi ngày.\n2,000 calo: ~78–89 gam chất béo mỗi ngày.\n2,500 calo: ~97–111 gam chất béo mỗi ngày.\n\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin A", R.drawable.vitamina, "Sữa, trứng, rau quả có màu tối, màu cam, màu xanh (carot, khoai lang, cải xoăn), cam.", "Tốt cho mắt và cho sự phát triển cơ thể bình thường, tốt cho hệ miễn dịch và sự khỏe mạnh của răng, da.", "Nam: 900 microgam/ngày\nNữ:  700 microgam/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin B", R.drawable.vitaminb, "Có  ở thức ăn chưa qua chế biến, tất cả các loại hạt, khoai tây, đậu hà lan, chuối, ớt, nấm, và mật mía.", "Đóng vai trò quan trọng trong quá trình năng lượng, chức năng miễn dịch và hấp thụ sắt.", "Nam: 15mg/ngày\nNữ: 12mg/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin C", R.drawable.vitaminc, "Trái cây có múi, dâu tây, kiwi, ổi, cà chua. ớt, bông cải xanh, rau bina", "Tác dụng có lợi cho\nxương, răng, da, hấp thụ sắt. Hỗ trợ chữa nhanh lành viết thương và hoạt định chính xác của não bộ.\n", "Nam: 90mg/ ngày\nNữ: 75mg/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin D", R.drawable.vitamind, "Trứng, cá ngừ, cá hồi. nấm, sửa, sữa đậu nành.", "Nhu cầu không thể thiếu cho sự phát triển của xưong, hệ miễn dịch.", "Trường thành: \n15 mg/ngày.\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin E", R.drawable.vitamine, "Hạt Hướng dương, bơ, rau bina, cải xoăn, đu đủ, dầu ôliu, quả hạnh nhân.", "Chống oxi hóa, chống gốc tự do, giảm cholesterol, chăm sóc da, tóc.", "Người trưởng thành: 15 mg/ngày."));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Vitamin K", R.drawable.vitamink, "Rau quả xanh như rau bina, bông cải xanh, bắp cải, xu hào.", "Vitamin k cùng với canxi giúp xưong chắc khỏe, ngăn ngừa đông máu", "Nam: 120 microgam/ ngày\nNữ: 90 microgam/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Axit folic (B9)", R.drawable.axicfolic, "Rau quả xanh lá cây đậm, bông cải xanh, trứng, trái cây có múi, carot, bơ, cây họ đậu…", "Có vai trò quan trọng trong việc sản xuất các tế bào máu và sự phát triển của ống thần kinh. Nhu cầu về chất này tăng cao ở phụ nữ mang thai và trẻ sơ sinh", "Nam: 400 microgam/ngày\nNữ: 450 microgam/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Canxi", R.drawable.canxi, "Phô mai, sữa, sưã chua, đậu nành, cam, củ cải xanh, hạnh nhân.", "Nhu cầu thiết yếu cho sự phát triển của xương. canxi còn cần cho quá trình hoạt động của thần kinh cơ, hoạt động của tim, chuyển hoá của thế bào và quá trình đông máu.", "Nam: 1000 mg/ngày\nNữ: 1200mg/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Sắt", R.drawable.sat, "Thịt bò, đậu, rau bina, trứng, ngủ cốc. đậu lăng, con hầu, thịt gia cầm", "sự hình thành huyết sắc tố, chuyển hóa cơ thể, hoạt động của cơ bắp, chức năng não và hệ miễn dịch.", "Nam: 12mg/ ngaỳ\nNữ: 18 mg/ngaỳ\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Kẽm", R.drawable.kem, "Hầu, rau bina, đậu, chocola đen, hạt điều", "Khả năng sản sinh và phát triển của hệ miễn dịch", "Nam: 11mg/ngày\nNữ: 8mg/ngày\n"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Kali", R.drawable.kali, "Khoai tây, chuối, bơ, đậu nành, sửa chua, thịt gà, sữa, cá vàng.", "Giảm lượng đường trong máu, kiểm soát huyét áp, giúp chức năng não, phát triển xương", "Người trưởng thành: 4700 mg/ngày"));
        this.NguonDinhDuongArrayList.add(new NguonDinhDuong("Chất xơ", R.drawable.chatxo, "Yến mạch, trái cây khô, đậu, hạt ngủ cốc, rau.", "Hỗ trợ tiêu hóa, giảm cholesterol, giúp cảm giác no lâu, điều tiết lượng đường trong máu.", "Nam: 30 gam/ ngày\nNữ: 21 gam/ngày\n"));
    }

    public void onBackPressed() {
        if (this.LinVisibily.booleanValue()) {
            this.LinVisibily = false;
            this.LNTT.setVisibility(4);
            return;
        }
        super.onBackPressed();
    }
}