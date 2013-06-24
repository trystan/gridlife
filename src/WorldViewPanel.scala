import scala.swing.Panel
import scala.swing.event.MouseClicked
import java.awt.Graphics2D

class WorldViewPanel(world: World, selectedPlantInfoPanel: SelectedPlantInfoPanel) extends Panel {

  listenTo(mouse.clicks)

  reactions += {
    case e: MouseClicked =>
      selectedPlantInfoPanel.select(world.plantAt(e.point.x / 4, e.point.y / 4))
  }

  override def paintComponent(g: Graphics2D) = {
    super.paintComponent(g)

    world.view.draw(g)
  }
}