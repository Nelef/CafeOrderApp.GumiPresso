package com.ssafy.gumipresso.model.dto

class ImageFile(var fileName: String, var url: String) {
    var id: Int? = 0
    constructor(id: Int?, fileName: String, url: String): this(fileName, url){
        this.id = id
    }

}