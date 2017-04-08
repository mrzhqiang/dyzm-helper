package cn.zhang.qiang.hellgate.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

/**
 * <p>
 * Created by mrZQ on 2017/4/7.
 */

public class TestRetrofit {
    private static final String BASE_URL = "http://haowanba.com/";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());

    private IHaowanba service;

    private static class Holder {
        private static TestRetrofit N = new TestRetrofit();
    }

    private TestRetrofit() {
        // Create a very simple Retrofit adapter which points the SudaMockService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(IHaowanba.class);
    }

    public static void main(String[] args) {
        Holder.N.service.hostHtml().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String html = response.body();

                    Document doc = Jsoup.parse(html, BASE_URL);

//                    Element body = doc.body();
                    String title = doc.title();
                    System.out.println("标题：" + title);

//                    List<Node> children = body.childNodes();
//                    showElement(children);
//
//                    Calendar calendar = Calendar.getInstance();
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 刚刚
//
//                    calendar.set(Calendar.HOUR_OF_DAY, 17);
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 今天
//
//                    calendar.set(Calendar.DAY_OF_MONTH, 6);
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 同月周内
//                    calendar.set(Calendar.DAY_OF_MONTH, 11);
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 超前周内
//                    calendar.set(Calendar.MONTH, Calendar.MARCH);
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 今年
//                    calendar.set(Calendar.YEAR, 2016);
//                    System.out.println(TimeHelper.showTime(calendar.getTimeInMillis()));// 前年

                    System.out.println("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }

            void showElement(List<Node> children) {

                for (Node element : children) {
                    String nodeName = element.nodeName();
                    if (!nodeName.startsWith("#")) {
                        System.out.println("tag：" + nodeName);
                    }
                    if (!nodeName.equals("p")) {
                        if (element.attributes().size() > 0) {
                            String url = element.absUrl("href");
                            if (url != null && url.trim().length() != 0) {
                                System.out.println("href：" + url);
                            }
                            String img = element.absUrl("src");
                            if (img != null && img.trim().length() != 0) {
                                System.out.println("img：" + img);
                            }
                        }
                    }
                    if (element.childNodeSize() > 0) {
                        List<Node> el = element.childNodes();
                        showElement(el);
                    } else {
                        if (element instanceof TextNode) {
                            String text = ((TextNode) element).text();
                            if (text != null && text.trim().length() != 0) {
                                System.out.println("text：" + text);
                            }
                        } else if (element instanceof Element) {
                            String text = ((Element) element).text();
                            if (text != null && text.trim().length() != 0) {
                                System.out.println("text：" + text);
                            }
                        }
                    }
                }
            }

        });
    }


    interface IHaowanba {

        @GET("/")
        Call<String> hostHtml();
    }

}
