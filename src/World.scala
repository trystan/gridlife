import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class World(width: Int, height: Int) {
  var rng = new Random();
  var climate = new Climate(width, height, rng)
  var plantList = Vector.empty :+ new Plant(rng)

  for (_ <- 0 to (width * height / 100))
    plantList = plantList :+ new Plant(rng)

  def draw(g: Graphics2D) = {
    climate.draw(g)
    plantList.foreach(p => p.draw(g))
  }
}