package top.hair404.riskcontroller

import com.alibaba.fastjson.JSONObject

class School(val address: String?, val name: String?, val educationId: String?, val endTime: Long) {

    companion object {
        fun fromJsonObject(json: JSONObject): School {
            return School(
                address = json.getString("address"),
                name = json.getString("name"),
                educationId = json.getString("educationId"),
                endTime = json.getLongValue("endTime")
            )
        }
    }

    override fun toString(): String {
        return "地址=$address," +
                "名称=$name," +
                "教育部ID=$educationId" +
                "截止日期=$endTime"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is School) {
            return false
        }
        return (this.name == other.name) &&
                (this.address == other.address) &&
                (this.educationId == other.educationId) &&
                (this.endTime == other.endTime)
    }
}