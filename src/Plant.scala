import scala.util.Random
import scala.swing.Color

object PlantMaker { // Do work that can't be done in alternate constructor here. Good idea?
  def random(width: Int, height: Int, rng: Random): Plant = {
    var p = new Plant(
                  rng.nextInt(width),
                  rng.nextInt(height),
                  new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)),
                  makeRandomEnergyPerClimate(rng))
    p.age = rng.nextInt(10)
    p.maxAge = 50 + rng.nextInt(50) + rng.nextInt(50)
    p.growthSpeed = rng.nextDouble() + rng.nextDouble();
    p.energy = rng.nextInt(90)
    p.spread = rng.nextInt(8) + 1
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
  var energy = 5.0
  var spread = 4
  var maxAge = 100
  var growthSpeed = 1.0
  
  def alive = { age * growthSpeed < maxAge && energy > 0 }
  
  def update(w: World, rng: Random) = {
    age += 1
    energy += (energyPerClimate(w.climateAt(x, y)) - 1) * growthSpeed
    
    reproduce(w, rng)
  }
  
  private def reproduce(w: World, rng: Random): Unit = {
    if (energy < 100 || age < maxAge / 2) return
    
    w.addPlant(makeChild(rng))
    energy *= 0.6
  }
  
  private def makeChild(rng: Random): Plant = {
    val childX = x + rng.nextInt(spread*2+1) - spread
    val childY = y + rng.nextInt(spread*2+1) - spread
    val epc = energyPerClimate.clone()
    val from = rng.nextInt(9)
    val to = rng.nextInt(9)
    val childColor = new Color(
		  				mutateColor(color.getRed(), rng),
    					mutateColor(color.getGreen(), rng),
    					mutateColor(color.getBlue(), rng))
    if (epc(from) > 0 && epc(to) < 9)
    {
      epc(from) -= 1
      epc(to) += 1
    }
    val child = new Plant(childX, childY, childColor, epc)
    child.energy = energy * 0.3
    child.maxAge = mutateInteger(maxAge, 10, 1000, rng)
    child.spread = if (rng.nextDouble < 0.9) spread else mutateInteger(spread, 1, 20, rng)
    child.growthSpeed = mutateDouble(growthSpeed, 0.1, 10.0, rng)
    child
  }
  
  private def mutateDouble(double:Double, min:Double, max:Double, rng: Random): Double = {
    val offset = (rng.nextDouble() - rng.nextDouble()) * 0.1
    Math.max(min, Math.min(double + offset, max))
  }
  
  private val offsets = List(0, -1, 0, 0, 1, 0)
  private def mutateInteger(int:Int, min:Int, max:Int, rng: Random): Int = {
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(min, Math.min(int + offset, max))
  }
  
  private def mutateColor(int:Int, rng: Random): Int = mutateInteger(int, 64, 250, rng)
}