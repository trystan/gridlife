package com.trystan.gridlife.gui

import scala.swing.Panel
import scala.swing.Label
import com.trystan.gridlife.core.Plant
import java.awt.Graphics2D
import java.awt.Color

class SelectedPlantInfoPanel extends Panel {
  var selectedPlant:Plant = null
  
  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)
    g.clearRect(0, 0, g.getClipBounds().width, g.getClipBounds().height)
    
    if (selectedPlant == null) { 
      g.drawString("No plant selected", 0, 14)
    } 
    else {
	    g.setColor(selectedPlant.color)
	    g.fillRect(1, 1, 32, 32)
	    
	    g.setColor(new Color(0, 0, 0))
	    
	    g.drawString(selectedPlant.toString().split("@")(1), 44, 14)
	    
	    for (i <- 0 to 8) {
	      val h = selectedPlant.energyPerClimate(i) * 3 + 1
	      g.fillRect(34 + i * 10, 32 - h, 8, h)
	    }
	    
	    var height = 14
	    g.drawString("Max age:", 0, 34 + height * 1)
	    g.drawString("Spread :", 0, 34 + height * 2)
	    g.drawString("Growth :", 0, 34 + height * 3)
	    
	    g.drawString(selectedPlant.maxAge.toString(), 60, 34 + height * 1)
	    g.drawString(selectedPlant.spread.toString(), 60, 34 + height * 2)
	    g.drawString(selectedPlant.growthSpeed.toString(), 60, 34 + height * 3)
    }
  }

  def select(plant: Plant) = {
    selectedPlant = plant
  }
}