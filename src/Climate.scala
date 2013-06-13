import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class Climate(width: Int, height: Int, rng: Random) {

  var time = rng.nextDouble() * 20;

  var grid = Array.ofDim[Int](width, height)
  
  def update = {
    time += 1
    
    for (x <- 0 until width)
    for (y <- 0 until height)
      grid(x)(y) = ((ImprovedNoise.noise(x * 0.033, y * 0.033, time) + 1.0) * 4.5).toInt
  }
  
  def draw(g: Graphics2D): Unit = {
    for (x <- 0 until width)
    for (y <- 0 until height) {
      val value = grid(x)(y)
      g.setColor(new Color(value * 9 + 8, value * 9 + 4, value * 9))
      g.fillRect(x * 4, y * 4, 4, 4)
    }
  }
}