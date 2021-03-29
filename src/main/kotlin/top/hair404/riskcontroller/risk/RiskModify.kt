package top.hair404.riskcontroller.risk

import top.hair404.riskcontroller.School

class RiskModify(val oldSchool: School, val newSchool: School) : Risk {

    override fun getType(): RiskType {
        return RiskType.MODIFY
    }

    override fun toRiskyString(): String {
        return "风险类型: 学校数据修改\n" +
                "原学校数据: $oldSchool\n" +
                "新学校数据: $newSchool\n"
    }

}