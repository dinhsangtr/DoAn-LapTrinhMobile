package com.simple.mcghealth.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;
import com.simple.mcghealth.adapters.BaiVietAdapter;
import com.simple.mcghealth.entities.BaiViet;

import java.util.ArrayList;

public class ListBaiVietActivity extends AppCompatActivity {
    BaiVietAdapter baiVietAdapter;
    ArrayList<BaiViet> baiVietArrayList;
    ListView listView;
    TextView textviewtieude;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_list_bai_viet);
        anhXa();
        addListChatBeo();
        docBaiViet();
    }

    private void docBaiViet() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(ListBaiVietActivity.this, DocbaoActivity.class);
                intent.putExtra("url", ListBaiVietActivity.this.baiVietArrayList.get(i).getUrl());
                ListBaiVietActivity.this.startActivity(intent);
            }
        });
    }

    private void anhXa() {
        this.listView = (ListView) findViewById(R.id.listviewbaiviet);
        this.baiVietArrayList = new ArrayList<>();
        this.baiVietAdapter = new BaiVietAdapter(this, R.layout.row_bai_viet, this.baiVietArrayList);
        this.listView.setAdapter(this.baiVietAdapter);
        this.textviewtieude = (TextView) findViewById(R.id.textviewtenchedobaiviet);
    }

    private void addListChatBeo() {
        switch (getIntent().getIntExtra("so", 0)) {
            case 1:
                this.textviewtieude.setText("Bài viết: Chế độ ăn tăng cân");
                this.baiVietArrayList.add(new BaiViet("Tháp dinh dưỡng cho người gầy tăng cân hiệu quả", R.drawable.protein, "https://hellobacsi.com/song-khoe/dinh-duong/thap-dinh-duong-cho-nguoi-gay/"));
                this.baiVietArrayList.add(new BaiViet("Chế độ ăn tăng cân một tuần cho người gầy", R.drawable.vitamind, "https://lifestyle.cfyc.com.vn/thuc-don-1-tuan-cho-nguoi-muon-tang-can-p2d319.html"));
                this.baiVietArrayList.add(new BaiViet("Chế độ ăn uống để tăng cân tự nhiên cho người gầy", R.drawable.chatbeo, "https://www.wheystore.vn/che-do-an-uong-de-tang-can-tu-nhien-cho-nguoi-gay.html"));
                return;
            case 2:
                this.textviewtieude.setText("Bài viết: Chế độ ăn giảm cân");
                this.baiVietArrayList.add(new BaiViet("Thực đơn giảm cân khoa học và an toàn trong một tuần", R.drawable.chatxo, "https://www.cfyc.com.vn/giam-can/thuc-don-giam-can.html"));
                this.baiVietArrayList.add(new BaiViet("Chế độ ăn kiêng giúp giảm 8 kg chỉ trong 7 ngày", R.drawable.vitamine, "https://news.zing.vn/che-do-an-kieng-giup-giam-8-kg-chi-trong-7-ngay-post682805.html"));
                this.baiVietArrayList.add(new BaiViet("Thực đơn ăn kiêng kiểu Ấn Độ", R.drawable.vitaminc, "https://vnexpress.net/suc-khoe/thuc-don-an-kieng-kieu-an-do-3964842.html"));
                return;
            case 3:
                this.textviewtieude.setText("Bài viết: Chế độ ăn đủ dưỡng chất");
                this.baiVietArrayList.add(new BaiViet("Như thế nào là chế độ dinh dưỡng hợp lí", R.drawable.protein, "https://icnm.vn/tin-tuc/17889/Dinh-duong/Nhu-the-nao-la-che-do-dinh-duong-hop-li.aspx"));
                this.baiVietArrayList.add(new BaiViet("Bữa ăn hợp lý và đủ dinh dưỡng tại gia đình", R.drawable.haisan, "http://viendinhduong.vn/vi/tin-tuc---su-kien-noi-bat/bua-an-hop-ly-va-du-dinh-duong-tai-gia-dinh.html"));
                this.baiVietArrayList.add(new BaiViet("Tháp dinh dưỡng cân đối – bí quyết vàng cho sức khỏe dẻo dai", R.drawable.kem, "https://hellobacsi.com/song-khoe/dinh-duong/thap-dinh-duong/"));
                return;
            case 4:
                this.textviewtieude.setText("Bài viết: Chế độ ăn người béo phì");
                this.baiVietArrayList.add(new BaiViet("Chế độ ăn uống cho người bệnh béo phì", R.drawable.protein, "https://omron-yte.com.vn/18316-che-do-an-uong-cho-nguoi-benh-beo-phi/"));
                this.baiVietArrayList.add(new BaiViet("Thực đơn cho người béo phì", R.drawable.vitamina, "https://suckhoedoisong.vn/thuc-don-cho-nguoi-beo-phi-n122057.html"));
                this.baiVietArrayList.add(new BaiViet("Thực đơn cho người béo phì giảm cân lành mạnh", R.drawable.carot, "https://hellobacsi.com/chuyen-de/beo-phi/thuc-don-cho-nguoi-beo-phi/"));
                return;
            case 5:
                this.textviewtieude.setText("Bài viết: Chế độ người tiểu đường");
                this.baiVietArrayList.add(new BaiViet("Thực đơn dành cho người tiểu đường", R.drawable.vitamind, "https://kienthuctieuduong.vn/thuc-don-danh-cho-nguoi-tieu-duong/"));
                this.baiVietArrayList.add(new BaiViet("Người bị bệnh tiểu đường nên ăn gì và nên kiêng gì?", R.drawable.protein, "https://www.vinmec.com/vi/tin-tuc/thong-tin-suc-khoe/nguoi-bi-benh-tieu-duong-nen-an-gi-va-nen-kieng-gi/"));
                this.baiVietArrayList.add(new BaiViet("Chế độ ăn cho người bị tiểu đường", R.drawable.haisan, "http://viendinhduong.vn/vi/dinh-duong-tiet-che/che-do-an-cho-nguoi-bi-tieu-duong.html"));
                return;
            default:
                return;
        }
    }
}
