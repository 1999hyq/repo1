package Com.XunAO.lucene;


import Com.XunAO.Projo.Blog;
import Com.XunAO.Utils.DateUtil;
import Com.XunAO.Utils.StringUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.FileReader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Myindex {
    private Directory dir=null;


    /**
     * 获取IndexWriter实例
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter()throws Exception{
        dir= FSDirectory.open(Paths.get("C://lucene"));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir, iwc);
        return writer;
    }
    public void addIndex(Blog blog)throws Exception {
        IndexWriter indexWriter=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
        doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
        indexWriter.addDocument(doc);

    }
    /**
     * 更新博客索引
     * @param blog
     * @throws Exception
     */
    public void updateIndex(Blog blog)throws Exception{
        IndexWriter writer = getWriter();
        Document document=new Document();
        document.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(),Field.Store.YES));
        document.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
        document.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
        writer.updateDocument(new Term("id",String.valueOf(blog.getId())),document);
        writer.close();
    }

    /**
     * 查询博客信息
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<Blog> searchBlog(String q)throws Exception {
       dir = FSDirectory.open(Paths.get("C://lucene"));
        DirectoryReader open = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(open);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser tilte = new QueryParser("tilte", analyzer);
        Query parse = tilte.parse(q);
        QueryParser content = new QueryParser("content", analyzer);
        Query parse1 = content.parse(q);
        builder.add(parse, BooleanClause.Occur.SHOULD);
         builder.add(parse1,BooleanClause.Occur.SHOULD);
        TopDocs search = indexSearcher.search(builder.build(), 100);
        QueryScorer queryScorer = new QueryScorer(parse);
        SimpleFragmenter simpleFragmenter = new SimpleFragmenter();
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, queryScorer);
        highlighter.setTextFragmenter(simpleFragmenter);
        List<Blog>list=new ArrayList<>();
        for(ScoreDoc scoreDoc:search.scoreDocs)
        {
            Document a = indexSearcher.doc(scoreDoc.doc);
            Blog blog=new Blog();
            blog.setId(Integer.parseInt(a.get("id")));
            blog.setReleaseDateStr(a.get("releaseDate"));
            String title = a.get("title");
            String content1 = a.get("content");
            if(tilte!=null)
            {
                TokenStream tokenStream = analyzer.tokenStream("title", new FileReader(title));
                String bestFragment = highlighter.getBestFragment(tokenStream, title);
                if (StringUtil.isempty(bestFragment))
                {
                    blog.setTitle(title);
                }
                else
                {
                    blog.setTitle(bestFragment);
                }
                if (content!=null)
                {
                    TokenStream tokenStream1 = analyzer.tokenStream("content", new StringReader(content1));
                    String hContent=highlighter.getBestFragment(tokenStream1, content1);
                    if(StringUtil.isempty(hContent)){
                        if(content1.length()<=200){
                            blog.setContent(content1);
                        }else{
                            blog.setContent(content1.substring(0, 200));
                        }
                    }else{
                        blog.setContent(hContent);
                    }

            }


        }
            list.add(blog);

        }
return list;
    }
    /**
     * 删除指定博客的索引
     * @param blogId
     * @throws Exception
     */
    public void deleteIndex(String blogId)throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteDocuments(new Term("id",blogId));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

}
