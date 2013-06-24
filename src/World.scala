import scala.util.Random

class World(width: Int, height: Int) {
  val rng = new Random();
  val climate = new Climate(width, height, rng)
  
  val plants = new PlantPopulation(width, height)
  plants.populate(width * height / 10, rng)
  
  def climateAt(x: Int, y: Int):Int = {
    climate.climateAt(x, y)
  }
  
  def plantAt(x: Int, y: Int):Plant = {
    plants.at(x, y)
  }
  
  def update = {
    climate.update
    plants.update(this, rng)
  }
  
  def view = new View(width, height, climate, plants.plantList)
}
