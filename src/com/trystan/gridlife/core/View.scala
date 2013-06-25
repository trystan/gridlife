package com.trystan.gridlife.core

import scala.swing.Color
import scala.swing.Graphics2D

class View(width: Int, height: Int, climate: Climate, plants: Vector[Plant]) {

  val climateColors: Array[Color] = Array.ofDim[Color](9)
  for (i <- 0 until 9) {
    climateColors(i) = new Color(32 + i * 8 + 8, 32 + i * 8 + 4, 32 + i * 8)
  }

  def draw(g: Graphics2D) = {
    drawClimate(climate, g)
    plants.foreach(p => drawPlant(p, g))
  }

  def drawClimate(climate: Climate, g: Graphics2D): Unit = {
    for (x <- 0 until width)
      for (y <- 0 until height) {
        val value = climate.climateAt(x, y)
        g.setColor(climateColors(value))
        g.fillRect(x * 4, y * 4, 4, 4)
      }
  }

  def drawPlant(plant: Plant, g: Graphics2D): Unit = {
    g.setColor(plant.color)
    if (plant.age < 5)
      g.fillRect(plant.x * 4 + 2, plant.y * 4 + 2, 1, 1)
    else if (plant.age < 20)
      g.fillRect(plant.x * 4 + 2, plant.y * 4 + 2, 2, 2)
    else
      g.fillRect(plant.x * 4 + 1, plant.y * 4 + 1, 3, 3)
  }
}