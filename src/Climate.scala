import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class Climate(width: Int, height: Int, rng: Random) {

  var grid = Array.ofDim[Int](width, height)

  val noiseOffset = rng.nextInt()
  def noise(x: Int, y:Int) = ((ImprovedNoise.noise(x * 0.033, y * 0.033, noiseOffset) + 1.0) * 4.5).toInt
 
  for (x <- 0 until width)
  for (y <- 0 until height)
    grid(x)(y) = noise(x, y)

  def draw(g: Graphics2D): Unit = {
    foreachWithIndex(grid, (x, y, value) => {
      g.setColor(new Color(value * 9 + 8, value * 9 + 4, value * 9))
      g.fillRect(x * 4, y * 4, 4, 4)
    })
  }

  def foreachWithIndex(g: Array[Array[Int]], f: (Int, Int, Int) => Unit) = {
    for (x <- 0 until g.size)
    for (y <- 0 until g(x).size)
      f(x, y, g(x)(y))
  }
}