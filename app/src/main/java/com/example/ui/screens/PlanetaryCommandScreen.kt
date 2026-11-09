package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AetherOmegaViewModel

@Composable
fun PlanetaryCommandScreen(
    viewModel: AetherOmegaViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.planetaryState.collectAsState()
    val tokamak by viewModel.tokamakParams.collectAsState()
    val consoleLogs by viewModel.consoleLogs.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("planetary_command_screen"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Banner with Generated Sci-Fi Bridge Asset
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_planetary_1794220375686),
                    contentDescription = "Planetary Command Bridge",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // High-tech gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    SpaceBackground.copy(alpha = 0.5f),
                                    SpaceBackground
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (state.isScramActive) PlasmaRuby.copy(alpha = 0.2f) else LaserEmerald.copy(alpha = 0.2f),
                                RoundedCornerShape(6.dp)
                            )
                            .border(
                                1.dp,
                                if (state.isScramActive) PlasmaRuby else LaserEmerald,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = state.threatLevel,
                            color = if (state.isScramActive) PlasmaRuby else LaserEmerald,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "PROJECT AETHER-OMEGA (2027)",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Autonomous Exo-Planetary Fusion, Quantum Grid & Climate Homeostasis",
                        color = NeonCyanDim,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Top 4 Metrics Grid
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Fusion Output",
                        value = "${"%.1f".format(state.fusionOutputTw)}",
                        unit = "TW",
                        accentColor = if (state.isScramActive) PlasmaRuby else NeonCyan,
                        statusBadge = if (tokamak.isIgnited) "IGNITED" else "SUB-CRIT",
                        modifier = Modifier.weight(1f),
                        testTag = "metric_fusion_output"
                    )
                    MetricCard(
                        title = "Orbital Beaming",
                        value = "${"%.0f".format(state.orbitalBeamingMw)}",
                        unit = "MW",
                        accentColor = SolarAmber,
                        statusBadge = "J2 LOCKED",
                        modifier = Modifier.weight(1f),
                        testTag = "metric_orbital_beaming"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Atmospheric CO₂",
                        value = "${"%.1f".format(state.atmosphericCarbonPpm)}",
                        unit = "ppm",
                        accentColor = LaserEmerald,
                        statusBadge = "PINN TARGET",
                        modifier = Modifier.weight(1f),
                        testTag = "metric_carbon"
                    )
                    MetricCard(
                        title = "Grid Homeostasis",
                        value = "${"%.2f".format(state.gridStabilityPct)}",
                        unit = "%",
                        accentColor = ElectricViolet,
                        statusBadge = "PQC-DAG",
                        modifier = Modifier.weight(1f),
                        testTag = "metric_grid_stability"
                    )
                }
            }
        }

        // Interactive Tokamak Plasma Vortex Canvas
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "TERRESTRIAL TOKAMAK CONFINEMENT CORE",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                PlasmaVortexCanvas(
                    magneticTesla = tokamak.toroidalMagneticFieldT,
                    isScramActive = state.isScramActive,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                // Tokamak Live Telemetry Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Lawson Triple Product (n·T·τ)",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${"%.2e".format(tokamak.lawsonTripleProduct)} m⁻³ keV s",
                                color = if (tokamak.isIgnited) LaserEmerald else SolarAmber,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        LinearProgressIndicator(
                            progress = { (tokamak.lawsonTripleProduct / 5.0e21).toFloat().coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = if (tokamak.isIgnited) LaserEmerald else SolarAmber,
                            trackColor = SurfaceElevated
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Ion Temp: ${tokamak.ionTemperatureKev} keV",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "B-Field: ${tokamak.toroidalMagneticFieldT} T",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Quench: ${state.quenchSafetyMarginK} K",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // Interactive Tuning Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.adjustMagneticField(-0.5) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_mag_minus"),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan)
                            ) {
                                Text("-0.5T", fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = { viewModel.adjustMagneticField(0.5) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_mag_plus"),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan)
                            ) {
                                Text("+0.5T", fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = { viewModel.adjustIonTemperature(1.0) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_heat_plus"),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = SolarAmber)
                            ) {
                                Text("+1.0keV", fontSize = 11.sp)
                            }
                        }

                        // SCRAM / Recovery Button
                        if (!state.isScramActive) {
                            Button(
                                onClick = { viewModel.triggerScramEmergency() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_scram_trigger"),
                                colors = ButtonDefaults.buttonColors(containerColor = PlasmaRuby),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "TRIGGER EMERGENCY DISRUPTION SCRAM",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        } else {
                            Button(
                                onClick = { viewModel.resetScramInterlock() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_scram_reset"),
                                colors = ButtonDefaults.buttonColors(containerColor = LaserEmerald),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "CLEAR INTERLOCK & RE-IGNITE PLASMA",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }

        // Live Telemetry Stream
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PLANETARY TELEMETRY LOG",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "LIVE TAISYNC",
                        color = LaserEmerald,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = SpaceBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        consoleLogs.take(5).forEach { log ->
                            Text(
                                text = log,
                                color = when {
                                    log.contains("SCRAM") || log.contains("CRITICAL") -> PlasmaRuby
                                    log.contains("FINALIZED") || log.contains("nominal") || log.contains("verified") -> LaserEmerald
                                    log.contains("PQC") || log.contains("BENCHMARK") -> NeonCyan
                                    else -> TextSecondary
                                },
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
