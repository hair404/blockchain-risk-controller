package top.hair404.riskcontroller.risk

import top.hair404.riskcontroller.School

class RiskDelete(val school: School) : Risk {
    override fun getType(): RiskType {
        return RiskType.DELETE
    }

    override fun toRiskyString(): String {
        return "风险类型: 学校删除\n" +
                "学校数据: $school"
    }
}