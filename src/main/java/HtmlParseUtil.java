import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        //在当当网搜索java，别的自行修改
        new HtmlParseUtil().parseDD("java").forEach(System.out::println);
    }

    /**
     * 封装
     * @param key   要搜索的内容
     * @return
     * @throws IOException
     */
    public List<Content> parseDD(String key) throws IOException{
        //获取请求
        String url="http://search.dangdang.com/?key="+key+"&act=input";
        //解析网页(返回Document就是Document对象)
        Document parse = Jsoup.parse(new URL(url), 30000);
        //所有在js中可以使用的数据都可以使用
        Element element = parse.getElementById("search_nature_rg");
        //获取所有li中的内容
        Elements li = element.getElementsByTag("li");

        ArrayList<Content> goodsList = new ArrayList<>();

        for(Element el:li){
            //关于这种图片特别多的网站，都是懒加载图片获得
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("price").eq(0).text();
            String title = el.getElementsByClass("name").eq(0).text();

            Content content = new Content();
            content.setImg(img);
            content.setTitle(title);
            content.setPrice(price);

            goodsList.add(content);
        }
        return goodsList;
    }
}
