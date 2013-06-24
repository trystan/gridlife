import scala.util.Random

class World(width: Int, height: Int) {
  val rng = new Random();
  val climate = new Climate(width, height, rng)
  
  val plantGrid = Array.ofDim[Plant](width, height)
  var plantList = Vector.empty[Plant]
  for (_ <- 0 to (width * height / 100))
	  addPlant(Plant.random(width, height, rng))
  
  def climateAt(x: Int, y: Int):Int = {
    climate.climateAt(x, y)
  }
  
  def plantAt(x: Int, y: Int):Plant = {
    plantGrid(x)(y)
  }
  
  def addPlant(plant: Plant): Unit = {
    if (plant.x < 0 || plant.x >= width 
     || plant.y < 0 || plant.y >= height
     || plantGrid(plant.x)(plant.y) != null)
      return
    
    plantGrid(plant.x)(plant.y) = plant
    plantList = plantList :+ plant
  }
  
  def update = {
    climate.update
    plantList.foreach(p => p.update(this, rng))
    removeDeadPlants
  }
  
  def view = new View(width, height, climate, plantList)
  
  private def removeDeadPlants = {
    plantList.filter(p => !p.alive).foreach(p => plantGrid(p.x)(p.y) = null)
    plantList = plantList.filter(p => p.alive)
  }
}