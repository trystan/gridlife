import scala.swing._

object gridlife extends SimpleSwingApplication {

  var world = new World(200, 150)
  world.update
  
  def top = new MainFrame {
    title = "Grid Life"
    size = new Dimension(800, 600)
    preferredSize = new Dimension(800, 600)
    minimumSize = new Dimension(800, 600)

    contents = new Panel {
      override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        
        world.view.draw(g)
      }
    }
    
    def update = {
      world.update
      this.repaint
    }
    
    val timer = new javax.swing.Timer((30.0 / 1.0).toInt, 
        new javax.swing.AbstractAction() {
	    	def actionPerformed(e : java.awt.event.ActionEvent) = update
  	    })
    timer.setRepeats(true)
    timer.start()
  }
}
