package com.book.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.book.vo.BookVo;
/**
 * @author Ran
 * @date 2020年2月29日
 */
public class CategoryUtil {

	public static void main(String[] args) throws IOException, ParseException {

		String url = "http://book.dangdang.com/";
		findBook(url);
		//Date stringtoDate = stringtoDate("2018-05-23");
		//System.out.println(stringtoDate);
		//Elements element = getElement(url);
		//LevelThree(element);
		// System.out.println(levelOne.size());
		// System.out.println(levelOne);
	}

	public static List<String> getOne(String url) throws IOException {
		Elements element = getElement(url);
		List<String> levelOne = LevelOne(element).subList(1, 12);
		return levelOne;
	}

	public static Map<String, List<String>> getTwo(String url) throws IOException {
		Elements element = getElement(url);
		Map<String, List<String>> levelTwo = LevelTwo(element);
		return levelTwo;
	}
	public static Map<String, List<String>> getThree(String url) throws IOException {
		Elements element = getElement(url);
		Map<String, List<String>> levelThree = LevelThree(element);
		return levelThree;
	}
	public static Elements getElement(String url) throws IOException {

		Document doc = Jsoup.connect(url).maxBodySize(0).get();
		Elements EList = doc
				.select("#bd_auto > div.con.storey_one > div.col.storey_one_left > div.sidemenu > div.con.flq_body");
		return EList;
	}

	public static List<String> LevelOne(Elements elements) {

		Elements selectOne = elements.select("div[class='level_one']");
		// System.out.println(selectOne);
		// System.out.println(selectOne.get(10));
		List<String> one = new ArrayList<>();
		for (Element element : selectOne) {
			// Elements select =element.select("dl[class='primary_dl']").select("dt");
			// System.out.println(select);
			String text = element.select("dl[class='primary_dl']").select("dt").text();
			one.add(text);
		}
		return one;
	};

	public static Map<String, List<String>> LevelTwo(Elements elements) {
		Map<String, List<String>> map = new HashMap<>();
		Elements selectOne = elements.select("div[class='level_one']");
		// System.out.println(selectOne);
		// System.out.println(selectOne.get(10));
		for (Element element : selectOne) {
			String text1 = element.select("dl[class='primary_dl'][son='1']").select("dt").text();
			//System.out.println(text1);
			Elements select2 = element.select("div[class='hide submenu ']").select("div[class='col eject_left']")
					.select("dl[class='inner_dl']");
			// System.out.println(select2);
			List<String> list = new ArrayList<>();
			for (Element element2 : select2) {
				String text2 = element2.select("dt").text();
				list.add(text2);
				// System.out.println(text2);
				// System.out.println("---------------");
			}
			map.put(text1, list);
		}
		// System.out.println(map);
		return map;
	};
	
	public static Map<String, List<String>> LevelThree(Elements elements) {
		Map<String, List<String>> map = new HashMap<>();
		Elements selectOne = elements.select("div[class='level_one']");
		for (Element element : selectOne) {
			Elements select2 = element.select("div[class='hide submenu ']").select("div[class='col eject_left']")
					.select("dl[class='inner_dl']");
			for (Element element2 : select2) {
				String text2 = element2.select("dt").text();
				//System.out.println(text2);
				Elements select3 = element2.select("dd").select("a");
				List<String> list = new ArrayList<>();
				for (Element element3 : select3) {
					String attr = element3.attr("title");
					list.add(attr);
					//System.out.println(attr);
				}
				map.put(text2, list);
			}
		}
		System.out.println(map);
		return map;
	}
	
	public static Date stringtoDate(String time) throws ParseException {
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=simpleDateFormat.parse(time);
		return date;
		
	}
	public static void  findBook(String url) throws IOException, ParseException {
		Map<String, List<BookVo>> map = new HashMap<>();
		Elements elements = getElement(url);
		Elements selectOne = elements.select("div[class='level_one']");
		for (Element element : selectOne) {
			Elements select2 = element.select("div[class='hide submenu ']").select("div[class='col eject_left']")
					.select("dl[class='inner_dl']");
			for (Element element2 : select2) {
				Elements select3 = element2.select("dd").select("a");
				for (Element element3 : select3) {
					String title = element3.attr("title");
					String bookurls=element3.attr("href");
					if (bookurls.contains("category.dangdang")) {
						Document doc = Jsoup.connect(bookurls).maxBodySize(0).get();
						Elements bookElement = doc.select("#component_59").select("li");
						List<BookVo> bookList=new ArrayList<>();
						BookVo bo=new BookVo();
						int i=0;
						for (Element element4 : bookElement) {
							if (i>=2) {
								break;
							}
							Long id=null;
							String name = element4.select("p[class='name']").text();
							String author=element4.select("p.search_book_author > span:nth-child(1) > a").text();
							String briefintroduction=element4.select("p[class='detail']").text();
							String press=element4.select("p.search_book_author > span:nth-child(3) > a").text();
							String publicationdate=element4.select("p.search_book_author > span:nth-child(2)").text();
							String image=element4.select("img").attr("data-original");
							if (i==0) {
								 image=element4.select("img").attr("src");
								
							}
							String bookurl=element4.select("p.name > a").attr("href");
							Document doc1 = Jsoup.connect(bookurl).maxBodySize(0).get();
							String isbn=doc1.select("#detail_describe > ul > li:nth-child(5)").text();
							publicationdate=publicationdate.replace("/", "");
							if (isbn.equals("")) {
								isbn="0";
							}else {
								isbn=isbn.replace("国际标准书号ISBN：", "");
							}
							Date d=null;
							if (!publicationdate.equals("")) {
								 d=stringtoDate(publicationdate);
							}
							/*
							 * bo.setId(id).setIsbn(isbn).setBookname(name).setAuthor(author).setPress(
							 * press)
							 * .setBriefintroduction(briefintroduction).setPublicationdate(d).setImage(image
							 * ).setCreated(new Date()).setUpdated(new Date()); System.out.println(image);
							 */
							//System.out.println(i+"|"+bo);
							i++;
						}
						map.put(title, bookList);
					}
				}
			}
		}
		//System.out.println(map);
	}
}
