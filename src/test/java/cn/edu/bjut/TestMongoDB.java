package cn.edu.bjut;

import cn.edu.bjut.MongoRepository.UserRepository;
import cn.edu.bjut.model.User;
import cn.edu.bjut.service.UserService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/23.
 */
public class TestMongoDB {

    public static void testMongo(){
        // 连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient( "113.209.24.0" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("runoob");

        //创建集合
        mongoDatabase.createCollection("test1");

        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("test1");

        //插入文档
        //插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
        Document document = new Document("title", "MongoDB").
                append("description", "database").
                append("likes", 100).
                append("by", "Fly");
        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        collection.insertMany(documents);
        System.out.println("文档插入成功");

    }
    @Autowired
    UserService userService;
    @Test
    public void testUserRepository(){
        System.out.println(userService.getUserByName("18811713280@163.com"));
    }
    public static void main(String[] args) {

        //testMongo();
    }
}
