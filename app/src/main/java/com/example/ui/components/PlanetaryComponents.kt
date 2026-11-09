package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*
import kotlin.math.*

@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    statusBadge: String? = null,
    testTag: String = "metric_card"
) {
    Card(
        modifier = modifier
            .testTag(testTag)
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title.uppercase(),
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Monospace
                )
                if (statusBadge != null) {
                    Box(
                        modifier = Modifier
                            .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                            .border(0.5.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = statusBadge,
                            color = accentColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                if (unit.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = unit,
                        color = accentColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            // Futuristic glowing accent line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(accentColor, accentColor.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )
            )
        }
    }
}

@Composable
fun PlasmaVortexCanvas(
    magneticTesla: Double,
    isScramActive: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "plasma_transition")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isScramActive) 10000 else 4000, easing = LinearEasing)
        ),
        label = "plasma_phase"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SpaceBackground)
            .border(1.dp, if (isScramActive) PlasmaRuby.copy(alpha = 0.8f) else NeonCyanDim.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = min(size.width, size.height) * 0.42f

            // Magnetic boundary coils (18 segments around the torus)
            val coilCount = 18
            for (i in 0 until coilCount) {
                val angle = (2 * PI * i / coilCount).toFloat()
                val coilX = center.x + radius * 1.05f * cos(angle)
                val coilY = center.y + radius * 1.05f * sin(angle)
                drawCircle(
                    color = if (isScramActive) PlasmaRuby else NeonCyanDim,
                    radius = 3.5f,
                    center = Offset(coilX, coilY)
                )
            }

            // Magnetic containment rings
            val ringCount = 5
            for (r in 1..ringCount) {
                val currentRadius = radius * (r.toFloat() / ringCount)
                drawCircle(
                    color = if (isScramActive) PlasmaRuby.copy(alpha = 0.2f) else ElectricVioletDim.copy(alpha = 0.25f),
                    radius = currentRadius,
                    center = center,
                    style = Stroke(width = 1.5f)
                )
            }

            // Swirling plasma stream particles
            if (!isScramActive) {
                val particleCount = 42
                for (i in 0 until particleCount) {
                    val angleOffset = (2 * PI * i / particleCount).toFloat()
                    val theta = angleOffset + phase * (1.2f - (i % 3) * 0.2f)
                    val rMod = radius * (0.3f + 0.6f * sin(theta * 2f + phase).absoluteValue)
                    val px = center.x + rMod * cos(theta)
                    val py = center.y + rMod * sin(theta)

                    val particleColor = if (i % 2 == 0) NeonCyan else SolarAmber
                    drawCircle(
                        color = particleColor.copy(alpha = 0.85f),
                        radius = 2.5f + (i % 3),
                        center = Offset(px, py)
                    )
                }

                // Super-hot Tokamak fusion core glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White,
                            NeonCyan.copy(alpha = 0.8f),
                            ElectricViolet.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius * 0.35f
                    ),
                    radius = radius * 0.35f,
                    center = center
                )
            } else {
                // SCRAM extinguished core
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PlasmaRuby.copy(alpha = 0.5f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius * 0.25f
                    ),
                    radius = radius * 0.25f,
                    center = center
                )
            }
        }

        // Overlay status indicators
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(SurfaceDark.copy(alpha = 0.85f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isScramActive) "CORE QUENCHED / SCRAM" else "PLASMA IGNITED (${"%.1f".format(magneticTesla)}T)",
                    color = if (isScramActive) PlasmaRuby else LaserEmerald,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun SurfaceCodeLatticeCanvas(
    surfaceCode: SurfaceCodeGrid,
    onQubitClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ROTATED SURFACE CODE (d=3 PATCH)",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "FIDELITY: ${"%.1f".format(surfaceCode.logicalFidelity * 100)}%",
                    color = if (surfaceCode.logicalFidelity >= 1.0) LaserEmerald else PlasmaRuby,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = "Tap any of the 9 Data Qubits to inject a bit-flip. The MWPM decoder extracts non-demolition syndromes to correct it.",
                color = TextSecondary,
                fontSize = 11.sp
            )

            // 3x3 Data Qubit Grid with Plaquet Ancillas
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0 until 3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (col in 0 until 3) {
                            val qIndex = row * 3 + col
                            val qubit = surfaceCode.dataQubits[qIndex]
                            val isCorrected = (surfaceCode.correctedQubitIndex == qIndex)

                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        when {
                                            qubit.hasBitFlipX -> PlasmaRuby.copy(alpha = 0.25f)
                                            qubit.hasPhaseFlipZ -> ElectricViolet.copy(alpha = 0.25f)
                                            isCorrected -> LaserEmerald.copy(alpha = 0.2f)
                                            else -> SurfaceElevated
                                        }
                                    )
                                    .border(
                                        1.5.dp,
                                        when {
                                            qubit.hasBitFlipX -> PlasmaRuby
                                            qubit.hasPhaseFlipZ -> ElectricViolet
                                            isCorrected -> LaserEmerald
                                            else -> SurfaceBorder
                                        },
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { onQubitClick(qIndex) }
                                    .testTag("qubit_$qIndex"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Q$qIndex",
                                        color = TextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = when {
                                            qubit.hasBitFlipX -> "ERR: X"
                                            qubit.hasPhaseFlipZ -> "ERR: Z"
                                            isCorrected -> "MWPM ✓"
                                            else -> "|0_L⟩"
                                        },
                                        color = when {
                                            qubit.hasBitFlipX -> PlasmaRuby
                                            qubit.hasPhaseFlipZ -> ElectricViolet
                                            isCorrected -> LaserEmerald
                                            else -> NeonCyanDim
                                        },
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Syndrome indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Z-Syndromes: ${surfaceCode.zSyndromes}",
                    color = NeonCyan,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "X-Syndromes: ${surfaceCode.xSyndromes}",
                    color = ElectricViolet,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun DagBlockCard(
    block: DagBlock,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(LaserEmerald, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = block.blockId,
                        color = NeonCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Box(
                    modifier = Modifier
                        .background(SurfaceElevated, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "ROUND #${block.round}",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Text(
                text = block.payloadSummary,
                color = TextPrimary,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Author: ${block.author}",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Parent: ${block.parentHash}",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = "SHA3-512: ${block.digest.take(24)}...",
                color = ElectricVioletDim,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun EngineBenchmarkCard(
    benchmark: EngineBenchmark,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = benchmark.name,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = benchmark.lineage,
                        color = NeonCyanDim,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Latency Badge
                Box(
                    modifier = Modifier
                        .background(
                            when {
                                benchmark.latencyUs < 50.0 -> LaserEmerald.copy(alpha = 0.15f)
                                benchmark.latencyUs < 1000.0 -> SolarAmber.copy(alpha = 0.15f)
                                else -> ElectricViolet.copy(alpha = 0.15f)
                            },
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${"%.2f".format(benchmark.latencyUs)} µs",
                        color = when {
                            benchmark.latencyUs < 50.0 -> LaserEmerald
                            benchmark.latencyUs < 1000.0 -> SolarAmber
                            else -> ElectricViolet
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceElevated, RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = benchmark.keyMetric,
                    color = TextPrimary,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = benchmark.description,
                color = TextSecondary,
                fontSize = 11.sp
            )

            Text(
                text = "▶ ${benchmark.details}",
                color = TextMuted,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
