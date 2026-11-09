package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class CodexPillar(
    val id: String,
    val title: String,
    val subtitle: String,
    val corporateLineage: String,
    val mathematicalFormula: String,
    val explanation: String,
    val hardwareImplementation: String
)

@Composable
fun ArchitectureCodexScreen(
    modifier: Modifier = Modifier
) {
    val pillars = remember {
        listOf(
            CodexPillar(
                id = "P1",
                title = "I. The Speed of Light & Distributed Causality",
                subtitle = "Atomic Clocks, TrueTime Intervals, and Quorum Overlaps",
                corporateLineage = "Google Spanner, CockroachDB, AWS Aurora, Figma CRDTs",
                mathematicalFormula = "TrueTime: [t_earliest, t_latest], ε ≈ 1.2ms | Commit Wait: 2ε | Quorum: V_w + V_r = 4 + 3 = 7 > 6",
                explanation = "Light takes 40-70ms to travel between continents. Without synchronized clocks, servers overwrite newer writes with older writes due to quartz drift. Spanner solves this with Rubidium atomic clocks and GPS, enforcing a 2ε commit wait to guarantee linearizable external consistency globally without read locks. Aurora eliminates full-page writes by shipping only redo logs across 6 nodes in 3 AZs.",
                hardwareImplementation = "Rubidium frequency standards + GPS antenna arrays in datacenter cages, hardware PPS interrupts."
            ),
            CodexPillar(
                id = "P2",
                title = "II. The Memory Wall & Mechanical Sympathy",
                subtitle = "Online Softmax Tiling & Cache-Line Alignment",
                corporateLineage = "OpenAI, vLLM, Tri Dao (FlashAttention), Jane Street, TigerBeetle",
                mathematicalFormula = "O_new = (α·l·O_old + P_tile·V_tile) / l_new, where α = exp(m_old - m_new) | struct align(64)",
                explanation = "ALUs compute in <1ns, but fetching from HBM3e/DRAM takes ~200ns. FlashAttention avoids materializing N×N attention matrices by computing online softmax dynamically in 128KB on-chip SRAM. Jane Street and TigerBeetle pad lock-free ring buffer pointers to 64 bytes (the CPU L1 cache line size) to prevent cross-core False Sharing.",
                hardwareImplementation = "NVIDIA Hopper H100 Tensor Core WGMMA, x86_64 L1D cache-line alignment to eliminate MESI invalidations."
            ),
            CodexPillar(
                id = "P3",
                title = "III. High-Dimensional Geometry & Contextual Bandits",
                subtitle = "Subspace Product Quantization & LinUCB Exploration",
                corporateLineage = "Pinecone, Turbopuffer, ByteDance (TikTok Monolith)",
                mathematicalFormula = "PQ: R^D -> (R^(D/M))^M | LinUCB: Score_a(x) = θ_a^T·x + α·√(x^T·A_a⁻¹·x)",
                explanation = "One billion 1536-D vectors require 6.14 TB of RAM. Product Quantization divides vectors into M=64 subspaces with 256 centroids, compressing 6,144 bytes to 64 bytes. Queries are scored via 64 table additions without floating-point multiplies. TikTok's LinUCB balances exploration vs exploitation via confidence ellipsoids.",
                hardwareImplementation = "SIMD AVX-512 / ARM NEON gather-scatter lookups, streaming parameter synchronization."
            ),
            CodexPillar(
                id = "P4",
                title = "IV. Deterministic State Machines & Ledgers",
                subtitle = "Idempotent Accounting & Event-Sourced Replays",
                corporateLineage = "Stripe, TigerBeetle, Citadel, Temporal",
                mathematicalFormula = "∑(All Account Balances) ≡ 0 at every instruction | WAL Event Replay: Hash(idempotency_key)",
                explanation = "In financial systems, money cannot be created from thin air. Every transaction debits one account and credits another, enforcing an invariant sum of zero. Idempotency keys prevent double-charging on network retries. Temporal's event-sourcing records each workflow step so that if a node crashes, it recovers by replaying past steps in 0µs.",
                hardwareImplementation = "Append-only NVMe write-ahead logs with CRC32C / BLAKE3 checksumming."
            ),
            CodexPillar(
                id = "P5",
                title = "V. Continuum Plasma & Divergence-Free PINNs",
                subtitle = "Fourier Spectral MHD & Autograd Curl Parameterization",
                corporateLineage = "ITER, Commonwealth Fusion, Earth-2027 Exascale Twin",
                mathematicalFormula = "u = ∇ × ψ => ∇·u ≡ 0 | B = ∇ × φ => ∇·B ≡ 0 | ∂ω/∂t + (u·∇)ω = (B·∇)J + ν∇²ω",
                explanation = "Tokamak plasma is an electrically conducting fluid governed by coupled Navier-Stokes and Maxwell equations. Standard finite elements suffer numerical grid diffusion. Aether-Omega solves it in Fourier space where Laplacians become simple array multiplications (-k²). Our PINN outputs vector potentials ψ and φ, ensuring zero mass leakage and zero magnetic monopoles to machine precision.",
                hardwareImplementation = "Fourier pseudo-spectral FFT pipelines with 2/3 Orszag de-aliasing masks on GPU clusters."
            ),
            CodexPillar(
                id = "P6",
                title = "VI. Relativistic Aerospace & Formal Lyapunov Proofs",
                subtitle = "Bradley Aberration, J2 Precession & Asymptotic Energy Bowls",
                corporateLineage = "SpaceX Starlink, Anduril Lattice, NASA JPL, AEGIS-FORMAL",
                mathematicalFormula = "Δθ = 2v/c | dΩ/dt = -3/2·n·J2·(R/a)²·cos(i) | A^T·P + P·A = -I, with λ_min(P) > 0",
                explanation = "Beaming orbital laser energy down to Earth requires compensating for the Bradley relativistic point-ahead angle (48.5 µrad) because the satellite travels at 7.28 km/s while light travels to ground in 3.8ms. Earth's oblate equatorial bulge precesses the orbital plane by -3.34°/day. AEGIS-FORMAL verifies system stability by solving the continuous-time Lyapunov matrix equation.",
                hardwareImplementation = "Piezoelectric fast-steering mirror arrays with FPGA sub-microsecond closed-loop control."
            ),
            CodexPillar(
                id = "P7",
                title = "VII. Fault-Tolerant Quantum & Lattice Cryptography",
                subtitle = "Rotated Surface Codes & Ring Learning With Errors",
                corporateLineage = "Google Quantum AI, IBM Quantum, NIST Post-Quantum (FIPS 203/204)",
                mathematicalFormula = "S_Z = Z_a·Z_b·Z_c·Z_d, S_X = X_a·X_b·X_c·X_d | R_q = Z_3329[X]/(X^n + 1)",
                explanation = "The No-Cloning theorem and wave-function collapse prevent copying or directly measuring quantum states. Rotated surface codes solve this by measuring 4-body parity syndromes on helper ancilla qubits, detecting bit and phase flips without disturbing the logical superposition. Post-quantum security uses high-dimensional lattice problems (LWE) that Shor's algorithm cannot solve.",
                hardwareImplementation = "Superconducting transmon qubit dilution refrigerators at 15 mK with FPGA MWPM syndrome decoders."
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("architecture_codex_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ENGINEERING ARCHITECTURE CODEX",
                    color = NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "The 7 Mathematical Pillars of Planetary Scale",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "First-principles deconstruction of the physical bottlenecks and mathematical solutions powering the global technology stack.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        items(pillars) { pillar ->
            CodexPillarCard(pillar = pillar)
        }
    }
}

@Composable
fun CodexPillarCard(pillar: CodexPillar, modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = pillar.title,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pillar.subtitle,
                        color = NeonCyanDim,
                        fontSize = 11.sp
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }

            // Formula highlight
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceElevated, RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = pillar.mathematicalFormula,
                    color = SolarAmber,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "CORPORATE & ACADEMIC LINEAGE",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = pillar.corporateLineage,
                        color = ElectricViolet,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Text(
                        text = "DEEP-DIVE PRINCIPLE",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = pillar.explanation,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Text(
                        text = "SILICON & HARDWARE SUBSTRATE",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = pillar.hardwareImplementation,
                        color = LaserEmerald,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
