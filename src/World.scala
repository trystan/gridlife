import scala.util.Random

class World(width: Int, height: Int) {
  val rng = new Random();
  val climate = new Climate(width, height, rng)
  
  val plantGrid = Array.ofDim[Plant](width, height)
  var plantList = Vector.empty[Plant]
  for (_ <- 0 to (width * height / 100))
	  addPlant(PlantMaker.random(width, height, rng))
  
  def climateAt(x: Int, y: Int):Int = {
    return climate.climateAt(x, y)
  }
  
  def addPlant(plant: Plant): Unit = {
    val mutated = plant.mutate(rng)
    
    if (mutated.x < 0 || mutated.x >= width 
     || mutated.y < 0 || mutated.y >= height
     || plantGrid(mutated.x)(mutated.y) != null)
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
  
  def view = new View(width, height, climate, plantList)
}