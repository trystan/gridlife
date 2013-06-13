import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class World(width: Int, height: Int) {
  var rng = new Random();
  var climate = new Climate(width, height, rng)
  
  var plantGrid = Array.ofDim[Plant](width, height)
  var plantList = Vector.empty[Plant]
  for (_ <- 0 to (width * height / 100))
	  addPlant(PlantMaker.random(width, height, rng))
  
  def climateAt(x: Int, y: Int):Int = {
    return climate.climateAt(x, y)
  }
  
  def addPlant(plant: Plant): Unit = {
    val mutated = plant.mutate(rng)
    
    if (mutated.x < 0 || mutated.x >= width || mutated.y < 0 || mutated.y >= height)
      return
    
    if (plantGrid(mutated.x)(mutated.y) != null)
      return
      
    plantGrid(mutated.x)(mutated.y) = mutated
    plantList = plantList :+ mutated
  }
  
  def removeDeadPlants = {
    plantList.filter(p => !p.alive).foreach(p => plantGrid(p.x)(p.y) = null)
    plantList = plantList.filter(p => p.alive)
  }
  
  def update = {
    climate.update
    plantList.foreach(p => p.update(this))
    removeDeadPlants
  }
  
  def draw(g: Graphics2D) = {
    drawClimate(climate, g)
    plantList.foreach(p => drawPlant(p, g))
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