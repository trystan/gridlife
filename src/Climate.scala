import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class Climate(width: Int, height: Int, rng: Random) {

  var time = rng.nextDouble() * 20;

  var grid = Array.ofDim[Int](width, height)
  
  def climateAt(x: Int, y: Int):Int = {
    return grid(x)(y)
  }
  
  def update = {
    time += 0.001
    
    for (x <- 0 until width)
    for (y <- 0 until height)
      grid(x)(y) = ((ImprovedNoise.noise(x * 0.033, y * 0.033, time) + 1.0) * 4.5).toInt
  }
}