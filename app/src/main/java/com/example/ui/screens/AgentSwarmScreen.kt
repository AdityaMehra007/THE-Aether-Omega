package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgentModel
import com.example.model.AgentStatus
import com.example.ui.components.DagBlockCard
import com.example.ui.theme.*
import com.example.viewmodel.AetherOmegaViewModel

@Composable
fun AgentSwarmScreen(
    viewModel: AetherOmegaViewModel,
    modifier: Modifier = Modifier
) {
    val agents by viewModel.agents.collectAsState()
    val dagBlocks by viewModel.dagBlocks.collectAsState()
    var missionPrompt by remember { mutableStateOf("") }

    val quickMissions = listOf(
        "Recalibrate 18 Toroidal Coils",
        "Compute J2 Laser Lead Angle",
        "Quantum QAOA Grid Routing",
        "Audit Ledger Zero-Sum Proof",
        "Stabilize Rossby Wave PINN"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("agent_swarm_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "THE PANTHEON OF AUTONOMOUS AGENTS",
                    color = NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "5-Agent Swarm Consciousness & BFT Consensus Matrix",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Decomposes planetary homeostasis objectives into mathematically verified DAG vertices under AEGIS-FORMAL Lyapunov veto supervision.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // 5 Agent Cards
        items(agents) { agent ->
            AgentCard(agent = agent)
        }

        // Mission Dispatcher Box
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "DISPATCH MISSION TO SWARM",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    // Quick Mission Chips
                    Text(
                        text = "Quick Task Templates:",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        quickMissions.take(2).forEach { qm ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceElevated)
                                    .border(1.dp, SurfaceBorder, RoundedCornerShape(6.dp))
                                    .clickable { missionPrompt = qm }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = qm,
                                    color = NeonCyanDim,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = missionPrompt,
                        onValueChange = { missionPrompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_mission_prompt"),
                        placeholder = {
                            Text(
                                "Enter objective (e.g. Recalibrate Tokamak harmonics)...",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedContainerColor = SpaceBackground,
                            unfocusedContainerColor = SpaceBackground
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = {
                            if (missionPrompt.isNotBlank()) {
                                viewModel.dispatchSwarmMission(missionPrompt)
                                missionPrompt = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_dispatch_mission"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = SpaceBackground),
                        shape = RoundedCornerShape(10.dp),
                        enabled = missionPrompt.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EXECUTE BFT SWARM ROUND",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Cryptographic DAG Blockchain Explorer
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BYZANTINE FAULT TOLERANT DAG LEDGER",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "${dagBlocks.size} VERTICES",
                        color = LaserEmerald,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        items(dagBlocks) { block ->
            DagBlockCard(block = block)
        }
    }
}

@Composable
fun AgentCard(agent: AgentModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(SurfaceElevated, CircleShape)
                            .border(1.dp, NeonCyanDim.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = agent.avatarSymbol, fontSize = 18.sp)
                    }

                    Column {
                        Text(
                            text = agent.name,
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = agent.role,
                            color = NeonCyanDim,
                            fontSize = 11.sp
                        )
                    }
                }

                // Status Badge
                val (statusBg, statusFg) = when (agent.status) {
                    AgentStatus.ACTIVE -> Pair(LaserEmerald.copy(alpha = 0.15f), LaserEmerald)
                    AgentStatus.REASONING -> Pair(SolarAmber.copy(alpha = 0.15f), SolarAmber)
                    AgentStatus.VERIFYING -> Pair(ElectricViolet.copy(alpha = 0.15f), ElectricViolet)
                    AgentStatus.COMMITTED -> Pair(NeonCyan.copy(alpha = 0.15f), NeonCyan)
                    AgentStatus.IDLE -> Pair(SurfaceElevated, TextMuted)
                }

                Box(
                    modifier = Modifier
                        .background(statusBg, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = agent.status.name,
                        color = statusFg,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceElevated, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "TASK: ${agent.currentTask}",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "DOMAIN: ${agent.domain} | LEVEL ${agent.clearance}",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
