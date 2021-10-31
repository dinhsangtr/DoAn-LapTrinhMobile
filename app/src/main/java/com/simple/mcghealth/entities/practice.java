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
        practices.add(new practice(4,"Tập bụng và tay sau","Bài tập này tập trung vào bắp tay sau và vai.\n" +
                "\n" +
                "•       Ngồi trên sàn nhà, đầu gối hơi gập.\n" +
                "\n" +
                "•       Đặt bàn tay ra phía sau, các ngón tay đối diện với cơ thể.\n" +
                "\n" +
                "•       Nâng mông lên khỏi sàn để tay và bàn chân nâng cơ thể bạn.\n" +
                "\n" +
                "•       Gập hai khuỷu tay cho đến khi mông chạm sàn, sau đó đẩy ngược lên vị trí bắt đầu.\n" +
                "\n" +
                "•       Nếu muốn khó hơn, khi đẩy lên, nâng chân trái và vươn về phía trước bằng cánh tay phải."));
        practices.add(new practice(5,"Chống đẩy","Đây là bài tập toàn thân vì nó đòi hỏi phải sử dụng rất nhiều nhóm cơ.\n" +
                "\n" +
                "·         Nằm úp mặt xuống sàn và đặt hai lòng bàn tay xuống sàn, cách nhau khoảng ngang vai và gần vai.\n" +
                "\n" +
                "·         Phần đệm của bàn chân cần chạm đất và hai bàn chân cách nhau một chút. Nâng cao người bằng cách sử dụng cánh tay.\n" +
                "\n" +
                "·         Thực hiện thẳng lưng từ đầu đến gót chân của bạn và co cơ bụng để giữ cho hông của bạn không bị  chùng xuống. Vị trí này là vị trí           bắt đầu và cũng là kết thúc cho một lần đẩy lên.\n" +
                "\n" +
                "·         Hạ ngực của bạn về phía sàn bằng cách gập khuỷu tay trong 1 giây, sau đó bắt đầu lại.\n" +
                "\n"));
        practices.add(new practice(6,"Thăng bằng tay, đầu gối và tập bụng","Bài tập này thách thức bạn giữ thăng bằng và tập cơ bụng.\n" +
                "\n" +
                "·         Đặt tay chân lên sàn. Tay trực tiếp dưới vai, đầu gối dưới hông. Giữ cho lưng thẳng. Nâng cánh tay phải  về phía trước và chân trái về phía sau. Đưa đầu gối về phía ngực đồng thời với khuỷu tay để chạm đầu gối.\n" +
                "\n" +
                "·         Lặp lại 10 lần, sau đó đổi chân.."));
        practices.add(new practice(7,"Tập mông (Squat)","Đây là bài tập chức năng hoạt động nhóm cơ lớn nhất trong cơ thể - mông và chân.\n" +
                "\n" +
                "·         Hai chân đứng rộng hơn vai một chút, hông xếp chồng lên đầu gối và đầu gối qua mắt cá chân. Dang rộng cánh tay, thẳng và song song với mặt đất, lòng bàn tay úp. Bắt đầu bằng cách vào vị trí như thể ngồi  trên ghế. Trong khi mông bắt đầu đẩy ra sau, đảm bảo ngực và vai thẳng, lưng thẳng. Giữ đầu hướng về phía trước, mắt nhìn thẳng về phía trước, cột sống thẳng.\n" +
                "\n" +
                "·         Squat tốt nhất là gập sâu nhất trong khả năng cho phép. Độ gập sâu tối ưu là hông thấp hơn đầu gối.\n" +
                "\n" +
                "·         Kéo căng cơ, trụ ở gót chân, đẩy ngược để đứng lên qua gót chân."));
        practices.add(new practice(8,"Chùng chân và nâng đầu gối","Bài tập này tập trung vào phía trước và phía sau chân.\n" +
                "\n" +
                "·        Nâng ngực, cằm và cơ bụng lên, lùi một bước bằng chân trái. Gập thẳng xuống sao cho đầu gối hướng xuống sàn. Nằm ngửa cùng ngón chân trái. Bàn chân trước đứng vững trên sàn, khi đẩy trở lại vị trí bắt đầu, nâng đầu gối lên trước mặt, giữ trong 1 giây sau đó lặp lại và đổi chân.\n" +
                "\n" +
                "Tóm lại, hãy sử dụng khoảng thời gian này để quan tâm bản thân nhiều hơn và dành thời gian để chăm sóc cơ thể của bạn. Đừng để bản thân bỏ lỡ những lợi ích sức khỏe tuyệt vời liên quan đến việc tập thể dục. Và hãy nhớ rằng, nếu bạn đang có vấn đề về sức khỏe thì cần lựa chọn bài tập thể dục phù hợp, hãy lắng nghe cơ thể và đừng cố gắng quá sức với thói quen tập thể dục đang áp dụng, nếu không bạn có thể gặp nguy cơ bị suy giảm tạm thời chức năng miễn dịch."));
        return practices;
    }
}