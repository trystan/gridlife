package com.trystan.gridlife.core

import scala.util.Random
import scala.swing.Color

object PlantDna {
  def random(rng: Random): PlantDna = {
    new PlantDna(
      new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)),
      randomEnergyPerClimate(rng),
      1 + rng.nextInt(5),
      1 + rng.nextInt(500),
      1 + rng.nextInt(10))
  }

  def randomEnergyPerClimate(rng: Random): Array[Int] = {
    var epc = Array.ofDim[Int](9)
    for (_ <- 1 to 9)
    	epc(rng.nextInt(9)) += 1
    epc
  }
}

class PlantDna (
  val color: Color,
  val energyPerClimate: Array[Int],
  val spread: Int,
  val maxAge: Int,
  val growthSpeed: Int) {
  
  val mutationRate = 0.1
  
  def mutate(rng: Random): PlantDna = {
    new PlantDna(
      new Color(
        mutateColor(color.getRed(), rng),
        mutateColor(color.getGreen(), rng),
        mutateColor(color.getBlue(), rng)),
      mutateEnergyPerClimate(energyPerClimate, rng),
      mutateInteger(spread, 1, 20, rng),
      mutateInteger(maxAge, 10, 1000, rng),
      mutateInteger(growthSpeed, 1, 1000, rng))
  }

  private val offsets = List(0, -1, 0, 0, 1, 0)
  private def mutateInteger(int: Int, min: Int, max: Int, rng: Random): Int = {
    if (rng.nextDouble > mutationRate)
      return int
    
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(min, Math.min(int + offset, max))
  }

  private val colorOffsets = List(-8, -6, -1, 0, 1, 6, 8)
  private def mutateColor(color: Int, rng: Random): Int = {
    if (rng.nextDouble > mutationRate)
      return color
    
    val offset = colorOffsets(rng.nextInt(colorOffsets.length))
    Math.max(64, Math.min(color + offset, 250))
  }

  private def mutateEnergyPerClimate(source: Array[Int], rng: Random): Array[Int] = {
    if (rng.nextDouble > mutationRate)
      return source.clone()
      
    val from = rng.nextInt(9)
    val to = rng.nextInt(9)
    val epc = source.clone()
    if (epc(from) > 0 && epc(to) < 9) {
      epc(from) -= 1
      epc(to) += 1
    }
    epc
  }
}