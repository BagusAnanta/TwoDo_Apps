package com.example.twodoapps.dataClassTodo

import java.sql.Timestamp

data class Twododata(
    var id : String = "",
    var name : String = "",
    var status : Boolean = false,
    var createAt : Timestamp = Timestamp(System.currentTimeMillis()),
    var updateAt : Timestamp = Timestamp(System.currentTimeMillis())
)
