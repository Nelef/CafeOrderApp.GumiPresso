package com.ssafy.gumipresso_admin.model.dto

import java.io.Serializable

data class Banner(var img: String?, var url: String?): Serializable {
    var id: Int? = 0

    constructor(id: Int, img: String?, url: String?) : this(img, url)
}