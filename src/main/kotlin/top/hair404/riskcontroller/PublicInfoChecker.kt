package top.hair404.riskcontroller

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import okhttp3.*
import top.hair404.riskcontroller.email.EmailUtil
import top.hair404.riskcontroller.risk.Risk
import top.hair404.riskcontroller.risk.RiskAdd
import top.hair404.riskcontroller.risk.RiskDelete
import top.hair404.riskcontroller.risk.RiskModify
import java.io.IOException

class PublicInfoChecker {

    val client = OkHttpClient.Builder().build()
    var schools = HashMap<Int, School>()
    val request = Request.Builder()
        .url("${Config.PUBLIC_SERVER}/school/get")
        .build()

    init {

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                println("网络错误")
                // TODO
            }

            override fun onResponse(call: Call, response: Response) {
                val json = JSON.parseObject(response.body!!.string())
                val schoolJsonArray = json.getJSONArray("schools")
                schoolJsonArray.map { it as JSONObject }.forEach {
                    schools[it.getIntValue("id")] = School.fromJsonObject(it)
                }
                println("系统")
            }
        })

        startChecker()
    }

    private fun getServerSchools(): HashMap<Int, School> {
        val response = client.newCall(request).execute()
        val json = JSON.parseObject(response.body!!.string())
        val schoolJsonArray = json.getJSONArray("schools")
        val serverSchools = HashMap<Int, School>()
        schoolJsonArray.map { it as JSONObject }.forEach {
            serverSchools[it.getIntValue("id")] = School.fromJsonObject(it)
        }
        return serverSchools
    }

    private fun startChecker() {
        while (true) {
            Thread.sleep(Config.CHECK_INTERVAL)
            val risks = ArrayList<Risk>()
            val serverSchools = getServerSchools()

            //新添加风险
            serverSchools.filter { !schools.keys.contains(it.key) }.forEach {
                risks.add(
                    RiskAdd(
                        school = it.value
                    )
                )
            }

            //修改风险
            serverSchools.filter { schools.keys.contains(it.key) }.forEach {
                if (schools[it.key]!! != it.value) {
                    risks.add(
                        RiskModify(
                            oldSchool = schools[it.key]!!,
                            newSchool = it.value
                        )
                    )
                }
            }

            //删除风险
            schools.filter { !serverSchools.containsKey(it.key) }.forEach {
                risks.add(
                    RiskDelete(
                        school = it.value
                    )
                )
            }

            if (risks.isNotEmpty()) {
                var text = "尊敬的管理员:\n" +
                        "   学位管理系统的公共服务器监测到了以下修改，若非授权操作，请即使检查相关的密码是否泄漏：\n\n"
                risks.forEach {
                    text += it.toRiskyString()
                    text += "\n\n"
                }

                EmailUtil.sendEmail(Config.NOTICE_EMAIL, "学位管理系统公共服务器修改提醒", text)

                println("检查到了 ${risks.size} 个风险，已通过邮件通知管理员")

                risks.clear()
                schools = serverSchools
            }
        }
    }
}