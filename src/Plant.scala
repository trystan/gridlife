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
  var age = 1
  var energy = 1
  val color = dna.color
  val energyPerClimate = dna.energyPerClimate
  val maxAge = dna.maxAge
  val growthSpeed = dna.growthSpeed
  val spread = dna.spread

  def alive = { age < maxAge && energy > 0 }

  def update(w: World, rng: Random) = {
    age += 1
    energy += (energyPerClimate(w.climateAt(x, y)) - 1) * growthSpeed

    if (energy >= 1000 && age >= maxAge / 4)
      w.plants.add(makeChild(rng))
  }

  private def makeChild(rng: Random): Plant = {
    energy -= 100
    new Plant(
      x + rng.nextInt(spread * 2 + 1) - spread,
      y + rng.nextInt(spread * 2 + 1) - spread,
      dna.mutate(rng))
  }
}
