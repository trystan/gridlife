import scala.util.Random
import scala.swing.Color

object PlantMaker { // Do work that can't be done in alternate constructor here. Good idea?
  def random(width: Int, height: Int, rng: Random): Plant = {
    var p = new Plant(
                  rng.nextInt(width),
                  rng.nextInt(height),
                  new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)),
                  makeRandomEnergyPerClimate(rng))
    p.age = rng.nextInt(90)
    p.energy = rng.nextInt(90)
    p.spread = rng.nextInt(4) + 1
    p
  }
  
  private def makeRandomEnergyPerClimate(rng:Random):Array[Int] = {
    var arr = Array.ofDim[Int](9)
    for (_ <- 0 until 9)
      arr(rng.nextInt(9)) += 1
    arr
  }
}

class Plant(var x: Int, var y: Int, val color: Color, val energyPerClimate: Array[Int]) {
  
  var age = 0
  var energy = 5
  var spread = 4
  
  def alive = { age < 100 && energy > 0 }
  
  def update(w: World, rng: Random) = {
    age += 1
    energy += energyPerClimate(w.climateAt(x, y)) - 1
    
    reproduce(w, rng)
  }
  
  private def reproduce(w: World, rng: Random): Unit = {
    if (energy < 100) return
    
    energy -= 10
    w.addPlant(makeChild(rng))
  }
  
  private def makeChild(rng: Random): Plant = {
    val childX = x + rng.nextInt(spread*2+1) - spread
    val childY = y + rng.nextInt(spread*2+1) - spread
    val epc = energyPerClimate.clone()
    val from = rng.nextInt(9)
    val to = rng.nextInt(9)
    val child = if (epc(from) > 0 && epc(to) < 9)
    {
      epc(from) -= 1
      epc(to) += 1
      
	  val childColor = new Color(
			  				mutateColor(color.getRed(), rng),
	    					mutateColor(color.getGreen(), rng),
	    					mutateColor(color.getBlue(), rng))
      new Plant(childX, childY, childColor, epc)
    }
    else
      new Plant(childX, childY, color, epc)
    
    child.spread = Math.max(1, offsets(rng.nextInt(offsets.length)) + spread)
    child
  }
  
  private val offsets = List(-2, -1, 0, 0, 0, 1, 2)
  private def mutateColor(int:Int, rng: Random): Int = {
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(64, Math.min(int + offset, 250))
  }
}