import scala.util.Random
import scala.swing.Color

object PlantMaker { // Do work that can't be done in alternate constructor here. Good idea?
  def random(width: Int, height: Int, rng: Random): Plant = {
    var p = new Plant(
                  rng.nextInt(width / 3) + width / 3,
                  rng.nextInt(height / 3) + height / 3,
                  new Color(64 + rng.nextInt(64), 64 + rng.nextInt(64), 64 + rng.nextInt(64)),
                  makeRandomEnergyPerClimate(rng))
    p.age = rng.nextInt(90)
    p.energy = rng.nextInt(90)
    p
  }
  
  private def makeRandomEnergyPerClimate(rng:Random):Array[Int] = {
    var arr = Array.ofDim[Int](9)
    for (_ <- 0 until 9)
      arr(rng.nextInt(9)) += 1
    arr
  }
}

class Plant(var x: Int, var y: Int, val color: Color, energyPerClimate: Array[Int]) {
  
  var age = 0
  var energy = 15
  
  def alive = { age < 100 && energy > 0 }
  
  def update(w: World, rng: Random) = {
    age += 1
    energy += energyPerClimate(w.climateAt(x, y)) - 1
    
    reproduce(w, rng)
  }
  
  private def reproduce(w: World, rng: Random): Unit = {
    if (energy < 100) return
    
    energy -= 20
    w.addPlant(makeChild(rng))
  }
  
  private def makeChild(rng: Random): Plant = {
    val childX = x + rng.nextInt(6) - 3
    val childY = y + rng.nextInt(6) - 3
    val childColor = new Color(
    						mutateColor(color.getRed(), rng),
    						mutateColor(color.getGreen(), rng),
    						mutateColor(color.getBlue(), rng))
    val epc = energyPerClimate.clone()
    val from = rng.nextInt(9)
    val to = rng.nextInt(9)
    if (epc(from) > 0 && epc(to) < 9)
    {
      epc(from) -= 1
      epc(to) += 1
    }
    new Plant(childX, childY, childColor, epc)
  }
  
  private val offsets = List(-2, -1, 0, 1, 2)
  private def mutateColor(int:Int, rng: Random): Int = {
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(32, Math.min(int + offset, 250))
  }
}