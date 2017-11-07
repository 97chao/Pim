package lucene.first;

import java.io.File;
import java.util.Iterator;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class IndexSearch {

	@Test
	public void indexSearch() throws Exception{
		//创建query对象
		//搜索QueryParser搜索时，需要指定分词器，搜索时的分词器要和索引时的分词器一致
		//第一个参数：默认搜索的域的名称
		QueryParser parser = new QueryParser("name", 
				new StandardAnalyzer());
		
		//通过queryparser对象来创建query对象
		//参数：输入的lucene的查询语句（关键字一定要大写）
		Query query = parser.parse("name:java");
		
		//创建IndexSearch
		//指定索引库的地址
		File indexFile = new File("E:\\index\\");
		Directory directory = FSDirectory.open(indexFile);
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		//通过searcher来搜索索引库
		//第二个参数;指定需要显示的顶部记录的N条
		TopDocs topDocs = searcher.search(query, 10);
		
		//根据查询条件匹配出的记录总数
		int count = topDocs.totalHits;
		System.out.println("匹配出的总数："+count);
		//根据查询条件匹配出的记录
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			//获取文档的ID
			int docId = scoreDoc.doc;
			
			//通过ID获取文档
			Document doc = searcher.doc(docId);
			System.out.println("ID:"+doc.get("id"));
			System.out.println("name:"+doc.get("name"));
			System.out.println("price:"+doc.get("price"));
			System.out.println("description:"+doc.get("description"));
		}
		//关闭资源
		reader.close();
	}
}
