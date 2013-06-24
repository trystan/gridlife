import scala.swing.Panel
import scala.swing.Label

class SelectedPlantInfoPanel extends Panel {
  val html = new Label { text = "<html>No plant selected</html>" }
  
  _contents += html
  
  def select(plant: Plant) = {
	  if (plant == null) {
	    html.text = "<html>No plant selected</html>"
	  } else {
	    html.text = "<html>Energy gained per climate: "
	    for (i <- 0 until plant.energyPerClimate.length)
	      html.text += " " + plant.energyPerClimate(i)
	    html.text += "<br/>Max Age: " + plant.maxAge
	    html.text += "<br/>Spread: " + plant.spread
	    html.text += "<br/>Growth: " + plant.growthSpeed
	    html.text += "</html>"
	  }
  }
}