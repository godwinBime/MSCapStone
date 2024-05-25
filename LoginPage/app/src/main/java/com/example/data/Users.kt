package com.example.data
//
//import com.example.dbconnection.MongoDBConnection
//import com.mongodb.client.MongoClients
//import com.mongodb.client.MongoCollection
//import com.mongodb.client.MongoDatabase
//import org.bson.Document
//
//object MongoDBUser{
//
//    // Connection to db
//    fun connectToDB(): MongoCollection<Document>{
//        val updateClient = MongoDBConnection.getMongoDBClient()
//        val database = updateClient.getDatabase("mscs-fp")
//        val collection = database.getCollection("user")
//
//        return collection
//    }
//
//    //Insert a single user
//    fun insertUser(){
//        val insertClient = MongoDBConnection.getMongoDBClient()
//        val database : MongoDatabase = insertClient.getDatabase("mscs-fp")
//        val collection: MongoCollection<Document> = database.getCollection("user")
//
//        val document = Document("name", "John Doe")
//            .append("email", "johndoe@example.com")
//            .append("age", 30)
//
//        collection.insertOne(document)
//        insertClient.close()
//
//    }
//
//    //Read data
//    fun readData(){
//        val readClient = MongoDBConnection.getMongoDBClient()
//        val database = readClient.getDatabase("mscs-fp")
//        val collection = database.getCollection("user")
//
//        val query = Document("name", "John Doe")
//        val result = collection.find(query)
//        result.forEach{ println(it)}
//        readClient.close()
//    }
//
//    //Update data
//    fun updateData(){
//        val updateClient = MongoDBConnection.getMongoDBClient()
//        val database = updateClient.getDatabase("mscs-fp")
//        val collection = database.getCollection("user")
//
//        val filter = Document("name", "John Doe")
//        val update = Document("\$set", Document("age", 31))
//
//        collection.updateOne(filter, update)
//        updateClient.close()
//    }
//
//    //Delete data/user
//    fun deleteData(){
//        val deleteClient = MongoDBConnection.getMongoDBClient()
//        val database = deleteClient.getDatabase("mscs-fp")
//        val collection = database.getCollection("user")
//
//        val filter = Document("name", "John Doe")
//        collection.deleteOne(filter)
//        deleteClient.close()
//    }
//}