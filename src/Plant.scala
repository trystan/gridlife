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
    val tenth = energy / 10
    energy = tenth * 6
    
    var child = new Plant(
    		x + rng.nextInt(dna.spread*2+1) - dna.spread,
    		y + rng.nextInt(dna.spread*2+1) - dna.spread,
    		dna.mutate(rng))
    child.energy = tenth * 3
    child
  }
}
