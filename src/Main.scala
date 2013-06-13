import scala.swing._

object gridlife extends SimpleSwingApplication {

  var world = new World(200, 200)
  world.update

  def top = new MainFrame {
    title = "Grid Life"
    size = new Dimension(600, 600)
    preferredSize = new Dimension(600, 600)
    minimumSize = new Dimension(600, 600)

    contents = new Panel {
      override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        
        world.draw(g)
      }
    }
  }
}
