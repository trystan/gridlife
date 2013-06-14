import scala.util.Random

class Climate(width: Int, height: Int, rng: Random) {

  var time = rng.nextDouble() * 20
  
  var grid = Array.ofDim[Int](width, height)
  
  def shortCycle = Math.sin(Math.Pi * 2 * time * 10 / 11) * 0.5
  def mediumCycle = Math.sin(Math.Pi * 2 * time * 10 / 197) * 0.75
  def longCycle = Math.sin(Math.Pi * 2 * time * 10 / 499)
  def cycles = shortCycle + mediumCycle + longCycle
  
  def climateAt(x: Int, y: Int):Int = {
    return Math.max(0, Math.min(grid(x)(y), 8))
  }
  
  def update = {
    time += 0.001
    
    for (x <- 0 until width)
    for (y <- 0 until height)
      grid(x)(y) = ((ImprovedNoise.noise(x * 0.033, y * 0.033, time) + 1.0) * 4.5 
    		  		+ cycles).toInt 
  }
}