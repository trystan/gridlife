package com.trystan.gridlife.core

import scala.util.Random
import scala.swing.Color

object PlantDna {
  def random(rng: Random): PlantDna = {
    new PlantDna(
      new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)),
      randomEnergyPerClimate(rng),
      rng.nextInt(8) + 1,
      50 + rng.nextInt(50) + rng.nextInt(50),
      rng.nextInt(10) + rng.nextInt(10) + 1)
  }

  def randomEnergyPerClimate(rng: Random): Array[Int] = {
    var epc = Array.ofDim[Int](9)
    epc(rng.nextInt(9)) += 2
    epc(rng.nextInt(9)) += 5
    epc(rng.nextInt(9)) += 2
    epc
  }
}

class PlantDna (
  val color: Color,
  val energyPerClimate: Array[Int],
  val spread: Int,
  val maxAge: Int,
  val growthSpeed: Int) {
  
  def mutate(rng: Random): PlantDna = {
    new PlantDna(
      new Color(mutateColor(color.getRed(), rng),
        mutateColor(color.getGreen(), rng),
        mutateColor(color.getBlue(), rng)),
      mutateEnergyPerClimate(energyPerClimate, rng),
      if (rng.nextDouble < 0.9) spread else mutateInteger(spread, 1, 20, rng),
      mutateInteger(maxAge, 10, 1000, rng),
      mutateInteger(growthSpeed, 1, 1000, rng))
  }

  private val offsets = List(0, -1, 0, 0, 1, 0)

  private def mutateInteger(int: Int, min: Int, max: Int, rng: Random): Int = {
    val offset = offsets(rng.nextInt(offsets.length))
    Math.max(min, Math.min(int + offset, max))
  }

  private def mutateColor(int: Int, rng: Random): Int = mutateInteger(int, 64, 250, rng)

  private def mutateEnergyPerClimate(source: Array[Int], rng: Random): Array[Int] = {
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