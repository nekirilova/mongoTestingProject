package tests;

import apiMethods.LogicMethods;
import apiMethods.UsersMethods;
import nosql.MongoDbMethods;

public class BaseTest {
   protected MongoDbMethods mongoDbMethods = new MongoDbMethods();
   static protected UsersMethods usersMethods = new UsersMethods();
   static protected LogicMethods logicMethods = new LogicMethods();
}
