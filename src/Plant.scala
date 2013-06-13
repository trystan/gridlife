import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

object PlantMaker { // good idea? Do work that can't be done in alternate constructor 
  def random(width: Int, height: Int, rng: Random): Plant = {
    var p = new Plant(
                  rng.nextInt(width),
                  rng.nextInt(height),
                  new Color(64 + rng.nextInt(64), 64 + rng.nextInt(64), 64 + rng.nextInt(64)),
                  makeRandomEnergyPerClimate(rng))
    p.age = rng.nextInt(90)
    p.energy = rng.nextInt(10)
    p
  }
  
  private def makeRandomEnergyPerClimate(rng:Random):Array[Int] = {
    var arr = Array.ofDim[Int](9)
    for (_ <- 0 until 9)
      arr(rng.nextInt(9)) += 1
    arr
  }
}

class Plant(var x: Int, var y: Int, val color: Color, energyPerClimate:Array[Int]) {
  
  var age = 0
  var energy = 10
  
  def alive = { age < 100 && energy > 0 }
  
  def update(w: World) = {
    age += 1
    energy += energyPerClimate(w.climateAt(x, y)) - 1
    reproduce(w)
  }
  
  def mutate(rng: Random): Plant = {
    val childX = x + rng.nextInt(6) - 3
    val childY = y + rng.nextInt(6) - 3
    val childColor = new Color(color.getRed() + rng.nextInt(6) - 3,
                               color.getGreen() + rng.nextInt(6) - 3,
                               color.getBlue() + rng.nextInt(6) - 3)
    var epc = energyPerClimate.clone()
    var from = rng.nextInt(9)
    var to = rng.nextInt(9)
    if (epc(from) > 0 && epc(to) < 9)
    {
      epc(from) -= 1
      epc(to) += 1
    }
    new Plant(childX, childY, childColor, epc)
  }
  
  private def reproduce(w: World): Unit = {
    if (energy < 100) return
    
    energy -= 20
    w.addPlant(new Plant(x, y, color, energyPerClimate))
  }
}