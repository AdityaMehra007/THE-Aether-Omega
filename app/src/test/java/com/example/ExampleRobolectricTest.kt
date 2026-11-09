package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.engine.SingularityEngines
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Aether Omega", appName)
  }

  @Test
  fun `verify double entry ledger zero sum invariant`() {
    val ledger = SingularityEngines.DoubleEntryLedgerEngine()
    val res = ledger.transfer("idem-test-1", "treasury:omnibus", "grid:tokamak_clearing", 50000L)
    assertTrue(res["isZeroSumVerified"] as Boolean)
  }

  @Test
  fun `verify surface code syndrome detection`() {
    val qec = SingularityEngines.SurfaceCodeQuantumEngine()
    val res = qec.simulateSyndromes(4, true)
    assertEquals(4, res["decodedCorrection"])
    assertEquals(1.0, res["fidelity"] as Double, 0.001)
  }
}
