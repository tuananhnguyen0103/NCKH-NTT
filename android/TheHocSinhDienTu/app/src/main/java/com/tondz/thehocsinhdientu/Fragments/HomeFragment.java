package com.tondz.thehocsinhdientu.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tondz.thehocsinhdientu.Activities.AbsentActivity;
import com.tondz.thehocsinhdientu.Activities.FaceDetectActivity;
import com.tondz.thehocsinhdientu.Activities.ScoreTableActivity;
import com.tondz.thehocsinhdientu.Activities.TimeTableActivity;
import com.tondz.thehocsinhdientu.Adapters.NewsAdapter;
import com.tondz.thehocsinhdientu.Models.FaceID;
import com.tondz.thehocsinhdientu.Models.News;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadNewsAdapter();
        onClick();
        loadDataUser();
    }

    ImageView imgAvatar;
    TextView tvName;
    Button btnRollCall, btnAbsent, btnCalendar;
    ImageView btnTimeTable, btnScore, btnExam, btnArchive, btnHomeWork, btnRank;
    RecyclerView rvNews;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();

    private void initView(View view) {
        imgAvatar = view.findViewById(R.id.img_avatar);
        tvName = view.findViewById(R.id.tv_name);
        btnRollCall = view.findViewById(R.id.btn_roll_call);
        btnAbsent = view.findViewById(R.id.btn_absent);
        btnCalendar = view.findViewById(R.id.btn_calendar);
        btnTimeTable = view.findViewById(R.id.btn_time_table);
        btnScore = view.findViewById(R.id.btn_score);
        btnExam = view.findViewById(R.id.btn_exam);
        btnArchive = view.findViewById(R.id.btn_achive);
        btnHomeWork = view.findViewById(R.id.btn_home_work);
        btnRank = view.findViewById(R.id.btn_rank);
        rvNews = view.findViewById(R.id.rv_news);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        rvNews.setAdapter(newsAdapter);
    }

    private void loadDataUser() {
        if (Common.HOC_SINH != null) {
            tvName.setText(Common.HOC_SINH.getHoTen());
            Glide.with(getContext()).load(Common.HOC_SINH.getAnhThe()).error(R.drawable.user).into(imgAvatar);


        }
    }

    private void onClick() {
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ScoreTableActivity.class));
            }
        });
        btnRollCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.HOC_SINH.getKhoaAnh().equals("0")) {
                    int check = checkFace();
                    if (check == 2) {
                        startActivity(new Intent(getContext(), FaceDetectActivity.class));
                    } else if (check == 1) {
                        Common.notiDialog("Thông báo", "Khuôn mặt của bạn chưa được phê duyệt, vui lòng liên hệ giáo viên để xác nhận", getContext());
                    } else {
                        Common.notiDialog("Thông báo", "Chưa có khuôn mặt, vui lòng cập nhật khuôn mặt", getContext());
                    }
                } else {
                    Common.notiDialog("Thông báo", "Khuôn mặt của bạn chưa được phê duyệt, vui lòng liên hệ giáo viên để xác nhận", getContext());
                }

            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TimeTableActivity.class));
            }
        });
        btnAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AbsentActivity.class));
            }
        });
        btnTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TimeTableActivity.class));
            }
        });
    }

    private int checkFace() {
        FaceID faceID = new DBHelper(getContext()).getFaceID();
        if (faceID != null) {
            if (faceID.getStatus() == 1) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private void loadNewsAdapter() {
        //load data from api
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsList.add(new News("1", "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu", "Những quả đồi bị 'xẻ thịt', băm nát để các đầu nậu tận thu đá cây. Loại đá này được cưa xẻ lót nền, có giá trị gấp hàng chục lần so với đá xây dựng bình thường khác.\n" +
                "Sau nhiều ngày thâm nhập đeo bám qua một số tỉnh, PV Thanh Niên lần ra đường dây khai thác đá trái phép quy mô lớn này.\n" +
                "\n" +
                "Mở đường xuyên đồi để khai thác đá\n" +
                "Theo tìm hiểu của PV, để tìm được khu vực đồi có nhiều đá cây, các chủ mỏ đá thường dò la thông tin từ xã này qua xã khác, từ huyện này qua huyện khác để săn lùng. Khi biết được nơi nào có nguồn đất và đá có thể khai thác, các đầu nậu sẽ thương thảo với chủ đất để mua lại toàn bộ khu đất hoặc cùng chủ đất hợp tác để làm mỏ khai thác đá trái phép.\n" +
                "\n" +
                "Qua điều tra của PV Thanh Niên, ở khu vực H.Bù Đăng (Bình Phước) có rất nhiều điểm khai thác đá trái phép. Cụ thể, ở xã Bom Bo có 2 điểm, xã Đắk Nhau 1 điểm, xã Đường 10 có 2 điểm. Còn tại H.Bù Gia Mập (Bình Phước) có tới 4 điểm, nhưng trong đó 2 điểm có dấu hiệu tạm ngưng hoạt động, 2 điểm vẫn đang khai thác đá cây rầm rộ. Thâm nhập thực tế, chúng tôi ghi nhận hầu hết các điểm khai thác đá trái phép đều mở đường gần chục cây số, nối tuyến giao thông chính vào đến tận “đại bản doanh”. Để các chuyến xe tải chở hàng chục tấn đá không bị sa lầy, các đầu nậu đã dùng xe ủi, xe lu, đồng thời trải thêm nhiều lớp đá hộc, đá mi... để tạo cốt nền.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 1\n" +
                "Một phần sông Đắk Glun (thuộc xã Đường 10, H.Bù Đăng) đang bị đất vùi lấp do khai thác đá trái phép\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Anh P. (ngụ Bình Phước, người từng kinh doanh đá cây ở khu vực Đông Nam bộ) cho hay thông thường các điểm khai thác đá đều nằm trên các quả đồi hoặc khu vực giáp sông và phần lớn là nằm sâu trong khu trồng trọt của người dân. Khu vực có đá thì cây cối thường sinh trưởng và phát triển chậm, dân ít qua lại nên không có đường đủ lớn để lượng xe cơ giới đi vào. “Do đó, sau khi nhận chuyển nhượng lại từ chủ đất, đa số đều phải tự mở đường, thậm chí còn phải mua thêm đất để mở đường kết nối, sau đó mới đưa xe cộ, máy móc và phương tiện vào khai thác, đồng thời tạo thuận lợi cho việc vận chuyển “chiến lợi phẩm” vượt núi, đưa đến nơi tiêu thụ”, anh P. nói.\n" +
                "\n" +
                "Xẻ đồi, lấp sông vô tội vạ\n" +
                "Sáng 5.5, nhóm PV Thanh Niên vượt qua nhiều đồi dốc, tiếp cận mỏ đá tại xã Đường 10, ghi nhận tình trạng khai thác đá cây trái phép. Di chuyển qua hàng chục cây số đường liên xã (nối xã Đắk Nhau với xã Đường 10), chúng tôi ghi nhận đường xuất hiện hàng loạt ổ voi, ổ gà. Buổi chiều thường có mưa nên việc di chuyển của người dân khá khó khăn. Chúng tôi hỏi một thanh niên địa phương đường vào mỏ đá lậu thì được chỉ dẫn: “Các anh tới ngã ba rồi rẽ phải, đi thêm mấy cây số đường sình lầy sẽ tới nơi”.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 2\n" +
                "Các xe máy múc đang thi nhau đào xới để khai thác đá tại thôn 5, xã Đường 10, H.Bù Đăng (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Đúng như lời cảnh báo, đường dẫn vào mỏ đá lầy lội, nước đọng thành vũng. Không ít lần xe máy của chúng tôi trượt bánh ngã sõng soài. Đáng chú ý, theo người dân địa phương, khi có người lạ xuất hiện thường sẽ có đối tượng cảnh giới (người của đầu nậu khai thác đá) bám đuôi, theo dõi. Sau hơn 30 phút lội sình bùn, chúng tôi cũng đến được mỏ đá. Trước mắt chúng tôi là cảnh tượng đất đá ngổn ngang, đồi bị “xẻ thịt”. Thậm chí lượng đất đá từ quả đồi bị “xẻ thịt” đã vùi lấp một phần sông Đắk Glun. “Tôi làm nghề chài lưới trên sông Đắk Glun. Đất đá đổ vùi xuống sông khiến việc bắt tôm cá mưu sinh trở nên rất khó khăn”, người phụ nữ chài lưới chia sẻ.\n" +
                "\n" +
                "Cách đó khoảng 7 km là một mỏ khai thác đá cây trái phép khác. Chúng tôi tiếp tục băng đồi vượt dốc, tìm đường vào đây để ghi hình. Đường vào mỏ đá này cũng nhọc nhằn không kém. Những chiếc xe tải chở hàng chục tấn đá chạy qua khiến con đường bị cày nát, lầy lội. Đáng chú ý, khi thấy người lạ tiếp cận mỏ đá, một tài xế điều khiển xe xúc liền tắt máy xe, lấy xe máy đuổi theo chúng tôi… Qua ống kính fly cam, những hình ảnh thể hiện rất rõ nét sự tàn phá không thương tiếc bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 3\n" +
                "Các xe máy múc được di chuyển vào khu vực nhiều cây cối khi cảnh giới thông báo có người lạ thâm nhập tại xã Phú Văn, H.Bù Gia Mập (Bình Phước)\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "“Họ hoành hành suốt nhiều năm nay rồi”\n" +
                "Một ngày tháng 5, nhóm PV Thanh Niên tiếp tục lần theo con đường mòn giữa các ngọn đồi để tìm đến mỏ khai thác đá trái phép thuộc địa bàn xã Phú Văn (H.Bù Gia Mập). Trên tuyến đường từ xã Bom Bo (H.Bù Đăng) đến xã Phú Văn, chúng tôi ghi nhận đường sá bị hư hỏng nghiêm trọng, ổ gà, ổ voi chi chít. Thậm chí 2 bên đường còn có vết tích đồi bị “xẻ thịt” bởi các đầu nậu khai thác đá.\n" +
                "\n" +
                "Dường như các mỏ khai thác đá trái phép đều rất… nổi tiếng với người dân địa phương,vì hầu như ai cũng biết chính xác đường vào mỏ. Một phụ nữ trung niên đang nhặt hạt điều chỉ tay, nói rành mạch khi chúng tôi hỏi thăm: “Ở xã Phú Văn có mỏ đá của ông T., ông Đ.L và ông H. Giờ các anh chạy vào con đường đất này sẽ tới mỏ khai thác của ông H.”. Theo chỉ dẫn, chúng tôi men theo đường đất để tiếp cận mỏ đá trái phép được cho là của ông H.\n" +
                "\n" +
                "Những đại công trường xẻ đồi, lấp sông khai thác đá lậu - ảnh 4\n" +
                "Một khu mỏ tại xã Đường 10, H.Bù Đăng (Bình Phước) đang tạm ngưng hoạt động\n" +
                "\n" +
                "THANH NIÊN\n" +
                "\n" +
                "Theo quan sát, tại đây tuy đá cây chất thành đống nhưng phương tiện cơ giới lại khá ít. Sau khi ghi hình từ trên cao, chúng tôi phát hiện 6 xe máy xúc cỡ lớn và 1 xe ben tại khu mỏ, trong đó 5 xe máy xúc đã di chuyển vào sâu trong các tán cây, chiếc xe ben và xe máy xúc còn lại vẫn đậu giữa khu mỏ. “Mấy nay chắc có “động” nên mỏ đá này tạm ngưng 2 ngày rồi. Bình thường thì xe xúc xẻ đồi, xe tải chở đá chạy ra vào liên tục. Họ hoành hành suốt nhiều năm nay rồi. Đường sá hư hỏng, xuống cấp, chúng tôi thấp cổ bé họng nên làm sao biết phản ánh đến ai”, một người dân làm rẫy ngao ngán nói.\n" +
                "\n" +
                "Chúng tôi rời mỏ đá giữa cảnh tượng núi đồi tan hoang, đất đá bị xới tung và tự đặt câu hỏi: Các cơ quan chức năng sở tại đã ở đâu? (còn tiếp)\n" +
                "\n" +
                "Theo ông B.Q.P (giám đốc một doanh nghiệp chuyên kinh doanh trong lĩnh vực đá tại TP.HCM), loại đá cây mà Báo Thanh Niên phản ánh là loại granite có tên thường gọi tại cửa hàng cũng như các kho đá là bazan xám, được khai thác nhiều ở tỉnh Bình Phước. Đá này thông thường có kích thước không được lớn như các dòng đá granite đang khai thác ở khu vực miền Trung, vả lại đá bazan xám cũng không được cứng, nên không dùng vào các hạng mục chịu lực nặng, mà đa số dùng để làm vỉa hè, đường đi nội bộ, hay trang trí trong các biệt thự, hồ bơi, và chủ yếu là xuất khẩu chứ trong nước số lượng tiêu thụ không nhiều. So với dòng đá san lấp hay đá dùng trong xây dựng công trình thì đá cây có giá trị cao gấp 4 lần. Dòng đá vật liệu tương đương với đá cây đang có giá bán trên thị trường khoảng hơn 300.000 đồng/tấn, còn dòng đá cây nhập để xẻ tấm thành phẩm thì giá nhập vào trên 1,2 triệu đồng/tấn.\n" +
                "\n", "https://image.thanhnien.vn/w2048/Uploaded/2022/wsxrxqeiod/2022_05_11/khai-thac-da2-4577.jpg"));
        newsAdapter = new NewsAdapter(getContext(), newsList);
        rvNews.setAdapter(newsAdapter);
    }
}