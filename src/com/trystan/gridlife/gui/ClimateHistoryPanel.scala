
package com.trystan.gridlife.gui

import scala.swing.Panel
import java.awt.Graphics2D
import com.trystan.gridlife.core.Climate

class ClimateHistoryPanel extends Panel {
  var recentClimate = Vector.empty[Double]
  val climateSampleRate = 10
  var climateSampleCounter = 0

  def update(climate: Climate) = {
    climateSampleCounter += 1

    if (climateSampleCounter == climateSampleRate) {
      climateSampleCounter = 0
      recentClimate = recentClimate :+ climate.cycles
      if (recentClimate.length > 200)
        recentClimate = recentClimate.tail
    }
  }

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    g.drawString("Climate", 2, 12)
    for (t <- 0 until 200) {
      if (recentClimate.length > t + 1)
        g.drawLine(t, (recentClimate(t) * -15 + 50).toInt, t + 1, (recentClimate(t + 1) * -15 + 50).toInt)
    }
  }
}