package com.example.model

enum class AgentStatus {
    IDLE,
    ACTIVE,
    REASONING,
    VERIFYING,
    COMMITTED
}

data class AgentModel(
    val id: String,
    val name: String,
    val role: String,
    val clearance: Int,
    val status: AgentStatus,
    val domain: String,
    val currentTask: String,
    val avatarSymbol: String
)

data class PlanetaryHomeostasis(
    val fusionOutputTw: Double = 42.8,
    val targetOutputTw: Double = 50.0,
    val orbitalBeamingMw: Double = 8400.0,
    val atmosphericCarbonPpm: Double = 348.2,
    val gridStabilityPct: Double = 99.98,
    val relativisticLeadUrad: Double = 48.54,
    val quenchSafetyMarginK: Double = 6.4,
    val maxCoilTempK: Double = 12.1,
    val isScramActive: Boolean = false,
    val threatLevel: String = "DEFCON 5 / NOMINAL",
    val activeAlarmCount: Int = 0
)

data class DagBlock(
    val blockId: String,
    val author: String,
    val round: Int,
    val parentHash: String,
    val timestampNs: Long,
    val digest: String,
    val payloadSummary: String,
    val isVerified: Boolean = true
)

enum class EngineCategory {
    DISTRIBUTED_STATE,
    FRONTIER_AI,
    FINANCE_DURABLE,
    PHYSICS_QUANTUM
}

data class EngineBenchmark(
    val id: String,
    val name: String,
    val lineage: String,
    val category: EngineCategory,
    val latencyUs: Double,
    val keyMetric: String,
    val description: String,
    val details: String
)

data class DoubleEntryRecord(
    val id: String,
    val idempotencyKey: String,
    val debitAccount: String,
    val creditAccount: String,
    val amountCents: Long,
    val timestamp: Long,
    val isZeroSumVerified: Boolean,
    val isIdempotentHit: Boolean
)

data class SurfaceCodeGrid(
    val dataQubits: List<QubitState> = List(9) { QubitState(it, hasBitFlipX = false, hasPhaseFlipZ = false) },
    val xSyndromes: List<Int> = List(4) { 0 },
    val zSyndromes: List<Int> = List(4) { 0 },
    val correctedQubitIndex: Int? = null,
    val logicalFidelity: Double = 1.0
)

data class QubitState(
    val index: Int,
    val hasBitFlipX: Boolean,
    val hasPhaseFlipZ: Boolean
)

data class TokamakSimParams(
    val electronDensity: Double = 1.8e20, // m^-3
    val ionTemperatureKev: Double = 16.5,  // keV
    val confinementTimeSec: Double = 2.4,  // s
    val toroidalMagneticFieldT: Double = 19.6, // Tesla
    val plasmaCurrentMa: Double = 15.0,    // MA
    val viscosityNu: Double = 0.01,
    val resistivityEta: Double = 0.01
) {
    // Lawson Triple Product: n * T * tau >= 3.0e21 m^-3 keV s for ignition
    val lawsonTripleProduct: Double
        get() = electronDensity * ionTemperatureKev * confinementTimeSec

    val isIgnited: Boolean
        get() = lawsonTripleProduct >= 3.0e21
}

enum class NavigationTab(val title: String) {
    COMMAND("Command"),
    SWARM("Swarm"),
    LABORATORY("Engines"),
    CODEX("Codex")
}
