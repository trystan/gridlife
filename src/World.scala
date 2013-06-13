import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class World(width: Int, height: Int) {
  var rng = new Random();
  var climate = new Climate(width, height, rng)
  
  var plantGrid = Array.ofDim[Plant](width, height)
  var plantList = Vector.empty[Plant]
  for (_ <- 0 to (width * height / 100))
	  addSeed(PlantMaker.random(rng))

  def update = {
    climate.update
    plantList.foreach(p => p.update(this))
    removeDeadPlants
  }
  
  def draw(g: Graphics2D) = {
    climate.draw(g)
    plantList.foreach(p => p.draw(g))
  }
  
  def climateAt(x: Int, y: Int):Int = {
    return climate.climateAt(x, y)
  }
  
  def addSeed(plant: Plant): Unit = {
    plant.x += rng.nextInt(6) - 3
    plant.y += rng.nextInt(6) - 3
    
    if (plant.x < 0 || plant.x >= width || plant.y < 0 || plant.y >= height)
      return
    
    if (plantGrid(plant.x)(plant.y) != null)
      return
    
    plantGrid(plant.x)(plant.y) = plant
    plantList = plantList :+ plant
  }
  
  def removeDeadPlants = {
    plantList.filter(p => !p.alive).foreach(p => plantGrid(p.x)(p.y) = null)
    plantList = plantList.filter(p => p.alive)
  }
}