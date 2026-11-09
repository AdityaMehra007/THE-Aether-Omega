package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.model.EngineCategory
import com.example.ui.components.EngineBenchmarkCard
import com.example.ui.components.SurfaceCodeLatticeCanvas
import com.example.ui.theme.*
import com.example.viewmodel.AetherOmegaViewModel

@Composable
fun EngineLabScreen(
    viewModel: AetherOmegaViewModel,
    modifier: Modifier = Modifier
) {
    val benchmarks by viewModel.engineBenchmarks.collectAsState()
    val surfaceCode by viewModel.surfaceCodeState.collectAsState()
    val ledgerRecords by viewModel.ledgerRecords.collectAsState()

    var selectedCategory by remember { mutableStateOf<EngineCategory?>(null) }
    var transferAmount by remember { mutableStateOf("500000") }

    val filteredBenchmarks = remember(benchmarks, selectedCategory) {
        if (selectedCategory == null) benchmarks
        else benchmarks.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("engine_lab_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header & Benchmark Button
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "15-ENGINE BENCHMARK & SIMULATION LABORATORY",
                    color = NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Planetary & MNC Tech Singularity Engines",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Live mathematical execution spanning Google Spanner HLC, AWS Aurora Quorums, vLLM PagedAttention, Pinecone PQ, Stripe Double-Entry, and Rotated Surface Codes.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { viewModel.runFullBenchmarkSuite() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_run_benchmarks"),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = SpaceBackground),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "EXECUTE ALL 15 ENGINES LIVE",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val categories = listOf(
                    null to "ALL",
                    EngineCategory.DISTRIBUTED_STATE to "STATE",
                    EngineCategory.FRONTIER_AI to "AI",
                    EngineCategory.FINANCE_DURABLE to "FINANCE",
                    EngineCategory.PHYSICS_QUANTUM to "PHYSICS"
                )

                categories.forEach { (cat, label) ->
                    val isSelected = (selectedCategory == cat)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) NeonCyan else SurfaceElevated)
                            .border(1.dp, if (isSelected) NeonCyan else SurfaceBorder, RoundedCornerShape(6.dp))
                            .clickable { selectedCategory = cat }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) SpaceBackground else TextSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Interactive Quantum Surface Code Playground
        item {
            SurfaceCodeLatticeCanvas(
                surfaceCode = surfaceCode,
                onQubitClick = { qubitIndex ->
                    viewModel.injectQuantumError(qubitIndex, isBitFlip = true)
                }
            )
        }

        // Interactive Stripe / TigerBeetle Financial Ledger Playground
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "STRIPE / TIGERBEETLE LEDGER PLAYGROUND",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Box(
                            modifier = Modifier
                                .background(LaserEmerald.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "∑BALANCES = 0 (CERTIFIED)",
                                color = LaserEmerald,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Text(
                        text = "Transfer energy-credits from Treasury to Tokamak clearing account. Invariant requires zero money creation.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = transferAmount,
                            onValueChange = { transferAmount = it },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_transfer_amount"),
                            label = { Text("Amount (Cents)", fontSize = 11.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = SurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Button(
                            onClick = {
                                val cents = transferAmount.toLongOrNull() ?: 500000L
                                viewModel.postCustomTransfer("treasury:omnibus", "grid:tokamak_clearing", cents)
                            },
                            modifier = Modifier.testTag("btn_post_transfer"),
                            colors = ButtonDefaults.buttonColors(containerColor = SolarAmber, contentColor = SpaceBackground),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "POST TRANSFER",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Recent transfers summary
                    ledgerRecords.take(2).forEach { rec ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceElevated, RoundedCornerShape(6.dp))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${rec.debitAccount} -> ${rec.creditAccount}",
                                color = TextSecondary,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "$${"%.2f".format(rec.amountCents / 100.0)}",
                                color = LaserEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        // Section Title for Benchmark Cards
        item {
            Text(
                text = "ENGINE LATENCY & MATHEMATICAL SPECS (${filteredBenchmarks.size})",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        // List of Engine Benchmarks
        items(filteredBenchmarks) { benchmark ->
            EngineBenchmarkCard(benchmark = benchmark)
        }
    }
}
