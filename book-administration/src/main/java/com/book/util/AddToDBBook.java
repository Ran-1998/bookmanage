package com.book.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.book.config.MybatisConfig;
import com.book.mapper.BookMapper;
import com.book.mapper.CategoryMapper;
import com.book.pojo.Book;
import com.book.pojo.BookCategory;

/**
 * @author Ran
 * @date 2020年2月29日
 */
public class AddToDBBook extends MybatisConfig {
	private String url = "http://book.dangdang.com/";

	@Test // 一级标题
	public void addOne() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
		BookCategory category = new BookCategory();
		System.out.println(category);
		List<String> one = CategoryUtil.getOne(url);
		for (String stringOne : one) {
			category.setId(null).setIsParent(true).setName(stringOne).setStatus(1).setParentId(0l).setCreated(new Date()).setUpdated(new Date());
			categoryMapper.addCategory(category);
			session.commit();
		}
	}

	@Test // 二级标题
	public void addTwo() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
		Map<String, List<String>> two = CategoryUtil.getTwo(url);
		// System.out.println(two.size());
		Set<Entry<String, List<String>>> set = two.entrySet();
		BookCategory category = new BookCategory();
		for (Entry<String, List<String>> entry : set) {
			if (entry.getKey().equals("")) {
				continue;
			}
			Integer selectParentId = categoryMapper.selectParentId(entry.getKey());
			List<String> value = entry.getValue();
			for (String s : value) {
				category.setId(null).setIsParent(true).setName(s).setStatus(1).setParentId(selectParentId.longValue()).setCreated(new Date()).setUpdated(new Date());
				if (categoryMapper.selectParentId(s) == null) {
					categoryMapper.addCategory(category);
				}
				session.commit();
			}
		}
	}

	@Test // 三级标题
	public void addThree() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
		Map<String, List<String>> three = CategoryUtil.getThree(url);
		Set<Entry<String, List<String>>> set = three.entrySet();
		BookCategory category = new BookCategory();
		for (Entry<String, List<String>> entry : set) {
			if (entry.getKey().equals("")) {
				continue;
			}
			Integer selectParentId = categoryMapper.selectParentId(entry.getKey());
			List<String> value = entry.getValue();
			for (String s : value) {
				if (selectParentId == null) {
					continue;
				}
				category.setId(null).setIsParent(false).setName(s).setStatus(1).setParentId(selectParentId.longValue()).setCreated(new Date()).setUpdated(new Date());
				if (categoryMapper.selectParentId(s) == null) {
					categoryMapper.addCategory(category);
				}
				session.commit();
			}
		}
	}

	@Test
	public void addBook() throws ParseException, IOException {
		SqlSession session = sqlSessionFactory.openSession();
		CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
		BookMapper bookMapper = session.getMapper(BookMapper.class);
		/*
		 * Book book=new Book();
		 * book.setAuthor("ss").setBookname("www").setCid(1l).setBriefintroduction(
		 * "dwdw").setId(null).setImage("").setIsbn("wwrwrw").setPress("dwdw").
		 * setPublicationdate(new Date()).setStatus(1l).setCreated(new Date())
		 * .setUpdated(new Date()); bookMapper.InsertBook(book);
		 */
		Elements elements;

		elements = CategoryUtil.getElement(url);

		Elements selectOne = elements.select("div[class='level_one']");
		for (Element element : selectOne) {
			Elements select2 = element.select("div[class='hide submenu ']").select("div[class='col eject_left']").select("dl[class='inner_dl']");
			for (Element element2 : select2) {
				Elements select3 = element2.select("dd").select("a");
				for (Element element3 : select3) {
					String title = element3.attr("title");
					String bookurls = element3.attr("href");
					if (categoryMapper.selectParentId(title) == null) {
						continue;
					}
					Integer cid = categoryMapper.selectParentId(title);
					if (bookurls.contains("category.dangdang")) {
						Document doc = Jsoup.connect(bookurls).maxBodySize(0).get();
						Elements bookElement = doc.select("#component_59").select("li");
						Book book = new Book();
						int i = 0;
						for (Element element4 : bookElement) {
							if (i >= 4) {
								break;
							}
							Long id = null;
							String name = element4.select("p[class='name']").text();
							String author = element4.select("p.search_book_author > span:nth-child(1) > a").text();
							String briefintroduction = element4.select("p[class='detail']").text();
							String press = element4.select("p.search_book_author > span:nth-child(3) > a").text();
							String publicationdate = element4.select("p.search_book_author > span:nth-child(2)").text();
							String image = element4.select("img").attr("data-original");
							if (briefintroduction.equals("") || author.equals("")) {
								continue;
							}
							if (i == 0) {
								image = element4.select("img").attr("src");

							}
							String bookurl = element4.select("p.name > a").attr("href");
							Document doc1;
							try {
								doc1 = Jsoup.connect(bookurl).maxBodySize(0).get();
								String isbn = doc1.select("#detail_describe > ul > li:nth-child(5)").text();
								publicationdate = publicationdate.replace("/", "");
								if (isbn.equals("")) {
									isbn = "0";
								} else {
									isbn = isbn.replace("国际标准书号ISBN：", "");
								}
								Date d = null;
								if (!publicationdate.equals("")) {
									d = CategoryUtil.stringtoDate(publicationdate);
								}
								if (image.contains("images/model/guan/url_none.png")) {
									continue;
								}
								book.setId(id).setIsbn(isbn).setBookname(name).setAuthor(author).setPress(press).setCid(cid).setStatus(1l).setBriefintroduction(briefintroduction).setPublicationdate(d)
										.setImage(image).setCreated(new Date()).setUpdated(new Date());
								// System.out.println(image);
								// System.out.println(i+"|"+bo);
								// System.out.println(book);
								bookMapper.InsertBook(book);
								i++;
								session.commit();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								continue;
							}

						}
					}
				}
			}
		}

	}
}
