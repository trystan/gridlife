import scala.util.Random

class PlantPopulation (width: Int, height: Int) {
  val plantGrid = Array.ofDim[Plant](width, height)
  var plantList = Vector.empty[Plant]
  
  def populate(count: Int, rng: Random) = {
	  for (_ <- 0 to count)
		  add(Plant.random(width, height, rng))
  }
  
  def update(world: World, rng: Random): Unit = {
    plantList.foreach(p => p.update(world, rng))
    plantList.filter(p => !p.alive).foreach(p => plantGrid(p.x)(p.y) = null)
    plantList = plantList.filter(p => p.alive)
  }
  
  def at(x: Int, y: Int):Plant = {
    plantGrid(x)(y)
  }
  
  def add(plant: Plant): Unit = {
    if (plant.x < 0 || plant.x >= width 
     || plant.y < 0 || plant.y >= height
     || plantGrid(plant.x)(plant.y) != null)
      return
    
    plantGrid(plant.x)(plant.y) = plant
    plantList = plantList :+ plant
  }
}