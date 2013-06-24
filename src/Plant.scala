import scala.util.Random
import scala.swing.Color

object Plant {
  def random(width: Int, height: Int, rng: Random): Plant = {
	  new Plant(
	      rng.nextInt(width),
	      rng.nextInt(height),
	      PlantDna.random(rng))
  }
}

object PlantDna {
  def random(rng: Random): PlantDna = {
	  new PlantDna(
	      new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)),
		  randomEnergyPerClimate(rng),
		  rng.nextInt(8) + 1,
		  50 + rng.nextInt(50) + rng.nextInt(50),
		  rng.nextInt(15) + rng.nextInt(15) + 1)
  }
  
  def randomEnergyPerClimate(rng: Random):Array[Int] = {
    var epc = Array.ofDim[Int](9)
    for (_ <- 0 until 9)
      epc(rng.nextInt(9)) += 1
    epc
  }
}

class Plant(var x: Int, var y: Int, val dna: PlantDna) {
  
  var age = 0
  var energy = 10
  val color = dna.color
  val energyPerClimate = dna.energyPerClimate
  val maxAge = dna.maxAge
  val growthSpeed = dna.growthSpeed
  val spread = dna.spread
  
  def alive = { age < dna.maxAge && energy > 0 }
  
  def update(w: World, rng: Random) = {
    age += 1
    energy += (dna.energyPerClimate(w.climateAt(x, y)) - 1) * dna.growthSpeed
    
    reproduce(w, rng)
  }
  
  private def reproduce(w: World, rng: Random): Unit = {
    if (energy < 1000 || age < dna.maxAge / 2) return
    
    w.addPlant(makeChild(rng))
  }
  
  private def makeChild(rng: Random): Plant = {
    val childX = x + rng.nextInt(dna.spread*2+1) - dna.spread
    val childY = y + rng.nextInt(dna.spread*2+1) - dna.spread
    
    var child = new Plant(childX, childY, dna.mutate(rng))
    child.energy = energy / 10 * 3
    energy = energy / 10 * 6
    child
  }
}

class PlantDna (
    val color: Color, 
    val energyPerClimate: Array[Int], 
    val spread: Int, 
    val maxAge: Int, 
    val growthSpeed: Int) {
  
  def mutate(rng: Random): PlantDna = {
    val epc = energyPerClimate.clone()
    val from = rng.nextInt(9)
    val to = rng.nextInt(9)
    
    if (epc(from) > 0 && epc(to) < 9)
    {
      epc(from) -= 1
      epc(to) += 1
    }
    
    new PlantDna(
    		new Color(mutateColor(color.getRed(), rng),
    				  mutateColor(color.getGreen(), rng),
    				  mutateColor(color.getBlue(), rng)),
    		epc,
	        if (rng.nextDouble < 0.9) spread else mutateInteger(spread, 1, 20, rng),
	        mutateInteger(maxAge, 10, 1000, rng),
	        mutateInteger(growthSpeed, 10, 1000, rng))
  }
  
  private val offsets = List(0, -1, 0, 0, 1, 0)
  private def mutateInteger(int:Int, min:Int, max:Int, rng: Random): Int = {
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(min, Math.min(int + offset, max))
  }
  
  private def mutateColor(int:Int, rng: Random): Int = mutateInteger(int, 64, 250, rng)
}