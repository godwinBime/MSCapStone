package com.example.model
//
//import io.realm.kotlin.types.RealmObject
//import io.realm.kotlin.types.annotations.PrimaryKey
//import java.util.UUID
//
//class User(): RealmObject {
//    @PrimaryKey
//    var id: String = ""
//    var firstName: String = ""
//    var lastName: String = ""
//    var email: String = ""
//    var phoneNumber: String = ""
//    var password: String = ""
//    var confirmPassword: String = ""
//
//    constructor(
//        id: String = UUID.randomUUID().toString(),
//        firstName: String,
//        lastName: String,
//        email: String,
//        phoneNumber: String,
//        password: String,
//        confirmPassword: String
//    ) : this() {
//        this.firstName = firstName
//        this.lastName = lastName
//        this.email = email
//        this.phoneNumber = phoneNumber
//        this.password = password
//        this.confirmPassword = confirmPassword
//        this.id = id
//    }
//
//    //Check if user input matches database records
//    override fun equals(other: Any?): Boolean {
//        if (this === other) {
//            return true
//        }
//
//        if (javaClass != other?.javaClass) {
//            return false
//        }
//
//        other as User
//
//        if (id != other.id) {
//            return false
//        }
//
//        if (firstName != other.firstName) {
//            return false
//        }
//
//        if (lastName != other.lastName) {
//            return false
//        }
//
//        if (email != other.email) {
//            return false
//        }
//
//        if (phoneNumber != other.phoneNumber) {
//            return false
//        }
//
//        if (password != other.password) {
//            return false
//        }
//
//        if (confirmPassword != other.confirmPassword) {
//            return false
//        }
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = id.hashCode()
//        result = 31 * result + firstName.hashCode()
//        result = 31 * result + lastName.hashCode()
//        result = 31 * result + email.hashCode()
//        result = 31 * result + phoneNumber.hashCode()
//        result = 31 * result + password.hashCode()
//        result = 31 * result + confirmPassword.hashCode()
//        return result
//    }
//
//    override fun toString(): String {
//        return super.toString()
//    }
//}
