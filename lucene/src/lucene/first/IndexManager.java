package lucene.first;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import lucene.dao.BookDao;
import lucene.dao.BookDaoImpl;
import lucene.po.Book;

public class IndexManager {

	@Test
	public void createIndex() throws Exception{
		//采集数据
		BookDao dao = new BookDaoImpl();
		List<Book> list = dao.queryBooks();
		
		//将采集到的数据封装到Document对象中
		List<Document> docList = new ArrayList<>();
		Document document;
		for(Book book : list){
			document = new Document();
			//store：如果是YES，則説明存储到文档域中
			//ID:不分词、索引、存储StringField
			Field id = new StringField("id", book.getId().toString(), Store.YES);
			//名称：分词、索引、存储TextField
			Field name = new TextField("name", book.getName(), Store.YES);
			//价格：分词、索引、存储  但是是数字类型，所以使用FloatField
			Field price = new FloatField("price", book.getPrice(), Store.YES);
			//描述：分词、索引、不存储TextField
			Field description = new TextField("description", book.getDescription(), Store.NO);
			
			document.add(id);
			document.add(name);
			document.add(price);
			document.add(description);
			
			docList.add(document);
		}
		
		//创建分词器,标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		
		//创建IndexWrite
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		File indexFile = new File("E:\\index\\");
		Directory directory = FSDirectory.open(indexFile);
		IndexWriter writer = new IndexWriter(directory, cfg);
		
		//通过IndexWriter对象将Document写入到索引库中
		for(Document doc : docList){
			writer.addDocument(doc);
		}
		//关闭writer
		writer.close();
		
	}
	
	@Test
	public void deleteIndex() throws Exception{
		
		//创建分词器,标准分词器
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		
		Directory directory = FSDirectory.open(new File("E:\\index\\"));
		//创建IndexWrite
		IndexWriter writer = new IndexWriter(directory , cfg);
		writer.deleteDocuments(new Term("id", "1"));
		writer.close();
	}
}
