package com.simple.mcghealth.entities;

import java.util.ArrayList;
import java.util.List;

public class practice {
    private String name;
    private int id;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public practice() {
    }
    public practice(int id, String name, String content) {
        this.id = id;
        this.name = name;

        this.content = content;
    }
    public static List<practice> lisTTD (){
        List<practice> practices = new ArrayList<>();
        practices.add(new practice(1,"Chạy bộ","Chạy bộ giúp giảm trầm cảm và cải thiện tâm trạng căng thẳng \n" +
                "Theo một nghiên cứu từ trường Đại học Y học Thể thao Mỹ (American College of Sports Medicine),\n mỗi ngày bạn chỉ cần bỏ ra 30 phút chạy bộ là có thể đủ để cải thiện tâm trạng của một người mắc các chứng bệnh trầm cảm.\n Thậm chí những người tham gia nghiên cứu này cũng đã chỉ ra, chỉ cần đi bộ cũng đủ để xóa tan phần nào tình trạng trầm cảm. Bạn không cần nhất thiết phải chạy quá nhanh, chỉ cần vận động 30 phút mỗi ngày tâm trạng sẽ được cải thiện và bạn sẽ cảm thấy vui vẻ hơn rất nhiều. Chạy bộ giúp bạn tập trung hơn và giấc ngủ ngon hơn.\nChạy bộ giúp tăng cường sức khỏe tim mạch.Hỗ trợ cải thiện và tăng cường trí nhớ.Chạy bộ giúp tăng khả năng chống chịu với khó khăn, căng thẳng"));
        practices.add(new practice(2,"Nhảy dây","Nhảy dây là bài tập đơn giản nhất giúp bạn đốt cháy nhiều calo, săn chắc cơ bắp và đồng thời giảm căng thẳng, mệt mỏi hiệu quả. Ba bài tập nhảy dây cá nhân phổ biến nhất là nhảy hai chân, nhảy dây chạy bộ và nhảy từng chân, mỗi bài tập đem đến những hiệu quả riêng."));
        practices.add(new practice(3, "Yoga","Yoga là tập hợp các phương pháp luyện tâm và luyện thân cổ xưa bắt nguồn từ đất nước Ấn Độ, đòi hỏi sự kết hợp cao độ của tinh thần và cơ thể trong cùng một thời điểm. Yoga có thể giúp bạn giảm cân nhanh và lành mạnh; giữ gìn sắc đẹp, đặc biệt là với phái nữ; đẩy lùi bệnh tật; cũng như sự thư thái, tĩnh tâm quý báu. Thời điểm hiện tại, việc đến các lớp học yoga gần như là không thể nên bạn chỉ có thể tham khảo các tài liệu, video trên mạng để tập theo những bài cơ bản và đơn giản nhất."));
        return practices;
    }
}