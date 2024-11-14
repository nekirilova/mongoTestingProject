package nosql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import dto.mongoDbDto.ConstantMongoDbDto;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static config.ConfigUtils.cfg;

public class MongoDbMethods {
    MongoClient mongoClient = MongoClients.create(cfg.ConnectionString());
    MongoDatabase hogwartsDb = mongoClient.getDatabase(cfg.Database());

    public MongoCollection<Document> getCollectionByName(String collectionName) {
        return hogwartsDb.getCollection(collectionName);
    }

    public String getConstantValueByName(String collectionName, String name) throws JsonProcessingException {
        //выбираем коллекцию по названию
        MongoCollection<Document> collection = getCollectionByName(collectionName);
        //создаем фильтр по полю name и находим константу по имени
        Bson filter = eq("name", name);
        String json = Objects.requireNonNull(collection.find(filter).first()).toJson();
        //получаем значение константы
        return readValue(json).getValue();
    }

    public UpdateResult changeConstantByName(String configName, String newConfigValue) throws JsonProcessingException {
        //выбираем коллекцию по названию
        MongoCollection<Document> constantCollection = getCollectionByName("constant");
        //создаем фильтр по полю name и находим константу по имени
        Bson filter = eq("name", configName);
        String json = Objects.requireNonNull(constantCollection.find(filter).first()).toJson();
        //сохраняем документ в виде Java объекта
        ConstantMongoDbDto constant = readValue(json);
        //задаем новое значение для константы
        constant.setValue(newConfigValue);

        //сериализуем объект в json, потом в Document
        String constantJson = new Gson().toJson(constant);
        Document newDocument = Document.parse(constantJson);
        //заменяем документ с константой в коллекции
        return getCollectionByName("constant").replaceOne(filter, newDocument);
    }

    private ConstantMongoDbDto readValue(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ConstantMongoDbDto.class);
    }
}
