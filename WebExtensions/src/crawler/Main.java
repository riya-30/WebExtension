package crawler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
 
public class Main {
	public static Map<String,Integer> map=new HashMap<String,Integer>();
	public static void main(String[] args) throws SQLException, IOException {
		System.out.print("Enter The Extension or Extensions Separated by Comma:-");
		Scanner sc=new Scanner(System.in);
		String input=sc.nextLine();
		String[] s=input.split(",");
		for(int i=0;i<s.length;i++){
			int j=0;
			while(s[i].charAt(j)!='.')
				j++;
			fileinfo("https://fileinfo.com/extension/"+s[i].substring(j+1));
			pc_net("https://pc.net/extensions/file/"+s[i].substring(j+1));
			file_suffix("https://www.filesuffix.com/en/extension/"+s[i].substring(j+1));
			System.out.println("-----------------------------------------------------------------------------------------------------------");
		}
		sc.close();
	}
 
	public static void fileinfo(String URL){
//		System.out.println(URL);
		Document doc;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			System.out.println("Extension Not existing in pc_net");
			return;
		}
		String fileType=doc.select("span[itemprop=name]").text();
		System.out.println("File Type-> "+fileType);
		Elements table=doc.select("table[class=headerInfo]");
		int i=0;
        for (Element row : table.select("tr")) {
            Elements tds = row.select("td");
            	String[] v=tds.get(1).text().split(" ");
            	System.out.println(tds.get(0).text() + "-> " + v[0]);
            i+=1;
            if(i==4)
            	break;
        }
        i=0;
        Map<String,Integer> map=new HashMap<String,Integer>();
        System.out.println("Softwares That Open JS Files in various Platforms :-");
        for(Element table_softwares: doc.select("table[class=programs]")){
	        for (Element row : table_softwares.select("tr")) {
	        	if(i>0){
	        		i-=1;
	        		continue;
	        	}
	            Elements tds = row.select("td");
	            if(map.get(tds.get(0).text())==null)
	            	map.put(tds.get(0).text(), 1);
	            else
	            	break;
	            System.out.print(tds.get(0).text()+"-> ");
	            for(Element row1: tds.select("tr")){
	            	Elements tds1 = row1.select("td");
	            	System.out.print(tds1.get(0).text()+"; ");
	            	i+=1; 
	            }
	        }
        }
	}
	
	public static void pc_net(String URL){
		//System.out.println(URL);
		Document doc;
		System.out.println();
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			System.out.println("Extension Not existing in pc_net");
			return;
		}
		Elements table=doc.select("table[class=display]");
		int i=0;
		System.out.println();
		System.out.print("Detailed Description-> ");
		for (Element row : table.select("tr")) {
            Elements tds = row.select("td");
            	i+=1;
            	if(tds.hasText() && i==5){          
            		System.out.println(tds.get(1).text());
            	}
        }
	}
	
	public static void file_suffix(String URL){
//		System.out.println(URL);
		Document doc;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			System.out.println("Extension Not existing in pc_net");
			return;
		}
		Element s=doc.getElementById("result");
		Element s1=s.getElementsByClass("er").first();
		String s2=s1.getElementsByAttribute("href").first().toString();
		int i=0;
		System.out.println();
		while(s2.length()>i){
			if(s2.charAt(i)=='"'){
				i+=1;
				String s3="";
				while(s2.charAt(i)!='"'){
					s3+=s2.charAt(i);
					i++;
				}
				System.out.print("Link For Further Reference:-");
				System.out.println(s3);
				break;
			}
			i++;
		}
	}
}