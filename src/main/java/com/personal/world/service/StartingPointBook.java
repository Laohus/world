package com.personal.world.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StartingPointBook{

    private static String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36";

    public static String BookName(String BookName) throws IOException {
        String URL = "https://www.qidian.com/search?kw=";
        Connection conn = Jsoup.connect(URL + BookName);
        Document doc = conn.header("User-Agent", header).get();
        Elements as = doc.select("div.book-mid-info h4 a cite");
        String NovelDirectory = null;
        /**获取小说链接地址*/
        for (Element a : as) {
            String title = a.text();
            if (title.equals(BookName)) {
                Elements bookId = doc.select("div.book-mid-info h4 a");
                String BookDetails = bookId.attr("href");
                if(BookDetails.length()!=0){
                    NovelDirectory = "http:" + BookDetails;
                    break;
                }

            }
        }
        return NovelDirectory;
    }

    public static List<Map<String, Object>> BookNovel(String NovelDirectory) throws IOException {

        List<Map<String, Object>> NovelList= new ArrayList<>();
        Map<String, List<String>> temp = new HashMap<>();

        String VipNovel = null;

        Connection connNovel = Jsoup.connect(NovelDirectory);
        Document docNovel = connNovel.header("User-Agent", header).get();
        Elements NovelNum = docNovel.select("div.volume-wrap > div");
        for(Element NovelNums : NovelNum){
            List<String> NovelName = new ArrayList<>();
            List<String> NovelUrl = new ArrayList<>();
            String FreeNovel = null;
            Map<String,Object> temps = new HashMap<>();
            /**获取小说目录*/
            Elements NovelFreeNum = NovelNums.select("h3");
            for(Element NovelFreeNums : NovelFreeNum){
                FreeNovel = NovelFreeNums.text();
            }
            Elements NovelFree = NovelNums.select("ul > li");

            for(Element Novela : NovelFree){
                Elements Novelbook = Novela.select("h2.book_name a");
                NovelName.add(Novelbook.text());
                NovelUrl.add("http:"+Novelbook.attr("href"));
            }
            temps.put("BigChapter",FreeNovel);
            temps.put("NovelName",NovelName);
            temps.put("NovelUrl",NovelUrl);
            NovelList.add(temps);

//            /**获取小说VIP目录*/
//            Elements NovelVipNum = docNovel.select("div.volume-wrap > div:nth-child(3) > h3");
//            for(Element NovelVipNums : NovelVipNum){
//                VipNovel = NovelVipNums.text();
//            }
//            Elements NovelVip = docNovel.select("div.volume-wrap > div:nth-child(3) > ul > li");
//            for(Element Novela : NovelVip){
//                Elements Novelbook = Novela.select("h2.book_name a");
//                NovelName.add(Novelbook.text());
//                NovelUrl.add("http:"+Novelbook.attr("href"));
//            }
        }

//        Elements NovelFreeNum = docNovel.select("div.volume-wrap > div:nth-child(2) > h3");
//        for(Element NovelFreeNums : NovelFreeNum){
//            FreeNovel = NovelFreeNums.text();
//        }
//        Elements NovelFree = docNovel.select("div.volume-wrap > div:nth-child(2) > ul > li");
//        for(Element Novela : NovelFree){
//            Elements Novelbook = Novela.select("h2.book_name a");
//            NovelName.add(Novelbook.text());
//            NovelUrl.add("http:"+Novelbook.attr("href"));
//        }
//        /**获取小说VIP目录*/
//        Elements NovelVipNum = docNovel.select("div.volume-wrap > div:nth-child(3) > h3");
//        for(Element NovelVipNums : NovelVipNum){
//            VipNovel = NovelVipNums.text();
//        }
//        Elements NovelVip = docNovel.select("div.volume-wrap > div:nth-child(3) > ul > li");
//        for(Element Novela : NovelVip){
//            Elements Novelbook = Novela.select("h2.book_name a");
//            NovelName.add(Novelbook.text());
//            NovelUrl.add("http:"+Novelbook.attr("href"));
//        }
//        temp.put("NovelName",NovelName);
//        temp.put("NovelUrl",NovelUrl);
        return NovelList;
    }

    public static String BookContent(List<Map<String, Object>> Novel, String titleName) throws IOException {
        String Content = null;
        for (Map<String, Object> NovelNameMap : Novel) {
            List<String> NovelNameList = (List<String>) NovelNameMap.get("NovelName");
            List<String> NovelUrlList = (List<String>) NovelNameMap.get("NovelUrl");
            for (int j = 0; j < NovelNameList.size(); j++) {
                if (titleName.equals(NovelNameList.get(j))){
                    Connection connNovelText = Jsoup.connect(NovelUrlList.get(j));
                    Document docNovelText = connNovelText.header("User-Agent", header).get();
                    Elements NovelFreeTextTitle = docNovelText.select("div.text-head > h3 > span.content-wrap");
                    Elements NovelFreeTextTitleId = docNovelText.select("div.text-head > a");
                    String ContentId = NovelFreeTextTitleId.attr("data-cid");
                    Elements NovelFreeText = docNovelText.select("#j_" + ContentId + " p");
                    Content = NovelFreeText.text().replace(" 　　", "");
                    break;
                }
            }
        }

//        for (int i =0; i<NovelName.size(); i++){
//            if(titleName.equals(NovelName.get(i))){
//                Connection connNovelText = Jsoup.connect(NovelUrl.get(i));
//                Document docNovelText = connNovelText.header("User-Agent", header).get();
//                Elements NovelFreeTextTitle = docNovelText.select("div.text-head > h3 > span.content-wrap");
//                Elements NovelFreeTextTitleId = docNovelText.select("div.text-head > a");
//                String ContentId = NovelFreeTextTitleId.attr("data-cid");
//                Elements NovelFreeText = docNovelText.select("#j_"+ContentId+" p");
//                Content = NovelFreeText.text().replace(" 　　","");
//                break;
//            }
//        }
        return Content;
    }

//    public static void main(String[] args) throws IOException {
//
//        String BookName = "帝霸";
//        String URL = "https://www.qidian.com/search?kw=";
//        String FreeNovel = null;
//        String VipNovel = null;
//        String titleName = "第7章九圣妖门（上）";
//        String Content = null;
//        String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36";
//        List<String> NovelName = new ArrayList<>();
//        List<String> NovelUrl = new ArrayList<>();
//
//        Connection conn = Jsoup.connect(URL + BookName);
//        Document doc = conn.header("User-Agent", header).get();
//        Elements as = doc.select("div.book-mid-info h4 a cite");
//        String NovelDirectory = null;
//        /**获取小说链接地址*/
//        for (Element a : as) {
//            String title = a.text();
//            if (title.equals(BookName)) {
//                Elements bookId = doc.select("div.book-mid-info h4 a");
//                String BookDetails = bookId.attr("href");
//                NovelDirectory = "http:" + BookDetails;
//                break;
//            }
//        }
//        /**获取小说免费目录*/
//        Connection connNovel = Jsoup.connect(NovelDirectory);
//        Document docNovel = connNovel.header("User-Agent", header).get();
//        Elements NovelFreeNum = docNovel.select("div.volume-wrap > div:nth-child(2) > h3");
//        for(Element NovelFreeNums : NovelFreeNum){
//            FreeNovel = NovelFreeNums.text();
//        }
//        Elements NovelFree = docNovel.select("div.volume-wrap > div:nth-child(2) > ul > li");
//        for(Element Novela : NovelFree){
//            Elements Novelbook = Novela.select("h2.book_name a");
//            NovelName.add(Novelbook.text());
//            NovelUrl.add("http:"+Novelbook.attr("href"));
//        }
//        /**获取小说VIP目录*/
//        Elements NovelVipNum = docNovel.select("div.volume-wrap > div:nth-child(3) > h3");
//        for(Element NovelVipNums : NovelVipNum){
//            VipNovel = NovelVipNums.text();
//        }
//        Elements NovelVip = docNovel.select("div.volume-wrap > div:nth-child(3) > ul > li");
//        for(Element Novela : NovelVip){
//            Elements Novelbook = Novela.select("h2.book_name a");
//            NovelName.add(Novelbook.text());
//            NovelUrl.add("http:"+Novelbook.attr("href"));
//        }
//
//        for (int i =0; i<NovelName.size(); i++){
//            if(titleName.equals(NovelName.get(i))){
//                Connection connNovelText = Jsoup.connect(NovelUrl.get(i));
//                Document docNovelText = connNovelText.header("User-Agent", header).get();
//                Elements NovelFreeTextTitle = docNovelText.select("div.text-head > h3 > span.content-wrap");
//                Elements NovelFreeTextTitleId = docNovelText.select("div.text-head > a");
//                String ContentId = NovelFreeTextTitleId.attr("data-cid");
//                Elements NovelFreeText = docNovelText.select("#j_"+ContentId+" p");
//                Content = NovelFreeText.text().replace(" 　　","");
//                break;
//            }
//        }
//        System.out.println(FreeNovel);
//        System.out.println(VipNovel);
//        System.out.println(NovelName);
////        System.out.println(NovelUrl);
//        System.out.println(Content);
//
//
//
//    }

}
