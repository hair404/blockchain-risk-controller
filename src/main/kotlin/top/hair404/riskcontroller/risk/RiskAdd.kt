package top.hair404.riskcontroller.risk

import top.hair404.riskcontroller.School

class RiskAdd(val school: School) : Risk {
    override fun getType(): RiskType {
        return RiskType.ADD
    }

    override fun toRiskyString(): String {
        return "风险类型: 新学校添加\n" +
                "学校数据: $school"
    }


}