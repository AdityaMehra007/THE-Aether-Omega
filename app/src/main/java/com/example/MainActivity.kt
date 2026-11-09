package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavigationTab
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.AetherOmegaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AetherOmegaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AetherOmegaApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AetherOmegaApp(viewModel: AetherOmegaViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val planetaryState by viewModel.planetaryState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("aether_omega_root"),
        containerColor = SpaceBackground,
        contentColor = TextPrimary,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "AETHER-OMEGA",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = NeonCyan,
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceElevated, RoundedCornerShape(4.dp))
                                .border(0.5.dp, SurfaceBorder, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "v2027.1",
                                color = TextSecondary,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                },
                actions = {
                    // Quick SCRAM warning indicator or Action Button
                    if (planetaryState.isScramActive) {
                        Box(
                            modifier = Modifier
                                .background(PlasmaRuby.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                .border(1.dp, PlasmaRuby, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "SCRAM ACTIVE",
                                color = PlasmaRuby,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.runFullBenchmarkSuite() },
                            modifier = Modifier.testTag("action_run_quick_benchmark")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Run 15-Engine Benchmark",
                                tint = NeonCyan
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpaceBackground,
                    titleContentColor = TextPrimary
                ),
                windowInsets = WindowInsets.statusBars
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceDark,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars
            ) {
                NavigationTab.entries.forEach { tab ->
                    val isSelected = (currentTab == tab)
                    val icon = when (tab) {
                        NavigationTab.COMMAND -> Icons.Default.Dashboard
                        NavigationTab.SWARM -> Icons.Default.Hub
                        NavigationTab.LABORATORY -> Icons.Default.Science
                        NavigationTab.CODEX -> Icons.Default.MenuBook
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SpaceBackground,
                            unselectedIconColor = TextSecondary,
                            selectedTextColor = NeonCyan,
                            unselectedTextColor = TextMuted,
                            indicatorColor = NeonCyan
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                NavigationTab.COMMAND -> PlanetaryCommandScreen(viewModel = viewModel)
                NavigationTab.SWARM -> AgentSwarmScreen(viewModel = viewModel)
                NavigationTab.LABORATORY -> EngineLabScreen(viewModel = viewModel)
                NavigationTab.CODEX -> ArchitectureCodexScreen()
            }
        }
    }
}
