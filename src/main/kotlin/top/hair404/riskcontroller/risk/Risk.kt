package top.hair404.riskcontroller.risk

interface Risk {

    fun getType(): RiskType

    fun toRiskyString() : String
}