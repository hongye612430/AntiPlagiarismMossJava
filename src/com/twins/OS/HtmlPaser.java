package com.twins.OS;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.htmlparser.tags.Div;

/**
 * @author twins 
 * help with https://www.ibm.com/developerworks/cn/opensource/os-cn-crawler/
 * @version
 */
public class HtmlPaser {

	/**
	 * Function to get all links in a html file.
	 * @param url
	 */
	public static void extracLinks(String url) {
			try {
				Parser parser = new Parser(url);
				parser.setEncoding("gb2312");
	//���� <frame> ��ǩ�� filter��������ȡ frame ��ǩ��� src ����������ʾ������
				NodeFilter frameFilter = new NodeFilter() {
					public boolean accept(Node node) {
						if (node.getText().startsWith("frame src=")) {
							return true;
						} else {
							return false;
						}
					}
				};
	//OrFilter �����ù��� <a> ��ǩ��<img> ��ǩ�� <frame> ��ǩ��������ǩ�� or �Ĺ�ϵ
				OrFilter rorFilter = new OrFilter(new NodeClassFilter(LinkTag.class), new NodeClassFilter(ImageTag.class));
				OrFilter linkFilter = new OrFilter(rorFilter, frameFilter);
		//�õ����о������˵ı�ǩ
				NodeList list = parser.extractAllNodesThatMatch(linkFilter);
				for (int i = 0; i < list.size(); i++) {
					Node tag = list.elementAt(i);
					if (tag instanceof LinkTag)//<a> ��ǩ 
					{
						LinkTag link = (LinkTag) tag;
						String linkUrl = link.getLink();//url
						String text = link.getLinkText();//��������
						System.out.println(linkUrl + "**********" + text);
					}
					else if (tag instanceof ImageTag)//<img> ��ǩ
					{
						ImageTag image = (ImageTag) list.elementAt(i);
						System.out.print(image.getImageURL() + "********");//ͼƬ��ַ
						System.out.println(image.getText());//ͼƬ����
					}
					else//<frame> ��ǩ
					{
	//��ȡ frame �� src ���Ե������� <frame src="test.html"/>
						String frame = tag.getText();
						int start = frame.indexOf("src=");
						frame = frame.substring(start);
						int end = frame.indexOf(" ");
						if (end == -1)
							end = frame.indexOf(">");
						frame = frame.substring(5, end - 1);
						System.out.println(frame);
					}
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
	}
	
	public void getAttributeValue(String url) {
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
		} catch (ParserException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * output the file without html tags
	 * @param url
	 */
	public void getContentHaveNoTag(String url) {
		StringBean sb = new StringBean();
		sb.setLinks(false);//���ý����ȥ������
		sb.setURL(url);//����������Ҫ�˵���ҳ��ǩ��ҳ�� url
		System.out.println(sb.getStrings());//��ӡ���
	}

	//�������нڵ㣬��������ؼ��ֵĽڵ�
	public static void extractKeyWordText(String url, String keyword) {
		try {
            //����һ����������������ҳ�� url ��Ϊ����
			Parser parser = new Parser(url);
			//������ҳ�ı���,����ֻ��������һ�� gb2312 ������ҳ
			parser.setEncoding("gb2312");
			//�������нڵ�, null ��ʾ��ʹ�� NodeFilter
			NodeList list = parser.parse(null);
            //�ӳ�ʼ�Ľڵ��б�������еĽڵ�
			processNodeList(list, keyword);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	private static void processNodeList(NodeList list, String keyword) {
		//������ʼ
		SimpleNodeIterator iterator = list.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			//�õ��ýڵ���ӽڵ��б�
			NodeList childList = node.getChildren();
			//���ӽڵ�Ϊ�գ�˵����ֵ�ڵ�
			if (null == childList)
			{
				//�õ�ֵ�ڵ��ֵ
				String result = node.toPlainTextString();
				//�������ؼ��֣���򵥴�ӡ�����ı�
				if (result.indexOf(keyword) != -1)
					System.out.println(result);
			} //end if
			//���ӽڵ㲻Ϊ�գ����������ú��ӽڵ�
			else 
			{
				processNodeList(childList, keyword);
			}//end else
		}//end wile
	}
	
	/**
	 * ���ָ����ǩ�ڵ�����
	 * @param url
	 * @param tag
	 */
	public static void getContentTage(String url, String tag) {
		try {
			Parser myParser = new Parser(url);

			// ���ñ���
			myParser.setEncoding("GBK");
			NodeFilter filter = new TagNameFilter(tag);
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
			Div tabletag = (Div) nodeList.elementAt(0);

			System.out.println(tabletag.toHtml());
			System.out.println("==============");
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ParserException {
		// TODO Auto-generated method stub
		String url = "https://www.ibm.com/developerworks/cn/opensource/os-cn-crawler/";
		getContentTage(url, "div");
	}
}
/*
 * String title; String constellation; String body; String summary;
 * 
 * Parser parser = new Parser( "http://astro.sina.com.cn/sagittarius.html");
 * parser.setEncoding("utf-8");
 * 
 * NodeFilter filter_constellation_summart = new AndFilter( (new
 * TagNameFilter(" td ")), (new HasChildFilter( new TagNameFilter(" b "))));
 * 
 * NodeFilter filter_title = new AndFilter(new TagNameFilter(" font "), new
 * HasAttributeFilter(" class ", " f1491 "));
 * 
 * NodeFilter filter_body = new AndFilter(new TagNameFilter(" td "), new
 * HasAttributeFilter(" width ", " 30% "));
 * 
 * NodeList nodelist = parser.parse(filter_constellation_summart); Node
 * node_constellation = nodelist.elementAt(0); constellation =
 * node_constellation.getFirstChild().getNextSibling() .toHtml();
 * 
 * Node node_summary = nodelist.elementAt(1); NodeList summary_nodelist =
 * node_summary.getChildren(); summary = summary_nodelist.elementAt(3).toHtml()
 * + summary_nodelist.elementAt(5).toHtml();
 * 
 * parser.reset();
 * 
 * nodelist = parser.parse(filter_title); Node node_title =
 * nodelist.elementAt(0); title =
 * node_title.getNextSibling().getNextSibling().toHtml(); // title =
 * node_title.getNextSibling().getNextSibling().toHtml() ;
 * 
 * parser.reset();
 * 
 * nodelist = parser.parse(filter_body); Node node_body = nodelist.elementAt(0);
 * Parser body_parser = new Parser(node_body.toHtml());
 * 
 * TextExtractingVisitor visitor = new TextExtractingVisitor();
 * body_parser.visitAllNodesWith(visitor); body = visitor.getExtractedText();
 * 
 * // System.out.println(node_summary.getChildren().toHtml()) ; //
 * System.out.println(node_body.toHtml()) ; // System.out.println(title.trim())
 * ; // System.out.println(constellation.trim()) ; //
 * System.out.println(body.trim()) ; System.out.println(summary.trim());
 * 
 * }
 */