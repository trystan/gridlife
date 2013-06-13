import scala.swing.Color
import scala.swing.Graphics2D

class View (width: Int, height: Int, climate: Climate, plants:Vector[Plant]) {
  
  def draw(g: Graphics2D) = {
    drawClimate(climate, g)
    plants.foreach(p => drawPlant(p, g))
  }
  
  def drawClimate(climate: Climate, g: Graphics2D): Unit = {
    for (x <- 0 until width)
    for (y <- 0 until height) {
      val value = climate.climateAt(x, y)
      g.setColor(new Color(value * 9 + 8, value * 9 + 4, value * 9))
      g.fillRect(x * 4, y * 4, 4, 4)
    }
  }
  
  def drawPlant(plant: Plant, g: Graphics2D): Unit = {
    g.setColor(plant.color)
    g.fillRect(plant.x * 4 + 1, plant.y * 4 + 1, 2, 2)
  }
}